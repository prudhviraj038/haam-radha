package com.mamacgroup.hamtest.HaamMerge;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Chinni on 04-05-2016.
 */
public class WishListActivity extends Activity {
    PagerSlidingTabStrip pagerSlidingTabStrip;
    ViewPager viewPager;
    ImageView setting,back;
    MyTextView tittle,no_fav,pd_tv;
    GridView gv;
    LinearLayout pd;
    ArrayList<News> newses;
    WishAdapter newsAdapter;
    int page=0;
    int preLast=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.haam_wishlist_activity);
        newses=new ArrayList<>();
        tittle = (MyTextView) findViewById(R.id.tittlee);
        no_fav = (MyTextView) findViewById(R.id.no_fav_tv);
        no_fav.setText(Settings.getword(this,"No favorite news"));
        tittle.setText(Settings.getword(this,"WISH LIST"));
        pd_tv=(MyTextView)findViewById(R.id.pd_tv_cat_wish);
        pd_tv.setText(Settings.getword(this,"Loading"));
        back=(ImageView)findViewById(R.id.back_cs_wish);
        setting=(ImageView)findViewById(R.id.slid_setting_wish);
        gv = (GridView)findViewById(R.id.gridView_neews_wish);
        pd=(LinearLayout)findViewById(R.id.progressBar_llll_wish);
        if(!AppController.getInstance().selected_channels.isEmpty()) {
            Log.e("wish",AppController.getInstance().selected_channels.toString());
            get_news();
        }else{
            no_fav.setVisibility(View.VISIBLE);
            Toast.makeText(WishListActivity.this, Settings.getword(WishListActivity.this,"Wish List Empty"), Toast.LENGTH_SHORT).show();
        }

//        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                final int last = i + i1;
//                if (last >= i2) {
//                    if (preLast != last) {
//                        preLast = last;
//                        page++;
//                        get_news();
//                    }
//                }
//
//            }
//        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishListActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    static WishListActivity mInstance;

    public static WishListActivity getInstance() {
        return mInstance;
    }
    @Override
    protected void onPause() {
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
        //AppController.getInstance().getDefaultTracker().setScreenName("WISHLIST SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    public void get_news() {
        String url;
            url = Settings.SERVERURL + "news.php?news="+android.text.TextUtils.join(",", AppController.getInstance().selected_channels);
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        pd.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                pd.setVisibility(View.GONE);
//                newses.clear();
                Log.e("response is: ", jsonObject.toString());
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        News prod = new News(sub);
                        newses.add(prod);
                    }
                    newsAdapter=new WishAdapter(WishListActivity.this,newses);
                    gv.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(WishListActivity.this, Settings.getword(WishListActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

}
