

package com.mamacgroup.hamtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.ContentViewEvent;
//import com.crashlytics.android.answers.CustomEvent;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.api.GoogleApiClient;

import com.mamacgroup.hamtest.HaamMerge.Category;
import com.mamacgroup.hamtest.HaamMerge.CategoryActivity;
import com.mamacgroup.hamtest.Qucik.ActionItem;
import com.mamacgroup.hamtest.Qucik.QuickAction;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mamacgroup.hamtest.R.id.end_of_news;
import static com.mamacgroup.hamtest.R.id.ham_cat_recyclerview;
import static com.mamacgroup.hamtest.R.id.header_tab;
import static com.mamacgroup.hamtest.R.id.recyclerView;
import static com.mamacgroup.hamtest.R.id.type;
import static com.mamacgroup.hamtest.R.id.webview_footer_holder;

/**
 * Created by mac on 1/11/17.
 */

public class NewsRecycleListActivity extends BaseActivity implements
        Serializable,
        SettingsFragment.FragmentTouchListner,
        SubCategories.FragmentTouchListner,
        SubCateDetailpage.FragmentTouchListner,
        NewsDetailFragment.FragmentTouchListner,
        AccountFragment.FragmentTouchListner,
        LiveTvFragment.FragmentTouchListner,
        SearchFragment.FragmentTouchListner,
        NotifySourcesFragment.FragmentTouchListner,
        MySourcesFragment.FragmentTouchListner,
        YoutubeChanelsFragment.FragmentTouchListner,
        VideoCateDetailpage.FragmentTouchListner {



    News_listAdapter news_list_adapter;
    MultiViewTypeActAdapter adapter;
    MultiViewTypeAdapterChannels channel_adapter;
    MultiViewTypeAdapterCategory youtube_adapter;
    YoutubeChannelsAdapter youtubeChannelsAdapter;

  //  ListView listView;
    ArrayList<News> news;
    ArrayList<Model> news_modle;
    ArrayList<Chanel> chanels;

    ArrayList<News> latest_news;
    LinearLayout progressBar,progressBarfooter,webprogressBar;

    String url = Session.SERVER_URL+"feeds-new3.php?";
    String url_appednd;
    String url_appednd_search="";

    ViewFlipper viewFlipper;
    int temp=0;
    int t=0;
    DatabaseHandler db;
    LinearLayout new_feeds_btn_ll;
    TextView new_feeds_btn;
    SwipeRefreshLayout swipeRefreshLayout;
    MyTextView cancel_tv,no_source,add_source,pop_up_label;
    MyBoldTextView label;
    LinearLayout pop_up_layout,add_source_ll;
    LinearLayout no_news_layout;
    HaamAdapter categoryAdapter;
    NewsRecycleListActivity mCallBack;
    FragmentManager fragmentManager;
    FrameLayout container;
    ImageView search_btn,settings_btn;
    LinearLayoutManager linearLayoutManager;

     String title;
     int page=0;
    String tab_id = MainActivity.world_id;
    int visibleItemCount,totalItemCount,firstVisibleItemIndex;
    private static int firstVisibleInListview;


    int getPreLast;

    boolean loading;
    boolean no_image;



    final Handler h = new Handler();
    final int delay = 1000*60;//milliseconds

    final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.e("time","ticked");

                if(news.size()>0)
                   get_latest_news("0");
        }
    };


    final Handler hs = new Handler();
    final int delays = 1000*8;//milliseconds

    final Runnable rs = new Runnable() {
        @Override
        public void run() {
            new_feeds_btn_ll.setVisibility(View.GONE);
        }
    };

    final Handler h_ch = new Handler();
    final int delay_ch = 1000*300;//milliseconds

    final Runnable r_ch = new Runnable() {
        @Override
        public void run() {
            get_ch(databaseHandler.all_selected_channels_new("0"));
        }
    };

    CustomRecylerView mRecyclerView;

    ImageView world_news,economy_news,sports_news,live_news,setings_news;
    com.mamacgroup.hamtest.MyBoldTextView  world_news_tv,economy_news_tv,sports_news_tv,live_news_tv,setings_news_tv;
    MenuClickListner menuClickListner;
    LinearLayout webview_layout;
    LinearLayout webview_footer_holder;

    WebView webview;
    boolean get_next_news;
    TextView bassket_ball_txt,foot_ball_txt,head_login,fb_tv,tw_tv,goo_tv,login_tv,skip_tv;
    LinearLayout basket_ball_btn,foot_ball_btn;
    LinearLayout header_tab;
    LinearLayout dim_bg;
    LinearLayout footer;
    LinearLayout fb,goo,tw,login,skip,login_popup;
    TwitterAuthClient mTwitterAuthClient;
//    GoogleApiClient  mGoogleApiClient;
    int RC_SIGN_IN=5;
    String emaill,id,login_name,login_type,ph;
    private CallbackManager callbackManager;
    DatabaseHandler databaseHandler;
    String login_check="1";
    int open_count=0;
    RecyclerView haam_recyclerview;
    MyTextView more_btn,stories_btn;
    LinearLayout stories_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Session.forceRTLIfSupported(this);
        FacebookSdk.sdkInitialize(this);
        //AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.newsrecyclelistfragment);
        databaseHandler = new DatabaseHandler(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
//        TwitterAuthConfig authConfi=new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));
        mTwitterAuthClient= new TwitterAuthClient();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("185349456914-ohpelq9n09vm1dtiv78lc4hub9og2v7j.apps.googleusercontent.com")
//                .requestEmail()
//                .requestId()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mInstance = this;
        get_next_news = false;
        footer = (LinearLayout)findViewById(R.id.footer);
        dim_bg = (LinearLayout) findViewById(R.id.dim_bg);
        webprogressBar = (LinearLayout) findViewById(R.id.webprogressBar);

        login_popup=(LinearLayout)findViewById(R.id.login_popup_ll);
        fb=(LinearLayout)findViewById(R.id.pop_fb_ll);
        goo=(LinearLayout)findViewById(R.id.pop_goo_ll);
        tw=(LinearLayout)findViewById(R.id.pop_tw_ll);
        login=(LinearLayout)findViewById(R.id.pop_login_ll);
        skip=(LinearLayout)findViewById(R.id.pop_skip_ll);

        head_login=(TextView)findViewById(R.id.login_pop_header);
        head_login.setText(Session.getword(this,"popup_display_text"));
        fb_tv=(TextView)findViewById(R.id.pop_fb_tv);
        fb_tv.setText(Session.getword(this,"fb_login"));
        tw_tv=(TextView)findViewById(R.id.pop_tw_tv);
        tw_tv.setText(Session.getword(this,"tw_login"));
        goo_tv=(TextView)findViewById(R.id.pop_goo_tv);
        goo_tv.setText(Session.getword(this,"google_login"));
        login_tv=(TextView)findViewById(R.id.pop_login_tv);
        login_tv.setText(Session.getword(this,"login_to_restore"));
        skip_tv=(TextView)findViewById(R.id.pop_skip_tv);
        skip_tv.setText(Session.getword(this,"skip"));
        
        header_tab = (LinearLayout) findViewById(R.id.header_tab);

        search_btn = (ImageView) findViewById(R.id.search_btn);
        settings_btn = (ImageView) findViewById(R.id.set_btn);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setttings_btn_clicked();
            }
        });

        bassket_ball_txt = (TextView)findViewById(R.id.basket_ball_txt) ;
        foot_ball_txt = (TextView)findViewById(R.id.foot_ball_txt) ;

        basket_ball_btn = (LinearLayout) findViewById(R.id.basket_ball_btn) ;
        foot_ball_btn = (LinearLayout)findViewById(R.id.foot_ball_btn) ;


        basket_ball_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // progressBar.setVisibility(View.VISIBLE);
                webview.clearHistory();
                webview.clearCache(true);
                webview.clearView();

                webview.loadUrl("http://naqsh.co/table_basket.html");
                bassket_ball_txt.setTextColor(Color.parseColor("black"));
                foot_ball_txt.setTextColor(Color.parseColor("white"));

            }
        });

        foot_ball_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   progressBar.setVisibility(View.VISIBLE);
                webview.clearHistory();
                webview.clearCache(true);
                webview.clearView();
                webview.loadUrl("http://naqsh.co/table.html");
                bassket_ball_txt.setTextColor(Color.parseColor("white"));
                foot_ball_txt.setTextColor(Color.parseColor("black"));

            }
        });
        // webview.setWebViewClient(new WebViewClient());
        if(getIntent().hasExtra("login_check")) {
            login_check=getIntent().getStringExtra("login_check");
        }
        if(Session.get_user_id(this).equals("-1") && login_check.equals("1")){
            login_popup.setVisibility(View.GONE);
        }else{
            login_popup.setVisibility(View.GONE);
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_popup.setVisibility(View.GONE);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(NewsRecycleListActivity.this,LoginActivity.class);
                startActivity(intent);
                login_popup.setVisibility(View.GONE);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbLogin(view);
            }
        });

        goo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTwitterAuthClient.authorize(NewsRecycleListActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        name =twitterSessionResult.data.getUserName();
                        Log.d("twittercommunity", name);
                        id=String.valueOf(twitterSessionResult.data.getUserName());
                        emaill = "";
                        ph = "";
                        type = "twitter";
                        String img="https://twitter.com/"+name+"/profile_image?size=original";
                        Log.d("twitter_url", img);
                        Session.set_user_img(NewsRecycleListActivity.this,img);
                        login(emaill, type, name, id, ph);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });

            }
        });

        bassket_ball_txt.setText(Session.getword(this,"BasketBall"));
        foot_ball_txt.setText(Session.getword(this,"FootBall"));

        fragmentManager = getSupportFragmentManager();



        webview_layout = (LinearLayout) findViewById(R.id.webview_layout);
        webview_footer_holder = (LinearLayout) findViewById(R.id.webview_footer_holder);

        webview_layout.setVisibility(View.GONE);
        webview_footer_holder.setVisibility(View.GONE);
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                    if(progress>70){
                        webprogressBar.setVisibility(View.GONE);
                    }else{
                        webprogressBar.setVisibility(View.VISIBLE);
                    }

            }
        });




        world_news = (ImageView) findViewById(R.id.world_news_img);
        economy_news = (ImageView) findViewById(R.id.economy_news_img);
        sports_news = (ImageView) findViewById(R.id.sports_news_img);
        live_news = (ImageView) findViewById(R.id.live_news_img);
        setings_news = (ImageView) findViewById(R.id.settings_img);

        world_news_tv = (com.mamacgroup.hamtest.MyBoldTextView) findViewById(R.id.world_news_tv);
        economy_news_tv = (com.mamacgroup.hamtest.MyBoldTextView) findViewById(R.id.economy_news_tv);
        sports_news_tv = (com.mamacgroup.hamtest.MyBoldTextView) findViewById(R.id.sports_news_tv);
        live_news_tv = (com.mamacgroup.hamtest.MyBoldTextView) findViewById(R.id.live_news_tv);
        setings_news_tv = (com.mamacgroup.hamtest.MyBoldTextView) findViewById(R.id.settings_news_tv);

        world_news_tv.setText(Session.getword(this,"tab_news"));
        economy_news_tv.setText(Session.getword(this,"title_economy"));
        sports_news_tv.setText(Session.getword(this,"title_sports"));
        live_news_tv.setText(Session.getword(this,"title_tv_live"));
        setings_news_tv.setText(Session.getword(this,"tab_settings"));

        menuClickListner = new MenuClickListner();

        world_news.setOnClickListener(menuClickListner);
        economy_news.setOnClickListener(menuClickListner);
        sports_news.setOnClickListener(menuClickListner);
        live_news.setOnClickListener(menuClickListner);
        setings_news.setOnClickListener(menuClickListner);

        world_news_tv.setOnClickListener(menuClickListner);
        economy_news_tv.setOnClickListener(menuClickListner);
        sports_news_tv.setOnClickListener(menuClickListner);
        live_news_tv.setOnClickListener(menuClickListner);
        setings_news_tv.setOnClickListener(menuClickListner);


        world_news.setImageResource(R.drawable.global);
        world_news.setColorFilter(ContextCompat.getColor(this, R.color.aa_menu_text_selected));
        world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        customs_in_header = (LinearLayout) findViewById(R.id.custom_header_layout);

        stories_layout = (LinearLayout) findViewById(R.id.stories_layout);

        display_custom(get_arraylist(0));

        news = new ArrayList<>();
        news_modle = new ArrayList<>();
        latest_news = new ArrayList<>();
        chanels = new ArrayList<>();


        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);


        new_feeds_btn_ll = (LinearLayout) findViewById(R.id.new_feeds_btn_ll);
        new_feeds_btn = (TextView) findViewById(R.id.new_feeds_btn);
        new_feeds_btn_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //listView.setSelection(0);
                mRecyclerView.smoothScrollToPosition(0);
                new_feeds_btn_ll.setVisibility(View.GONE);


            }
        });

        no_news_layout = (LinearLayout) findViewById(R.id.no_news);

        //listView=(ListView)view.findViewById(R.id.listView);
       //listView.setScrollingCacheEnabled(false);
        //listView.setAnimationCacheEnabled(false);
        //listView.setSmoothScrollbarEnabled(true);


        adapter = new MultiViewTypeActAdapter(news,this,no_image,this);
        youtube_categories = new ArrayList<>();
        youtube_chanels = new ArrayList<>();

        youtube_adapter = new MultiViewTypeAdapterCategory(youtube_categories,this,this);
        youtubeChannelsAdapter = new YoutubeChannelsAdapter(this,youtube_chanels,this);
        channel_adapter = new MultiViewTypeAdapterChannels(chanels,NewsRecycleListActivity.this,this);

       // adapter.setHasStableIds(true);

        linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        mRecyclerView = (CustomRecylerView) findViewById(recyclerView);
        haam_recyclerview = (RecyclerView) findViewById(ham_cat_recyclerview);



        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        haam_recyclerview.setLayoutManager(layoutManager);

        more_btn = (MyTextView) findViewById(R.id.more_stories_btn);
        more_btn.setText(Session.getword(this,"more stories"));
        stories_btn = (MyTextView) findViewById(R.id.stories);
        stories_btn.setText(Session.getword(this,"stories"));
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewsRecycleListActivity.this,CategoryActivity.class);
                intent.putExtra("feed_id", "0");
                intent.putExtra("login_check","1");
                startActivity(intent);
            }
        });



        // mRecyclerView.setItemViewCacheSize(100);
        //mRecyclerView.setDrawingCacheEnabled(true);
        //mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        firstVisibleInListview = linearLayoutManager.findFirstVisibleItemPosition();

        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setAdapter(adapter);

        get_next_news = true;


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {
                super.onScrollStateChanged(view,scrollState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.i("scrolled amount: ", String.valueOf(dy));
                Log.i("header_height: ", String.valueOf(header_height));
                Log.i("header position: ", String.valueOf(isBig));

                if(dy>50){
                    Log.i("RecyclerView scrolled: ", "scroll up!");
                    Log.i("scrolled amount: ", String.valueOf(dy));

                    if(isBig)
                        animate(header_tab);

                    stories_layout.setVisibility(View.GONE);


                }else if(dy<-50){
                    Log.i("RecyclerView scrolled: ", "scroll down!");
                    Log.i("scrolled amount: ", String.valueOf(dy));

                    if(!isBig)
                       animate(header_tab);

                    if(selected==0 && page==0){

                        stories_layout.setVisibility(View.VISIBLE);
                        isBigrecycle=true;
                    }else{
                        Log.e("open","header");

                        stories_layout.setVisibility(View.GONE);
                        isBigrecycle=false;

                    }



                }


                if(get_next_news) {

                    //swipeRefreshLayout.setEnabled(true);

                    if (page != 2) {


                        visibleItemCount = recyclerView.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        firstVisibleItemIndex = linearLayoutManager.findFirstVisibleItemPosition();


                      //  Log.e("visibleItemCount",String.valueOf(visibleItemCount));
                        //Log.e("totalcount",String.valueOf(totalItemCount));
                        //Log.e("firstvisibleItemIndex",String.valueOf(firstVisibleItemIndex));

                        //synchronizew loading state when item count changes
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                    }
//                }

                        if (((totalItemCount) - visibleItemCount) <= firstVisibleItemIndex + 10) {
                            // Loading NOT in progress and end of list has been reached
                            // also triggered if not enough items to fill the screen
                            // if you start loading


                                Log.e("reaxhed", "climax");

                                if (!last_loaded_id.equals(news.get(news.size() - 1).id)) {

                                    if (selected == 2 && page == 0)
                                        get_video_news();
                                    else
                                        get_news(url_appednd, false, false);
                                }







                        } else if (firstVisibleItemIndex == 0) {

                            // top of list reached
                            // if you start loading

                          //  if(!isBig)
                             //   animate(header_tab);


                        }
                    } else {
                        swipeRefreshLayout.setEnabled(false);


                    }


                }else {
                    swipeRefreshLayout.setEnabled(false);
                }

        }}
        );


        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_progress, null, false);
        progressBarfooter = (LinearLayout) footerView.findViewById(R.id.progressBarfooter);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);
        label = (MyBoldTextView) findViewById(R.id.lable);
        label.setText(Session.getword(this,"tab_news"));
        no_source = (MyTextView) findViewById(R.id.no_source);
        no_source.setText(Session.getword(this,"error_no_channels_subscribed"));
        add_source = (MyTextView) findViewById(R.id.add_source_tv);
        add_source.setText(Session.getword(this,"title_add_source"));
        add_source_ll = (LinearLayout) findViewById(R.id.add_source_ll);
        add_source_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                catselected(tab_id);
            }
        });

        progressBar = (LinearLayout) findViewById(R.id.progressBar);


        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        swipeRefreshLayout.setRefreshing(true);

                        if(get_next_news) {

                            no_news_layout.setVisibility(View.GONE);
                            last_loaded_id="";

                            if(selected==2 && page==0){
                                get_video_news();
                            }else {
                                get_news(url_appednd, true, true);
                            }

                            swipeRefreshLayout.setRefreshing(false);

                        }
                        else
                            swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


      //  listView.setAdapter(news_list_adapter);


        db = new DatabaseHandler(this);


        if(db.selected_channels(tab_id).equals("0")) {

//            Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
            //viewFlipper.setDisplayedChild(1);

            no_news_layout.setVisibility(View.VISIBLE);
            add_source_ll.setVisibility(View.VISIBLE);
            no_source.setText(Session.getword(this, "error_no_channels_subscribed"));


        }
        else {

            //label.setText(Session.getword(getActivity(),"latest"));
           // viewFlipper.setDisplayedChild(0);
            no_news_layout.setVisibility(View.GONE);
            get_news("channels=" + db.selected_channels(tab_id),true,true);
        }


        boolean pauseOnScroll = false; // or true
        boolean pauseOnFling = true; // or false



      //  listView.setOnScrollListener(listener);

        //listView.addFooterView(footerView);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(news.get(position).type.equals("news")){
//                    mCallBack.newsclicked(news.get(position));
//                }else{
//                    if (news.get(position).video.equals("") && news.get(position).mp4.equals("")) {
//                        Intent intent = new Intent(getActivity(), WebviewActivity.class);
//                        intent.putExtra("link", news.get(position).link);
//                        startActivity(intent);
//                    }else{
//                        if(!news.get(position).mp4.equals("")){
//                            Intent intent = new Intent(getActivity(), AndroidVideoPlayerActivity.class);
//                            intent.putExtra("video", news.get(position).mp4);
//                            startActivity(intent);
//                        }else{
//                            Intent intent = new Intent(getActivity(), YoutubePlayer.class);
//                            intent.putExtra("video", news.get(position).video);
//                            startActivity(intent);
//                        }
//                    }
//                }
//
//            }
//        });

//        if(page!=1)
//        set_refresh_timer();




        if(!Session.will_resume(this)){

            Log.e("resume","will not resume");

            feed_id = Session.get_news_feed_id(this);

            if(!feed_id.equals("0"))
                type = "news";

            if(getIntent().hasExtra("feed_id")) {

                // Session.set_news_feed_id(this, getIntent().getStringExtra("feed_id"));
                feed_id = getIntent().getStringExtra("feed_id");
                getIntent().removeExtra("feed_id");

            }

            if(getIntent().hasExtra("name")) {

                // Session.set_news_feed_id(this, getIntent().getStringExtra("feed_id"));
                name = getIntent().getStringExtra("name");
                getIntent().removeExtra("name");



            }





            if ( !feed_id.equals("0")) {


                if(getIntent().hasExtra("type"))
                    type = getIntent().getStringExtra("type");



                if(type.equals("news")) {
                    NewsDetailFragment D_fragmnet = new NewsDetailFragment();
                    Bundle args = new Bundle();
                    args.putString("feed_id", feed_id);
                    args.putString("parent", "0");
//            args.putString("parent", "0");
                    D_fragmnet.setArguments(args);
                    Session.set_news_feed_id(this, "0");
                    viewFlipper.setDisplayedChild(1);
                    fragmentManager.popBackStackImmediate();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, D_fragmnet)
                            .commit();
                }else if(type.equals("channel")){

                    chanel_selected_id(feed_id);

                }else if(type.equals("tv")){

//                    Intent intent = new Intent(this, JWPlayerViewExample.class);
//                    intent.putExtra("jw_url", feed_id);
//                    intent.putExtra("name", name);
//                    startActivity(intent);


                }

            }

        }else{
            Log.e("resume","will resume");
        }



      //  chanel_selected_id("260");

       // Intent intent = new Intent(this, JWPlayerViewExample.class);
        //intent.putExtra("jw_url", feed_id);
        //intent.putExtra("name", feed_id);
        //startActivity(intent);

        set_refresh_timer();


        header_tab.post(new Runnable() {
            @Override
            public void run() {
                Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                header_height = header_tab.getHeight();
                 //height is ready
            }
        });

        Log.e("on_create","called_here");
        if(Session.is_first_get(this).equals("0")){
            if(databaseHandler.all_selected_channels_new("0").equals("")) {
                add_source_ll.performClick();
            }
            login_popup.setVisibility(View.GONE);
            Session.is_first_set(this,"1");
        }
        if(!Session.get_user_id(NewsRecycleListActivity.this).equals("-1")) {
            get_ch(databaseHandler.all_selected_channels_new("0"));
        }



//        if(isBigrecycle)
//            animate_reycle(stories_layout);

        stories_layout.setVisibility(View.GONE);
        isBigrecycle = false;


        get_category("0");





        get_youtube_channels(false);

    }

    String feed_id="0";
    String name="chanel_from_notification";
    String type = "welcome";

    int header_height = 0;
    int recycle_height = 0;
    int header_temp_height = 0;


    WorldFragment worldFragment;

    private void set_refresh_timer(){

            h.removeCallbacks(r);
            AppController.getInstance().getRequestQueue().cancelAll("latest");
            h.postDelayed(r, delay);
            new_feeds_btn_ll.setVisibility(View.GONE);
    }
    private void get_ch(final String list){
        String url;
//        final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
//        progressDialog.setMessage(Session.getword(SplashActivity.this,"please_wait"));
//        progressDialog.show();
//        progressDialog.setCancelable(false);

        // progressBar.setVisibility(View.VISIBLE);

            url = Session.NOTIFY_SERVER_URL + "channels2.php?member_id="+Session.get_user_id(NewsRecycleListActivity.this)+
                    "&push_channels="+databaseHandler.notification_enabled_chanels();
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
//                if(progressDialog!=null)
//                    progressDialog.dismiss();

                //  progressBar.setVisibility(View.GONE);
                // chanels =new ArrayList<>();
                ArrayList<String> server_ids = new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Chanel chanel=new Chanel(jsonObject,"0");
                        server_ids.add(chanel.ch_id);
                        if(!databaseHandler.is_following(chanel.ch_id)){
                            databaseHandler.addPlaylist(chanel.ch_id,chanel.parent_id,chanel.status);
                        }else{
                            databaseHandler.updatenotify(chanel.ch_id,chanel.status);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                String[] jsonArray1 = list.split(",");
                for(int j=0;j<jsonArray1.length;j++) {
                    if(!server_ids.contains(jsonArray1[j])){
                        databaseHandler.deletePlaylist(jsonArray1[j]);
                    }

                }


                h_ch.postDelayed(r_ch,delay_ch);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                if(progressDialog!=null)
//                    progressDialog.dismiss();

                //  progressBar.setVisibility(View.GONE);
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
    public void fbLogin(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
//        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.e("fbresult", loginResult.toString());
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginInitialActivity", object.toString());

                                        // Application code
                                        try {
                                            if (object.has("picture")) {
                                                String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                                Log.d("twitter_url", profilePicUrl);
                                                Session.set_user_img(NewsRecycleListActivity.this,profilePicUrl);
                                            }else{
                                                Session.set_user_img(NewsRecycleListActivity.this,"-1");
                                            }
                                            emaill = object.getString("email");
                                            id = object.getString("id");
                                            name = object.getString("name");
                                            ph = "";
                                            type = "facebook";
                                            login(emaill, type, name, id, ph);
                                            Log.e("facebook", emaill);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
//                                String birthday = object.getString("birthday"); // 01/31/1980 format
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("exception",exception.toString());
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                        // App code
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.e("googlee_result", String.valueOf(result.getStatus().getStatusCode()));
////            if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            // Log.e("googlee_succ",result.getStatus().getStatusMessage());
//            GoogleSignInAccount acct = result.getSignInAccount();
//            login_name =acct.getDisplayName();
//            id=acct.getId();
//            emaill = acct.getEmail();;
//            ph = "";
//            login_type = "gplus";
//            Uri uri=acct.getPhotoUrl();;
//            String stringUri=uri.toString();
//            Log.d("google_img_url", stringUri);
//            Session.set_user_img(NewsRecycleListActivity.this,stringUri);
//            login(emaill, login_type, login_name, id, ph);
////            }else{
////
////            }
//        }else{
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
//        }


    }
    public  void login(String email,String type,String name,String id,String ph){
        String url = null;
        try {
            url = Session.NOTIFY_SERVER_URL+"login-api.php?email="+ URLEncoder.encode(email, "utf-8")+
                    "&type="+ URLEncoder.encode(type, "utf-8")+ "&name="+ URLEncoder.encode(name, "utf-8")+
                    "&user_id="+ URLEncoder.encode(id, "utf-8")+ "&phone="+ URLEncoder.encode(ph, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(NewsRecycleListActivity.this, "please_wait"));
//        progressDialog.setMessage(Settings.getword(this, "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(NewsRecycleListActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String msg = jsonObject.getString("name");
                        Session.set_user_id(NewsRecycleListActivity.this, mem_id,msg);
                        Toast.makeText(NewsRecycleListActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        Session.sendChannelsToServer(NewsRecycleListActivity.this);
                        get_chanels_login(databaseHandler.all_selected_channels_new("0"));
//                        Intent intent = new Intent(NewsRecycleListActivity.this, NewsRecycleListActivity.class);
//                        startActivity(intent);
//                        if(temp.equals("1")){
//                            onBackPressed();
//                        }else {
//                            Intent intent = new Intent(NewsRecycleListActivity.this, ViewpagerActivity.class);
//                            ActivityOptions options = ActivityOptions.makeCustomAnimation(NewsRecycleListActivity.this, R.anim.enter_from_left, R.anim.exit_to_right);
//                            startActivity(intent, options.toBundle());
//                            finish();
//                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(NewsRecycleListActivity.this, Session.getword(NewsRecycleListActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }


    private void get_chanels_login(String list){
        String url;
        final ProgressDialog progressDialog = new ProgressDialog(NewsRecycleListActivity.this);
        progressDialog.setMessage(Session.getword(NewsRecycleListActivity.this,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);

        // progressBar.setVisibility(View.VISIBLE);
        if(Session.get_user_id(NewsRecycleListActivity.this).equals("-1")) {
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list;
        }else{
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list+"&member_id="+Session.get_user_id(NewsRecycleListActivity.this);
        }
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

                //  progressBar.setVisibility(View.GONE);
                // chanels =new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Chanel chanel=new Chanel(jsonObject,"0");
                        if(!databaseHandler.is_following(chanel.ch_id)){
                            databaseHandler.addPlaylist(chanel.ch_id,chanel.parent_id,"1");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    login_popup.setVisibility(View.GONE);
                    Intent intent = new Intent(NewsRecycleListActivity.this, NewsRecycleListActivity.class);
                    intent.putExtra("feed_id", "0");
                    intent.putExtra("login_check", "0");
                    startActivity(intent);
                    finish();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog!=null)
                    progressDialog.dismiss();

                //  progressBar.setVisibility(View.GONE);
                Log.e("error", volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
    String temp_url = "";


    private void get_latest_news(final String value) {

        try {

            temp_url = url + url_appednd + "&first_id=" + news.get(0).id;



            if(page==3)
                temp_url = temp_url+"&type=urgent";
            else if(page==1  && selected==0)
                temp_url = temp_url+"&country="+AppController.getInstance().country;


            Log.e("url", temp_url);
            // final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setMessage("please_wait");
            //progressDialog.show();
            //progressDialog.setCancelable(false);
//        temp_url=temp_url
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.e("response", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/
                    if(value.equals("0"))
                        h.postDelayed(r,delay);

                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    latest_news.clear();

                    if(jsonArray.length()==0){
                        //Toast.makeText(getActivity(),"There is no new feeds at this moment",Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("json", jsonObject.toString());
                            latest_news.add(new News(jsonObject,NewsRecycleListActivity.this));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("lates_news",String.valueOf(latest_news.size()));

                        if(latest_news.size()>0){

                            if(value.equals("0")) {

                                new_feeds_btn.setText(latest_news.size()+ " "+ Session.getword(NewsRecycleListActivity.this,"scroll_up_message"));
                                t=1;
                                new_feeds_btn_ll.setVisibility(View.VISIBLE);
                                hs.postDelayed(rs,delays);

                                for(int j=latest_news.size()-1;j>=0;j--){

                                    try {
                                        news.add(0,latest_news.get(j));
                                    }catch (Exception ex){
                                        break;
                                    }
                                }

                              //  int t1=listView.getMaxScrollAmount();
                             //   int x=listView.getScrollX();
                             //   int scroll_posi=t1-x;
                                adapter.notifyDataSetChanged();
                              //  listView.smoothScrollToPosition(0);
                             //   listView.setScrollX(listView.getMaxScrollAmount()-scroll_posi);
                                swipeRefreshLayout.setRefreshing(false);


                            }else {
                                for(int j=latest_news.size()-1;j>=0;j--){

                                    try {
                                        news.add(0,latest_news.get(j));
                                    }catch (Exception ex){
                                        break;
                                    }

                                }
                                adapter.notifyDataSetChanged();
                            //    listView.smoothScrollToPosition(0);
                                swipeRefreshLayout.setRefreshing(false);
                                set_refresh_timer();
                            }



                        }

                        //news_list_adapter.notifyDataSetChanged();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                    progressBar.setVisibility(View.GONE);
                    Log.e("error", volleyError.toString());

                    //  Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();

                }
            });

            AppController.getInstance().addToRequestQueue(jsonArrayRequest,"latest");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void get_news(final String url_append, final boolean clear_data, final boolean show_progress){


        get_next_news = true;

        //mRecyclerView.setLayoutManager(linearLayoutManager);

        if(show_progress) {

            progressBar.setVisibility(View.VISIBLE);
            progressBarfooter.setVisibility(View.GONE);
            mRecyclerView.setAdapter(adapter);
            visibleItemCount =0;
            totalItemCount =0;
            firstVisibleItemIndex=0;
            preLast=0;
            get_next_news=true;

        }
        else {
            progressBar.setVisibility(View.GONE);
            progressBarfooter.setVisibility(View.VISIBLE);
        }

        String lastid = "";

        if(clear_data) {
            news.clear();
            latest_news.clear();
            adapter.notifyDataSetChanged();

        }else {

        }

        if(news.size()==0){
            if(url_append.equals("")) {
                temp_url = url + url_append + url_appednd_search;
            }
            else {
               // temp_url = url + url_append + "&" + url_appednd_search;
                temp_url = url + url_append ;

            }

        }else{
            if(url_append.equals("")) {
                temp_url = url + url_append + url_appednd_search + "last_id=" + news.get(news.size()-1).id ;
                last_loaded_id = news.get(news.size()-1).id;
            }
            else {
                temp_url = url + url_append + "&" + url_appednd_search + "last_id=" + news.get(news.size()-1).id ;
                last_loaded_id = news.get(news.size()-1).id;
            }

        }

       if(page==1 && selected==0){
           temp_url = temp_url+"&country="+AppController.getInstance().country;
        }
        else if(page==3)
        temp_url = temp_url+"&type=urgent";
        else if(page==2)
            temp_url = temp_url+"&type=most_view";


        Log.e("url", temp_url);

        url_appednd = url_append;
        // final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setMessage("please_wait");
        //progressDialog.show();
        //progressDialog.setCancelable(false);
//        temp_url=temp_url
        final  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response_news", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/                  progressBar.setVisibility(View.GONE);
                progressBarfooter.setVisibility(View.GONE);



                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        news.add(new News(jsonObject,NewsRecycleListActivity.this));




                    } catch (JSONException e) {
                       // adapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }



                }


                if(news.size() == 0 ) {
                   // viewFlipper.setDisplayedChild(1);
                    no_news_layout.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(NewsRecycleListActivity.this, "no_feeds"));
                    add_source_ll.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"no feeds to display",Toast.LENGTH_SHORT).show();
                }else{
                   // viewFlipper.setDisplayedChild(0);


                    no_news_layout.setVisibility(View.GONE);

                    Log.e("pos",String.valueOf(firstVisibleItemIndex));

                    adapter.notifyDataSetChanged();
                    Log.e("new","data_aded");

                   // mRecyclerView.getLayoutManager().scrollToPosition(firstVisibleItemIndex);
                }
                    //listView.setSelection(start);

                if(jsonArray.length()==0)
                    adapter.end_of_news = true;
                else
                    adapter.end_of_news = false;

                adapter.notifyItemChanged(adapter.getItemCount()-1);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                try{
                    progressBarfooter.setVisibility(View.GONE);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

                if(volleyError.toString().equals("com.android.volley.TimeoutError")){

                    get_news(url_append, clear_data, show_progress);

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    progressBarfooter.setVisibility(View.GONE);
                }
                Log.e("error", volleyError.toString());
//                Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }




ArrayList<YoutubeCategories> youtube_categories;
    ArrayList<VideoChanel> youtube_chanels;




    private void get_youtube_channels(final boolean setadapter){

        String url = "http://news.haamapp.com/apiv2/yt-full.php";
        Log.e("url", url);
        youtube_categories.clear();
        youtube_chanels.clear();
        youtube_adapter.notifyDataSetChanged();

        final  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("youtube_response", jsonArray.toString());



                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        YoutubeCategories youtubeCategories = new YoutubeCategories(jsonObject);
                        youtube_categories.add(youtubeCategories);
                        for(int j=0;j<youtubeCategories.chanels.size();j++){
                            youtube_chanels.add(youtubeCategories.chanels.get(j));
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }


                if(setadapter)
                youtube_adapter.notifyDataSetChanged();

                Log.e("youtube",String.valueOf(youtube_categories.size()));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("error", volleyError.toString());

            }
        });


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


    }




    private void get_video_news(){


            get_next_news = true;
            progressBar.setVisibility(View.VISIBLE);
            progressBarfooter.setVisibility(View.GONE);
            mRecyclerView.setAdapter(adapter);
            visibleItemCount =0;
            totalItemCount =0;
            firstVisibleItemIndex=0;
            preLast=0;
            get_next_news=true;






            news.clear();
            latest_news.clear();
            adapter.notifyDataSetChanged();












        temp_url= "http://news.haamapp.com/apiv2/yt-videos.php";

        final  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/                  progressBar.setVisibility(View.GONE);
                progressBarfooter.setVisibility(View.GONE);



                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        news.add(new News(jsonObject,NewsRecycleListActivity.this,"video"));




                    } catch (JSONException e) {
                        // adapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }



                }


                if(news.size() == 0 ) {
                    // viewFlipper.setDisplayedChild(1);
                    no_news_layout.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(NewsRecycleListActivity.this, "no_feeds"));
                    add_source_ll.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"no feeds to display",Toast.LENGTH_SHORT).show();
                }else{
                    // viewFlipper.setDisplayedChild(0);


                    no_news_layout.setVisibility(View.GONE);

                    Log.e("pos",String.valueOf(firstVisibleItemIndex));

                    adapter.notifyDataSetChanged();
                    Log.e("new","data_aded");

                    // mRecyclerView.getLayoutManager().scrollToPosition(firstVisibleItemIndex);
                }
                //listView.setSelection(start);

                if(jsonArray.length()==0)
                    adapter.end_of_news = true;
                else
                    adapter.end_of_news = false;

                adapter.notifyItemChanged(adapter.getItemCount()-1);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                try{
                    progressBarfooter.setVisibility(View.GONE);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

                if(volleyError.toString().equals("com.android.volley.TimeoutError")){

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    progressBarfooter.setVisibility(View.GONE);
                }
                Log.e("error", volleyError.toString());
//                Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }


    private int preLast;

    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;
    private boolean mIsScrollingUppre = false;


    String last_loaded_id="";
    int selected=0;
    int preselected = 0;

   // @Override

    public void chanel_selected(Chanel chanel) {
        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel",chanel);
        main_fragmnet.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }


    public void chanels_selected(String chanels) {
        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putString("chanels",chanels);
        main_fragmnet.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }




    @Override
    public void ihave_completed_hide_footer() {
        viewFlipper.setDisplayedChild(1);
        footer.setVisibility(View.GONE);
    }

    public void chanel_selected_id(String chanel_id) {
        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel_id",chanel_id);
        main_fragmnet.setArguments(args);
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet)
                .commit();

    }


    public void chanel_selected_clear(Chanel chanel) {

        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel",chanel);
        main_fragmnet.setArguments(args);
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet)
                .commit();

    }


    @Override
    public void ihave_completed() {
        viewFlipper.setDisplayedChild(1);
        footer.setVisibility(View.VISIBLE);
    }

    @Override
    public void refresh_news() {

        try{

            if(adapter!=null)
                adapter.notifyDataSetChanged();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void search() {
       // viewFlipper.setDisplayedChild(1);
        Fragment main_fragmnet = new SearchFragment();
        Bundle arguments = new Bundle();
        arguments.putString("tab_id",tab_id);
        main_fragmnet.setArguments(arguments);
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet)
                .commit();
    }


    @Override
    public void to_video_frg(VideoChanel videoChanel) {
        Fragment main_fragmnet = new VideoCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel",videoChanel);
        main_fragmnet.setArguments(args);
        viewFlipper.setDisplayedChild(1);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();


    }

    @Override
    public void back() {

            footer.setVisibility(View.VISIBLE);
            onBackPressed();

    }

    @Override
    public void refresh_lst_back() {


try {
    if (subCategories != null) {
        if (subCategories.categories != null)
            subCategories.categoryAdapter.notifyDataSetChanged();
    }
}catch (Exception ex){
    ex.printStackTrace();
}


    }

    @Override
    public void tohome() {

    }

    @Override
    public void goto_setting_activity(String no, String id) {
        Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        mainIntent.putExtra("no", no);
        mainIntent.putExtra("id", id);
        startActivity(mainIntent);

    }

    @Override
    public void to_slidingActivity(ArrayList<String> images) {
        Intent intent=new Intent(NewsRecycleListActivity.this,SlidingActivity.class);
        intent.putStringArrayListExtra("images", images);
        startActivity(intent);

    }

    @Override
    public void subcatselected(Chanel chanel,String cat_id,String parent_name) {
        viewFlipper.setDisplayedChild(1);
        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel",chanel);
        args.putString("parent_id", cat_id);
        args.putString("parent_name",parent_name);
        main_fragmnet.setArguments(args);
        viewFlipper.setDisplayedChild(1);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }
    @Override
    public void onBackPressed() {

        if(webview_layout.getVisibility() != View.VISIBLE) {

            if (fragmentManager.getBackStackEntryCount() == 0 && viewFlipper.getDisplayedChild() != 0) {

               // fragmentManager.popBackStackImmediate();
               // if(selected == 3)
                   // moveTaskToBack(true);
                //setselected(String.valueOf(preselected));
                  if(selected == 4)
                    setselected(String.valueOf(preselected));                //else
                    viewFlipper.setDisplayedChild(0);
                    footer.setVisibility(View.VISIBLE);

            }

            else if (viewFlipper.getDisplayedChild() == 0 && fragmentManager.getBackStackEntryCount() == 0)
            {
                fragmentManager.popBackStackImmediate();
                moveTaskToBack(true);
            }

            else
                try {
                   // fragmentManager.popBackStack();
                    super.onBackPressed();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        }else{
            super.onBackPressed();
        }
    }


    @Override
    public void catselected(Categories categories) {

         subCategories = new SubCategories();
        Bundle args = new Bundle();
        args.putSerializable("categorie", categories);
        subCategories.setArguments(args);
        viewFlipper.setDisplayedChild(1);
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, subCategories).addToBackStack(null)
                .commit();

    }

    SubCategories subCategories;

    public void catselected(String id) {


        if(id.equals(MainActivity.world_id)){
            source();
        }else {
            subCategories = new SubCategories();
            Bundle args = new Bundle();
            args.putSerializable("id", id);
            subCategories.setArguments(args);
            viewFlipper.setDisplayedChild(1);
            fragmentManager.beginTransaction()
                    .add(R.id.content_frame, subCategories).addToBackStack(null)
                    .commit();
        }
    }

    public void source() {

        setselected("4");
        setings_news.setImageResource(R.drawable.settings3_selected);
        setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        preselected=selected;
        selected=4;
        Fragment main_fragmnet = new MySourcesFragment();
        viewFlipper.setDisplayedChild(1);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }


    public void youtube_chanels_fragment(ArrayList<VideoChanel> videoChanels) {

        setselected("2");
        //setings_news.setImageResource(R.drawable.settings3_selected);
        //setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
        //setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        preselected=selected;
        selected=2;
        Fragment main_fragmnet = new YoutubeChanelsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chanels", videoChanels);
        main_fragmnet.setArguments(bundle);
        viewFlipper.setDisplayedChild(1);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }



    @Override
    public void notify_sources() {

        setselected("4");
        setings_news.setImageResource(R.drawable.settings3_selected);
        setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        preselected=selected;
        selected=4;
        Fragment main_fragmnet = new NotifySourcesFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void recreateActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void hideProgress() {
        if(progressBar!=null)
            progressBar.setVisibility(View.GONE);
    }

    //@Override

    public void newsclicked(News news) {

        Fragment main_fragmnet = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString("parent", String.valueOf(selected));
        args.putParcelable("news", news);
        args.putString("feed_id", "-1");
        main_fragmnet.setArguments(args);
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet)
                .commit();
    }

    @Override
    public void newsclickednofinish(News news) {

        Fragment main_fragmnet = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString("parent", String.valueOf(selected));
        args.putParcelable("news", news);
        args.putString("feed_id", "-1");
        main_fragmnet.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void livetvselected(LiveChannels liveChannels) {

    }

    @Override
    public void setselected(String index) {
        int id = Integer.parseInt(index);
        Log.e("backkk", index);
        switch (id){
            case 0:
            {
                if(selected!=0){
                    reset_selected();
                    world_news.setImageResource(R.drawable.global);
                    world_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=0;
                }
            }
            break;
            case 1:
            {
                if(selected!=1){
                    reset_selected();
                    economy_news.setImageResource(R.drawable.list);
                    economy_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));

                    selected=1;

                }
            }
            break;
            case 3:
            {
                if(selected!=3){
                    reset_selected();
                    sports_news.setImageResource(R.drawable.football);
                    sports_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=3;

                }
            }
            break;

            case 2:
            {
                if(selected!=2){
                    reset_selected();
                    live_news.setImageResource(R.drawable.play);
                    live_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=2;

                }
            }
            break;

            case 4:
            {
                if(selected!=4){
                    reset_selected();
                    setings_news.setImageResource(R.drawable.user);
                    setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=4;

                }
            }
            break;

        }

    }

    @Override
    public void present(String index) {

    }

    @Override
    public Animation get_animation(Boolean enter, Boolean loaded) {

        return null;
    }

    @Override
    public void setttings_btn_clicked() {
        Fragment main_fragmnet = new AccountFragment();
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet)
                .commit();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class MenuClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();

            mRecyclerView.setLayoutManager(linearLayoutManager);

            if(!isBig)
            animate(header_tab);

            stories_layout.setVisibility(View.GONE);

            footer.setVisibility(View.VISIBLE);

            switch (id){
                case R.id.world_news_tv:
                case R.id.world_news_img:
                {
                    viewFlipper.setDisplayedChild(0);
                    if(selected!=0){
                        reset_selected();
                        label.setText(Session.getword(NewsRecycleListActivity.this,"tab_news"));
                        fragmentManager.popBackStackImmediate();
                        world_news.setImageResource(R.drawable.global);
                        world_news.setColorFilter(ContextCompat.getColor(NewsRecycleListActivity.this,R.color.aa_menu_text_selected));
                        world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=0;
                        send_screen_name();
                        tab_id=MainActivity.world_id;
                        display_custom(get_arraylist(selected));
                        webview.clearHistory();
                        webview.clearCache(true);

                        webview.clearView();
                        webview_layout.setVisibility(View.GONE);

                        if(db.selected_channels(tab_id).equals("0")) {
                            no_news_layout.setVisibility(View.VISIBLE);
                            add_source_ll.setVisibility(View.VISIBLE);
                            no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
                        }
                        else {
                            no_news_layout.setVisibility(View.GONE);
                            last_loaded_id="";
                            get_news("channels=" + db.selected_channels(tab_id),true,true);
                        }
                    }else{
                        mRecyclerView.scrollToPosition(0);
                    }
                }
                break;
                case R.id.economy_news_tv:
                case R.id.economy_news_img:
                {
                    viewFlipper.setDisplayedChild(1);
                    if(selected!=1){
                        preselected=selected;
                        reset_selected();
                        economy_news.setImageResource(R.drawable.list);
                        economy_news.setColorFilter(ContextCompat.getColor(NewsRecycleListActivity.this,R.color.aa_menu_text_selected));
                        economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=1;
                        send_screen_name();

                        Fragment account_fragment = new SettingsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 0);
                        account_fragment.setArguments(bundle);
                        viewFlipper.setDisplayedChild(1);
                        fragmentManager.popBackStackImmediate();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, account_fragment)
                                .commit();
                    }
                }
                break;
                case R.id.sports_news_tv:
                case R.id.sports_news_img:
                {
                    viewFlipper.setDisplayedChild(0);
                    if(selected!=3){
                        label.setText(Session.getword(NewsRecycleListActivity.this,"title_sports"));

                        reset_selected();
                        fragmentManager.popBackStackImmediate();
                        sports_news.setImageResource(R.drawable.football);
                        sports_news.setColorFilter(ContextCompat.getColor(NewsRecycleListActivity.this,R.color.aa_menu_text_selected));
                        sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=3;
                        send_screen_name();
                        tab_id=MainActivity.sports_id;
                        display_custom(get_arraylist(selected));
                        webview.clearHistory();
                        webview.clearCache(true);
                        webview.clearView();
                        webview.loadUrl("http://naqsh.co/table.html");
                        webview_layout.setVisibility(View.GONE);


                        if(db.selected_channels(tab_id).equals("0")) {
                            no_news_layout.setVisibility(View.VISIBLE);
                            add_source_ll.setVisibility(View.VISIBLE);
                            no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
                        }
                        else {
                            no_news_layout.setVisibility(View.GONE);
                            last_loaded_id="";
                            get_news("channels=" + db.selected_channels(tab_id),true,true);
                        }
                    }else{
                        mRecyclerView.scrollToPosition(0);
                    }
                }
                break;
                case R.id.live_news_tv:
                case R.id.live_news_img:
                {
                    viewFlipper.setDisplayedChild(0);
                    if(selected!=2){
                        reset_selected();
                        live_news.setImageResource(R.drawable.play);
                        live_news.setColorFilter(ContextCompat.getColor(NewsRecycleListActivity.this,R.color.aa_menu_text_selected));
                        live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=2;
                        tab_id=MainActivity.economy_id;
                        display_custom(get_arraylist(selected));
                        webview.clearHistory();
                        webview.clearCache(true);
                        webview.clearView();
                        webview.loadUrl("http://naqsh.co/agenda/agenda.html");
                        webview_layout.setVisibility(View.GONE);

//                        if(db.selected_channels(tab_id).equals("0")) {
//                            no_news_layout.setVisibility(View.VISIBLE);
//                            add_source_ll.setVisibility(View.VISIBLE);
//                            no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
//                        }
//                        else {

                        no_news_layout.setVisibility(View.GONE);
                        last_loaded_id="";
                        //get_news("channels=" + db.selected_channels(tab_id),true,true);
                        get_video_news();
                        // }

                }
                }
                break;
                case R.id.settings_news_tv:
                case R.id.settings_img:
                {
                    viewFlipper.setDisplayedChild(1);
                    if(selected!=4){
                        preselected=selected;
                        reset_selected();
                        setings_news.setImageResource(R.drawable.user);
                        setings_news.setColorFilter(ContextCompat.getColor(NewsRecycleListActivity.this,R.color.aa_menu_text_selected));
                        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=4;
                        send_screen_name();

                        Fragment account_fragment = new AccountFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 0);
                        account_fragment.setArguments(bundle);
                        viewFlipper.setDisplayedChild(1);
                        fragmentManager.popBackStackImmediate();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, account_fragment)
                                .commit();


                    }
                }
                break;

            }



        }

    }

    private void reset_selected(){

        world_news.setImageResource(R.drawable.global_inactive);
        world_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        economy_news.setImageResource(R.drawable.list_inactive);
        economy_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        sports_news.setImageResource(R.drawable.football_inactive);
        sports_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        live_news.setImageResource(R.drawable.paly_inactive);
        live_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        setings_news.setImageResource(R.drawable.user_inactive);
        setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));


    }

    ArrayList<MyBoldTextView> custom_names;
    LinearLayout customs_in_header;

    public ArrayList<String> get_arraylist(int page){

        ArrayList<String> tab_names = new ArrayList<>();
      //  tab_names.add(Session.getword(this,"source"));
        if(page==0){
            tab_names.add(Session.getword(this,"latest"));
            tab_names.add(Session.getword(this,"local"));
            tab_names.add(Session.getword(this,"urgent"));
        }else if(page==3){
            tab_names.add(Session.getword(this,"latest"));
            tab_names.add(Session.getword(this,"matches"));
        }else if(page==2){
            tab_names.add(Session.getword(this,"latest"));
            tab_names.add(Session.getword(this,"sources"));
            tab_names.add(Session.getword(this,"selected_sources"));
        }
        else{
            tab_names.clear();
        }

        return  tab_names;
    }


    public void display_custom(final ArrayList<String> jsonArray) {

        custom_names = new ArrayList<>();
        customs_in_header.removeAllViewsInLayout();
        customs_in_header.setVisibility(View.VISIBLE);


        for (int i=0;i<jsonArray.size();i++){
            final  MyBoldTextView temp = new MyBoldTextView(this);
            try {
                temp.setText(jsonArray.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            temp.setLayoutParams(params);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER);
            temp.setTextSize(15);
            temp.setTextColor(getResources().getColor(R.color.aa_app_blue));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);

            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;

            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setEnabled(true);

                    if(!isBig)
                        animate(header_tab);


                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(getResources().getColor(R.color.aa_app_blue));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                    }

                    custom_names.get(finalI).setBackgroundResource(R.drawable.border_full_appcolor);
                    custom_names.get(finalI).setTextColor(Color.parseColor("white"));
                    Log.e("select_tab",String.valueOf(finalI2));
                    page=finalI;

                        if(finalI==1 && selected==0){

//                            webview_layout.setVisibility(View.GONE);
//                            if(db.selected_channels(tab_id).equals("0")) {
//
////            Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
//                                //viewFlipper.setDisplayedChild(1);
//                                no_news_layout.setVisibility(View.VISIBLE);
//                                add_source_ll.setVisibility(View.VISIBLE);
//                                no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
//                            }
//                            else {
//
//                                //label.setText(Session.getword(getActivity(),"latest"));
//                                // viewFlipper.setDisplayedChild(0);
//
//                                no_news_layout.setVisibility(View.GONE);
//                                get_chanels("channels=" + db.selected_channels(tab_id));
//                            }



//                            if(db.selected_channels(tab_id).equals("0")) {
//                                no_news_layout.setVisibility(View.VISIBLE);
//                                add_source_ll.setVisibility(View.VISIBLE);
//                                no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
//                            }
//                            else {
                                webview_layout.setVisibility(View.GONE);
                                no_news_layout.setVisibility(View.GONE);
                                get_news("",true,true);

                            //}
                        }else  if(finalI2 == 2 && selected==1) {
                          //  webview.loadDataWithBaseURL(null,"<html>...</html>", "text/html", "utf-8", null);
                            webview.clearHistory();
                            webview.clearCache(true);

                            webview.clearView();
                            webview.loadUrl("http://naqsh.co/agenda/agenda.html");
                            foot_ball_btn.setVisibility(View.GONE);
                            basket_ball_btn.setVisibility(View.GONE);
                            webview_footer_holder.setVisibility(View.GONE);

                            webview_layout.setVisibility(View.VISIBLE);
                            webprogressBar.setVisibility(View.VISIBLE);


                           // foot_ball_btn.performClick();

                        }else if(finalI2 == 1 && selected==2){

                            mRecyclerView.setLayoutManager(new GridLayoutManager(NewsRecycleListActivity.this,2));
                            mRecyclerView.setAdapter(youtube_adapter);
                            youtube_adapter.notifyDataSetChanged();

                            if(youtube_chanels.size()==0)
                                get_youtube_channels(true);

                            swipeRefreshLayout.setEnabled(false);

                        }

                        else if(finalI2 == 2 && selected==2){

                            mRecyclerView.setLayoutManager(new GridLayoutManager(NewsRecycleListActivity.this,3));

                            mRecyclerView.setAdapter(youtubeChannelsAdapter);
                            youtubeChannelsAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setEnabled(false);

                        }
                        else if(finalI2 == 0 && selected==2){

                            webview_layout.setVisibility(View.GONE);
                            no_news_layout.setVisibility(View.GONE);
                            get_video_news();

                        }
                            else {
                            webview_layout.setVisibility(View.GONE);
                            if(db.selected_channels(tab_id).equals("0")) {
                                no_news_layout.setVisibility(View.VISIBLE);
                                add_source_ll.setVisibility(View.VISIBLE);
                                no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
                            }
                            else {
                                no_news_layout.setVisibility(View.GONE);
                                get_news("channels=" + db.selected_channels(tab_id),true,true);
                            }

                        }

                    set_refresh_timer();

                    if(selected==0 && page==0){

                        stories_layout.setVisibility(View.VISIBLE);

                                            }else{
                        Log.e("open","header");
                        stories_layout.setVisibility(View.GONE);
                                            }

                }
            });

            custom_names.add(temp);

        }
        for (int j = 0; j < jsonArray.size(); j++) {
            custom_names.get(j).setTextColor(getResources().getColor(R.color.aa_app_blue));
            custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
        }
        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(0).setTextColor(Color.parseColor("white"));

        for(int r=custom_names.size()-1;r>=0;r--)
            customs_in_header.addView(custom_names.get(r));

        page=0;

        if(selected==0 && page==0){

            stories_layout.setVisibility(View.VISIBLE);

        }else{
            Log.e("open","header");
            stories_layout.setVisibility(View.GONE);
        }

    }



    private void get_chanels(String list) {


        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
       */

        progressBar.setVisibility(View.VISIBLE);
        String url = Session.NOTIFY_SERVER_URL+"channels2.php?"+list;
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                chanels =new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        chanels.add(new Chanel(jsonObject,"0"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                news.clear();
                latest_news.clear();
                adapter.notifyDataSetChanged();



                channel_adapter = new MultiViewTypeAdapterChannels(chanels,NewsRecycleListActivity.this,NewsRecycleListActivity.this);
                mRecyclerView.setAdapter(channel_adapter);
                get_next_news=false;

                if(chanels.size()==0){
//                    Toast.makeText(getActivity(),"You have not selected any sources",Toast.LENGTH_SHORT).show();
                    no_news_layout.setVisibility(View.VISIBLE);
                    add_source_ll.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(NewsRecycleListActivity.this, "error_no_channels_subscribed"));
//                    for (int j = 0; j < custom_names.size(); j++) {
//                        custom_names.get(j).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
//                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
//                    }
//                  //  custom_names.get(pre_selected).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
//                   // custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_empty_appcolor);
//
//                    custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_full_appcolor);
//                    custom_names.get(pre_selected).setTextColor(Color.parseColor("white"));
//                    selected=pre_selected;

                }else{
                    Log.e("error","have channels");
                    no_news_layout.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }


        public void single_channel_news(String channel_id){

            get_news("channels="+channel_id,true,true);

    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        try {
            AppController.getInstance().cancelPendingRequests();
            Session.set_minimizetime(this);

        }catch (Exception ex){

        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        try {

                Session.get_minimizetime(this);
                data(js);
            categoryAdapter.notifyDataSetChanged();

            Log.e("on_resume","called_here");

            send_screen_name();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        mInstance = null;
    }

    static NewsRecycleListActivity mInstance;

    public static NewsRecycleListActivity getInstance() {
        return mInstance;
    }




    private String get_screen_name(){

        switch (selected){
            case 0:
                return "General";
            case 1:
                return "Economy";
            case 2:
                return "Sports";
            case 3:
                return "LiveTvList";
            case 4:
                return "Sources";
            default:
                return "Home";
        }
    }


    private void send_screen_name(){

        try {

//            AppController.getInstance().getDefaultTracker().setScreenName(get_screen_name());
//            AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
            Log.e("screen_name",get_screen_name());

//            AppController.getInstance().kTracker.event(get_screen_name(),get_screen_name());
//
//            Answers.getInstance().logCustom(new CustomEvent(get_screen_name()));

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    boolean isBig=true;
    boolean isBigrecycle=true;

//    public void animate_reycle(final View v) {
//
//        if(!isBigrecycle){
//
////            if(recycle_height==0){
////                stories_layout.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        Log.e("re_ht",String.valueOf(stories_layout.getHeight()));
////                        recycle_height = stories_layout.getHeight();
////                        //height is ready
////                    }
////                });
////
////            }else{
////                ValueAnimator va = ValueAnimator.ofInt(0, recycle_height);
////                va.setDuration(100);
////                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
////                    public void onAnimationUpdate(ValueAnimator animation) {
////                        Integer value = (Integer) animation.getAnimatedValue();
////                        v.getLayoutParams().height = value.intValue();
////                        v.requestLayout();
////                    }
////                });
////                va.addListener(new Animator.AnimatorListener() {
////                    @Override
////                    public void onAnimationStart(Animator animator) {
////
////                    }
////
////                    @Override
////                    public void onAnimationEnd(Animator animator) {
////                        isBigrecycle = true;
////                        haam_recyclerview.getAdapter().notifyDataSetChanged();
////                    }
////
////                    @Override
////                    public void onAnimationCancel(Animator animator) {
////
////                    }
////
////                    @Override
////                    public void onAnimationRepeat(Animator animator) {
////
////                    }
////                });
////                va.start();
////                isBigrecycle = true;
//
//
//            stories_layout.setVisibility(View.GONE);
//            isBigrecycle = false;
//
//
//            }
//
//
//        else{
//
////            if(recycle_height==0){
////                stories_layout.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        Log.e("re_ht",String.valueOf(stories_layout.getHeight()));
////                        recycle_height = stories_layout.getHeight();
////                        //height is ready
////                    }
////                });
////
////            }else {
////
////
////                ValueAnimator va = ValueAnimator.ofInt(recycle_height, 0);
////                va.setDuration(100);
////                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
////                    public void onAnimationUpdate(ValueAnimator animation) {
////                        Integer value = (Integer) animation.getAnimatedValue();
////                        v.getLayoutParams().height = value.intValue();
////                        v.requestLayout();
////                    }
////                });
////
////                va.addListener(new Animator.AnimatorListener() {
////                    @Override
////                    public void onAnimationStart(Animator animator) {
////
////                    }
////
////                    @Override
////                    public void onAnimationEnd(Animator animator) {
////                        isBigrecycle = false;
////                    }
////
////                    @Override
////                    public void onAnimationCancel(Animator animator) {
////
////                    }
////
////                    @Override
////                    public void onAnimationRepeat(Animator animator) {
////
////                    }
////                });
////                va.start();
////                isBigrecycle = false;
//
//            stories_layout.setVisibility(View.VISIBLE);
//            isBigrecycle =true;
//
//
//
//
//        }
//
//    }



    public void animate(final View v) {
        if(!isBig){

            if(header_height==0){
                header_tab.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                        header_height = header_tab.getHeight();
                        //height is ready
                    }
                });

            }else{
                ValueAnimator va = ValueAnimator.ofInt(0, header_height);
                va.setDuration(100);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        v.getLayoutParams().height = value.intValue();
                        v.requestLayout();
                    }
                });
                va.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isBig = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                va.start();
                isBig = true;

            }




        }
        else{

            if(header_height==0){
                header_tab.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                        header_height = header_tab.getHeight();
                        //height is ready
                    }
                });

            }else {


                ValueAnimator va = ValueAnimator.ofInt(header_height, 0);
                va.setDuration(100);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        v.getLayoutParams().height = value.intValue();
                        v.requestLayout();
                    }
                });

                va.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isBig = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                va.start();
                isBig = false;
            }

        }


    }






    private static final int ID_FB     = 1;
    private static final int ID_TW   = 2;
    private static final int ID_WP = 3;
    private static final int ID_INS   = 4;
    private static final int ID_MSG  = 5;
    private static final int ID_MAI     = 6;




     void show_new_popup(View v, final News news){

        ActionItem nextItem 	= new ActionItem(ID_FB, "", getResources().getDrawable(R.drawable.facebook_square));
        ActionItem prevItem 	= new ActionItem(ID_TW, "", getResources().getDrawable(R.drawable.twitter_square));
        ActionItem searchItem 	= new ActionItem(ID_WP, "", getResources().getDrawable(R.drawable.whats_up_square));
        ActionItem infoItem 	= new ActionItem(ID_INS, "", getResources().getDrawable(R.drawable.instagram_ic));
        ActionItem eraseItem 	= new ActionItem(ID_MSG, "", getResources().getDrawable(R.drawable.messaging_icon));
        ActionItem okItem 		= new ActionItem(ID_MAI, "", getResources().getDrawable(R.drawable.mail_icon_square));

        //use setSticky(true) to disable QuickAction dialog being dismissed after an item is clicked
       // prevItem.setSticky(true);
        //nextItem.setSticky(true);

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(this, QuickAction.HORIZONTAL);

        //add action items into QuickAction
        quickAction.addActionItem(nextItem);
        quickAction.addActionItem(prevItem);
        quickAction.addActionItem(searchItem);
        quickAction.addActionItem(infoItem);
        quickAction.addActionItem(eraseItem);
        quickAction.addActionItem(okItem);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                ActionItem actionItem = quickAction.getActionItem(pos);

                //here we can filter which action item was clicked with pos or actionId parameter
                if (actionId == ID_FB) {
                    share_fb(news);
                } else if (actionId == ID_TW) {
                    share_tw(news);
                } else if (actionId == ID_WP){
                    share_wp(news);
                }else if (actionId == ID_INS) {
                        share_ins(news);
                } else if (actionId == ID_MSG) {
                        share_msg(news);
                } else if (actionId == ID_MAI){
                        share_mail(news);
                }
            }
        });

        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
        //by clicking the area outside the dialog.
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
               // Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
                dim_bg.setVisibility(View.GONE);
            }
        });

         quickAction.show(v);
         dim_bg.setVisibility(View.VISIBLE);
    }




    private void share_fb(News news){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        //     i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
        //     i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
        i.putExtra(Intent.EXTRA_TEXT, html2text(news.facebook_str));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.facebook.katana");
        try {
            startActivity(i);
        } catch (Exception e) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.facebook.katana")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.facebook.katana")));
            }
        }

    }
    private void share_tw(News news){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        //     i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
        //     i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
        i.putExtra(Intent.EXTRA_TEXT, html2text(news.twitter_str));

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.twitter.android");
        try {
            startActivity(i);
        } catch (Exception e) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.twitter.android")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.twitter.android")));
            }
        }


    }private void share_wp(News news){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
        i.putExtra(Intent.EXTRA_TEXT, html2text(news.whatsapp_str));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.whatsapp");
        try {
            startActivity(i);
        } catch (Exception e) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.whatsapp")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.whatsapp")));
            }
        }



    }private void share_ins(final News news){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(this,"please_wait"));
        progressDialog.show();

        // progressDialog.setCancelable(false);

        Picasso.with(this).load(news.insta_img).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                if(progressDialog!=null)
                    progressDialog.dismiss();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
                i.putExtra(Intent.EXTRA_TEXT, html2text(news.link));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.instagram.android");
                try {
                    startActivity(i);
                } catch (Exception e) {

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
                    }
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if(progressDialog!=null)
                    progressDialog.dismiss();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

//                            if(progressDialog!=null)
//                                progressDialog.dismiss();

            }
        });
    }



    private void share_msg(News news){
        String smsBody=html2text(news.whatsapp_str);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);


    }private void share_mail(News news){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
        i.putExtra(Intent.EXTRA_TEXT, html2text(news.mail_str));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.google.android.gm");
        try {
            startActivity(i);
        } catch (Exception e) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.android.gm")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.gm")));
            }
        }


    }



    public static String html2text(String html) {
        // return Jsoup.parse(html).text();
        return html;
    }



    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }



    ArrayList<String> categories_id;
    ArrayList<String> categories_names;
    ArrayList<Category> categories=new ArrayList<>();

    JSONArray js=new JSONArray();
    public void get_category(String te) {
        categories_id = new ArrayList<String>();
        categories_names = new ArrayList<String>();
        String url;
        url = "http://haamapp.com/api/category_new.php";
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
//                progressDialog.dismiss();position
//                swipeRefreshLayout.setRefreshing(false);
                for (int i = 0; i < jsonObject.length(); i++) {
                    Log.e("sizeeee", String.valueOf(jsonObject.length()));
                    JSONObject sub = null;
                    try {
                        sub = jsonObject.getJSONObject(i);
//                        Category cate = new Category(sub);
//                        categories.add(cate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.e("response is: ", jsonObject.toString());
                js=jsonObject;
                data(jsonObject);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    public void  data(JSONArray jsonArray){
        categories.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("sizeeee", String.valueOf(jsonArray.length()));
                JSONObject sub = jsonArray.getJSONObject(i);
                Category cate = new Category(sub);
                categories.add(cate);
                categories_id.add(sub.getString("id"));
                categories_names.add(sub.getString("title_ar"));

            }
            categoryAdapter   = new HaamAdapter(this,categories_id,categories_names,categories_names,categories);
            haam_recyclerview.setAdapter(categoryAdapter);

            stories_layout.setVisibility(View.VISIBLE);

//            if(!isBigrecycle)
//                animate_reycle(stories_layout);

            categoryAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}


