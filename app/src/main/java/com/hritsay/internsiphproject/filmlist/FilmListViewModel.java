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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
        Disposable d = filmsRepository
                .loadFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Film>>() {
                               @Override
                               public void accept(List<Film> films) throws Exception {
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
    }


    public LiveData<SearchModel> getFilmsLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
