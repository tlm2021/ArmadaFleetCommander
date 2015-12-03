package com.travismosley.logging.database;

import android.database.Cursor;
import android.util.Log;

/**
 * A helper for logging the index of a column name within a cursor
 */
public class ColumnIndexLogger {

    private final String mTag;

    public ColumnIndexLogger(String tag){
        mTag = tag;
    }

    public void logIndex(String columnName, Cursor cursor){
        Log.d(mTag, columnName + " " + cursor.getColumnIndex(columnName));
    }
}
