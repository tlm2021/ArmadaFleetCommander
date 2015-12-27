package com.travismosley.armadafleetcommander.game.component.upgrade;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract.ShipUpgradeSlotsTable;

/**
 * Class for defining and upgrade support and storage
 */
public class UpgradeSlot {

    private int mUpgradeTypeId;
    private String mUpgradeTypeName;
    private Upgrade mEquippedUpgrade;

    public UpgradeSlot(){}

    public void populate(Cursor cursor){

        // Base component attributes
        mUpgradeTypeId = cursor.getInt(ShipUpgradeSlotsTable.COLUMN_NAME_UPGRADE_TYPE_ID);
        mUpgradeTypeName = cursor.getString(ShipUpgradeSlotsTable.COLUMN_NAME_UPGRADE_TYPE_NAME);
    }

    public String typeName(){
        return mUpgradeTypeName;
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

}
