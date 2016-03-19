package com.travismosley.armadafleetadmiral.data.query.component;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SquadronDefenseTokenQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = SquadronDefenseTokenQueryBuilder.class.getSimpleName();

    protected String getTableName() {
        return ComponentDatabaseContract.SquadronDefenseTokenTable.TABLE_NAME;
    }

    @Override
    protected List<String> getColumns() {
        return new ArrayList<>(Arrays.asList(
                ComponentDatabaseContract.SquadronDefenseTokenTable.SQUADRON_ID,
                ComponentDatabaseContract.SquadronDefenseTokenTable.DEFENSE_TOKEN_ID,
                ComponentDatabaseContract.SquadronDefenseTokenTable.TITLE));
    }

    protected String getWhereSquadronIdClause(int shipId) {
        return ComponentDatabaseContract.SquadronDefenseTokenTable.SQUADRON_ID + "=" + String.valueOf(shipId);
    }

    public String queryWhereSquadronId(int id) {
        return queryWhere(getWhereSquadronIdClause(id));
    }

}
