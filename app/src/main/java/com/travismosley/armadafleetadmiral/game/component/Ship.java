package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.game.component.upgrade.ShipUpgradeManager;
import com.travismosley.armadafleetadmiral.game.component.upgrade.TitleUpgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.Upgrade;
import com.travismosley.armadafleetadmiral.game.component.upgrade.UpgradeSlot;

import java.util.List;

/**
 * Class encapsulating an Armada Ship
 */
public class Ship extends Vehicle {

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

    private final static String LOG_TAG = Ship.class.getSimpleName();
    private TitleUpgrade mTitleUpgrade;
    private String mCustomTitle;
    private ShipUpgradeManager mUpgradeManager;
    private boolean mIsFlagship = false;

    public Ship(){}

    // Parcel support
    protected Ship(Parcel in) {
        super(in);
        mTitleUpgrade = in.readParcelable(TitleUpgrade.class.getClassLoader());
        mCustomTitle = in.readString();
        mUpgradeManager = in.readParcelable(ShipUpgradeManager.class.getClassLoader());
        mIsFlagship = in.readByte() != 0x00;
    }

    @Override
    public String typeName(){
        return Ship.class.getSimpleName();
    }

    public void populate(Cursor cursor){
        super.populate(cursor);
    }

    public void setUpgradeSlots(List<UpgradeSlot> upgradeSlots){
        mUpgradeManager = new ShipUpgradeManager(upgradeSlots);
    }

    @Override
    public int pointCost(){
        int cost = mPointCost;
        cost += mUpgradeManager.getEquippedUpgradePoints();

        if (mTitleUpgrade != null){
            cost += mTitleUpgrade.pointCost();
        }
        return cost;
    }

    public boolean hasUpgrade(Upgrade upgrade){
        return mUpgradeManager.hasUpgrade(upgrade);
    }

    public void addUpgrade(Upgrade upgrade){
        mUpgradeManager.addUpgrade(upgrade);
    }

    public List<Upgrade> getEquippedUpgrades() {
        return mUpgradeManager.getEquippedUpgrades();
    }

    public void setTitle(TitleUpgrade title) {
        mTitleUpgrade = title;
    }

    public void clearTitle() {
        mTitleUpgrade = null;
    }

    public boolean hasTitleUpgrade() {
        return mTitleUpgrade != null;
    }

    public TitleUpgrade titleUpgrade() {
        return mTitleUpgrade;
    }

    public boolean hasCustomTitle() {
        return mCustomTitle != null;
    }

    public String customTitle() {
        return mCustomTitle;
    }

    public void clearCustomTitle() {
        mCustomTitle = null;
    }

    public void setCustomTitle(String title) {
        mCustomTitle = title;
    }

    public boolean isFlagship() {
        return mIsFlagship;
    }

    public void toggleIsFlagship() {
        mIsFlagship = !mIsFlagship;
    }

    public void setIsFlagship(boolean isFlagship) {
        mIsFlagship = isFlagship;
    }

    public List<UpgradeSlot> upgradeSlots() {
        return mUpgradeManager.getUpgradeSlots();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mTitleUpgrade, flags);
        dest.writeString(mCustomTitle);
        dest.writeParcelable(mUpgradeManager, flags);
        dest.writeByte((byte) (mIsFlagship ? 0x01 : 0x00));
    }
}