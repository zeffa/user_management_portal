package com.zeffah.usermanagementportal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.methods.GlobalMethods;
import com.zeffah.usermanagementportal.pages.UserAuthenticateFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalMethods.startFragment(this, new UserAuthenticateFragment(), false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
