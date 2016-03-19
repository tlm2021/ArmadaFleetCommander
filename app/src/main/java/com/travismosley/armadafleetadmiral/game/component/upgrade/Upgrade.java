package com.travismosley.armadafleetadmiral.game.component.upgrade;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract.UpgradeTable;
import com.travismosley.armadafleetadmiral.game.component.FleetPointsGameComponent;

import java.util.ArrayList;

/**
 * GameComponent class for ship upgrade cards
 */
public class Upgrade extends FleetPointsGameComponent {

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
        super.populate(cursor);

        // Upgrade attributes
        mUnique = cursor.getBoolean(UpgradeTable.IS_UNIQUE);
        mText = cursor.getString(UpgradeTable.TEXT);
        mUpgradeTypeId = cursor.getInt(UpgradeTable.TYPE_ID);
        mUpgradeTypeName = cursor.getString(UpgradeTable.TYPE_NAME);
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
