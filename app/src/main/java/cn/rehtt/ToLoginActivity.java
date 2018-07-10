package cn.rehtt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ToLoginActivity extends AppCompatActivity {

    WebView webView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_login);
        context=this;
        webView=(WebView)findViewById(R.id.login_web);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.requestFocusFromTouch();
        webView.loadUrl("http://yuancheng.xunlei.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("url",request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
}
