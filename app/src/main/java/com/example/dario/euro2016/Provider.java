package com.example.dario.euro2016;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Dario on 09/07/2016.
 */
public class Provider extends ContentProvider {

    private Db db;

    private static final String TAG = "provider";

    private static final int CODE_PARTITE = 200;
    private static final int CODE_PARTITA = 201;
    private static final int CODE_PARTITE_GIORNATA = 202;

    private static final int CODE_CLASSIFICHE = 300;
    private static final int CODE_CLASSIFICA = 301;
    private static final int CODE_CLASSIFICHE_GRUPPO = 302;
    private static final int CODE_CLASSIFICHE_NOMI_GRUPPO = 303;

    private static final int CODE_TORNEI = 400;
    private static final int CODE_TORNEO = 401;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.Partite.TABLE_NAME,
                CODE_PARTITE);

        matcher.addURI(authority, Contract.Partite.TABLE_NAME + "/#",
                CODE_PARTITA);

        matcher.addURI(authority, Contract.Partite.TABLE_NAME + "/" + Contract.Partite.COLUMN_GIORNATA + "/*",
                CODE_PARTITE_GIORNATA);

        matcher.addURI(authority, Contract.Classifiche.TABLE_NAME,
                CODE_CLASSIFICHE);

        matcher.addURI(authority, Contract.Classifiche.TABLE_NAME + "/#",
                CODE_CLASSIFICA);

        matcher.addURI(authority, Contract.Partite.TABLE_NAME + "/*",
                CODE_CLASSIFICHE_GRUPPO);

        matcher.addURI(authority, Contract.Partite.TABLE_NAME + "/nomi",
                CODE_CLASSIFICHE_NOMI_GRUPPO);

        matcher.addURI(authority, Contract.Torneo.TABLE_NAME,
                CODE_TORNEI);

        matcher.addURI(authority, Contract.Torneo.TABLE_NAME + "/#",
                CODE_TORNEO);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        //istanzio il db qui
        db = new Db(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (sUriMatcher.match(uri)){
            case CODE_PARTITE:
                cursor = db.getReadableDatabase().query(
                        Contract.Partite.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder
                );
                break;

            case CODE_PARTITA:
                cursor = db.getReadableDatabase().query(
                        Contract.Partite.TABLE_NAME,
                        projection,
                        Contract.Partite.COLUMN_ID_API_PARTITA + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder
                );
                break;

            case CODE_PARTITE_GIORNATA:
                cursor = db.getReadableDatabase().query(
                        Contract.Partite.TABLE_NAME,
                        projection,
                        Contract.Partite.COLUMN_GIORNATA + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder
                );
                Log.d(TAG, "query: " + uri.getLastPathSegment() + ", " + cursor.getCount());
                break;

            case CODE_CLASSIFICHE:
                cursor = db.getReadableDatabase().query(
                        Contract.Classifiche.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder
                );
                break;

            case CODE_CLASSIFICA:
                cursor = db.getReadableDatabase().query(
                        Contract.Classifiche.TABLE_NAME,
                        projection,
                        Contract.Classifiche.COLUMN_ID_API_SQ + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder
                );
                break;

            case CODE_CLASSIFICHE_GRUPPO:
                cursor = db.getReadableDatabase().query(
                        Contract.Classifiche.TABLE_NAME,
                        projection,
                        Contract.Classifiche.COLUMN_GRUPPO + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder
                );
                break;

            case CODE_CLASSIFICHE_NOMI_GRUPPO:
                cursor = db.getReadableDatabase().query(
                        true,
                        Contract.Classifiche.TABLE_NAME,
                        new String[] {Contract.Classifiche.COLUMN_GRUPPO},
                        selection,
                        selectionArgs,
                        null, null, sortOrder, null
                );
                break;

            case CODE_TORNEI:
                cursor = db.getReadableDatabase().query(
                        Contract.Torneo.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder
                );
                break;

            case CODE_TORNEO:
                cursor = db.getReadableDatabase().query(
                        Contract.Torneo.TABLE_NAME,
                        projection,
                        Contract.Torneo.COLUMN_ID_API + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        Log.d(TAG, "query: " + uri.toString() + ", " + cursor.getCount());

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri returnUri = null;
        long id = -1;

        switch (sUriMatcher.match(uri)){

            case CODE_PARTITE:
                id = db.getWritableDatabase().insert(
                        Contract.Partite.TABLE_NAME,
                        null, values
                );
                break;

            case CODE_CLASSIFICHE:

                id = db.getWritableDatabase().insert(
                        Contract.Classifiche.TABLE_NAME,
                        null, values
                );
                break;

            case CODE_TORNEI:

                id = db.getWritableDatabase().insert(
                        Contract.Torneo.TABLE_NAME,
                        null, values
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        returnUri = ContentUris.withAppendedId(uri, id);

        Log.d(TAG, "insert: " + uri.toString() + ", " + returnUri.toString());

        if(!returnUri.getLastPathSegment().equals("-1"))
            getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {

        int rows = 0;

        final SQLiteDatabase myDb = db.getWritableDatabase();

        switch (sUriMatcher.match(uri)){

            case CODE_PARTITE:
                rows = myDb.delete(Contract.Partite.TABLE_NAME, where, whereArgs);
                break;

            case CODE_CLASSIFICHE:
                rows = myDb.delete(Contract.Classifiche.TABLE_NAME, where, whereArgs);
                break;

            case CODE_TORNEI:
                rows = myDb.delete(Contract.Torneo.TABLE_NAME, where, whereArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(rows>0)
            getContext().getContentResolver().notifyChange(uri, null);

        Log.d(TAG, "delete: " + uri.toString() + ", " + rows);

        return rows;

    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {

        int rows = 0;

        switch (sUriMatcher.match(uri)){

            case CODE_PARTITE:
                rows = db.getWritableDatabase().update(
                        Contract.Partite.TABLE_NAME,
                        values,
                        where,
                        whereArgs
                );
                break;

            case CODE_PARTITA:
                rows = db.getWritableDatabase().update(
                        Contract.Partite.TABLE_NAME,
                        values,
                        Contract.Partite.COLUMN_ID_API_PARTITA + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );
                break;

            case CODE_CLASSIFICHE:
                rows = db.getWritableDatabase().update(
                        Contract.Classifiche.TABLE_NAME,
                        values,
                        where,
                        whereArgs
                );
                break;

            case CODE_CLASSIFICA:
                rows = db.getWritableDatabase().update(
                        Contract.Classifiche.TABLE_NAME,
                        values,
                        Contract.Classifiche.COLUMN_ID_API_SQ + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );
                break;

            case CODE_TORNEI:
                rows = db.getWritableDatabase().update(
                        Contract.Torneo.TABLE_NAME,
                        values,
                        where,
                        whereArgs
                );
                break;

            case CODE_TORNEO:
                rows = db.getWritableDatabase().update(
                        Contract.Torneo.TABLE_NAME,
                        values,
                        Contract.Torneo.COLUMN_ID_API + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(rows>0)
            getContext().getContentResolver().notifyChange(uri, null);

        Log.d(TAG, "update: " + uri.toString() + ", " + rows);

        return rows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){

        final SQLiteDatabase myDb = db.getWritableDatabase();
        myDb.beginTransaction();

        int returnCount = 0;

        switch(sUriMatcher.match(uri)){

            case CODE_PARTITE:
                returnCount = QueryHelper.setBulkInsert(getContext(), Contract.Partite.TABLE_NAME, myDb, values);
                break;

            case CODE_CLASSIFICHE:
                returnCount = QueryHelper.setBulkInsert(getContext(), Contract.Classifiche.TABLE_NAME, myDb, values);
                break;

            case CODE_TORNEI:
                returnCount = QueryHelper.setBulkInsert(getContext(), Contract.Torneo.TABLE_NAME, myDb, values);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        Log.i(TAG, "bulk insert: " +  uri.toString() + ", " + returnCount);

        if(returnCount>0)
            getContext().getContentResolver().notifyChange(uri, null);

        return returnCount;
    }
}
