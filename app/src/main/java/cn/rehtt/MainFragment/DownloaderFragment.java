package cn.rehtt.MainFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.rehtt.DataClass;
import cn.rehtt.DownloaderListClass;
import cn.rehtt.R;

/**
 * Created by Rehtt on 2018/2/14.
 */

public class DownloaderFragment extends Fragment {
    private DataClass dataClass = new DataClass();

    private ListView listView;
    private TextView isNull;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloader,null);

        listView=(ListView)view.findViewById(R.id.downloaderList);
        isNull=(TextView)view.findViewById(R.id.null_downloader);
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"功能完善中",Toast.LENGTH_LONG).show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                DownloaderListClass downloaderListClass;

                while (!dataClass.isStop()){
                    if (dataClass.isDownloaderLoadDone()){
                        final List<String> list = new ArrayList<>();
                        downloaderListClass = gson.fromJson(dataClass.getDownloaderList(),DownloaderListClass.class);
                        for (int i=0;i<downloaderListClass.getPeerList().size();i++){
                            String isOnline = "";
                            if (downloaderListClass.getPeerList().get(i).getOnline() == 0 ){
                                isOnline = "离线";
                            }
                            else {
                                isOnline = "在线";
                            }

                            String v="机器名:"+downloaderListClass.getPeerList().get(i).getName()+"\n" +
                                    isOnline+"\n" +
                                    "本地ip:"+downloaderListClass.getPeerList().get(i).getLocalIP();
                            list.add(v);
                        }
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!list.isEmpty()) {
                                        isNull.setVisibility(View.GONE);
                                        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list));
                                    }else {
                                        isNull.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        return view;
    }
}
