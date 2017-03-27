package cn.ninesmart.ninenews.data.update.models;

import android.net.Uri;

/**
 * Author   : perqin
 * Date     : 17-3-28
 */

public class VersionInfo {
    private int versionCode;
    private String versionName;
    private Uri downloadUri;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Uri getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(Uri downloadUri) {
        this.downloadUri = downloadUri;
    }
}
