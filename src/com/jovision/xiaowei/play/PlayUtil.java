
package com.jovision.xiaowei.play;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.view.Surface;

import com.jovision.AppConsts;
import com.jovision.Jni;

import java.io.IOException;

public class PlayUtil {
    
    /**
     * 抓拍
     * @param index
     */
    public static boolean capture(int index){
        return Jni.screenshot(index, AppConsts.CAPTURE_PATH + System.currentTimeMillis()
                + AppConsts.IMAGE_JPG_KIND, 100);
    }
    
    /**
     * 抓拍声音
     */
    public static void prepareAndPlay(Context mContext,MediaPlayer mediaPlayer, boolean playSound) {
        if (playSound) {
            try {
                AssetManager assetMgr = mContext.getAssets();
                // 打开指定音乐文件
                AssetFileDescriptor afd = assetMgr.openFd("capture.wav");
                mediaPlayer.reset();

                // 使用MediaPlayer加载指定的声音文件。
                mediaPlayer.setDataSource(afd.getFileDescriptor(),
                        afd.getStartOffset(), afd.getLength());
                // 准备声音
                mediaPlayer.prepare();
                // 播放
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
    /**
     * 查询音频监听状态
     */
    public static boolean isPlayAudio(int index) {
        return Jni.isPlayAudio(index);
    }

    /**
     * 开始音频监听
     */
    public static boolean startAudioMonitor(int index) {
        Jni.resumeAudio(index);
        return Jni.enablePlayAudio(index, true);
    }

    /**
     * 停止音频监听
     */
    public static boolean stopAudioMonitor(int index) {
        Jni.pauseAudio(index);
        return Jni.enablePlayAudio(index, false);
    }
    
    /**
     * 开始录像
     */
    public static boolean stopRecord() {
        return Jni.stopRecord();
    }

    /**
     * 停止录像
     */
    public static boolean startRecord(int index) {
        return Jni.startRecord(index, AppConsts.VIDEO_PATH
                + System.currentTimeMillis()
                + AppConsts.VIDEO_MP4_KIND, true, true);
    }
    
    
    /**
     * 继续播放视频
     * @param index
     * @param surface
     */
    public static void resumeVideo(int index, Surface surface) {
        Jni.resume(index, surface);
    }

    /**
     * 暂停视频
     * @param index
     */
    public static void pauseVideo(int index) {
        Jni.pause(index);
    }

    /**
     * 断开视频
     * 
     * @param index
     */
    public static void disConnectWindow(int index) {
        Jni.disconnect(index);
    }
}
