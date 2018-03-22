package com.mamacgroup.hamtest.HaamMerge;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Chinni on 04-05-2016.
 */
public class CompanySlidingActivity extends AppCompatActivity implements CompanyListFragment.FragmentTouchListner {
    PagerSlidingTabStrip pagerSlidingTabStrip;
    ViewPager viewPager;
    int cat=0;
    Map<Integer,CompanyListFragment> frags;
    MyPagerAdapter adapter;
    ArrayList<Category> categories = new ArrayList<>();
    ImageView setting,back,search,fav_img;
    TextView tittle;
    MyTextView pd_tv;
    LinearLayout pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.haam_company_sliding_fragment);
        tittle = (TextView) findViewById(R.id.tittle);
        tittle.setText("الكل");
        setting=(ImageView)findViewById(R.id.slid_setting);
        search=(ImageView)findViewById(R.id.fav_search);

        back=(ImageView)findViewById(R.id.back_cs);
        pd=(LinearLayout)findViewById(R.id.progressBar_ll_sliding);
        pd_tv=(MyTextView)findViewById(R.id.pd_tv__slid);
        pd_tv.setText(Settings.getword(this,"Loading"));
        get_category();
//        categories = (ArrayList<Category>)getIntent().getSerializableExtra("categories");
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.view3);
       // pagerSlidingTabStrip.setTabMode(TabLayout.MODE_SCROLLABLE);
        //pagerSlidingTabStrip.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        //pagerSlidingTabStrip.setSelectedTabIndicatorHeight(7);
        //pagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#A71C21"));
        //pagerSlidingTabStrip.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#039cd5"));

        viewPager = (ViewPager) findViewById(R.id.view4);

        pagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    tittle.setText("الكل");
                else
                    tittle.setText(categories.get(position - 1).title);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fav_img=(ImageView)findViewById(R.id.fav_img_arc);
        fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanySlidingActivity.this, WishListActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        viewPager.setCurrentItem(adapter.getCount()-1);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanySlidingActivity.this, SettingsActivity.class);
//                intent.putExtra("categories",categories);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanySlidingActivity.this, SearchActivity.class);
//                intent.putExtra("categories",categories);
                startActivity(intent);
            }
        });

        try{

            getSupportActionBar().hide();
        }catch (Exception ex){

        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        try {
            AppController.getInstance().cancelPendingRequests();
            Settings.set_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    @Override
    protected void onResume(){
        super.onResume();
      //  AppController.getInstance().getDefaultTracker().setScreenName("ARCHIVELIST SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {
//        private final Map<Integer,CompanyListFragment>

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "الكل";
            else
            return categories.get(position-1).title;
        }

        @Override
        public int getCount() {
            return categories.size()+1;
        }

        @Override
        public Fragment getItem(int position) {
            if(frags==null){
                frags = new Hashtable<>();
            }

            if(position==0){
                frags.put(position,CompanyListFragment.newInstance("الكل",""));
            }else{
                frags.put(position,CompanyListFragment.newInstance(categories.get(position-1).title,categories.get(position-1).id));

            }

            return frags.get(position);
        }

        public CompanyListFragment getthisfrag(int pos){

            return    frags.get(pos);
        }

    }
    public void get_category() {
        String url;
        url = Settings.SERVERURL+"category_new.php";
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        pd.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
//                progressDialog.dismiss();position
                pd.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
                categories.clear();
//                categories_to_send.clear();

                Log.e("response is: ", jsonObject.toString());
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {

                        JSONObject sub = jsonObject.getJSONObject(i);
                        Picasso.with(CompanySlidingActivity.this).load(sub.getString("image")).fetch();
                        categories.add(new Category(sub));

                    }
                    adapter = new MyPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(cat);
                    pagerSlidingTabStrip.setViewPager(viewPager);
                    pagerSlidingTabStrip.setTextColor(Color.WHITE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(CompanySlidingActivity.this, Settings.getword(CompanySlidingActivity.this, "server_not_connected"), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}
