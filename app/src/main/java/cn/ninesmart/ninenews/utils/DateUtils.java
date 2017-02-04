package cn.ninesmart.ninenews.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Author   : perqin
 * Date     : 17-2-4
 */

public final class DateUtils {
    private DateUtils() {
        // Prevent construction
    }

    public static String getHumanDate(long milli) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        return sdf.format(milli);
    }
}
