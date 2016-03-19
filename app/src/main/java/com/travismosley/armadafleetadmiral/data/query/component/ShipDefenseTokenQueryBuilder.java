package com.travismosley.armadafleetadmiral.data.query.component;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ShipDefenseTokenQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = ShipDefenseTokenQueryBuilder.class.getSimpleName();

    protected String getTableName() {
        return ComponentDatabaseContract.ShipDefenseTokenTable.TABLE_NAME;
    }

    @Override
    protected List<String> getColumns() {
        return new ArrayList<>(Arrays.asList(
                ComponentDatabaseContract.ShipDefenseTokenTable.SHIP_ID,
                ComponentDatabaseContract.ShipDefenseTokenTable.DEFENSE_TOKEN_ID,
                ComponentDatabaseContract.ShipDefenseTokenTable.TITLE));
    }

    protected String getWhereShipIdClause(int shipId) {
        return ComponentDatabaseContract.ShipDefenseTokenTable.SHIP_ID + "=" + String.valueOf(shipId);
    }

    public String queryWhereShipId(int id) {
        return queryWhere(getWhereShipIdClause(id));
    }

}
