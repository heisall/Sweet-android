
package com.jovision.xiaowei.mydevice;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.utils.MyLog;

/**
 * 我的设备列表界面
 */
public class JVMainActivity extends BaseActivity {
    private static final String TAG = "JVMainActivity";

	private SlidingPaneLayout slidingPaneLayout;
	private JVLeftFragment leftFragment;
	private JVMyDeviceFragment contentFragment;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private int maxMargin = 0;

	@SuppressLint("NewApi")

	/**
	 * @return the slidingPaneLayout
	 */
	public SlidingPaneLayout getSlidingPaneLayout() {
		return slidingPaneLayout;
	}

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
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setContentView(R.layout.main_layout);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanellayout);
        leftFragment = new JVLeftFragment();
        contentFragment = new JVMyDeviceFragment();
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.slidingpane_menu, leftFragment);
        transaction.replace(R.id.slidingpane_content, contentFragment);
        transaction.commit();
        maxMargin = displayMetrics.heightPixels / 10;
        slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                int contentMargin = (int) (slideOffset * maxMargin);
                FrameLayout.LayoutParams contentParams = contentFragment
                        .getCurrentViewParams();
                contentParams.setMargins(0, contentMargin, 0, contentMargin);
                contentFragment.setCurrentViewPararms(contentParams);

                float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
                        / (float) displayMetrics.heightPixels;
                leftFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
                leftFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
                leftFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
                leftFragment.getCurrentView().setPivotY(
                        displayMetrics.heightPixels / 2);
                
                MyLog.v(TAG, "slideOffset="+slideOffset+";scale="+scale);
                leftFragment.getCurrentView().setAlpha(slideOffset);
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });        
    }

    @Override
    protected void saveSettings() {
        
    }

    @Override
    protected void freeMe() {
        
    }

}
