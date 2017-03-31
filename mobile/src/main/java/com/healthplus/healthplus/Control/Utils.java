package com.healthplus.healthplus.Control;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ramkishorevs on 03/03/17.
 */

public class Utils {


    public Typeface getFontType(Context context)
    {

            Typeface heading = Typeface.createFromAsset(context.getResources().getAssets(), "Montserrat-Regular.ttf");
            return heading;


    }
}
