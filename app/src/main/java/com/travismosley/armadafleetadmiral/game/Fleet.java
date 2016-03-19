package com.travismosley.armadafleetadmiral.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.travismosley.android.data.database.PopulateFromCursorInterface;
import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.FleetDatabaseContract;
import com.travismosley.armadafleetadmiral.game.component.FleetPointsGameComponent;
import com.travismosley.armadafleetadmiral.game.component.Objective;
import com.travismosley.armadafleetadmiral.game.component.Ship;
import com.travismosley.armadafleetadmiral.game.component.Squadron;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Commander;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class encapsulating a fleet, it's contents, and other properties
 */

public class Fleet implements Parcelable, PopulateFromCursorInterface {

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
    public int mFactionId;
    public Map<Squadron, Integer> mSquadronCounts;
    public List<Ship> mShips;
    String LOG_TAG = Fleet.class.getSimpleName();
    private int mFleetId;
    private String mFleetName;
    private int mPointLimit;
    private Commander mCommander;
    private Objective mAssaultObjective;
    private Objective mDefenseObjective;
    private Objective mNavigationObjective;

    public Fleet(){
        mPointLimit = 400;
        mSquadronCounts = new HashMap<>();
        mShips = new ArrayList<>();
    }

    public Fleet(int factionId) {
        this();
        Log.d(LOG_TAG, "Fleet: factionId: " + factionId);
        mFactionId = factionId;
    }

    protected Fleet(Parcel in) {

        mFleetId = in.readInt();
        mFleetName = in.readString();
        mFactionId = in.readInt();
        mPointLimit = in.readInt();
        mCommander = in.readParcelable(Commander.class.getClassLoader());

        mSquadronCounts = new HashMap<>();

        int mapSize = in.readInt();

        for (int i = 0; i < mapSize; i++) {
            Squadron squad = in.readParcelable(Squadron.class.getClassLoader());
            int count = in.readInt();
            mSquadronCounts.put(squad, count);
        }

        if (in.readByte() == 0x01) {
            mShips = new ArrayList<>();
            in.readList(mShips, Ship.class.getClassLoader());
        } else {
            mShips = null;
        }
    }

    public void populate(Cursor cursor){
        mFleetId = cursor.getInt(FleetDatabaseContract.FleetTable._ID);
        mFactionId = cursor.getInt(FleetDatabaseContract.FleetTable.FACTION_ID);
        mPointLimit = cursor.getInt(FleetDatabaseContract.FleetTable.FLEET_POINT_LIMIT);
        mFleetName = cursor.getString(FleetDatabaseContract.FleetTable.NAME);
        mFleetId = cursor.getInt(FleetDatabaseContract.FleetTable._ID);
    }

    public String name(){
        return mFleetName;
    }

    public Integer id(){
        return mFleetId;
    }

    public void setId(int id) {
        mFleetId = id;
    }

    public void setId(long id) {
        setId((int) id);
    }

    public void setName(String name){
        mFleetName = name;
    }

    public Integer fleetPointLimit(){
        return mPointLimit;
    }

    public Integer fleetPoints(){

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
        for (Map.Entry<Squadron, Integer> squadCount : mSquadronCounts.entrySet()) {
            total += squadCount.getKey().pointCost() * squadCount.getValue();
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
        int count = mSquadronCounts.containsKey(squadron) ? mSquadronCounts.get(squadron) : 0;
        mSquadronCounts.put(squadron, count + 1);
    }

    public Map<Squadron, Integer> squadronCounts(){
        return mSquadronCounts;
    }

    public boolean hasComponent(Squadron squadron){

        // Check if this fleet already has a squadron with the same id
        return mSquadronCounts.containsKey(squadron);
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

    public boolean canAddComponent(FleetPointsGameComponent component) {
        Log.w(LOG_TAG, "Point-only check for component" + component);
        Log.w(LOG_TAG, "Need canAddComponent method for more specific check.");

        // Check if the squadron fits under the point limit
        return component.pointCost() <= this.remainingFleetPoints();
    }

    public void clearCommander() {
        mCommander = null;
    }

    public void setCommander(Commander commander) {
        mCommander = commander;
    }

    public Commander commander() {
        return mCommander;
    }

    /* Parcel support */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mFleetId);
        dest.writeString(mFleetName);
        dest.writeInt(mFactionId);
        dest.writeInt(mPointLimit);
        dest.writeParcelable(mCommander, flags);

        dest.writeInt(mSquadronCounts.size());

        Iterator<Map.Entry<Squadron, Integer>> squadIter = mSquadronCounts.entrySet().iterator();

        while (squadIter.hasNext()) {
            Map.Entry<Squadron, Integer> entry = squadIter.next();
            dest.writeParcelable(entry.getKey(), flags);
            dest.writeInt(entry.getValue());
        }

        if (mShips == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mShips);
        }
    }
}
