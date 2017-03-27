package cn.ninesmart.ninenews.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import cn.ninesmart.ninenews.R;
import cn.ninesmart.ninenews.data.update.models.VersionInfo;
import cn.ninesmart.ninenews.data.update.repositories.IUpdateRepository;
import cn.ninesmart.ninenews.data.update.repositories.UpdateRepository;
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
        private IUpdateRepository mUpdateRepository;
        private Preference mVersionPreference;
        private boolean mIsChecking;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mUpdateRepository = UpdateRepository.getInstance(getActivity());
            mIsChecking = false;

            addPreferencesFromResource(R.xml.settings_main);

            mVersionPreference = getPreferenceManager().findPreference(getString(R.string.pk_settings_version));
            mVersionPreference.setTitle(getString(R.string.version_version, AppInfo.APP_VERSION_NAME));
            mVersionPreference.setSummary(mUpdateRepository.isUpdating() ?
                    R.string.downloading_latest_version_of_9news : R.string.click_to_check_update);
            mVersionPreference.setOnPreferenceClickListener(this::checkUpdate);
        }

        private boolean checkUpdate(Preference preference) {
            if (!mIsChecking && !mUpdateRepository.isUpdating()) {
                mIsChecking = true;
                preference.setSummary(R.string.checking_update);
                mUpdateRepository.checkLatestVersion().subscribe(info -> {
                    mIsChecking = false;
                    if (mUpdateRepository.getCurrentVersion().getVersionCode() < info.getVersionCode()) {
                        preference.setSummary(getString(R.string.new_version_available_version, info.getVersionName()));
                        showDownloadDialog(info);
                    } else {
                        preference.setSummary(R.string.click_to_check_update);
                        Toast.makeText(getActivity(), R.string.ninenews_is_up_to_date, Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    mIsChecking = false;
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(getActivity(), R.string.update_in_progress, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        private void showDownloadDialog(VersionInfo info) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.new_version_available)
                    .setMessage(R.string.go_to_download)
                    .setPositiveButton(R.string.ok, (dialog, which) -> mUpdateRepository.startUpdating(info).subscribe(
                            aVoid -> Toast.makeText(getActivity(), R.string.start_downloading, Toast.LENGTH_SHORT).show(),
                            throwable -> Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show()
                    ))
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        }
    }
}
