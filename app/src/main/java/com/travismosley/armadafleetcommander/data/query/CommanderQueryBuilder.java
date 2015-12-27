package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.CommanderTable;

/**
 * A helper class around SQLiteQueryBuilder to simplify building queries for
 * getting information about Squadrons.
 */

public class CommanderQueryBuilder {

    private final String LOG_TAG = CommanderQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            CommanderTable.COLUMN_NAME_ID,
            CommanderTable.COLUMN_NAME_TITLE,
            CommanderTable.COLUMN_NAME_TEXT,
            CommanderTable.COLUMN_NAME_IS_UNIQUE,
            CommanderTable.COLUMN_NAME_POINT_COST,
            CommanderTable.COLUMN_NAME_FACTION_ID
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = CommanderTable.COLUMN_NAME_POINT_COST + "," +
            CommanderTable.COLUMN_NAME_TITLE;

    public CommanderQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(CommanderTable.TABLE_NAME);
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

    private String factionIdWhereClause(int factionId){
        return CommanderTable.COLUMN_NAME_FACTION_ID + "=" + String.valueOf(factionId);
    }

    public String queryWhereFactionId(int factionId){
        return queryWhere(factionIdWhereClause(factionId));
    }

}
