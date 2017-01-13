package cn.ninesmart.ninenews.data.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public final class PreferencesFactory {
    private static PreferencesFactory sInstance;
    private final SharedPreferences mAppPreferences;

    public static PreferencesFactory getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesFactory(context);
        }
        return sInstance;
    }

    public SharedPreferences getAppPreferences() {
        return mAppPreferences;
    }

    private PreferencesFactory(Context context) {
        mAppPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }
}
