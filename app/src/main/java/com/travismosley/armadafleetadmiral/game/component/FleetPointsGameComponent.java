package com.travismosley.armadafleetadmiral.game.component;

import android.os.Parcel;

import com.travismosley.android.data.database.cursor.Cursor;
import com.travismosley.armadafleetadmiral.data.contract.table.ComponentTableContract;

/**
 * Base class for all objects that represent GameComponents
 */
public abstract class FleetPointsGameComponent extends GameComponent {

    private static final String LOG_TAG = FleetPointsGameComponent.class.getSimpleName();

    protected int mPointCost;

    public FleetPointsGameComponent() {
    }

    public int pointCost() {
        return mPointCost;
    }

    // Parcel support
    protected FleetPointsGameComponent(Parcel in) {
        super(in);
        mPointCost = in.readInt();
    }

    @Override
    public void populate(Cursor cursor) {
        super.populate(cursor);
        mPointCost = cursor.getInt(ComponentTableContract.POINT_COST);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mPointCost);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
