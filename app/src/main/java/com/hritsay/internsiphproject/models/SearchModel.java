package com.hritsay.internsiphproject.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {
    @SerializedName("Search")
    private List<FilmDetailsItem> filmDetailsItemList;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("Response")
    private boolean status;

    public SearchModel() {}

    public SearchModel(List<FilmDetailsItem> filmDetailsItemList, int totalResults, boolean status) {
        this.filmDetailsItemList = filmDetailsItemList;
        this.totalResults = totalResults;
        this.status = status;
    }

    public List<FilmDetailsItem> getFilmDetailsItemList() {
        return filmDetailsItemList;
    }

    public void setFilmDetailsItemList(List<FilmDetailsItem> filmDetailsItemList) {
        this.filmDetailsItemList = filmDetailsItemList;
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
