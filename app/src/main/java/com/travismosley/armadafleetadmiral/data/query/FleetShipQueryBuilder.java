package com.travismosley.armadafleetadmiral.data.query;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class FleetShipQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = FleetShipQueryBuilder.class.getSimpleName();

    @Override
    protected List<String> getColumns(){
        return new ArrayList<>(Arrays.asList(
                FleetDatabaseContract.FleetShipView.FLEET_ID,
                FleetDatabaseContract.FleetShipView.SHIP_BUILD_ID,
                FleetDatabaseContract.FleetShipView.SHIP_ID,
                FleetDatabaseContract.FleetShipView.CUSTOM_TITLE,
                FleetDatabaseContract.FleetShipView.TITLE_UPGRADE_ID));
    }

    @Override
    protected String getTableName(){
        return FleetDatabaseContract.FleetShipView.VIEW_NAME;
    }

    @Override
    protected String getOrderBy(){
        return FleetDatabaseContract.FleetShipView.CUSTOM_TITLE;
    }

    public String getFleetIdWhereClause(int fleetId) {
        return FleetDatabaseContract.FleetShipView.FLEET_ID + "='" + fleetId + "'";
    }

    public String queryWhereFleetId(int fleetId){
        return queryWhere(getFleetIdWhereClause(fleetId));
    }
}
