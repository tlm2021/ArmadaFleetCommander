package com.travismosley.armadafleetcommander.game.components;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipTable;

/**
 * Class encapsulating an Armada Ship
 */
public class Ship extends Vehicle {

    public Ship(){}

    @Override
    public String typeName(){
        return Ship.class.getSimpleName();
    }

    public void populate(Cursor cursor){

        // Base component attributes
        mId = cursor.getInt(ShipTable.COLUMN_NAME_ID);
        mTitle = cursor.getString(ShipTable.COLUMN_NAME_TITLE);
        mPointCost = cursor.getInt(ShipTable.COLUMN_NAME_POINT_COST);

        // Vehicle attributes
        mClass = cursor.getString(ShipTable.COLUMN_NAME_CLASS_TITLE);
        mHull = cursor.getInt(ShipTable.COLUMN_NAME_HULL);
        mMaxSpeed = cursor.getInt(ShipTable.COLUMN_NAME_SPEED);
    }

    // Parcel support
    protected Ship(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ship> CREATOR = new Parcelable.Creator<Ship>() {
        @Override
        public Ship createFromParcel(Parcel in) {
            return new Ship(in);
        }

        @Override
        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };
}