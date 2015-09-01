package com.chinazmob.unlockearn.base;

import android.content.Context;
import android.content.Intent;

import com.chinazmob.unlockearn.ui.Main;

/**
 * Created by 晓宇 on 2015/3/29.
 */
public class UiManager {

    public static void showMainUi(Context context) {
        Intent intent = new Intent(context, Main.class);
        context.startActivity(intent);
    }
}
