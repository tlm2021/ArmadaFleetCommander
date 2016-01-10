package com.travismosley.armadafleetadmiral.data.query;

import android.database.sqlite.SQLiteQueryBuilder;

import com.travismosley.armadafleetadmiral.data.contract.ArmadaDatabaseContract.CommanderTable;
import com.travismosley.armadafleetadmiral.data.query.base.BaseUpgradeQueryBuilder;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */

public class CommanderQueryBuilder extends BaseUpgradeQueryBuilder{

    private final String LOG_TAG = CommanderQueryBuilder.class.getSimpleName();
    private SQLiteQueryBuilder mQueryBuilder;

    protected String getTableName(){
        return CommanderTable.TABLE_NAME;
    }
}
