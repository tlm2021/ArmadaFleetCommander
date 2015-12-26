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
    private int mPointLimit;
    public List<Squadron> mSquadrons;
    public List<Ship> mShips;

    public Fleet(int factionId) {
        mFactionId = factionId;
        mPointLimit = 400;
        mSquadrons = new ArrayList<>();
        mShips = new ArrayList<>();
    }

    public int fleetPointLimit(){
        return mPointLimit;
    }

    public int squadronPointLimit(){
        // You can only spend 1/3 the max points on squadrons.
        return Math.round(mPointLimit / 3f);
    }

    public int squadronPoints(){

        // Get the total points spent on squadrons
        int total = 0;
        for (int i=0; i < mSquadrons.size(); i++){
            total += mSquadrons.get(i).pointCost();
        }
        return total;
    }

    public int shipPoints(){

        // Get the total points spent on ships
        int total = 0;
        for (int i=0; i<mShips.size(); i++){
            total += mShips.get(i).pointCost();
        }
        return total;
    }

    public int fleetPoints(){

        // Get the total fleet points spent
        return squadronPoints() + shipPoints();
    }

    public int remainingFleetPoints(){
        return fleetPointLimit() - fleetPoints();
    }

    public int remainingSquadronPoints(){
        return Math.min(remainingFleetPoints(), squadronPointLimit() - squadronPoints());
    }

    public void addSquadron(Squadron squadron){
        mSquadrons.add(squadron);
    }

    public boolean hasSquadron(Squadron squadron){
        // Check if this fleet already has a squadron with the same id
        for (int i = 0; i < mSquadrons.size(); i++){
            if (mSquadrons.get(i).id() == squadron.id()){
                return true;
            }
        }
        return false;
    }

    public boolean canAddSquadron(Squadron squadron){

        // Can't have two instances of a unique squadron
        if (squadron.isUnique() && this.hasSquadron(squadron)){
            return false;
        }

        // Check if the squadron fits under the point limit
        else if (squadron.pointCost() > remainingSquadronPoints()){
            return false;
        }

        return true;
    }

    public boolean canAddShip(Ship ship){

        // Check if the squadron fits under the point limit
        return ship.pointCost() <= this.remainingFleetPoints();
    }

    /* Parcel support */

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
