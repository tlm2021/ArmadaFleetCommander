package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;
import android.os.Parcelable;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.ComponentDatabaseContract;
import com.travismosley.armadafleetadmiral.data.contract.table.DefenseTokenTableContract;

public class SquadronKeyword extends GameComponent {

    public SquadronKeyword() {

    }

    @Override
    public void populate(Cursor cursor) {
        mId = cursor.getInt(ComponentDatabaseContract.SquadronKeywordTable.SQUADRON_KEYWORD_ID);
        mTitle = cursor.getString(DefenseTokenTableContract.TITLE);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String typeName() {
        return SquadronKeyword.class.getSimpleName();
    }

    // Parcel support
    protected SquadronKeyword(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SquadronKeyword> CREATOR = new Parcelable.Creator<SquadronKeyword>() {
        @Override
        public SquadronKeyword createFromParcel(Parcel in) {
            return new SquadronKeyword(in);
        }

        @Override
        public SquadronKeyword[] newArray(int size) {
            return new SquadronKeyword[size];
        }
    };
}