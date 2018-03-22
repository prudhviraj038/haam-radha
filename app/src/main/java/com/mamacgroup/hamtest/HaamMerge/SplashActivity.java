package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SplashActivity extends Activity {
    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haam_splash_activity);
        //AppController.getInstance().getDefaultTracker().setScreenName("SPLASH SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        categories = new ArrayList<>();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView = (ImageView) findViewById(R.id.splash_img);
//        ExceptionHandler.register(this, "https://www.darabeel.com/api/error.php");
        //   https://www.darabeel.com/api/error.php
//        imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enter_from_up));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                get_language_words();
//                setting();

                Settings.set_user_language(SplashActivity.this, "ar");

            }
        }, 2000);
        String selected_ch = Settings.getselectedCat(SplashActivity.this);
        Log.e("selected_ch_splash", selected_ch);

        if (!selected_ch.equals("-1")) {
            try {
                JSONArray jsonArray = new JSONArray(selected_ch);

                AppController.getInstance().selected_categories.clear();
                for (int i = 0; i < jsonArray.length(); i++) {

                    AppController.getInstance().selected_categories.add(jsonArray.get(i).toString());
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        String news_view = Settings.getNewsViewed(SplashActivity.this);
        Log.e("selected_ch_splash", news_view);

        if (!news_view.equals("-1")) {
            try {
                JSONArray jsonArray = new JSONArray(news_view);
                Log.e("news_view",news_view.toString());
                AppController.getInstance().news_viewed.clear();
                for (int i = 0; i < jsonArray.length(); i++) {

                    AppController.getInstance().news_viewed.add(jsonArray.get(i).toString());
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
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
    protected void onResume() {
        super.onPause();
//        AppController.getInstance().getDefaultTracker().setScreenName("SplashActivity");
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void get_category() {
        String url;
        url = Settings.SERVERURL + "category_new.php";
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
//                progressDialog.dismiss();
                categories.clear();
                Log.e("response is: ", jsonObject.toString());
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        categories.add(new Category(sub));
                    }
                    Intent intent = new Intent(SplashActivity.this, CategoryActivity.class);
                    intent.putExtra("categories", categories);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(SplashActivity.this, Settings.getword(SplashActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }



//    public  void newone(){
//        String abc= Settings.get_isfirsttime(MainActivity.this);
//        Log.e("lng", abc);
//        if(abc.equals("-1")) {
//            Intent mainIntent = new Intent(getApplicationContext(), LanguageActivity.class);
//            ActivityOptions options= ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.enter_from_up, R.anim.exit_to_down);
//            startActivity(mainIntent, options.toBundle());
//            finish();
//        }
//        else {
////            if(Settings.getUserid(MainActivity.this).equals("-1")){
////                Intent mainIntent = new Intent(getApplicationContext(), LoginInitialActivity.class);
////                ActivityOptions options=ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.enter_from_up, R.anim.exit_to_down);
////                startActivity(mainIntent, options.toBundle());
////                finish();
////            }else {
//                Intent mainIntent = new Intent(getApplicationContext(), ViewpagerActivity.class);
//                ActivityOptions options=ActivityOptions.makeCustomAnimation(this, R.anim.enter_from_up, R.anim.exit_to_down);
//                this.startActivity(mainIntent, options.toBundle());
////                overridePendingTransition(R.anim.enter_from_up,R.anim.exit_to_down);
////                startActivity(mainIntent);
//                finish();
////            }
//
//        }
//    }

    private void get_language_words() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(this, "please_wait"));
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL + "words-json-android.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                Settings.set_user_language_words(SplashActivity.this, jsonObject.toString());
                if(Settings.is_first_get(SplashActivity.this).equals("-1")){
                    Intent intent = new Intent(SplashActivity.this, FirstTimeScreenActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
//                get_category();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Log.e("error", error.toString());
//                Toast.makeText(SplashActivity.this, "Cannot reach our servers, Check your connection", Toast.LENGTH_SHORT).show();
                Intent no_internet = new Intent(SplashActivity.this,NoInternetActivity.class);
                startActivity(no_internet);
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
//    private void setting() {
//        String url = Settings.SERVERURL + "settings.php";
//        Log.e("url", url);
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//
//                Log.e("response is: ", jsonObject.toString());
//                Settings.setSettings(MainActivity.this, jsonObject.toString());
//
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error", error.toString());
//                Toast.makeText(MainActivity.this, "Cannot reach our servers, Check your connection", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//
//// Access the RequestQueue through your singleton class.
//        AppController.getInstance().addToRequestQueue(jsObjRequest);
//    }
//@Override
//public void onBackPressed() {
//    super.onBackPressed();
//    overridePendingTransition(R.anim.enter_from_down, R.anim.exit_to_up);
//}
@Override
protected void onDestroy() {
    super.onDestroy();
    Settings.setCurrencies(this, AppController.getInstance().selected_channels.toString());
}

}