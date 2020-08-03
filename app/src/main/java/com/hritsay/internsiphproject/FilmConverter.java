package com.hritsay.internsiphproject;


import com.hritsay.internsiphproject.db.entities.Film;
import com.hritsay.internsiphproject.models.FilmItem;


public class FilmConverter {

    public static FilmItem convert(Film value) {
        return new FilmItem(
                value.getImdbId(),
                value.getYear(),
                value.getTitle(),
                value.getUrl(),
                value.getDuration(),
                value.getActors(),
                value.getGenres(),
                value.getPlot()
        );
    }

    public static Film convert(FilmItem value) {
        return new Film(
                value.getImdbId(),
                value.getTitle(),
                value.getYear(),
                value.getUrl(),
                value.getDuration(),
                value.getActors(),
                value.getGenres(),
                value.getPlot()
        );
    }

}
