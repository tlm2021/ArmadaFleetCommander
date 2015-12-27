package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.UpgradeTable;

/**
 * A helper class around SQLiteQUeryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class UpgradeQueryBuilder {

    private final String LOG_TAG = UpgradeQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            UpgradeTable.COLUMN_NAME_ID,
            UpgradeTable.COLUMN_NAME_TITLE,
            UpgradeTable.COLUMN_NAME_TYPE_NAME,
            UpgradeTable.COLUMN_NAME_IS_UNIQUE,
            UpgradeTable.COLUMN_NAME_POINT_COST,
            UpgradeTable.COLUMN_NAME_TEXT,
            UpgradeTable.COLUMN_NAME_FACTION_ID
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = UpgradeTable.COLUMN_NAME_TYPE_NAME + "," +
            UpgradeTable.COLUMN_NAME_POINT_COST + "," +
            UpgradeTable.COLUMN_NAME_TITLE;

    public UpgradeQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(UpgradeTable.TABLE_NAME);
    }

    private String queryWhere(String whereClause){
        String query = mQueryBuilder.buildQuery(
                mColumns,     // SELECT <List>
                whereClause,  // WHERE <String>
                mGroupBy,     // GROUP BY <String>
                null,         // HAVING <String>
                mOrderBy,     // ORDER BY <String>
                null          // LIMIT <String>
        );
        Log.d(LOG_TAG, "Built query: " + query);
        return query;
    }

    public String queryAll(){
        // passing null returns all rows
        return queryWhere(null);
    }

    private String factionWhereClause(int factionId){
        return UpgradeTable.COLUMN_NAME_FACTION_ID + "=" + String.valueOf(factionId) +
                "OR" + UpgradeTable.COLUMN_NAME_FACTION_ID + "is NULL";
    }

    private String typeWhereClause(String type){
        return UpgradeTable.COLUMN_NAME_TYPE_NAME + "='" + type + "'";
    }

    public String queryWhereFactionId(int factionId){
        return queryWhere(factionWhereClause(factionId));
    }

    public String queryWhereType(String type){
        return queryWhere(typeWhereClause(type));
    }

    public String queryForTypeAndFactionId(String type, int factionId){
        String whereClause = "(" + factionWhereClause(factionId) + ") AND " + typeWhereClause(type);
        return queryWhere(whereClause);
    }

}
