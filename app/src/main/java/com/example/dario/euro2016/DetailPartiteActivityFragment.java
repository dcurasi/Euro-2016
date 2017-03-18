package com.example.dario.euro2016;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailPartiteActivityFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    private ShareActionProvider mShareActionProvider;
    Partita mDetailPartita = new Partita();
    View mView;

    public DetailPartiteActivityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View detailPartiteView = inflater.inflate(R.layout.fragment_detail_partite, container, false);
        mView = detailPartiteView;

        Bundle bundle = new Bundle();
        bundle.putLong(Contract.Partite.COLUMN_ID_API_PARTITA, getArguments().getLong(Contract.Partite.COLUMN_ID_API_PARTITA));
        getActivity().getSupportLoaderManager().initLoader(4, bundle, this);

        return detailPartiteView;
    }


    public static DetailPartiteActivityFragment newInstance(long id){

        Bundle args = new Bundle();
        args.putLong(Contract.Partite.COLUMN_ID_API_PARTITA, id);

        DetailPartiteActivityFragment detailPartiteActivityFragment = new DetailPartiteActivityFragment();
        detailPartiteActivityFragment.setArguments(args);

        return detailPartiteActivityFragment;

    }

    public void onDetach() {
        super.onDetach();
        getActivity().getSupportLoaderManager().destroyLoader(4);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                Contract.Partite.buildUri(args.getLong(Contract.Partite.COLUMN_ID_API_PARTITA)),
                null,
                Contract.Partite.COLUMN_ID_API_PARTITA + " = ?",
                new String[]{Long.toString(args.getLong(Contract.Partite.COLUMN_ID_API_PARTITA))},
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mDetailPartita == null) {
            mDetailPartita = new Partita();
        }
        if (data != null && data.moveToFirst()) {
            mDetailPartita.setId(data.getLong(data.getColumnIndex(Contract.Partite.COLUMN_ID)));
            mDetailPartita.setId_api_partita(data.getLong(data.getColumnIndex(Contract.Partite.COLUMN_ID_API_PARTITA)));
            mDetailPartita.setId_api_casa(data.getLong(data.getColumnIndex(Contract.Partite.COLUMN_ID_API_CASA)));
            mDetailPartita.setId_api_fuori(data.getLong(data.getColumnIndex(Contract.Partite.COLUMN_ID_API_FUORI)));
            mDetailPartita.setData(data.getString(data.getColumnIndex(Contract.Partite.COLUMN_DATA)));
            mDetailPartita.setStato(data.getString(data.getColumnIndex(Contract.Partite.COLUMN_STATO)));
            mDetailPartita.setGiornata(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GIORNATA)));
            mDetailPartita.setSquadra_casa(data.getString(data.getColumnIndex(Contract.Partite.COLUMN_SQ_CASA)));
            mDetailPartita.setSquadra_fuori(data.getString(data.getColumnIndex(Contract.Partite.COLUMN_SQ_FUORI)));
            mDetailPartita.setGol_casa(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_CASA)));
            mDetailPartita.setGol_fuori(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_FUORI)));
            mDetailPartita.setGol_pt_casa(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_PT_CASA)));
            mDetailPartita.setGol_pt_fuori(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_PT_FUORI)));
            mDetailPartita.setGol_sup_casa(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_SUP_CASA)));
            mDetailPartita.setGol_sup_fuori(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_SUP_FUORI)));
            mDetailPartita.setGol_rig_casa(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_RIG_CASA)));
            mDetailPartita.setGol_rig_fuori(data.getInt(data.getColumnIndex(Contract.Partite.COLUMN_GOL_RIG_FUORI)));

            setDatiDetailPartita(mDetailPartita);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDetailPartita = null;
    }

    public void setDatiDetailPartita(Partita datiPartita) {
        TextView data = (TextView) mView.findViewById(R.id.dt_data_partita);
        data.setText(datiPartita.getGiorno());
        TextView giornata = (TextView) mView.findViewById(R.id.dt_giornata);
        giornata.setText(datiPartita.getGiornataName());
        TextView ora = (TextView) mView.findViewById(R.id.dt_ora_partita);
        ora.setText(datiPartita.getOra());
        TextView slug_casa = (TextView) mView.findViewById(R.id.dt_slug_sq_casa);
        slug_casa.setText(datiPartita.getSquadra_casa());
        TextView slug_fuori = (TextView) mView.findViewById(R.id.dt_slug_sq_fuori);
        slug_fuori.setText(datiPartita.getSquadra_fuori());
        TextView primo_tempo = (TextView) mView.findViewById(R.id.dt_half_time);
        TextView risultato = (TextView) mView.findViewById(R.id.dt_risultato);
        TextView extra_time = (TextView) mView.findViewById(R.id.dt_extra_time);
        if(datiPartita.getStato().equals("TIMED")) {
            primo_tempo.setText("");
            risultato.setText(" - ");
            extra_time.setText("");
        }
        else {
            primo_tempo.setText(datiPartita.getRisultatoPt());
            risultato.setText(datiPartita.getRisultato());
            if(datiPartita.isRigori()) {
                extra_time.setText(datiPartita.getRisultatoRig());
            }
            else if(datiPartita.isSupplementari()) {
                extra_time.setText(datiPartita.getRisultatoSup());
            }
            else {
                extra_time.setText("");
            }
        }

        ImageView img_sq_casa = (ImageView) mView.findViewById(R.id.dt_img_sq_casa);
        Drawable flag_casa = mView.getContext().getResources()
                .getDrawable(
                        mView.getContext().getResources().
                                getIdentifier("img_flag_" + datiPartita.getId_api_casa(), "drawable", mView.getContext().getPackageName()));
        Glide.with(mView.getContext()).load("").placeholder(flag_casa).centerCrop().into(img_sq_casa);

        ImageView img_sq_fuori = (ImageView) mView.findViewById(R.id.dt_img_sq_fuori);
        Drawable flag_fuori = mView.getContext().getResources()
                .getDrawable(
                        mView.getContext().getResources().
                                getIdentifier("img_flag_" + datiPartita.getId_api_fuori(), "drawable", mView.getContext().getPackageName()));
        Glide.with(mView.getContext()).load("").placeholder(flag_fuori).centerCrop().into(img_sq_fuori);
    }
}
