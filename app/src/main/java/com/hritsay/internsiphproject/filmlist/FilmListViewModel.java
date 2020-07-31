package com.hritsay.internsiphproject.filmlist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.models.FilmDetailsItem;
import com.hritsay.internsiphproject.services.FilmsRepository;
import com.hritsay.internsiphproject.models.SearchModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FilmListViewModel extends ViewModel {
    private final String TAG = getClass().getCanonicalName();
    private MutableLiveData<SearchModel> mutableLiveData;
    private MutableLiveData<Throwable>  throwableMutableLiveData;


    public FilmListViewModel() {
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
    }


    public void initDataFilms() {
        Log.i(TAG, "initDataFilms()");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        filmsRepository
                .loadFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FilmDetailsItem>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<FilmDetailsItem> filmDetailsItems) {
                        SearchModel model = new SearchModel();
                        model.setFilmDetailsItemList(filmDetailsItems);
                        mutableLiveData.setValue(model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        throwableMutableLiveData.setValue(e);
                    }
                });
    }

    public LiveData<SearchModel> getFilmsLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
