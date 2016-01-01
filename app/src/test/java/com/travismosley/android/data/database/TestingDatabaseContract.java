package com.travismosley.android.data.database;

import android.provider.BaseColumns;


public class TestingDatabaseContract {

    // Contract class for our unit testing database

    public static final String DATABASE_NAME = "unittest.db";

    public static abstract class TestTable implements BaseColumns{
        public static final String TABLE_NAME = "test_table";
        public static final String COL_STRING = "col_string";
        public static final String COL_INT = "col_int";
        public static final String COL_BOOL = "col_bool";
    }
}
