
package com.jovision.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jovision.xiaowei.R;


public class ConnectView extends RelativeLayout {
    
    private static final int linkStateID = 0x01;
    private static final int logoImgID = 0x02;
    private static final int loadingID = 0x03;
    private static final int playImgID = 0x04;
    
    
    public static final int connecting = 0x20;//连接中                        ：               开始连接
    public static final int buffering1 = 0x21;//缓冲中                        ：               connect change 回来，等待O帧
    public static final int buffering2 = 0x22;//缓冲中...    ：                O帧过来了，等待I帧（I帧有可能解码失败）
    public static final int connected = 0x23;//已连接                           ：                I帧过来了（分辨率很高时有可能I帧传的比较慢）
    public static final int connectFailed = 0x24;//连接失败         :     连接失败           
    public static final int disconnected = 0x25;//断开连接             :     主动断开连接
    
    
    TextView linkState;//连接文字
    ImageView logoImg;//小维logo
    ProgressBar loading;//加载进度
    ImageView playImg;//播放按钮
    
    public void setConnectState(int connnectState,int stringId){
        switch(connnectState){
            case connecting:{
                linkState.setVisibility(View.VISIBLE);//连接文字
                linkState.setText(getResources().getString(R.string.connectting));
                logoImg.setVisibility(View.VISIBLE);//小维logo
                loading.setVisibility(View.VISIBLE);//加载进度
                playImg.setVisibility(View.GONE);//播放按钮
                break;
            }
            case buffering1:{
                linkState.setVisibility(View.VISIBLE);//连接文字
                linkState.setText(getResources().getString(R.string.buffering));
                logoImg.setVisibility(View.VISIBLE);//小维logo
                loading.setVisibility(View.VISIBLE);//加载进度
                playImg.setVisibility(View.GONE);//播放按钮
                break;
            }
            case buffering2:{
                linkState.setVisibility(View.VISIBLE);//连接文字
                linkState.setText(getResources().getString(R.string.buffering));
                logoImg.setVisibility(View.VISIBLE);//小维logo
                loading.setVisibility(View.VISIBLE);//加载进度
                playImg.setVisibility(View.GONE);//播放按钮
                break;
            }
            case connected:{
                linkState.setVisibility(View.GONE);//连接文字
                logoImg.setVisibility(View.GONE);//小维logo
                loading.setVisibility(View.GONE);//加载进度
                playImg.setVisibility(View.GONE);//播放按钮
                break;
            }
            case connectFailed:{
                linkState.setVisibility(View.VISIBLE);//连接文字
                linkState.setText(getResources().getString(stringId));
                logoImg.setVisibility(View.VISIBLE);//小维logo
                loading.setVisibility(View.GONE);//加载进度
                playImg.setVisibility(View.VISIBLE);//播放按钮
                break;
            }
            case disconnected:{
                linkState.setVisibility(View.VISIBLE);//连接文字
                linkState.setText(getResources().getString(R.string.connfailed_timeout));
                logoImg.setVisibility(View.VISIBLE);//小维logo
                loading.setVisibility(View.GONE);//加载进度
                playImg.setVisibility(View.VISIBLE);//播放按钮
                break;
            }
        }
    }

    public ConnectView(Context context) {
        super(context);
        init();
    }

    public ConnectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConnectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
        //连接状态文字
        linkState = new TextView(getContext());
        RelativeLayout.LayoutParams linkParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        linkParams.addRule(CENTER_IN_PARENT);
        linkState.setLayoutParams(linkParams);
        linkState.setText("loading...");
        linkState.setTextColor(getContext().getResources().getColor(R.color.white));
        linkState.setId(linkStateID);
        
        //小维标识logo
        logoImg = new ImageView(getContext());
        RelativeLayout.LayoutParams logoParams = new RelativeLayout.LayoutParams(
                100, 100);
        logoParams.addRule(CENTER_IN_PARENT);
        logoParams.addRule(RelativeLayout.ABOVE, linkStateID);
        logoParams.setMargins(0, 0, 0, 10);
        logoImg.setLayoutParams(logoParams);
        logoImg.setImageDrawable(getResources().getDrawable(R.drawable.xiaowei));
        logoImg.setId(logoImgID);

        //加载进度loading
        loading = new ProgressBar(getContext());
        loading.setHorizontalScrollBarEnabled(false);
        RelativeLayout.LayoutParams loadingParams = new RelativeLayout.LayoutParams(
                70, 70);
        loadingParams.addRule(CENTER_IN_PARENT);
        loadingParams.addRule(RelativeLayout.BELOW, linkStateID);
        loadingParams.setMargins(0, 10, 0, 0);
        loading.setLayoutParams(loadingParams);
        loading.setId(loadingID);

        //播放按钮
        playImg = new ImageView(getContext());
        RelativeLayout.LayoutParams playImgParams = new RelativeLayout.LayoutParams(
                -1, -2);
        playImgParams.addRule(CENTER_IN_PARENT);
        playImgParams.addRule(RelativeLayout.BELOW, linkStateID);
        playImgParams.setMargins(0, 10, 0, 0);
        playImg.setLayoutParams(playImgParams);
        playImg.setImageDrawable(getResources().getDrawable(R.drawable.play));
        playImg.setId(playImgID);

        this.addView(logoImg);
        this.addView(linkState);
        this.addView(loading);
        this.addView(playImg);
    };

}

// public class ConnectView extends View {
//
// private Paint mPaint;
// private Context mContext;
// private static final String mString = "Welcome to Mr Wei's blog";
//
//
// public ConnectView(Context context) {
// super(context);
// }
//
// public ConnectView(Context context, AttributeSet attrs) {
// super(context, attrs);
// }
//
// public ConnectView(Context context, AttributeSet attrs, int defStyleAttr) {
// super(context, attrs, defStyleAttr);
// }
//
// @Override
// protected void onDraw(Canvas canvas) {
//
// // TODO Auto-generated method stub
// super.onDraw(canvas);
// mPaint = new Paint();
// // 设置画笔颜色
// mPaint.setColor(Color.RED);
// // 设置填充
// mPaint.setStyle(Style.STROKE);
// // 画一个矩形,前俩个是矩形左上角坐标，后面俩个是右下角坐标
// canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
// canvas.drawCircle(50, 50, 50, mPaint);
// mPaint.setColor(Color.BLUE);
// // 绘制文字
// canvas.drawText(mString, 0, 100, mPaint);
//
// }
//
// }
