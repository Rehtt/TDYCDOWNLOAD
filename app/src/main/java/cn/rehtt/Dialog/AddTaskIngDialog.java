package cn.rehtt.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.rehtt.BoxSpaceClass;
import cn.rehtt.CreateTaskClass;
import cn.rehtt.DataClass;
import cn.rehtt.DownloaderListClass;
import cn.rehtt.R;
import cn.rehtt.SettingsClass;
import cn.rehtt.UrlResolveClass;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Rehtt on 2018/2/16.
 */

public class AddTaskIngDialog extends Dialog {
    private Context mcontext;
    private DataClass dataClass;
    private DownloaderListClass downloaderListClass;
    private BoxSpaceClass boxSpaceClassl;

    private List<String> downloaderPid ;
    private List<String> downloaderName;
    private List<Boolean> isOnline;
    private String settings;
    private String space = "";
    private String path = "";
    private String apid= "";
    private String defaultPath = "";
    private String name = "";
    private long fileSize = 0;

    private Spinner spinner;
    private TextView online;
    private TextView downloadInfo;
    private TextView taskInfo;
    private EditText editText;
    private Button button;

    private Handler handler;

    private Gson gson;

    public AddTaskIngDialog(@NonNull Context context) {
        super(context);
        mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_ing_dialog);

        spinner = (Spinner)findViewById(R.id.add_task_spinner_dialog);
        online = (TextView)findViewById(R.id.add_task_isonlien);
        downloadInfo = (TextView)findViewById(R.id.add_task_downloader_info);
        taskInfo = (TextView)findViewById(R.id.add_task_task_info);
        editText = (EditText)findViewById(R.id.add_url);
        button = (Button)findViewById(R.id.add_task_button_dialog);

        analysisJSON();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_dropdown_item_1line,downloaderName);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                downloadInfo.setText("");
                taskInfo.setText("");
                if (isOnline.get(i)){
                    online.setText("下载器在线");
                    online.setTextColor(Color.GREEN);
                    apid = downloaderPid.get(i);
                    downloadState(apid,1);
                }else {
                    online.setText("下载器离线");
                    online.setTextColor(Color.RED);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        float sp =(float)Math.round(Float.parseFloat(space)/1073741824*100)/100;
                        String info = "路径："+path+"\n" +
                            "剩余空间："+String.valueOf(sp)+"G";
                        downloadInfo.setText(info);break;
                    case 1:
                        Toast.makeText(mcontext,"空间不够",Toast.LENGTH_LONG).show();break;
                    case 2:
                        Toast.makeText(mcontext,"重复任务",Toast.LENGTH_LONG).show();break;
                    case 3:
                        Toast.makeText(mcontext,"抽风了",Toast.LENGTH_LONG).show();break;
                    case 4:
                        Toast.makeText(mcontext,"任务添加成功",Toast.LENGTH_LONG).show();break;
                    default:break;
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadState(apid,3);
            }
        });

    }

    private void analysisJSON(){

        isOnline = new ArrayList<>();
        downloaderPid = new ArrayList<>();
        downloaderName = new ArrayList<>();
        Gson gson = new Gson();
        DownloaderListClass downloaderListClass = gson.fromJson(dataClass.getDownloaderList(),DownloaderListClass.class);
        for (int i=0;i<downloaderListClass.getPeerList().size();i++){
            downloaderName.add(downloaderListClass.getPeerList().get(i).getName());
            downloaderPid.add(downloaderListClass.getPeerList().get(i).getPid());
            if (downloaderListClass.getPeerList().get(i).getOnline() == 0){
                isOnline.add(false);
            }else {
                isOnline.add(true);
            }
        }

    }

    //状态
    private void downloadState(final String pid, final int on){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://homecloud.yuancheng.xunlei.com/";

        switch (on){
            case 1:url = url+"boxSpace?pid="+pid+"&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());break;
            case 2:url = url+"settings?pid="+pid+"&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());break;
            case 3:url = url+"urlResolve?pid="+pid+"&v=2&ct=0";break;
            case 4:url = url+"createTask?pid="+pid+"&v=2&ct=0";break;
            default:break;
        }
        Request request;

        if (on == 3) {
            RequestBody body = new FormBody.Builder()
                    .add("json","{\"url\":\""+editText.getText().toString().trim()+"\"}")
                    .build();
            request= new Request.Builder()
                    .url(url)
                    .addHeader("Host","homecloud.yuancheng.xunlei.com")
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                    .addHeader("Accept","*/*")
                    .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                    .addHeader("Accept-Encoding","gzip, deflate")
                    .addHeader("Referer","http://yuancheng.xunlei.com/")
                    .addHeader("Cookie","deviceid="+dataClass.getDeviceid()+"; referfrom=GS_144; appidstack=113; _x_t_=1_1; state=0; order="+dataClass.getOrder()+"; usertype="+dataClass.getUsertype()+"; sessionid="+dataClass.getSessionid()+"; upgrade=; usernick="+dataClass.getUsernick()+"; score="+dataClass.getScore()+"; logintype="+dataClass.getLogintype()+"; isvip="+dataClass.getIsvip()+"; username="+dataClass.getUsername()+"; usernewno="+dataClass.getUsernewno()+"; accessmode=10005; userid="+dataClass.getUserid())
                    .addHeader("Connection","keep-alive")
                    .addHeader("DNT","1")
                    .addHeader("Pragma","no-cache")
                    .addHeader("Cache-Control","no-cache")
                    .post(body)
                    .build();
        }else if (on ==4){
            String json = "{\"path\":\""+defaultPath+"\",\"tasks\":[{\"url\":\""+editText.getText().toString().trim()+"\",\"name\":\""+name+"\",\"gcid\":\"\",\"cid\":\"\",\"filesize\":"+String.valueOf(fileSize)+",\"ext_json\":{\"autoname\":1}}]}";

            RequestBody body = new FormBody.Builder()
                    .add("json",json)
                    .build();
            request= new Request.Builder()
                    .url(url)
                    .addHeader("Host","homecloud.yuancheng.xunlei.com")
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                    .addHeader("Accept","*/*")
                    .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                    .addHeader("Accept-Encoding","gzip, deflate")
                    .addHeader("Referer","http://yuancheng.xunlei.com/")
                    .addHeader("Cookie","deviceid="+dataClass.getDeviceid()+"; referfrom=GS_144; appidstack=113; _x_t_=1_1; state=0; order="+dataClass.getOrder()+"; usertype="+dataClass.getUsertype()+"; sessionid="+dataClass.getSessionid()+"; upgrade=; usernick="+dataClass.getUsernick()+"; score="+dataClass.getScore()+"; logintype="+dataClass.getLogintype()+"; isvip="+dataClass.getIsvip()+"; username="+dataClass.getUsername()+"; usernewno="+dataClass.getUsernewno()+"; accessmode=10005; userid="+dataClass.getUserid())
                    .addHeader("Connection","keep-alive")
                    .addHeader("DNT","1")
                    .addHeader("Pragma","no-cache")
                    .addHeader("Cache-Control","no-cache")
                    .post(body)
                    .build();

        }
        else {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("Host", "homecloud.yuancheng.xunlei.com")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                    .addHeader("Accept", "*/*")
                    .addHeader("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Referer", "http://yuancheng.xunlei.com/")
                    .addHeader("Cookie", "deviceid=" + dataClass.getDeviceid() + "; referfrom=GS_144; appidstack=113; _x_t_=1_1; state=0; order=" + dataClass.getOrder() + "; usertype=" + dataClass.getUsertype() + "; sessionid=" + dataClass.getSessionid() + "; upgrade=; usernick=" + dataClass.getUsernick() + "; score=" + dataClass.getScore() + "; logintype=" + dataClass.getLogintype() + "; isvip=" + dataClass.getIsvip() + "; username=" + dataClass.getUsername() + "; usernewno=" + dataClass.getUsernewno() + "; accessmode=10005; userid=" + dataClass.getUserid())
                    .addHeader("Connection", "keep-alive")
                    .addHeader("DNT", "1")
                    .addHeader("Pragma", "no-cache")
                    .addHeader("Cache-Control", "no-cache")
                    .build();
        }
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseV = response.body().string();
                if (on == 1){
                    gson = new Gson();
                    boxSpaceClassl=gson.fromJson(responseV,BoxSpaceClass.class);
                    space = boxSpaceClassl.getSpace().get(0).getRemain();
                    path = boxSpaceClassl.getSpace().get(0).getPath();
                    handler.sendEmptyMessage(0);
                    downloadState(pid,2);
                }else if (on == 3){
                    gson = new Gson();
                    UrlResolveClass urlResolveClass = gson.fromJson(responseV,UrlResolveClass.class);
                    if (Long.valueOf(space) > urlResolveClass.getTaskInfo().getSize()){
                        name = urlResolveClass.getTaskInfo().getName();
                        fileSize = urlResolveClass.getTaskInfo().getSize();
                        downloadState(pid,4);
                    }else {
                        handler.sendEmptyMessage(1);
                    }

                }else if (on == 2){
                    gson = new Gson();
                    SettingsClass settingsClass = gson.fromJson(responseV,SettingsClass.class);
                    defaultPath = settingsClass.getDefaultPath();

                }else if (on == 4){
                    gson = new Gson();
                    CreateTaskClass createTaskClass = gson.fromJson(responseV,CreateTaskClass.class);
                    if (createTaskClass.getTasks().get(0).getResult() == 0){
                        dismiss();
                        handler.sendEmptyMessage(4);
                    }else if (createTaskClass.getTasks().get(0).getResult() == 202){
                        handler.sendEmptyMessage(2);

                    }else {
                        handler.sendEmptyMessage(3);
                    }
                }
            }
        });
    }


//    private void task(){
//        OkHttpClient okHttpClient = new OkHttpClient();
//        String url = "http://homecloud.yuancheng.xunlei.com/";
//
//        switch (on){
//            case 1:url = url+"boxSpace?pid="+pid+"&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());break;
//            case 2:url = url+"settings?pid="+pid+"&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());break;
//            default:break;
//        }
//
//        Request request = new Request.Builder()
//                .url(url)
//                .addHeader("Host","homecloud.yuancheng.xunlei.com")
//                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
//                .addHeader("Accept","*/*")
//                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
//                .addHeader("Accept-Encoding","gzip, deflate")
//                .addHeader("Referer","http://yuancheng.xunlei.com/")
//                .addHeader("Cookie","deviceid="+dataClass.getDeviceid()+"; referfrom=GS_144; appidstack=113; _x_t_=1_1; state=0; order="+dataClass.getOrder()+"; usertype="+dataClass.getUsertype()+"; sessionid="+dataClass.getSessionid()+"; upgrade=; usernick="+dataClass.getUsernick()+"; score="+dataClass.getScore()+"; logintype="+dataClass.getLogintype()+"; isvip="+dataClass.getIsvip()+"; username="+dataClass.getUsername()+"; usernewno="+dataClass.getUsernewno()+"; accessmode=10005; userid="+dataClass.getUserid())
//                .addHeader("Connection","keep-alive")
//                .addHeader("DNT","1")
//                .addHeader("Pragma","no-cache")
//                .addHeader("Cache-Control","no-cache")
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseV = response.body().string();
//                if (on == 1){
//                    gson = new Gson();
//                    boxSpaceClassl=gson.fromJson(responseV,BoxSpaceClass.class);
//                    space = boxSpaceClassl.getSpace().get(0).getRemain();
//                    path = boxSpaceClassl.getSpace().get(0).getPath();
//                    handler.sendEmptyMessage(0);
//                    downloadState(pid,2);
//                }else {
//
//                }
//            }
//        });
//    }



}
