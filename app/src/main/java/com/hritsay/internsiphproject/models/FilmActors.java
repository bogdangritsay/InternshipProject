package com.hritsay.internsiphproject.models;

import com.google.gson.annotations.SerializedName;

public class FilmActors {
    @SerializedName("imdbID")
    private String imdbId;
    @SerializedName("Actors")
    private String actors;


    public FilmActors(String imdbId, String actors) {
        this.imdbId = imdbId;
        this.actors = actors;
    }
}
