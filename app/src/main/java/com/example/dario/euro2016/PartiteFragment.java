package com.example.dario.euro2016;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Dario on 15/07/2016.
 */
public class PartiteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    private PartiteAdapter mPartiteAdapter;
    private Communication listener;
    public static final String TAG = PartiteFragment.class.getSimpleName();
    private String[] nomiGior = {
                    "Tutte",
                    "Giornata 1",
                    "Giornata 2",
                    "Giornata 3",
                    "Ottavi di finale",
                    "Quarti di finale",
                    "Semi-finali",
                    "Finale"
    };

    public PartiteFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Communication)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View partiteView = inflater.inflate(R.layout.fragment_partite, container, false);

        mRecyclerView = (RecyclerView) partiteView.findViewById(R.id.recyclerview_partite);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_giornata);
        spinnerAdapter.addAll(nomiGior);
        Spinner sp=(Spinner) partiteView.findViewById(R.id.select_giornata);
        sp.setAdapter(spinnerAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> av, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(Contract.Partite.COLUMN_GIORNATA, position);
                if(position == 0) {
                    getActivity().getSupportLoaderManager().initLoader(5, bundle, PartiteFragment.this);
                }
                else {
                    getActivity().getSupportLoaderManager().restartLoader(6, bundle, PartiteFragment.this);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0){ }
        });

        return partiteView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(5);
        getActivity().getSupportLoaderManager().destroyLoader(6);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(id == 5){
            return new CursorLoader(
                    getActivity(),
                    Contract.Partite.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }
        else {
            return new CursorLoader(
                    getActivity(),
                    Contract.Partite.buildUriFilter(Contract.Partite.COLUMN_GIORNATA, Integer.toString(args.getInt(Contract.Partite.COLUMN_GIORNATA))),
                    null,
                    Contract.Partite.COLUMN_GIORNATA + " = ?",
                    new String[]{Integer.toString(args.getInt(Contract.Partite.COLUMN_GIORNATA))},
                    null
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        if (mPartiteAdapter == null) {

            mPartiteAdapter = new PartiteAdapter(data);

            mPartiteAdapter.setOnItemClickListener(new PartiteAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(long id) {
                    if (listener != null)
                        listener.onItemChoosed(id);
                }
            });
        }
        else
            mPartiteAdapter.swapCursor(data);

        if(mRecyclerView.getAdapter()==null)
            mRecyclerView.setAdapter(mPartiteAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPartiteAdapter.swapCursor(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mPartiteAdapter != null)
            mPartiteAdapter.notifyDataSetChanged();
    }

    public interface Communication{
        void onItemChoosed(long id);
    }
}
