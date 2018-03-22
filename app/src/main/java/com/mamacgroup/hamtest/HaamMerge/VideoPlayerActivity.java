package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.mamacgroup.hamtest.Session;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by mac on 10/24/16.
 */
public class VideoPlayerActivity extends Activity implements GestureDetector.OnGestureListener {
    SimpleExoPlayerView simpleExoPlayerView;
    LinearLayout back,next,watch,read,line,sha_ll,pd;
    SimpleExoPlayer player;
    ImageView img,am_img,ch_img,logo;
    ArrayList<News> newses;
    MyTextView news_title,time,pd_tv,watch_tv,read_tv;
    RelativeLayout rl;
    String com="0";
    String cat_id="";
    String video_id="";
    ProgressBar pd_pd;
    int current_video=0;
    int index=-1;
    ArrayList<SimpleExoPlayer> sources;
    ImageView fav_btn,share_btn;
    GestureDetector gestureDetector;
    String n_id="-1";
    String last_id="0";
    String act="0";
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Remove notification bar
       // this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.haam_exovideodisplay);

        deleteCache(this);
        newses=new ArrayList<>();
       Uri data = getIntent().getData();
//        if(getIntent().hasExtra("cat_id")){
//            cat_id=getIntent().getStringExtra("cat_id");
//        }else
        if(getIntent().hasExtra("news")) {
            newses = (ArrayList<News>) getIntent().getSerializableExtra("news");
            last_id=getIntent().getStringExtra("last");
            act=getIntent().getStringExtra("cat_act");
            Log.e("news_size",String.valueOf(newses.size()));
        }else if(getIntent().hasExtra("news_id")) {
            n_id = getIntent().getStringExtra("news_id");
        }else {
            if (getIntent().getData() == null) {
//            get_language_words("0");
            } else {
                String scheme = data.getScheme(); // "http"
                String host = data.getHost(); // "twitter.com"
                List<String> params = data.getPathSegments();

                for (int i = 0; i < params.size(); i++) {
                    Log.e("prams_size", params.get(i));
                }
               n_id = params.get(0); // "status"
//            String second = params.get(1);

            }
        }
        gestureDetector=new GestureDetector(this);

        for (int i = 0; i < newses.size(); i++) {
           Log.e("idddd",newses.get(i).id);
        }

        pd_pd=(ProgressBar)findViewById(R.id.progressBar);
        pd=(LinearLayout)findViewById(R.id.pdd_lll);
        img=(ImageView)findViewById(R.id.rl_jcat1);
        ch_img=(ImageView)findViewById(R.id.ch_img);
        logo=(ImageView)findViewById(R.id.logo_ham_own);
        am_img=(ImageView)findViewById(R.id.anim_img);
        img.setVisibility(View.GONE);
        pd.setVisibility(View.GONE);

        rl=(RelativeLayout)findViewById(R.id.rl_main);
        back=(LinearLayout)findViewById(R.id.back_ll);
        next=(LinearLayout)findViewById(R.id.next_ll);
        read=(LinearLayout)findViewById(R.id.red_ll);
        watch=(LinearLayout)findViewById(R.id.blue_ll);
        line=(LinearLayout)findViewById(R.id.line_ll);
        sha_ll=(LinearLayout)findViewById(R.id.shadow_llll);
        news_title=(MyTextView)findViewById(R.id.news_title_tv);
        time=(MyTextView)findViewById(R.id.time_tvvv);
        pd_tv=(MyTextView)findViewById(R.id.pd_tv_loading);
        pd_tv.setText(Session.getword(this,"Loading"));
        pd_tv.setVisibility(View.GONE);
        read_tv=(MyTextView)findViewById(R.id.read_tvvv);
        read_tv.setText(Session.getword(this,"Read"));
        watch_tv=(MyTextView)findViewById(R.id.watch_tvvvvv);
        watch_tv.setText(Session.getword(this,"Watch"));
        share_btn = (ImageView) findViewById(R.id.share_btn);
        fav_btn = (ImageView) findViewById(R.id.fav_btn);
        share_btn.setVisibility(View.GONE);
        fav_btn.setVisibility(View.GONE);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, newses.get(current_video).share_str);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "share"));
            }
        });
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppController.getInstance().selected_channels.contains(newses.get(current_video).id)){
                    AppController.getInstance().selected_channels.remove(newses.get(current_video).id);
                    fav_btn.setImageResource(R.drawable.haam_star_empty);
                }else{
                    AppController.getInstance().selected_channels.add(newses.get(current_video).id);
                    fav_btn.setImageResource(R.drawable.haam_star_fill);

                }
            }
        });
        sources = new ArrayList<>();
        simpleExoPlayerView=(SimpleExoPlayerView)findViewById(R.id.view);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        simpleExoPlayerView.setUseController(false);
        if(newses.size()==0)
            get_news();
        else {
            share_btn.setVisibility(View.VISIBLE);
            fav_btn.setVisibility(View.VISIBLE);
//            prepare_players();
            for (int i = 0; i <newses.size(); i++) {
                if (!AppController.getInstance().news_viewed.contains(newses.get(i).id)) {
                    if (index == -1) {
                        current_video = i;
                        index = i;
                        Log.e("currnetvideo1", String.valueOf(current_video));
                    }
                }
            }
            next_video();
//            if(act.equals("cat")){
//                next.setVisibility(View.VISIBLE);
//                back.setVisibility(View.VISIBLE);
//            }else{
//                next.setVisibility(View.GONE);
//                back.setVisibility(View.GONE);
//            }
        }

//        if(act.equals("cat")){
//            next.setVisibility(View.VISIBLE);
//            back.setVisibility(View.VISIBLE);
//        }else{
//            next.setVisibility(View.GONE);
//            back.setVisibility(View.GONE);
//        }
        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(newses.size()!=0) {
                    motionEvent.setSource(1);
                    Log.e("long press3", "long press1");
//                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                        Log.e("cv",String.valueOf(current_video));
////                        sources.get(current_video).setPlayWhenReady(false);
//                    } else {
//                        Log.e("long press2", "long press2");
//                        Log.e("cv1",String.valueOf(current_video));
////                         sources.get(current_video).setPlayWhenReady(true);
//                    }
                    gestureDetector.onTouchEvent(motionEvent);
                    return true;
                }
                return true;
            }
        });
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(newses.size()!=0) {
                    motionEvent.setSource(0);
//                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                        Log.e("cv2",String.valueOf(current_video));
////                        sources.get(current_video).setPlayWhenReady(false);
//                    } else {
//                        Log.e("cv3",String.valueOf(current_video));
////                        sources.get(current_video).setPlayWhenReady(true);
//                    }
                    gestureDetector.onTouchEvent(motionEvent);
                    return true;
                }
                return true;
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).description.equals("")){
                    Intent intent = new Intent(VideoPlayerActivity.this, DescriptionActivity.class);
                    intent.putExtra("news", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))));
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                    startActivity(intent, options.toBundle());
//                    startActivity(intent);
                }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).external_link.equals("")) {
                    Intent intent = new Intent(VideoPlayerActivity.this, WebviewActivity.class);
                    intent.putExtra("link", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).external_link);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                    startActivity(intent, options.toBundle());
                }
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("current",Settings.getWishid(VideoPlayerActivity.this));
                if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).large_video.equals("")) {
                    Intent intent=new Intent(VideoPlayerActivity.this,ExoActivity.class);
                    intent.putExtra("jw_url", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).large_video);
                    intent.putExtra("name", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).title);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                    startActivity(intent, options.toBundle());
                }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).m3u8_url.equals("")){
//                    Intent intent=new Intent(VideoPlayerActivity.this,JWPlayerViewExample.class);
//                    intent.putExtra("jw_url", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).m3u8_url);
//                    intent.putExtra("name", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).title);
//                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
//                    startActivity(intent, options.toBundle());
                }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).video_script.equals("")){
                    Intent intent=new Intent(VideoPlayerActivity.this,VideoPlayerActivityMain.class);
                    intent.putExtra("video",newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).video_script);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                    startActivity(intent, options.toBundle());
//                    startActivity(intent);
                }
            }
        });


        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, 0, sha_ll.getHeight(),
                        new int[] {
                                0x000000,0x11000000,0x22000000,0x44000000,0x55000000,0x77000000,0x99000000}, //substitute the correct colors for these
                        new float[] {
                                0,0.1f,0.2f,0.3f,0.45f, 0.55f, 1 },
                        Shader.TileMode.REPEAT);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        //sha_ll.setBackgroundDrawable((Drawable) p);


    }


    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("ended",Settings.getWishid(VideoPlayerActivity.this));
            if(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this)) == newses.size() - 1){
                img.setVisibility(View.GONE);
                onBackPressed();
            }else {
                img.setVisibility(View.GONE);
                next.performClick();
            }
        }
    };
    private void pausePlayer(){
        try {
            if (simpleExoPlayerView.getPlayer() != null) {
                simpleExoPlayerView.getPlayer().setPlayWhenReady(false);
                //sources.get(current_video).getPlaybackState();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void startPlayer(){

        if(newses.size()==0){



        }else {

            if (simpleExoPlayerView.getPlayer() != null) {
                simpleExoPlayerView.setPlayer(simpleExoPlayerView.getPlayer());
                simpleExoPlayerView.getPlayer().setPlayWhenReady(true);
                //sources.get(current_video).getPlaybackState();
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        if(com.equals("0")&&player!=null) {
            player.stop();
        }
        super.onBackPressed();  // optional depending on your needs
    }
    @Override
    public void onResume(){
        super.onResume();
        //AppController.getInstance().getDefaultTracker().setScreenName("NEWS SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        startPlayer();
        try {
            Log.e("time_diff","time_diff");
//            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "destory");
        Settings.setCurrencies(this, AppController.getInstance().selected_channels.toString());
        for (int i=0;i<newses.size();i++){
//            sources.get(i).release();
        }
        try {
             simpleExoPlayerView.getPlayer().release();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        Settings.setNewsViewed(this, AppController.getInstance().news_viewed.toString());
        Settings.set_news_viewed(this, AppController.getInstance().cat_images.toString());
        Settings.set_news_viewed_des(this, AppController.getInstance().cat_des.toString());
        Log.e("cat_object1",String.valueOf(AppController.getInstance().news_viewed));
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("pause", "pause");
        pausePlayer();
        try {
            AppController.getInstance().cancelPendingRequests();
            Settings.set_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    private long req_start_time;
    public void get_news() {
        req_start_time=System.currentTimeMillis();
        String url = null;
        if(!n_id.equals("-1")){
            url = Settings.SERVERURL + "news.php?news_id=" + n_id;
        }
//        else {
//            url = Settings.SERVERURL + "news.php?category_id=" + cat_id;
//        }
//        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        pd.setVisibility(View.VISIBLE);
        pd_tv.setVisibility(View.VISIBLE);
        final JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
//                pd.setVisibility(View.GONE);
                newses.clear();
                Log.e("response is: ", jsonObject.toString());
                long total_req_time=System.currentTimeMillis()-req_start_time;

//                Toast.makeText(VideoPlayerActivity.this, String.valueOf(total_req_time)+" MilliSeconds", Toast.LENGTH_SHORT).show();


                try {
                    for (int i = 0; i <jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        News prod = new News(sub);
                        newses.add(prod);
                        if (!AppController.getInstance().news_viewed.contains(newses.get(i).id)) {
                            if (index == -1) {
                                current_video = i;
                                index = i;
                                Log.e("currnetvideo1", String.valueOf(current_video));
                            }
                        }
                    }
                   // play();
//                    Toast.makeText(VideoPlayerActivity.this, "news loaded", Toast.LENGTH_SHORT).show();
                    share_btn.setVisibility(View.VISIBLE);
                    fav_btn.setVisibility(View.VISIBLE);
//                    prepare_players();


                    next_video();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(VideoPlayerActivity.this, Session.getword(VideoPlayerActivity.this,"no_network"), Toast.LENGTH_SHORT).show();
                pd.setVisibility(View.GONE);
                pd_tv.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
//        Toast.makeText(VideoPlayerActivity.this, "request started", Toast.LENGTH_SHORT).show();


    }


    final Handler h = new Handler();
    final Runnable r = new Runnable() {
        public void run() {
            pd.setVisibility(View.VISIBLE);

        }
    };


//    private void prepare_players(){
//        Log.e("news_size1",String.valueOf(newses.size()));
//
//
//        for (int i = 0; i < newses.size(); i++) {
//                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//                TrackSelection.Factory videoTrackSelectionFactory =
//                        new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                TrackSelector trackSelector =
//                        new DefaultTrackSelector(videoTrackSelectionFactory);
//
//                SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//                player.setPlayWhenReady(false);
//            final int finalI = i;
//            player.addListener(new ExoPlayer.EventListener() {
//                                       @Override
//                                       public void onTimelineChanged(Timeline timeline, Object manifest) {
//
//                                       }
//
//                                       @Override
//                                       public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//                                       }
//
//                                       @Override
//                                       public void onLoadingChanged(boolean isLoading) {
//
//                                       }
//
//                                       @Override
//                                       public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                                           if (playbackState == ExoPlayer.STATE_ENDED) {
//
//
//                                               current_video++;
//
//                                               if (current_video < newses.size()) {
//                                                   Log.e("next", "video");
//                                                   next_video();
//                                               } else {
//                                                   current_video--;
//                                                   finish();
//                                               }
//                                           }
//
//                                           if (playbackState == ExoPlayer.STATE_BUFFERING) {
//                                               pd_tv.setVisibility(View.VISIBLE);
//
//                                               h.postDelayed(r, 1000);
////                        pd.setVisibility(View.VISIBLE);
//
//                                           } else {
//                                               h.removeCallbacks(r);
//                                               if(newses.size()>1) {
//                                                   if (!AppController.getInstance().news_viewed.contains(newses.get(current_video).id)) {
//                                                       AppController.getInstance().news_viewed.add(newses.get(current_video).id);
//                                                       try {
//                                                           if (current_video + 1 < newses.size() && !AppController.getInstance().cat_images.getString(newses.get(current_video + 1).cat_id).equals("0")) {
//                                                               Log.e("cat_id", newses.get(current_video + 1).cat_id);
//                                                               AppController.getInstance().cat_images.put(newses.get(current_video + 1).cat_id, newses.get(current_video + 1).image);
//                                                           } else {
//                                                               AppController.getInstance().cat_images.put(newses.get(current_video).cat_id, "0");
//                                                           }
//                                                       } catch (JSONException e) {
//
//                                                       }
//                                                   }
//                                               }
//                                               pd.setVisibility(View.GONE);
//                                               pd_tv.setVisibility(View.GONE);
//
//                                           }
//
//                                       }
//
//                                   @Override
//                                   public void onRepeatModeChanged(int i) {
//
//                                   }
//
//                                   @Override
//                                       public void onPlayerError(ExoPlaybackException error) {
//                                           Log.e("player error", error.toString());
////                                           if(finalI<sources.size()) {
////                                               sources.remove(finalI);
////                                               next.performClick();
////                                           }
//
//                                       }
//
//                                       @Override
//                                       public void onPositionDiscontinuity() {
//
//                                       }
//
//                                       @Override
//                                       public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//                                       }
//                                   }
//                );
//                Log.e("news_url",newses.get(i).video_script);
//                player.prepare(newVideoSource(newses.get(i).small_video));
//                sources.add(player);
//
//        }
//
//        for (int i = 0; i < newses.size(); i++) {
//            if (!AppController.getInstance().news_viewed.contains(newses.get(i).id)) {
//                if(index==-1) {
//                    current_video = i;
//                    index=i;
//                }
//            }
//        }
//        if(newses.size()==1){
//            next.setVisibility(View.GONE);
//            back.setVisibility(View.GONE);
//        }else{
//            next.setVisibility(View.VISIBLE);
//            back.setVisibility(View.VISIBLE);
//        }
//
//    }
    private SimpleExoPlayer prepare_player(int i){
        Log.e("news_size1",String.valueOf(newses.size()));
//        for (int i = 0; i < newses.size(); i++) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            player.setPlayWhenReady(false);
            final int finalI = i;
            player.addListener(new Player.EventListener() {


                                   @Override
                                   public void onTimelineChanged(Timeline timeline, Object o, int j) {

                                   }

                                   @Override
                                   public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                                   }

                                   @Override
                                   public void onLoadingChanged(boolean isLoading) {

                                   }

                                   @Override
                                   public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                                       if (playbackState == ExoPlayer.STATE_ENDED) {


                                           current_video++;

                                           if (current_video < newses.size()) {
                                               Log.e("next", "video");
                                               next_video();
                                           } else {
                                               current_video--;
                                               finish();
                                           }
                                       }

                                       if (playbackState == ExoPlayer.STATE_BUFFERING) {
                                           pd_tv.setVisibility(View.VISIBLE);

                                           h.postDelayed(r, 1000);
//                        pd.setVisibility(View.VISIBLE);

                                       } else {
                                           h.removeCallbacks(r);
                                           if (newses.size() > 0 && act.equals("cat")) {
                                               if (!AppController.getInstance().news_viewed.contains(newses.get(current_video).id)) {
                                                   AppController.getInstance().news_viewed.add(newses.get(current_video).id);
                                                   Log.e("news_id", String.valueOf(AppController.getInstance().news_viewed.size()));
                                                   try {
                                                       if (current_video + 1 < newses.size() && !AppController.getInstance().cat_images.getString(newses.get(current_video + 1).cat_id).equals("0")) {
                                                           Log.e("cat_id", newses.get(current_video + 1).cat_id);
                                                           AppController.getInstance().cat_images.put(newses.get(current_video + 1).cat_id, newses.get(current_video + 1).image);
                                                           AppController.getInstance().cat_des.put(newses.get(current_video + 1).cat_id, newses.get(current_video + 1).about);
                                                       } else {
                                                           AppController.getInstance().cat_images.put(newses.get(current_video).cat_id, "0");
                                                           AppController.getInstance().cat_des.put(newses.get(current_video).cat_id, "0");
                                                       }
                                                   } catch (JSONException e) {

                                                   }
                                               }

                                           }
                                           pd.setVisibility(View.GONE);
                                           pd_tv.setVisibility(View.GONE);

                                       }

                                   }

                                   @Override
                                   public void onRepeatModeChanged(int i) {

                                   }

                                   @Override
                                   public void onShuffleModeEnabledChanged(boolean b) {

                                   }

                                   @Override
                                   public void onPlayerError(ExoPlaybackException error) {
                                       Log.e("player error", error.toString());
//                                           if(finalI<sources.size()) {
//                                               sources.remove(finalI);
//                                               next.performClick();
//                                           }

                                   }

                                   @Override
                                   public void onPositionDiscontinuity(int i) {

                                   }

//                                   @Override
//                                   public void onPositionDiscontinuity() {
//
//                                   }

                                   @Override
                                   public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                                   }

                                   @Override
                                   public void onSeekProcessed() {

                                   }
                               }
            );
        Log.e("news_url", String.valueOf(i));
            Log.e("news_url", newses.get(i).small_video);
            player.prepare(newVideoSource(newses.get(i).small_video));
            sources.clear();
//            sources.add(player);

//        if(newses.size()==1){
//            next.setVisibility(View.GONE);
//            back.setVisibility(View.GONE);
//        }else{
//            next.setVisibility(View.VISIBLE);
//            back.setVisibility(View.VISIBLE);
//        }
        return player;
    }


    private void next_video(){
        if(simpleExoPlayerView.getPlayer()!=null) {
            simpleExoPlayerView.getPlayer().release();
        }

        Log.e("next", String.valueOf(newses.size()));
        Log.e("currnetvideo", String.valueOf(current_video));

        SimpleExoPlayer player=prepare_player(current_video);
        simpleExoPlayerView.setPlayer(player);
        player.seekTo(0, 0);
        player.setPlayWhenReady(true);
        pd_tv.setText(newses.get(current_video).title);
        time.setText(newses.get(current_video).time);
        Log.e("ch_img", newses.get(current_video).ch_image);
        Picasso.with(VideoPlayerActivity.this).load(newses.get(current_video).ch_image_white).into(ch_img);
//        Picasso.with(VideoPlayerActivity.this).load(newses.get(current_video).ch_image_white).into(logo);
        if(AppController.getInstance().selected_channels.contains(newses.get(current_video).id)){

            fav_btn.setImageResource(R.drawable.haam_star_fill);
        }else{
            fav_btn.setImageResource(R.drawable.haam_star_empty);
        }

        if(!newses.get(current_video).m3u8_url.equals("")||!newses.get(current_video).video_script.equals("")||!newses.get(current_video).large_video.equals("")){
            read.setVisibility(View.GONE);
            watch.setVisibility(View.VISIBLE);
        }else if(!newses.get(current_video).external_link.equals("")||!newses.get(current_video).description.equals("")){
            watch.setVisibility(View.GONE);
            read.setVisibility(View.VISIBLE);
        }else{
            watch.setVisibility(View.GONE);
            read.setVisibility(View.GONE);
        }


    }


    private MediaSource newVideoSource(String url) {
        return new ExtractorMediaSource(Uri.parse(url), new CacheDataSourceFactory(this, 1024 * 1024 * 1024, 5 * 1024 * 1024), new DefaultExtractorsFactory(), null, null);
//        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        String userAgent = Util.getUserAgent(this, "Ham demo");
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//        Log.e("cached", "cached");
////        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent, bandwidthMeter);
////        return new ExtractorMediaSource(Uri.parse(AppController.getProxy(this).getProxyUrl(url)), dataSourceFactory, extractorsFactory, null, null);
//
//
//         if(AppController.getProxy(this).isCached(url)) {
//           Log.e("cached", "cached");
//         DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent, bandwidthMeter);
//        return new ExtractorMediaSource(Uri.parse(AppController.getProxy(this).getProxyUrl(url)), dataSourceFactory, extractorsFactory, null, null);
//        }else{
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
//                    .readTimeout(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
//                    .followSslRedirects(false)
//                    .cache(new okhttp3.Cache(Utils.getVideoCacheDir(this),1024*1024*1024))
//                    .build();
//
//            OkHttpDataSourceFactory dataSourceFactory = new OkHttpDataSourceFactory(okHttpClient,
//                    Util.getUserAgent(this, "Ham demo"),bandwidthMeter);
//            return new ExtractorMediaSource(Uri.parse(AppController.getProxy(this).getProxyUrl(url)), dataSourceFactory, extractorsFactory, null, null);
////             DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent, bandwidthMeter);
////             return new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
//         }
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
//        sources.get(current_video).setPlayWhenReady(false);
        return  false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.e("Swipe test",String.valueOf(motionEvent.getSource()));
        if(motionEvent.getSource()==0){
            if(simpleExoPlayerView.getPlayer().getPlayWhenReady()) {
                    if (current_video > 0) {
                        pd_tv.setText(newses.get(current_video).title);
                        pd.setVisibility(View.VISIBLE);
                        pd_tv.setVisibility(View.VISIBLE);
                        simpleExoPlayerView.getPlayer().setPlayWhenReady(false);
                        current_video--;
                        Settings.setWishid(VideoPlayerActivity.this, String.valueOf(current_video));
                        next_video();
                    }
                }else{
                simpleExoPlayerView.getPlayer().setPlayWhenReady(true);
                }
        }else{
            if(simpleExoPlayerView.getPlayer().getPlayWhenReady()) {
                if (current_video < newses.size() - 1) {
                    if(!last_id.equals(newses.get(current_video).id)) {
                        pd_tv.setText(newses.get(current_video).title);
                        pd.setVisibility(View.VISIBLE);
                        pd_tv.setVisibility(View.VISIBLE);
                        simpleExoPlayerView.getPlayer().setPlayWhenReady(false);
                        current_video++;
                        Settings.setWishid(VideoPlayerActivity.this, String.valueOf(current_video));
                        next_video();
                    }else{
//                        if(newses.size()!=1){
                            finish();
//                        }
                    }
                }else {
//                    if(newses.size()!=1){
                        finish();
//                    }

                }
            }else{
                simpleExoPlayerView.getPlayer().setPlayWhenReady(true);
            }
        }
        return  false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return  false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.e("long press",String.valueOf(motionEvent.getSource()));
//        sources.get(current_video).setPlayWhenReady(false);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        Log.e("Swipe test1",String.valueOf(motionEvent1.getSource()));
        if(motionEvent.getY() - motionEvent1.getY() > 50){

//            Toast.makeText(VideoPlayerActivity.this , " Swipe Up " , Toast.LENGTH_LONG).show();
            Log.e("Swipe Up",String.valueOf(motionEvent.getSource()));
            Log.e("current_swipe",Settings.getWishid(VideoPlayerActivity.this));
//            Log.e("m3u8_url",newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).m3u8_url);
            if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).large_video.equals("")) {
                Intent intent=new Intent(VideoPlayerActivity.this,ExoActivity.class);
                intent.putExtra("jw_url", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).large_video);
                intent.putExtra("name", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).title);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                startActivity(intent, options.toBundle());
            }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).m3u8_url.equals("")){
//                Intent intent=new Intent(VideoPlayerActivity.this,JWPlayerViewExample.class);
//                intent.putExtra("jw_url", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).m3u8_url);
//                intent.putExtra("name", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).title);
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
//                startActivity(intent, options.toBundle());
            }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).video_script.equals("")){
                Intent intent=new Intent(VideoPlayerActivity.this,VideoPlayerActivityMain.class);
                intent.putExtra("video",newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).video_script);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                startActivity(intent, options.toBundle());
//                    startActivity(intent);
                }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).description.equals("")){
                    Intent intent = new Intent(VideoPlayerActivity.this, DescriptionActivity.class);
                    intent.putExtra("news", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))));
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                    startActivity(intent, options.toBundle());
//                    startActivity(intent);
                }else if(!newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).external_link.equals("")) {
                    Intent intent = new Intent(VideoPlayerActivity.this, WebviewActivity.class);
                    intent.putExtra("link", newses.get(Integer.parseInt(Settings.getWishid(VideoPlayerActivity.this))).external_link);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(VideoPlayerActivity.this, R.anim.enter_from_down, R.anim.exit_to_up);
                    startActivity(intent, options.toBundle());
                }else{
                    watch.setVisibility(View.GONE);
                    read.setVisibility(View.GONE);
                }
            return true;
        }

        if(motionEvent1.getY() - motionEvent.getY() > 50){

//            Toast.makeText(VideoPlayerActivity.this , " Swipe Down " , Toast.LENGTH_LONG).show();
            finish();
            Log.e("Swipe Down","Swipe Down");
            return true;
        }if(motionEvent.getX() - motionEvent1.getX() > 50){

//            Toast.makeText(VideoPlayerActivity.this , " Swipe Left " , Toast.LENGTH_LONG).show();

            return true;
        }

        if(motionEvent1.getX() - motionEvent.getX() > 50) {

//            Toast.makeText(VideoPlayerActivity.this, " Swipe Right ", Toast.LENGTH_LONG).show();

            return true;
        }
        else {

            return true ;
        }
    }
   @Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
        Log.e("Home Down","Home Down");
        return true;
    default:
        return true;
    }
}
}