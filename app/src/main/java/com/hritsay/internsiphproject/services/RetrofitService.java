package com.hritsay.internsiphproject.services;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Service class for work with API
 */
public class RetrofitService {
    private static RetrofitService retrofitService;
    private RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build();


    /**
     * Creating film service
     * @return created FilmServiceAPI
     */
    public FilmServiceAPI createFilmService() {
        return retrofit.create(FilmServiceAPI.class);
    }

    /**
     * Getting instance of service
     * @return instance
     */
    public static RetrofitService getInstance() {
        if(retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

}
