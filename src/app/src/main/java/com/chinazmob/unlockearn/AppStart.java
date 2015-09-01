package com.chinazmob.unlockearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.chinazmob.unlockearn.base.DataBaseManager;
import com.chinazmob.unlockearn.base.LockService;
import com.chinazmob.unlockearn.base.ThreadManager;
import com.chinazmob.unlockearn.base.UiManager;


public class AppStart extends Activity {

    final int logShowTime = 800;
    Handler mhander = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        mhander = new Handler();
        AppConfig.setAppContext(getApplicationContext());
        //开启服务
        startService(new Intent(AppStart.this, LockService.class));
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mhander.postDelayed(new Splashhandler(), logShowTime);
    }

    class Splashhandler implements Runnable {

        public void run() {
            UiManager.showMainUi(AppStart.this);
            finish();
        }

    }

    private void init(){
        ThreadManager.getInstance().getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                DataBaseManager.getInstance(AppConfig.getContext());
            }
        });
    }
}
