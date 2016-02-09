package com.travismosley.armadafleetadmiral.data.query;

import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.SquadronTable;
import com.travismosley.armadafleetadmiral.data.query.base.VehicleQueryBuilder;

import java.util.List;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class SquadronQueryBuilder extends VehicleQueryBuilder {

    private final String LOG_TAG = SquadronQueryBuilder.class.getSimpleName();

    protected String getTableName(){
        return SquadronTable.TABLE_NAME;
    }

    @Override
    protected List<String> getColumns(){
        List<String> columns = super.getColumns();
        columns.add(SquadronTable.IS_UNIQUE);
        return columns;
    }

}
