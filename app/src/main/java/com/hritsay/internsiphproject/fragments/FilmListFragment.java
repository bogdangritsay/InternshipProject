package com.hritsay.internsiphproject.fragments;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hritsay.internsiphproject.details.ExoPlayerUtil;
import com.hritsay.internsiphproject.details.FilmDetailsViewModel;
import com.hritsay.internsiphproject.filmlist.FilmListViewModel;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.filmlist.FilmListAdapter;
import com.hritsay.internsiphproject.databinding.FragmentMainBinding;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FilmListFragment extends Fragment {
    private static final String TAG = FilmListFragment.class.getCanonicalName();
    private FragmentMainBinding fragmentMainBinding;
    private FilmListAdapter adapter;
    private FilmListViewModel filmListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        fragmentMainBinding = FragmentMainBinding.inflate(getLayoutInflater());
        filmListViewModel = new ViewModelProvider(this).get(FilmListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        filmListViewModel.getFilmsLiveData().observe(getViewLifecycleOwner(), filmsResponse -> {
            Log.i(TAG, "Observer running");
            List<FilmItem> filmsArticles = filmsResponse.getFilmItemList();
            adapter.setmFilmList(filmsArticles);
        });
        filmListViewModel.getThrowableMutableLiveData().observe(getViewLifecycleOwner(), throwable -> {
            Toast toast = Toast.makeText(getContext(), "Error! Message: " + throwable.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        });
        filmListViewModel.initDataFilms();
        addCustomBackPress();
        ExoPlayerUtil.getInstance().reset();
        Log.i(TAG, "onCreateView");
        initRecyclerView();
        return fragmentMainBinding.getRoot();
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecycleView");
        RecyclerView recyclerView = fragmentMainBinding.recyclerView;
        if (adapter == null) {
            adapter = new FilmListAdapter();
            adapter.setmFilmList(Collections.emptyList());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    private void addCustomBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), callback);
    }


}