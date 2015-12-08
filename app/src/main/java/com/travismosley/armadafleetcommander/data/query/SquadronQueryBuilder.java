package com.travismosley.armadafleetcommander.data.query;

import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.SquadronContract.SquadronEntry;
import com.travismosley.armadafleetcommander.game.components.Squadron;

/**
 * A helper class around SQLiteQUeryBuilder to simplify buildling queries for
 * getting information about Squadrons.
 */
public class SquadronQueryBuilder {

    private final String LOG_TAG = SquadronQueryBuilder.class.getSimpleName();

    private SQLiteQueryBuilder mQueryBuilder;
    private String[] mColumns = {
            SquadronEntry.COLUMN_NAME_ID,
            SquadronEntry.COLUMN_NAME_TITLE,
            SquadronEntry.COLUMN_NAME_CLASS_TITLE,
            SquadronEntry.COLUMN_NAME_IS_UNIQUE,
            SquadronEntry.COLUMN_NAME_HULL,
            SquadronEntry.COLUMN_NAME_POINT_COST,
            SquadronEntry.COLUMN_NAME_SPEED,
            SquadronEntry.COLUMN_NAME_FACTION_ID,
    };

    private final static String mGroupBy = null;
    private final static String mOrderBy = SquadronEntry.COLUMN_NAME_CLASS_TITLE;

    public SquadronQueryBuilder(){
        mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(SquadronEntry.TABLE_NAME);
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
        return queryWhere(SquadronEntry.COLUMN_NAME_FACTION_ID + "=" + String.valueOf(factionId));
    }

}
