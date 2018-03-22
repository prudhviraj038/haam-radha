package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Downloader;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sriven on 6/7/2016.
 */
public class LoginActivity extends BaseActivity{
    TextView fp,fp_email_sta,fp_title,submit_tv,head_login,fb_tv,tw_tv,goo_tv,email_tv,pass_tv,tv_tv,login_tv,nac_tv;
    DatabaseHandler databaseHandler;
    LinearLayout fb,goo,tw,login,new_acc;
    EditText email,password,fp_et;
    ImageView close,close_fp;
    LinearLayout fp_pop_ll,submit_ll;
    private CallbackManager callbackManager;
    String emaill,id,name,type,ph;
    TwitterAuthClient mTwitterAuthClient;
//    GoogleApiClient  mGoogleApiClient;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    int RC_SIGN_IN=5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Session.forceRTLIfSupported(this);
        FacebookSdk.sdkInitialize(this);
       // AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login_activity);
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

        fb=(LinearLayout)findViewById(R.id.login_fb_ll);
        goo=(LinearLayout)findViewById(R.id.login_gm_ll);
        tw=(LinearLayout)findViewById(R.id.login_tw_ll);
        login=(LinearLayout)findViewById(R.id.login_lllll);
        new_acc=(LinearLayout)findViewById(R.id.new_accc_ll);

        email=(EditText)findViewById(R.id.et_email_login);
        email.setHint(Session.getword(this,"empty_email"));
        password=(EditText)findViewById(R.id.et_password_login);
        password.setHint(Session.getword(this,"empty_password"));

        fp=(TextView)findViewById(R.id.for_password_tv);
        fp.setText(Session.getword(this,"forgot_password"));
        close=(ImageView)findViewById(R.id.close_login_img);
        fp_et=(EditText)findViewById(R.id.fp_et_email);
        fp_et.setHint("empty_email");
        fp_email_sta=(TextView)findViewById(R.id.fp_email_sta_tv);
        fp_email_sta.setText(Session.getword(this, "email"));
        fp_title=(TextView)findViewById(R.id.fp_title);
        fp_title.setText(Session.getword(this,"forgot_password"));
        submit_tv=(TextView)findViewById(R.id.fp_submit_tv);
        submit_tv.setText(Session.getword(this,"submit"));

        head_login=(TextView)findViewById(R.id.header_login);
        head_login.setText(Session.getword(this,"login"));
        fb_tv=(TextView)findViewById(R.id.fb_tv_login);
        fb_tv.setText(Session.getword(this,"fb_login"));
        tw_tv=(TextView)findViewById(R.id.tw_tv_login);
        tw_tv.setText(Session.getword(this,"tw_login"));
        goo_tv=(TextView)findViewById(R.id.goo_tv_login);
        goo_tv.setText(Session.getword(this,"google_login"));
        email_tv=(TextView)findViewById(R.id.email_tv_login);
        email_tv.setText(Session.getword(this,"email"));
        pass_tv=(TextView)findViewById(R.id.password_tv_login);
        pass_tv.setText(Session.getword(this,"password"));
        tv_tv=(TextView)findViewById(R.id.tvv_tv_login);
        tv_tv.setText(Session.getword(this,"login_by_email"));
        login_tv=(TextView)findViewById(R.id.login_tvvvvv);
        login_tv.setText(Session.getword(this,"login"));
        nac_tv=(TextView)findViewById(R.id.new_accc_tvvvv);
        nac_tv.setText(Session.getword(this,"new_acc"));

        fp_pop_ll=(LinearLayout)findViewById(R.id.fp_pop_ll);
        submit_ll=(LinearLayout)findViewById(R.id.fp_submit_ll);

        close_fp=(ImageView)findViewById(R.id.fp_close);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp_pop_ll.setVisibility(View.VISIBLE);
            }
        });
        close_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp_pop_ll.setVisibility(View.GONE);
            }
        });
        submit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fp_et.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, Session.getword(LoginActivity.this,"empty_email"), Toast.LENGTH_SHORT).show();
                } else if (!fp_et.getText().toString().matches(emailPattern)){
                    Toast.makeText(LoginActivity.this, Session.getword(LoginActivity.this, "empty_email_valid"), Toast.LENGTH_SHORT).show();
                }else{
//                    fp_pop_ll.setVisibility(View.GONE);
                    forgot_pass();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, Session.getword(LoginActivity.this, "empty_email"), Toast.LENGTH_SHORT).show();
                }else if(!email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, Session.getword(LoginActivity.this, "empty_email_valid"), Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this,Session.getword(LoginActivity.this,"empty_password"), Toast.LENGTH_SHORT).show();
                }else {
                    login();
                }
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
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTwitterAuthClient.authorize(LoginActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        name =twitterSessionResult.data.getUserName();
                        id=String.valueOf(twitterSessionResult.data.getUserName());
                        Log.d("twittercommunity", name+"  "+id);
                        emaill = "";
                        ph = "";
                        type = "twitter";
                        String img="https://twitter.com/"+name+"/profile_image?size=original";
                        Log.d("twitter_url", img);
                        Session.set_user_img(LoginActivity.this,img);
                       login(emaill, type, name, id, ph);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });

            }
        });
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
                                                Session.set_user_img(LoginActivity.this,profilePicUrl);
                                            }else{
                                                Session.set_user_img(LoginActivity.this,"-1");
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
        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.e("googlee_result", String.valueOf(result.getStatus().getStatusCode()));
////            if (result.isSuccess()) {
//                // Signed in successfully, show authenticated UI.
//               // Log.e("googlee_succ",result.getStatus().getStatusMessage());
//                GoogleSignInAccount acct = result.getSignInAccount();
//                name =acct.getDisplayName();
//                id=acct.getId();
//            Log.e("googlee_id", id);
//                emaill = acct.getEmail();;
//                ph = "";
//                type = "gplus";
//            Uri uri=acct.getPhotoUrl();;
//            String stringUri=uri.toString();
//            Log.d("google_img_url", stringUri);
//            Session.set_user_img(LoginActivity.this,stringUri);
//                login(emaill, type, name, id, ph);
////            }else{
//
//            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    public  void login(){
        String url = null;
        try {
            url = Session.NOTIFY_SERVER_URL+"login.php?" + "email="+ URLEncoder.encode(email.getText().toString(), "utf-8")+
                    "&password="+URLEncoder.encode(password.getText().toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(LoginActivity.this, "please_wait"));
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
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String msg = jsonObject.getString("name");
                        Session.set_user_id(LoginActivity.this, mem_id,msg);
                        Session.set_user_img(LoginActivity.this, "-1");
                        Session.is_first_set(LoginActivity.this, "0");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        Session.sendChannelsToServer(LoginActivity.this);
                        get_chanels(databaseHandler.all_selected_channels_new("0"));

//                        Intent intent = new Intent(LoginActivity.this, NewsRecycleListActivity.class);
//                        startActivity(intent);
//                        if(temp.equals("1")){
//                            onBackPressed();
//                        }else {
//                            Intent intent = new Intent(LoginActivity.this, ViewpagerActivity.class);
//                            ActivityOptions options = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.enter_from_left, R.anim.exit_to_right);
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
                Toast.makeText(LoginActivity.this, Session.getword(LoginActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

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
        progressDialog.setMessage(Session.getword(LoginActivity.this, "please_wait"));
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
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String msg = jsonObject.getString("name");
                        Session.set_user_id(LoginActivity.this, mem_id,msg);
                        Session.is_first_set(LoginActivity.this, "0");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        Session.sendChannelsToServer(LoginActivity.this);
                        get_chanels(databaseHandler.all_selected_channels_new("0"));
//                        Intent intent = new Intent(LoginActivity.this, NewsRecycleListActivity.class);
//                        startActivity(intent);
//                        if(temp.equals("1")){
//                            onBackPressed();
//                        }else {
//                            Intent intent = new Intent(LoginActivity.this, ViewpagerActivity.class);
//                            ActivityOptions options = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.enter_from_left, R.anim.exit_to_right);
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
                Toast.makeText(LoginActivity.this, Session.getword(LoginActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    public void forgot_pass(){
        String url = Session.NOTIFY_SERVER_URL+"forget-password.php?email="+fp_et.getText().toString();
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(LoginActivity.this,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        fp_pop_ll.setVisibility(View.GONE);
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
                Toast.makeText(LoginActivity.this,  Session.getword(LoginActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    ProgressDialog progressDialog1;
    private void get_chanels(String list){
    String url;
    // progressBar.setVisibility(View.VISIBLE);
    if(Session.get_user_id(LoginActivity.this).equals("-1")) {
        url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list;
    }else{
        url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list+"&member_id="+Session.get_user_id(LoginActivity.this);
//                + "&push_channels="+databaseHandler.notification_enabled_chanels();
    }
    Log.e("url", url);
    progressDialog1 = new ProgressDialog(LoginActivity.this);
    progressDialog1.setMessage(Session.getword(LoginActivity.this,"please_wait"));
    progressDialog1.show();
    progressDialog1.setCancelable(false);
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray jsonArray) {
            Log.e("response", jsonArray.toString());

             new DownloadFilesTask().execute(jsonArray);
            //  progressBar.setVisibility(View.GONE);
            // chanels =new ArrayList<>();

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(progressDialog1!=null)
                progressDialog1.dismiss();

            //  progressBar.setVisibility(View.GONE);
            Log.e("error", volleyError.toString());

        }
    });

    AppController.getInstance().addToRequestQueue(jsonArrayRequest);

}
    private class DownloadFilesTask extends AsyncTask<JSONArray, Integer, Integer> {
        protected Integer doInBackground(JSONArray... urls) {

            for(int i=0;i<urls[0].length();i++){

                try {
                    JSONObject jsonObject = urls[0].getJSONObject(i);
                    Chanel chanel=new Chanel(jsonObject,"0");
                    if(!databaseHandler.is_following(chanel.ch_id)){
                        databaseHandler.addPlaylist(chanel.ch_id,chanel.parent_id,chanel.status);
                    }else{
                        databaseHandler.updatenotify(chanel.ch_id,chanel.status);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return 0;
        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Integer result) {
//            showDialog("Downloaded " + result + " bytes");
//            if(progressDialog1!=null)
//                progressDialog1.dismiss();
//            Session.sendRegistrationToServer(LoginActivity.this);
            Log.e("testing","testttttttttttttttt");
            Log.e("testing_login","testing_login");
            Intent intent = new Intent(LoginActivity.this, NewsRecycleListActivity.class);
            intent.putExtra("feed_id", "0");
            intent.putExtra("login_check", "0");
            startActivity(intent);
            finish();

        }
    }
}
