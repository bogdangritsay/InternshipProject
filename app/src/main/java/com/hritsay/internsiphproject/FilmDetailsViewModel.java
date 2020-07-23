package com.hritsay.internsiphproject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.models.FilmItem;


public class FilmDetailsViewModel extends ViewModel {
    private MutableLiveData<FilmItem> mutableLiveData;

    private final String TAG = getClass().getCanonicalName();
    private MutableLiveData<Throwable>  throwableMutableLiveData;

    public void init(String imdbId) {
        Log.i(TAG, "init()");
        if (mutableLiveData != null) {
            return;
        }
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
        FilmDetailsListener listener = new FilmDetailsListener() {
            @Override
            public void onFilmLoaded(FilmItem filmItem) {
                Log.d(TAG, "onDataLoaded in anon started");
                mutableLiveData.setValue(filmItem);
            }

            @Override
            public void onFilmLoadedFail(Throwable t) {
               throwableMutableLiveData.setValue(t);
            }
        };
        filmsRepository.loadFilmByImdbId(listener, imdbId);
    }

    public LiveData<FilmItem> getFilmLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
