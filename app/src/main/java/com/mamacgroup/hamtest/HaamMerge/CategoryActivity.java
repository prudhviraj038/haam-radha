package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.facebook.FacebookSdk;
//import com.google.android.gms.analytics.HitBuilders;
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


public class CategoryActivity extends Activity {
    static final String DEVELOPER_KEY = "AIzaSyDpQ6VmRaiN728aG7TyXrGewgOoisuLJZg";
    RelativeLayout r1,r2,r3,r4,r5,r6,r7,r8;
    MyTextViewBold tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,d1,d2,d3,d4,d5,d6,d7,d8;
    MyTextView pd_tv;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,backup_cat,setting,fav_img;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;
    ArrayList<Category> categ;
    ArrayList<Category> categories_to_send;
    GridView gv;
    String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",im1="",im2="",im3="",im4="",im5="",im6="",im7="",im8="";
    LinearLayout pd,l1,l2,l3,l4,l5,l6,l7,l8;
    SwipeRefreshLayout swipeRefreshLayout;
    JSONArray js;
    MyTextView logo_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.haam_categoryactivity);
        String cat_images_str = Settings.get_news_vied(this);
        String cat_des_str = Settings.get_news_vied(this);
        js=new JSONArray();
        try {
            AppController.getInstance().cat_images = new JSONObject(cat_images_str);
            AppController.getInstance().cat_des = new JSONObject(cat_des_str);
            Log.e("cat_object",AppController.getInstance().cat_images.toString());
        } catch (JSONException e) {
        }
        Settings.setWishid(this, "0");
        categories=new ArrayList<>();
        categ=new ArrayList<>();
        categories_to_send = new ArrayList<>();

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setEnabled(false);
        gv=(GridView)findViewById(R.id.catgridView);
        pd=(LinearLayout)findViewById(R.id.progressBar_ll);
        pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        logo_tv=(MyTextView)findViewById(R.id.tv_logo_cat);
        logo_tv.setText(Session.getword(this,"main_title"));

        l1=(LinearLayout)findViewById(R.id.l_s_1);
        l2=(LinearLayout)findViewById(R.id.l_s_2);
        l3=(LinearLayout)findViewById(R.id.l_s_3);
        l4=(LinearLayout)findViewById(R.id.l_s_4);
        l5=(LinearLayout)findViewById(R.id.l_s_5);
        l6=(LinearLayout)findViewById(R.id.l_s_6);
        l7=(LinearLayout)findViewById(R.id.l_s_7);
        l8=(LinearLayout)findViewById(R.id.l_s_8);


        r1=(RelativeLayout)findViewById(R.id.rl_cat1);
        r2=(RelativeLayout)findViewById(R.id.rl_cat2);
        r3=(RelativeLayout)findViewById(R.id.rl_cat3);
        r4=(RelativeLayout)findViewById(R.id.rl_cat4);
        r5=(RelativeLayout)findViewById(R.id.rl_cat5);
        r6=(RelativeLayout)findViewById(R.id.rl_cat6);
        r7=(RelativeLayout)findViewById(R.id.rl_cat7);
        r8=(RelativeLayout)findViewById(R.id.rl_cat8);

        tv1=(MyTextViewBold)findViewById(R.id.tv1);
        tv2=(MyTextViewBold)findViewById(R.id.tv2);
        tv3=(MyTextViewBold)findViewById(R.id.tv3);
        tv4=(MyTextViewBold)findViewById(R.id.tv4);
        tv5=(MyTextViewBold)findViewById(R.id.tv5);
        tv6=(MyTextViewBold)findViewById(R.id.tv6);
        tv7=(MyTextViewBold)findViewById(R.id.tv7);
        tv8=(MyTextViewBold)findViewById(R.id.tv8);

        d1=(MyTextViewBold)findViewById(R.id.des1);
        d2=(MyTextViewBold)findViewById(R.id.des2);
        d3=(MyTextViewBold)findViewById(R.id.des3);
        d4=(MyTextViewBold)findViewById(R.id.des4);
        d5=(MyTextViewBold)findViewById(R.id.des5);
        d6=(MyTextViewBold)findViewById(R.id.des6);
        d7=(MyTextViewBold)findViewById(R.id.des7);
        d8=(MyTextViewBold)findViewById(R.id.des8);

        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);
        img6=(ImageView)findViewById(R.id.img6);
        img7=(ImageView)findViewById(R.id.img7);
        img8=(ImageView)findViewById(R.id.img8);

        backup_cat=(ImageView)findViewById(R.id.reload_backup_cat);
        setting=(ImageView)findViewById(R.id.setting_img);
        fav_img=(ImageView)findViewById(R.id.fav_img);

        pd_tv=(MyTextView)findViewById(R.id.pd_tv_cattt);
        pd_tv.setText(Session.getword(this,"Loading"));


//        if(getIntent().hasExtra("categories")) {
//            categ = (ArrayList<Category>) getIntent().getSerializableExtra("categories");
//            categories_to_send = (ArrayList<Category>) getIntent().getSerializableExtra("categories");
//            for (int i = 0; i < categ.size(); i++) {
//            if (i == 0) {
//                s1 = categ.get(i).id;
//                tv1.setText(categ.get(i).title_ar);
//                Picasso.with(CategoryActivity.this).load(categ.get(i).image).into(img1);
//                l1.setVisibility(View.VISIBLE);
//            } else if (i == 1) {
//                s2 = categ.get(i).id;
//                tv2.setText(categ.get(i).title_ar);
//                Picasso.with(CategoryActivity.this).load(categ.get(i).image).into(img2);
//                l2.setVisibility(View.VISIBLE);
//            } else if (i == 2) {
//                s3 = categ.get(i).id;
//                tv3.setText(categ.get(i).title_ar);
//                Picasso.with(CategoryActivity.this).load(categ.get(i).image).into(img3);
//                l3.setVisibility(View.VISIBLE);
//            }
//////                        else if(i==3){
//////                            tv4.setText(sub.getString("title_ar"));
//////                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img4);
//////                        }else if(i==4){
//////                            tv5.setText(sub.getString("title_ar"));
//////                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img5);
//////                        }else if(i==5){
//////                            tv6.setText(sub.getString("title_ar"));
//////                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img6);
//////                        }
//            else {
//                categories.add(categ.get(i));
//            }
//        }
//            categoryAdapter=new CategoryAdapter(CategoryActivity.this,categories);
//            gv.setAdapter(categoryAdapter);
//            categoryAdapter.notifyDataSetChanged();
//            setGridViewHeightBasedOnItems(gv);
//            set_refresh_timer();
//        }
//        swipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
////                        swipeRefreshLayout.setRefreshing(true);
//                        get_category();
//                    }
//                }
//        );
        get_category("0");
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img1;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(0).newses);
                intent.putExtra("last",categories_to_send.get(0).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img2;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(1).newses);
                intent.putExtra("last",categories_to_send.get(1).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img3;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news", categories_to_send.get(2).newses);
                intent.putExtra("last",categories_to_send.get(2).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img1;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(3).newses);
                intent.putExtra("last",categories_to_send.get(3).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img2;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(4).newses);
                intent.putExtra("last",categories_to_send.get(4).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img3;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(5).newses);
                intent.putExtra("last",categories_to_send.get(5).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img1;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(6).newses);
                intent.putExtra("last",categories_to_send.get(6).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });
        r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(CategoryActivity.this, "0");
                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
//                View sharedView = img2;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories_to_send.get(7).newses);
                intent.putExtra("last",categories_to_send.get(7).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                startActivity(intent);
            }
        });

//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Settings.setWishid(CategoryActivity.this, "0");
//                Intent intent = new Intent(CategoryActivity.this, VideoPlayerActivity.class);
////                View sharedView = img3;
////                String transitionName = getString(R.string.transition_name_image);
//                intent.putExtra("news",categories.get(i).newses);
////                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                startActivity(intent);
//            }
//        });
        backup_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CompanySlidingActivity.class);
//                intent.putExtra("categories", categories_to_send);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(CategoryActivity.this, SettingsActivity.class);
//                intent.putExtra("categories",categories);
//                startActivity(intent);
                finish();
            }
        });

        fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, WishListActivity.class);
//                intent.putExtra("categories", categories_to_send);
                startActivity(intent);
            }
        });

        String selected_ch = Settings.getCurrencies(this);
        Log.e("selected_ch_splash",selected_ch);

        if(!selected_ch.equals("-1")){
            try {
                JSONArray jsonArray = new JSONArray(selected_ch);

                AppController.getInstance().selected_channels.clear();
                for(int i=0;i<jsonArray.length();i++){

                    AppController.getInstance().selected_channels.add(jsonArray.get(i).toString());
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        set_refresh_timer();
    }
    final Handler h = new Handler();
    final int delay = 1000*60;//milliseconds

    final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.e("time","ticked");
                h.postDelayed(this, delay);
                // tabclicked(selected,true);
              get_category("1");
        }
    };

    private void set_refresh_timer(){
            h.postDelayed(r, delay);
    }
    public void get_category(String te) {
        String url;
        url = Settings.SERVERURL+"category_new.php";
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        if(te.equals("0"))
            pd.setVisibility(View.VISIBLE);
        else
            pd.setVisibility(View.GONE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
//                progressDialog.dismiss();position
                pd.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
                categories.clear();
                categories_to_send.clear();
                Log.e("response is: ", jsonObject.toString());
                js=jsonObject;
               data(jsonObject);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(CategoryActivity.this,  Session.getword(CategoryActivity.this,"no_network"), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    public void  data(JSONArray jsonArray){

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("sizeeee",String.valueOf(jsonArray.length()));
                JSONObject sub = jsonArray.getJSONObject(i);
                Picasso.with(CategoryActivity.this).load(sub.getString("image")).fetch();
                Category cate=new Category(sub);
                categories_to_send.add(cate);
                if(cate.newses.size()>0 && !AppController.getInstance().news_viewed.contains(cate.newses.get(0).id)){
                    AppController.getInstance().cat_images.put(cate.newses.get(0).cat_id, cate.newses.get(0).image);
                    AppController.getInstance().cat_des.put(cate.newses.get(0).cat_id, cate.newses.get(0).about);
                }
                if(i==0){
                    s1=sub.getString("id");
                    tv1.setText(sub.getString("title_ar"));
                    im1=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s1)){
                        Log.e("cat_img", "cat_img");
                        if(cate.all_viewed=="1"){
//                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img1);
                            d1.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img1.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s1)).into(img1);
                            d1.setText(AppController.getInstance().cat_des.getString(s1));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img1);
                        d1.setText(sub.getString("about"));
                    }
                    l1.setVisibility(View.VISIBLE);
                }else if(i==1){
                    s2=sub.getString("id");
                    tv2.setText(sub.getString("title_ar"));
                    im2=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s2)){
                        Log.e("cat_img","cat_img");
                        if(cate.all_viewed=="1"){
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img2);
                            d2.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img2.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s2)).into(img2);
                            d2.setText(AppController.getInstance().cat_des.getString(s2));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img2);
                        d2.setText(sub.getString("about"));
                    }
//                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img2);
                    l2.setVisibility(View.VISIBLE);
                }else if(i==2){
                    s3=sub.getString("id");
                    tv3.setText(sub.getString("title_ar"));
                    im3=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s3)){
                        Log.e("cat_img","cat_img");
                        if(cate.all_viewed=="1"){
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img3);
                            d3.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img3.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s3)).into(img3);
                            d3.setText(AppController.getInstance().cat_des.getString(s3));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img3);
                        d3.setText(sub.getString("about"));
                    }
//                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img3);
                    l3.setVisibility(View.VISIBLE);
                }else if(i==3){
                    s4=sub.getString("id");
                    tv4.setText(sub.getString("title_ar"));
                    im4=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s4)){
                        Log.e("cat_img", "cat_img");
                        if(cate.all_viewed=="1"){
//                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img4);
                            d4.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img4.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s4)).into(img4);
                            d4.setText(AppController.getInstance().cat_des.getString(s4));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img4);
                        d4.setText(sub.getString("about"));
                    }
                    l4.setVisibility(View.VISIBLE);
                }else if(i==4){
                    s5=sub.getString("id");
                    tv5.setText(sub.getString("title_ar"));

                    im5=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s5)){
                        Log.e("cat_img","cat_img");
                        if(cate.all_viewed=="1"){
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img5);
                            d5.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img5.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s5)).into(img5);
                            d5.setText(AppController.getInstance().cat_des.getString(s5));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img5);
                        d5.setText(sub.getString("about"));
                    }
//                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img2);
                    l5.setVisibility(View.VISIBLE);
                }else if(i==5){
                    s6=sub.getString("id");
                    tv6.setText(sub.getString("title_ar"));
                    im6=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s6)){

                        if(cate.all_viewed=="1"){
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img6);
                            d6.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img6.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s6)).into(img6);
                            d6.setText(AppController.getInstance().cat_des.getString(s6));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img6);
                        d6.setText(sub.getString("about"));
                    }
//                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img3);
                    l6.setVisibility(View.VISIBLE);
                }else if(i==6){
                    s7=sub.getString("id");
                    tv7.setText(sub.getString("title_ar"));
                    im7=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s7)){
                        Log.e("cat_img7","cat_img");
                        if(cate.all_viewed=="1"){
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img7);
                            Log.e("cat_img_711",sub.getString("image"));
                            d7.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img7.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s7)).into(img7);
                            Log.e("cat_img_7", AppController.getInstance().cat_images.getString(s7));
                            d7.setText(AppController.getInstance().cat_des.getString(s7));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img7);
                        d7.setText(sub.getString("about"));
                    }
//                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img3);
                    l7.setVisibility(View.VISIBLE);
                }else if(i==7){
                    s8=sub.getString("id");
                    tv8.setText(sub.getString("title_ar"));
                    im8=sub.getString("image");
                    if(AppController.getInstance().cat_images.has(s8)){
                        Log.e("cat_img","cat_img");
                        if(cate.all_viewed=="1"){
                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img8);
                            d8.setText(sub.getString("about"));
                            ColorMatrix matrix = new ColorMatrix();
                            matrix.setSaturation(0);

                            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                            img8.setColorFilter(filter);
                        }else {
                            Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s8)).into(img8);
                            d8.setText(AppController.getInstance().cat_des.getString(s8));
                        }
                    }else {
                        Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img8);
                        d8.setText(sub.getString("about"));
                    }
//                            Picasso.with(CategoryActivity.this).load(sub.getString("image")).into(img3);
                    l8.setVisibility(View.VISIBLE);
                }
                else {
                    Category prod = new Category(sub);
                    categories.add(prod);
                }
            }
//                    title.setText(categories.get(0).getTitle(this));
            categoryAdapter=new CategoryAdapter(CategoryActivity.this,categories);
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CategoryActivity.this);
//                    gv.setLayoutManager(mLayoutManager);
//                    gv.setItemAnimator(new DefaultItemAnimator());
//                    gv.getItemAnimator().setMoveDuration(1000);
//                    gv.setLayoutManager(manager);
//                    int spanCount = 2; // 3 columns
//                    int spacing = dpToPx(1); // 50px
//                    boolean includeEdge = false;
//                    gv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            gv.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
            setGridViewHeightBasedOnItems(gv);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    SpannedGridLayoutManager manager = new SpannedGridLayoutManager(
            new SpannedGridLayoutManager.GridSpanLookup() {
                @Override
                public SpannedGridLayoutManager.SpanInfo getSpanInfo(int position) {
                    switch (position % 5) {
                        case 0:
                            return new SpannedGridLayoutManager.SpanInfo(66, 116);

                        case 1:
                            return new SpannedGridLayoutManager.SpanInfo(34,58);

                        case 2:
                            return new SpannedGridLayoutManager.SpanInfo(34, 58);

                        case 3:
                            return new SpannedGridLayoutManager.SpanInfo(50, 87);

                        case 4:
                            return new SpannedGridLayoutManager.SpanInfo(50, 87);

                        default:
                            return new SpannedGridLayoutManager.SpanInfo(50, 87);
                    }
                }
            },
            100, // number of columns
            1f // default size of item
    );
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics =CategoryActivity.this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    public static boolean setGridViewHeightBasedOnItems(GridView gridView) {

        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount()-3;

            // Get total height of all items.
            int totalItemsHeight = 0;
            int lastItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, gridView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
                lastItemsHeight=item.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            if(numberOfItems%2==1)
                totalItemsHeight=totalItemsHeight+lastItemsHeight;

            params.height = totalItemsHeight/2;
            gridView.setLayoutParams(params);
            gridView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        h.removeCallbacksAndMessages(null);
        Settings.setCurrencies(this, AppController.getInstance().selected_channels.toString());
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
        set_refresh_timer();
        //AppController.getInstance().getDefaultTracker().setScreenName("MAIN SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());

        try {
            Settings.get_minimizetime(this);
            categories.clear();
            categories_to_send.clear();
            categoryAdapter.notifyDataSetChanged();

//            if(AppController.getInstance().cat_images.has(s1)){
//                if(AppController.getInstance().cat_images.getString(s1).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im1).into(img1);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img1.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s1)).into(img1);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s2)) {
//                if(AppController.getInstance().cat_images.getString(s2).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im2).into(img2);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img2.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s2)).into(img2);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s3)){
//                if(AppController.getInstance().cat_images.getString(s3).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im3).into(img3);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img3.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s3)).into(img3);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s4)){
//                if(AppController.getInstance().cat_images.getString(s4).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im4).into(img4);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img4.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s4)).into(img4);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s5)) {
//                if(AppController.getInstance().cat_images.getString(s5).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im5).into(img5);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img5.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s5)).into(img5);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s6)){
//                if(AppController.getInstance().cat_images.getString(s6).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im6).into(img6);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img6.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s6)).into(img6);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s7)){
//                if(AppController.getInstance().cat_images.getString(s7).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im7).into(img7);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img7.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s7)).into(img7);
//                }
//            }
//            if(AppController.getInstance().cat_images.has(s8)) {
//                if(AppController.getInstance().cat_images.getString(s8).equals("0")){
////                                    ImageView imageview = (ImageView) findViewById(R.id.imageView1);
//                    Picasso.with(CategoryActivity.this).load(im8).into(img8);
//                    ColorMatrix matrix = new ColorMatrix();
//                    matrix.setSaturation(0);
//
//                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//                    img8.setColorFilter(filter);
//                }else {
//                    Picasso.with(CategoryActivity.this).load(AppController.getInstance().cat_images.getString(s8)).into(img8);
//                }
//            }
//            categoryAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        data(js);
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


}
