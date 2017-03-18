package com.example.dario.euro2016;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Dario on 09/07/2016.
 */
public class Db extends SQLiteOpenHelper {

    private static final String DB_NAME = "euro16.db";
    private static final int DB_VERSION = 5;

    public Db(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Statements.PARTITE_STATEMENT);
        db.execSQL(Statements.CLASSIFICHE_STATEMENT);
        db.execSQL(Statements.TORNEO_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Partite.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Classifiche.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Torneo.TABLE_NAME);
        onCreate(db);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
