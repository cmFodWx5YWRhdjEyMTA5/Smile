package com.zzj.open.base.player;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/24 13:21
 * @desc :exoPlayer
 * @version: 1.0
 */
/*
public class JZExoPlayer extends JZMediaInterface implements Player.EventListener, VideoListener {

    private SimpleExoPlayer simpleExoPlayer;
    private Handler mainHandler;
    private Runnable callback;
    private String TAG = "JZExoPlayer";
    private MediaSource videoSource;
    private long previousSeek = 0;


    @Override
    public void start() {
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void prepare() {
        Log.e(TAG, "prepare");
        mainHandler = new Handler();
        Context context = JzvdMgr.getCurrentJzvd().getContext();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE),
                360000, 600000, 1000, 5000,
                C.LENGTH_UNSET,
                false);

        // 2. Create the player
        RenderersFactory renderersFactory = new DefaultRenderersFactory(context);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(JzvdMgr.getCurrentJzvd().getContext(),trackSelector , loadControl, (DrmSessionManager<FrameworkMediaCrypto>) renderersFactory);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getResources().getString(R.string.app_name)));

        String currUrl = jzDataSource.getCurrentUrl().toString();
        if (currUrl.contains(".m3u8")) {
            videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(currUrl), mainHandler, null);
        } else {
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(currUrl));
        }
        simpleExoPlayer.addVideoListener(this);

        Log.e(TAG, "URL Link = " + currUrl);

        simpleExoPlayer.addListener(this);

        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
        callback = new onBufferingUpdate();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        JZMediaManager.instance().currentVideoWidth = width;
        JZMediaManager.instance().currentVideoHeight = height;
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JzvdMgr.getCurrentJzvd() != null) {
                JzvdMgr.getCurrentJzvd().onVideoSizeChanged();
            }
        });
    }

    @Override
    public void onRenderedFirstFrame() {
        Log.e(TAG, "onRenderedFirstFrame");
    }

    private class onBufferingUpdate implements Runnable {
        @Override
        public void run() {
            final int percent = simpleExoPlayer.getBufferedPercentage();
            JZMediaManager.instance().mainThreadHandler.post(() -> {
                if (JzvdMgr.getCurrentJzvd() != null) {
                    JzvdMgr.getCurrentJzvd().setBufferProgress(percent);
                }
            });
            if (percent < 100) {
                mainHandler.postDelayed(callback, 300);
            } else {
                mainHandler.removeCallbacks(callback);
            }
        }
    }

    @Override
    public void pause() {
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public boolean isPlaying() {
        return simpleExoPlayer.getPlayWhenReady();
    }

    @Override
    public void seekTo(long time) {
        if (time != previousSeek) {
            simpleExoPlayer.seekTo(time);
            previousSeek = time;
            JzvdMgr.getCurrentJzvd().seekToInAdvance = time;
        }
    }

    @Override
    public void release() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
        if (mainHandler != null)
            mainHandler.removeCallbacks(callback);
    }

    @Override
    public long getCurrentPosition() {
        if (simpleExoPlayer != null)
            return simpleExoPlayer.getCurrentPosition();
        else return 0;
    }

    @Override
    public long getDuration() {
        if (simpleExoPlayer != null)
            return simpleExoPlayer.getDuration();
        else return 0;
    }

    @Override
    public void setSurface(Surface surface) {
        simpleExoPlayer.setVideoSurface(surface);
        Log.e(TAG, "setSurface");
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        simpleExoPlayer.setVolume(leftVolume);
        simpleExoPlayer.setVolume(rightVolume);
    }

    @Override
    public void setSpeed(float speed) {
        PlaybackParameters playbackParameters = new PlaybackParameters(speed, 1.0F);
        simpleExoPlayer.setPlaybackParameters(playbackParameters);
    }

    @Override
    public void onTimelineChanged(final Timeline timeline, Object manifest, final int reason) {
        Log.e(TAG, "onTimelineChanged");
//        JZMediaManager.instance().mainThreadHandler.post(() -> {
//                if (reason == 0) {
//
//                    JzvdMgr.getCurrentJzvd().onInfo(reason, timeline.getPeriodCount());
//                }
//        });
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.e(TAG, "onLoadingChanged");
    }

    @Override
    public void onPlayerStateChanged(final boolean playWhenReady, final int playbackState) {
        Log.e(TAG, "onPlayerStateChanged" + playbackState + "/ready=" + String.valueOf(playWhenReady));
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JzvdMgr.getCurrentJzvd() != null) {
                switch (playbackState) {
                    case Player.STATE_IDLE: {
                    }
                    break;
                    case Player.STATE_BUFFERING: {
                        mainHandler.post(callback);
                    }
                    break;
                    case Player.STATE_READY: {
                        if (playWhenReady) {
                            JzvdMgr.getCurrentJzvd().onPrepared();
                        } else {
                        }
                    }
                    break;
                    case Player.STATE_ENDED: {
                        JzvdMgr.getCurrentJzvd().onAutoCompletion();
                    }
                    break;
                }
            }
        });
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.e(TAG, "onPlayerError" + error.toString());
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JzvdMgr.getCurrentJzvd() != null) {
                JzvdMgr.getCurrentJzvd().onError(1000, 1000);
            }
        });
    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {
        JZMediaManager.instance().mainThreadHandler.post(() -> {
            if (JzvdMgr.getCurrentJzvd() != null) {
                JzvdMgr.getCurrentJzvd().onSeekComplete();
            }
        });
    }
}
*/
