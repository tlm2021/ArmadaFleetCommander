package com.travismosley.armadafleetcommander.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travismosley.armadafleetcommander.data.contract.SquadronContract.SquadronEntry;
import com.travismosley.armadafleetcommander.game.components.Squadron;
import com.travismosley.logging.database.ColumnIndexLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * A Facade class for extracting Armada information from the database as
 * internal classes. Helps out with encapsulation and type conversion;
 */

public class ArmadaDatabaseFacade{

    private static final String LOG_TAG = ArmadaDatabaseFacade.class.getSimpleName();
    private SQLiteDatabase mArmadaDb;
    private final ColumnIndexLogger mIdxLogger = new ColumnIndexLogger(LOG_TAG);

    public ArmadaDatabaseFacade(Context context) {

        ArmadaDatabaseHelper dbHelper = new ArmadaDatabaseHelper(context);
        mArmadaDb = dbHelper.getDatabase();
    }

    public List<Squadron> getSquadrons() {

        Cursor cursor = mArmadaDb.rawQuery("SELECT * FROM " + SquadronEntry.TABLE_NAME,
                null);
        List<Squadron> squadrons = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " squadrons.");

        Log.d(LOG_TAG, "Column Indices:");
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_ID, cursor);
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_TITLE, cursor);
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_CLASS_TITLE, cursor);
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_IS_UNIQUE, cursor);
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_HULL, cursor);
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_SPEED, cursor);
        mIdxLogger.logIndex(SquadronEntry.COLUMN_NAME_POINT_COST, cursor);

        for (int i = 0; i < cursor.getCount(); i++) {
            Squadron squad = new Squadron();
            cursor.moveToPosition(i);
            squad.mSquadronId = getInt(SquadronEntry.COLUMN_NAME_ID, cursor);
            squad.mTitle = getString(SquadronEntry.COLUMN_NAME_TITLE, cursor);
            squad.mClass = getString(SquadronEntry.COLUMN_NAME_CLASS_TITLE, cursor);
            squad.mUnique = getBoolean(SquadronEntry.COLUMN_NAME_IS_UNIQUE, cursor);
            squad.mHull = getInt(SquadronEntry.COLUMN_NAME_HULL, cursor);
            squad.mSpeed = getInt(SquadronEntry.COLUMN_NAME_SPEED, cursor);
            squad.mPointCost = getInt(SquadronEntry.COLUMN_NAME_POINT_COST, cursor);
            squadrons.add(squad);
        }

        cursor.close();
        return squadrons;
    }

    // Some helper methods for fetching values from the cursor

    private String getString(String columnTitle, Cursor cursor){
        return cursor.getString(cursor.getColumnIndex(columnTitle));
    }

    private int getInt(String columnTitle, Cursor cursor){
        return cursor.getInt(cursor.getColumnIndex(columnTitle));
    }

    private boolean getBoolean(String columnTitle, Cursor cursor){
        return getInt(columnTitle, cursor) == 0;
    }
}
