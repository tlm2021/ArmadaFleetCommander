package com.travismosley.armadafleetcommander.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetcommander.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetcommander.game.component.upgrade.UpgradeSlot;

import java.util.List;

/**
 * Class encapsulating an Armada Ship
 */
public class Ship extends Vehicle {

    private final static String LOG_TAG = Ship.class.getSimpleName();
    private List<UpgradeSlot> mUpgradeSlots;

    public Ship(){}

    @Override
    public String typeName(){
        return Ship.class.getSimpleName();
    }

    public void populate(Cursor cursor){
        super.populate(cursor);
    }

    public void setUpgradeSlots(List<UpgradeSlot> upgradeSlots){
        mUpgradeSlots = upgradeSlots;
    }

    public boolean hasUpgrade(Upgrade upgrade){

        for (int i=0; i < mUpgradeSlots.size(); i++){

            // See if this upgrade slot is equipped
            if (mUpgradeSlots.get(i).isEquipped()){

                // See if the equipped upgrade is unique
                Upgrade equippedUpgrade = mUpgradeSlots.get(i).getEquipped();
                if (equippedUpgrade.id() == upgrade.id()){
                    return true;
                }
            }
        }
        return false;
    }

    public List<UpgradeSlot> upgradeSlots(){
        return mUpgradeSlots;
    }

    // Parcel support
    protected Ship(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
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