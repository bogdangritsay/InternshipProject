package com.hritsay.internsiphproject.services;


import android.util.Log;


import com.hritsay.internsiphproject.FilmConverter;
import com.hritsay.internsiphproject.db.AppDatabase;
import com.hritsay.internsiphproject.db.dao.FilmDAO;
import com.hritsay.internsiphproject.db.entities.Film;
import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

import java.util.LinkedList;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class FilmsRepository {
    private static final String TAG = FilmsRepository.class.getCanonicalName();
    private static FilmsRepository filmsRepository;
    private FilmServiceAPI filmServiceAPI;
    private FilmDAO filmDao;

    /**
     * Getting instance of film repository
     * @return FilmRepository instance
     */
    public static FilmsRepository getInstance() {
        if (filmsRepository == null) {
            filmsRepository = new FilmsRepository();
        }
        return filmsRepository;
    }

    private FilmsRepository() {
        filmServiceAPI = RetrofitService.getInstance().createFilmService();
        filmDao = AppDatabase.db.filmDAO();
    }

    /**
     * Loading film list from database
     * @return Flowable FilmList
     */
    public Flowable<List<Film>> loadFilms() {
        return filmDao.loadAllFilms();
    }

    /**
     * Loading film details entity from database
     * @param imdbId imdbId for the search
     * @return Flowable film entity
     */
    public Flowable<Film> loadFilmByImdbId(String imdbId) {
    return filmDao.loadFilmbyId(imdbId);
    }


    /**
     * Updating data in database
     */
    public Single<List<FilmItem>> updateDatabase() {
        return filmServiceAPI
                .getShortFilmsDescription()
                .map(SearchModel::getFilmItemList)
                .flatMap(new Function<List<FilmItem>, ObservableSource<FilmItem>>() {
                    @Override
                    public ObservableSource<FilmItem> apply(List<FilmItem> films) throws Exception {
                        return Observable.fromIterable(films);
                    }
                })
                .flatMap(new Function<FilmItem, ObservableSource<FilmItem>>() {
                    @Override
                    public ObservableSource<FilmItem> apply(FilmItem filmItem) throws Exception {
                        Log.e("THREAD TEST", Thread.currentThread().getName());
                        return filmServiceAPI
                                .getLongFilmDescription(filmItem.getImdbId())
                                .subscribeOn(Schedulers.io());
                    }
                }, new BiFunction<FilmItem, FilmItem, FilmItem>() {
                    @Override
                    public FilmItem apply(FilmItem filmItem, FilmItem filmSource) throws Exception {
                        Thread.sleep(3000);
                        Log.d(TAG, "Thread — " + Thread.currentThread().getName());
                        filmItem.setActors(filmSource.getActors());
                        filmItem.setDuration(filmSource.getDuration());
                        filmItem.setGenres(filmSource.getGenres());
                        filmItem.setPlot(filmSource.getPlot());
                        return filmItem;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io());
    }

    /**
     * Wrapping method for inserting films to db
     * @param films for insert
     */
    public void insertFilms(List<Film> films) {
        filmDao.insertFilms(films);
    }

}
