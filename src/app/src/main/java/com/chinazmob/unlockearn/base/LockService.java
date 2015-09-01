package com.chinazmob.unlockearn.base;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.chinazmob.unlockearn.ui.Unlock;

/**
 * Created by 晓宇 on 2015/2/10.
 */
public class LockService extends Service {
    private Intent mlockIntent = null;
    private KeyguardManager mkeyguardManager = null;
    private KeyguardManager.KeyguardLock mkeyguardLock = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mlockIntent = new Intent(getBaseContext(), Unlock.class);
        mlockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        registerComponent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterComponent();
        //被销毁时启动自身，保持自身在后台存活
        startService(new Intent(LockService.this, LockService.class));
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    //监听来自用户按Power键点亮点暗屏幕的广播
    private BroadcastReceiver mScreenOnOrOffReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_ON")
                    || intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                mkeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                mkeyguardLock = mkeyguardManager.newKeyguardLock("FxLock");
                //屏蔽手机内置的锁屏
                mkeyguardLock.disableKeyguard();
                //启动该第三方锁屏
                startActivity(mlockIntent);
            }
        }
    };

    //注册广播监听
    public void registerComponent() {
        IntentFilter mScreenOnOrOffFilter = new IntentFilter();
        mScreenOnOrOffFilter.addAction("android.intent.action.SCREEN_ON");
        mScreenOnOrOffFilter.addAction("android.intent.action.SCREEN_OFF");
        LockService.this.registerReceiver(mScreenOnOrOffReceiver, mScreenOnOrOffFilter);
    }

    //解除广播监听
    public void unregisterComponent() {
        if (mScreenOnOrOffReceiver != null) {
            LockService.this.unregisterReceiver(mScreenOnOrOffReceiver);
        }
    }
}
