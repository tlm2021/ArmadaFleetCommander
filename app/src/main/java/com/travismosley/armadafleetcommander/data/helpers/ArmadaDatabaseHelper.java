package com.travismosley.armadafleetcommander.data.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Travis on 1/12/2015.
 */
public class ArmadaDatabaseHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = ArmadaDatabaseHelper.class.getSimpleName();

    private static String DB_PATH = "/data/data/com.travismosley.armadafleetcommander/databases/";
    private static String DB_NAME = "armada_fleet_commander.db";
    private SQLiteDatabase dbArmada;
    private final Context myContext;

    public ArmadaDatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        File file = new File(DB_PATH + DB_NAME);
        if(file.exists())
            file.delete();

        if (checkDataBase()) {
            Log.i(LOG_TAG, "Found and loaded " + DB_PATH + DB_NAME + " successfully.");// do nothing - database exists
        } else {
            // By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            // database doesn't exist yet
            Log.i(LOG_TAG, "Couldn't find " + DB_PATH + DB_NAME + ". Will copy from APK.");
        }

        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        Log.i(LOG_TAG, "Copying " + DB_NAME + " from APK to " + DB_PATH + DB_NAME);

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

    public void openDataBase() throws SQLException{
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        dbArmada = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        Log.i(LOG_TAG, "Successfully opened " + DB_PATH + DB_NAME);
    }

    @Override
    public synchronized void close(){
        if(dbArmada != null)
            dbArmada.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public Cursor fetchSquadronTitles(){
        return dbArmada.rawQuery("SELECT * FROM squadron_view", new String[]{});
    }
}

