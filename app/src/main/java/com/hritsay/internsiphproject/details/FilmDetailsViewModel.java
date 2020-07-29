package com.hritsay.internsiphproject.details;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.services.FilmsRepository;
import com.hritsay.internsiphproject.models.FilmItem;


public class FilmDetailsViewModel extends ViewModel {
    private final String TAG = getClass().getCanonicalName();
    private MutableLiveData<FilmItem> mutableLiveData;
    private MutableLiveData<Throwable>  throwableMutableLiveData;

    public FilmDetailsViewModel() {
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
    }

    public void getFilmById(String imdbId) {
        Log.i(TAG, "getFIlmById(" + imdbId + ")");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
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
