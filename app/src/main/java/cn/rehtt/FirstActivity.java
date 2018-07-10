package cn.rehtt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirstActivity extends AppCompatActivity {


    private boolean isLoading = true;

    private String loginkey = null;
    private String userid = null;
    private String deviceid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //判断是否有deviceid
        if (!isDeviceid()){
            getDeviceId();
        }else {
            isLoading = false;
        }

        //若获取到id就跳转
        new Thread(new Runnable() {
            @Override
            public void run() {
                //阻塞线程直到加载完
                while (isLoading){

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //停留两秒
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //若存在用户信息则自动登录并调至主页
                if (isSave()){
                    login();

                }else {
                    toLoginActivity();
                }

            }
        }).start();


    }


    //判断deviceid是否存在
    public boolean isDeviceid(){
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        deviceid = sharedPreferences.getString("deviceid",null);
        if (deviceid == null){
            return false;
        }else {
            return true;
        }

    }

    //判断是否有保存用户信息
    private boolean isSave(){
        SharedPreferences sharedPreferences =getSharedPreferences("data",MODE_PRIVATE);
        userid = sharedPreferences.getString("userid",null);
        if (userid != null){
            loginkey = sharedPreferences.getString("loginkey",null);
            return true;
        }
        return false;
    }

    //跳转到登陆页面
    private void toLoginActivity(){
        Intent intent =new Intent();
        intent.setClass(this,LoginActivity.class);
        startActivity(intent);
        this.finish();

    }



    //获取deviceid
    private void getDeviceId(){
        String cachetime = String.valueOf(System.currentTimeMillis());
        String xl_fp_raw = "TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NDsgcnY6NTguMCkgR2Vja28vMjAxMDAxMDEgRmlyZWZveC81OC4wIyMjemgtQ04jIyMyNCMjIzg2NHgxNTM2IyMjLTQ4MCMjI3RydWUjIyN0cnVlIyMjdHJ1ZSMjI3VuZGVmaW5lZCMjI3VuZGVmaW5lZCMjIyMjI1dpbjY0IyMjMSMjI1Nob2Nrd2F2ZSBGbGFzaDo6U2hvY2t3YXZlIEZsYXNoIDI3LjAgcjA6OmFwcGxpY2F0aW9uL3gtc2hvY2t3YXZlLWZsYXNofnN3ZixhcHBsaWNhdGlvbi9mdXR1cmVzcGxhc2h+c3BsIyMjZjQ2ZjlmZmE5Zjk2MDM0OGUxNjZkNDVmYWE0MzU0M2Y=";
        String xl_fp = "3e2e4572e7988127a67eb464e2153f81";
        String xl_fp_sign = "4930e2666c2e8ca1b066e66419c1a177";

        OkHttpClient okHttpClient =new OkHttpClient();
        String url = "https://login.xunlei.com/risk?cmd=report";
        RequestBody body = new FormBody.Builder()
                .add("cachetime",cachetime)
                .add("xl_fp",xl_fp)
                .add("xl_fp_raw",xl_fp_raw)
                .add("xl_fp_sign",xl_fp_sign)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host","login.xunlei.com")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate, br")
                .addHeader("Referer","http://i.xunlei.com/login/?r_d=1&use_cdn=0&timestamp="+cachetime+"&refurl=http%3A%2F%2Fyuancheng.xunlei.com%2Flogin.html")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Content-Length","537")
                .addHeader("Cookie","appidstack=113; _x_t_=0")
                .addHeader("DNT","1")
                .addHeader("Connection","keep-alive")
                .addHeader("Upgrade-Insecure-Requests","1")
                .addHeader("Pragma","no-cache")
                .addHeader("Cache-Control","no-cache")
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"请开启网络重启软件后重试",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int i = 0;
                List<String> cookie = response.headers("Set-Cookie");
                for (String q:cookie){
                    if (q.split("=|;")[0].equals("deviceid")){
                        saveUserIni(cookie);
                        isLoading = false;
                        i = 1;
                        break;
                    }
                }
                if (i == 0){
                    Log.e("FirstActivity_getDeviceId()","network error");

                }
            }
        });


    }
    //保存cookie信息
    public void saveUserIni(List<String> cookie){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        for (String q:cookie){
            String[] w = q.split("=|;");
            editor.putString(w[0],w[1]);
        }
        editor.commit();
    }

    //登录
    private void login(){
        String url = "https://login.xunlei.com/loginkeylogin/";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("business_type","113")
                .add("cachetime", String.valueOf(System.currentTimeMillis()))
                .add("loginkey",loginkey)
                .add("userid",userid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host","login.xunlei.com")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate, br")
                .addHeader("Referer","http://yuancheng.xunlei.com/login.html")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Cookie","deviceid="+deviceid+"; referfrom=GS_144; userid="+userid+"; appidstack=113")
                .addHeader("DNT","1")
                .addHeader("Connection","keep-alive")
                .addHeader("Upgrade-Insecure-Requests","1")
                .addHeader("Pragma","no-cache")
                .addHeader("Cache-Control","no-cache")
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new DataClass().setCookie(response.headers("Set-Cookie"));

                Intent intent = new Intent();
                intent.setClass(FirstActivity.this,Main2Activity.class);
                startActivity(intent);
                FirstActivity.this.finish();
                new DataClass().setStop(false);
            }
        });
    }

}
