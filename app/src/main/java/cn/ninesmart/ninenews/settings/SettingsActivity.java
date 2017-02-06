package cn.ninesmart.ninenews.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.utils.AppInfo;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        private Preference mVersionPreference;
        private boolean mIsChecking;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mIsChecking = false;

            addPreferencesFromResource(R.xml.settings_main);

            mVersionPreference = getPreferenceManager().findPreference(getString(R.string.pk_settings_version));
            mVersionPreference.setTitle(getString(R.string.version_version, AppInfo.APP_VERSION));
            mVersionPreference.setSummary(R.string.click_to_check_update);
            mVersionPreference.setOnPreferenceClickListener(this::checkUpdate);
        }

        private boolean checkUpdate(Preference preference) {
            if (!mIsChecking) {
                mIsChecking = true;
                preference.setSummary(R.string.checking_update);
                ApiFactory.getInstance().getNineNewsService().getAppInfo(AppInfo.APP_ID).compose(ApplySchedulers.network()).subscribe(res -> {
                    if (AppInfo.APP_VERSION.compareTo(res.app.version) < 0) {
                        preference.setSummary(getString(R.string.new_version_available_version, res.app.version));
                        IAuthRepository authRepository = AuthRepository.getInstance(getActivity());
                        if (!authRepository.isLoggedIn()) {
                            mIsChecking = false;
                            Toast.makeText(getActivity(), R.string.you_are_not_logged_in, Toast.LENGTH_SHORT).show();
                        } else {
                            String auth;
                            try {
                                auth = URLDecoder.decode(authRepository.getAuth().getAuth(), "US-ASCII");
                            } catch (UnsupportedEncodingException e) {
                                auth = authRepository.getAuth().getAuth();
                            }
                            ApiFactory.getInstance().getNineNewsService().getAppDownloadInfo(AppInfo.APP_ID, auth).compose(ApplySchedulers.network()).subscribe(dlRes -> {
                                mIsChecking = false;
                                // Have update
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(R.string.new_version_available)
                                        .setMessage(R.string.go_to_download)
                                        .setPositiveButton(R.string.ok, (dialog, which) ->
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dlRes.url))))
                                        .setNegativeButton(R.string.cancel, null)
                                        .show();
                            });
                        }
                    } else {
                        mIsChecking = false;
                        Toast.makeText(getActivity(), R.string.ninenews_is_up_to_date, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return true;
        }
    }
}
