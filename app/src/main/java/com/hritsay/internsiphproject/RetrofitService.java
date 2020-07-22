package com.hritsay.internsiphproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static FilmServiceAPI createFilmService() {
        return retrofit.create(FilmServiceAPI.class);
    }

}
