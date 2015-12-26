package com.travismosley.armadafleetcommander.game.components;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.SquadronTable;

/**
 * Class encapsulating an Armada Squadron
 */
public class Squadron extends Vehicle {

    protected boolean mUnique;

    public Squadron(){}

    @Override
    public String typeName(){
        return Squadron.class.getSimpleName();
    }

    @Override
    public boolean isUnique(){
        return mUnique;
    }

    public void populate(Cursor cursor){

        // Base component attributes
        mId = cursor.getInt(SquadronTable.COLUMN_NAME_ID);
        mTitle = cursor.getString(SquadronTable.COLUMN_NAME_TITLE);
        mPointCost = cursor.getInt(SquadronTable.COLUMN_NAME_POINT_COST);

        // Vehicle attributes
        mClass = cursor.getString(SquadronTable.COLUMN_NAME_CLASS_TITLE);
        mHull = cursor.getInt(SquadronTable.COLUMN_NAME_HULL);
        mMaxSpeed = cursor.getInt(SquadronTable.COLUMN_NAME_SPEED);

        // Squadron attribute
        mUnique = cursor.getBoolean(SquadronTable.COLUMN_NAME_IS_UNIQUE);
    }

    // Parcel support
    protected Squadron(Parcel in) {
        super(in);
        mUnique = in.readByte() != 0x00;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (mUnique ? 0x01 : 0x00));
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
