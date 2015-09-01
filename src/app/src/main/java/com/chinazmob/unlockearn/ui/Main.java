package com.chinazmob.unlockearn.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chinazmob.unlockearn.Interface.ILoginCallback;
import com.chinazmob.unlockearn.R;
import com.chinazmob.unlockearn.base.Account;


public class Main extends Activity {
    private final int TOOLBAR_COUNTS = 4;
    private Screens[] mscreens = new Screens[TOOLBAR_COUNTS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initToolBar();
        if(Account.getIntstance().checkedRegister()){
//Account.getIntstance().initAccountInfo(NetManager.getInstance().);
        }else{

        }
        Account.getIntstance().register("123456","1",new ILoginCallback() {
            @Override
            public void success() {

            }

            @Override
            public void passwdErr() {

            }

            @Override
            public void netErr() {

            }

            @Override
            public void exception() {

            }
        });
    }

    class Screens {
        public RadioButton mradioButton;
        public View mview;
    }

    private void initToolBar() {
        TextView tv = null;
        mscreens[0] = new Screens();
        mscreens[0].mradioButton = (RadioButton) findViewById(R.id.radio_first);
        mscreens[0].mview = (View) findViewById(R.id.income);
        mscreens[1] = new Screens();
        mscreens[1].mradioButton = (RadioButton) findViewById(R.id.radio_second);
        mscreens[1].mview = (View) findViewById(R.id.cash);
        tv = (TextView) mscreens[1].mview.findViewById(R.id.head_title_text);
        tv.setText(R.string.tool_bar_charge);
        mscreens[2] = new Screens();
        mscreens[2].mradioButton = (RadioButton) findViewById(R.id.radio_third);
        mscreens[2].mview = (View) findViewById(R.id.invite);
        tv = (TextView) mscreens[2].mview.findViewById(R.id.head_title_text);
        tv.setText(R.string.tool_bar_invite);
        mscreens[3] = new Screens();
        mscreens[3].mradioButton = (RadioButton) findViewById(R.id.radio_fourth);
        mscreens[3].mview = (View) findViewById(R.id.setting);
        tv = (TextView) mscreens[3].mview.findViewById(R.id.head_title_text);
        tv.setText(R.string.tool_bar_more);
        tv = null;
        for (int i = 0; i < TOOLBAR_COUNTS; i++) {
            mscreens[i].mradioButton.setOnClickListener(new ToolbarClick());
        }
    }

    private void changeClickStatus(int id) {
        for (int i = 0; i < TOOLBAR_COUNTS; i++) {
            if (mscreens[i].mradioButton.getId() == id) {
                mscreens[i].mradioButton.setChecked(true);
                mscreens[i].mview.setVisibility(View.VISIBLE);
            } else {
                mscreens[i].mradioButton.setChecked(false);
                mscreens[i].mview.setVisibility(View.GONE);
            }
        }
    }

    class ToolbarClick implements View.OnClickListener {
        public void onClick(View v) {
            changeClickStatus(v.getId());
        }
    }
}
