package com.travismosley.armadafleetcommander.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.armadafleetcommander.game.components.Ship;
import com.travismosley.armadafleetcommander.game.components.Squadron;

import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating a fleet, it's contents, and other properties
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

    public int squadronPointLimit(){
        // You can only spend 1/3 the max points on squadrons.
        return Math.round(mPointLimit / 3f);
    }

    public int squadronPoints(){
        int total = 0;
        for (int i=0; i < mSquadrons.size(); i++){
            total += mSquadrons.get(i).mPointCost;
        }
        return total;
    }

    public void addSquadron(Squadron squadron){
        mSquadrons.add(squadron);
    }

    public boolean hasSquadron(Squadron squadron){
        // Check if this fleet already has a squadron with the same id
        for (int i = 0; i < mSquadrons.size(); i++){
            if (mSquadrons.get(i).mSquadronId == squadron.mSquadronId){
                return true;
            }
        }
        return false;
    }

    public boolean canAddSquadron(Squadron squadron){

        // Can't have two instances of the same squadron
        if (squadron.isUnique() && this.hasSquadron(squadron)){
            return false;
        }

        // Check if the squadron fits under the point limit
        if (this.squadronPoints() + squadron.mPointCost > this.squadronPointLimit()){
            return false;
        }

        return true;
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
