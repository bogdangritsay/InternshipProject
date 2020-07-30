package com.hritsay.internsiphproject.details;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.services.FilmsRepository;
import com.hritsay.internsiphproject.models.FilmDetailsItem;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class FilmDetailsViewModel extends ViewModel {
    private final String TAG = getClass().getCanonicalName();
    private MutableLiveData<FilmDetailsItem> mutableLiveData;
    private MutableLiveData<Throwable>  throwableMutableLiveData;

    public FilmDetailsViewModel() {
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
    }

    public void getFilmById(String imdbId) {
        Log.i(TAG, "getFIlmById(" + imdbId + ")");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        filmsRepository.loadFilmByImdbId(imdbId)

                .subscribe(new Observer<FilmDetailsItem>() {
                    FilmDetailsItem model;
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FilmDetailsItem filmDetailsItem) {
                        model = filmDetailsItem;
                    }

                    @Override
                    public void onError(Throwable e) {
                        throwableMutableLiveData.setValue(e);
                    }

                    @Override
                    public void onComplete() {
                        mutableLiveData.setValue(model);
                    }
                });
    }

    public LiveData<FilmDetailsItem> getFilmLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
