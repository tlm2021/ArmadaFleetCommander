package com.travismosley.android.data.database.contract;

import android.provider.BaseColumns;

public abstract class BaseTableContract implements BaseColumns {

    public static String getForeignKeyColumnSql(String columnName,String tableName){
        return columnName + " INTEGER REFERENCES " + tableName + "(" + _ID + ")" + " ON UPDATE CASCADE";
    }
}