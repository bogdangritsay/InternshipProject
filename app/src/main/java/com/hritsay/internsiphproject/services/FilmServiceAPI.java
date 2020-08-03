package com.hritsay.internsiphproject.services;



import io.reactivex.Observable;

import com.hritsay.internsiphproject.models.FilmActors;
import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;


import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmServiceAPI {
    String API_KEY = "?apikey=67c8e857";
    String SHORT_PARAMS = "&s=Dark";

    @GET(API_KEY + SHORT_PARAMS)
    Observable<SearchModel> getShortFilmsDescription();

    @GET(API_KEY)
    Observable<FilmItem> getLongFilmDescription(@Query("i") String imdbID);

    @GET(API_KEY)
    Observable<FilmActors> getFilmActors(@Query("i") String imdbID);



}
