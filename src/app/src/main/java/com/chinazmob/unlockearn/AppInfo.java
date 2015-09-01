package com.chinazmob.unlockearn;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by 晓宇 on 2015/3/1.
 */
public class AppInfo {
    private static String APP_PACKAGE_NAME = null;
    private static String APP_VERSION = null;

    public static String getAppPackageName() {
        if (APP_PACKAGE_NAME == null) {
            APP_PACKAGE_NAME = AppConfig.getContext().getPackageName();
        }
        return APP_PACKAGE_NAME;
    }

    public static String getAppVersion() {
        if (APP_VERSION == null) {
            try {
                PackageManager manager = AppConfig.getContext().getPackageManager();
                PackageInfo info = manager.getPackageInfo(AppConfig.getContext().getPackageName(), 0);
                APP_VERSION = info.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return APP_VERSION;
    }

}
