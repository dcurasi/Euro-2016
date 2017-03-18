package com.example.dario.euro2016;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;

/**
 * Created by Dario on 18/07/2016.
 */
public class ClassificheFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    private ClassificheAdapter mClassificheAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    public static final String TAG = ClassificheFragment.class.getSimpleName();



    public ClassificheFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View classificheView = inflater.inflate(R.layout.fragment_classifiche, container, false);
        mContext = classificheView.getContext();
        mRecyclerView = (RecyclerView) classificheView.findViewById(R.id.recyclerview_classifiche);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getActivity().getSupportLoaderManager().initLoader(3, null, this);

        return classificheView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(3);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                Contract.Classifiche.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<Classifica[]> classifiche;
        if (data != null) {

            classifiche = Utility.cursorToClassifiche(data);

            if (mClassificheAdapter == null) {

                mClassificheAdapter = new ClassificheAdapter(classifiche);

            } else
                mClassificheAdapter.modelChanged(classifiche);

            if (mRecyclerView.getAdapter() == null)
                mRecyclerView.setAdapter(mClassificheAdapter);
        } else {
            Toast.makeText(mContext, "Dati non presenti nel DB.\n Attivare la Connessione", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mClassificheAdapter.modelChanged(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mClassificheAdapter != null)
            mClassificheAdapter.notifyDataSetChanged();
    }

}
