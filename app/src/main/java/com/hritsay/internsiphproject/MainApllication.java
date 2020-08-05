package com.hritsay.internsiphproject;

import android.app.Application;

import androidx.room.Room;

import com.hritsay.internsiphproject.db.AppDatabase;
import com.hritsay.internsiphproject.details.ExoPlayerUtil;
import com.hritsay.internsiphproject.services.FilmsRepository;

public class MainApllication extends Application {
    public static MainApllication app;
    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = AppDatabase.getAppDatabase(getApplicationContext());
        ExoPlayerUtil.getInstance().initializePlayer(getApplicationContext());
    }

    public static MainApllication getInstance() {
        return app;
    }

    public AppDatabase getDatabase() {
        return db;
    }


}
