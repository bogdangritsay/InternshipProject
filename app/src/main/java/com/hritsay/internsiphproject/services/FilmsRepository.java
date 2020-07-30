package com.hritsay.internsiphproject.services;

import android.util.Log;

import com.hritsay.internsiphproject.models.FilmActors;
import com.hritsay.internsiphproject.models.FilmDetailsItem;
import com.hritsay.internsiphproject.models.SearchModel;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
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

    public Single<List<FilmDetailsItem>> loadFilms() {
            return filmServiceAPI
                .getShortFilmsDescription()
                .map(SearchModel::getFilmDetailsItemList)
                .flatMap(new Function<List<FilmDetailsItem>, ObservableSource<FilmDetailsItem>>() {
                    @Override
                    public ObservableSource<FilmDetailsItem> apply(List<FilmDetailsItem> films) throws Exception {
                        return Observable.fromIterable(films);
                    }
                })
                .flatMap(new Function<FilmDetailsItem, ObservableSource<FilmActors>>() {
                    @Override
                    public ObservableSource<FilmActors> apply(FilmDetailsItem filmDetailsItem) throws Exception {
                        return filmsRepository.loadActorsByImdbId(filmDetailsItem.getImdbId());
                    }
                }, new BiFunction<FilmDetailsItem, FilmActors, FilmDetailsItem>() {
                    @Override
                    public FilmDetailsItem apply(FilmDetailsItem filmDetailsItem, FilmActors filmActors) throws Exception {
                        filmDetailsItem.setActors(filmActors.getActors());
                        return filmDetailsItem;
                    }
                }).toList();
    }

    public Observable<FilmDetailsItem> loadFilmByImdbId(String imdbId) {
        Log.d(TAG, "New request to API for film by imdbId");
        return filmServiceAPI.getLongFilmDescription(imdbId)
                .subscribeOn(Schedulers.io()) //в каком потоке будет работать источник
                .observeOn(AndroidSchedulers.mainThread());  // в каком потоке будем отображать результат
    }

    public Observable<FilmActors> loadActorsByImdbId(String imdbId) {
        Log.d(TAG, "New request to API for film by imdbId");
        return filmServiceAPI.getFilmActors(imdbId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
