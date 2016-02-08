package com.travismosley.android.data.database;

import android.content.Context;

import com.travismosley.android.data.database.helper.PackagedDatabaseHelper;

/**
 * Database helper for the fetched the testing database
 */
public class TestingDatabaseHelper extends PackagedDatabaseHelper {

    public TestingDatabaseHelper(Context context){
        super(context, TestingDatabaseContract.DATABASE_NAME);
    }
}