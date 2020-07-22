package com.hritsay.internsiphproject;

import android.util.Log;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilmsRepository {
    private final String TAG = "FILMS REPOSITORY";
    private static FilmsRepository filmsRepository;
    private FilmsListener listener;

    public static FilmsRepository getInstance(){
        if (filmsRepository == null) {
            filmsRepository = new FilmsRepository();
        }
        return filmsRepository;
    }

    private FilmServiceAPI filmServiceAPI;

    private FilmsRepository() {
        filmServiceAPI = RetrofitService.createFilmService();
    }

    public void  loadFilms(FilmsListener filmsListener) {
        Log.d(TAG, "New request to API for films searching");
        filmServiceAPI.getShortFilmsDescription().enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call,
                                   Response<SearchModel> response) {
                if (response.isSuccessful()){
                    Log.i(TAG, "RESPONSE BODY" + response.toString());
                    filmsListener.onFilmListLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                Log.e("QUERY TO API", "Failure response to API or error in query\n" + t.getMessage());
            }
        });
    }

    public void loadFilmByImdbId(FilmsListener filmsListener, String imdbId) {
        Log.d(TAG, "New request to API for film by imdbId");
        filmServiceAPI.getLongFilmDescription(imdbId).enqueue(new Callback<FilmItem>() {
            @Override
            public void onResponse(Call<FilmItem> call, Response<FilmItem> response) {
                if (response.isSuccessful()){
                    Log.i(TAG, "RESPONSE BODY" + response.toString());
                    filmsListener.onFilmLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<FilmItem> call, Throwable t) {
                Log.e("QUERY TO API", "Failure response to API or error in query\n" + t.getMessage());
            }
        });
    }



}
