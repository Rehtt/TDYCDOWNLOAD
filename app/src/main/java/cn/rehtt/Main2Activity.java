package cn.rehtt;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import cn.rehtt.Dialog.AboutDialog;
import cn.rehtt.MainFragment.DoneFragment;
import cn.rehtt.MainFragment.DownloaderFragment;
import cn.rehtt.MainFragment.IngFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DataClass dataClass = new DataClass();
    //下载器列表
    private String downloaderList = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /////////////////////////////////////////////////////////////////////////////////////////

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main2);
        TextView name = (TextView)headerLayout.findViewById(R.id.name);
        TextView isvip = (TextView)headerLayout.findViewById(R.id.isvip);

        List<String> cookie = dataClass.getCookie();
        for (String q:cookie){
            String[] w = q.split("=|;");
            switch (w[0]){
                case "deviceid":dataClass.setDeviceid(w[1]);break;
                case "userid":dataClass.setUserid(w[1]);break;
                case "sessionid":dataClass.setSessionid(w[1]);break;
                case "usernick":dataClass.setUsernick(w[1]);break;
                case "isvip":dataClass.setIsvip(w[1]);break;
                case "logintype":dataClass.setLogintype(w[1]);break;
                case "usernewno":dataClass.setUsernewno(w[1]);break;
                case "score":dataClass.setScore(w[1]);break;
                case "username":dataClass.setUsername(w[1]);break;
                case "order":dataClass.setOrder(w[1]);break;
                case "usertype":dataClass.setUsertype(w[1]);break;
                default:break;
            }
        }
        name.setText("Hi，"+dataClass.getUsernick());
        if (dataClass.getIsvip().equals("0")){
            isvip.setText("非迅雷会员");
        }else {
            isvip.setText("迅雷会员");
        }
        loading();



        replaceFragment(new IngFragment());

        appUseCount();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main2,fragment);
        transaction.commit();
    }


    //加载
    private void loading(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //一秒请求一次
                    while (!dataClass.isStop()) {
                        Gson gson =new Gson();
//                        while (!dataClass.isDownloaderLoadDone()) {
                            downloadState();
//                            Thread.sleep(500);
//                        }

                        Log.e("asd","循环结束");
                        Thread.sleep(5000);
                        dataClass.setDownloaderLoadDone(false);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    //状态
    private void downloadState(){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url ="http://homecloud.yuancheng.xunlei.com/";

        url = url+"listPeer?type=0&v=2&ct=0&_="+String.valueOf(System.currentTimeMillis());


        Request request = new Request.Builder()
                .url(url)
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
                downloaderList = response.body().string();
                dataClass.setDownloaderList(downloaderList);
                Log.e("qwe1", downloaderList);
                dataClass.setDownloaderLoadDone(true);

            }
        });
    }


    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
        && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //使用计数
    private String uuid;
    private void appUseCount(){
        String applicationId = getApplicationInfo().packageName;

        SharedPreferences mShare = getSharedPreferences("uuid",MODE_PRIVATE);
        if(mShare != null){
            uuid = mShare.getString("uuid", "");
        }
        if(TextUtils.isEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString("uuid",uuid).commit();
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://app.rehtt.com/aaa.php?a="+applicationId+"&b="+uuid;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this,SoActivity.class);
            startActivity(intent);
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ing) {
            // Handle the camera action
            replaceFragment(new IngFragment());
        } else if (id == R.id.nav_done) {

            replaceFragment(new DoneFragment());
        } else if (id == R.id.nav_downloader) {

            replaceFragment(new DownloaderFragment());
        } else if (id == R.id.nav_exitLogin) {

            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putString("loginkey",null);
            editor.putString("deviceid",null);
            editor.putString("userid",null);
            editor.commit();
            dataClass.setStop(true);
            Intent intent = new Intent();
            intent.setClass(this,FirstActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_about) {
            AboutDialog aboutDialog = new AboutDialog(this);
            aboutDialog.show();

        }else if (id == R.id.exit){
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
