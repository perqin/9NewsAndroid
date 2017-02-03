package cn.ninesmart.ninenews.utils;

import android.os.Build;

/**
 * Author   : perqin
 * Date     : 17-2-3
 */

public final class DeviceUtils {
    private DeviceUtils() {
        // Prevent construction
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }
}
