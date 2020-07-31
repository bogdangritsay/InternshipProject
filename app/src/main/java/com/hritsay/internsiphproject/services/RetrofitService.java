package com.hritsay.internsiphproject.services;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    private static RetrofitService retrofitService;
    private RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build();


    public FilmServiceAPI createFilmService() {
        return retrofit.create(FilmServiceAPI.class);
    }

    public static RetrofitService getInstance() {
        if(retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

}
