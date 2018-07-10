package cn.rehtt;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.rehtt.LoginFragment.SMSLoginFragment;
import cn.rehtt.LoginFragment.UserLoginFragment;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;

    UserLoginFragment userLoginFragment;
    SMSLoginFragment smsLoginFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout=(TabLayout)findViewById(R.id.login_tab);


        setFragment(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition=tab.getPosition();
                setFragment(tabPosition);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //设置Fragment
    private void setFragment(int position){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //只能add一次
        if (userLoginFragment == null && smsLoginFragment ==null) {
            userLoginFragment = new UserLoginFragment();
            smsLoginFragment = new SMSLoginFragment();
            //传deviceId
            SharedPreferences sharedPreferences =getSharedPreferences("data",MODE_PRIVATE);
            String deviceid = sharedPreferences.getString("deviceid",null);
            if (deviceid != null) {
                Bundle bundle = new Bundle();
                bundle.putString("deviceid", deviceid);
                userLoginFragment.setArguments(bundle);
                smsLoginFragment.setArguments(bundle);
            }

            fragmentTransaction.add(R.id.show, userLoginFragment);
            fragmentTransaction.add(R.id.show, smsLoginFragment);

        }
        switch (position){
            case 0:
                fragmentTransaction.hide(smsLoginFragment);
                fragmentTransaction.show(userLoginFragment);
                break;

            case 1:
                fragmentTransaction.hide(userLoginFragment);
                fragmentTransaction.show(smsLoginFragment);
                break;

            default:break;

        }
        fragmentTransaction.commit();

    }


}
