package com.travismosley.armadafleetadmiral.data.contract;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import junit.framework.Assert;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.runner.RunWith;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class FleetDatabaseContractTest extends AndroidTestCase{

    private static final String DATABASE_NAME = FleetDatabaseContractTest.class.getSimpleName() + "_DB";
    private class TestingDatabaseOpenHelper extends SQLiteOpenHelper{

        public TestingDatabaseOpenHelper(Context context){
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        }
    }

    private final String LOG_TAG = FleetDatabaseContractTest.class.getSimpleName();
    private SQLiteDatabase mDb;

    @Override
    protected void setUp() throws Exception{
        Log.i(LOG_TAG, "Running Setup");
        super.setUp();

        SQLiteOpenHelper dbHelper = new TestingDatabaseOpenHelper(getContext());
        mDb = dbHelper.getWritableDatabase();
    }

    @Override
    protected void tearDown() throws Exception{
        Log.d(LOG_TAG, "Running tearDown");
        super.tearDown();
        mDb.close();
        getContext().deleteDatabase(DATABASE_NAME);
    }

    private void runSql(String sql){
        Log.d(LOG_TAG, "Running SQL: " + sql);
        mDb.execSQL(sql);
    }

    private void checkExpectedColumns(String[] expectedColumns, Cursor cursor){
        Log.d(LOG_TAG, "Expected Columns: " + ToStringBuilder.reflectionToString(expectedColumns));
        Log.d(LOG_TAG, "Got Columns: " + ToStringBuilder.reflectionToString(cursor.getColumnNames()));

        Assert.assertEquals(cursor.getColumnCount(), expectedColumns.length);
        for (String colName: expectedColumns){
            Assert.assertTrue(cursor.getColumnIndex(colName) != -1);
        }
    }

    public void test_contract_createFleetTable(){
        Log.d(LOG_TAG, "in test_contract_createFleetTable");
        runSql(FleetDatabaseContract.FleetTable.SQL_CREATE_TABLE);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + FleetDatabaseContract.FleetTable.TABLE_NAME, null);
        String[] expectedColumns = new String[]{
                FleetDatabaseContract.FleetTable._ID,
                FleetDatabaseContract.FleetTable.NAME,
                FleetDatabaseContract.FleetTable.FACTION_ID,
                FleetDatabaseContract.FleetTable.COMMANDER_ID,
                FleetDatabaseContract.FleetTable.FLEET_POINT_LIMIT,
                FleetDatabaseContract.FleetTable.FLEET_POINT_TOTAL
        };

        checkExpectedColumns(expectedColumns, cursor);
        cursor.close();
    }

    public void test_contract_createShipBuildTable(){
        Log.d(LOG_TAG, "in test_contract_createFleetShipBuildTable");
        runSql(FleetDatabaseContract.ShipBuildTable.SQL_CREATE_TABLE);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + FleetDatabaseContract.ShipBuildTable.TABLE_NAME, null);
        String[] expectedColumns = new String[]{
                FleetDatabaseContract.ShipBuildTable._ID,
                FleetDatabaseContract.ShipBuildTable.SHIP_ID,
                FleetDatabaseContract.ShipBuildTable.CUSTOM_TITLE,
                FleetDatabaseContract.ShipBuildTable.TITLE_UPGRADE_ID
        };

        checkExpectedColumns(expectedColumns, cursor);
        cursor.close();
    }

    public void test_contract_createFleetShipBuildTable(){
        Log.d(LOG_TAG, "in test_contract_createFleetShipBuildTable");
        runSql(FleetDatabaseContract.FleetTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.ShipBuildTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.FleetShipBuildTable.SQL_CREATE_TABLE);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + FleetDatabaseContract.FleetShipBuildTable.TABLE_NAME, null);
        String[] expectedColumns = new String[]{
                FleetDatabaseContract.FleetShipBuildTable._ID,
                FleetDatabaseContract.FleetShipBuildTable.FLEET_ID,
                FleetDatabaseContract.FleetShipBuildTable.SHIP_BUILD_ID,
                FleetDatabaseContract.FleetShipBuildTable.FLAGSHIP
        };

        checkExpectedColumns(expectedColumns, cursor);
        cursor.close();
    }

    public void test_contract_createFleetSquadronsTable(){
        Log.d(LOG_TAG, "in test_contract_createFleetSquadronsTable");
        runSql(FleetDatabaseContract.FleetTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.FleetSquadronsTable.SQL_CREATE_TABLE);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + FleetDatabaseContract.FleetSquadronsTable.TABLE_NAME, null);
        String[] expectedColumns = new String[]{
                FleetDatabaseContract.FleetSquadronsTable._ID,
                FleetDatabaseContract.FleetSquadronsTable.FLEET_ID,
                FleetDatabaseContract.FleetSquadronsTable.SQUADRON_ID,
                FleetDatabaseContract.FleetSquadronsTable.COUNT
        };

        checkExpectedColumns(expectedColumns, cursor);
        cursor.close();
    }

    public void test_contract_createShipBuildUpgradesTable(){
        Log.d(LOG_TAG, "in test_contract_createShipBuildUpgradesTable");

        runSql(FleetDatabaseContract.ShipBuildTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.ShipBuildUpgradesTable.SQL_CREATE_TABLE);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + FleetDatabaseContract.ShipBuildUpgradesTable.TABLE_NAME, null);
        String[] expectedColumns = new String[]{
                FleetDatabaseContract.ShipBuildUpgradesTable._ID,
                FleetDatabaseContract.ShipBuildUpgradesTable.SHIP_BUILD_ID,
                FleetDatabaseContract.ShipBuildUpgradesTable.UPGRADE_ID
        };

        checkExpectedColumns(expectedColumns, cursor);
        cursor.close();
    }

    public void test_contract_createFleetShipView(){
        Log.d(LOG_TAG, "in test_contract_createFleetShipView");

        runSql(FleetDatabaseContract.FleetTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.ShipBuildTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.FleetShipBuildTable.SQL_CREATE_TABLE);
        runSql(FleetDatabaseContract.FleetShipView.SQL_CREATE_VIEW);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + FleetDatabaseContract.FleetShipView.VIEW_NAME, null);
        String[] expectedColumns = new String[]{
                FleetDatabaseContract.FleetShipView.FLEET_ID,
                FleetDatabaseContract.FleetShipView.SHIP_BUILD_ID,
                FleetDatabaseContract.FleetShipView.SHIP_ID,
                FleetDatabaseContract.FleetShipView.CUSTOM_TITLE,
                FleetDatabaseContract.FleetShipView.TITLE_UPGRADE_ID
        };

        checkExpectedColumns(expectedColumns, cursor);
        cursor.close();
    }

    public void test_contract_onCreateCommands(){

        String[] expectedCommands = new String[] {
                FleetDatabaseContract.FleetTable.SQL_CREATE_TABLE,
                FleetDatabaseContract.ShipBuildTable.SQL_CREATE_TABLE,
                FleetDatabaseContract.ShipBuildUpgradesTable.SQL_CREATE_TABLE,
                FleetDatabaseContract.FleetShipBuildTable.SQL_CREATE_TABLE,
                FleetDatabaseContract.FleetSquadronsTable.SQL_CREATE_TABLE,
                FleetDatabaseContract.FleetShipView.SQL_CREATE_VIEW
        };

        ArrayList<String> onCreateCommands = FleetDatabaseContract.getOnCreateSqlCommands();

        Assert.assertEquals(expectedCommands.length, onCreateCommands.size());

        for (int i=0; i < expectedCommands.length; i++){
            Assert.assertEquals(expectedCommands[i], onCreateCommands.get(i));
        }
    }

}
