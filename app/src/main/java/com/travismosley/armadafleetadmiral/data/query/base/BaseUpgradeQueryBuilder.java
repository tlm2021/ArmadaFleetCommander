package com.travismosley.armadafleetadmiral.data.query.base;

import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.UpgradeTable;

import java.util.Arrays;
import java.util.List;

/**
 * A helper class around SQLiteQUeryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public abstract class BaseUpgradeQueryBuilder extends ComponentQueryBuilder {

    private final String LOG_TAG = BaseUpgradeQueryBuilder.class.getSimpleName();

    @Override
    protected List<String> getColumns(){
        List<String> columns = super.getColumns();
        columns.addAll(Arrays.asList(
                UpgradeTable.TYPE_ID,
                UpgradeTable.TYPE_NAME,
                UpgradeTable.TEXT,
                UpgradeTable.IS_UNIQUE,
                UpgradeTable.SHIP_CLASS_ID));
        return columns;
    }

    @Override
    protected String getOrderBy(){
        return UpgradeTable.TYPE_ID + "," + UpgradeTable.POINT_COST + "," + UpgradeTable.TITLE;
    }

    private String getTypeIdWhereClause(int typeId){
        return UpgradeTable.TYPE_ID + "='" + typeId + "'";
    }

    public String queryWhereTypeIdAndFactionId(int typeId, int factionId){
        String whereClause = "(" + getFactionWhereClause(factionId) + ") AND " + getTypeIdWhereClause(typeId);
        return queryWhere(whereClause);
    }
}
