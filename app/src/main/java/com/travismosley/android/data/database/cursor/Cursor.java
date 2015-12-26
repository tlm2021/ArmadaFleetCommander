package com.travismosley.android.data.database.cursor;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteQuery;

/**
 * Custom cursor with some helpers for getting values by column name
 */
public class Cursor extends SQLiteCursor{

    Cursor(SQLiteCursorDriver driver, String editTable, SQLiteQuery query){
        super(driver, editTable, query);
    }

    public String getString(String columnTitle){
        return getString(getColumnIndex(columnTitle));
    }

    public int getInt(String columnTitle){
        return getInt(getColumnIndex(columnTitle));
    }

    public boolean getBoolean(String columnTitle){
        return getInt(columnTitle) != 0;
    }
}
