package com.example.dario.euro2016;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dario on 09/07/2016.
 */
public class QueryHelper {

    public static int setBulkInsert(Context context, String table, SQLiteDatabase myDb, ContentValues[] values){

        int returnCount = 0;

        try {

            for (ContentValues value : values) {

                final long _id = myDb.replace(table, null, value);

                if (_id != -1)
                    returnCount++;
            }

            myDb.setTransactionSuccessful();

        } finally {
            myDb.endTransaction();
        }

        return returnCount;
    }

}
