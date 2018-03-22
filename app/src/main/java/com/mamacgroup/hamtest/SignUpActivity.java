package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by sriven on 6/7/2016.
 */
public class SignUpActivity extends BaseActivity {
    LinearLayout fb,goo,tw,signup;
    DatabaseHandler databaseHandler;
    EditText email,password,fullname;
    ImageView close;
    TextView head_signup,fb_tv,tw_tv,goo_tv,fn_tv,email_tv,pass_tv,tv_tv,signup_tv,nac_tv;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private CallbackManager callbackManager;
    String emaill,id,name,type,ph;
    TwitterAuthClient mTwitterAuthClient;
//    GoogleApiClient mGoogleApiClient;
    int RC_SIGN_IN=5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.signup_activity);
        databaseHandler = new DatabaseHandler(this);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
//        TwitterAuthConfig authConfi=new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));
        mTwitterAuthClient= new TwitterAuthClient();
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("185349456914-ohpelq9n09vm1dtiv78lc4hub9og2v7j.apps.googleusercontent.com")
//                .requestEmail()
//                .requestId()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        fb=(LinearLayout)findViewById(R.id.signup_fb_ll);
        goo=(LinearLayout)findViewById(R.id.signup_gm_ll);
        tw=(LinearLayout)findViewById(R.id.signup_tw_ll);
        signup=(LinearLayout)findViewById(R.id.signup_lllll);

        email=(EditText)findViewById(R.id.et_email_signup);
        email.setHint(Session.getword(this,"empty_email"));
        password=(EditText)findViewById(R.id.et_password_signup);
        password.setHint(Session.getword(this,"empty_password"));
        fullname=(EditText)findViewById(R.id.et_fn_signup);
        fullname.setHint(Session.getword(this,"empty_name"));

        head_signup=(TextView)findViewById(R.id.header_signup);
        head_signup.setText(Session.getword(this,"signup"));
        fb_tv=(TextView)findViewById(R.id.fb_tv_signup);
        fb_tv.setText(Session.getword(this,"fb_signup"));
        tw_tv=(TextView)findViewById(R.id.tw_tv_signup);
        tw_tv.setText(Session.getword(this,"tw_signup"));
        goo_tv=(TextView)findViewById(R.id.goo_tv_signup);
        goo_tv.setText(Session.getword(this,"google_signup"));
        fn_tv=(TextView)findViewById(R.id.fn_tv_signup);
        fn_tv.setText(Session.getword(this,"full_name"));
        email_tv=(TextView)findViewById(R.id.email_tv_signup);
        email_tv.setText(Session.getword(this,"email"));
        pass_tv=(TextView)findViewById(R.id.password_tv_signup);
        pass_tv.setText(Session.getword(this,"password"));
        tv_tv=(TextView)findViewById(R.id.tvv_tv_signup);
        tv_tv.setText(Session.getword(this,"signup_by_email"));
        nac_tv=(TextView)findViewById(R.id.cre_new_accc_tvvvv);
        nac_tv.setText(Session.getword(this,"cre_new_acc"));

        close=(ImageView)findViewById(R.id.close_signup_img);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, Session.getword(SignUpActivity.this, "empty_name"), Toast.LENGTH_SHORT).show();
                }else if(email.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, Session.getword(SignUpActivity.this, "empty_email"), Toast.LENGTH_SHORT).show();
                }else if(!email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(SignUpActivity.this, Session.getword(SignUpActivity.this, "empty_email_valid"), Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this,Session.getword(SignUpActivity.this,"empty_password"), Toast.LENGTH_SHORT).show();
                }else {
                    signup();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                mTwitterAuthClient.authorize(SignUpActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

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
                        Session.set_user_img(SignUpActivity.this,img);
                        signup(emaill, type, name, id, ph);

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
                                                Session.set_user_img(SignUpActivity.this,profilePicUrl);
                                            }else{
                                                Session.set_user_img(SignUpActivity.this,"-1");
                                            }
                                            emaill = object.getString("email");
                                            id = object.getString("id");
                                            name = object.getString("name");
                                            ph = "";
                                            type = "facebook";
                                            signup(emaill, type, name, id, ph);
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
//            name =acct.getDisplayName();
//            id=acct.getId();
//            emaill = acct.getEmail();;
//            ph = "";
//            type = "gplus";
//            Uri uri=acct.getPhotoUrl();;
//            String stringUri=uri.toString();
//            Log.d("google_img_url", stringUri);
//            Session.set_user_img(SignUpActivity.this,stringUri);
//            signup(emaill, type, name, id, ph);
////            }else{
////
////            }
//        }else{
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
//        }
    }

    public  void signup(String email,String type,String name,String id,String ph){
        String url = null;
        try {
            url = Session.NOTIFY_SERVER_URL+"login-api.php?" + "email="+ URLEncoder.encode(email, "utf-8")+
                    "&type="+ URLEncoder.encode(type, "utf-8")+ "&name="+ URLEncoder.encode(name, "utf-8")+
                    "&user_id="+ URLEncoder.encode(id, "utf-8")+ "&phone="+ URLEncoder.encode(ph, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(SignUpActivity.this, "please_wait"));
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
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String msg = jsonObject.getString("name");
                        Session.set_user_id(SignUpActivity.this, mem_id,msg);
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        Session.sendChannelsToServer(SignUpActivity.this);
                        get_chanels(databaseHandler.all_selected_channels_new("0"));

//                        Intent intent = new Intent(SignUpActivity.this, NewsRecycleListActivity.class);
//                        startActivity(intent);
//                        if(temp.equals("1")){
//                            onBackPressed();
//                        }else {
//                            Intent intent = new Intent(SignUpActivity.this, ViewpagerActivity.class);
//                            ActivityOptions options = ActivityOptions.makeCustomAnimation(SignUpActivity.this, R.anim.enter_from_left, R.anim.exit_to_right);
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
                Toast.makeText(SignUpActivity.this, Session.getword(SignUpActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    public  void signup(){
        String url = null;
        try {
            url = Session.NOTIFY_SERVER_URL+"add-member.php?name="+ URLEncoder.encode(fullname.getText().toString()) + "&email="+ URLEncoder.encode(email.getText().toString(), "utf-8")+
                    "&password="+URLEncoder.encode(password.getText().toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(SignUpActivity.this, "please_wait"));
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
                    if(reply.equals("Success")) {
                        String mem_id=jsonObject.getString("member_id");
                        String msg = jsonObject.getString("message");
                        Session.set_user_id(SignUpActivity.this, mem_id,msg);
                        Session.set_user_img(SignUpActivity.this, "-1");
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        Session.sendChannelsToServer(SignUpActivity.this);
                        get_chanels(databaseHandler.all_selected_channels_new("0"));

//                        Intent intent = new Intent(SignUpActivity.this, NewsRecycleListActivity.class);
//                        startActivity(intent);
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SignUpActivity.this, Session.getword(SignUpActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    private void get_chanels(String list){
        String url;
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage(Session.getword(SignUpActivity.this,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);

        // progressBar.setVisibility(View.VISIBLE);
        if(Session.get_user_id(SignUpActivity.this).equals("-1")) {
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list;
        }else{
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list+"&member_id="+Session.get_user_id(SignUpActivity.this);

        }
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
                new DownloadFilesTask().execute(jsonArray);

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
    private class DownloadFilesTask extends AsyncTask<JSONArray, Integer, Integer> {
        protected Integer doInBackground(JSONArray... urls) {

            for(int i=0;i<urls[0].length();i++){

                try {
                    JSONObject jsonObject = urls[0].getJSONObject(i);
                    Chanel chanel=new Chanel(jsonObject,"0");
                    if(!databaseHandler.is_following(chanel.ch_id)){
                        databaseHandler.addPlaylist(chanel.ch_id,chanel.parent_id,chanel.status);
                    }else{
                        databaseHandler.updatenotify(chanel.ch_id, chanel.status);
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
//            Session.sendRegistrationToServer(SignUpActivity.this);
            Log.e("testing","testttttttttttttttt");
            Log.e("testing_login","testing_login");
            Intent intent = new Intent(SignUpActivity.this, NewsRecycleListActivity.class);
            intent.putExtra("feed_id", "0");
            intent.putExtra("login_check", "0");
            startActivity(intent);
            finish();

        }
    }
}
