package cn.ninesmart.ninenews.settings;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.storage.preferences.PreferencesFactory;
import cn.ninesmart.ninenews.utils.AppInfo;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_black_24dp);
        }

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static class SettingsFragment extends PreferenceFragment {
        public static final String PK_APP_DOWNLOAD_REF = "APP_DOWNLOAD_REF";
        private Preference mVersionPreference;
        private boolean mIsChecking;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mIsChecking = false;

            addPreferencesFromResource(R.xml.settings_main);

            mVersionPreference = getPreferenceManager().findPreference(getString(R.string.pk_settings_version));
            mVersionPreference.setTitle(getString(R.string.version_version, AppInfo.APP_VERSION));
            mVersionPreference.setSummary(isDownloading() ?
                    R.string.downloading_latest_version_of_9news : R.string.click_to_check_update);
            mVersionPreference.setOnPreferenceClickListener(this::checkUpdate);
        }

        private boolean checkUpdate(Preference preference) {
            if (!mIsChecking && !isDownloading()) {
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
                                                downloadNewVersionApk(dlRes.url, res.app.version))
                                        .setNegativeButton(R.string.cancel, null)
                                        .show();
                            });
                        }
                    } else {
                        mIsChecking = false;
                        preference.setSummary(R.string.click_to_check_update);
                        Toast.makeText(getActivity(), R.string.ninenews_is_up_to_date, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return true;
        }

        private void downloadNewVersionApk(String url, String version) {
            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            request.setTitle(getString(R.string.downloading_latest_version_of_9news));
            request.setDescription(getString(R.string.downloading_9news_version, version));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.format("9News-%s.apk", version));

            long downloadRef = downloadManager.enqueue(request);
            PreferencesFactory.getInstance(getActivity()).getAppPreferences()
                    .edit().putLong(PK_APP_DOWNLOAD_REF, downloadRef).apply();
        }

        private boolean isDownloading() {
            return PreferencesFactory.getInstance(getActivity()).getAppPreferences()
                    .getLong(PK_APP_DOWNLOAD_REF, -1) != -1;
        }
    }
}
