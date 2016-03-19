package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.table.DefenseTokenTableContract;

public class DefenseToken extends GameComponent {

    public DefenseToken() {

    }

    @Override
    public void populate(Cursor cursor) {
        mId = cursor.getInt(DefenseTokenTableContract.DEFENSE_TOKEN_ID);
        mTitle = cursor.getString(DefenseTokenTableContract.TITLE);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String typeName() {
        return DefenseToken.class.getSimpleName();
    }

    // Parcel support
    protected DefenseToken(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DefenseToken> CREATOR = new Parcelable.Creator<DefenseToken>() {
        @Override
        public DefenseToken createFromParcel(Parcel in) {
            return new DefenseToken(in);
        }

        @Override
        public DefenseToken[] newArray(int size) {
            return new DefenseToken[size];
        }
    };
}