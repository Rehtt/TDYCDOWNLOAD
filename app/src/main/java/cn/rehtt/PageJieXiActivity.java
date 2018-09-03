package cn.rehtt;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import cn.rehtt.Dialog.AddTaskIngDialog;


public class PageJieXiActivity extends AppCompatActivity {

    private AddTaskIngDialog addTaskIngDialog;

    private ProgressBar progressBar;
    private ListView listView;
    private String rurl;

    private ArrayList<String> url = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_jie_xi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskIngDialog = new AddTaskIngDialog(PageJieXiActivity.this);
                addTaskIngDialog.show();
            }
        });
        rurl = getIntent().getStringExtra("url");

        progressBar = (ProgressBar) findViewById(R.id.pageJieXi_dd);
        listView = (ListView) findViewById(R.id.pageJieXi_list);
        progressBar.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //url复制到剪切板
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(url.get(i));
                handler.sendEmptyMessage(2);
            }
        });

        Search search = new Search();
        String[] ss = {"info", rurl};
        try {
            search.okhttp(ss, new Search.back() {
                @Override
                public void callBack(Map<String, ArrayList<String>> map) {
                    show(map);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void show(Map<String, ArrayList<String>> map) {
        if (map != null) {
            for (String i : map.get("url")) {
                url.add(i);
            }
        } else {
            url.add("暂无数据");
        }
        handler.sendEmptyMessage(1);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                progressBar.setVisibility(View.GONE);
                listView.setAdapter(new ArrayAdapter<String>(PageJieXiActivity.this, android.R.layout.simple_list_item_1, url));
            } else if (msg.what == 2) {
                Toast.makeText(PageJieXiActivity.this, "已复制到剪切板", Toast.LENGTH_LONG).show();

                addTaskIngDialog = new AddTaskIngDialog(PageJieXiActivity.this);
                addTaskIngDialog.show();
            }
            super.handleMessage(msg);
        }
    };


}
