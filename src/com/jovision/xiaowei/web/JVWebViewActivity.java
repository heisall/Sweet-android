
package com.jovision.xiaowei.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.jovision.xiaowei.BaseActivity;
import com.jovision.xiaowei.R;
import com.jovision.xiaowei.utils.ConfigUtil;

import java.util.Stack;

public class JVWebViewActivity extends BaseActivity {

    private WebView myWebview;
    private String linkUrlStr;
    private WebChromeClient wvcc = null;
    private Stack<String> titleStack = new Stack<String>();// 标题栈，后进先出

    @Override
    public void onHandler(int what, int arg1, int arg2, Object obj) {

    }

    @Override
    public void onNotify(int what, int arg1, int arg2, Object obj) {

    }

    @Override
    protected void initSettings() {
        linkUrlStr = getIntent().getStringExtra("linkUrl");
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initUi() {
        setContentView(R.layout.webview_layout);
        leftBtn = (Button) findViewById(R.id.left_btn);
        rightBtn = (Button) findViewById(R.id.right_btn);
        currentMenu = (TextView) findViewById(R.id.center_textview);
        currentMenu.setText(R.string.left_myservice);
        leftBtn.setOnClickListener(mOnClickListener);
        
        myWebview = (WebView) findViewById(R.id.webview);
        myWebview.getSettings().setJavaScriptEnabled(true);
        myWebview.getSettings().setDomStorageEnabled(true);

        wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                currentMenu.setText(ConfigUtil.getShortTile(title));
                titleStack.push(title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        };

        // 设置setWebChromeClient对象
        myWebview.setWebChromeClient(wvcc);
        myWebview.requestFocus(View.FOCUS_DOWN);

        // 加快加载速度
        myWebview.getSettings().setRenderPriority(RenderPriority.HIGH);
        myWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        myWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String newUrl) {
                myWebview.loadUrl(newUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        myWebview.loadUrl(linkUrlStr);
    }
    
    /**
     * 点击事件
     */
    OnClickListener mOnClickListener = new OnClickListener(){

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.left_btn:{//top左键
                    backMethod();
                    break;
                }
                case R.id.right_btn:{//top右键
                    break;
                }
            }
        }
        
    };
    
    /**
     * webview返回
     */
    public void backMethod() {
        try {
            if (myWebview.canGoBack()) {
                if (null != titleStack && 0 != titleStack.size()) {
                    titleStack.pop();
                    String lastTitle = titleStack.peek();
                    currentMenu.setText(ConfigUtil.getShortTile(lastTitle));
                    myWebview.goBack(); // goBack()表示返回WebView的上一页面
                }
            } else {
                JVWebViewActivity.this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        backMethod();
    }

    @Override
    protected void saveSettings() {

    }

    @Override
    protected void freeMe() {

    }

}
