package com.travismosley.armadafleetadmiral.game.component.upgrade;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.ShipUpgradeSlotsTable;
import com.travismosley.armadafleetadmiral.game.component.GameComponent;

/**
 * Class for defining and upgrade support and storage
 */
public class UpgradeSlot extends GameComponent {

    private Upgrade mEquippedUpgrade;

    public UpgradeSlot(){}

    public void populate(Cursor cursor){

        // Base component attributes
        mId = cursor.getInt(ShipUpgradeSlotsTable.UPGRADE_TYPE_ID);
        mTitle = cursor.getString(ShipUpgradeSlotsTable.TITLE);
    }

    public String typeName(){
        return mTitle;
    }

    public int typeId(){
        return mId;
    }

    public boolean canEquip(Upgrade upgrade){
        return upgrade.upgradeTypeId() == mId;
    }

    public boolean isEquipped(){
        return mEquippedUpgrade != null;
    }

    public void equip(Upgrade upgrade){
        if (canEquip(upgrade)) {
            mEquippedUpgrade = upgrade;
        }
    }

    public Upgrade getEquipped() {
        return mEquippedUpgrade;
    }

    public void clear() {
        mEquippedUpgrade = null;
    }


    // Parcel support
    protected UpgradeSlot(Parcel in) {
        super(in);
        mEquippedUpgrade = in.readParcelable(Upgrade.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mEquippedUpgrade, 0);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UpgradeSlot> CREATOR = new Parcelable.Creator<UpgradeSlot>() {
        @Override
        public UpgradeSlot createFromParcel(Parcel in) {
            return new UpgradeSlot(in);
        }

        @Override
        public UpgradeSlot[] newArray(int size) {
            return new UpgradeSlot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
