package com.travismosley.armadafleetadmiral.data.query.base;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.table.ComponentTableContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about various components.
 */
public abstract class ComponentQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = ComponentQueryBuilder.class.getSimpleName();

    @Override
    protected List<String> getColumns() {
        return new ArrayList<>(Arrays.asList(
                ComponentTableContract._ID,
                ComponentTableContract.TITLE,
                ComponentTableContract.FACTION_ID,
                ComponentTableContract.POINT_COST));
    }

    @Override
    protected String getOrderBy(){
        return ComponentTableContract.TITLE;
    }

    protected String getFactionWhereClause(int factionId){
        return ComponentTableContract.FACTION_ID + "=" + String.valueOf(factionId) +
                " OR " + ComponentTableContract.FACTION_ID + " is NULL";
    }

    public String queryWhereFactionId(int factionId){
        return queryWhere(getFactionWhereClause(factionId));
    }

}
