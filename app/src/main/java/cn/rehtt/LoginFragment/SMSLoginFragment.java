package cn.rehtt.LoginFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.rehtt.DataClass;
import cn.rehtt.Main2Activity;
import cn.rehtt.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Rehtt on 2018/2/6.
 */

public class SMSLoginFragment extends Fragment {

    TextView error;
    EditText phone;
    EditText verifi_edit;
    Button getVerifi;
    Button login;
    LinearLayout linearLayout;

    String phoneNumber = "";
    String verifi = "";
    String token = null;

    boolean verifiButton = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms_login,null);

        init(view);

        getVerifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber =phone.getText().toString();
                //隐藏错误与加载提示
                error.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);

                if (isPhoneNumber(phoneNumber)){
                    sendSMS();
                    getVerifiButtonText();
                }else {
                    phone.setText("");
                    phone.setHint("请输入正确的号码");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber =phone.getText().toString();
                verifi = verifi_edit.getText().toString();
                //隐藏错误与加载提示
                error.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);

                if (isPhoneNumber(phoneNumber) && !verifi.equals("")){

                    toLogin();
                }else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    //初始化
    private void init(View view) {
        phone=(EditText)view.findViewById(R.id.phone_sms);
        verifi_edit=(EditText)view.findViewById(R.id.verifi_edit_sms);
        getVerifi=(Button)view.findViewById(R.id.getVerifi_sms);
        login=(Button)view.findViewById(R.id.button_sms);
        linearLayout=(LinearLayout)view.findViewById(R.id.linearLayout_loading_sms);
        error=(TextView)view.findViewById(R.id.error_sms);
        error.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }

    //判断是否为手机号（国内）
    private boolean isPhoneNumber(String number){
        String pattern = "0?(13|14|15|18)[0-9]{9}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);
        return m.matches();
    }

    //获取验证码按钮倒计时
    private void getVerifiButtonText(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 60;

                while (time >=0 ){
                    final int i=time;
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getVerifi.setText(String.valueOf(i));
                                if (i != 0)
                                    getVerifi.setEnabled(false);
                                else {
                                    getVerifi.setText("获取验证码");
                                    getVerifi.setEnabled(true);
                                }
                            }
                        });
                    }
                    time--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    //发送验证码
    private void sendSMS(){
        String deviceid = getArguments().getString("deviceid");
        String url = "https://login.xunlei.com/sendsms";


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("v","202")
                .add("appid","113")
                .add("appname","yuancheng.xunlei.com")
                .add("devicemodel","Firefox: 58.0")
                .add("clientversion","NONE")
                .add("devicename","FF")
                .add("devicesign",deviceid)
                .add("cachetime", String.valueOf(System.currentTimeMillis()))
                .add("mobile",phoneNumber)
                .add("networktype","NONE")
                .add("osversion","Win64")
                .add("platform","WEB")
                .add("providername","NONE")
                .add("sdkversion","2.0")
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
//                .addHeader("Content-Length","309")
                .addHeader("Cookie","appidstack=113; _x_t_=0; deviceid="+deviceid)
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
                List<String> q=response.headers("Set-Cookie");
                String[] p = new String[q.size()];
                q.toArray(p);
                token = String.valueOf(p[2].split("=|;")[1]);

            }
        });
    }

    //登陆
    private void toLogin(){
        String deviceid = getArguments().getString("deviceid");
        String url = "https://login.xunlei.com/smslogin";


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("v","202")
                .add("appid","113")
                .add("appname","yuancheng.xunlei.com")
                .add("devicemodel","Firefox: 58.0")
                .add("clientversion","NONE")
                .add("devicename","FF")
                .add("devicesign",deviceid)
                .add("cachetime", String.valueOf(System.currentTimeMillis()))
                .add("mobile",phoneNumber)
                .add("networktype","NONE")
                .add("osversion","Win64")
                .add("platform","WEB")
                .add("providername","NONE")
                .add("sdkversion","2.0")
                .add("token",token)
                .add("smscode",verifi)
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
//                .addHeader("Content-Length","309")
                .addHeader("Cookie","appidstack=113; _x_t_=0; deviceid="+deviceid+"; token="+token)
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
                List<String> cookie = response.headers("Set-Cookie");
                new DataClass().setCookie(cookie);
                saveUserIni(cookie);

                Intent intent = new Intent();
                intent.setClass(getContext(), Main2Activity.class);
                startActivity(intent);
                getActivity().finish();
                new DataClass().setStop(false);

            }
        });
    }

    //保存cookie信息
    public void saveUserIni(List<String> cookie){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        for (String q:cookie){
            String[] w = q.split("=|;");
            if (w[0].equals("loginkey") || w[0].equals("deviceid") || w[0].equals("userid"))
                editor.putString(w[0],w[1]);
        }
        editor.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
