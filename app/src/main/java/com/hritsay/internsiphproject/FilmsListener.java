package com.hritsay.internsiphproject;


import com.hritsay.internsiphproject.models.SearchModel;

public interface FilmsListener {
    void onFilmListLoaded(SearchModel searchModel);

    void onFilmListLoadFail(Throwable t);
}
