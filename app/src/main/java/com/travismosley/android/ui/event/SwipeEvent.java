package com.travismosley.android.ui.event;

import android.view.MotionEvent;

import com.travismosley.math.Vector2D;

/**
 * This class distills out the basic information about a swipe from a MotionEvent.
 */
public class SwipeEvent {
    public enum Direction{
        UP, DOWN, LEFT, RIGHT, NONE
    }

    private float mSourceX;
    private float mSourceY;

    private float mX;
    private float mY;

    private Vector2D mSwipeVector;
    private long mDuration;

    public SwipeEvent(MotionEvent event){
        // Initialize with the data from the MOTION_DOWN event

        // Store the initial touch location
        mSourceX = event.getX(0);
        mSourceY = event.getY(0);

        this.updateFrom(event);
    }

    public void updateFrom(MotionEvent event){
        // Store the current touch location
        mX = event.getX();
        mY = event.getY();

        // Get a vector for the swipe event
        mSwipeVector = new Vector2D(mX-mSourceX, mY-mSourceY);

        // Set how long the Swipe motion took
        mDuration = event.getEventTime() - event.getDownTime();
    }

    public float x (){
        return mX;
    }

    public float y (){
        return mY;
    }

    public float sourceX(){
        return mSourceX;
    }

    public float sourceY(){
        return mSourceY;
    }

    public Direction direction(){

        // Find the direction of the swipe
        if (Math.abs(mSwipeVector.x) > Math.abs(mSwipeVector.y)){

            // If the X direction is longer, we have a horizontal swipe
            if (mSwipeVector.x > 0){
                return Direction.RIGHT;
            } else{
                return Direction.LEFT;
            }
        }else if (Math.abs(mSwipeVector.x) < Math.abs(mSwipeVector.y)) {

            // If the Y direction is longer, we have a vertical swipe
            if (mSwipeVector.y > 0){
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }else {

            // Should only reach this case if the user hasn't moved their finger
            return Direction.NONE;
        }
    }

    public float length(){
        return mSwipeVector.magnitude();
    }

    public float duration(){
        return mDuration;
    }

    public float deviationAngle(){
        // Gives the angle, in degrees, that this swipe event deviates from its ideal
        Direction d = direction();
        Vector2D idealVector;

        // Define our "ideal" vector for each direction (a perfect up/down/left/right swipe)
        if (d == Direction.UP){
            idealVector = new Vector2D(0, -1);
        }else if (d == Direction.DOWN){
            idealVector = new Vector2D(0, 1);
        }else if (d == Direction.LEFT){
            idealVector = new Vector2D(-1, 0);
        }else if (d == Direction.RIGHT){
            idealVector = new Vector2D(1, 0);
        }else{
            idealVector = new Vector2D(0, 0);
        }

        return Math.abs(mSwipeVector.angleBetween(idealVector));
    }

    public String toString(){
        return "SwipeEvent(" + direction() + ", " + mSwipeVector + ">)";
    }

}
