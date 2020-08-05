package com.hritsay.internsiphproject;


import com.hritsay.internsiphproject.db.entities.Film;
import com.hritsay.internsiphproject.models.FilmItem;

/**
 * Class to convert between model and entity
 */

public class FilmConverter {

    /**
     * Convert Film entity to FilmItem model
     * @param value Film entity
     * @return FilmItem model
     */
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

    /**
     * Convert FilmItem model to Film entity
     * @param value FilmItem model
     * @return Film entity
     */
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
