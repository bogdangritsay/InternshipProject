package com.hritsay.internsiphproject.details;

import com.hritsay.internsiphproject.models.FilmItem;

public interface FilmDetailsListener {
    void onFilmLoaded(FilmItem filmItem);

    void onFilmLoadedFail(Throwable t);
}
