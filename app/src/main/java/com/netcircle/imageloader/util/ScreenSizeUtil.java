package com.netcircle.imageloader.util;

import android.app.Activity;

/**
 * Created by sweetgirl on 2017/12/5
 */

public class ScreenSizeUtil {

    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }
    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }
}
