package com.hritsay.internsiphproject.services;



import io.reactivex.Observable;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;


import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for work with http://www.omdbapi.com/ api
 */
public interface FilmServiceAPI {
    String API_KEY = "?apikey=67c8e857";


    /**
     * Getting short films description by key word
     * @return Observable SearchModel which includes list of films
     */
    @GET(API_KEY)
    Observable<SearchModel> getShortFilmsDescription(@Query("s") String keyword);

    /**
     * Getting full film description by imdbID
     * @param imdbID id for the search
     * @return FilmItem
     */
    @GET(API_KEY)
    Observable<FilmItem> getLongFilmDescription(@Query("i") String imdbID);




}
