package com.travismosley.armadafleetcommander.data.query;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipTable;
import com.travismosley.armadafleetcommander.data.query.base.ComponentQueryBuilder;

/**
 * A helper class around SQLiteQUeryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class ShipQueryBuilder extends ComponentQueryBuilder {

    private final String LOG_TAG = ShipQueryBuilder.class.getSimpleName();

    protected String getTableName(){
        return ShipTable.TABLE_NAME;
    }

}
