package com.hritsay.internsiphproject;

import android.app.Application;

import com.hritsay.internsiphproject.details.ExoPlayerUtil;

public class MainApllication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExoPlayerUtil.getInstance().initializePlayer(getApplicationContext());
    }
}
