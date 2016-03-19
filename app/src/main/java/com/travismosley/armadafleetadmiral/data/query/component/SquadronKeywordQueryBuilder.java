package com.travismosley.armadafleetadmiral.data.query.component;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SquadronKeywordQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = SquadronKeywordQueryBuilder.class.getSimpleName();

    protected String getTableName() {
        return ComponentDatabaseContract.SquadronKeywordTable.TABLE_NAME;
    }

    @Override
    protected List<String> getColumns() {
        return new ArrayList<>(Arrays.asList(
                ComponentDatabaseContract.SquadronKeywordTable.SQUADRON_ID,
                ComponentDatabaseContract.SquadronKeywordTable.SQUADRON_KEYWORD_ID,
                ComponentDatabaseContract.SquadronKeywordTable.TITLE));
    }

    protected String getWhereSquadronIdClause(int shipId) {
        return ComponentDatabaseContract.SquadronKeywordTable.SQUADRON_ID + "=" + String.valueOf(shipId);
    }

    public String queryWhereSquadronId(int id) {
        return queryWhere(getWhereSquadronIdClause(id));
    }

}
