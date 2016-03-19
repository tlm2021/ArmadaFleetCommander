package com.travismosley.android.ui;

import android.os.Build;

/**
 * Gravity extention with some backwards compatibility methods
 */
public class Gravity extends android.view.Gravity {

    public static int start() {
        if (Build.VERSION.SDK_INT < 22) {

            return android.view.Gravity.LEFT;
        } else {
            return android.view.Gravity.START;
        }
    }

    public static int end() {
        if (Build.VERSION.SDK_INT < 22) {
            return android.view.Gravity.RIGHT;
        } else {
            return android.view.Gravity.END;
        }
    }

}
