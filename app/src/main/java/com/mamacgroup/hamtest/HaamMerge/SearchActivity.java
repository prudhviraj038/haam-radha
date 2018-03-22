package com.mamacgroup.hamtest.HaamMerge;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
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

/**
 * Created by Chinni on 04-05-2016.
 */
public class SearchActivity extends Activity {
    PagerSlidingTabStrip pagerSlidingTabStrip;
    ViewPager viewPager;
    ImageView setting,back,search;
    MyTextView tittle,no_news,pd_tv;
    GridView gv;
    EditText et_sr;
    LinearLayout pd;
    ArrayList<News> newses;
    NewsAdapter newsAdapter;
    int page=0;
    int preLast=0;
    String search1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.haam_search_activity);
        newses=new ArrayList<>();
        tittle = (MyTextView) findViewById(R.id.tittlee_sr);
        tittle.setText(Settings.getword(this,"SEARCH LIST"));
        no_news = (MyTextView) findViewById(R.id.no_newss);
        no_news.setText(Settings.getword(this,"No News related your search"));
        et_sr=(EditText)findViewById(R.id.et_search);
        et_sr.setHint(Settings.getword(this, "Type here"));
        pd_tv=(MyTextView)findViewById(R.id.pd_tv_cat_sr);
        pd_tv.setText(Settings.getword(this, "Loading"));
        search=(ImageView)findViewById(R.id.img_sr);
        back=(ImageView)findViewById(R.id.back_cs_sr);
        setting=(ImageView)findViewById(R.id.slid_setting_sr);
        gv = (GridView)findViewById(R.id.gridView_neews_sr);
        pd=(LinearLayout)findViewById(R.id.progressBar_llll_sr);
        newsAdapter=new NewsAdapter(this,newses);
        gv.setAdapter(newsAdapter);
        et_sr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search1 = s.toString();
                if (search1.length() > 0) {
                    get_news("1");
                }

            }

        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search1 = et_sr.getText().toString();
                get_news("1");
            }
        });
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                final int last = i + i1;
                if (last >= i2) {
                    if (preLast != last) {
                        preLast = last;
                        page++;
                        get_news("0");
                    }
                }

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
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
        //AppController.getInstance().getDefaultTracker().setScreenName("SEARCH SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    public void get_news(final String t) {
        String url;
            url = Settings.SERVERURL + "news.php?key="+search1+"&page="+page;
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        pd.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                pd.setVisibility(View.GONE);
                if(t.equals("1")) {
                    newses.clear();
                }
                Log.e("response is: ", jsonObject.toString());
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        News prod = new News(sub);
                        newses.add(prod);
                    }
                    newsAdapter.notifyDataSetChanged();
                    if(newses.size()<=0){
                        no_news.setVisibility(View.VISIBLE);
                    }else{
                        no_news.setVisibility(View.GONE);
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
                Toast.makeText(SearchActivity.this, Settings.getword(SearchActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

}
