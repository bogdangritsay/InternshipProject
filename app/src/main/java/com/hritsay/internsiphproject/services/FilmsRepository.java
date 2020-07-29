package com.hritsay.internsiphproject.services;

import android.util.Log;

import com.hritsay.internsiphproject.details.FilmDetailsListener;
import com.hritsay.internsiphproject.filmlist.FilmsListener;
import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FilmsRepository {
    private final String TAG = getClass().getCanonicalName();
    private static FilmsRepository filmsRepository;


    public static FilmsRepository getInstance(){
        if (filmsRepository == null) {
            filmsRepository = new FilmsRepository();
        }
        return filmsRepository;
    }

    private FilmServiceAPI filmServiceAPI;

    private FilmsRepository() {
        filmServiceAPI = RetrofitService.getInstance().createFilmService();
    }

    public void  loadFilms(FilmsListener filmsListener) {

        Observable<SearchModel> searchModelObservable = filmServiceAPI.getShortFilmsDescription();

        searchModelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchModel>() {
                    SearchModel model = new SearchModel();
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(SearchModel searchModel) {
                        model = searchModel;
                    }

                    @Override
                    public void onError(Throwable e) {
                        filmsListener.onFilmListLoadFail(e);
                    }

                    @Override
                    public void onComplete() {
                        filmsListener.onFilmListLoaded(model);
                    }
                });
    }

    public void loadFilmByImdbId(FilmDetailsListener filmDetailsListener, String imdbId) {
        Log.d(TAG, "New request to API for film by imdbId");
        Observable<FilmItem> searchModelObservable = filmServiceAPI.getLongFilmDescription(imdbId);
        searchModelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FilmItem>() {
                    FilmItem model;
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FilmItem filmItem) {
                        model = filmItem;
                    }

                    @Override
                    public void onError(Throwable e) {
                        filmDetailsListener.onFilmLoadedFail(e);
                    }

                    @Override
                    public void onComplete() {
                        filmDetailsListener.onFilmLoaded(model);
                    }
                });
    }
}
