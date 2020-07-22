package com.hritsay.internsiphproject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

public class FilmListViewModel extends ViewModel {
    private MutableLiveData<SearchModel> mutableLiveData;
    private FilmsRepository filmsRepository;
    private final String TAG = "FILM_LIST_VIEW_MODEL";

    public void init() {
        Log.i(TAG, "init()");
        if (mutableLiveData != null) {
            return;
        }
        filmsRepository = FilmsRepository.getInstance();
        mutableLiveData = new MutableLiveData<>();
        FilmsListener listener = new FilmsListener() {
            @Override
            public void onFilmListLoaded(SearchModel searchModel) {
                Log.i(TAG, "onDataLoaded in anon started");
                mutableLiveData.setValue(searchModel);
            }

            @Override
            public void onFilmLoaded(FilmItem filmItem) {

            }
        };
        filmsRepository.loadFilms(listener);
    }

    public LiveData<SearchModel> getFilmsLiveData() {
        return mutableLiveData;
    }

}
