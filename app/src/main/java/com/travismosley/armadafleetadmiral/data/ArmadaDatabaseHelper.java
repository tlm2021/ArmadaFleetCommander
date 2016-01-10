package com.travismosley.armadafleetadmiral.data;

import android.content.Context;

import com.travismosley.android.data.database.helper.PackagedDatabaseHelper;
import com.travismosley.armadafleetadmiral.data.contract.ArmadaDatabaseContract;

/**
 * This class helps initialize and open the database;
 */
public class ArmadaDatabaseHelper extends PackagedDatabaseHelper{

    protected ArmadaDatabaseHelper(Context context){
        super(context, ArmadaDatabaseContract.DATABASE_NAME);
    }


}

