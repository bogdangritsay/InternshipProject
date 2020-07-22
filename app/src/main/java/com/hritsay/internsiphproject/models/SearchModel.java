package com.hritsay.internsiphproject.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {
    @SerializedName("Search")
    private List<FilmItem> filmItemList;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("Response")
    private boolean status;

    public SearchModel() {}

    public SearchModel(List<FilmItem> filmItemList, int totalResults, boolean status) {
        this.filmItemList = filmItemList;
        this.totalResults = totalResults;
        this.status = status;
    }

    public List<FilmItem> getFilmItemList() {
        return filmItemList;
    }

    public void setFilmItemList(List<FilmItem> filmItemList) {
        this.filmItemList = filmItemList;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
