package com.example.dario.euro2016;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    private PartiteAdapter mPartiteAdapter;
    private Context mContext;
    private Communication listener;
    public static final String TAG = MainActivityFragment.class.getSimpleName();
    ResponseReceiver receiver;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Communication)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = rootView.getContext();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        getActivity().getSupportLoaderManager().initLoader(1, null, this);

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(1);
        getActivity().getSupportLoaderManager().destroyLoader(2);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(id == 1) {
            return new CursorLoader(
                    getActivity(),
                    Contract.Torneo.buildUri(424),
                    null,
                    Contract.Torneo.COLUMN_ID_API + " = ?",
                    new String[] {"424"},
                    null
            );
        }
        else {
            return new CursorLoader(
                    getActivity(),
                    Contract.Partite.buildUriFilter(Contract.Partite.COLUMN_GIORNATA, String.valueOf(args.getInt("giornata"))),
                    null,
                    Contract.Partite.COLUMN_GIORNATA + " = ?",
                    new String[]{String.valueOf(args.getInt("giornata"))},
                    null
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(loader.getId() == 1){
            int giorCorrente = 0;
            if(data != null && data.moveToFirst()) {
                int giorCorrenteIndex = data.getColumnIndex(Contract.Torneo.COLUMN_GIOR_CORRENTE);
                giorCorrente = data.getInt(giorCorrenteIndex);
            }
            Bundle bundle = new Bundle();
            bundle.putInt("giornata", giorCorrente);
            getActivity().getSupportLoaderManager().restartLoader(2, bundle, this);
        }
        else {
            if (data.getCount() != 0) {
                if (mPartiteAdapter == null) {

                    mPartiteAdapter = new PartiteAdapter(data);

                    mPartiteAdapter.setOnItemClickListener(new PartiteAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(long id) {
                            if (listener != null)
                                listener.onItemChoosed(id);
                        }
                    });
                } else
                    mPartiteAdapter.swapCursor(data);

                if (mRecyclerView.getAdapter() == null)
                    mRecyclerView.setAdapter(mPartiteAdapter);
            } else {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (!(networkInfo != null && networkInfo.isConnected())) {
                    Toast.makeText(mContext, "Dati non presenti nel DB.\n Attivare la Connessione", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPartiteAdapter.swapCursor(null);
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.logo_main);
        Drawable logo_torneo = getActivity().getResources()
                .getDrawable(
                        getActivity().getResources().
                                getIdentifier("logo_euro_2016", "drawable", getActivity().getPackageName()));
        Glide.with(getActivity()).load("").placeholder(logo_torneo).centerCrop().into(imageView);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        receiver = new ResponseReceiver();
        mContext.registerReceiver(receiver, filter);

        if(mPartiteAdapter != null)
            mPartiteAdapter.notifyDataSetChanged();
    }

    public interface Communication{
        void onItemChoosed(long id);
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "com.example.dario.euro2016.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().getSupportLoaderManager().restartLoader(1, null, MainActivityFragment.this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(receiver);
    }

}
