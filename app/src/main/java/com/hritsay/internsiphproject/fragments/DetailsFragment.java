package com.hritsay.internsiphproject.fragments;

import android.content.res.Configuration;
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
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.hritsay.internsiphproject.details.ExoPlayerUtil;
import com.hritsay.internsiphproject.details.FilmDetailsViewModel;
import com.hritsay.internsiphproject.MainActivity;
import com.hritsay.internsiphproject.R;
import com.hritsay.internsiphproject.databinding.FragmentDetailsBinding;
import com.hritsay.internsiphproject.models.FilmItem;


public class DetailsFragment extends Fragment {
    private final static String TAG = DetailsFragment.class.getCanonicalName();
    private final static String VISIBILITY_TAG = "IMAGE_VISIBILITY";
    private final static String PLAYBACK_TAG = "PLAYBACK_POSITION";
    private final static String DESCRIPTION_TAG = "filmDescription";
    private final static String IMDB_ID_KEY = "imdbId";
    private boolean posterVisibility;
    private FilmDetailsViewModel filmDetailsViewModel;
    private String imdbId;
    private FilmItem filmItem = new FilmItem();
    private FragmentDetailsBinding fragmentDetailsBinding;
    private ExoPlayerUtil exoPlayerUtil;
    private PlayerView playerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            imdbId = getArguments().getString(IMDB_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        initItem(imdbId);
        playerView = fragmentDetailsBinding.videoView;
        exoPlayerUtil = ExoPlayerUtil.getInstance();
        playerView.setPlayer(exoPlayerUtil.getPlayer());
        if (savedInstanceState != null) {
            posterVisibility = savedInstanceState.getBoolean(VISIBILITY_TAG);
        } else {
            posterVisibility = true;
        }
        //for landscape
        if(!posterVisibility) {
           fragmentDetailsBinding.posterImage.setVisibility(View.INVISIBLE);
           fragmentDetailsBinding.videoView.setVisibility(View.VISIBLE);
           exoPlayerUtil.play();
        }

        return fragmentDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            fragmentDetailsBinding.descriptionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(DESCRIPTION_TAG, filmItem.getPlot());
                    Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_descriptionFragment, bundle);
                }
            });

            if(savedInstanceState != null) {
                long position = savedInstanceState.getLong(PLAYBACK_TAG);
                exoPlayerUtil.setPlaybackPosition(position);
                Log.e(TAG, Long.valueOf(position).toString());
            }

        fragmentDetailsBinding.videoViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(posterVisibility) {
                    fragmentDetailsBinding.posterImage.setVisibility(View.INVISIBLE);
                    fragmentDetailsBinding.videoView.setVisibility(View.VISIBLE);
                    posterVisibility = false;
                    exoPlayerUtil.play();
                } else {
                    fragmentDetailsBinding.posterImage.setVisibility(View.VISIBLE);
                    fragmentDetailsBinding.videoView.setVisibility(View.INVISIBLE);
                    posterVisibility = true;
                    exoPlayerUtil.pause();
                }
            }
        });

        ExoPlayerUtil.getInstance().getPlayer().addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    fragmentDetailsBinding.posterImage.setVisibility(View.VISIBLE);
                    playerView.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)  {
            ((MainActivity)getActivity()).getSupportActionBar().hide();
        }

    }

    private void initItem(String imdbId) {
        filmDetailsViewModel = new ViewModelProvider(this).get(FilmDetailsViewModel.class);
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
                        .into(fragmentDetailsBinding.posterImage);
            }
        });

        filmDetailsViewModel.getThrowableMutableLiveData().observe(getViewLifecycleOwner(), throwable -> {
            Toast toast = Toast.makeText(getContext(), "Error! Message: " + throwable.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        });

        filmDetailsViewModel.getFilmById(imdbId);
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayerUtil.releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_TAG, exoPlayerUtil.getPlaybackPosition());
        outState.putBoolean(VISIBILITY_TAG, posterVisibility);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if(ExoPlayerUtil.getInstance().getPlayer() != null) {
                exoPlayerUtil.setPlaybackPosition(savedInstanceState.getLong(PLAYBACK_TAG));
            }
        } else {
            exoPlayerUtil.reset();
        }
    }

    @Override
    public void onDestroy() {
        filmDetailsViewModel.disposeAll();
        super.onDestroy();
    }
}