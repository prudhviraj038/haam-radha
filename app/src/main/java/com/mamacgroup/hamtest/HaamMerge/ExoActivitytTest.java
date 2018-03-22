package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;

import java.util.ArrayList;

//import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;

//import okhttp3.OkHttpClient;

/**
 * Created by mac on 7/27/17.
 */

public class ExoActivitytTest extends Activity {

    SimpleExoPlayerView simpleExoPlayerView;
    Uri mp4VideoUri;

    ArrayList<String> urls;
    int current_video=0;
    SimpleExoPlayer player;
    ArrayList<SimpleExoPlayer> sources;
    LinearLayout pre,next;

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.haam_exo_view);

        pre = (LinearLayout) findViewById(R.id.prev);
        next = (LinearLayout) findViewById(R.id.next);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(current_video>0){
                    sources.get(current_video).setPlayWhenReady(false);
                    current_video--;

                    next_video();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(current_video<sources.size()-1){
                    sources.get(current_video).setPlayWhenReady(false);
                    current_video++;
                    next_video();

                }
            }
        });

        urls = new ArrayList<>();
       // urls.add("http://content.jwplatform.com/videos/ltBOfwb6-8dW5PJfi.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/2311500894029.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/2111500893636.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/2011500893458.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1911500893341.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1811500893210.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1711500893131.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1511500892814.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1411500892652.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1311500556556.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1211500556498.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1111500556394.mp4");
        urls.add("http://mamacgroup.com/news/uploads/news/1011500556315.mp4");



        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.simpleexoplayer_view);
        sources = new ArrayList<>();


        for (int i = 0; i < urls.size(); i++) {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player.setPlayWhenReady(false);
//        player.addListener(new ExoPlayer.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest) {
//
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                if (playbackState == ExoPlayer.STATE_ENDED) {
//
//
//                    current_video++;
//
//                    if (current_video < sources.size()) {
//                        Log.e("next","video");
//                       // next_video();
//                    }
//
//                    next_video();
//                }
//
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//
//            }
//
//            @Override
//            public void onPositionDiscontinuity() {
//
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//            }
//        });

            player.prepare(newVideoSource(urls.get(i)));
            sources.add(player);

    }

next_video();
    }


            private void next_video(){
                simpleExoPlayerView.setPlayer(sources.get(current_video));
                sources.get(current_video).seekTo(0,0);
                sources.get(current_video).setPlayWhenReady(true);
            }


    private MediaSource newVideoSource(String url) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        String userAgent = Util.getUserAgent(this, "Ham demo");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Log.e("cached","cached");
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent, bandwidthMeter);
        return new ExtractorMediaSource(Uri.parse(AppController.getProxy(this).getProxyUrl(url)), dataSourceFactory, extractorsFactory, null, null);


       // if(AppController.getProxy(this).isCached(url)) {
         //   Log.e("cached","cached");
           // DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent, bandwidthMeter);
            //return new ExtractorMediaSource(Uri.parse(AppController.getProxy(this).getProxyUrl(url)), dataSourceFactory, extractorsFactory, null, null);
        //}else{
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
//                    .readTimeout(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
//                    .followSslRedirects(false)
//                    .cache(new okhttp3.Cache(Utils.getVideoCacheDir(this),1024*1024*50))
//                    .build();
//
//            OkHttpDataSourceFactory dataSourceFactory = new OkHttpDataSourceFactory(okHttpClient,
//                    Util.getUserAgent(this, "Ham demo"),bandwidthMeter);
//            return new ExtractorMediaSource(Uri.parse(AppController.getProxy(this).getProxyUrl(url)), dataSourceFactory, extractorsFactory, null, null);
        //}


    }


}