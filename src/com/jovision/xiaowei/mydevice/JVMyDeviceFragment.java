/**   
 * 我的设备主界面 
 */

package com.jovision.xiaowei.mydevice;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jovision.AppConsts;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.adddevice.JVAddDeviceActivity;
import com.jovision.xiaowei.play.JVPlayActivity;
import com.jovision.xiaowei.web.JVWebViewActivity;

import java.util.ArrayList;

/**
 * 我的设备设备列表界面
 */
public class JVMyDeviceFragment extends Fragment {
    public static final int REFRESH_DELAY = 2000;

    /********* view ***********/
    private View currentView;
    // topBar
    private Button leftBtn;
    private Button rightBtn;
    private TextView menuTV;
    // 下拉刷新的view
    private SwipeRefreshLayout mSwipeLayout;
    private LinearLayout functionLayout;
    private GridView myFuncGV;
    private ListView myDevLV;
    private LinearLayout addNewDevLayout;

    /********* data ***********/
    private ArrayList<Device> myDevList = new ArrayList<Device>();
    private MyDeviceAdapter myDeviceAdapter;
    private ArrayList<Function> myFuncList = new ArrayList<Function>();
    private MyFuncGridAdapter myFuncGridAdapter;

    public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
        currentView.setLayoutParams(layoutParams);
    }

    public FrameLayout.LayoutParams getCurrentViewParams() {
        return (LayoutParams) currentView.getLayoutParams();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.mydevice_fragment_layout,
                container, false);

        leftBtn = (Button) currentView.findViewById(R.id.left_btn);
        leftBtn.setOnClickListener(mOnClickListener);
        rightBtn = (Button) currentView.findViewById(R.id.right_btn);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setBackground(getResources().getDrawable(R.drawable.icon_add));
        rightBtn.setOnClickListener(mOnClickListener);
        menuTV = (TextView) currentView.findViewById(R.id.center_textview);
        menuTV.setText(R.string.xiaowei_cloud);
        mSwipeLayout = (SwipeRefreshLayout) currentView.findViewById(R.id.swipe_refresh_layout);
        mSwipeLayout.setOnRefreshListener(mOnRefreshListener);
        mSwipeLayout.setColorScheme(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        functionLayout = (LinearLayout) layoutInflater.inflate(R.layout.mydevice_function_layout,
                null);

        myFuncGV = (GridView) functionLayout.findViewById(R.id.mydev_gridview);
        myDevLV = (ListView) currentView.findViewById(R.id.mydev_listview);
        addNewDevLayout = (LinearLayout) currentView.findViewById(R.id.add_new_dev_layout);
        addNewDevLayout.setOnClickListener(mOnClickListener);
        
        if (!AppConsts.APP_CUSTOM) {
            myDevLV.addHeaderView(functionLayout);
        }
        

        Function func1 = new Function();
        func1.setFunctionName("商城");
        func1.setFunctionLink("http://www.baidu.com");

        Function func2 = new Function();
        func2.setFunctionName("社区");
        func2.setFunctionLink("http://www.360.com");

        Function func3 = new Function();
        func3.setFunctionName("直播");
        func3.setFunctionLink("http://www.sina.com");

        myFuncList.add(func1);
        myFuncList.add(func2);
        myFuncList.add(func3);

        myFuncGridAdapter = new MyFuncGridAdapter(getActivity());
        myFuncGridAdapter.setData(myFuncList);
        myFuncGV.setAdapter(myFuncGridAdapter);
        myFuncGV.setOnItemClickListener(mGridOnItemClickListener);

        Device dev1 = new Device("", 9101, "B", 175926366, "abc", "123", true, 1, 0, "B175926366");
        dev1.setFullNo("B175926366");
        Device dev2 = new Device("", 9101, "B", 127713027, "abc", "123", true, 1, 0, "B127713027");
        dev2.setFullNo("B127713027");
        
        myDevList.add(dev1);
        myDevList.add(dev2);
        myDeviceAdapter = new MyDeviceAdapter(getActivity());
        myDeviceAdapter.setData(myDevList);
        myDevLV.setAdapter(myDeviceAdapter);
        myDevLV.setOnItemClickListener(mListOnItemClickListener);

        
        return currentView;
    }

    /**
     * 按钮事件
     */
    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.left_img: {

                    break;
                }
                case R.id.add_new_dev_layout://添加一个新设备
                case R.id.right_btn: {
                    Intent addIntent = new Intent(getActivity(), JVAddDeviceActivity.class);
                    getActivity().startActivity(addIntent);
                    break;
                }
            }
        }

    };

    /**
     * GridView click event
     */
    OnItemClickListener mGridOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {

            Intent linkIntent = new Intent(getActivity(), JVWebViewActivity.class);
            linkIntent.putExtra("linkUrl", myFuncList.get(index).getFunctionLink());
            getActivity().startActivity(linkIntent);

        }

    };

    /**
     * ListView click event
     */
    OnItemClickListener mListOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
            Intent playIntent = new Intent(getActivity(), JVPlayActivity.class);
            getActivity().startActivity(playIntent);
            getActivity().overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }

    };

    /**
     * 下拉刷新
     */
    SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            // 模拟刷新
            new AsyncTask<Integer, Integer, Integer>() {

                @Override
                protected Integer doInBackground(Integer... params) {
                    try {
                        // 睡眠2秒
                        Thread.sleep(4000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                protected void onPostExecute(Integer result) {
                    myDeviceAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                };
            }.execute();
        }

    };

}
