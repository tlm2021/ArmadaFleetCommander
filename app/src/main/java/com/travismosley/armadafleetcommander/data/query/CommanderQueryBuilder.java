package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.CommanderTable;
import com.travismosley.armadafleetcommander.data.query.base.BaseUpgradeQueryBuilder;

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
