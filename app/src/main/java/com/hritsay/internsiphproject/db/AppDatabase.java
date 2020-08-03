package com.hritsay.internsiphproject.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hritsay.internsiphproject.db.dao.FilmDAO;
import com.hritsay.internsiphproject.db.entities.Film;

@Database(entities = {Film.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDAO filmDAO();

    public static AppDatabase db;

    public static AppDatabase getAppDatabase(Context context) {
        if(db == null) {
            db = Room
                    .databaseBuilder(context, AppDatabase.class, "films-db")
                    .build();
        }
        return db;
    }
}
