package com.travismosley.armadafleetcommander.game.component.upgrade;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetcommander.data.contract.ArmadaDatabaseContract;
import com.travismosley.armadafleetcommander.game.component.GameComponent;

import java.util.ArrayList;

/**
 * GameComponent class for ship upgrade cards
 */
public class Upgrade extends GameComponent {

    private final static String LOG_TAG = Upgrade.class.getSimpleName();

    protected boolean mUnique;
    protected String mText;

    protected int mUpgradeTypeId;
    protected String mUpgradeTypeName;

    protected ArrayList<UpgradeSlot> mUpgradeSlots;

    public Upgrade(){}

    @Override
    public boolean isUnique(){
        return mUnique;
    }

    public String typeName(){
        return Upgrade.class.getSimpleName();
    }

    public int upgradeTypeId(){
        return mUpgradeTypeId;
    }

    public void populate(Cursor cursor){

        // Base component attributes
        mId = cursor.getInt(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_ID);
        mTitle = cursor.getString(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_TITLE);
        mPointCost = cursor.getInt(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_POINT_COST);

        // Upgrade attributes
        mUnique = cursor.getBoolean(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_IS_UNIQUE);
        mText = cursor.getString(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_TEXT);
        mUpgradeTypeId = cursor.getInt(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_TYPE_ID);
        mUpgradeTypeName = cursor.getString(ArmadaDatabaseContract.UpgradeTable.COLUMN_NAME_TYPE_NAME);
    }

    // Parcel support
    protected Upgrade(Parcel in) {
        super(in);
        mUnique = in.readByte() != 0x00;
        mText = in.readString();
        mUpgradeTypeId = in.readInt();
        mUpgradeTypeName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (mUnique ? 0x01 : 0x00));
        dest.writeString(mText);
        dest.writeInt(mUpgradeTypeId);
        dest.writeString(mUpgradeTypeName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Upgrade> CREATOR = new Parcelable.Creator<Upgrade>() {
        @Override
        public Upgrade createFromParcel(Parcel in) {
            return new Upgrade(in);
        }

        @Override
        public Upgrade[] newArray(int size) {
            return new Upgrade[size];
        }
    };
}
