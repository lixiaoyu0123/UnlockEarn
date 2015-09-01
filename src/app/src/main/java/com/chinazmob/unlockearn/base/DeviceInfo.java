package com.chinazmob.unlockearn.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.chinazmob.unlockearn.AppConfig;

/**
 * Created by 晓宇 on 2015/3/1.
 */
public class DeviceInfo {
    // 手机信息
    public static String PHONE_MODEL = null; // 手机型号
    public static String PHONE_NUM = null; //手机号码
    public static int SDK_VERSION = 0; // sdk版本，整数

    // app信息
    private static String APP_NAME = null; // 应用版本名 在AndroidManifest.xml，package

    private static String ANDROID_VERSION = null; // Android版本
    private static String ANDROID_ID = null;
    private static String BUILDE_NUMBER = null; // 版本号（一系列数据的组合）
    private static String IMEI = null; // IMEI
    private static String IMSI = null; // sim卡标识
    private static String ICCID = null; // sim卡背后的序列号
    private static NET_WORK_TYPES NET_WORK_TYPE = null; //网络配置 WIFI或者2G或者3G或者4G

    enum NET_WORK_TYPES {
        NETWORKTYPE_INVALID, NETWORKTYPE_WIFI, NETWORKTYPE_2G, NETWORKTYPE_3G, NETWORKTYPE_WAP
    }


    public static String getPhoneModel() {
        if (PHONE_MODEL == null) {
            PHONE_MODEL = Build.MODEL;
        }
        return PHONE_MODEL;
    }

    public static String getPhoneNum() {
        if (PHONE_NUM == null) {
            TelephonyManager tm = (TelephonyManager) AppConfig.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                PHONE_NUM = tm.getLine1Number();
            }
        }
        return PHONE_NUM;
    }

    public static int getSdkVersion() {
        if (SDK_VERSION == 0) {
            SDK_VERSION = Build.VERSION.SDK_INT;
        }
        return SDK_VERSION;
    }

    public static String getAppName() {
        if (APP_NAME == null) {
            Context context = AppConfig.getContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                APP_NAME = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return APP_NAME;
    }

    public static String getAndroidVersion() {
        if (ANDROID_VERSION == null) {
            ANDROID_VERSION = Build.VERSION.RELEASE;
        }
        return ANDROID_VERSION;
    }

    public static String getAndroidId(Context context){
        if(ANDROID_ID == null){
            ANDROID_ID = android.provider.Settings.Secure.getString(
                    context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        }
        return ANDROID_ID;
    }

    public static String getBuildNumber() {
        if (BUILDE_NUMBER == null) {
            BUILDE_NUMBER = Build.DISPLAY;
        }
        return BUILDE_NUMBER;
    }

    public static String getImei() {
        if (IMEI == null) {
            TelephonyManager tm = (TelephonyManager) AppConfig.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                IMEI = tm.getDeviceId();
            }
        }
        return IMEI;
    }

    public static String getImsi() {
        if (IMSI == null) {
            TelephonyManager tm = (TelephonyManager) AppConfig.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                IMSI = tm.getSubscriberId();
            }
        }
        return IMSI;
    }

    public static String getIccid() {
        if (ICCID == null) {
            TelephonyManager tm = (TelephonyManager) AppConfig.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                ICCID = tm.getSimSerialNumber();
            }
        }
        return ICCID;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #},{@link #},          *{@link #},{@link #}* <p>{@link #}
     */

    public static NET_WORK_TYPES getNetWorkType(Context context) {
        if (NET_WORK_TYPE == null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                String type = networkInfo.getTypeName();

                if (type.equalsIgnoreCase("WIFI")) {
                    NET_WORK_TYPE = NET_WORK_TYPES.NETWORKTYPE_WIFI;
                } else if (type.equalsIgnoreCase("MOBILE")) {
                    String proxyHost = android.net.Proxy.getDefaultHost();

                    NET_WORK_TYPE = TextUtils.isEmpty(proxyHost)
                            ? (isFastMobileNetwork(context) ? NET_WORK_TYPES.NETWORKTYPE_3G : NET_WORK_TYPES.NETWORKTYPE_2G)
                            : NET_WORK_TYPES.NETWORKTYPE_WAP;
                }
            } else {
                NET_WORK_TYPE = NET_WORK_TYPES.NETWORKTYPE_INVALID;
            }
        }
        return NET_WORK_TYPE;
    }
}
