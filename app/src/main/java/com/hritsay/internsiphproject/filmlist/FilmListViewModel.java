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
import io.reactivex.disposables.CompositeDisposable;
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
    private Disposable dbDisp;
    private Disposable apiDisp;
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
    public void initDataFilms(String keyword) {
        Log.i(TAG, "initDataFilms()");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        if (apiDisp != null && !apiDisp.isDisposed()) {
            apiDisp.dispose();
        }
        filmsRepository.updateDatabase(keyword)
                .subscribe(new SingleObserver<List<FilmItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiDisp = d;
                    }

                    @Override
                    public void onSuccess(List<FilmItem> filmItems) {
                        List<Film> films = new LinkedList<>();
                        for (int i = 0; i < filmItems.size(); i++) {
                            films.add(FilmConverter.convert(filmItems.get(i)));
                        }
                        filmsRepository.insertFilms(films);
                        throwableMutableLiveData.postValue(null);
                        Log.i(TAG, "Database was been updated!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        throwableMutableLiveData.postValue(e);
                    }
                });

        if (dbDisp != null && !dbDisp.isDisposed()) {
            dbDisp.dispose();
        }
        dbDisp =  filmsRepository
                .loadFilms(keyword)
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
        //disposable.add(d);
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
