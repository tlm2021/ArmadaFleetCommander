package com.travismosley.armadafleetcommander.data;

import android.content.Context;

import com.travismosley.android.data.database.helper.PackagedDatabaseHelper;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract;

/**
 * This class helps initialize and open the database;
 */
public class ArmadaDatabaseHelper extends PackagedDatabaseHelper{

    public ArmadaDatabaseHelper(Context context){
        super(context, ArmadaDatabaseContract.DATABASE_NAME);
    }
}

