package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.ArmadaDatabaseContract;

/**
 * Class for Objective cards
 */
public class Objective extends GameComponent{

    protected int mPointCost = 0;
    protected boolean mUnique = true;
    protected String mDescription;
    protected int mVictoryPoints;
    protected String mObjectiveTypeName;
    protected int mObjectiveTypeId;

    @Override
    public boolean isUnique(){
        return mUnique;
    }

    public String typeName(){
        return Objective.class.getSimpleName();
    }

    public int typeId(){
        return mObjectiveTypeId;
    }

    public void populate(Cursor cursor){

        // Upgrade attributes
        mId = cursor.getInt(ArmadaDatabaseContract.ObjectiveTable._ID);
        mTitle = cursor.getString(ArmadaDatabaseContract.ObjectiveTable.TITLE);
        mVictoryPoints = cursor.getInt(ArmadaDatabaseContract.ObjectiveTable.VICTORY_POINTS);
        mDescription = cursor.getString(ArmadaDatabaseContract.ObjectiveTable.DESCRIPTION);
        mObjectiveTypeId = cursor.getInt(ArmadaDatabaseContract.ObjectiveTable.TYPE_ID);
        mObjectiveTypeName = cursor.getString(ArmadaDatabaseContract.ObjectiveTable.TYPE_NAME);
    }

    // Parcel support
    protected Objective(Parcel in) {

        mId = in.readInt();
        mTitle = in.readString();
        mVictoryPoints = in.readInt();
        mDescription = in.readString();
        mObjectiveTypeId = in.readInt();
        mObjectiveTypeName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeInt(mVictoryPoints);
        dest.writeString(mDescription);
        dest.writeInt(mObjectiveTypeId);
        dest.writeString(mObjectiveTypeName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Objective> CREATOR = new Parcelable.Creator<Objective>() {
        @Override
        public Objective createFromParcel(Parcel in) {
            return new Objective(in);
        }

        @Override
        public Objective[] newArray(int size) {
            return new Objective[size];
        }
    };
}
