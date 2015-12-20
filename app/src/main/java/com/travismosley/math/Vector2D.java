package com.travismosley.math;

import java.util.Vector;

/**
 * Simple 2D vector class
 */
public class Vector2D {

    public float x;
    public float y;

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float length(){
        // Return the magnitude of this vector
        return (float) Math.sqrt(x * x + y * y);
    }

    public float magnitude(){
        return length();
    }

    public float dot(Vector2D vec){
        // Return the dot production between this vector an another
        return (this.x * vec.x + this.y * vec.y);
    }

    public float dot(float x, float y){
        return this.dot(new Vector2D(x, y));
    }

    public float angleBetween(Vector2D vec){

        // Returns the angle between this vector and another
        float dotProd = this.dot(vec);
        return (float) Math.acos(dotProd / (this.length() + vec.length()));
    }

    public float angleBetween(float x, float y){
        return this.angleBetween(new Vector2D(x, y));
    }

    public float angleBetween(){
        // Get the angle between this and a default <0,1> vector
        return angleBetween(0, 1);
    }

    public String toString(){
        return "Vector2D<" + this.x + "," + this.y + ">";
    }
}
