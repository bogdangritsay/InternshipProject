package com.hritsay.internsiphproject.filmlist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.FilmConverter;
import com.hritsay.internsiphproject.db.entities.Film;
import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.services.FilmsRepository;
import com.hritsay.internsiphproject.models.SearchModel;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Class for work with FilmListView
 */
public class FilmListViewModel extends ViewModel {
    private static final String TAG = FilmListViewModel.class.getCanonicalName();
    private MutableLiveData<SearchModel> mutableLiveData;
    private MutableLiveData<Throwable>  throwableMutableLiveData;

    /**
     * Default constructor for ViewModel
     */
    public FilmListViewModel() {
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
    }

    /**
     * Method for view model initialization
     */
    public void initDataFilms() {
        Log.i(TAG, "initDataFilms()");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        Disposable d = filmsRepository
                .loadFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Film>>() {
                               @Override
                               public void accept(List<Film> films) {
                                   try {
                                       List<FilmItem> filmItemList = new LinkedList<>();
                                       for (int i = 0; i < films.size(); i++) {
                                           filmItemList.add(FilmConverter.convert(films.get(i)));
                                       }
                                       SearchModel model = new SearchModel();
                                       model.setFilmItemList(filmItemList);
                                       mutableLiveData.setValue(model);
                                   } catch (Throwable t) {
                                       throwableMutableLiveData.setValue(t);
                                   }
                               }
                           });

        filmsRepository.updateDatabase()
                .subscribe(new SingleObserver<List<FilmItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<FilmItem> filmItems) {
                        List<Film> films = new LinkedList<>();
                        for (int i = 0; i < filmItems.size(); i++) {
                            films.add(FilmConverter.convert(filmItems.get(i)));
                        }
                        filmsRepository.insertFilms(films);

                    }

                    @Override
                    public void onError(Throwable e) {
                        throwableMutableLiveData.setValue(e);
                    }
                });
    }

    /**
     * Getter for SearchModel of films in LiveData
     * @return SearchModel in LiveData
     */
    public LiveData<SearchModel> getFilmsLiveData() {
        return mutableLiveData;
    }

    /**
     * Getter for errors in LiveData
     * @return Throwable LiveData
     */
    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
