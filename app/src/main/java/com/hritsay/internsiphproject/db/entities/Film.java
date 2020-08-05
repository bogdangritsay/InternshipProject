package com.hritsay.internsiphproject.db.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Film entity
 */
@Entity(tableName = "films")
public class Film {

    @PrimaryKey
    @NonNull
    public String imdbId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "poster_url")
    public String url;

    @ColumnInfo(name = "duration")
    public String duration;

    @ColumnInfo(name = "actors")
    public String actors;

    @ColumnInfo(name = "genres")
    public String genres;

    @ColumnInfo(name = "plot")
    public String plot;


    @NonNull
    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public String getDuration() {
        return duration;
    }

    public String getActors() {
        return actors;
    }

    public String getGenres() {
        return genres;
    }

    public String getPlot() {
        return plot;
    }

    public Film(@NonNull String imdbId, String title, String year, String url, String duration, String actors, String genres, String plot) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.url = url;
        this.duration = duration;
        this.actors = actors;
        this.genres = genres;
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "Film{" +
                "imdbId='" + imdbId + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", url='" + url + '\'' +
                ", duration='" + duration + '\'' +
                ", actors='" + actors + '\'' +
                ", genres='" + genres + '\'' +
                ", plot='" + plot + '\'' +
                '}';
    }
}
