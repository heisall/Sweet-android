
package com.jovision.xiaowei.play;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.jovision.AppConsts;
import com.jovision.Jni;
import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.mydevice.Channel;
import com.jovision.xiaowei.mydevice.Device;
import com.jovision.xiaowei.utils.MyLog;

public class PlayActivity extends BaseActivity {
    protected MediaPlayer mediaPlayer = new MediaPlayer();
    protected Device connectDevice;
    protected Channel connectChannel;// 连接的通道
    protected int connectIndex = 0;// 窗口从0开始

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

    }

    @Override
    protected void saveSettings() {

    }

    @Override
    protected void freeMe() {
        try {
            if (null != mediaPlayer) {
                mediaPlayer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 抓拍
     */
    public void capture() {
        if (hasSDCard(5, true) && allowThisFuc()) {
            boolean captureRes = PlayUtil.capture(connectIndex);
            if (captureRes) {
                PlayUtil.prepareAndPlay(PlayActivity.this, mediaPlayer, true);
                showTextToast(AppConsts.CAPTURE_PATH);
                MyLog.e("capture", "success");
            } else {
                showTextToast(R.string.capture_error);
            }
        }
    }

    /**
     * 音频监听
     */
    public void audio() {
        
        if (allowThisFuc() && !connectChannel.isVoiceCall()) {// 对讲时不允许开监听
            if (connectChannel.isLisening()) {// 正在监听
                boolean stopRes = stopAudio(connectIndex);
                if (stopRes) {
                    connectChannel.setLisening(false);
                }

            } else {
                boolean startRes = startAudio(connectIndex,
                        connectChannel.getAudioByte());
                if (startRes) {
                    connectChannel.setLisening(true);
                }

            }
        }
    }

    /**
     * 录像
     */
    public void record() {
        if (Jni.checkRecord(connectIndex)) {// 正在录像
            PlayUtil.stopRecord();
            showTextToast(AppConsts.VIDEO_PATH);
        } else {
            PlayUtil.startRecord(connectIndex);
        }
    }

    /**
     * 应用层开启音频监听功能
     * 
     * @param index
     * @return
     */
    public static boolean startAudio(int index, int audioByte) {
        boolean open = false;
        if (PlayUtil.isPlayAudio(index)) {// 正在监听,确保不会重复开启
            open = true;
        } else {
            PlayUtil.startAudioMonitor(index);// enable audio
            open = true;
        }
        return open;
    }

    /**
     * 应用层关闭音频监听功能
     * 
     * @param index
     * @return
     */
    public static boolean stopAudio(int index) {
        boolean close = false;
        if (PlayUtil.isPlayAudio(index)) {// 正在监听，停止监听
            PlayUtil.stopAudioMonitor(index);// stop audio
            close = true;
        } else {// 确保不会重复关闭
            close = true;
        }
        return close;
    }
    
    /**
     * 开始远程回放
     */
    public void startRemote() {
        if (allowThisFuc()) {
            Intent remoteIntent = new Intent();
            remoteIntent.setClass(PlayActivity.this, JVRemotePlayActivity.class);
            remoteIntent.putExtra("IndexOfChannel", connectChannel.getIndex());
            remoteIntent.putExtra("ChannelOfChannel",
                    connectChannel.getChannel());
            remoteIntent.putExtra("is05", connectChannel.getParent().is05());
            remoteIntent.putExtra("DeviceType", connectChannel.getParent()
                    .getType());
            remoteIntent.putExtra("isJFH", connectChannel.getParent().isJFH());
            PlayActivity.this.startActivity(remoteIntent);
        }
    }

    /**
     * 调用功能前判断是否可调用
     * 
     * @return
     */
    public boolean allowThisFuc() {
        boolean allow = false;
        if (connectChannel.isConnected()) {
            allow = true;
        } else {
            showTextToast(R.string.wait_connect);
            allow = false;
        }
        return allow;
    }

}
