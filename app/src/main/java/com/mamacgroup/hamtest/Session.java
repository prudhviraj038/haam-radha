package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by HP on 8/18/2016.
 */
public class Session {

    static String NOTIFY_SERVER_URL = "http://news.haamapp.com/apiv2/";

    static String SERVER_URL = "http://news.haamapp.com/apiv2/";


  //  static String SERVER_URL = "http://letuknow.co.in/api/";



    static String user_key = "radio_user";
    static String player_id = "player_id";

    static String user_name = "radio_user_name";
    static String user_img = "radio_user_img";
    static String lan_key = "radio_lan";
    static String add_key = "add_key";
    static String user_key_first = "radio_user_first";
    static String is_first = "is_first";

    static String words_key = "danden_words";
    static String notifications_key = "notification_count";

    static String firstopen = "notification_count";

    static String news_id = "news_id";

    static String KOCHAV_ID = "konaqsh-o3u85";




    public  static void setFirstopen(Context context){
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(firstopen,"1");
    editor.commit();

    try {
//
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.EventBuilder()
//            .setCategory("Action")
//            .setAction("Installation Completed")
//            .build());
//        Bundle bundle = new Bundle();
//        bundle.putString("Time", new Date().toString());
//        FirebaseAnalytics.getInstance(context).logEvent("Installation Completed", bundle);
    }catch (Exception ex){

    }

}
public static String getFirstopen(Context context){
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(firstopen,"0");

}

    public static   void forceRTLIfSupported(Activity activity)
    {
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        Log.e("lan", sharedPref.getString(lan_key, "-1"));

        if (sharedPref.getString(lan_key, "-1").equals("en")) {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        else if(sharedPref.getString(lan_key, "-1").equals("ar")){
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        else {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

    }


    static SharedPreferences sharedPreferences;

    public static void set_user_language(Context context,String user_id){
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(lan_key, user_id);
            editor.commit();
        }catch (Exception ex){

        }
    }
    public static String get_user_language1(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(lan_key,"ar");
    }
    public static void set_ad_id(Context context,String user_id){
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(add_key, user_id);
            editor.commit();
        }catch (Exception ex){

        }
    }
    public static String get_ad_id(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(add_key,"-1");
    }
    public static String get_user_language(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(lan_key,"ar");
    }

    public static String get_append(Context context){
//        try{
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        if(sharedPreferences==null)
//            return "";
//        if(sharedPreferences.getString(lan_key,"en").equals("ar"))
//        {
//            return "_ar";
//        }
//        else if(sharedPreferences.getString(lan_key,"en").equals("fr"))
//        {
//            return "_ar";
//        }

        return "_ar";
    }
    public static String is_first_get(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(is_first,"-1");
    }
    public static  void is_first_set(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(is_first,user_id);
        editor.commit();
    }
    public static String count_get(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("count","0");
    }
    public static  void count_set(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("count",user_id);
        editor.commit();
    }
    public static String get_user_id(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(user_key,"-1");
    }
    public static String get_user_name(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(user_name,"");
    }
    public static  void set_user_id(Context context,String user_id,String name){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(user_key,user_id);
        editor.putString(user_name,name);
        editor.commit();
    }
    public static String get_player_id(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(player_id,"-1");
    }
    public static  void set_player_id(Context context,String play_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(player_id,play_id);
        editor.commit();
    }
    public static String get_user_img(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(user_img,"-1");
    }
    public static  void set_user_img(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(user_img,user_id);
        editor.commit();
    }
    public static JSONObject get_user_language_words(Context context){

        JSONObject jsonObject = new JSONObject();
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            jsonObject = new JSONObject(sharedPreferences.getString(words_key,"-1"));
            jsonObject = jsonObject.getJSONObject(get_user_language(context));
        } catch (Exception e) {
            e.printStackTrace();
            return jsonObject;

        }
        return jsonObject;
    }

    public static String getword(Context context,String word)
    {

        try {
            JSONObject words = get_user_language_words(context);
            return words.getString(word);
        } catch (Exception e) {
            e.printStackTrace();

            return word;
        }
    }

    public static void set_user_language_words(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(words_key,user_id);
        editor.commit();
    }
    public static String get_gcmid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("gcm_id", "-1");
    }

    public static void set_gcmid(Context context, String gcm_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gcm_id",gcm_id);
        editor.commit();
    }

    public static void set_minimizetime(Context context){
        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("timer_key", date.getTime()).apply();
        editor.commit();
    }



    public static void get_minimizetime(Context context) {

        Date resume = new Date(System.currentTimeMillis());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Date pause = new Date(sharedPreferences.getLong("timer_key", 0));

        if(sharedPreferences.getLong("timer_key",0)!=0) {

            long resume_time = System.currentTimeMillis();
            long pause_time = sharedPreferences.getLong("timer_key", 0);
            long diff = resume_time-pause_time;
            Log.e("time_diff",String.valueOf(diff));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("timer_key", 0).apply();

            long twentyfiveminutes = 1*1000*60*25;

            Log.e("time_25",String.valueOf(twentyfiveminutes));

            if(diff>twentyfiveminutes){

            //    deleteCache(context);

                Intent i = context.getPackageManager()
                        .getLaunchIntentForPackage( context.getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);


            }

        }

    }


    public static boolean will_resume(Context context) {

        Date resume = new Date(System.currentTimeMillis());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Date pause = new Date(sharedPreferences.getLong("timer_key", 0));

        if(sharedPreferences.getLong("timer_key",0)!=0) {

            long resume_time = System.currentTimeMillis();
            long pause_time = sharedPreferences.getLong("timer_key", 0);
            long diff = resume_time-pause_time;
            Log.e("time_diff",String.valueOf(diff));

          //  SharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.putLong("timer_key", 0).apply();

            long twentyfiveminutes = 1*1000*60*25;

            Log.e("time_25",String.valueOf(twentyfiveminutes));

            if(diff>twentyfiveminutes){

                //    deleteCache(context);

                return true;

            }

        }
        return false;
    }



//    public static String getDeviceId(Context context){
//        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//        final String tmDevice, tmSerial, androidId;
//        tmDevice = "" + tm.getDeviceId();
//        tmSerial = "" + tm.getSimSerialNumber();
//        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        String deviceId = deviceUuid.toString();
//
//        return deviceId;
//    }
//(int) Calendar.getInstance().getTimeInMillis()

    public static void sendRegistrationToServer(Context context) {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String status,chanels,d_id;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.getBoolean("notify",true))
            status="true";
        else
            status="false";
        DatabaseHandler databaseHandler;
        databaseHandler=new DatabaseHandler(context);

        chanels=databaseHandler.notification_enabled_chanels();

        // TODO: Implement this method to send token to your app server.
        // http://clients.yellowsoft.in/naqsh/admin/send_notice.php?action=list
        String url = Session.NOTIFY_SERVER_URL+"android-token-register.php?";
        try {
            if(Session.get_user_id(context).equals("-1")) {
                url = url +
//                        "device_token=" + refreshedToken +
                        "&status=" + status + "&chanels=" + chanels+"&player_id="+Session.get_player_id(context);
            }else{
                url = url +
//                        "device_token=" + refreshedToken +
                        "&status=" + status + "&chanels=" + chanels+"&member_id="+Session.get_user_id(context)+"&player_id="+Session.get_player_id(context);
            }
            Log.e("urlllllll", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error",volleyError.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public static void sendChannelsToServer(Context context,String chanels) {
        String url;
//        String chanels;
//        final DatabaseHandler databaseHandler;
//        databaseHandler=new DatabaseHandler(context);
//        chanels=databaseHandler.notification_enabled_chanels();
        if(Session.get_user_id(context).equals("-1")) {
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + chanels;
        }else{
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + chanels+"&member_id="+Session.get_user_id(context);
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
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Chanel chanel=new Chanel(jsonObject,"0");
//                        if(!databaseHandler.is_following(chanel.ch_id)){
//                            databaseHandler.addPlaylist(chanel.ch_id,chanel.parent_id,"1");
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }


    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }


    public static void changeColortoNightmode(View view){
            view.setBackgroundColor(Color.parseColor("#000000"));
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    public static void changeColortoDaymode(View view){
            view.setBackgroundColor(Color.parseColor("#000000"));

            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    public static void setTheme(Context context, int theme) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(context.getString(R.string.prefs_theme_key), theme).apply();
    }

    public static boolean getTheme(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("night", false);

    }


    public static void insertpushnotification(Context context,String notification_id, NotificationManager notificationManager){
        ArrayList<String> notify_ids = getnotification_array(context);
    Log.e("notify_size",String.valueOf(notify_ids.size()));
        if(notify_ids.size()==4){
            try {
                notificationManager.cancel(Integer.parseInt(notify_ids.get(0)));
            }catch (Exception ex){
                notificationManager.cancelAll();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String notify_string = "";
                editor.putString(notifications_key,notify_string);
            }

            notify_ids.remove(0);
        }
        notify_ids.add(notification_id);
        notification_array(context,notify_ids);
    }

    public static void notification_array(Context context,ArrayList<String> notifyids){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String notify_string = "";

        for(int i=0;i<notifyids.size();i++){

            if(i==0)
                notify_string=notifyids.get(i);
            else
                notify_string=notify_string+"@"+notifyids.get(i);
        }
        editor.putString(notifications_key,notify_string);
        editor.commit();

    }

    public static  ArrayList<String> getnotification_array(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String notify_string = "";
        notify_string =  sharedPreferences.getString(notifications_key,"");
        ArrayList<String> notify_array = new ArrayList<>();
        if(notify_string.equals("")){
            return notify_array;
        }
        String[] notify_spit = notify_string.split("@");
        for(int i=0;i<notify_spit.length;i++){
            notify_array.add(notify_spit[i]);
        }
        return notify_array;
    }


    public static void set_news_feed_id(Context context,String feed_id){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(news_id,feed_id);
        editor.commit();


    }

    public static String get_news_feed_id(Context context){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(news_id,"0");

    }




}
