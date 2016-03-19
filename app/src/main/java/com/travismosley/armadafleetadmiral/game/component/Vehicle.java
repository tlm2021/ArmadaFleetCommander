package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.table.VehicleTableContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for shared attributes for all Vehicles
 */
public abstract class Vehicle extends FleetPointsGameComponent {

    private final static String LOG_TAG = Vehicle.class.getSimpleName();

    protected String mClass;
    protected int mClassId;
    protected int mHull;
    protected int mMaxSpeed;
    private List<DefenseToken> mDefenseTokens;

    public Vehicle(){}

    public void populate(Cursor cursor) {
        super.populate(cursor);

        // Vehicle attributes
        mClass = cursor.getString(VehicleTableContract.CLASS_TITLE);
        mClassId = cursor.getInt(VehicleTableContract.CLASS_ID);
        mHull = cursor.getInt(VehicleTableContract.HULL);
        mMaxSpeed = cursor.getInt(VehicleTableContract.SPEED);
    }

    public String vehicleClass(){
        return mClass;
    }

    public int vehicleClassId(){
        return mClassId;
    }

    public int hull(){
        return mHull;
    }

    public int maxSpeed(){
        return mMaxSpeed;
    }

    public List<DefenseToken> defenseTokens() {
        return mDefenseTokens;
    }

    public void setDefenseTokens(List<DefenseToken> tokens) {
        mDefenseTokens = tokens;
    }

    // Parcel support
    protected Vehicle(Parcel in) {
        super(in);
        mClass = in.readString();
        mClassId = in.readInt();
        mHull = in.readInt();
        mMaxSpeed = in.readInt();
        mDefenseTokens = new ArrayList<>();
        in.readTypedList(mDefenseTokens, DefenseToken.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mClass);
        dest.writeInt(mClassId);
        dest.writeInt(mHull);
        dest.writeInt(mMaxSpeed);
        dest.writeTypedList(mDefenseTokens);
    }

}
