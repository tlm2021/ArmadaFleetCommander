package com.travismosley.armadafleetadmiral.data.query;

import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.ShipTable;
import com.travismosley.armadafleetadmiral.data.query.base.VehicleQueryBuilder;

/**
 * A helper class around SQLiteQUeryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class ShipQueryBuilder extends VehicleQueryBuilder {

    private final String LOG_TAG = ShipQueryBuilder.class.getSimpleName();

    @Override
    protected String getTableName(){
        return ShipTable.TABLE_NAME;
    }
}
