package com.travismosley.armadafleetcommander.game.components;

import android.os.Parcel;

/**
 * Base class for shared attributes for all Vehicles
 */
public abstract class Vehicle extends GameComponent {

    protected String mClass;
    protected int mHull;
    protected int mMaxSpeed;

    public Vehicle(){}

    public String vehicleClass(){
        return mClass;
    }

    public int hull(){
        return mHull;
    }

    public int maxSpeed(){
        return mMaxSpeed;
    }

    // Parcel support
    protected Vehicle(Parcel in) {
        super(in);
        mClass = in.readString();
        mHull = in.readInt();
        mMaxSpeed = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mClass);
        dest.writeInt(mHull);
        dest.writeInt(mMaxSpeed);
    }

}
