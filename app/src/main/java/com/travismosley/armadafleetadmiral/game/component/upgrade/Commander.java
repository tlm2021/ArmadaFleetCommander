package com.travismosley.armadafleetadmiral.game.component.upgrade;

import android.os.Parcel;

import com.travismosley.android.data.database.cursor.Cursor;

/**
 * Upgrade class specific for Titles
 */
public class Commander extends Upgrade {

    private final static String LOG_TAG = Commander.class.getSimpleName();

    public Commander(){}

    public void populate(Cursor cursor){
        super.populate(cursor);
    }

    // Parcel support
    protected Commander(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @SuppressWarnings("unused")
    public static final Creator<Commander> CREATOR = new Creator<Commander>() {
        @Override
        public Commander createFromParcel(Parcel in) {
            return new Commander(in);
        }

        @Override
        public Commander[] newArray(int size) {
            return new Commander[size];
        }
    };
}
