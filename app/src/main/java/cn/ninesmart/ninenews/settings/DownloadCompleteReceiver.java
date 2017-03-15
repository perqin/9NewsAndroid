package cn.ninesmart.ninenews.settings;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import cn.ninesmart.ninenews.data.storage.preferences.PreferencesFactory;

public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (id != -1 && id == PreferencesFactory.getInstance(context).getAppPreferences().getLong(SettingsActivity.SettingsFragment.PK_APP_DOWNLOAD_REF, -1)) {
                Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(new DownloadManager.Query().setFilterById(id));
                if (cursor != null && !cursor.isAfterLast()) {
                    cursor.moveToFirst();
                    String localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    cursor.close();
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(Uri.parse(localUri), "application/vnd.android.package-archive");
                    context.startActivity(installIntent);
                }
            }
        }
    }
}
