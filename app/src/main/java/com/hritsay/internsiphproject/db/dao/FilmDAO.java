package com.hritsay.internsiphproject.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hritsay.internsiphproject.db.entities.Film;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface  FilmDAO {

    @Query("SELECT * FROM films")
    Flowable<List<Film>> loadAllFilms();

    @Query("SELECT * FROM films WHERE imdbId LIKE :id")
    Flowable<Film> loadFilmbyId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFilms(List<Film> films);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFilm(Film film);

    @Delete
    void deleteFilm(Film film);

    @Update
    void updateFilms(List<Film> films);

    @Update
    void updateFilm(Film film);




}
