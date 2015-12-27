package com.travismosley.armadafleetcommander.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;

/**
 * Base class for all objects that represent GameComponents
 */
public abstract class GameComponent implements Parcelable {

    protected int mId; // An app-internal, unique ID
    protected String mTitle;
    protected int mPointCost;

    public GameComponent(){}

    public abstract String typeName();
    public abstract void populate(Cursor cursor);

    public int id(){
        return mId;
    }

    public String title(){
        return mTitle;
    }

    public int pointCost(){
        return mPointCost;
    }

    public boolean isUnique(){
        return true;
    }

    public String toString(){
        return typeName() + "<" + title() + ", " + pointCost() + ">";
    }

    // Parcel support
    protected GameComponent(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mPointCost = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeInt(mPointCost);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
