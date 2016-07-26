package com.virtucure.practiceforms;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by AJITI on 6/30/2016.
 */
public class OpenSans {

    private static OpenSans instance;
    private static Typeface typeface;

    public static OpenSans getInstance(Context context) {
        synchronized (OpenSans.class) {
            if (instance == null) {
                instance = new OpenSans();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Roboto-Regular.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}