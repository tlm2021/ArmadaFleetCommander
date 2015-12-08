package com.travismosley.armadafleetcommander.game.components;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class encapsulating an Armada Squadron
 */
public class Squadron implements Parcelable {

    public int mSquadronId;
    public String mTitle;
    public String mClass;
    public boolean mUnique;
    public int mHull;
    public int mSpeed;
    public int mPointCost;

    public Squadron(){}

    public boolean isUnique(){
        return mUnique;
    }

    protected Squadron(Parcel in) {
        mSquadronId = in.readInt();
        mTitle = in.readString();
        mClass = in.readString();
        mUnique = in.readByte() != 0x00;
        mHull = in.readInt();
        mSpeed = in.readInt();
        mPointCost = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mSquadronId);
        dest.writeString(mTitle);
        dest.writeString(mClass);
        dest.writeByte((byte) (mUnique ? 0x01 : 0x00));
        dest.writeInt(mHull);
        dest.writeInt(mSpeed);
        dest.writeInt(mPointCost);
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
