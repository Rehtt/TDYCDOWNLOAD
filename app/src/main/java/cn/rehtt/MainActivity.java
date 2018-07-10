package cn.rehtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String deviceid = "";
    private String userid = "";
    private String sessionid = "";
    private String usernick = "";
    private String isvip = "";
    private String logintype = "";
    private String usernewno = "";
    private String score = "";
    private String username = "";
    private String order = "";
    private String usertype ="";

    //下载器列表
    private String downloaderList = "";

    //下载器的下载列表
    private String downloadList ="";

    private String pid = "";

    //
    private Boolean isDownloaderListLoadDone = false;
    private Boolean isDownloadListLoadDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> cookie = new DataClass().getCookie();
        for (String q:cookie){
            String[] w = q.split("=|;");
            switch (w[0]){
                case "deviceid":deviceid = w[1];break;
                case "userid":userid = w[1];break;
                case "sessionid":sessionid = w[1];break;
                case "usernick":usernick = w[1];break;
                case "isvip":isvip = w[1];break;
                case "logintype":logintype = w[1];break;
                case "usernewno":usernewno =w[1];break;
                case "score":score = w[1];break;
                case "username":username = w[1];break;
                case "order":order = w[1];break;
                case "usertype":usertype =w[1];break;
                default:break;
            }
        }

        loading();


    }

    //加载
    private void loading(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //一秒请求一次
                    while (true) {
                        Gson gson =new Gson();
                        while (!isDownloaderListLoadDone) {
                            downloadState("listPeer");
                            Thread.sleep(100);
                        }
                        DownloaderListClass downloaderListClass = gson.fromJson(downloaderList,DownloaderListClass.class);
                        for (int i=0;i < downloaderListClass.getPeerList().size();i++) {
                            if (downloaderListClass.getPeerList().get(i).getOnline() != 0) {
                                pid = downloaderListClass.getPeerList().get(i).getPid();
                                while (!isDownloadListLoadDone) {
                                    downloadState("list");
                                    Thread.sleep(100);
                                }
                            }
                        }

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    //状态
    private void downloadState(final String v){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url ="http://homecloud.yuancheng.xunlei.com/";

        if (v.equals("listPeer")){
            //全部下载器信息
            url = url+"listPeer?type=0&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());
        }else {
            //下载器下载速度
            url = url+"list?pid="+pid+"&type=0&pos=0&number=10&needUrl=1&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());
        }
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Host","homecloud.yuancheng.xunlei.com")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .addHeader("Accept","*/*")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Referer","http://yuancheng.xunlei.com/")
                .addHeader("Cookie","deviceid="+deviceid+"; referfrom=GS_144; userid="+userid+"; appidstack=113; _x_t_=1_1; state=0; order="+order+"; usertype="+usertype+"; sessionid="+sessionid+"; usernick="+usernick+"; isvip="+isvip+"; accessmode=10005; logintype="+logintype+"; usernewno="+usernewno+"; score="+score+"; username="+username)
                .addHeader("Connection","keep-alive")
                .addHeader("DNT","1")
                .addHeader("Pragma","no-cache")
                .addHeader("Cache-Control","no-cache")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (v.equals("listPeer")){
                    downloaderList = response.body().string();
                    isDownloaderListLoadDone = true;
                }
                else {
                    downloadList = response.body().string();
                    isDownloadListLoadDone = true;
                }
            }
        });
    }




}
