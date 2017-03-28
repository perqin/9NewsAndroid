package cn.ninesmart.ninenews.data.update.repositories;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.ninesmart.ninenews.common.ApplySchedulers;
import cn.ninesmart.ninenews.data.auth.repositories.AuthRepository;
import cn.ninesmart.ninenews.data.auth.repositories.IAuthRepository;
import cn.ninesmart.ninenews.data.network.retrofit.ApiFactory;
import cn.ninesmart.ninenews.data.storage.preferences.PreferencesFactory;
import cn.ninesmart.ninenews.data.update.models.VersionInfo;
import cn.ninesmart.ninenews.utils.AppInfo;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-3-28
 */

public class UpdateRepository implements IUpdateRepository {
    public static final String PK_APP_DOWNLOAD_REF = "APP_DOWNLOAD_REF";

    private static UpdateRepository sInstance;

    private SharedPreferences mSharedPreferences;
    private IAuthRepository mAuthRepository;
    private DownloadManager mDownloadManager;

    public static UpdateRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UpdateRepository(context);
        }
        return sInstance;
    }

    private UpdateRepository(Context context) {
        context = context.getApplicationContext();
        mSharedPreferences = PreferencesFactory.getInstance(context).getAppPreferences();
        mAuthRepository = AuthRepository.getInstance(context);
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    public VersionInfo getCurrentVersion() {
        VersionInfo info = new VersionInfo();
        info.setVersionCode(AppInfo.APP_VERSION_CODE);
        info.setVersionName(AppInfo.APP_VERSION_NAME);
        return info;
    }

    @Override
    public Observable<VersionInfo> checkLatestVersion() {
        return ApiFactory.getInstance().getNineNewsService().getAppInfo(AppInfo.APP_ID).compose(ApplySchedulers.network()).map(getAppInfoRes -> {
            VersionInfo info = new VersionInfo();
            info.setVersionName(getAppInfoRes.app.version);
            // FIXME: Server should provide version code
            info.setVersionCode(AppInfo.APP_VERSION_CODE + getAppInfoRes.app.version.compareTo(AppInfo.APP_VERSION_NAME));
            return info;
        });
    }

    @Override
    public boolean isUpdating() {
        return mSharedPreferences.getLong(PK_APP_DOWNLOAD_REF, -1) != -1;
    }

    @Override
    public Observable<Void> startUpdating(VersionInfo info) {
        if (!mAuthRepository.isLoggedIn()) {
            return Observable.error(new RuntimeException("Not logged in"));
        }

        String auth;
        try {
            auth = URLDecoder.decode(mAuthRepository.getAuth().getAuth(), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            auth = mAuthRepository.getAuth().getAuth();
        }
        return ApiFactory.getInstance().getNineNewsService().getAppDownloadInfo(AppInfo.APP_ID, auth).compose(ApplySchedulers.network()).flatMap(getAppDownloadInfoRes -> {
            info.setDownloadUri(Uri.parse(getAppDownloadInfoRes.url));
            startDownloading(info);
            return null;
        });
    }

    @Override
    public Observable<Void> cancelUpdating() {
        int removedCount = mDownloadManager.remove(mSharedPreferences.getLong(PK_APP_DOWNLOAD_REF, -1));
        mSharedPreferences.edit().remove(PK_APP_DOWNLOAD_REF).apply();
        return removedCount == 0
                ? Observable.error(new RuntimeException("No download found"))
                : Observable.just(null);
    }

    private void startDownloading(VersionInfo info) {
        DownloadManager.Request request = new DownloadManager.Request(info.getDownloadUri());

//        request.setTitle(getString(R.string.downloading_latest_version_of_9news));
//        request.setDescription(getString(R.string.downloading_9news_version, info.getVersionName()));
        // TODO: How to get title string?
        request.setTitle("Downloading...");
        request.setDescription("Downloading " + info.getVersionName() + "...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.format("9News-%s.apk", info.getVersionName()));

        long downloadRef = mDownloadManager.enqueue(request);
        mSharedPreferences.edit().putLong(PK_APP_DOWNLOAD_REF, downloadRef).apply();
    }
}
