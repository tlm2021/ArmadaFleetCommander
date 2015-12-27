package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipTitleTable;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */

public class ShipTitleQueryBuilder {

    private final String LOG_TAG = ShipTitleQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            ShipTitleTable.COLUMN_NAME_ID,
            ShipTitleTable.COLUMN_NAME_TITLE,
            ShipTitleTable.COLUMN_NAME_TEXT,
            ShipTitleTable.COLUMN_NAME_IS_UNIQUE,
            ShipTitleTable.COLUMN_NAME_POINT_COST,
            ShipTitleTable.COLUMN_NAME_CLASS_ID
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = ShipTitleTable.COLUMN_NAME_POINT_COST + "," +
            ShipTitleTable.COLUMN_NAME_TITLE;

    public ShipTitleQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(ShipTitleTable.TABLE_NAME);
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

    private String shipClassWhereClause(int shipClassId){
        return ShipTitleTable.COLUMN_NAME_CLASS_ID + "=" + String.valueOf(shipClassId);
    }

    public String queryWhereShipClassId(int shipClassId){
        return queryWhere(shipClassWhereClause(shipClassId));
    }

}
