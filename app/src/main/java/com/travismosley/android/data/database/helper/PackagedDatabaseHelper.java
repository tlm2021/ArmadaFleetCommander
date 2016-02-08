package com.travismosley.android.data.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.travismosley.android.data.database.cursor.CursorFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * A class for initialing, opening, and fetching a database that is packaged
 * with the APK.
 */

public abstract class PackagedDatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PackagedDatabaseHelper.class.getSimpleName();

    private final Context mContext;
    private String mDbPath;
    private SQLiteDatabase mDb;
    private SQLiteDatabase.CursorFactory mCursorFactory;

    public PackagedDatabaseHelper(Context context, String dbName){
        super(context, dbName, null, 1);
        mContext = context;
        mDbPath = getDatabasePath();
        mCursorFactory = new CursorFactory();
    }

    private String getDatabasePath(){

        // Builds and returns the path to the db in the data dir
        return mContext.getDatabasePath(getDatabaseName()).getPath();
    }

    public void createDatabase() throws IOException {

        File file = new File(mDbPath);
        if(file.exists()) {
            file.delete();
        }

        if (checkDatabase()) {
            // Do nothing - database exists
            Log.i(LOG_TAG, "Found and loaded " + mDbPath + " successfully.");
        } else {
            // By calling this method an empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error copying database.", e);
                throw new Error("Unable to create the database.");
            }
        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = mDbPath;
            checkDB = SQLiteDatabase.openDatabase(myPath, mCursorFactory, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e){
            // database doesn't exist yet
            Log.i(LOG_TAG, "Couldn't find " + mDbPath + ". Will copy from APK.");
        }

        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDatabase() throws IOException{
        // Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(getDatabaseName());

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(mDbPath);

        Log.i(LOG_TAG, "Copying " + getDatabaseName() + " from APK to " + mDbPath);

        // transfer bytes from inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0){
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLException {
        // Open the database
        mDb = SQLiteDatabase.openDatabase(mDbPath, mCursorFactory, SQLiteDatabase.OPEN_READONLY);
        Log.i(LOG_TAG, "Successfully opened " + mDbPath);
    }

    public SQLiteDatabase getDatabase(){
        if (mDb != null) {
            return mDb;
        }

        try {
            createDatabase();
        } catch (IOException e) {
            throw new Error("Error creating the database;");
        }
        try {
            openDatabase();
        } catch (SQLException e) {
            throw new Error("Error loading the database!");
        }
        return mDb;
    }

    @Override
    public synchronized void close(){
        if(mDb != null)
            mDb.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

}
