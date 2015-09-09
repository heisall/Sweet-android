package com.jovision.xiaowei.login;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.service.JVServiceActivity;

public class JVFindPassActivity extends BaseActivity{

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
        setContentView(R.layout.findpass_layout);
        leftBtn = (Button) findViewById(R.id.left_btn);
        rightBtn = (Button) findViewById(R.id.right_btn);
        currentMenu = (TextView) findViewById(R.id.center_textview);
        currentMenu.setText(R.string.findpass);
        leftBtn.setOnClickListener(mOnClickListener);
    }
    
    /**
     * 点击事件
     */
    OnClickListener mOnClickListener = new OnClickListener(){

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.left_btn:{//top左键
                    backMethod();
                    break;
                }
                case R.id.right_btn:{//top右键
                    break;
                }
            }
        }
        
    };
    
    @Override
    public void onBackPressed() {
    }
    /**
     * 返回事件
     */
    private void backMethod(){
        JVFindPassActivity.this.finish();
    }

    @Override
    protected void saveSettings() {
        
    }

    @Override
    protected void freeMe() {
        
    }

}
