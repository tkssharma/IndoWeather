package com.tarunsoft.weatherIndia.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tarunsoft.weatherIndia.R;

/**
 * Created by tsharma3 on 8/25/2015.
 */
public class Util {

    public String getSavedLocation(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_location_key),
                context.getString(R.string.pref_location_default));
    }
}
