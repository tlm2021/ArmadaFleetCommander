package com.travismosley.armadafleetadmiral.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract;

import junit.framework.Assert;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class FleetDatabaseHelperTest extends AndroidTestCase {

    private final String LOG_TAG = FleetDatabaseHelperTest.class.getSimpleName();
    private FleetDatabaseHelper mDbHelper;

    @Override
    protected void setUp() throws Exception{
        Log.i(LOG_TAG, "Running Setup");
        super.setUp();

        getContext().deleteDatabase(FleetDatabaseContract.DATABASE_NAME);
        mDbHelper = new FleetDatabaseHelper(getContext());
    }

    @Override
    protected void tearDown() throws Exception{
        Log.i(LOG_TAG, "Running tearDown");
        super.tearDown();

        mDbHelper.close();
    }

    public void test_createDatabaseTables(){
        Log.d(LOG_TAG, "in test_createDatabaseTables");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] expectedTables = new String[]{
                FleetDatabaseContract.FleetTable.TABLE_NAME,
                FleetDatabaseContract.ShipBuildTable.TABLE_NAME,
                FleetDatabaseContract.ShipBuildUpgradesTable.TABLE_NAME,
                FleetDatabaseContract.FleetShipBuildTable.TABLE_NAME,
                FleetDatabaseContract.FleetSquadronsTable.TABLE_NAME
        };

        for (int i=0; i < expectedTables.length; i++){
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + expectedTables[i] + "'", null);
            Assert.assertTrue(cursor.getCount() > 0);
            cursor.close();
        }

        mDbHelper.close();
    }

    public void test_createDatabaseViews(){
        Log.d(LOG_TAG, "in test_createDatabaseViews");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] expectedViews = new String[]{
                FleetDatabaseContract.FleetShipView.VIEW_NAME
        };

        for (int i=0; i < expectedViews.length; i++) {
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'view' AND name = '" + expectedViews[i] + "'", null);
            Assert.assertFalse(cursor.getCount() == 0);
            cursor.close();
        }
        mDbHelper.close();
    }

}
