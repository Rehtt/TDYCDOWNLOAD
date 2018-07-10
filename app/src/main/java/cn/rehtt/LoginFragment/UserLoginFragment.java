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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

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

public class UserLoginFragment extends Fragment {

    EditText user;
    EditText passwd;
    EditText verifi_code;
    ImageView verifi_img;
    Button login;
    TextView error;
    LinearLayout linearLayout_loading;
    LinearLayout linearLayout_verifi;

    String userName = "";
    String userPasswd = "";
    //是否需要验证码
    boolean needVerifi = false;
    String verifiCode = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_login,null);

        init(view);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏 错误与加载 提示
                error.setVisibility(View.GONE);
                linearLayout_loading.setVisibility(View.GONE);

                userName = user.getText().toString();
                userPasswd = passwd.getText().toString();
                verifiCode = verifi_code.getText().toString();

                if (!userPasswd.equals("") && !userName.equals("")){
                    if (!needVerifi ) {
                        toLogin();

                    }else {
                        if (verifiCode.equals("")) {
                            verifi_code.setText("");
                            verifi_code.setHint("请输入验证码");
                        }else {

                        }
                    }
                }else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    //初始化控件
    private void init(View view){
        user=(EditText)view.findViewById(R.id.user_user);
        passwd=(EditText)view.findViewById(R.id.passwd_user);
        verifi_code=(EditText)view.findViewById(R.id.verifi_edit_user);
        verifi_img=(ImageView)view.findViewById(R.id.verifi_img_user);
        login=(Button)view.findViewById(R.id.button_user);
        error=(TextView)view.findViewById(R.id.error_user);
        linearLayout_loading=(LinearLayout)view.findViewById(R.id.linearLayout_loading_user);
        linearLayout_verifi=(LinearLayout)view.findViewById(R.id.linearLayout_verifi_user);
        error.setVisibility(View.GONE);
        linearLayout_loading.setVisibility(View.GONE);
        linearLayout_verifi.setVisibility(View.GONE);
    }

    //登陆
    private void toLogin(){
        String deviceid = getArguments().getString("deviceid");
        String url = "https://login.xunlei.com/sec2login";


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("v","202")
                .add("appid","113")
                .add("appname","yuancheng.xunlei.com")
                .add("devicemodel","Firefox: 58.0")
                .add("devicename","FF")
                .add("osversion","Win64")
                .add("providername","NONE")
                .add("networktype","NONE")
                .add("sdkversion","2.0")
                .add("clientversion","NONE")
                .add("devicesign",deviceid)
                .add("platform","WEB")
                .add("u","18127068160")
                .add("p","Rehtt@643")
                .add("cachetime","1518234862990")
                .build();
        Request request =new Request.Builder()
                .url(url)
                .addHeader("Host","login.xunlei.com")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Accept-Encoding","gzip, deflate, br")
                .addHeader("Referer","http://yuancheng.xunlei.com/login.html")
                .addHeader("Cookie","appidstack=113; deviceid="+deviceid+"; _x_t_=0")
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
                boolean i = true;
                for (String q:cookie){
                    if (!q.split("=|;")[0].equals("logindetail")){
                        new DataClass().setCookie(cookie);
                        saveUserIni(cookie);
                        i = false;
                        break;
                    }
                }
                if (i){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            error.setVisibility(View.VISIBLE);
                        }
                    });
                }
                Intent intent = new Intent();
                intent.setClass(getContext(),Main2Activity.class);
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


    //显示验证框
    private void showVerifi(){
        if (needVerifi){
            linearLayout_verifi.setVisibility(View.VISIBLE);
        }else {
            linearLayout_verifi.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
