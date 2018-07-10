package cn.rehtt;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.rehtt.Dialog.AddTaskIngDialog;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private AddTaskIngDialog addTaskIngDialog;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskIngDialog = new AddTaskIngDialog(WebViewActivity.this);
                addTaskIngDialog.show();
            }
        });
        webView=(WebView)findViewById(R.id.webView);

        webView.loadUrl(intent.getStringExtra("url"));
        webView.getSettings().setJavaScriptEnabled(false);
//        setContentView(webView);
        // 设置WebViewClient事件，这里设置的是点击链接的加载为在当前WebView中加载
        webView.setWebViewClient(new WebViewClient() {

            // 控制新连接在当前WebView中打开
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] u=new String[50];
                u=url.split(":");
                if (u[0].equals("ftp")){
                    //url复制到剪切板
                    ClipboardManager clipboardManager=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(url);
                    Toast.makeText(WebViewActivity.this,"已复制到剪切板",Toast.LENGTH_LONG).show();

                    addTaskIngDialog = new AddTaskIngDialog(WebViewActivity.this);
                    addTaskIngDialog.show();

                }else
                    view.loadUrl(url);

                return true;
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 物理返回键的点击事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果点击的是“返回”并且webView支持返回功能
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 就执行返回
            webView.goBack();
            return true;
        }else if (!webView.canGoBack()){
            WebViewActivity.this.finish();
            return true;
        }
        return false;
    }

}
