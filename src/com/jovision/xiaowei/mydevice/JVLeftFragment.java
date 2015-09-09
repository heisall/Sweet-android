
package com.jovision.xiaowei.mydevice;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jovision.AppConsts;
import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.aboutus.JVAboutUsActivity;
import com.jovision.xiaowei.album.JVAlbumActivity;
import com.jovision.xiaowei.login.JVLoginActivity;
import com.jovision.xiaowei.service.JVServiceActivity;
import com.jovision.xiaowei.setting.JVSettingActivity;
import com.jovision.xiaowei.utils.MyActivityManager;
import com.jovision.xiaowei.web.JVWebViewActivity;

/**
 * 左侧工具界面
 */
public class JVLeftFragment extends Fragment {

    private View currentView;

    /*********** view ************/
    private TextView usernameTV;
    private ListView leftLV;
    private Button logoutBtn;
    

    /*********** data ************/
    private LeftAdapter leftAdapter;
    private String[] leftFuncArray;
    private int[] leftImgIdArray = {
            R.drawable.icon_back, 
            R.drawable.icon_poweroff, 
            R.drawable.icon_back,
            R.drawable.icon_poweroff, 
            R.drawable.icon_back
    };

    public View getCurrentView() {
        return currentView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.left_fragment_layout,
                container, false);
        usernameTV = (TextView) currentView.findViewById(R.id.username_textview);
        usernameTV.setText(((BaseActivity)getActivity()).statusHashMap.get(AppConsts.USER_NAME));
        
                
        leftFuncArray = getResources().getStringArray(R.array.array_left_func);
        leftLV = (ListView) currentView.findViewById(R.id.left_listview);
        logoutBtn = (Button) currentView.findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(mOnClickListener);

        leftAdapter = new LeftAdapter(getActivity());
        leftAdapter.setData(leftFuncArray, leftImgIdArray);
        leftLV.setAdapter(leftAdapter);
        leftLV.setOnItemClickListener(mOnItemClickListener);
        return currentView;
    }

    /**
     * 按钮事件
     */
    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            ((BaseActivity)getActivity()).statusHashMap.clear();
            MyActivityManager.getActivityManager().popAllActivityExceptOne(JVLoginActivity.class);
            Intent intent = new Intent(getActivity(), JVLoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

    };

    /**
     * 列表点击事件
     */
    OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
            Intent intent = new Intent();
            switch (index) {
                case 0: {// 我的服务
                    intent.setClass(getActivity(), JVServiceActivity.class);
                    break;
                }
                case 1: {// 我的相册
                    intent.setClass(getActivity(), JVAlbumActivity.class);
                    break;
                }
                case 2: {// 设置中心
                    intent.setClass(getActivity(), JVSettingActivity.class);
                    break;
                }
                case 3: {// 帮助
                    intent.setClass(getActivity(), JVWebViewActivity.class);
                    break;
                }
                case 4: {// 关于
                    intent.setClass(getActivity(), JVAboutUsActivity.class);
                    break;
                }
            }
            getActivity().startActivity(intent);
        }

    };

    // ((JVMainActivity) getActivity()).getSlidingPaneLayout().closePane();
}
