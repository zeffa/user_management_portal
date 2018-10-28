package com.zeffah.usermanagementportal.app;

import com.orm.SugarApp;
import com.orm.SugarContext;

public class AppController extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
