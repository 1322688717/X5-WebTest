package com.java8888.java9999.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java8888.java9999.R;


public class CustomWebActivity extends AppCompatActivity {

    private TextView tvAdTitle;
    private ImageView ivAdBack;
    private ProgressBar progressBar;
    private com.github.lzyzsd.jsbridge.BridgeWebView webView;
    private TextView tvAdReload;

    private String url;
    private String title = "内容详情";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_web);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(vis);
            }
        }
        initViews();
        initWebView();
        initData();
    }

    private void initWebView() {

    }

    /**
     * 初始化View
     */
    private void initViews() {
        tvAdTitle = findViewById(R.id.tv_ad_title);
        ivAdBack = findViewById(R.id.iv_ad_back);
        progressBar = findViewById(R.id.pb_ad);
        webView = findViewById(R.id.wb_ad);
        tvAdReload = findViewById(R.id.tv_ad_reload);


        ivAdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAdReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
    }


    /**
     * 加载数据
     */
    private void initData() {
        // 获取传来的url
        try {
            url = getIntent().getExtras().getString("url");
        } catch (Exception e) {
            onBackPressed();
            return;
        }
        // 获取传来的title
        try {
            title = getIntent().getExtras().getString("title");
        } catch (Exception e) {
            title = "内容详情";
        }
        // 设置title
        tvAdTitle.setText(title);
        // 加载网页
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }
}