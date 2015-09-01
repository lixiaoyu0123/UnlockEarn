package com.chinazmob.unlockearn;

import android.content.Context;

/**
 * Created by 晓宇 on 2015/3/1.
 */
public class AppConfig {
    public static final String CHANNEL_ID = "18000";
    public static final String SERVER_URL = "http://zhuhaizhongyi.eicp.net:8001/api.php";
    private static Context APP_CONTEXT = null;
    public  static void setAppContext(Context context){
        APP_CONTEXT = context;
    }
    public static Context getContext(){
        return APP_CONTEXT;
    }
}
