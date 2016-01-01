package com.travismosley.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Vector2DTest {

    public static final float X1 = 2f;
    public static final float Y1 = 0f;

    public static final float X2 = 0f;
    public static final float Y2 = 2f;

    public static final float X3 = -2f;
    public static final float Y3 = 2f;

    public static final float X4 = 2f;
    public static final float Y4 = 2f;

    public static final float MAGNITUDE_ONE = 2f;
    public static final float MAGNITUDE_THREE = 2.828f;
    public static final float DOT_PRODUCT = 0f;
    public static final float NINETY_DEGREES = 90.0f;
    public static final float FOURTY_FIVE_DEGREES = 45.0f;
    public static final float ZERO_DEGREES = 0f;

    Vector2D mTestVectorOne;
    Vector2D mTestVectorTwo;
    Vector2D mTestVectorThree;
    Vector2D mTestVectorFour;

    @Before
    public void initVectors(){
        mTestVectorOne = new Vector2D(X1, Y1);
        mTestVectorTwo = new Vector2D(X2, Y2);
        mTestVectorThree = new Vector2D(X3, Y3);
        mTestVectorFour = new Vector2D(X4, Y4);
    }

    @Test
    public void vector2D_Constructor(){
        float x = 1.23f;
        float y = 4.56f;
        Vector2D vec = new Vector2D(x, y);
        Assert.assertEquals(x, vec.x, 0f);
        Assert.assertEquals(y, vec.y, 0f);
    }

    @Test
    public void vector2D_CalculateLength(){
        Assert.assertEquals(MAGNITUDE_ONE, mTestVectorOne.length(), 0f);
        Assert.assertEquals(MAGNITUDE_ONE, mTestVectorOne.magnitude(), 0f);

        Assert.assertEquals(MAGNITUDE_THREE, mTestVectorThree.length(), 0.001f);
        Assert.assertEquals(MAGNITUDE_THREE, mTestVectorThree.magnitude(), 0.001f);
    }

    @Test
    public void vector2D_CalculateDotProduct(){
        Assert.assertEquals(DOT_PRODUCT, mTestVectorOne.dot(X2, Y1), 0f);
        Assert.assertEquals(DOT_PRODUCT, mTestVectorOne.dot(mTestVectorTwo), 0f);

        Assert.assertEquals(DOT_PRODUCT, mTestVectorThree.dot(X4, Y4), 0f);
        Assert.assertEquals(DOT_PRODUCT, mTestVectorThree.dot(mTestVectorFour), 0f);
    }

    @Test
    public void vector2D_CalculateAngleBetween(){


        // Test the default angleBetween, which is a 0,1 vector
        Assert.assertEquals(NINETY_DEGREES, mTestVectorOne.angleBetween(), 0f);
        Assert.assertEquals(ZERO_DEGREES, mTestVectorTwo.angleBetween(), 0f);
        Assert.assertEquals(FOURTY_FIVE_DEGREES, mTestVectorThree.angleBetween(), 0f);
        Assert.assertEquals(FOURTY_FIVE_DEGREES, mTestVectorFour.angleBetween(), 0f);

        // Test angles that are 90 degrees from each other (both Vector2D and (int,int) forms)
        Assert.assertEquals(NINETY_DEGREES, mTestVectorOne.angleBetween(X2, Y2), 0f);
        Assert.assertEquals(NINETY_DEGREES, mTestVectorOne.angleBetween(mTestVectorTwo), 0f);

        Assert.assertEquals(NINETY_DEGREES, mTestVectorThree.angleBetween(X4, Y4), 0f);
        Assert.assertEquals(NINETY_DEGREES, mTestVectorOne.angleBetween(mTestVectorTwo), 0f);

        // Test angles that are 45 degrees from each other
        Assert.assertEquals(FOURTY_FIVE_DEGREES, mTestVectorOne.angleBetween(mTestVectorFour), 0.0f);
        Assert.assertEquals(FOURTY_FIVE_DEGREES, mTestVectorTwo.angleBetween(mTestVectorThree), 0.0f);
    }

}
