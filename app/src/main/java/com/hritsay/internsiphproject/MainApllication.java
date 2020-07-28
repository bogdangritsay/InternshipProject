package com.hritsay.internsiphproject;

import android.app.Application;

public class MainApllication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExoPlayerUtil.setContext(getApplicationContext());
    }
}
