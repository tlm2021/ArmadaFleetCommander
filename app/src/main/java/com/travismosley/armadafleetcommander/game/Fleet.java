package com.travismosley.armadafleetcommander.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.travismosley.armadafleetcommander.game.component.GameComponent;
import com.travismosley.armadafleetcommander.game.component.Ship;
import com.travismosley.armadafleetcommander.game.component.Squadron;
import com.travismosley.armadafleetcommander.game.component.upgrade.Commander;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating a fleet, it's contents, and other properties
 */

public class Fleet implements Parcelable {

    String LOG_TAG = Fleet.class.getSimpleName();

    public int mFactionId;
    private int mPointLimit;
    public List<Squadron> mSquadrons;
    public List<Ship> mShips;
    private Commander mCommander;


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

    public int commanderPoints(){
        if (mCommander == null){
            return 0;
        }
        return mCommander.pointCost();
    }

    public int fleetPoints(){

        // Get the total fleet points spent
        return squadronPoints() + shipPoints() + commanderPoints();
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

    public boolean hasComponent(Squadron squadron){

        // Check if this fleet already has a squadron with the same id
        for (int i = 0; i < mSquadrons.size(); i++){
            if (mSquadrons.get(i).id() == squadron.id()){
                return true;
            }
        }
        return false;
    }

    public boolean canAddComponent(Squadron squadron){

        // Can't have two instances of a unique squadron
        if (squadron.isUnique() && hasComponent(squadron)){
            return false;
        }

        // Check if the squadron fits under the point limit
        else if (squadron.pointCost() > remainingSquadronPoints()){
            return false;
        }

        return true;
    }

    public boolean canAddComponent(Upgrade upgrade){

        // Check if the upgrade fits under the point limit
        if (upgrade.pointCost() > this.remainingSquadronPoints()){
            return false;
        }

        if (upgrade.isUnique()){
            for (int i=0; i<mShips.size(); i++){
                if (mShips.get(i).hasUpgrade(upgrade)){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canAddComponent(GameComponent component){
        Log.w(LOG_TAG, "Point-only check for component" + component);
        Log.w(LOG_TAG, "Need canAddComponent method for more specific check.");

        // Check if the squadron fits under the point limit
        return component.pointCost() <= this.remainingFleetPoints();

    }

    public void clearCommander(){
        mCommander = null;
    }

    public void setCommander(Commander commander){
        mCommander = commander;
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
