
package com.jovision.xiaowei.welcome;

import android.content.Intent;
import android.os.Handler;
import android.test.AutoLoad;
import android.widget.ImageView;

import com.jovision.AppConsts;
import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.login.JVLoginActivity;
import com.jovision.xiaowei.utils.ConfigUtil;
import com.jovision.xiaowei.utils.MobileUtil;
import com.jovision.xiaowei.utils.MyLog;
import com.jovision.xiaowei.utils.MySharedPreference;

public class JVWelcomeActivity extends BaseActivity {
    static {
        AutoLoad.foo();
    }

    private static final String TAG = "JVWelcomeActivity";
    private Handler welcomeHandler;
    private ImageView welcomeImg;
    private boolean firstUse = true;

    @Override
    public void onHandler(int what, int arg1, int arg2, Object obj) {

    }

    @Override
    public void onNotify(int what, int arg1, int arg2, Object obj) {

    }

    @Override
    protected void initSettings() {
        MySharedPreference.init(JVWelcomeActivity.this);
        AppConsts.CURRENT_LAN = ConfigUtil.getLanguage(JVWelcomeActivity.this);
        MobileUtil.creatAllFolder();

        initSDKThread.start();
        welcomeHandler = new Handler();
        welcomeHandler.postDelayed(startThread, 4000);
    }

    @Override
    protected void initUi() {
        setContentView(R.layout.welcome_layout);
        welcomeImg = (ImageView) findViewById(R.id.welcome_img);

    }

    /**
     * 初始化SDK线程
     */
    Thread initSDKThread = new Thread() {

        @Override
        public void run() {
            super.run();
            int region = ConfigUtil.getSinaRegion();
            boolean initCloudSDK = ConfigUtil.initCloudSDK(JVWelcomeActivity.this);
        }

    };

    /**
     * 开始线程
     */
    Thread startThread = new Thread() {

        @Override
        public void run() {
            super.run();
            if (MySharedPreference.getBoolean(AppConsts.FIRST_OPEN_APP, true)) {
                Intent startIntent = new Intent(JVWelcomeActivity.this, JVGuidActivity.class);
                JVWelcomeActivity.this.startActivity(startIntent);
                JVWelcomeActivity.this.finish();
            } else {
                Intent startIntent = new Intent(JVWelcomeActivity.this, JVLoginActivity.class);
                JVWelcomeActivity.this.startActivity(startIntent);
                JVWelcomeActivity.this.finish();
            }

        }

    };

    @Override
    protected void saveSettings() {

    }

    @Override
    protected void freeMe() {

    }

}
