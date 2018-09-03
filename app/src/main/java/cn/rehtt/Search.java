package cn.rehtt;

import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    private String urlHost = "http://s.ygdy8.com";

    private String searc = "";

    public interface back {
        void callBack(Map<String, ArrayList<String>> map);
    }

    private back back;


    public void okhttp(final String[] chuang, final back back) throws UnsupportedEncodingException {
        OkHttpClient okHttpClient = new OkHttpClient();
        searc = chuang[1];
        String url = "";
        if (chuang[0] == "search") {
            url += urlHost + "/plus/so.php?kwtype=0&searchtype=title&keyword=" + URLEncoder.encode(chuang[1], "gb2312");
        } else if (chuang[0] == "info") {
            url = chuang[1];
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (chuang[0] == "search") {
                    back.callBack(search(response.body().bytes()));
                } else if (chuang[0] == "info") {
                    back.callBack(info(response.body().bytes()));
                }
            }
        });
    }

    //
    private Map<String, ArrayList<String>> search(byte[] searchString) throws UnsupportedEncodingException {
        String res = new String(searchString, "GB2312");
        String[] line = res.split("\n");

        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> url = new ArrayList<>();
        for (String i : line) {
            if (patter(i, searc)) {
                if (patter(i, "href")) {
                    title.add(i.replaceAll("[^0-9A-Z\u4e00-\u9fa5.，,。？“”]+", "").split("\\.", 2)[1]);
                    url.add(urlHost + i.split("href='")[1].split("'>")[0]);

                }
            }
        }

        if (title.isEmpty() || url.isEmpty()) {
            return null;
        } else {
            Map<String, ArrayList<String>> info = new HashMap<>();
            info.put("title", title);
            info.put("url", url);
            return info;
        }

    }

    private Map<String, ArrayList<String>> info(byte[] infoString) throws UnsupportedEncodingException {
        String res = new String(infoString, "GB2312");
        String[] line = res.split("\n");
        ArrayList<String> url=new ArrayList<>();
        for (String i : line) {
            if (patter(i, "ftp")) {
                url.add(i.split("\">")[2].split("</a></td>")[0]);
            }
        }
        if (url.isEmpty()){
            return null;
        }else {
            Map<String, ArrayList<String>> map = new HashMap<>();
            map.put("url", url);
            return map;
        }
    }

    private boolean patter(String s, String pattern) {
        String str = s;

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.find();
    }
}
