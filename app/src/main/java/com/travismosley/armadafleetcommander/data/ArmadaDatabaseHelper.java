package com.travismosley.armadafleetcommander.data;

import android.content.Context;

import com.travismosley.data.database.helper.PackagedDatabaseHelper;

/**
 * This class helps initialize and open the database;
 */
public class ArmadaDatabaseHelper extends PackagedDatabaseHelper{

    private static final String DB_NAME = "armada_fleet_commander.db";

    public ArmadaDatabaseHelper(Context context){
        super(context, DB_NAME);
    }
}

