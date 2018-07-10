package cn.rehtt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class SoActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView dytt;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioGroup radioGroup;

    private int soType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so);

        editText=(EditText)findViewById(R.id.editText_so);
        button=(Button)findViewById(R.id.button_so);
//        radioButton=(RadioButton)findViewById(R.id.radioButton);
//        radioButton2=(RadioButton)findViewById(R.id.radioButton2);
        radioGroup=(RadioGroup)findViewById(R.id.radio_so);
        dytt=(TextView)findViewById(R.id.dytt);
        dytt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toWebView("http://www.dytt8.net","电影天堂");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url;
                    if (soType==1) {
                        url = "https://www.baidu.com/s?wd="+editText.getText().toString()+" 电影天堂";
                    }else if(soType==2){
                        String soName = java.net.URLEncoder.encode(editText.getText().toString(), "gb2312");
                        url = "http://s.ygdy8.com/plus/so.php?kwtype=0&keyword=" + soName;
                    }else {
                        url = "";
                    }
                    toWebView(url,"搜索\""+editText.getText().toString()+"\"");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton:
                        soType=1;
                        break;

                    case R.id.radioButton2:
                        soType=2;
                        break;

                    default:break;
                }
            }
        });

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void toWebView(String url,String title){
        Intent intent = new Intent();
        intent.setClass(SoActivity.this,WebViewActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
