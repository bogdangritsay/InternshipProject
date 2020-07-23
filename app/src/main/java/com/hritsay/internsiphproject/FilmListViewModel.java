package com.hritsay.internsiphproject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.models.SearchModel;



public class FilmListViewModel extends ViewModel {
    private MutableLiveData<SearchModel> mutableLiveData;
    private final String TAG = getClass().getCanonicalName();
    private MutableLiveData<Throwable>  throwableMutableLiveData;

    public void init() {
        Log.i(TAG, "init()");
        if (mutableLiveData != null) {
            return;
        }
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
        FilmsListener listener = new FilmsListener() {
            @Override
            public void onFilmListLoaded(SearchModel searchModel) {
                Log.i(TAG, "onDataLoaded in anon started");
                mutableLiveData.setValue(searchModel);
            }

            @Override
            public void onFilmListLoadFail(Throwable t) {
                throwableMutableLiveData.setValue(t);
            }
        };
        filmsRepository.loadFilms(listener);
    }

    public LiveData<SearchModel> getFilmsLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
