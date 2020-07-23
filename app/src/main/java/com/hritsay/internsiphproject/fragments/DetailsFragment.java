package com.hritsay.internsiphproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hritsay.internsiphproject.FilmDetailsViewModel;
import com.hritsay.internsiphproject.R;
import com.hritsay.internsiphproject.databinding.FragmentDetailsBinding;
import com.hritsay.internsiphproject.models.FilmItem;


public class DetailsFragment extends Fragment {
    private final String IMDB_ID_KEY = "imdbId";
    private String imdbId;
    private FilmItem filmItem = new FilmItem();
    private FragmentDetailsBinding fragmentDetailsBinding;
    private final String TAG = getClass().getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Second details fragment was been successfully created");
        imdbId = getArguments().getString(IMDB_ID_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        initItem(imdbId);
        return fragmentDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDetailsBinding.descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("filmDescription", filmItem.getPlot());
                Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_descriptionFragment, bundle);
            }
        });
    }

    private void initItem(String imdbId) {
        FilmDetailsViewModel filmDetailsViewModel = new ViewModelProvider(this).get(FilmDetailsViewModel.class);
        filmDetailsViewModel.init(imdbId);
        filmDetailsViewModel.getFilmLiveData().observe(getViewLifecycleOwner(), filmResponse -> {
            Log.i(TAG, "Observer running");
            if(filmResponse != null) {
                filmItem = filmResponse;
                fragmentDetailsBinding.filmTitle.setText(filmItem.getTitle());
                fragmentDetailsBinding.actors.setText(filmItem.getActors());
                fragmentDetailsBinding.duration.setText(filmItem.getDuration());
                fragmentDetailsBinding.genres.setText(filmItem.getGenres());
                fragmentDetailsBinding.year.setText(filmItem.getYear());
                Glide.with(getContext())
                        .load(filmItem.getUrl())
                        .placeholder(R.mipmap.film_placeholder)
                        .error(R.drawable.ic_baseline_error_outline_24)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(fragmentDetailsBinding.poster);
            }
        });


        filmDetailsViewModel.getThrowableMutableLiveData().observe(getViewLifecycleOwner(), throwable -> {
            Toast toast = Toast.makeText(getContext(), "Error! Message: " + throwable.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        });
    }

}