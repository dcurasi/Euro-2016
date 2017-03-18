package com.example.dario.euro2016;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by Dario on 09/07/2016.
 */
public class Contract {

    public static final String CONTENT_AUTHORITY = "com.example.dario.euro2016";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class Partite {

        public static final String TABLE_NAME = "partite";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ID_API_PARTITA = "id_api_partita";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_STATO = "stato";
        public static final String COLUMN_GIORNATA = "giornata";
        public static final String COLUMN_SQ_CASA = "sq_casa";
        public static final String COLUMN_SQ_FUORI = "sq_fuori";
        public static final String COLUMN_GOL_CASA = "gol_casa";
        public static final String COLUMN_GOL_FUORI = "gol_fuori";
        public static final String COLUMN_GOL_PT_CASA = "gol_pt_casa";
        public static final String COLUMN_GOL_PT_FUORI = "gol_pt_fuori";
        public static final String COLUMN_GOL_SUP_CASA = "gol_sup_casa";
        public static final String COLUMN_GOL_SUP_FUORI = "gol_sup_fuori";
        public static final String COLUMN_GOL_RIG_CASA = "gol_rig_casa";
        public static final String COLUMN_GOL_RIG_FUORI = "gol_rig_fuori";
        public static final String COLUMN_ID_API_CASA = "id_api_casa";
        public static final String COLUMN_ID_API_FUORI = "id_api_fuori";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static Uri buildUriFilter(String filter, String num){
            return CONTENT_URI.buildUpon().appendPath(filter).appendPath(num).build();
        }

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static final class Classifiche {

        public static final String TABLE_NAME = "classifiche";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_GRUPPO = "gruppo";
        public static final String COLUMN_POSIZIONE = "posizione";
        public static final String COLUMN_NOME_SQ = "nome_sq";
        public static final String COLUMN_ID_API_SQ = "id_api_sq";
        public static final String COLUMN_NUM_PARTITE = "num_partite";
        public static final String COLUMN_PUNTI = "punti";
        public static final String COLUMN_GOL_FATTI = "gol_fatti";
        public static final String COLUMN_GOL_SUBITI = "gol_subiti";
        public static final String COLUMN_DIFF_RETI = "diff_reti";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriFilter(String filter){
            return CONTENT_URI.buildUpon().appendPath(filter).build();
        }

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static final class Torneo {

        public static final String TABLE_NAME = "torneo";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_ANNO = "anno";
        public static final String COLUMN_ID_API = "id_api";
        public static final String COLUMN_GIOR_CORRENTE = "gior_corrente";
        public static final String COLUMN_NUM_GIORNATE = "num_giornate";
        public static final String COLUMN_NUM_SQ = "num_squadre";
        public static final String COLUMN_NUM_PARTITE = "num_partite";
        public static final String COLUMN_LAST_UPDATE = "last_update";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }
}
