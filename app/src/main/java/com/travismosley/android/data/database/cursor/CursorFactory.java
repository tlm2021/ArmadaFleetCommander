package com.travismosley.android.data.database.cursor;

import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

/**
 * A factory for returning com.travismosley.data.databse.cursor.Cursor objects
 */
public class CursorFactory implements SQLiteDatabase.CursorFactory{

    public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query){
        return new Cursor(masterQuery, editTable, query);
    }
}
