package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipTable;

/**
 * A helper class around SQLiteQUeryBuilder to simplify buildling queries for
 * getting information about Squadrons.
 */
public class ShipQueryBuilder {

    private final String LOG_TAG = ShipQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            ShipTable.COLUMN_NAME_ID,
            ShipTable.COLUMN_NAME_TITLE,
            ShipTable.COLUMN_NAME_CLASS_TITLE,
            ShipTable.COLUMN_NAME_HULL,
            ShipTable.COLUMN_NAME_POINT_COST,
            ShipTable.COLUMN_NAME_SPEED,
            ShipTable.COLUMN_NAME_FACTION_ID,
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = ShipTable.COLUMN_NAME_CLASS_TITLE + "," +
            ShipTable.COLUMN_NAME_TITLE;

    public ShipQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(ShipTable.TABLE_NAME);
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

    public String queryWhereFactionId(int factionId){
        return queryWhere(ShipTable.COLUMN_NAME_FACTION_ID + "=" + String.valueOf(factionId));
    }

}
