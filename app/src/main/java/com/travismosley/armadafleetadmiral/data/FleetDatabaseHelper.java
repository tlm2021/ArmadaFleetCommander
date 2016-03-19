package com.travismosley.armadafleetadmiral.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.travismosley.android.data.database.cursor.CursorFactory;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract;

import java.util.ArrayList;

/**
 * Class for creating the Fleet Database
 */
public class FleetDatabaseHelper extends SQLiteOpenHelper{

    public FleetDatabaseHelper(Context context){
        super(context, FleetDatabaseContract.DATABASE_NAME, new CursorFactory(), FleetDatabaseContract.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        ArrayList<String> onCreateCommands = FleetDatabaseContract.getOnCreateSqlCommands();
        for (int i = 0; i < onCreateCommands.size(); i++){
            db.execSQL(onCreateCommands.get(i));
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
}
