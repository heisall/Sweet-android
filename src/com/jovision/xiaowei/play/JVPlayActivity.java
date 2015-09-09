
package com.jovision.xiaowei.play;

import android.media.MediaPlayer;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jovision.AppConsts;
import com.jovision.JVNetConst;
import com.jovision.Jni;
import com.jovision.view.ConnectView;
import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.mydevice.Channel;
import com.jovision.xiaowei.mydevice.Device;
import com.jovision.xiaowei.utils.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

public class JVPlayActivity extends PlayActivity {

    private static final String TAG = "JVPlayActivity";
    /** view **/
    private SurfaceView playSurface;// 视频播放view
    private SurfaceHolder surfaceholder;
    private ConnectView connectView;
    private Button captureBtn, audioBtn, callBtn, recordBtn, streamBtn, remotePlayBtn;

    @Override
    public void onHandler(int what, int arg1, int arg2, Object obj) {
        switch (what) {
            case AppConsts.CALL_NORMAL_DATA: {// O 帧 视频数据回调
                connectChannel.setConnected(true);
                connectChannel.setPaused(false);
                try {
                    JSONObject jobj;
                    jobj = new JSONObject(obj.toString());
                    int type = jobj.optInt("device_type");
                    if (null != jobj) {
                        connectChannel.getParent().setType(type);
                        connectChannel.getParent()
                                .setJFH(jobj.optBoolean("is_jfh"));
                        // connectChannel.getParent().set05(jobj.optBoolean("is05"));
                        connectChannel.setAudioType(jobj.getInt("audio_type"));
                        connectChannel.setAudioByte(jobj.getInt("audio_bit"));
                        connectChannel.setAudioEncType(jobj
                                .getInt("audio_enc_type"));
                        connectChannel.setSingleVoice(true);
                        if (8 == connectChannel.getAudioByte()
                                && AppConsts.DEVICE_TYPE_DVR == type) {
                            connectChannel.setSupportVoice(false);
                        } else {
                            connectChannel.setSupportVoice(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            }

            case AppConsts.CALL_NEW_PICTURE: {// I帧
                connectView.setConnectState(ConnectView.connected, 0);
                break;
            }

            case AppConsts.CALL_CONNECT_CHANGE: {// 连接结果
                switch (arg2) {
                // 1 -- 连接成功
                    case JVNetConst.CONNECT_OK: {
                        connectChannel.setConnected(true);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.buffering1, 0);
                        break;
                    }
                    // 2 -- 断开连接成功
                    case JVNetConst.DISCONNECT_OK: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);
                        // JSONObject connectObj;
                        // try {
                        // connectObj = new JSONObject(obj.toString());
                        // String errorMsg = connectObj.getString("msg");
                        // } catch (JSONException e) {
                        // e.printStackTrace();
                        // }

                        break;
                    }
                    // 3 -- 不必要重复连接
                    case JVNetConst.NO_RECONNECT: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);
                        break;
                    }
                    // 4 -- 连接失败
                    case JVNetConst.CONNECT_FAILED: {
                        int errorID = 0;
                        try {
                            JSONObject connectObj = new JSONObject(obj.toString());
                            String errorMsg = connectObj.getString("msg");
                            if ("password is wrong!".equalsIgnoreCase(errorMsg)
                                    || "pass word is wrong!".equalsIgnoreCase(errorMsg)) {// 密码错误时提示身份验证失败
                                errorID = R.string.connfailed_auth;

                            } else if ("channel is not open!"
                                    .equalsIgnoreCase(errorMsg)) {// 无该通道服务
                                errorID = R.string.connfailed_channel_notopen;
                            } else if ("connect type invalid!"
                                    .equalsIgnoreCase(errorMsg)) {// 连接类型无效
                                errorID = R.string.connfailed_type_invalid;
                            } else if ("client count limit!".equalsIgnoreCase(errorMsg)) {// 超过主控最大连接限制
                                errorID = R.string.connfailed_maxcount;
                            } else if ("connect timeout!".equalsIgnoreCase(errorMsg)) {//
                                errorID = R.string.connfailed_timeout;
                            } else if ("check password timeout!"
                                    .equalsIgnoreCase(errorMsg)) {// 验证密码超时
                                errorID = R.string.connfailed_checkpass_timout;
                            } else {// "Connect failed!"
                                errorID = R.string.connect_failed;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorID = R.string.connect_failed;
                        }
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.connectFailed, errorID);

                        break;
                    }
                    // 5 -- 没有连接
                    case JVNetConst.NO_CONNECT: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);
                        break;
                    }
                    // 6 -- 连接异常断开
                    case JVNetConst.ABNORMAL_DISCONNECT: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);

                        break;
                    }
                    // 7 -- 服务停止连接，连接断开
                    case JVNetConst.SERVICE_STOP: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);
                        break;
                    }

                    // 8 -- 断开连接失败
                    case JVNetConst.DISCONNECT_FAILED: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);
                        break;
                    }

                    // 9 -- 其他错误
                    case JVNetConst.OHTER_ERROR: {
                        connectChannel.setConnected(false);
                        connectChannel.setPaused(false);
                        connectView.setConnectState(ConnectView.disconnected, 0);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onNotify(int what, int arg1, int arg2, Object obj) {
        handler.sendMessage(handler.obtainMessage(what, arg1, arg2, obj));
    }

    @Override
    protected void initSettings() {
        connectDevice = new Device("", 9101, "B", 175926366, "abc", "123", true, 1, 0, "B175926366");
        connectDevice.setFullNo("B175926366");
        connectChannel = connectDevice.getChannelList().toList().get(0);
    }

    @Override
    protected void initUi() {
        setContentView(R.layout.play_layout);
        playSurface = (SurfaceView) findViewById(R.id.playsurface);
        connectView = (ConnectView) findViewById(R.id.connectview);
        captureBtn = (Button) findViewById(R.id.capture_btn);
        audioBtn = (Button) findViewById(R.id.audio_btn);
        callBtn = (Button) findViewById(R.id.call_btn);
        recordBtn = (Button) findViewById(R.id.record_btn);
        streamBtn = (Button) findViewById(R.id.stream_btn);
        remotePlayBtn = (Button) findViewById(R.id.remote_btn);
        captureBtn.setOnClickListener(mOnClickListener);
        audioBtn.setOnClickListener(mOnClickListener);
        callBtn.setOnClickListener(mOnClickListener);
        recordBtn.setOnClickListener(mOnClickListener);
        streamBtn.setOnClickListener(mOnClickListener);
        remotePlayBtn.setOnClickListener(mOnClickListener);

        surfaceholder = playSurface.getHolder();
        surfaceholder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (null != connectChannel) {
                    if (connectChannel.isConnected()) {
                        if (connectChannel.isPaused()) {
                            connectView.setConnectState(ConnectView.buffering1, 0);
                            PlayUtil.resumeVideo(connectIndex, surfaceholder.getSurface());
                        }
                    } else {
                        connectView.setConnectState(ConnectView.connecting, 0);
                        connect(connectDevice, connectChannel, surfaceholder.getSurface());
                    }
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                    int width, int height) {
            }
        });
    }

    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.capture_btn: {// 抓拍
                    capture();
                    break;
                }
                case R.id.audio_btn: {
                    audio();
                    break;
                }
                case R.id.call_btn: {
                    break;
                }
                case R.id.record_btn: {
                    record();
                    break;
                }
                case R.id.stream_btn: {
                    break;
                }
                case R.id.remote_btn: {
                    
                    break;
                }
            }
        }

    };

    /**
     * 视频连接
     * 
     * @param device
     * @param channel
     * @param surface
     * @return
     */
    private int connect(Device device, Channel channel, Surface surface) {
        int result = -1;

        connectIndex = channel.getIndex();
        connectChannel = channel;
        if (null != device && null != channel) {
            if ("".equalsIgnoreCase(device.getIp()) || 0 == device.getPort()) {// 无ip和端口走云视通连接
                result = Jni.connect(channel.getIndex(), channel.getChannel(),
                        device.getIp(), device.getPort(), device.getUser(),
                        device.getPwd(), device.getNo(), device.getGid(), true,
                        1, true, JVNetConst.TYPE_3GMO_UDP, surface, false,
                        false, false, false, null);
            } else {// 有Ip用ip连接，云视通号字段需要传-1，否则仍然走的云视通连接
                result = Jni.connect(channel.getIndex(), channel.getChannel(),
                        device.getIp(), device.getPort(), device.getUser(),
                        device.getPwd(), -1, device.getGid(), true, 1, true,
                        JVNetConst.TYPE_3GMO_UDP, surface, false, false,
                        false, false, null);
            }
        }

        return result;
    }

    @Override
    protected void onPause() {
        if (connectChannel.isConnected()) {
            PlayUtil.pauseVideo(connectIndex);
            connectChannel.setPaused(true);
        } else if (connectChannel.isConnecting()) {
            PlayUtil.disConnectWindow(connectIndex);
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        PlayUtil.disConnectWindow(connectIndex);
        this.finish();
        overridePendingTransition(R.anim.slide_up_in,
                R.anim.slide_down_out);
    }

    @Override
    protected void saveSettings() {

    }

    @Override
    protected void freeMe() {

    }

}
