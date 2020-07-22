package com.hritsay.internsiphproject;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.models.SearchModel;

public interface FilmsListener {
    void onFilmListLoaded(SearchModel searchModel);

    void onFilmLoaded(FilmItem filmItem);

    //TODO on failuur
}
