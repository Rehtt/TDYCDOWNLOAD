package cn.rehtt.MainFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.rehtt.DataClass;
import cn.rehtt.Dialog.ListSelectDialog;
import cn.rehtt.DownloadListClass;
import cn.rehtt.DownloaderListClass;
import cn.rehtt.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rehtt on 2018/2/14.
 */

public class DoneFragment extends Fragment {

    private ListView listView;
    private TextView isNull;

    private DataClass dataClass = new DataClass();

    private List<String> downloaderName;
    private List<String> downloaderPid;
    private List<Boolean> isOnline;

    private String downloadList = "";
    private boolean isDownloadListLoadDone = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, null);
        listView = (ListView) view.findViewById(R.id.done_listview);
        isNull=(TextView)view.findViewById(R.id.null_done);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list;
                Gson gson;
                DownloadListClass downloadListClass;
                while (!dataClass.isStop()) {

                    list = new ArrayList<>();
                    gson = new Gson();

                    while (!dataClass.isDownloaderLoadDone()) {
                        try {
                            Log.e("qwe", "111");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    analysisJSON();
                    for (int i = 0; i < downloaderPid.size(); i++) {
                        if (isOnline.get(i)) {
                            downloadState(downloaderPid.get(i));
                            while (!isDownloadListLoadDone) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            downloadListClass = gson.fromJson(downloadList, DownloadListClass.class);
                            for (int j = 0; j < downloadListClass.getTasks().size(); j++) {
                                String speed = "";
                                if (!(downloadListClass.getTasks().get(j).getSpeed() > 1048576)) {

                                    speed = String.valueOf((float) (Math.round((float) downloadListClass.getTasks().get(j).getSpeed() / 1024 * 100)) / 100) + "Kb/s";
                                } else {
                                    speed = String.valueOf((float) (Math.round((float) downloadListClass.getTasks().get(j).getSpeed() / 1048576 * 100)) / 100) + "Mb/s";
                                }
                                list.add("任务名:" + downloadListClass.getTasks().get(j).getName() + "\n速度:" + speed + "\n下载器:" + downloaderName.get(i));
                            }
                            isDownloadListLoadDone = false;
                        }
                    }
                    final List<String> finalList = list;
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!finalList.isEmpty()) {
                                    isNull.setVisibility(View.GONE);
                                    listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, finalList));
                                }else {
                                    isNull.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                    Log.e("qwe", "获取下载中列表完成");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                DownloadListClass downloaderListClass = gson.fromJson(downloadList, DownloadListClass.class);
                new ListSelectDialog(getActivity(),downloaderListClass.getTasks().get(i).getId(),downloaderPid.get(i)).show();
                return false;
            }
        });

        return view;
    }

    private void analysisJSON() {
        isOnline = new ArrayList<>();
        downloaderPid = new ArrayList<>();
        downloaderName = new ArrayList<>();
        Gson gson = new Gson();
        DownloaderListClass downloaderListClass = gson.fromJson(dataClass.getDownloaderList(), DownloaderListClass.class);
        for (int i = 0; i < downloaderListClass.getPeerList().size(); i++) {
            downloaderName.add(downloaderListClass.getPeerList().get(i).getName());
            downloaderPid.add(downloaderListClass.getPeerList().get(i).getPid());
            if (downloaderListClass.getPeerList().get(i).getOnline() == 0) {
                isOnline.add(false);
            } else {
                isOnline.add(true);
            }
        }
    }


    //状态
    private void downloadState(String pid) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://homecloud.yuancheng.xunlei.com/";
        url = url + "list?pid=" + pid + "&type=1&pos=0&number=200&needUrl=1&v=2&ct=0&_=" + String.valueOf(System.currentTimeMillis());


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host", "homecloud.yuancheng.xunlei.com")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0")
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Referer", "http://yuancheng.xunlei.com/")
                .addHeader("Cookie", "deviceid=" + dataClass.getDeviceid() + "; referfrom=GS_144; userid=" + dataClass.getUserid() + "; appidstack=113; _x_t_=1_1; state=0; order=" + dataClass.getOrder() + "; usertype=" + dataClass.getUsertype() + "; sessionid=" + dataClass.getSessionid() + "; usernick=" + dataClass.getUsernick() + "; isvip=" + dataClass.getIsvip() + "; accessmode=10005; logintype=" + dataClass.getLogintype() + "; usernewno=" + dataClass.getUsernewno() + "; score=" + dataClass.getScore() + "; username=" + dataClass.getUsername())
                .addHeader("Connection", "keep-alive")
                .addHeader("DNT", "1")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                downloadList = response.body().string();
                Log.e("rty", downloadList);
                isDownloadListLoadDone = true;

            }
        });

    }
}
