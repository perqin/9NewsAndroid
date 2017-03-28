package cn.ninesmart.ninenews.utils;

import cn.ninesmart.ninenews.BuildConfig;

/**
 * Author   : perqin
 * Date     : 17-2-7
 */

public final class AppInfo {
    public static final String APP_ID = BuildConfig.APP_ID;
    public static final String APP_VERSION_NAME = BuildConfig.VERSION_NAME;
    public static final int APP_VERSION_CODE = BuildConfig.VERSION_CODE;

    private AppInfo() {
        // Prevent construction
    }
}
