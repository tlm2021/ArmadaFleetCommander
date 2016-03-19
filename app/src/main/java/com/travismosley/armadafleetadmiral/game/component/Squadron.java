package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.SquadronTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating an Armada Squadron
 */
public class Squadron extends Vehicle {

    private final static String LOG_TAG = Squadron.class.getSimpleName();

    protected boolean mUnique;
    protected List<SquadronKeyword> mKeywords;

    public Squadron(){}

    @Override
    public boolean isUnique(){
        return mUnique;
    }

    @Override
    public String typeName(){
        return Squadron.class.getSimpleName();
    }

    public void populate(Cursor cursor){
        super.populate(cursor);

        // Squadron attribute
        mUnique = cursor.getBoolean(SquadronTable.IS_UNIQUE);
    }

    public void setKeywords(List<SquadronKeyword> keywords) {
        mKeywords = keywords;
    }

    public List<SquadronKeyword> keywords() {
        return mKeywords;
    }

    // Parcel support
    protected Squadron(Parcel in) {
        super(in);
        mUnique = in.readByte() != 0x00;
        mKeywords = new ArrayList<>();
        in.readTypedList(mKeywords, SquadronKeyword.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (mUnique ? 0x01 : 0x00));
        dest.writeTypedList(mKeywords);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Squadron> CREATOR = new Parcelable.Creator<Squadron>() {
        @Override
        public Squadron createFromParcel(Parcel in) {
            return new Squadron(in);
        }

        @Override
        public Squadron[] newArray(int size) {
            return new Squadron[size];
        }
    };
}