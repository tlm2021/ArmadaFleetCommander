package com.travismosley.armadafleetadmiral.data.factory;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travismosley.android.data.database.PopulateFromCursorInterface;
import com.travismosley.android.data.database.cursor.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for generating a list of GameComponents from a db query
 */
public class ComponentFactory<Component extends PopulateFromCursorInterface> {

    private static final String LOG_TAG = ComponentFactory.class.getSimpleName();

    public ComponentFactory(){}

    public List<Component> getForQuery(String query, SQLiteDatabase db, Class<Component> componentClass){

        Cursor cursor = (Cursor) db.rawQuery(query, null);
        List<Component> componentList = new ArrayList<>();

        Log.d(LOG_TAG, "Found " + cursor.getCount() + " components.");
        try{
            for (int i = 0; i < cursor.getCount(); i++) {
                Component component;
                try {
                    component = componentClass.newInstance();
                } catch (Exception exc) {
                    Log.e(LOG_TAG, "Unable to create new instance of " + componentClass, exc);
                    continue;
                }
                cursor.moveToPosition(i);
                component.populate(cursor);
                componentList.add(component);
            }
        } catch (Exception exc) {
            Log.e(LOG_TAG, "Error creating component.", exc);
        } finally {
            cursor.close();
        }

        return componentList;
    }

}
