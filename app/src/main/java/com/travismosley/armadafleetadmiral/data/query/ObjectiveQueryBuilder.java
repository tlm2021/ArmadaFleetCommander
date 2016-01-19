package com.travismosley.armadafleetadmiral.data.query;

import com.travismosley.android.data.database.QueryBuilder;
import com.travismosley.armadafleetadmiral.data.contract.ArmadaDatabaseContract.ObjectiveTable;
import com.travismosley.armadafleetadmiral.data.contract.table.ObjectiveTableContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */

public class ObjectiveQueryBuilder extends QueryBuilder {

    private final String LOG_TAG = ObjectiveQueryBuilder.class.getSimpleName();

    protected String getTableName(){
        return ObjectiveTable.TABLE_NAME;
    }

    @Override
    protected List<String> getColumns() {
        return new ArrayList<>(Arrays.asList(
                ObjectiveTableContract._ID,
                ObjectiveTableContract.TITLE,
                ObjectiveTableContract.DESCRIPTION,
                ObjectiveTableContract.VICTORY_POINTS,
                ObjectiveTableContract.TYPE_ID,
                ObjectiveTableContract.TYPE_NAME));
    }

    @Override
    protected String getOrderBy(){
        return ObjectiveTableContract.TYPE_ID + "," + ObjectiveTableContract.TITLE;
    }

    protected  String getTypeIdWhereClause(int typeId){
        return ObjectiveTableContract.TYPE_ID + "=" + String.valueOf(typeId);
    }

    public String queryWhereTypeId(int typeId){
        return queryWhere(getTypeIdWhereClause(typeId));
    }
}