package com.chinazmob.unlockearn.base;

import com.chinazmob.unlockearn.AppConfig;
import com.chinazmob.unlockearn.AppInfo;
import com.chinazmob.unlockearn.Interface.ILoginCallback;
import com.chinazmob.unlockearn.Interface.IPostCallback;
import com.chinazmob.unlockearn.common.FileUtils;
import com.chinazmob.unlockearn.common.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 晓宇 on 2015/3/1.
 */
public class Account {
    private static String FUN_CODE_INIT = "101001";
    private static final int RET_CODE_LOGIN_SUCCESS = 200;
    private static final int RET_CODE_DATA_NO_REVISE = 300;
    private static final int RET_CODE_INVALID_PARAM = 400;
    private static final int RET_CODE_NEED_UPDATE = 401;
    private static final int RET_CODE_IP_COUNT_LIMIT = 402;
    private static final int RET_CODE_IMSI_COUNT_LIMIT = 403;
    private static final int RET_CODE_ACCOUNT_FORBID = 404;
    private static final int RET_CODE_PASSWD_ERR = 405;
    private static final int RET_CODE_UNREGISTER = 406;
    private static final int RET_CODE_STOP_ADVERT = 407;
    private static final int RET_CODE_UNDOWNLOAD_ADVERT = 408;
    private static final int RET_CODE_SETUP_FULL = 409;
    private static final int RET_CODE_REDISGRATION_FULL = 410;

    private static String FUN_CODE_GET_ACCOUNT_INFO = "101002";

    private static volatile Account INSTANCE = new Account();
    private static String TOKEN = null;

    public static Account getIntstance() {
        return INSTANCE;
    }

    private boolean misLogin = false;

    public boolean checkedRegister() {
        boolean isRegister = false;
        if (FileUtils.checkFileExists(PathManager.getToken())) {
            isRegister = true;
        }
        return isRegister;
    }

    public boolean checkedLogin() {
        return misLogin;
    }

    public void register(String passwd, String recommended, final ILoginCallback callback) {
        List<String> parms = new LinkedList<>();
        NetManager.getInstance().putParam(parms, "IMEI", DeviceInfo.getImei());
        NetManager.getInstance().putParam(parms, "IMSI", DeviceInfo.getImsi());
        NetManager.getInstance().putParam(parms, "AndroidId", DeviceInfo.getAndroidId(AppConfig.getContext()));
        NetManager.getInstance().putParam(parms, "Phone", DeviceInfo.getPhoneNum());
        NetManager.getInstance().putParam(parms, "Version", AppInfo.getAppVersion());
        NetManager.getInstance().putParam(parms, "SysVer", DeviceInfo.getAndroidVersion());
        NetManager.getInstance().putParam(parms, "Model", DeviceInfo.getPhoneModel());
        NetManager.getInstance().putParam(parms, "NetType", String.valueOf(DeviceInfo.getNetWorkType(AppConfig.getContext()).ordinal()));
        NetManager.getInstance().putParam(parms, "PassWord", passwd);
        NetManager.getInstance().putParam(parms, "Referral", recommended);
        NetManager.getInstance().putParam(parms, "Channel", AppConfig.CHANNEL_ID);

        String requestParm = NetManager.getInstance().getFunRequestData(FUN_CODE_INIT, StringUtils.convertListToString(parms));

        NetManager.getInstance().post("MSG", requestParm, new IPostCallback() {
            @Override
            public void success(String status) {
                try {
                    JSONObject dataJson = new JSONObject(status);
                    String code = dataJson.getString("Code");
                    switch (Integer.valueOf(code)) {
                        case RET_CODE_LOGIN_SUCCESS:
                            JSONObject param = dataJson.getJSONObject("Param");
                            TOKEN = param.getString("Token");
                            UserInfo userInfo = new UserInfo();
                            String name = param.getString("Name");
                            String sex = param.getString("Six");
                            String birthDay = param.getString("Birthday");
                            String state = param.getString("State");
                            String money = param.getString("Money");
                            String totalMoney = param.getString("TotalMoney");
                            String phone = param.getString("Phone");
                            String todayMoney = param.getString("TodayMoney");
                            String rightSwipBaseCount = param.getString("RightSwipBaseCount");
                            String rightSwipCount = param.getString("RightSwipCount");
                            String rightSwipReward = param.getString("RightSwipReward");
                            String rightSwipBaseHour = param.getString("RightSwipBaseHour");
                            userInfo.setName(name);
                            userInfo.setSex(sex);
                            userInfo.setBirthday(birthDay);
                            userInfo.setState(state);
                            userInfo.setMoney(money);
                            userInfo.setTotalMoney(totalMoney);
                            userInfo.setTodayMoney(todayMoney);
                            userInfo.setPhone(phone);
                            userInfo.setRightSwipBaseCount(rightSwipBaseCount);
                            userInfo.setRightSwipBaseHour(rightSwipBaseHour);
                            userInfo.setRightSwipCount(rightSwipCount);
                            userInfo.setRightSwipReward(rightSwipReward);
                            DataBaseManager.getInstance(AppConfig.getContext()).open();
                            DataBaseManager.getInstance(AppConfig.getContext()).updateUserInfo(0, userInfo);
                            DataBaseManager.getInstance(AppConfig.getContext()).close();
                            callback.success();
                            break;
                        case RET_CODE_PASSWD_ERR:
                            callback.passwdErr();
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    DataBaseManager.getInstance(AppConfig.getContext()).close();
                    callback.exception();
                }
            }

            @Override
            public void failed() {
                callback.netErr();
            }
        });
    }

    public void initAccountInfo(String token) {
        List<String> parms = new LinkedList<>();
        String requestParm = NetManager.getInstance().getFunRequestData(FUN_CODE_GET_ACCOUNT_INFO, StringUtils.convertListToString(parms));
        NetManager.getInstance().post("MSG", requestParm, new IPostCallback() {
            @Override
            public void success(String status) {
                String test = status;
            }

            @Override
            public void failed() {

            }
        });
    }
}
