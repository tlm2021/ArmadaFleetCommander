package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.SquadronTable;

/**
 * A helper class around SQLiteQUeryBuilder to simplify building queries for
 * getting information about Squadrons.
 */
public class SquadronQueryBuilder {

    private final String LOG_TAG = SquadronQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            SquadronTable.COLUMN_NAME_ID,
            SquadronTable.COLUMN_NAME_TITLE,
            SquadronTable.COLUMN_NAME_CLASS_TITLE,
            SquadronTable.COLUMN_NAME_IS_UNIQUE,
            SquadronTable.COLUMN_NAME_HULL,
            SquadronTable.COLUMN_NAME_POINT_COST,
            SquadronTable.COLUMN_NAME_SPEED,
            SquadronTable.COLUMN_NAME_FACTION_ID,
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = SquadronTable.COLUMN_NAME_CLASS_TITLE + "," +
            SquadronTable.COLUMN_NAME_IS_UNIQUE + "," +
            SquadronTable.COLUMN_NAME_TITLE;

    public SquadronQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(SquadronTable.TABLE_NAME);
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
        return queryWhere(SquadronTable.COLUMN_NAME_FACTION_ID + "=" + String.valueOf(factionId));
    }

}
