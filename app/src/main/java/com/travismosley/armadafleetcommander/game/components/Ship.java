package com.travismosley.armadafleetcommander.game.components;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple container class for Ship properties
 */
public class Ship implements Parcelable {
    public int mInstanceId;
    public int mShipId;
    public String mTitle;
    public boolean mUnique;
    public int mHull;
    public int mSpeed;
    public int mPointCost;

    public List<String> mUpgrades;

    protected Ship(Parcel in) {
        mInstanceId = in.readInt();
        mShipId = in.readInt();
        mTitle = in.readString();
        mUnique = in.readByte() != 0x00;
        mHull = in.readInt();
        mSpeed = in.readInt();
        mPointCost = in.readInt();
        if (in.readByte() == 0x01) {
            mUpgrades = new ArrayList<>();
            in.readList(mUpgrades, String.class.getClassLoader());
        } else {
            mUpgrades = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mInstanceId);
        dest.writeInt(mShipId);
        dest.writeString(mTitle);
        dest.writeByte((byte) (mUnique ? 0x01 : 0x00));
        dest.writeInt(mHull);
        dest.writeInt(mSpeed);
        dest.writeInt(mPointCost);
        if (mUpgrades == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mUpgrades);
        }
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