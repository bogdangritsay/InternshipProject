package com.hritsay.internsiphproject;


import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmServiceAPI {
    String API_KEY = "?apikey=67c8e857";
    String SHORT_PARAMS = "&s=Dark";

    @GET(API_KEY + SHORT_PARAMS)
    Call<SearchModel> getShortFilmsDescription();

    @GET(API_KEY)
    Call<FilmItem> getLongFilmDescription(@Query("i") String imdbID);

}
