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
public class FleetSquadronsQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = FleetSquadronsQueryBuilder.class.getSimpleName();

    @Override
    protected List<String> getColumns(){
        return new ArrayList<>(Arrays.asList(
                FleetDatabaseContract.FleetSquadronsTable.FLEET_ID,
                FleetDatabaseContract.FleetSquadronsTable.SQUADRON_ID,
                FleetDatabaseContract.FleetSquadronsTable.TABLE_NAME));
    }

    @Override
    protected String getTableName(){
        return FleetDatabaseContract.FleetSquadronsTable.TABLE_NAME;
    }

    @Override
    protected String getOrderBy(){
        return FleetDatabaseContract.FleetSquadronsTable.SQUADRON_ID;
    }

    private String getFleetIdWhereClause(int fleetId){
        return FleetDatabaseContract.FleetSquadronsTable.FLEET_ID + "='" + fleetId + "'";
    }

    public String getSquadronIdWhereClause(int squadId){
        return FleetDatabaseContract.FleetSquadronsTable.SQUADRON_ID + "='" + squadId + "'";
    }

    public String getFleetAndSquadronIdWhereClause(int fleetId, int squadId){
        return getFleetIdWhereClause(fleetId) + " AND " + getSquadronIdWhereClause(squadId);
    }

    public String queryWhereFleetId(int fleetId){
        return queryWhere(getFleetIdWhereClause(fleetId));
    }

    public String queryWhereFleetAndSquadronId(int fleetId, int squadId){
        return queryWhere(getFleetAndSquadronIdWhereClause(fleetId, squadId));
    }
}
