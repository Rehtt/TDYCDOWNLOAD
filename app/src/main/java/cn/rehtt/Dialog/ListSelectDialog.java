package cn.rehtt.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import cn.rehtt.DataClass;
import cn.rehtt.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListSelectDialog extends Dialog {
    String id;
    Context context;
    String pid;
    ImageView imageView;
    public ListSelectDialog(@NonNull Context context,String id,String pid) {
        super(context);
        this.id = id;
        this.context = context;
        this.pid = pid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_select);
        imageView=(ImageView)findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile();
            }
        });

    }


    private void deleteFile(){
        DataClass dataClass = new DataClass();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://homecloud.yuancheng.xunlei.com/del?pid="+pid+"&tasks="+id+"_0&recycleTask=1&deleteFile=true&v=2&ct=0")
//            .header("Accept","*/*")
//            .header("Accept-Encoding","gzip, deflate")
//            .header("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
//            .header("Cache-Control","no-cache")
//            .header("Connection","keep-alive")
//            .header("Cookie","deviceid="+dataClass.getDeviceid()+"; referfrom=GS_144; userid="+dataClass.getUserid()+"; appidstack=113; _x_t_=1_1; state=0; order="+dataClass.getOrder()+"; usertype="+dataClass.getUsertype()+"; sessionid="+dataClass.getSessionid()+"; usernick="+dataClass.getUsernick()+"; isvip="+dataClass.getIsvip()+"; accessmode=10005; logintype="+dataClass.getLogintype()+"; usernewno="+dataClass.getUsernewno()+"; score="+dataClass.getScore()+"; username="+dataClass.getUsername())
//            .header()
                .addHeader("Host","homecloud.yuancheng.xunlei.com")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .addHeader("Accept","*/*")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Referer","http://yuancheng.xunlei.com/")
                .addHeader("Cookie","deviceid="+dataClass.getDeviceid()+"; referfrom=GS_144; userid="+dataClass.getUserid()+"; appidstack=113; _x_t_=1_1; state=0; order="+dataClass.getOrder()+"; usertype="+dataClass.getUsertype()+"; sessionid="+dataClass.getSessionid()+"; usernick="+dataClass.getUsernick()+"; isvip="+dataClass.getIsvip()+"; accessmode=10005; logintype="+dataClass.getLogintype()+"; usernewno="+dataClass.getUsernewno()+"; score="+dataClass.getScore()+"; username="+dataClass.getUsername())
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
                dismiss();
            }
        });
    }
}
