package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.PopulateFromCursorInterface;
import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.table.ComponentTableContract;

/**
 * Base class for all objects that represent GameComponents
 */
public abstract class GameComponent implements Parcelable, PopulateFromCursorInterface {

    private static final String LOG_TAG = GameComponent.class.getSimpleName();

    protected int mId;
    protected String mTitle;

    public GameComponent(){}

    public abstract String typeName();

    public int id(){
        return mId;
    }

    public String title(){
        return mTitle;
    }

    public boolean isUnique(){
        return false;
    }

    public String toString(){
        return typeName() + "<" + title() + ">";
    }

    // Parcel support
    protected GameComponent(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
    }

    public void populate(Cursor cursor){
        mId = cursor.getInt(ComponentTableContract._ID);
        mTitle = cursor.getString(ComponentTableContract.TITLE);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
