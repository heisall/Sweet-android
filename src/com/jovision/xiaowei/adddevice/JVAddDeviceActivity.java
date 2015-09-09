
package com.jovision.xiaowei.adddevice;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;

public class JVAddDeviceActivity extends BaseActivity {

    private Button wifiBtn;
    private Button lineBtn;

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
        setContentView(R.layout.adddevice_layout);
        
        wifiBtn = (Button) findViewById(R.id.wifi_add_btn);
        lineBtn = (Button) findViewById(R.id.line_add_btn);
        wifiBtn.setOnClickListener(mOnClickListener);
        lineBtn.setOnClickListener(mOnClickListener);
        leftBtn = (Button) findViewById(R.id.left_btn);
        rightBtn = (Button) findViewById(R.id.right_btn);
        currentMenu = (TextView) findViewById(R.id.center_textview);
        currentMenu.setText(R.string.add_dev);
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
                case R.id.wifi_add_btn:{
                    break;
                }
                case R.id.line_add_btn:{
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
        JVAddDeviceActivity.this.finish();
    }

    
    @Override
    protected void saveSettings() {

    }

    @Override
    protected void freeMe() {

    }

}
