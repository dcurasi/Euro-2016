package com.example.dario.euro2016;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Dario on 16/07/2016.
 */
public class Utility {

    public Utility(){
    }

    public static Partita cursorToPartita(Cursor partiteCursor, int position) {
        partiteCursor.moveToPosition(position);
        Partita partita = new Partita();
        partita.setId(partiteCursor.getLong(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_ID)));
        partita.setId_api_partita(partiteCursor.getLong(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_ID_API_PARTITA)));
        partita.setId_api_casa(partiteCursor.getLong(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_ID_API_CASA)));
        partita.setId_api_fuori(partiteCursor.getLong(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_ID_API_FUORI)));
        partita.setData(partiteCursor.getString(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_DATA)));
        partita.setStato(partiteCursor.getString(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_STATO)));
        partita.setGiornata(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GIORNATA)));
        partita.setSquadra_casa(partiteCursor.getString(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_SQ_CASA)));
        partita.setSquadra_fuori(partiteCursor.getString(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_SQ_FUORI)));
        if (!partita.getStato().equals("TIMED")) {
            partita.setGol_casa(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_CASA)));
            partita.setGol_fuori(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_FUORI)));
            partita.setGol_pt_casa(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_PT_CASA)));
            partita.setGol_pt_fuori(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_PT_FUORI)));
            partita.setGol_sup_casa(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_SUP_CASA)));
            partita.setGol_sup_fuori(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_SUP_FUORI)));
            partita.setGol_rig_casa(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_RIG_CASA)));
            partita.setGol_rig_fuori(partiteCursor.getInt(partiteCursor.getColumnIndex(Contract.Partite.COLUMN_GOL_RIG_FUORI)));
        }

        return partita;
    }

    public static List<Classifica[]> cursorToClassifiche(Cursor classificaCursor) {
        Classifica[] gruppo = new Classifica[4];
        List<Classifica[]> result = new ArrayList<Classifica[]>();
        int i = 0;
        Classifica classifica;
        if (classificaCursor != null && classificaCursor.moveToFirst()) {
            do {
                classifica = new Classifica();
                classifica.setId_api_sq(classificaCursor.getLong(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_ID_API_SQ)));
                classifica.setGruppo(classificaCursor.getString(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_GRUPPO)));
                classifica.setPosizione(classificaCursor.getInt(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_POSIZIONE)));
                classifica.setNome_sq(classificaCursor.getString(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_NOME_SQ)));
                classifica.setNum_partite(classificaCursor.getInt(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_NUM_PARTITE)));
                classifica.setPunti(classificaCursor.getInt(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_PUNTI)));
                classifica.setGol_fatti(classificaCursor.getInt(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_GOL_FATTI)));
                classifica.setGol_subiti(classificaCursor.getInt(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_GOL_SUBITI)));
                classifica.setDiff_reti(classificaCursor.getInt(classificaCursor.getColumnIndex(Contract.Classifiche.COLUMN_DIFF_RETI)));

                gruppo[(i % 4)] = classifica;
                i++;
                if(((i % 4) == 0) && (i!= 0)) {
                    result.add(gruppo);
                    gruppo = new Classifica[4];
                }
            } while (classificaCursor.moveToNext());
        }
        return result;
    }

    public static long getApiIdByLinkPartita(String link) {
        return Long.valueOf(link.substring(38));
    }

    public static long getApiIdPartitaByLinkPartita(String link) {
        return Long.valueOf(link.substring(41));
    }

    public static String setLast_update(String last_update) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "dd/MM/yy HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(last_update);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Log.d("Utility", e.getMessage() );
        }
        return str;
    }

}
