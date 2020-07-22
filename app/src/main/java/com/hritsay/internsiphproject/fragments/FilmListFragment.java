package com.hritsay.internsiphproject.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hritsay.internsiphproject.FilmListViewModel;
import com.hritsay.internsiphproject.models.FilmItem;
import com.hritsay.internsiphproject.FilmListAdapter;
import com.hritsay.internsiphproject.databinding.FragmentMainBinding;

import java.util.LinkedList;
import java.util.List;

public class FilmListFragment extends Fragment {
    private FragmentMainBinding fragmentMainBinding;
    private RecyclerView recyclerView;
    private FilmListAdapter adapter;
    private List<FilmItem> itemList = new LinkedList<>();
    private FilmListViewModel filmListViewModel;
    private final String TAG = "MAIN FRAGMENT";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        fragmentMainBinding = FragmentMainBinding.inflate(getLayoutInflater());

        filmListViewModel = ViewModelProviders.of(this).get(FilmListViewModel.class);
        filmListViewModel.init();
        filmListViewModel.getFilmsLiveData().observe(this, filmsResponse -> {
            Log.i(TAG, "Observer running");
            List<FilmItem> filmsArticles = filmsResponse.getFilmItemList();
            if (filmsArticles != null) {
                itemList.addAll(filmsArticles);
            }
            adapter.notifyDataSetChanged();
        });
        initRecyclerView();
        addCustomBackPress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return fragmentMainBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");


    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecycleView");
        recyclerView = fragmentMainBinding.recyclerView;
        if (adapter == null) {
            adapter = new FilmListAdapter(getContext(), itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            adapter.notifyDataSetChanged();
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
                .addCallback(this, callback);
    }



}