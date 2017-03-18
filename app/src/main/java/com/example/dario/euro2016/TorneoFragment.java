package com.example.dario.euro2016;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TorneoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private View mView;
    private long idTorneo;
    public static final String TAG = TorneoFragment.class.getSimpleName();

    public TorneoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View torneoView = inflater.inflate(R.layout.fragment_torneo, container, false);

        mView = torneoView;

        idTorneo = getArguments().getLong(Contract.Torneo.COLUMN_ID_API);

        return torneoView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = new Bundle();
        bundle.putLong(Contract.Torneo.COLUMN_ID_API, idTorneo);
        getActivity().getSupportLoaderManager().initLoader(7, bundle, this);

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.logo_main);
        Drawable logo_torneo = getActivity().getResources()
                .getDrawable(
                        getActivity().getResources().
                                getIdentifier("logo_euro_2016", "drawable", getActivity().getPackageName()));
        Glide.with(getActivity()).load("").placeholder(logo_torneo).centerCrop().into(imageView);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                Contract.Torneo.buildUri(args.getLong(Contract.Torneo.COLUMN_ID_API)),
                null,
                Contract.Torneo.COLUMN_ID_API + " = ?",
                new String[]{String.valueOf(args.getLong(Contract.Torneo.COLUMN_ID_API))},
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.moveToFirst()) {
            TextView currentGiornata = (TextView) mView.findViewById(R.id.giornata_corrente);
            currentGiornata.setText(String.valueOf(data.getInt(data.getColumnIndex(Contract.Torneo.COLUMN_GIOR_CORRENTE))));
            TextView totGiornate = (TextView) mView.findViewById(R.id.tot_giornate);
            totGiornate.setText(String.valueOf(data.getInt(data.getColumnIndex(Contract.Torneo.COLUMN_NUM_GIORNATE))));
            TextView numSquadre = (TextView) mView.findViewById(R.id.num_squadre);
            numSquadre.setText(String.valueOf(data.getInt(data.getColumnIndex(Contract.Torneo.COLUMN_NUM_SQ))));
            TextView numPartite = (TextView) mView.findViewById(R.id.num_partite);
            numPartite.setText(String.valueOf(data.getInt(data.getColumnIndex(Contract.Torneo.COLUMN_NUM_PARTITE))));
            TextView lastUpdate = (TextView) mView.findViewById(R.id.last_update);
            lastUpdate.setText(Utility.setLast_update(data.getString(data.getColumnIndex(Contract.Torneo.COLUMN_LAST_UPDATE))));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(7);
    }
}
