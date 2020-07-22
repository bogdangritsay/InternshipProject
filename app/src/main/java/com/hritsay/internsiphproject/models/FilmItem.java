package com.hritsay.internsiphproject.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;



public class FilmItem {
    @SerializedName("imdbID")
    private String imdbId;
    @SerializedName("Year")
    private String year;
    @SerializedName("Title")
    private String title;
    @SerializedName("Poster")
    private String url;
    @SerializedName("Runtime")
    private String duration;
    @SerializedName("Actors")
    private String actors;
    @SerializedName("Genre")
    private String genres;
    @SerializedName("Ratings")
    private List<Rating> ratings;
    @SerializedName("Plot")
    private String plot;

    public FilmItem() {}

    public FilmItem(String imdbId, String year, String title, String url, String duration, String actors, String genres, List<Rating> ratings, String plot) {
        this.imdbId = imdbId;
        this.year = year;
        this.title = title;
        this.url = url;
        this.duration = duration;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.plot = plot;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "FilmItem{" +
                "imdbId='" + imdbId + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", duration='" + duration + '\'' +
                ", actors='" + actors + '\'' +
                ", genres='" + genres + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
