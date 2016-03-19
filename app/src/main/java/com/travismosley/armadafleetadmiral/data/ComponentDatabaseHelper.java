package com.travismosley.armadafleetadmiral.data;

import android.content.Context;

import com.travismosley.android.data.database.helper.PackagedDatabaseHelper;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract;

/**
 * This class helps initialize and open the database;
 */
public class ComponentDatabaseHelper extends PackagedDatabaseHelper{

    protected ComponentDatabaseHelper(Context context){
        super(context, ComponentDatabaseContract.DATABASE_NAME);
    }
}