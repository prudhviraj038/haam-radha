package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mamacgroup.hamtest.HaamMerge.Settings;
//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.CustomEvent;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by sriven on 6/7/2016.
 */
public class SplashActivity extends Activity {
    DatabaseHandler databaseHandler;
    String onesignal_player_id;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        setContentView(R.layout.haam_splash_activity);
        databaseHandler = new DatabaseHandler(this);

       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Log.d( "Refreshed token: " , refreshedToken);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView=(ImageView)findViewById(R.id.splash_img);
        Session.set_user_language(SplashActivity.this, "ar");
       // imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation));

        get_language_words();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sound", true);
        editor.commit();
//        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
//            @Override
//            public void idsAvailable(String userId, String registrationId) {
//                Log.e("haam_test","test1");
//                Log.d("debug", "User:" + userId);
//                Session.set_player_id(SplashActivity.this,userId);
//                if (registrationId != null)
//                    Log.d("debug", "registrationId:" + registrationId);
//
//            }
//        });

//        if(Session.getFirstopen(this).equals("0"))
//            Session.setFirstopen(this);
//        try {
//            PackageInfo info = this.getPackageManager().getPackageInfo(
//                    "com.mamacgroup.hamtest",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", "KeyHash:" + Base64.encodeToString(md.digest(),
//                        Base64.DEFAULT));
////                Toast.makeText(this.getApplicationContext(), Base64.encodeToString(md.digest(),
////                        Base64.DEFAULT), Toast.LENGTH_LONG).show();
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (Exception e) {
//
//        }
//        Session.sendRegistrationToServer(this);

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
    private void get_chanels(final String list){
        String url;
//        final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
//        progressDialog.setMessage(Session.getword(SplashActivity.this,"please_wait"));
//        progressDialog.show();
//        progressDialog.setCancelable(false);

        // progressBar.setVisibility(View.VISIBLE);
        if(Session.get_user_id(SplashActivity.this).equals("-1")) {
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list;
        }else{
            url = Session.NOTIFY_SERVER_URL + "channels2.php?member_id="+Session.get_user_id(SplashActivity.this);
        }
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
                            databaseHandler.addPlaylist(chanel.ch_id,chanel.parent_id,"1");
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




                if(Session.get_user_id(SplashActivity.this).equals("-1") && Session.is_first_get(SplashActivity.this).equals("-1")){
                    Intent intent = new Intent(SplashActivity.this, FirstTimeAskingActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, NewsRecycleListActivity.class);
                    intent.putExtra("feed_id", "0");
                    intent.putExtra("login_check","1");
                    startActivity(intent);
                    finish();
                }

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
    private void get_language_words(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        String url = Session.SERVER_URL+"words-json.php";

        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                Session.set_user_language_words(SplashActivity.this, jsonObject.toString());

//                if(Session.get_user_language1(SplashActivity.this).equals("-1")){
//                    Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else {


                get_chanels(databaseHandler.all_selected_channels_new("0"));



//                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
                Log.e("error",error.toString());
              //  Toast.makeText(SplashActivity.this, Session.getword(SplashActivity.this,"no_network"), Toast.LENGTH_SHORT).show();
                Intent no_internet = new Intent(SplashActivity.this,NoInternetActivity.class);
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
//            AppController.getInstance().cancelPendingRequests();
            Session.set_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        try {
//            Session.get_minimizetime(this);
//
//            AppController.getInstance().getDefaultTracker().setScreenName("splashscreen");
//            AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
//            Answers.getInstance().logCustom(new CustomEvent("splashscreen"));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
