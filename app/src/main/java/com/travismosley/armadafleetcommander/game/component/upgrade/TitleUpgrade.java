package com.travismosley.armadafleetcommander.game.component.upgrade;

import android.os.Parcel;

import com.travismosley.android.data.database.cursor.Cursor;

import java.util.ArrayList;

/**
 * Upgrade class specific for Titles
 */
public class TitleUpgrade extends Upgrade {

    private final static String LOG_TAG = TitleUpgrade.class.getSimpleName();

    protected ArrayList<UpgradeSlot> mUpgradeSlots;

    public TitleUpgrade(){}

    @Override
    public boolean isUnique(){
        return mUnique;
    }

    public String typeName(){
        return TitleUpgrade.class.getSimpleName();
    }

    public int upgradeTypeId(){
        return mUpgradeTypeId;
    }

    public void populate(Cursor cursor){
        super.populate(cursor);
    }

    // Parcel support
    protected TitleUpgrade(Parcel in) {
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
    public static final Creator<TitleUpgrade> CREATOR = new Creator<TitleUpgrade>() {
        @Override
        public TitleUpgrade createFromParcel(Parcel in) {
            return new TitleUpgrade(in);
        }

        @Override
        public TitleUpgrade[] newArray(int size) {
            return new TitleUpgrade[size];
        }
    };
}
