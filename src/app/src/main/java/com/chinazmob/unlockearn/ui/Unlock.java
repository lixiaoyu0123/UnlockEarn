package com.chinazmob.unlockearn.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.chinazmob.unlockearn.R;

/**
 * Created by 晓宇 on 2015/2/10.
 */
public class Unlock extends Activity {
    private UnlockView mlockView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lock_screen);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    //使home物理键失效
    @Override
    public void onAttachedToWindow() {
       // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onAttachedToWindow();
    }

    //使back键，音量加减键失效
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return disableKeycode(keyCode, event);
    }

    private boolean disableKeycode(int keyCode, KeyEvent event)
    {
        int key = event.getKeyCode();
        switch (key)
        {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
