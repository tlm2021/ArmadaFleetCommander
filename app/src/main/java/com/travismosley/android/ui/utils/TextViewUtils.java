package com.travismosley.android.ui.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Utilities for manipulating android TextView objects
 */
public class TextViewUtils {

    public static void fitText(TextView textView) {
        /* Updates with size of the text to view the TextView it's contained in */

        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(textView.getTypeface());
        float textSize = textView.getTextSize();
        paint.setTextSize(textSize);
        String text = textView.getText().toString();
        paint.getTextBounds(text, 0, text.length(), bounds);

        textView.measure(0, 0);

        while (bounds.width() > textView.getMeasuredWidth())
        {
            if (textSize <= 0){
                throw new RuntimeException("Failed to find correct text size.");
            }

            textSize--;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
