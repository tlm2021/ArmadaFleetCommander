package com.travismosley.android.data.database;

import com.travismosley.android.data.database.cursor.Cursor;

/**
 * Interface for objects that can be populated from a Cursor object
 */
public interface PopulateFromCursorInterface {
    void populate(Cursor cursor);
}
