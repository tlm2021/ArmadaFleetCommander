package com.travismosley.armadafleetadmiral.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.travismosley.android.data.database.PopulateFromCursorInterface;
import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract;
import com.travismosley.armadafleetadmiral.game.component.GameComponent;
import com.travismosley.armadafleetadmiral.game.component.Objective;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Commander;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class encapsulating a fleet, it's contents, and other properties
 */

public class Fleet implements Parcelable, PopulateFromCursorInterface {

    String LOG_TAG = Fleet.class.getSimpleName();

    private String mFleetName;
    public int mFactionId;
    private int mPointLimit;
    public List<Squadron> mSquadrons;
    public List<Ship> mShips;
    private Commander mCommander;
    private Objective mAssaultObjective;
    private Objective mDefenseObjective;
    private Objective mNavigationObjective;

    public Fleet(){
        mPointLimit = 400;
        mSquadrons = new ArrayList<>();
        mShips = new ArrayList<>();
    }

    public Fleet(int factionId) {
        super();
        mFactionId = factionId;
    }

    public void populate(Cursor cursor){
        mFactionId = cursor.getInt(FleetDatabaseContract.FleetTable.FACTION_ID);
        mPointLimit = cursor.getInt(FleetDatabaseContract.FleetTable.FLEET_POINT_LIMIT);
        mFleetName = cursor.getString(FleetDatabaseContract.FleetTable.NAME);
    }

    public String name(){
        return mFleetName;
    }

    public void setName(String name){
        mFleetName = name;
    }

    public int fleetPointLimit(){
        return mPointLimit;
    }

    public int fleetPoints(){

        // Get the total fleet points spent
        return squadronPoints() + shipPoints() + commanderPoints();
    }

    public void setPointLimit(int points){
        mPointLimit = points;
    }

    public int remainingFleetPoints(){
        return fleetPointLimit() - fleetPoints();
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

    public int remainingSquadronPoints(){
        return Math.min(remainingFleetPoints(), squadronPointLimit() - squadronPoints());
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

    public void addSquadron(Squadron squadron){
        mSquadrons.add(squadron);
    }

    public Map<Integer, Integer> squadronCounts(){

        Map<Integer, Integer> counts = new HashMap<>();

        for (int i=0; i<mSquadrons.size(); i++){
            Squadron squad = mSquadrons.get(i);
            int count = counts.containsKey(squad.id()) ? counts.get(squad.id()) : 0;
            counts.put(squad.id(), count + 1);
        }
        return counts;
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

    public Commander commander(){
        return mCommander;
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
