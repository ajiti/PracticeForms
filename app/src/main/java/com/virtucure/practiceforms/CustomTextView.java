package com.virtucure.practiceforms;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by AJITI on 6/30/2016.
 */
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.custom_font);
        String fontFamily = null;
        final int n = a.getIndexCount();
        for (int i = 0; i < n; ++i) {
            int attr = a.getIndex(i);
            fontFamily = a.getString(attr);
/*            if (attr == R.styleable.customfont_android_fontFamily) {
                fontFamily = a.getString(attr);
            }*/
            a.recycle();
        }
        if (!isInEditMode()) {
            try {
                setTypeface(OpenSans.getInstance(context).getTypeFace());
//                setTypeface(Typeface.createFromAsset(context.getAssets(), fontFamily));
            } catch (Exception e) {
            }
        }
    }
}
