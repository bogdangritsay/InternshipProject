package com.hritsay.internsiphproject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

public class FilmDetailsViewModel extends ViewModel {
    private MutableLiveData<FilmItem> mutableLiveData;
    private FilmsRepository filmsRepository;
    private final String TAG = "FILM_ITEM_VIEW_MODEL";

    public void init(String imdbId) {
        Log.i(TAG, "init()");
        if (mutableLiveData != null) {
            return;
        }
        filmsRepository = FilmsRepository.getInstance();
        mutableLiveData = new MutableLiveData<>();
        FilmsListener listener = new FilmsListener() {
            @Override
            public void onFilmListLoaded(SearchModel searchModel) {}

            @Override
            public void onFilmLoaded(FilmItem filmItem) {
                Log.i(TAG, "onDataLoaded in anon started");
                mutableLiveData.setValue(filmItem);
            }
        };
        filmsRepository.loadFilmByImdbId(listener, imdbId);
    }

    public LiveData<FilmItem> getFilmLiveData() {
        return mutableLiveData;
    }
}
