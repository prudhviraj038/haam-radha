//package com.mamacgroup.hamtest;
//
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//
//import android.util.Log;
//import android.view.KeyEvent;
//
////import com.crashlytics.android.answers.Answers;
////import com.crashlytics.android.answers.CustomEvent;
////import com.google.android.gms.analytics.HitBuilders;
//import com.longtailvideo.jwplayer.JWPlayerFragment;
//import com.longtailvideo.jwplayer.JWPlayerView;
//import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
//
///**
// * Created by mac on 10/19/16.
// */
//
//
//public class JWPlayerActivity extends FragmentActivity {
//
//
//    JWPlayerView playerView;
//    String channel_name;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//         //Session.forceRTLIfSupported(this);
//        // Set the Activity's content view.
//
//
//        String jw_url = getIntent().getStringExtra("jw_url");
//        channel_name = getIntent().getStringExtra("name");
//
//        Log.e("url",jw_url);
//
//        setContentView(R.layout.jw_player_fragment);
//
//        // Get a handle to the JWPlayerFragment
//        JWPlayerFragment fragment = (JWPlayerFragment) getFragmentManager().findFragmentById(R.id.playerFragment);
//
//        // Get a handle to the JWPlayerView
//         playerView = fragment.getPlayer();
//
//
//        // Create a PlaylistItem
//        PlaylistItem video = new PlaylistItem(jw_url);
//
//        // Load a stream into the player
//
//        playerView.load(video);
//        playerView.play();
//
//
//    }
//
//
//    @Override
//    public void onPause() {
//        super.onPause();  // Always call the superclass method first
//
//        // Release the Camera because we don't need it when paused
//        // and other activities might need to use it.
//
//
//        try {
//            AppController.getInstance().cancelPendingRequests();
//            Session.set_minimizetime(this);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();  // Always call the superclass method first
//
////        AppController.getInstance().getDefaultTracker().setScreenName(channel_name);
////        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
////        AppController.getInstance().kTracker.event("Live Tv",channel_name);
////        Answers.getInstance().logCustom(new CustomEvent("Live Tv"));
//
//
//
//
//    }
//
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        // Set fullscreen when the device is rotated to landscape
//        playerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE, true);
//        super.onConfigurationChanged(newConfig);
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // Exit fullscreen when the user pressed the Back button
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (playerView.getFullscreen()) {
//                playerView.setFullscreen(false, false);
//                return false;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//}
