
package com.jovision;

import android.os.Environment;

import java.io.File;

public class AppConsts {

    public static final String APP_NAME = "XiaoWei";
    public static final boolean APP_CUSTOM = false;//是否定制 true：定制  false：小维

    

    /************* 程序中用到的路径 ********************/
    public static final String SD_CARD_PATH = Environment
            .getExternalStorageDirectory().getPath() + File.separator;
    public static final String LOG_CLOUD_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "logcloud" + File.separator;
    public static final String LOG_ACCOUNT_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "logaccout" + File.separator;
    
    public static final String CAPTURE_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "capture" + File.separator;
    public static final String VIDEO_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "video" + File.separator;
    public static final String DOWNLOAD_VIDEO_PATH = SD_CARD_PATH
            + APP_NAME + File.separator + "downvideo" + File.separator;
    public static final String BUG_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "bugs" + File.separator;
    public static final String HEAD_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "head" + File.separator;
    public static final String WELCOME_IMG_PATH = SD_CARD_PATH
            + APP_NAME + File.separator + "welcome" + File.separator;
    public static final String SCENE_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "scene" + File.separator;
    // 云存储边下载边播放ts,m3u8文件路径
    public static final String CLOUDVIDEO_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "cloudvideo" + File.separator;
    // 报警图片路径
    public static final String ALARM_IMG_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "alarmimage" + File.separator;
    // 报警视频路径
    public static final String ALARM_VIDEO_PATH = SD_CARD_PATH + APP_NAME
            + File.separator + "alarmvideo" + File.separator;
    
    /************* 媒体类型 ********************/
    public static final String IMAGE_PNG_KIND = ".png";// 图片类型
    public static final String IMAGE_JPG_KIND = ".jpg";// 图片类型
    public static final String VIDEO_MP4_KIND = ".mp4";// 视频类型
    public static final String VIDEO_M3U8_KIND = ".m3u8";// 视频类型
    
    /************* statusHashMap key ********************/
    public static final String USER_NAME = "USER_NAME";//用户名key
    public static final String INIT_ACCOUNT_SDK = "INIT_ACCOUNT";//账号库sdk是否初始化的key
    public static final String INIT_CLOUD_SDK = "INIT_CLOUD";//云视通sdk是否初始化的key
    public static final String BROADCAST_STATE = "BROADCAST_STATE";//广播状态
    public static final String HELPER_STATE = "HELPER_STATE";//小助手状态
    
    /************* MySharedPreference key ********************/
    // 张帅服务端0：中文，1：英文，2：繁体
    public static final int LANGUAGE_ZH = 0;// 中文
    public static final int LANGUAGE_EN = 1;// 英文
    public static final int LANGUAGE_ZHTW = 2;// 繁体

    
    public static final int COUNTRY_CHINA = 0;// 中国大陆
    public static final int COUNTRY_OTHER = 1;// 非中国大陆
    
    public static int CURRENT_LAN = -1;// 当前系统语言

    /************* MySharedPreference key ********************/
    public static final String FIRST_OPEN_APP = "FIRST_OPEN_APP";// true:第一次用软件;false：非第一次

    
    
    /************* 视频播放相关 ********************/
    public static final int CALL_CONNECT_CHANGE = 0xA1;
    public static final int CALL_NORMAL_DATA = 0xA2;
    public static final int CALL_CHECK_RESULT = 0xA3;
    public static final int CALL_CHAT_DATA = 0xA4;
    public static final int CALL_TEXT_DATA = 0xA5;
    public static final int CALL_DOWNLOAD = 0xA6;
    public static final int CALL_PLAY_DATA = 0xA7;
    public static final int CALL_LAN_SEARCH = 0xA8;
    public static final int CALL_NEW_PICTURE = 0xA9;
    public static final int CALL_STAT_REPORT = 0xAA;
    public static final int CALL_GOT_SCREENSHOT = 0xAB;
    public static final int CALL_PLAY_DOOMED = 0xAC;
    public static final int CALL_PLAY_AUDIO = 0xAD;
    public static final int CALL_QUERY_DEVICE = 0xAE;
    public static final int CALL_HDEC_TYPE = 0xAF;
    public static final int CALL_LAN_STOP = 0xB2;

    public static final int DEVICE_TYPE_UNKOWN = -1;
    public static final int DEVICE_TYPE_DVR = 0x01;
    public static final int DEVICE_TYPE_950 = 0x02;
    public static final int DEVICE_TYPE_951 = 0x03;
    public static final int DEVICE_TYPE_IPC = 0x04;
    public static final int DEVICE_TYPE_NVR = 0x05;

    public static final int JAE_ENCODER_SAMR = 0x00;
    public static final int JAE_ENCODER_ALAW = 0x01;
    public static final int JAE_ENCODER_ULAW = 0x02;
    public static final int JAE_ENCODER_G729 = 0x03;

    public static final int ENC_PCM_SIZE = 320;
    public static final int ENC_AMR_SIZE = 640;
    public static final int ENC_G711_SIZE = 640;
    public static final int ENC_G729_SIZE = 960;

    public static final int TEXT_REMOTE_CONFIG = 0x01;
    public static final int TEXT_AP = 0x02;
    public static final int TEXT_GET_STREAM = 0x03;

    public static final int FLAG_WIFI_CONFIG = 0x01;
    public static final int FLAG_WIFI_AP = 0x02;
    public static final int FLAG_BPS_CONFIG = 0x03;
    public static final int FLAG_CONFIG_SCCUESS = 0x04;
    public static final int FLAG_CONFIG_FAILED = 0x05;
    public static final int FLAG_CONFIG_ING = 0x06;
    public static final int FLAG_SET_PARAM = 0x07;
    public static final int FLAG_GPIN_ADD = 0x10;
    public static final int FLAG_GPIN_SET = 0x11;
    public static final int FLAG_GPIN_SELECT = 0x12;
    public static final int FLAG_GPIN_DEL = 0x13;

    public static final int EX_WIFI_CONFIG = 0x0A;

    public static final int ARG1_PLAY_BAD = 0x01;

    public static final int DOWNLOAD_REQUEST = 0x20;
    public static final int DOWNLOAD_START = 0x21;
    public static final int DOWNLOAD_FINISHED = 0x22;
    public static final int DOWNLOAD_ERROR = 0x23;
    public static final int DOWNLOAD_STOP = 0x24;
    public static final int DOWNLOAD_TIMEOUT = 0x76;

    public static final int BAD_STATUS_NOOP = 0x00;
    public static final int BAD_STATUS_OMX = 0x01;
    public static final int BAD_STATUS_FFMPEG = 0x02;
    public static final int BAD_STATUS_OPENGL = 0x03;
    public static final int BAD_STATUS_AUDIO = 0x04;
    public static final int BAD_STATUS_DECODE = 0x05;
    public static final int PLAYBACK_DONE = 0x06;
    public static final int HDEC_BUFFERING = 0x07;

    public static final int BAD_SCREENSHOT_NOOP = 0x00;
    public static final int BAD_SCREENSHOT_INIT = 0x01;
    public static final int BAD_SCREENSHOT_CONV = 0x02;
    public static final int BAD_SCREENSHOT_OPEN = 0x03;

    public static final String IPC_DEFAULT_USER = "jwifiApuser";
    public static final String IPC_DEFAULT_PWD = "^!^@#&1a**U";
    public static final String IPC_DEFAULT_IP = "10.10.0.1";
    public static final int IPC_DEFAULT_PORT = 9101;

    public static final String DEFAULT_USER = "admin";
    public static final String DEFAULT_PWD = "";

    /** 音频播放 */
    public static final int PLAY_AUDIO_WHAT = 0x26;
    public static final int STOP_AUDIO_GATHER = 0x08;
    public static final int START_AUDIO_GATHER = 0x09;
}
