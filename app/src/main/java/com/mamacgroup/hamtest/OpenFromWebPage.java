package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by mac on 1/5/17.
 */

public class OpenFromWebPage extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        setContentView(R.layout.spalsh_screen);
        // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Log.d( "Refreshed token: " , refreshedToken);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView=(ImageView)findViewById(R.id.splash);
        Session.set_user_language(OpenFromWebPage.this, "ar");
        // imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation));


//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Session.sendRegistrationToServer(this);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
////                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
////                //intent.putExtra("goto",getIntent().getStringExtra("goto"));
////               // intent.putExtra("data",getIntent().getStringExtra("data"));
////                startActivity(intent);
////                finish();
//
//            }
//        }, 500);


//<a href="app.naqsh.scheme://parameter0/feed_id/parameter2" id="submit"></a>

        Uri data = getIntent().getData();
        if(getIntent().getData()==null){
            get_language_words("0");
        }else {
            String scheme = data.getScheme(); // "http"
            String host = data.getHost(); // "twitter.com"
            List<String> params = data.getPathSegments();

            for (int i = 0; i < params.size(); i++) {
                Log.e("prams_size", params.get(i));
            }
            String first = params.get(0); // "status"
            String second = params.get(1);
            get_language_words(first);
        }

        if(Session.getFirstopen(this).equals("0"))
            Session.setFirstopen(this);


    }

    private void get_language_words(final String feed_id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        String url = Session.SERVER_URL+"words-json.php";

        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                Session.set_user_language_words(OpenFromWebPage.this, jsonObject.toString());
//                if(Session.get_user_language1(SplashActivity.this).equals("-1")){
//                    Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else {

                Intent intent = new Intent(OpenFromWebPage.this, NewsRecycleListActivity.class);
            //    Session.set_news_feed_id(OpenFromWebPage.this,feed_id);
                intent.putExtra("feed_id", feed_id);
                intent.putExtra("type", "news");

                startActivity(intent);
                finish();
//                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("error",error.toString());
                //  Toast.makeText(SplashActivity.this, Session.getword(SplashActivity.this,"no_network"), Toast.LENGTH_SHORT).show();
                Intent no_internet = new Intent(OpenFromWebPage.this,NoInternetActivity.class);
                startActivity(no_internet);
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);


    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        try {
            AppController.getInstance().cancelPendingRequests();
            Session.set_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }



    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        try {
            Session.get_minimizetime(this);
        }catch(Exception ex){
            ex.printStackTrace();
        }


    }
}
