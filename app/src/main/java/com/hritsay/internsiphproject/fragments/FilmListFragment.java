package com.hritsay.internsiphproject.fragments;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hritsay.internsiphproject.MainActivity;
import com.hritsay.internsiphproject.details.ExoPlayerUtil;
import com.hritsay.internsiphproject.filmlist.FilmListViewModel;

import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.filmlist.FilmListAdapter;
import com.hritsay.internsiphproject.databinding.FragmentMainBinding;

import java.util.Collections;
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
        ((MainActivity)getActivity()).getSupportActionBar().show();

        filmListViewModel.getFilmsLiveData().observe(getViewLifecycleOwner(), filmsResponse -> {
            List<FilmItem> filmsArticles = filmsResponse.getFilmItemList();
            adapter.setmFilmList(filmsArticles);
        });
        filmListViewModel.getThrowableMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if(throwable != null) {
                    Toast toast = Toast.makeText(getContext(), "Error! Message: " + throwable.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        addCustomBackPress();
        ExoPlayerUtil.getInstance().reset();
        initRecyclerView();
        return fragmentMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentMainBinding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = fragmentMainBinding.editKeyword.getText().toString();
                filmListViewModel.initDataFilms(keyword);
            }
        });
    }

    private void initRecyclerView() {
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

    @Override
    public void onDestroy() {
        filmListViewModel.disposeAll();
        super.onDestroy();
    }
}