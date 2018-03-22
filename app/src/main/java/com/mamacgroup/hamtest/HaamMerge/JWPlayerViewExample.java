//package com.mamacgroup.hamtest.HaamMerge;
//
//import android.app.Activity;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.design.widget.CoordinatorLayout;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
////import com.google.android.gms.analytics.HitBuilders;
//import com.longtailvideo.jwplayer.JWPlayerView;
//import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
//import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
//import com.mamacgroup.hamtest.R;
//
////import com.longtailvideo.jwplayer.cast.CastManager;
//
//public class JWPlayerViewExample extends Activity implements VideoPlayerEvents.OnFullscreenListener {
//
//	/**
//	 * Reference to the {@link JWPlayerView}
//	 */
//	private JWPlayerView mPlayerView;
//
//	/**
//	 * An instance of our event handling class
//	 */
//	private JWEventHandler mEventHandler;
//
//	/**
//	 * Reference to the {@link CastManager}
//	 */
//	//private CastManager mCastManager;
//
//	/**
//	 * Stored instance of CoordinatorLayout
//	 * http://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.html
//	 */
//	private CoordinatorLayout mCoordinatorLayout;
//	String channel_name = "ChannelName";
//	String add_url = "ChannelName";
//	String jw_url = "";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_jwplayerview);
//		mPlayerView = (JWPlayerView)findViewById(R.id.jwplayer);
//		TextView outputTextView = (TextView)findViewById(R.id.output);
//		ImageView im=(ImageView)findViewById(R.id.back_vsss_jw);
//		im.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				finish();
//			}
//		});
//		mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_jwplayerview);
//
//		// Handle hiding/showing of ActionBar
//		mPlayerView.addOnFullscreenListener(this);
//
//
//		new KeepScreenOnHandler(mPlayerView, getWindow());
//
//		// Instantiate the JW Player event handler class
//		mEventHandler = new JWEventHandler(mPlayerView, outputTextView);
//		if((getIntent().hasExtra("jw_url")))
//			jw_url = getIntent().getStringExtra("jw_url");
//			Log.e("video",jw_url);
//		if((getIntent().hasExtra("name")))
//			channel_name = getIntent().getStringExtra("name");
//
//		if((getIntent().hasExtra("add_url")))
//			add_url = getIntent().getStringExtra("add_url");
//		else
//			add_url = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator=";
//
//
//		//Ad ad = new Ad(AdSource.IMA, add_url);
//		//AdBreak adBreak = new AdBreak("10%", ad);
//		//List<AdBreak> adSchedule = new ArrayList<>();
//		//adSchedule.add(adBreak);
//
//
//
//
//		// Load a media source
//		PlaylistItem pi = new PlaylistItem.Builder()
//				.file(jw_url)
//				//.adSchedule(adSchedule)
//			//	.title("BipBop")
//			//	.description("A video player testing video.")
//				.build();
//
//		mPlayerView.load(pi);
//		mPlayerView.play();
//
//
//
//
//
//
//
//		// Get a reference to the CastManager
//	//	mCastManager = CastManager.getInstance();
//
//
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//			if(mPlayerView.getFullscreen()) {
//				mPlayerView.setFullscreen(false, true);
//			}
//		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//			if(!mPlayerView.getFullscreen()) {
//				mPlayerView.setFullscreen(true, true);
//			}
//		}
//	}
//
//	@Override
//	protected void onResume() {
//		// Let JW Player know that the app has returned from the background
//		super.onResume();
//		mPlayerView.onResume();
////		AppController.getInstance().getDefaultTracker().setScreenName(channel_name);
////		AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
////		AppController.getInstance().kTracker.event("Live Tv JWplayer",channel_name);
////
////
////		try {
////			Session.get_minimizetime(this);
////		}catch(Exception ex){
////			ex.printStackTrace();
////		}
//
//
//	}
//
//	@Override
//	protected void onPause() {
//		// Let JW Player know that the app is going to the background
//		mPlayerView.onPause();
//		super.onPause();
//
//		try {
////			AppController.getInstance().cancelPendingRequests();
////			Session.set_minimizetime(this);
//
//		}catch (Exception ex){
//			//	ex.printStackTrace();
//		}
//
//	}
//
//	@Override
//	protected void onDestroy() {
//		// Let JW Player know that the app is being destroyed
//		mPlayerView.onDestroy();
//		super.onDestroy();
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// Exit fullscreen when the user pressed the Back button
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (mPlayerView.getFullscreen()) {
//				mPlayerView.setFullscreen(false, true);
//				return false;
//			}
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	/**
//	 * Handles JW Player going to and returning from fullscreen by hiding the ActionBar
//	 *
//	 * @param fullscreen true if the player is fullscreen
//	 */
//
//
//	@Override
//	public void onFullscreen(boolean fullscreen) {
//
////		ActionBar actionBar = getSupportActionBar();
////		if (actionBar != null) {
////			if (fullscreen) {
////				actionBar.hide();
////			} else {
////				actionBar.show();
////			}
////		}
//
//		// When going to Fullscreen we want to set fitsSystemWindows="false"
//		mCoordinatorLayout.setFitsSystemWindows(!fullscreen);
//
//
//	}
//
//
//
//}
