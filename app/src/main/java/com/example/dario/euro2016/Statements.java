package com.example.dario.euro2016;

/**
 * Created by Dario on 09/07/2016.
 */
public class Statements {

    private static final String START_STATEMENTS = "create table if not exists ";

    public static final String PARTITE_STATEMENT = START_STATEMENTS + Contract.Partite.TABLE_NAME + " (" +
            Contract.Partite.COLUMN_ID + " integer primary key, " +
            Contract.Partite.COLUMN_ID_API_PARTITA + " integer not null unique, " +
            Contract.Partite.COLUMN_DATA + " text, " +
            Contract.Partite.COLUMN_STATO + " text, " +
            Contract.Partite.COLUMN_GIORNATA + " integer, " +
            Contract.Partite.COLUMN_SQ_CASA + " text, " +
            Contract.Partite.COLUMN_SQ_FUORI + " text, " +
            Contract.Partite.COLUMN_GOL_CASA + " integer, " +
            Contract.Partite.COLUMN_GOL_FUORI + " integer, " +
            Contract.Partite.COLUMN_GOL_PT_CASA + " integer, " +
            Contract.Partite.COLUMN_GOL_PT_FUORI + " integer, " +
            Contract.Partite.COLUMN_GOL_SUP_CASA + " integer, " +
            Contract.Partite.COLUMN_GOL_SUP_FUORI + " integer, " +
            Contract.Partite.COLUMN_GOL_RIG_CASA + " integer, " +
            Contract.Partite.COLUMN_GOL_RIG_FUORI + " integer, " +
            Contract.Partite.COLUMN_ID_API_CASA + " integer not null, " +
            Contract.Partite.COLUMN_ID_API_FUORI + " integer not null);";

    public static final String CLASSIFICHE_STATEMENT = START_STATEMENTS + Contract.Classifiche.TABLE_NAME + " (" +
            Contract.Classifiche.COLUMN_ID + " integer primary key, " +
            Contract.Classifiche.COLUMN_GRUPPO + " text, " +
            Contract.Classifiche.COLUMN_POSIZIONE + " integer, " +
            Contract.Classifiche.COLUMN_NOME_SQ + " text, " +
            Contract.Classifiche.COLUMN_ID_API_SQ + " integer not null unique, " +
            Contract.Classifiche.COLUMN_NUM_PARTITE + " integer, " +
            Contract.Classifiche.COLUMN_PUNTI + " integer, " +
            Contract.Classifiche.COLUMN_GOL_FATTI + " integer, " +
            Contract.Classifiche.COLUMN_GOL_SUBITI + " integer, " +
            Contract.Classifiche.COLUMN_DIFF_RETI + " integer);";

    public static final String TORNEO_STATEMENT = START_STATEMENTS + Contract.Torneo.TABLE_NAME + " (" +
            Contract.Torneo.COLUMN_ID + " integer primary key, " +
            Contract.Torneo.COLUMN_NOME + " text, " +
            Contract.Torneo.COLUMN_ANNO + " text, " +
            Contract.Torneo.COLUMN_ID_API + " integer not null unique, " +
            Contract.Torneo.COLUMN_GIOR_CORRENTE + " integer, " +
            Contract.Torneo.COLUMN_NUM_GIORNATE + " integer, " +
            Contract.Torneo.COLUMN_NUM_SQ + " integer, " +
            Contract.Torneo.COLUMN_NUM_PARTITE + " integer, " +
            Contract.Torneo.COLUMN_LAST_UPDATE + " text);";
}
