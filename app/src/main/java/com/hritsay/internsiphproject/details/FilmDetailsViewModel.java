package com.hritsay.internsiphproject.details;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hritsay.internsiphproject.FilmConverter;
import com.hritsay.internsiphproject.db.entities.Film;
import com.hritsay.internsiphproject.services.FilmsRepository;
import com.hritsay.internsiphproject.models.FilmItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class FilmDetailsViewModel extends ViewModel {
    private static final String TAG = FilmDetailsViewModel.class.getCanonicalName();
    private MutableLiveData<FilmItem> mutableLiveData;
    private MutableLiveData<Throwable>  throwableMutableLiveData;

    /**
     * Default constructor for FilmDetailsViewModel
     */
    public FilmDetailsViewModel() {
        mutableLiveData = new MutableLiveData<>();
        throwableMutableLiveData = new MutableLiveData<>();
    }

    /**
     * Getting film by imdbId
     * @param imdbId id for the search
     */
    public void getFilmById(String imdbId) {
        Log.i(TAG, "getFIlmById(" + imdbId + ")");
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        Disposable d = filmsRepository.loadFilmByImdbId(imdbId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Film>() {
                    @Override
                    public void accept(Film film) {
                        try {
                            mutableLiveData.setValue(FilmConverter.convert(film));
                        } catch (Throwable t) {
                            throwableMutableLiveData.setValue(t);
                        }
                    }
                });
    }

    /**
     * Getter for FilmItem LiveData
     * @return Livedata FilmItem
     */
    public LiveData<FilmItem> getFilmLiveData() {
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
