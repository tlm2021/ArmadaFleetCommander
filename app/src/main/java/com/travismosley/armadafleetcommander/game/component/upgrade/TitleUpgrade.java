package com.travismosley.armadafleetcommander.game.component.upgrade;

import android.os.Parcel;

import com.travismosley.android.data.database.cursor.Cursor;

/**
 * Upgrade class specific for Titles
 */
public class TitleUpgrade extends Upgrade {

    private final static String LOG_TAG = TitleUpgrade.class.getSimpleName();

    public TitleUpgrade(){}

    public void populate(Cursor cursor){
        super.populate(cursor);
    }

    // Parcel support
    protected TitleUpgrade(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @SuppressWarnings("unused")
    public static final Creator<TitleUpgrade> CREATOR = new Creator<TitleUpgrade>() {
        @Override
        public TitleUpgrade createFromParcel(Parcel in) {
            return new TitleUpgrade(in);
        }

        @Override
        public TitleUpgrade[] newArray(int size) {
            return new TitleUpgrade[size];
        }
    };
}
