package com.travismosley.armadafleetadmiral.game.component.upgrade;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.PopulateFromCursorInterface;
import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.ShipUpgradeSlotsTable;

/**
 * Class for defining and upgrade support and storage
 */
public class UpgradeSlot implements Parcelable, PopulateFromCursorInterface {

    private int mUpgradeTypeId;
    private String mUpgradeTypeName;
    private Upgrade mEquippedUpgrade;

    public UpgradeSlot(){}

    public void populate(Cursor cursor){

        // Base component attributes
        mUpgradeTypeId = cursor.getInt(ShipUpgradeSlotsTable.UPGRADE_TYPE_ID);
        mUpgradeTypeName = cursor.getString(ShipUpgradeSlotsTable.UPGRADE_TYPE_NAME);
    }

    public String typeName(){
        return mUpgradeTypeName;
    }

    public int typeId(){
        return mUpgradeTypeId;
    }

    public boolean canEquip(Upgrade upgrade){
        return upgrade.upgradeTypeId() == mUpgradeTypeId;
    }

    public boolean isEquipped(){
        return (mEquippedUpgrade != null);
    }

    public void equip(Upgrade upgrade){
        if (canEquip(upgrade)) {
            mEquippedUpgrade = upgrade;
        }
    }

    public Upgrade getEquipped(){
        return mEquippedUpgrade;
    }

    public void clear(){
        mEquippedUpgrade = null;
    }

    // Parcel support
    protected UpgradeSlot(Parcel in) {
        mUpgradeTypeId = in.readInt();
        mUpgradeTypeName = in.readString();
        mEquippedUpgrade = in.readParcelable(Upgrade.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mUpgradeTypeId);
        dest.writeString(mUpgradeTypeName);
        dest.writeParcelable(mEquippedUpgrade, 0);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
