package com.hritsay.internsiphproject.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hritsay.internsiphproject.FilmDetailsViewModel;
import com.hritsay.internsiphproject.MainActivity;
import com.hritsay.internsiphproject.R;
import com.hritsay.internsiphproject.databinding.FragmentDetailsBinding;
import com.hritsay.internsiphproject.models.FilmItem;


public class DetailsFragment extends Fragment {
    private static boolean showImage;
    private static int count = 0;
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
        if (count == 0) {
            count = 1;
            playbackPosition = 0;
            showImage = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        initItem(imdbId);
        playerView = fragmentDetailsBinding.videoView;

        if(showImage) {
            fragmentDetailsBinding.videoView.setVisibility(View.INVISIBLE);
            fragmentDetailsBinding.posterImage.setVisibility(View.VISIBLE);
        } else {
            fragmentDetailsBinding.videoView.setVisibility(View.VISIBLE);
            fragmentDetailsBinding.posterImage.setVisibility(View.INVISIBLE);
        }


        fragmentDetailsBinding.videoViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showImage) {
                    fragmentDetailsBinding.posterImage.setVisibility(View.INVISIBLE);
                    fragmentDetailsBinding.videoView.setVisibility(View.VISIBLE);
                    showImage = false;
                    playWhenReady = true;
                } else {
                    fragmentDetailsBinding.videoView.setVisibility(View.INVISIBLE);
                    fragmentDetailsBinding.posterImage.setVisibility(View.VISIBLE);
                    showImage = true;
                    playWhenReady = false;
                }
            }
        });

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
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)  {
            ((MainActivity)getActivity()).getSupportActionBar().hide();
        }

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
                        .into(fragmentDetailsBinding.posterImage);
            }
        });


        filmDetailsViewModel.getThrowableMutableLiveData().observe(getViewLifecycleOwner(), throwable -> {
            Toast toast = Toast.makeText(getContext(), "Error! Message: " + throwable.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        });
    }


    private PlayerView playerView;
    private SimpleExoPlayer player;
    private static boolean playWhenReady;
    private int currentWindow = 0;
    private static long playbackPosition = 0;


    //PLAYER

    public static void resetPosition() {
        playbackPosition = 0;
        count = 0;
        showImage = true;
        playWhenReady = false;
    }


    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);
        Uri uri = Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8");
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        // Create a data source factory.
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory(Util.getUserAgent(getContext(), "exoplayer-codelab"));
        // Create a HLS media source pointing to a playlist uri.
        return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
        }
    }


    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }



}