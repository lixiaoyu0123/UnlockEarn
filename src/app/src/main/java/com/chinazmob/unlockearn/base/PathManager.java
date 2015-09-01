package com.chinazmob.unlockearn.base;

import android.os.Environment;

import com.chinazmob.unlockearn.common.FileUtils;

/**
 * Created by 晓宇 on 2015/3/4.
 */
public class PathManager {
    private static String MAIN_DIR = "com_chinazmob";
    private static String PRO_DIR = MAIN_DIR + "/unlockearn";
    private static String CACHE_DIR = PRO_DIR + "/cache";
    private static String TOKEN_DIR = PRO_DIR + "/token";
    private static String SDCARD_PATH = null;

    public static String getSdcardPath() {
        if (SDCARD_PATH == null) {
            SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return SDCARD_PATH;
    }

    public static String getTokenPath() {
        String path = getSdcardPath() + "/" + TOKEN_DIR;
        if (!FileUtils.checkPathExists(path)) {
            FileUtils.createPath(path);
        }
        return path;
    }

    public static String getToken(){
        return getTokenPath() + "/token";
    }
}
