/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package baseutil.library.special.view.webview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.infrastructure.interfaces.OnBottomDragListener;
import com.infrastructure.utils.StringUtil;
import com.simple.lib.R;

import baseutil.library.base.BaseActivity;

/**
 * 通用网页Activity
 *
 * @author Lemon
 * @use toActivity或startActivity(WebViewActivity.createIntent)
 */
public class WebLoadLocalHtmlActivity extends BaseActivity implements OnClickListener, OnBottomDragListener {
    public static final String TAG = "WebViewActivity";
    public static final String INTENT_TITLE = "INTENT_TITLE";
    protected Intent intent = null;

    /**
     * 获取启动这个Activity的Intent
     *
     * @param title
     * @param url
     */
    public static Intent createIntent(Context context, String title, String url) {
        return new Intent(context, WebLoadLocalHtmlActivity.class).
                putExtra(INTENT_TITLE, title).
                putExtra(INTENT_URL, url);
    }

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, WebLoadLocalHtmlActivity.class);
        intent.putExtra(INTENT_TITLE, title);
        intent.putExtra(INTENT_URL, url);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.web_view_activity);
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private TextView tvWebViewTitle;
    private WebView wvWebView;


    @Override
    public void initView() {

        tvWebViewTitle = (TextView) findViewById(R.id.tvWebViewTitle);

        wvWebView = (WebView) findViewById(R.id.wvWebView);
        findViewById(R.id.tvWebViewReturn).setOnClickListener(this);

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String INTENT_RETURN = "INTENT_RETURN";
    public static final String INTENT_URL = "INTENT_URL";

    @SuppressWarnings("unused")
    private Handler webHandler = new Handler();

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void initData() {

        intent = getIntent();
        if (StringUtil.isNotEmpty(intent.getStringExtra(INTENT_TITLE), true)) {
            tvWebViewTitle.setText("" + StringUtil.getCurrentString());
        }

        WebSettings webSettings = wvWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra(INTENT_URL);
        if (StringUtil.isNotEmpty(url, true) == false) {
            Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
            finish();
            return;
        }

        showProgressDialog("正在加载中...");
        wvWebView.requestFocus();
        wvWebView.loadUrl(url);
        wvWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                dismissProgressDialog();
            }
        });

    }


    //data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            if (wvWebView.canGoForward()) {
                wvWebView.goForward();
            }
            return;
        }
        onBackPressed();
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvWebViewReturn) {
            if (wvWebView.canGoBack()) {
                wvWebView.goBack();
                return;
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (wvWebView.canGoBack()) {
            wvWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
        wvWebView = null;
    }


    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}