package com.travismosley.android.ui.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.travismosley.android.ui.event.SwipeEvent;

/**
 * An abstract class for configuring and detecting swipe event callbacks
 */
public abstract class OnSwipeListener implements View.OnTouchListener {

    private static final String LOG_TAG = OnSwipeListener.class.getSimpleName();

    // The distance a swipe must be to register as a swipe
    public int mHorizontalThreshold = 50;
    public int mVerticalThreshold = 50;

    // Tolerance is the angle (in degrees) the swipe motion can deviate from perfect
    // and still register has a horizontal or vertical swipe.

    // These angles cannot be greater than 45
    private int mVerticalTolerance = 45;
    private int mHorizontalTolerance = 45;

    private SwipeEvent mCurrentSwipe;

    private void testToleranceAngle(int angle){
        if (Math.abs(angle) >= 45){
            throw new IllegalArgumentException("Tolerance angles must be between 0-89 degrees");
        }
    }

    public void setVerticalTolerance(int angle){

        testToleranceAngle(angle);

        // Ignore negatives
        mVerticalTolerance = Math.abs(angle);
    }

    public void setHorizontalTolerance(int angle){

        testToleranceAngle(angle);

        // Ignore negatives
        mHorizontalTolerance = Math.abs(angle);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event){

        // We only want to process our event when the swipe is complete (ACTION_UP)
        switch(event.getActionMasked()){
            case (MotionEvent.ACTION_DOWN):

                Log.d(LOG_TAG, "Saw ACTION_DOWN");
                mCurrentSwipe = new SwipeEvent(event);
                return true;

            case (MotionEvent.ACTION_MOVE):
                Log.d(LOG_TAG, "Saw ACTION_MOVE");
                mCurrentSwipe.updateFrom(event);
                return true;

            case (MotionEvent.ACTION_UP):

                Log.d(LOG_TAG, "Saw ACTION_UP");

                Log.d(LOG_TAG, "Processing " + mCurrentSwipe);

                // Get the swipe direction, and start working out what to call
                SwipeEvent.Direction swipeDir = mCurrentSwipe.direction();
                if (swipeDir == SwipeEvent.Direction.NONE) {
                    return false;

                } else if (swipeDir == SwipeEvent.Direction.UP || swipeDir == SwipeEvent.Direction.DOWN) {
                    // This is a vertical swipe. Test accordingly
                    // Check it hits our swipe length threshold and angle tolerance
                    if (mCurrentSwipe.length() < mVerticalThreshold ||
                            mCurrentSwipe.deviationAngle() > mVerticalTolerance) {
                        return false;
                    }

                    // Call the appropriate callback
                    if (swipeDir == SwipeEvent.Direction.UP) {
                        return this.onSwipeUp(view, mCurrentSwipe);

                    } else {
                        return this.onSwipeDown(view, mCurrentSwipe);
                    }
                } else {
                    // This is a horizontal swipe. Test accordingly
                    if (mCurrentSwipe.length() < mHorizontalThreshold ||
                            mCurrentSwipe.deviationAngle() > mHorizontalTolerance) {
                        return false;
                    }

                    // Call the appropriate callback
                    if (swipeDir == SwipeEvent.Direction.LEFT) {
                        return this.onSwipeLeft(view, mCurrentSwipe);

                    } else {
                        return this.onSwipeRight(view, mCurrentSwipe);
                    }
                }
        }
        return false;
    }

    public abstract boolean onSwipeLeft(View view, SwipeEvent event);
    public abstract boolean onSwipeRight(View view, SwipeEvent event);
    public abstract boolean onSwipeUp(View view, SwipeEvent event);
    public abstract boolean onSwipeDown(View view, SwipeEvent event);
}