package com.travismosley.armadafleetadmiral.data.contract.table;

import android.provider.BaseColumns;

/**
 * Created by Travis on 2/10/2016.
 */
public class BaseTableContract implements BaseColumns {

    // These should be overridden in sub classes
    public static final String TABLE_NAME = null;
    public static final String CREATE_TABLE_SQL = null;

    public static final String getForeignKeyColumnSql(String columnName){
        return "FOREIGN KEY(" + columnName + ") REFERENCES + " + TABLE_NAME + "(" + _ID + ")";
    }

}
