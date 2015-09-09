
package com.jovision.xiaowei;

import android.app.Application;

import com.jovision.xiaowei.utils.MyLog;

import java.util.HashMap;
import java.util.Stack;

/**
 * 整个应用的入口，管理状态、活动集合，消息队列以及漏洞汇报
 * 
 * @author neo
 */
public class MainApplication extends Application implements IHandlerLikeNotify {

    private static final String TAG = "MainApplication";
    private HashMap<String, String> statusHashMap;

    private IHandlerLikeNotify currentNotifyer;

    @Override
    public void onCreate() {
        super.onCreate();
        statusHashMap = new HashMap<String, String>();

        currentNotifyer = null;
    }

    /**
     * 底层所有的回调接口，将代替以下回调
     * <p>
     * {@link JVACCOUNT#JVOnLineCallBack(int)} <br>
     * {@link JVACCOUNT#JVPushCallBack(int, String, String, String)}<br>
     * {@link JVSUDT#enqueueMessage(int, int, int, int)}<br>
     * {@link JVSUDT#saveCaptureCallBack(int, int, int)}<br>
     * {@link JVSUDT#ConnectChange(String, byte, int)}<br>
     * {@link JVSUDT#NormalData(byte, int, int, int, int, double, int, int, int, int, byte[], int)}
     * <br>
     * {@link JVSUDT#m_pfLANSData(String, int, int, int, int, int, boolean, int, int, String)}
     * <br>
     * {@link JVSUDT#CheckResult(int, byte[], int)}<br>
     * {@link JVSUDT#ChatData(int, byte, byte[], int)}<br>
     * {@link JVSUDT#TextData(int, byte, byte[], int, int)}<br>
     * {@link JVSUDT#PlayData(int, byte, byte[], int, int, int, int, double, int, int, int)}
     * 
     * @param what 分类
     * @param arg1 参数1
     * @param arg2 参数2
     * @param obj 附加对象
     */
    public synchronized void onJniNotify(int what, int uchType, int channel,
            Object obj) {
        if (null != currentNotifyer) {
            currentNotifyer.onNotify(what, uchType, channel, obj);
        } else {
            MyLog.e(TAG, "null notifyer");
        }
    }

    @Override
    public void onNotify(int what, int arg1, int arg2, Object obj) {
    }

    /**
     * 修改当前显示的 Activity 引用
     * 
     * @param currentNotifyer
     */
    public void setCurrentNotifyer(IHandlerLikeNotify currentNotifyer) {
        this.currentNotifyer = currentNotifyer;
    }

    /**
     * 获取状态 Map
     * 
     * @return
     */
    public HashMap<String, String> getStatusHashMap() {
        return statusHashMap;
    }


}
