
package com.jovision.xiaowei.aboutus;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.utils.Url;

public class JVAboutUsActivity extends BaseActivity {

    private ListView aboutusLV;
    private AboutUsAdapter aboutUsAdapter;
    private String[] aboutArray;
    
    private TextView termOfService;//服务条款
    private TextView website;//官网
    private TextView customerService;//客服

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
        setContentView(R.layout.aboutus_layout);
        leftBtn = (Button) findViewById(R.id.left_btn);
        rightBtn = (Button) findViewById(R.id.right_btn);
        currentMenu = (TextView) findViewById(R.id.center_textview);
        currentMenu.setText(R.string.left_aboutus);
        leftBtn.setOnClickListener(mOnClickListener);

        aboutArray = getResources().getStringArray(R.array.array_aboutus);
        aboutUsAdapter = new AboutUsAdapter(JVAboutUsActivity.this);
        aboutUsAdapter.setData(aboutArray, null);
        aboutusLV = (ListView) findViewById(R.id.aboutus_listview);
        aboutusLV.setAdapter(aboutUsAdapter);
        termOfService = (TextView) findViewById(R.id.terms_of_service_textview);//服务条款
        website = (TextView) findViewById(R.id.website_textview);//官网
        customerService = (TextView) findViewById(R.id.customer_service_textview);//客服
        termOfService.setOnClickListener(mOnClickListener);
        website.setOnClickListener(mOnClickListener);
        customerService.setOnClickListener(mOnClickListener);
    }

    /**
     * 点击事件
     */
    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.left_btn: {// top左键
                    backMethod();
                    break;
                }
                case R.id.right_btn: {// top右键
                    break;
                }
                case R.id.terms_of_service_textview:{//服务条款
                    break;
                }
                case R.id.website_textview:{//官网
                    break;
                }
                case R.id.customer_service_textview:{//拨打客服电话
                    callService();
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
    private void backMethod() {
        JVAboutUsActivity.this.finish();
    }

    /**
     * 拨打客服电话
     */
    private void callService(){
        Intent phoneIntent = new Intent("android.intent.action.CALL",
        Uri.parse("tel:"+Url.CALL_400));
        JVAboutUsActivity.this.startActivity(phoneIntent);
    }

    @Override
    protected void saveSettings() {

    }

    @Override
    protected void freeMe() {

    }

}
