package cn.rehtt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public class SoJieXiActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView listView;

    private String value;
    private ArrayList<String> title;
    private ArrayList<String> url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_jie_xi);

        value = getIntent().getStringExtra("value");
        progressBar = (ProgressBar) findViewById(R.id.SoJieXi_dd);
        listView = (ListView) findViewById(R.id.SoJieXi_list);

        progressBar.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (url == null) {

                } else {
                    Intent intent = new Intent();
                    intent.putExtra("url", url.get(i));
                    intent.setClass(SoJieXiActivity.this, PageJieXiActivity.class);
                    startActivity(intent);
                }
            }
        });

        Search search = new Search();
        String[] ss = {"search", value};
        try {
            search.okhttp(ss, new Search.back() {
                @Override
                public void callBack(Map<String, ArrayList<String>> map) {
                    showList(map);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void showList(Map<String, ArrayList<String>> map) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putBoolean("done", true);
        if (map != null) {
            title = map.get("title");
            url = map.get("url");
        } else {
            title = new ArrayList<>();
            title.add("暂无数据");
        }
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.getData().getBoolean("done")) {
                listView.setAdapter(new ArrayAdapter<String>(SoJieXiActivity.this, android.R.layout.simple_list_item_1, title));
                progressBar.setVisibility(View.GONE);
            }

            super.handleMessage(msg);
        }
    };

}
