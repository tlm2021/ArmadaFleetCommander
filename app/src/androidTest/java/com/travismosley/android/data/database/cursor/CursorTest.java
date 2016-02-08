package com.travismosley.android.data.database.cursor;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.travismosley.android.data.database.TestingDatabaseContract;
import com.travismosley.android.data.database.TestingDatabaseHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.String;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class CursorTest extends AndroidTestCase {

    private final String LOG_TAG = CursorTest.class.getSimpleName();

    private Cursor mCursor;
    private String mQueryAll = "SELECT * FROM " + TestingDatabaseContract.TestTable.TABLE_NAME;

    @Override
    protected void setUp() throws Exception{
        Log.i(LOG_TAG, "Running Setup");
        super.setUp();

        TestingDatabaseHelper dbHelper = new TestingDatabaseHelper(getContext());
        mCursor = (Cursor) dbHelper.getDatabase().rawQuery(mQueryAll, new String[] {});
    }

    @Override
    protected void tearDown() throws Exception{
        mCursor = null;
    }

    @Test
    public void test_cursor_getString_typeAndDataCorrect(){
        // Check that strings are properly fetched and converted
        Log.i(LOG_TAG, "Running cursor_getString_typeAndDataCorrect");

        mCursor.moveToPosition(0);
        Assert.assertEquals(TestingDatabaseContract.TestTable.ROW_ONE_COL_STRING_VALUE, mCursor.getString(TestingDatabaseContract.TestTable.COL_STRING));

        mCursor.moveToPosition(1);
        Assert.assertEquals(TestingDatabaseContract.TestTable.ROW_TWO_COL_STRING_VALUE, mCursor.getString(TestingDatabaseContract.TestTable.COL_STRING));
    }

    @Test
    public void test_cursor_getInt_typeAndDataCorrect(){
        // Check that ints are properly fetched and converted
        Log.i(LOG_TAG, "Running cursor_getInt_typeAndDataCorrect");

        for (int i = 0; i<mCursor.getColumnCount(); i++){
            Log.i(LOG_TAG, mCursor.getColumnName(i));
        }

        mCursor.moveToPosition(0);
        Assert.assertTrue(TestingDatabaseContract.TestTable.ROW_ONE_COL_INT_VALUE == mCursor.getInt(TestingDatabaseContract.TestTable.COL_INT));

        mCursor.moveToPosition(1);
        Assert.assertTrue(TestingDatabaseContract.TestTable.ROW_TWO_COL_INT_VALUE == mCursor.getInt(TestingDatabaseContract.TestTable.COL_INT));
    }

    @Test
    public void test_cursor_getBool_typeAndDataCorrect(){
        // Check that booleans are properly fetched and converted

        Log.i(LOG_TAG, "Running cursor_getBool_typeAndDataCorrect");

        mCursor.moveToPosition(0);
        Assert.assertEquals(TestingDatabaseContract.TestTable.ROW_ONE_COL_BOOL_VALUE, mCursor.getBoolean(TestingDatabaseContract.TestTable.COL_BOOL));

        mCursor.moveToPosition(1);
        Assert.assertEquals(TestingDatabaseContract.TestTable.ROW_TWO_COL_BOOL_VALUE, mCursor.getBoolean(TestingDatabaseContract.TestTable.COL_BOOL));
    }
}
