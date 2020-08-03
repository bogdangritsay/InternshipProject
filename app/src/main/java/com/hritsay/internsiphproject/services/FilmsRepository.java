package com.hritsay.internsiphproject.services;


import android.util.Log;


import com.hritsay.internsiphproject.FilmConverter;
import com.hritsay.internsiphproject.db.AppDatabase;
import com.hritsay.internsiphproject.db.dao.FilmDAO;
import com.hritsay.internsiphproject.db.entities.Film;
import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class FilmsRepository {
    private static final String APP_PREFERENCES = "update_settings";
    private static LocalDateTime UPDATE_TIME;

    private final String TAG = getClass().getCanonicalName();
    private static FilmsRepository filmsRepository;
    private FilmDAO filmDao = AppDatabase.db.filmDAO();

    public static FilmsRepository getInstance() {
        if (filmsRepository == null) {
            filmsRepository = new FilmsRepository();
        }
        return filmsRepository;
    }

    private FilmServiceAPI filmServiceAPI;

    private FilmsRepository() {
        filmServiceAPI = RetrofitService.getInstance().createFilmService();

    }

    public Flowable<List<Film>> loadFilms() {
        updateDatabase();
        Log.e(TAG, "After update");
                return filmDao
                        .loadAllFilms();
    }

    public Flowable<Film> loadFilmByImdbId(String imdbId) {
    return filmDao.loadFilmbyId(imdbId);
    }

    public void updateDatabase() {
        filmServiceAPI
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
                    public FilmItem apply(FilmItem filmItem, FilmItem filmActors) throws Exception {
                        Thread.sleep(3000);
                        Log.d(TAG, "Thread — " + Thread.currentThread().getName());
                        filmItem.setActors(filmActors.getActors());
                        filmItem.setDuration(filmActors.getDuration());
                        filmItem.setGenres(filmActors.getGenres());
                        return filmItem;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<FilmItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<FilmItem> filmItems) {
                        Log.e(TAG, "Before update");
                        List<Film> films = new LinkedList<>();
                        for (int i = 0; i < filmItems.size(); i++) {
                            films.add(FilmConverter.convert(filmItems.get(i)));
                        }
                        filmDao.insertFilms(films);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

}
