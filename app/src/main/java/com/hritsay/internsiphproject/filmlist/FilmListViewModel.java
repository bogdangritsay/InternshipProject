package com.hritsay.internsiphproject.filmlist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.services.FilmsRepository;
import com.hritsay.internsiphproject.models.SearchModel;



public class FilmListViewModel extends ViewModel {
    private final String TAG = getClass().getCanonicalName();
    private MutableLiveData<SearchModel> mutableLiveData;
    private MutableLiveData<Throwable>  throwableMutableLiveData;


    public FilmListViewModel() {
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
    }


    public void loadFilms() {
        Log.i(TAG, "loadFilms()");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        FilmsListener listener = new FilmsListener() {
            @Override
            public void onFilmListLoaded(SearchModel searchModel) {
                Log.i(TAG, "onDataLoaded in anon started: " + searchModel.getFilmItemList().toString());
                mutableLiveData.setValue(searchModel);
            }

            @Override
            public void onFilmListLoadFail(Throwable t) {
                Log.e(TAG, "ERROR: " + t.getMessage());
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
