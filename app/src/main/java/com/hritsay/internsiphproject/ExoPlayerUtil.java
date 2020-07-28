package com.hritsay.internsiphproject;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerUtil {
    private final String TAG = getClass().getCanonicalName();
    private static ExoPlayerUtil exoPlayerUtil;
    private static SimpleExoPlayer player;
    private static Context context;
    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private PlayerView playerView;
    private MediaSource mediaSource;

    public static void setContext(Context context) {
        ExoPlayerUtil.context = context;
    }

    public static ExoPlayerUtil getInstance() {
        if(exoPlayerUtil == null) {
            exoPlayerUtil = new ExoPlayerUtil();
        }

        return exoPlayerUtil;
    }

    private ExoPlayerUtil() {
        player = new SimpleExoPlayer.Builder(context).build();
    }


    public void initializePlayer() {
        playerView.setPlayer(player);
        Uri uri = Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8");
        mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);


    }

    private MediaSource buildMediaSource(Uri uri) {
        // Create a data source factory.
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "KinoInternship"));
        // Create a HLS media source pointing to a playlist uri.
        return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    public void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            //player.release();
        }
    }

    public void play() {
        player.setPlayWhenReady(true);
        player.seekTo(0, playbackPosition);
    }

    public void pause() {
        player.setPlayWhenReady(false);
        player.stop();
    }

    public void reset() {
        pause();
        playbackPosition = 0;
    }

    public static SimpleExoPlayer getPlayer() {
        return player;
    }

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }
}
