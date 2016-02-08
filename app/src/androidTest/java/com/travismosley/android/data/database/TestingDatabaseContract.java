package com.travismosley.android.data.database;

import android.provider.BaseColumns;

import java.lang.Boolean;
import java.lang.Integer;


public class TestingDatabaseContract {

    // Contract class for our unit testing database

    public static final String DATABASE_NAME = "unittest.db";

    public static abstract class TestTable implements BaseColumns{
        public static final String TABLE_NAME = "test_table";
        public static final String COL_STRING = "col_string";
        public static final String COL_INT = "col_int";
        public static final String COL_BOOL = "col_bool";

        public static final String ROW_ONE_COL_STRING_VALUE = "foo";
        public static final String ROW_TWO_COL_STRING_VALUE = "bar";

        public static final Integer ROW_ONE_COL_INT_VALUE = 13;
        public static final Integer ROW_TWO_COL_INT_VALUE = 25;

        public static final Boolean ROW_ONE_COL_BOOL_VALUE = true;
        public static final Boolean ROW_TWO_COL_BOOL_VALUE = false;

    }
}
