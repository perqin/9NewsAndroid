package cn.ninesmart.ninenews.data.auth.stores;

import android.content.Context;
import android.content.SharedPreferences;

import cn.ninesmart.ninenews.data.auth.models.AuthModel;
import cn.ninesmart.ninenews.data.storage.preferences.PreferencesFactory;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class LocalAuthStore {
    private static final String PK_APP_AUTH_UID = "AUTH_UID";
    private static final String PK_APP_AUTH_TOKEN = "AUTH_TOKEN";

    private SharedPreferences mAppPreferences;
    private AuthModel mAuth;

    public LocalAuthStore(Context context) {
        mAppPreferences = PreferencesFactory.getInstance(context).getAppPreferences();
        // Init auth model
        String uid = mAppPreferences.getString(PK_APP_AUTH_UID, null);
        String token = mAppPreferences.getString(PK_APP_AUTH_TOKEN, null);
        if (uid != null && token != null) {
            mAuth = new AuthModel();
            mAuth.setUid(uid);
            mAuth.setAuth(token);
        } else {
            mAuth = null;
        }
    }

    public void updateAuthSync(AuthModel model) {
        mAuth = model;
        mAppPreferences.edit()
                .putString(PK_APP_AUTH_UID, model.getUid())
                .putString(PK_APP_AUTH_TOKEN, model.getAuth()).apply();
    }

    public void removeAuthSync() {
        mAuth = null;
        mAppPreferences.edit()
                .remove(PK_APP_AUTH_UID)
                .remove(PK_APP_AUTH_TOKEN).apply();
    }

    public AuthModel getAuth() {
        return mAuth;
    }

    public boolean hasAuth() {
        return mAuth != null;
    }
}
