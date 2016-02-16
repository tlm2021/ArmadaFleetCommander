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
public class FleetQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = FleetQueryBuilder.class.getSimpleName();

    @Override
    protected List<String> getColumns(){
        return new ArrayList<>(Arrays.asList(
                FleetDatabaseContract.FleetTable._ID,
                FleetDatabaseContract.FleetTable.FACTION_ID,
                FleetDatabaseContract.FleetTable.NAME,
                FleetDatabaseContract.FleetTable.FLEET_POINT_TOTAL,
                FleetDatabaseContract.FleetTable.FLEET_POINT_LIMIT,
                FleetDatabaseContract.FleetTable.COMMANDER_ID));
    }

    @Override
    protected String getTableName(){
        return FleetDatabaseContract.FleetTable.TABLE_NAME;
    }

    @Override
    protected String getOrderBy(){
        return FleetDatabaseContract.FleetTable.NAME;
    }

    private String getFactionIdWhereClause(int factionId){
        return FleetDatabaseContract.FleetTable.FACTION_ID + "='" + factionId + "'";
    }

    public String queryWhereFactionId(int factionId){
        return queryWhere(getFactionIdWhereClause(factionId));
    }

    private String getFleetIdWhereClause(int fleetId){
        return FleetDatabaseContract.FleetTable._ID + "='" + fleetId + "'";
    }

    public String queryWhereFleetId(int fleetId){
        return queryWhere(getFleetIdWhereClause(fleetId));
    }
}
