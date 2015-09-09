package com.jovision.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.jovision.xiaowei.R;

public class PullToRefreshView  extends LinearLayout implements OnTouchListener {

    private static PullToRefreshView mPullToRefreshView;
    private static Context mContext;
    private AttributeSet mAttrs;
    private View[] childs;
    private RelativeLayout mTopView, mBottomView;
    private MyScrollView mScrollView;
    private LinearLayout mContentView;
    private LayoutParams mParams;
    private static TextView mTopTips, mBottomTips;
    private static ImageView mTopTipsIcon, mBottomTipsIcon;
    private RotateAnimation  toAnmt, backAnmt, progressBarAnmt;
    
    private boolean isToTop, isToBottom;
    private boolean isGetChilds;
    private boolean isRecordY;
    private boolean isContentNotFull;
    private boolean isResetLayoutParams;
    private boolean isActviteScrollEvent;//偏移量达到刷新或加载更多的要求时，为true，否则为false
    private boolean isTurnUp;//判断手势，向上时为true，向下为false
    private static boolean isRefresing;//是否正在刷新
    private static boolean isRefreshed = true;//是否已刷新完毕
    private static boolean isLoading;//是否正在加载
    private static boolean isLoaded = true;//是否已加载完毕
    private static boolean isPulling;//是否正在拖动scrollview
    private boolean isRotate;//是否已经动画
    
    private static int topViewHeight;//topview的高度，和bottomview高度一致
    private int mHeight;//整个PullToRefreshView的高度
    private int scrollToEnd;//scrollview滚动到底部的长度
    private float lastY;//
    private float guestureLastY;//用于记录手势按下屏幕时的第一个位置
    
    private OnRefreshListener mRefreshListener;
    private OnLoadListener mLoadListener;
    
    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.mAttrs = attrs;
        initView();
    }

    private void initView() {
        mPullToRefreshView = this;
        toAnmt = new RotateAnimation(0, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        toAnmt.setDuration(300);
        toAnmt.setFillAfter(true);
        backAnmt = new RotateAnimation(0, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        backAnmt.setDuration(300);
        backAnmt.setFillAfter(true);
        progressBarAnmt = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        progressBarAnmt.setDuration(1800);
        progressBarAnmt.setRepeatCount(-1);
        progressBarAnmt.setRepeatMode(RotateAnimation.RESTART);
        progressBarAnmt.setInterpolator(new LinearInterpolator());
        toAnmt.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                mTopTipsIcon.clearAnimation();
                mTopTipsIcon.setBackgroundResource(R.drawable.ic_up);
                mBottomTipsIcon.clearAnimation();
                mBottomTipsIcon.setBackgroundResource(R.drawable.ic_up);
            }
            
        });
        
        backAnmt.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                mTopTipsIcon.clearAnimation();
                mTopTipsIcon.setBackgroundResource(R.drawable.ic_down);
                mBottomTipsIcon.clearAnimation();
                mBottomTipsIcon.setBackgroundResource(R.drawable.ic_down);
            }
        });
        
        ViewTreeObserver ob = getViewTreeObserver();
        ob.addOnPreDrawListener(new OnPreDrawListener() {
            
            @Override
            public boolean onPreDraw() {
                if(topViewHeight == 0 || mHeight == 0) {
                    if(topViewHeight == 0) {
                        topViewHeight = mTopView.getHeight();
                    }
                    if(mHeight == 0) {
                        mHeight = getHeight();
                    }
                    if(mHeight > 0 && topViewHeight > 0) {
                        SharedPreferences p = mContext.getSharedPreferences("PullToRefreshView", 0);
                        p.edit().putInt("barHeight", topViewHeight).putInt("height", mHeight).commit();
                    }
                    mParams = new LayoutParams(LayoutParams.MATCH_PARENT, mHeight+2*topViewHeight);
                    setLayoutParams(mParams);
                    setY(-topViewHeight);
                }
                return true;
            }
        });
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setLayout();
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setLayout() {
        if(!isGetChilds) {
            
            int count = getChildCount();
            childs = new View[count];
            for(int i=0; i<count; i++) {
                childs[i] = getChildAt(i);
            }
            for(int i=0; i<count; i++) {
                removeAllViews();
            }
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mTopView = (RelativeLayout) inflater.inflate(R.layout.pulltorefresh_top_layout, null);
            mBottomView = (RelativeLayout) inflater.inflate(R.layout.pulltorefresh_bottom_layout, null);
            mTopTips = (TextView) mTopView.findViewById(R.id.top_tips);
            mBottomTips = (TextView) mBottomView.findViewById(R.id.bottom_tips);
            mTopTipsIcon = (ImageView) mTopView.findViewById(R.id.ic_down);
            mBottomTipsIcon = (ImageView) mBottomView.findViewById(R.id.ic_up);
            
            mScrollView = new MyScrollView(mContext, mAttrs);
            mParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
            mParams.weight = 1;
            mScrollView.setLayoutParams(mParams);
            mContentView = new LinearLayout(mContext, mAttrs);
            mContentView.setLayoutParams(mParams);
            mContentView.setOrientation(LinearLayout.VERTICAL);
            for(int i=0; i<count; i++) {
                mContentView.addView(childs[i]);
            }
            mScrollView.addView(mContentView);
            mScrollView.setOnTouchListener(this);
            addView(mTopView);
            addView(mScrollView);
            addView(mBottomView);
            isGetChilds = true;
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mScrollView.getHeight() > mContentView.getHeight()) {
            isContentNotFull = true;
            mBottomView.setVisibility(View.GONE);
        } else {
            isContentNotFull = false;
        }
    }
    /**
     * 设置刷新监听器
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }
    /**
     * 设置加载监听器
     */
    public void setOnLoadListener(OnLoadListener listener) {
        mLoadListener = listener;
    }
    
    class MyScrollView extends ScrollView {

        public MyScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            //MyLog.i("t="+t);
            if(t == 0) {
                isToTop = true;
            } else {
                if(t == getChildAt(0).getHeight()-getHeight()) {
                    scrollToEnd = t;
                    isToBottom = true;
                }
            }
        }
    }
    
    /**
     * 刷新监听器
     */
    public static abstract class OnRefreshListener {
        /**
         * 正在刷新
         */
        public void onRefresh() {
        }
        /**
         * 停止刷新，刷新完毕时请主动调用
         */
        public void stopRefresh() {
            isRefresing = false;
            mTopTips.setText(mContext.getResources().getString(R.string.refreshed));
            mTopTipsIcon.clearAnimation();
            mTopTipsIcon.setBackgroundResource(R.drawable.ic_finish);
            resetYOffset();
        }
    }
    /**
     * 加载更多监听器
     *
     */
    public static abstract class OnLoadListener {
        /**
         * 正在加载
         */
        public void onLoad() {
        }
        /**
         * 停止加载，加载完毕时请主动调用
         */
        public void stopLoad() {
            isLoading = false;
            mBottomTips.setText(mContext.getResources().getString(R.string.loaded));
            mBottomTipsIcon.clearAnimation();
            mBottomTipsIcon.setBackgroundResource(R.drawable.ic_finish);
            resetYOffset();
        }
    }
    
    public static void resetYOffset() {
        new AsyncTask<Integer, Integer, Integer>() {

            @Override
            protected Integer doInBackground(Integer... params) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            
            protected void onPostExecute(Integer result) {
                if(!isPulling) {
                    mPullToRefreshView.setY(-topViewHeight);
                    mTopTipsIcon.setBackgroundResource(R.drawable.ic_down);
                    mBottomTipsIcon.setBackgroundResource(R.drawable.ic_up);
                    isRefreshed = true;
                    isLoaded = true;
                }
                
            };
        }.execute();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            guestureLastY = event.getRawY();
            isPulling = true;
            break;
        case MotionEvent.ACTION_MOVE:
            
            if(event.getRawY() - guestureLastY > 0) {
                isTurnUp = false;
            } else {
                isTurnUp = true;
            }
            if(isContentNotFull && !isResetLayoutParams) {
                mParams = new LayoutParams(LayoutParams.MATCH_PARENT, getHeight()-topViewHeight);
                setLayoutParams(mParams);
                isResetLayoutParams = true;
            }
            if(isToTop && !isTurnUp) {
                
                if(!isRecordY) {
                    lastY = event.getRawY()/3;
                    isRecordY = true;
                }
                
                mScrollView.setVerticalScrollBarEnabled(false);
                float y = 0.0f;
                if(!isRefreshed) {
                    if(!isPulling) {
                        y = event.getRawY()/3 - lastY - topViewHeight;
                    } else {
                        y = event.getRawY()/3 - lastY;
                    }
                } else {
                    y = event.getRawY()/3 - lastY - topViewHeight;
                }
                if(!isRefresing && isRefreshed) {
                    if(Math.abs(event.getRawY()/3 - lastY) >= topViewHeight) {
                        isActviteScrollEvent = true;
                        mTopTips.setText(mContext.getResources().getString(R.string.release_to_refresh));
                        if(!isRotate) {
                            mTopTipsIcon.startAnimation(toAnmt);
                            isRotate = true;
                        }
                        
                    } else {
                        
                        isActviteScrollEvent = false;
                        mTopTips.setText(mContext.getResources().getString(R.string.pull_to_refresh));
                        if(isRotate) {
                            mTopTipsIcon.startAnimation(backAnmt);
                            isRotate = false;
                        }
                    }
                }
                setY(y);
                if(event.getRawY()/3 - lastY < 0) {
                    isToTop = false;
                    isRecordY = false;
                } else {
                    mScrollView.setScrollY(0);
                }
            } else if(isToBottom || isContentNotFull) {
                
                if(!isRecordY) {
                    lastY = event.getRawY()/3;
                    isRecordY = true;
                }
                
                mScrollView.setVerticalScrollBarEnabled(false);
                
                if(!isLoading && isLoaded) {
                    if(Math.abs(event.getRawY()/3 - lastY) >= topViewHeight) {
                        isActviteScrollEvent = true;
                        mBottomTips.setText(mContext.getResources().getString(R.string.release_to_load));
                        if(!isRotate) {
                            mBottomTipsIcon.startAnimation(backAnmt);
                            isRotate = true;
                        }
                    } else {
                        isActviteScrollEvent = false;
                        mBottomTips.setText(mContext.getResources().getString(R.string.push_to_load));
                        if(isRotate) {
                            mBottomTipsIcon.startAnimation(toAnmt);
                            isRotate = false;
                        }
                    }
                }
                float y = 0.0f;
                if(!isLoaded) {
                    if(isPulling) {
                        y = event.getRawY()/3 - lastY - 2*topViewHeight;
                    } else {
                        y = event.getRawY()/3 - lastY - topViewHeight;
                    }
                } else {
                    y = event.getRawY()/3 - lastY - topViewHeight;
                }
                setY(y);
                if(event.getRawY()/3 - lastY > 0) {
                    isToBottom = false;
                    isRecordY = false;
                } else {
                    mScrollView.setScrollY(scrollToEnd);
                }
            }
            break;
        case MotionEvent.ACTION_UP:
            
            mScrollView.setVerticalScrollBarEnabled(true);
            isRotate = false;
            isRecordY = false;
            isPulling = false;
            if(isActviteScrollEvent) {
                if(isToTop && !isTurnUp) {
                    if(mRefreshListener != null) {
                        isRefresing = true;
                        isRefreshed = false;
                        setY(0);
                        mTopTips.setText(mContext.getResources().getString(R.string.refreshing));
                        mTopTipsIcon.setBackgroundResource(R.drawable.ic_refresh_progress_bar);
                        mTopTipsIcon.startAnimation(progressBarAnmt);
                        mRefreshListener.onRefresh();
                    }
                } else if(isToBottom) {
                    if(mLoadListener != null) {
                        setY(-2*topViewHeight);
                        mBottomTips.setText(mContext.getResources().getString(R.string.loading));
                        mBottomTipsIcon.setBackgroundResource(R.drawable.ic_refresh_progress_bar);
                        mBottomTipsIcon.startAnimation(progressBarAnmt);
                        isLoading = true;
                        isLoaded = false;
                        mLoadListener.onLoad();
                    }
                } else if(isContentNotFull) {
                    setY(-topViewHeight);
                }
                
                isActviteScrollEvent = false;
            } else {
                if(isRefresing) {
                    setY(0);
                } else if(isLoading) {
                    setY(-2*topViewHeight);
                } else {
                    setY(-topViewHeight);
                    
                }
            }
            if(!isRefresing) {
                isRefreshed = true;
            } 
            if(!isLoading) {
                isLoaded = true;
            }
            
            isToTop = false;
            isToBottom = false;
            break;

        default:
            break;
        
            }
            return false;
    }
    
    public void initLayoutParams() {
        SharedPreferences p = mContext.getSharedPreferences("PullToRefreshView", 0);
        topViewHeight = p.getInt("barHeight", 0);
        mHeight = p.getInt("height", 0);
        
        if(mHeight > 0) {
            mParams = new LayoutParams(LayoutParams.MATCH_PARENT, mHeight+2*topViewHeight);
            setLayoutParams(mParams);
            setY(-topViewHeight);
        }
    }
}
