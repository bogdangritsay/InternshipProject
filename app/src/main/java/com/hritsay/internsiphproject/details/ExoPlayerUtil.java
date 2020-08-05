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
    private static ExoPlayerUtil exoPlayerUtil;
    private SimpleExoPlayer player;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private MediaSource mediaSource;

    /**
     * Getting instance of ExoPlayer
     * @return instance of ExoPlayer
     */
    public static ExoPlayerUtil getInstance() {
        if(exoPlayerUtil == null) {
            exoPlayerUtil = new ExoPlayerUtil();
        }
        return exoPlayerUtil;
    }

    private ExoPlayerUtil() {}

    /**
     * Initializing of ExoPlayer
     * @param context it's context for player
     */
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

    /**
     * Releasing for player
     */
    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
        }
    }

    /**
     * Method for playing video in player
     */
    public void play() {
        player.setPlayWhenReady(true);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    /**
     * Method for pausing video in player
     */
    public void pause() {
        player.setPlayWhenReady(false);
        player.stop();
    }

    /**
     * Method for reset playback position and player pausing
     */
    public void reset() {
        pause();
        playbackPosition = 0;
    }

    /**
     * Getter for player
     * @return player
     */
    public SimpleExoPlayer getPlayer() {
        return player;
    }

    /**
     * Method for setting playback position
     * @param playbackPosition position
     */
    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    /**
     * Method for getting playback position
     * @return position
     */
    public long getPlaybackPosition() {
        return playbackPosition;
    }
}
