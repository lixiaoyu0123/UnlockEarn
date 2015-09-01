package com.chinazmob.unlockearn.Interface;

/**
 * Created by 晓宇 on 2015/3/30.
 */
public interface ILoginCallback {
    void success();
    void passwdErr();
    void netErr();
    void exception();
}
