package com.travismosley.armadafleetcommander.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.armadafleetcommander.game.components.Ship;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple container class for Fleet properties
 */
public class Fleet implements Parcelable {

    public int mFactionId;
    public int mPointLimit;
    public List<Squadron> mSquadrons;
    public List<Ship> mShips;

    public Fleet(int factionId) {
        mFactionId = factionId;
        mPointLimit = 400;
        mSquadrons = new ArrayList<>();
        mShips = new ArrayList<>();
    }

    public void addSquadron(Squadron squadron){
        mSquadrons.add(squadron);
    }

    public boolean hasSquadron(int squadronId){
        for (int i = 0; i < mSquadrons.size(); i++){
            if (mSquadrons.get(i).mSquadronId == squadronId){
                return true;
            }
        }
        return false;
    }

    protected Fleet(Parcel in) {
        mPointLimit = in.readInt();
        if (in.readByte() == 0x01) {
            mSquadrons = new ArrayList<>();
            in.readList(mSquadrons, Squadron.class.getClassLoader());
        } else {
            mSquadrons = null;
        }
        if (in.readByte() == 0x01) {
            mShips = new ArrayList<>();
            in.readList(mShips, Ship.class.getClassLoader());
        } else {
            mShips = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPointLimit);
        if (mSquadrons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mSquadrons);
        }
        if (mShips == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mShips);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Fleet> CREATOR = new Parcelable.Creator<Fleet>() {
        @Override
        public Fleet createFromParcel(Parcel in) {
            return new Fleet(in);
        }

        @Override
        public Fleet[] newArray(int size) {
            return new Fleet[size];
        }
    };
}
