package com.polka.rentplace.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font {

    public void setFont(Context _context, TextView textView) {
        Typeface googleSans = Typeface.createFromAsset(_context.getAssets(), "font/GoogleSans-Regular.ttf");
        textView.setTypeface(googleSans);
    }

}
