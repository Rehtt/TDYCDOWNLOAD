package cn.rehtt.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import cn.rehtt.R;

/**
 * Created by Rehtt on 2018/2/25.
 */

public class AboutDialog extends Dialog {

    Context mcontext;

    public AboutDialog(@NonNull Context context) {
        super(context);
        mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_dialog);
    }
}
