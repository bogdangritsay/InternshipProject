package com.hritsay.internsiphproject.models;

import com.google.gson.annotations.SerializedName;

public class FilmActors {
    @SerializedName("imdbID")
    private String imdbId;
    @SerializedName("Actors")
    private String actors;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public FilmActors(String imdbId, String actors) {
        this.imdbId = imdbId;
        this.actors = actors;
    }
}
