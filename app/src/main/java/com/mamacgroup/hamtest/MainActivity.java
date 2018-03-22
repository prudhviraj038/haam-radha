package com.mamacgroup.hamtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
//import com.google.android.gms.analytics.HitBuilders;
//import com.labo.kaji.fragmentanimations.MoveAnimation;



import java.util.ArrayList;

public class MainActivity extends BaseActivity implements
        WorldFragment.FragmentTouchListner,
        SettingsFragment.FragmentTouchListner,
        LiveChannelsFragment.FragmentTouchListner,
        EconomyFragment.FragmentTouchListner,
        SportsFragment.FragmentTouchListner,
        SubCategories.FragmentTouchListner,
        NewsDetailFragmentDemo.FragmentTouchListner,
        LiveTvFragment.FragmentTouchListner,
        LiveDetailFragment.FragmentTouchListner,
        SubCateDetailpage.FragmentTouchListner,
        AccountFragment.FragmentTouchListner,
        SearchFragment.FragmentTouchListner,
        NewsDetailFragment.FragmentTouchListner,
        NotifySourcesFragment.FragmentTouchListner {


    static String world_id="2";
    static String economy_id="29";
    static String sports_id="4";
    static String live_id="live";

    static String world_id_str="world";
    static String economy_id_str="economy";
    static String sports_id_str="sports";


    static final String DEVELOPER_KEY = "AIzaSyDpQ6VmRaiN728aG7TyXrGewgOoisuLJZg";

    ImageView world_news,economy_news,sports_news,live_news,setings_news;
    com.mamacgroup.hamtest.MyTextView  world_news_tv,economy_news_tv,sports_news_tv,live_news_tv,setings_news_tv;
    MenuClickListner menuClickListner;
    int selected=0;
    int preselected = 0;
    String feed_id="0";
    FragmentManager fragmentManager;
    FrameLayout container;
    News news;

    static MainActivity mInstance;

    public static MainActivity getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
       // FacebookSdk.sdkInitialize(this);
        //AppEventsLogger.activateApp(this);

        mInstance = this;

        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        feed_id=getIntent().getStringExtra("feed_id");
        container = (FrameLayout)findViewById(R.id.content_frame);

        world_news = (ImageView) findViewById(R.id.world_news_img);
        economy_news = (ImageView) findViewById(R.id.economy_news_img);
        sports_news = (ImageView) findViewById(R.id.sports_news_img);
        live_news = (ImageView) findViewById(R.id.live_news_img);
        setings_news = (ImageView) findViewById(R.id.settings_img);

        world_news_tv = (com.mamacgroup.hamtest.MyTextView) findViewById(R.id.world_news_tv);
        economy_news_tv = (com.mamacgroup.hamtest.MyTextView) findViewById(R.id.economy_news_tv);
        sports_news_tv = (com.mamacgroup.hamtest.MyTextView) findViewById(R.id.sports_news_tv);
        live_news_tv = (com.mamacgroup.hamtest.MyTextView) findViewById(R.id.live_news_tv);
        setings_news_tv = (com.mamacgroup.hamtest.MyTextView) findViewById(R.id.settings_news_tv);

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

        world_news.setImageResource(R.drawable.news_selected);
        world_news.setColorFilter(ContextCompat.getColor(this, R.color.aa_menu_text_selected));
        world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));

        Fragment main_fragmnet = new WorldFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        main_fragmnet.setArguments(bundle);

        animation_direction = true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet)
                .commit();

        if(!feed_id.equals("0")) {
            NewsDetailFragment D_fragmnet = new NewsDetailFragment();
            animation_direction = true;
            Bundle args = new Bundle();
            args.putString("feed_id", feed_id);
            args.putString("parent", "0");
//            args.putString("parent", "0");
            D_fragmnet.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, D_fragmnet)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void reset_selected(){

        world_news.setImageResource(R.drawable.news);
        world_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        economy_news.setImageResource(R.drawable.dollar);
        economy_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        sports_news.setImageResource(R.drawable.sport);
        sports_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        live_news.setImageResource(R.drawable.live_tv_unse);
        live_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));

        setings_news.setImageResource(R.drawable.settings3);
        setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text));
        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text));


    }
    @Override
    public  void  to_slidingActivity(ArrayList<String> images){
        Intent intent=new Intent(MainActivity.this,SlidingActivity.class);
        intent.putStringArrayListExtra("images", images);
        startActivity(intent);

    }
    @Override
    public  void goto_setting_activity(String  no,String id){
        Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        mainIntent.putExtra("no", no);
        mainIntent.putExtra("id", id);
        startActivity(mainIntent);
    }
    @Override
    public void newsclicked() {
        Fragment main_fragmnet = new NewsDetailFragmentDemo();
        Bundle args = new Bundle();
        args.putString("parent", String.valueOf(selected));
        main_fragmnet.setArguments(args);
        animation_direction=true;
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
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
        animation_direction=true;
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();
    }

    @Override
    public void back() {
        animation_direction=false;
        onBackPressed();
    }

    @Override
    public void refresh_lst_back() {

    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()==0)
             moveTaskToBack(true);

        else
        try {
            fragmentManager.popBackStack();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void tohome() {
        animation_direction=false;
        Fragment main_fragmnet = new WorldFragment();
        animation_direction = true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet)
                .commit();
    }

    @Override
    public void source() {
        animation_direction=false;
        setselected("4");
        setings_news.setImageResource(R.drawable.settings3_selected);
        setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        preselected=selected;
        selected=4;
        Fragment main_fragmnet = new SettingsFragment();
        animation_direction=true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void notify_sources() {
        animation_direction=false;
        setselected("4");
        setings_news.setImageResource(R.drawable.settings3_selected);
        setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        preselected=selected;
        selected=4;
        Fragment main_fragmnet = new NotifySourcesFragment();
        animation_direction=true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void search() {
//        reset_selected();
        animation_direction=false;
        Fragment main_fragmnet = new SearchFragment();
        animation_direction=true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();
    }

    @Override
    public void subcatselected(Chanel chanel,String cat_id,String parent_name) {

        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel",chanel);
        args.putString("parent_id", cat_id);
        args.putString("parent_name",parent_name);
        main_fragmnet.setArguments(args);
        animation_direction=true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void chanel_selected(Chanel chanel) {

        Fragment main_fragmnet = new SubCateDetailpage();
        Bundle args = new Bundle();
        args.putSerializable("chanel",chanel);
        main_fragmnet.setArguments(args);
        animation_direction=true;
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void ihave_completed_hide_footer() {

    }

    @Override
    public void ihave_completed() {

    }

    @Override
    public void refresh_news() {

    }


    @Override
    public void catselected(Categories categories) {

        Fragment main_fragmnet = new SubCategories();
        Bundle args = new Bundle();
        args.putSerializable("categorie",categories);
        main_fragmnet.setArguments(args);
        animation_direction=true;
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void catselected(String id) {


        if(id.equals(MainActivity.world_id)){
            source();
        }else {
            Fragment main_fragmnet = new SubCategories();
            Bundle args = new Bundle();
            args.putSerializable("id", id);
            main_fragmnet.setArguments(args);
            animation_direction = true;
            fragmentManager.beginTransaction()
                    .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                    .commit();
        }
    }




    @Override
    public void livetvselected(LiveChannels liveChannels) {
        Fragment main_fragmnet = new LiveDetailFragment();
        Bundle args = new Bundle();
        args.putString("link",liveChannels.link);
        main_fragmnet.setArguments(args);
        animation_direction=true;
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();

    }

    @Override
    public void present(String index) {
       /* Log.e("back", index);
        reset_selected();
        if(index.equals("0")){
            world_news.setImageResource(R.drawable.news_selected);
            world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        }else if(index.equals("1")){
            economy_news.setImageResource(R.drawable.dollar_selected);
            economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        }else if(index.equals("2")){
            sports_news.setImageResource(R.drawable.sport_selected);
            sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        }else if(index.equals("3")){
            live_news.setImageResource(R.drawable.live_tv);
            live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        }else{
            setings_news.setImageResource(R.drawable.settings3_selected);
            setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
        }

*/

    }

    @Override
    public void newsclicked(News news) {

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
                    world_news.setImageResource(R.drawable.news_selected);
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
                    economy_news.setImageResource(R.drawable.dollar_selected);
                    economy_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));

                    selected=1;

                }
            }
            break;
            case 2:
            {
                if(selected!=2){
                    reset_selected();
                    sports_news.setImageResource(R.drawable.sport_selected);
                    sports_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=2;

                }
            }
            break;

            case 3:
            {
                if(selected!=3){
                    reset_selected();
                    live_news.setImageResource(R.drawable.live_tv);
                    live_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=3;

                }
            }
            break;

            case 4:
            {
                if(selected!=4){
                    reset_selected();
                    setings_news.setImageResource(R.drawable.settings3_selected);
                    setings_news.setColorFilter(ContextCompat.getColor(this,R.color.aa_menu_text_selected));
                    setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                    selected=4;

                }
            }
            break;

        }


    }
    public  static final long DURATION=1000;
    boolean animation_direction=true;

    @Override
    public Animation get_animation(Boolean enter, Boolean loaded) {
//            if(animation_direction)
//                return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION);
//            else
//                return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);

        return null;

    }

    @Override
    public void setttings_btn_clicked() {
       // setings_news.performClick();

        Fragment main_fragmnet = new AccountFragment();
        animation_direction=true;
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, main_fragmnet).addToBackStack(null)
                .commit();



    }



    private class MenuClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id){
                case R.id.world_news_tv:
                case R.id.world_news_img:
                {
                    if(selected!=0){
                        reset_selected();
                        world_news.setImageResource(R.drawable.news_selected);
                        world_news.setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.aa_menu_text_selected));
                        world_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=0;
                        Fragment main_fragmnet = new WorldFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", selected);
                        main_fragmnet.setArguments(bundle);
                        animation_direction=true;
                        fragmentManager.popBackStackImmediate();
                        fragmentManager.beginTransaction()
                                .add(R.id.content_frame, main_fragmnet)
                                .commit();

                    }
                }
                break;
                case R.id.economy_news_tv:
                case R.id.economy_news_img:
                {
                    if(selected!=1){
                        reset_selected();
                        economy_news.setImageResource(R.drawable.dollar_selected);
                        economy_news.setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.aa_menu_text_selected));
                        economy_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));

                        selected=1;
                        Fragment main_fragmnet = new WorldFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", selected);
                        main_fragmnet.setArguments(bundle);
                        animation_direction=true;
                        fragmentManager.popBackStackImmediate();
                        fragmentManager.beginTransaction()
                                .add(R.id.content_frame, main_fragmnet)
                                .commit();


                    }
                }
                break;
                case R.id.sports_news_tv:
                case R.id.sports_news_img:
                {
                    if(selected!=2){
                        reset_selected();
                        sports_news.setImageResource(R.drawable.sport_selected);
                        sports_news.setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.aa_menu_text_selected));
                        sports_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));

                        selected=2;
                        Fragment main_fragmnet = new WorldFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", selected);
                        main_fragmnet.setArguments(bundle);

                        animation_direction=true;
                        fragmentManager.popBackStackImmediate();

                        fragmentManager.beginTransaction()
                                .add(R.id.content_frame, main_fragmnet)
                                .commit();

                    }
                }
                break;
                case R.id.live_news_tv:
                case R.id.live_news_img:
                {
                    if(selected!=3){
                        reset_selected();
                        live_news.setImageResource(R.drawable.live_tv);
                        live_news.setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.aa_menu_text_selected));
                        live_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=3;
                        Fragment main_fragmnet = new LiveTvFragment();
                        animation_direction=true;
                        fragmentManager.popBackStackImmediate();
                        fragmentManager.beginTransaction()
                                .add(R.id.content_frame, main_fragmnet)
                                .commit();

                    }
                }
                break;
                case R.id.settings_news_tv:
                case R.id.settings_img:
                {
                    if(selected!=4){
                        preselected=selected;
                        reset_selected();
                        setings_news.setImageResource(R.drawable.settings3_selected);
                        setings_news.setColorFilter(ContextCompat.getColor(MainActivity.this,R.color.aa_menu_text_selected));
                        setings_news_tv.setTextColor(getResources().getColor(R.color.aa_menu_text_selected));
                        selected=4;
                        Fragment main_fragmnet = new SettingsFragment();
                        animation_direction=true;
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, main_fragmnet).addToBackStack(null)
                                .commit();
                    }
                }
                break;

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
//            AppController.getInstance().getDefaultTracker().setScreenName("HomePage");
//            AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());


        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        mInstance = null;
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

    }


}
