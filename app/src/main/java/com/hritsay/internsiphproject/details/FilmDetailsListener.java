package com.hritsay.internsiphproject.details;

import com.hritsay.internsiphproject.models.FilmDetailsItem;

public interface FilmDetailsListener {
    void onFilmLoaded(FilmDetailsItem filmDetailsItem);

    void onFilmLoadedFail(Throwable t);
}
