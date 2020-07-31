package com.hritsay.internsiphproject.details;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerUtil {
    private final String TAG = getClass().getCanonicalName();
    private static ExoPlayerUtil exoPlayerUtil;
    private SimpleExoPlayer player;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private MediaSource mediaSource;

    public static ExoPlayerUtil getInstance() {
        if(exoPlayerUtil == null) {
            exoPlayerUtil = new ExoPlayerUtil();
        }
        return exoPlayerUtil;
    }

    private ExoPlayerUtil() {}

    public void initializePlayer(Context context) {
        player = new SimpleExoPlayer.Builder(context).build();
        Uri uri = Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8");
        mediaSource =  buildMediaSource(uri, context);
    }

    private MediaSource buildMediaSource(Uri uri, Context context) {
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "KinoInternship"));

        return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
        }
    }

    public void play() {
        player.setPlayWhenReady(true);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    public void pause() {
        player.setPlayWhenReady(false);
        player.stop();
    }

    public void reset() {
        pause();
        playbackPosition = 0;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }


    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }
}
