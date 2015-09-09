
package com.jovision.xiaowei.login;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jovision.AppConsts;
import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.mydevice.JVMainActivity;
import com.jovision.xiaowei.utils.ConfigUtil;
import com.jovision.xiaowei.utils.MyLog;
import com.jovision.xiaowei.welcome.JVWelcomeActivity;

public class JVLoginActivity extends BaseActivity {
    private static final String TAG = "JVLoginActivity";

    private EditText userNameET;
    private EditText userPwdET;
    private Button loginBtn;
    private TextView registerTV;
    private TextView findPassTV;

    @Override
    public void onHandler(int what, int arg1, int arg2, Object obj) {

    }

    @Override
    public void onNotify(int what, int arg1, int arg2, Object obj) {

    }

    @Override
    protected void initSettings() {

    }

    @Override
    protected void initUi() {
        setContentView(R.layout.login_layout);
        userNameET = (EditText) findViewById(R.id.username_edittext);
        userPwdET = (EditText) findViewById(R.id.password_edittext);

        loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(mOnClickListener);

        registerTV = (TextView) findViewById(R.id.register_textview);
        registerTV.setOnClickListener(mOnClickListener);
        findPassTV = (TextView) findViewById(R.id.findpass_textview);
        findPassTV.setOnClickListener(mOnClickListener);

    }

    /**
     * 按钮点击事件
     */
    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_button: {// 登陆
                    if ("".equalsIgnoreCase(userNameET.getText().toString())) {
                        showTextToast(R.string.login_username_null);
                    } else if ("".equalsIgnoreCase(userPwdET.getText().toString())) {
                        showTextToast(R.string.login_password_null);
                    } else {
                        statusHashMap.put(AppConsts.USER_NAME, userNameET.getText().toString());
                        Intent startIntent = new Intent(JVLoginActivity.this, JVMainActivity.class);
                        JVLoginActivity.this.startActivity(startIntent);
                        JVLoginActivity.this.finish();
                    }

                    break;
                }
                case R.id.register_textview: {// 注册
                    Intent registerIntent = new Intent(JVLoginActivity.this,
                            JVRegisterActivity.class);
                    JVLoginActivity.this.startActivity(registerIntent);
                    break;
                }
                case R.id.findpass_textview: {// 找回密码
                    Intent findPassIntent = new Intent(JVLoginActivity.this,
                            JVFindPassActivity.class);
                    JVLoginActivity.this.startActivity(findPassIntent);
                    break;
                }
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
