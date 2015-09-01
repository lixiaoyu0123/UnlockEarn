package com.chinazmob.unlockearn.base;

/**
 * Created by 晓宇 on 2015/3/29.
 */
public class UserInfo {
    private static UserInfo INSTANCE = new UserInfo();

    public static UserInfo getInstance() {
        return INSTANCE;
    }

    private String mname;
    private String msex;
    private String mbirthday;
    private String mstate;
    private String mmoney;
    private String mtodayMoney;
    private String mphone;
    private String mtotalMoney;
    private String mrightSwipBaseCount;
    private String mrightSwipCount;
    private String mrightSwipReward;
    private String mrightSwipBaseHour;

    public String getName() {
        return mname;
    }

    public void setName(String name) {
        mname = name;
    }

    public String getSex() {
        return msex;
    }

    public void setSex(String sex) {
        msex = sex;
    }

    public String getBirthday() {
        return mbirthday;
    }

    public void setBirthday(String birthday) {
        mbirthday = birthday;
    }

    public String getState() {
        return mstate;
    }

    public void setState(String state) {
        mstate = state;
    }

    public String getMoney() {
        return mmoney;
    }

    public void setMoney(String money) {
        mmoney = money;
    }

    public String getTodayMoney() {
        return mtodayMoney;
    }

    public void setTodayMoney(String todayMoney) {
        mtodayMoney = todayMoney;
    }

    public String getPhone() {
        return mphone;
    }

    public void setPhone(String phone) {
        mphone = phone;
    }

    public String getTotalMoney() {
        return mtotalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        mtotalMoney = totalMoney;
    }

    public String getRightSwipBaseCount() {
        return mrightSwipBaseCount;
    }

    public void setRightSwipBaseCount(String rightSwipBaseCount) {
        mrightSwipBaseCount = rightSwipBaseCount;
    }

    public String getRightSwipCount() {
        return mrightSwipCount;
    }

    public void setRightSwipCount(String rightSwipCount) {
        mrightSwipCount = rightSwipCount;
    }

    public String getRightSwipReward() {
        return mrightSwipReward;
    }

    public void setRightSwipReward(String rightSwipReward) {
        mrightSwipReward = rightSwipReward;
    }

    public String getRightSwipBaseHour() {
        return mrightSwipBaseHour;
    }

    public void setRightSwipBaseHour(String rightSwipBaseHour) {
        mrightSwipBaseHour = rightSwipBaseHour;
    }

}
