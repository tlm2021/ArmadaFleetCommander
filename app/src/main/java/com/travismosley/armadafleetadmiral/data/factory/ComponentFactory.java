package com.travismosley.armadafleetadmiral.data.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travismosley.android.data.database.PopulateFromCursorInterface;
import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.android.data.database.cursor.CursorFactory;
import com.travismosley.armadafleetadmiral.data.ArmadaDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for generating a list of GameComponents from a db query
 */
public class ComponentFactory<Component extends PopulateFromCursorInterface> {

    private static final String LOG_TAG = ComponentFactory.class.getSimpleName();

    protected ArmadaDatabaseHelper mArmadaDbHelper;

    public ComponentFactory(Context context){
        mArmadaDbHelper = new ArmadaDatabaseHelper(context);
        mArmadaDbHelper.setCursorFactory(new CursorFactory());
    }

    public List<Component> getForQuery(String query, Class<Component> componentClass){

        SQLiteDatabase db = mArmadaDbHelper.getDatabase();
        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<Component> componentList = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " components.");

        for (int i = 0; i < cursor.getCount(); i++) {
            Component component;
            try {
                component = componentClass.newInstance();
            }catch (Exception exc) {
                Log.e(LOG_TAG, "Unable to create new instance of " + componentClass, exc);
                continue;
            }
            cursor.moveToPosition(i);
            component.populate(cursor);
            componentList.add(component);
        }

        cursor.close();
        db.close();
        return componentList;
    }

}
