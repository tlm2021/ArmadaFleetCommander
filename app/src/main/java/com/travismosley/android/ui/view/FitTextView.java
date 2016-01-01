package com.travismosley.android.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

/*
    A simple TextView that dynamically re-sizes its text to fit into the available space
 */

public class FitTextView extends TextView {

    // The default text size, which is the size set in the attributes
    private float mDefaultTextSize;

    public FitTextView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter){
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

    }

    @Override
    public void setTextSize(float size){
        // If the text size it set procedurally, update the default text size
        super.setTextSize(size);
        mDefaultTextSize = size;
    }

    private void fitText() {

        /* Updates the size of the text to fit the TextView it's contained in */

        Log.d(VIEW_LOG_TAG, "Fitting text...");
        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(getTypeface());
        float textSize = mDefaultTextSize;
        paint.setTextSize(textSize);
        String text = getText().toString();
        paint.getTextBounds(text, 0, text.length(), bounds);

        measure(0, 0);

        while (bounds.width() > getMeasuredWidth())
        {
            if (textSize <= 0){
                Log.e(VIEW_LOG_TAG, "Failed to find correct text size.");
                return;
            }

            textSize--;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        // Set the text size. Use the super class so this doesn't change the default size
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

}
