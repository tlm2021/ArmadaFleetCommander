package com.travismosley.armadafleetcommander.data.query;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.UpgradeTable;
import com.travismosley.armadafleetcommander.data.query.base.BaseUpgradeQueryBuilder;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */

public class UpgradeQueryBuilder extends BaseUpgradeQueryBuilder {

    private final String LOG_TAG = UpgradeQueryBuilder.class.getSimpleName();

    protected String getTableName(){
        return UpgradeTable.TABLE_NAME;
    }

}
