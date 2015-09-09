
package com.jovision.xiaowei;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jovision.xiaowei.utils.MobileUtil;
import com.jovision.xiaowei.utils.MyActivityManager;

import java.util.HashMap;

/**
 * 抽象的活动基类，所有活动都应该继承这个类，并实现其抽象方法和接口
 * 
 * @author neo
 */
public abstract class BaseActivity extends Activity implements IHandlerNotify,
        IHandlerLikeNotify {
    public HashMap<String, String> statusHashMap;

    /** 初始化设置，不要在这里写费时的操作 */
    protected abstract void initSettings();

    /** 初始化界面，不要在这里写费时的操作 */
    protected abstract void initUi();

    /** 保存设置，不要在这里写费时的操作 */
    protected abstract void saveSettings();

    /** 释放资源、解锁、删除不用的对象，不要在这里写费时的操作 */
    protected abstract void freeMe();

    protected ProgressDialog proDialog;
    protected Toast toast;

    private IHandlerNotify notify = this;
    protected MyHandler handler = new MyHandler(this);

    /** view **/
    public Button leftBtn;
    public Button rightBtn;
    public TextView currentMenu;

    protected static class MyHandler extends Handler {

        private BaseActivity activity;

        public MyHandler(BaseActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            activity.notify.onHandler(msg.what, msg.arg1, msg.arg2, msg.obj);
            super.handleMessage(msg);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyActivityManager.getActivityManager().pushActivity(this);

        ((MainApplication) getApplication()).setCurrentNotifyer(this);

        statusHashMap = ((MainApplication) getApplicationContext())
                .getStatusHashMap();
        initSettings();
        initUi();
    }

    @Override
    protected void onDestroy() {
        freeMe();
        MyActivityManager.getActivityManager().popActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        saveSettings();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 弹dialog
     * 
     * @param msg
     */
    public void createDialog(String msg, boolean cancel) {

        if (null == msg || "".equalsIgnoreCase(msg)) {
            msg = getResources().getString(R.string.waiting);
        }
        try {
            if (null != BaseActivity.this && !BaseActivity.this.isFinishing()) {
                if (null == proDialog) {
                    proDialog = new ProgressDialog(BaseActivity.this);
                }
                proDialog.setMessage(msg);
                if (null != proDialog) {
                    if (null != BaseActivity.this
                            && !BaseActivity.this.isFinishing()) {
                        proDialog.show();
                        proDialog.setCancelable(cancel);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭dialog
     * 
     * @param dialog
     */
    public void dismissDialog() {
        if (null != proDialog) {
            proDialog.dismiss();
            proDialog = null;
        }
    }

    /**
     * 弹系统消息
     * 
     * @param context
     * @param id
     */
    public void showTextToast(int id) {
        String msg = getApplication().getResources().getString(id);
        if (toast == null) {
            toast = Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 弹系统消息
     * 
     * @param context
     * @param msg
     */
    public void showTextToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    // /**
    // * 没有网络提示 打开设置网络界面
    // */
    // public void alertNetDialog() {
    // statusHashMap.put(Consts.HAS_LOAD_DEMO, "false");
    // if (null != netErrorDialog && netErrorDialog.isShowing()) {
    // return;
    // }
    // try {
    // // 提示对话框
    // AlertDialog.Builder builder = new Builder(this);
    // builder.setTitle(R.string.tips)
    // .setMessage(R.string.network_error)
    // .setPositiveButton(R.string.setting,
    // new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog,
    // int which) {
    //
    // if (android.os.Build.VERSION.SDK_INT > 10) {
    // startActivity(new Intent(
    // android.provider.Settings.ACTION_SETTINGS));
    // } else {
    // startActivity(new Intent(
    // android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    // }
    //
    // }
    // })
    // .setNegativeButton(R.string.cancel,
    // new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog,
    // int which) {
    // dialog.dismiss();
    // }
    // });
    //
    // netErrorDialog = builder.create();
    // netErrorDialog.show();
    // } catch (ActivityNotFoundException e) {
    // showTextToast(R.string.network_error);
    // e.printStackTrace();
    // }
    //
    // }
    //
    /**
     * 判断是否有sd卡
     * 
     * @param minSize 最小容量
     * @param alert 是否弹提示
     * @return
     */
    public boolean hasSDCard(int minSize, boolean alert) {
        boolean canSave = true;
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            if (alert) {
                showTextToast(R.string.sdcard_out_memery);
            }
            canSave = false;
        } else {
            if (MobileUtil.getSDFreeSize() < minSize) {
                if (alert) {
                    showTextToast(R.string.sdcard_notenough);
                }
                canSave = false;
            }
        }
        return canSave;
    }

}
