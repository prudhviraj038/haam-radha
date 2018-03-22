package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.onesignal.OneSignal;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 8/5/2016.kkkll
 */
public class SettingsActivity extends Activity {
    SharedPreferences sharedPreferences;
    Switch notification_switch,sound_switch;
    String title,title_ar,title_fr,logo,email,phone,play_store,facebook,twitter,instagram,share_text;
    LinearLayout change_fontsize,contact_us_link,
            facebook_link,twitter_link,instagram_link,rating_link,add_source_link,report_bug_link,select_cat,
    whatsup_share,facebook_share,twitter_share,instagram_share,google_share,linkin_share,email_share,sms_share,
    l_ll,s_ll,m_ll,x_ll;
    MyTextView l_tv,m_tv,s_tv,x_tv,test_text;
    ImageView l_im,s_im,m_im,x_im;
    ViewFlipper viewFlipper;
    MyTextView settings_head_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.haam_settings_activity);
        get_details();
        settings_head_tv=(MyTextView)findViewById(R.id.settings_head_tv);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        ImageView back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onBackPressed();
            }
        });


        notification_switch = (Switch) findViewById(R.id.notification_switch);
        sound_switch = (Switch) findViewById(R.id.sound_switch);

        select_cat = (LinearLayout) findViewById(R.id.select_sources_for_notify);
        change_fontsize = (LinearLayout) findViewById(R.id.font_size_btn);
        contact_us_link = (LinearLayout) findViewById(R.id.contact_us);
        facebook_link= (LinearLayout) findViewById(R.id.facebook_link);
        twitter_link= (LinearLayout) findViewById(R.id.twitter_link);
        instagram_link= (LinearLayout) findViewById(R.id.instagram_link);
//        rating_link= (LinearLayout) findViewById(R.id.rating_link);
        add_source_link= (LinearLayout) findViewById(R.id.add_source_link);
//        report_bug_link= (LinearLayout) findViewById(R.id.report_bug_link);
        whatsup_share= (LinearLayout) findViewById(R.id.whatsup_share);
        facebook_share= (LinearLayout) findViewById(R.id.facebook_share);
        twitter_share= (LinearLayout) findViewById(R.id.twitter_share);
        instagram_share= (LinearLayout) findViewById(R.id.instagram_share);
        google_share= (LinearLayout) findViewById(R.id.google_share);
        linkin_share= (LinearLayout) findViewById(R.id.linkdin_share);
        email_share= (LinearLayout) findViewById(R.id.email_share);
        sms_share= (LinearLayout) findViewById(R.id.sms_share);


        settings_head_tv.setText(Settings.getword(SettingsActivity.this,"Settings"));
        MyTextView settings_tv=(MyTextView)findViewById(R.id.settings_tv);
        settings_tv.setText(Settings.getword(SettingsActivity.this,"Settings").toUpperCase());
//        MyTextView change_lan_tv=(MyTextView)findViewById(R.id.change_lang_tv);
//        change_lan_tv.setText(Settings.getword(SettingsActivity.this,"title_select_sources"));
        MyTextView urgent_news_tv=(MyTextView)findViewById(R.id.urgent_news_tv);
        urgent_news_tv.setText(Settings.getword(SettingsActivity.this,"Urgent News"));
        MyTextView sound_tv=(MyTextView)findViewById(R.id.notify_tv);
        sound_tv.setText(Settings.getword(SettingsActivity.this, "sound_notifications"));
        MyTextView select=(MyTextView)findViewById(R.id.select_sources_tv);
        select.setText(Settings.getword(SettingsActivity.this,"Select Categories to get Urgent"));

        MyTextView naqsh_app=(MyTextView)findViewById(R.id.app_tv);
        naqsh_app.setText(Settings.getword(SettingsActivity.this,"Ham App").toUpperCase());

        MyTextView share_tv=(MyTextView)findViewById(R.id.share_tv);
        share_tv.setText(Settings.getword(SettingsActivity.this,"Share Ham App").toUpperCase());

        MyTextView contact_tv=(MyTextView)findViewById(R.id.contact_us_tv);
        contact_tv.setText(Settings.getword(SettingsActivity.this,"Contact Us"));
        MyTextView facebook_tv=(MyTextView)findViewById(R.id.facebook_tv);
        facebook_tv.setText(Settings.getword(SettingsActivity.this,"Facebook"));
        MyTextView twitter_tv=(MyTextView)findViewById(R.id.twitter_tv);
        twitter_tv.setText(Settings.getword(SettingsActivity.this,"Twitter"));
        MyTextView instagram_tv=(MyTextView)findViewById(R.id.instagram_tv);
        instagram_tv.setText(Settings.getword(SettingsActivity.this,"Instagram"));
//        MyTextView rating_tv=(MyTextView)findViewById(R.id.rating_tv);
//        rating_tv.setText(Settings.getword(SettingsActivity.this,"rate_nashrapp"));
//        MyTextView source_tv=(MyTextView)findViewById(R.id.addsource_tv);
//        source_tv.setText(Settings.getword(SettingsActivity.this,"suggest_add_source"));
        MyTextView report_tv=(MyTextView)findViewById(R.id.bug_tv);
        report_tv.setText(Settings.getword(SettingsActivity.this,"Report Bugs"));

        MyTextView watsapp_tv=(MyTextView)findViewById(R.id.watspp_tv);
        watsapp_tv.setText(Settings.getword(SettingsActivity.this,"Whatsapp"));
        MyTextView facebook_share_tv=(MyTextView)findViewById(R.id.facebook_share_tv);
        facebook_share_tv.setText(Settings.getword(SettingsActivity.this,"Facebook"));
        MyTextView twitter_share_tv=(MyTextView)findViewById(R.id.twitter_share_tv);
        twitter_share_tv.setText(Settings.getword(SettingsActivity.this,"Twitter"));
        MyTextView instagram_share_tv=(MyTextView)findViewById(R.id.instagram_share_tv);
        instagram_share_tv.setText(Settings.getword(SettingsActivity.this,"Instagram"));
        MyTextView google_share_tv=(MyTextView)findViewById(R.id.google_share_tv);
//        google_share_tv.setText(Settings.getword(SettingsActivity.this,"google_plus"));
        MyTextView linked_share_tv=(MyTextView)findViewById(R.id.linked_share_tv);
//        linked_share_tv.setText(Settings.getword(SettingsActivity.this,"linked_in"));
        MyTextView email_share_tv=(MyTextView)findViewById(R.id.email_share_tv);
        email_share_tv.setText(Settings.getword(SettingsActivity.this,"Email"));
        MyTextView sms_share_tv=(MyTextView)findViewById(R.id.sms_share_tv);
//        sms_share_tv.setText(Settings.getword(SettingsActivity.this,"sms"));

        select_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, SelectedCategoriesActivity.class);
                startActivity(intent);

            }
        });

//        change_fontsize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewFlipper.setDisplayedChild(1);
//                settings_head_tv.setText(Settings.getword(SettingsActivity.this, "change_font"));
//            }
//        });
//        int font_size = 17;
//        try {
//            font_size = Integer.parseInt(sharedPreferences.getString("font_sizee", String.valueOf("15")));
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }

//        s_im.setVisibility(View.GONE);
//        m_im.setVisibility(View.VISIBLE);
//        l_im.setVisibility(View.GONE);
//        x_im.setVisibility(View.GONE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("font_sizee", 15);
//        editor.commit();
//        test_text.setTextSize(15);


//        s_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                s_im.setVisibility(View.VISIBLE);
//                m_im.setVisibility(View.GONE);
//                l_im.setVisibility(View.GONE);
//                x_im.setVisibility(View.GONE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("font_sizee", 15);
//                editor.commit();
//                test_text.setTextSize(15);
//            }
//        });
//        m_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                s_im.setVisibility(View.GONE);
//                m_im.setVisibility(View.VISIBLE);
//                l_im.setVisibility(View.GONE);
//                x_im.setVisibility(View.GONE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("font_sizee", 20);
//                editor.commit();
//                test_text.setTextSize(20);
//            }
//        });
//        l_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                s_im.setVisibility(View.GONE);
//                m_im.setVisibility(View.GONE);
//                l_im.setVisibility(View.VISIBLE);
//                x_im.setVisibility(View.GONE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("font_sizee", 25);
//                editor.commit();
//                test_text.setTextSize(25);
//            }
//        });
//        x_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                s_im.setVisibility(View.GONE);
//                m_im.setVisibility(View.GONE);
//                l_im.setVisibility(View.GONE);
//                x_im.setVisibility(View.VISIBLE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("font_sizee", 30);
//                editor.commit();
//                test_text.setTextSize(30);
//            }
//        });
//
//        change_language.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                show_language_popup();
//                mCallBack.source();
//            }
//        });

        notification_switch.setChecked(sharedPreferences.getBoolean("notify", true));
        if (notification_switch.isChecked()) {
//                    select_sources_tv.setTextColor(news_item_ti);
            OneSignal.sendTag("notification_on","1");

        } else {
//                    select_sources_tv.setTextColor(news_item_fade_ti);
            OneSignal.sendTag("notification_on","0");
        }
        sound_switch.setChecked(sharedPreferences.getBoolean("sound", false));
        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notify", isChecked);
                editor.commit();
//                Session.sendRegistrationToServer(getActivity());
                if (notification_switch.isChecked()) {
//                    select_sources_tv.setTextColor(news_item_ti);
                    OneSignal.sendTag("notification_on","1");

                } else {
//                    select_sources_tv.setTextColor(news_item_fade_ti);
                    OneSignal.sendTag("notification_on","0");
                }


            }
        });
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("sound", isChecked);
                editor.commit();

            }
        });
        contact_us_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/html");
                final PackageManager pm = SettingsActivity.this.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
                String className = null;
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                        className = info.activityInfo.name;

                        if(className != null && !className.isEmpty()){
                            break;
                        }
                    }
                }
                emailIntent.setClassName("com.google.android.gm", className);
                emailIntent.setData(Uri.parse(email));
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Settings.getword(SettingsActivity.this, "Report Bug"));
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException ex) {
                    // handle error
                }
            }
        });

        facebook_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
                startActivity(browserIntent);
            }catch (Exception exception){
                Toast.makeText(SettingsActivity.this, Settings.getword(SettingsActivity.this,"invalid url"), Toast.LENGTH_SHORT).show();
            }
            }
        });

        twitter_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                startActivity(browserIntent);
            }catch (Exception exception){
                Toast.makeText(SettingsActivity.this,Settings.getword(SettingsActivity.this,"invalid url"), Toast.LENGTH_SHORT).show();
            }
            }
        });
        instagram_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram));
                startActivity(browserIntent);
                }catch (Exception exception){
                    Toast.makeText(SettingsActivity.this,Settings.getword(SettingsActivity.this,"invalid url"), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        rating_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome"));
//                //startActivity(browserIntent);
//              //  https://play.google.com/store/apps/details?id=com.android.chrome
//
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.android.chrome")));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.android.chrome")));
//                }
//            }
//        });

        add_source_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent mainIntent = new Intent(SettingsActivity.this, ReportActivity.class);
//                startActivity(mainIntent);
                contact_us_link.performClick();
            }
        });

//        report_bug_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        facebook_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("face");
            }
        });
        whatsup_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("what");
            }
        });

        twitter_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("twit");
            }
        });

        instagram_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = null;
                try {
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(SettingsActivity.this.getContentResolver(),
                            BitmapFactory.decodeResource(getResources(), R.drawable.haam_ham_logo), null, null));
                } catch (NullPointerException e) {
                }

               startActivity(createInstagramIntent(imageUri)); ;
            }
        });

        google_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("com.google.android.apps.plus");
            }
        });

        linkin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("com.linkedin.android");
            }
        });

        email_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("com.google.android.gm");
            }
        });

        sms_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareusingsms();
            }
        });

//        if(font_size==10)
//            s_ll.performClick();
//        else if(font_size==15)
//            m_ll.performClick();
//        else if(font_size==20)
//            l_ll.performClick();
//        else if(font_size==25)
//            x_ll.performClick();
//        else
//            m_ll.performClick();
//

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
       // AppController.getInstance().getDefaultTracker().setScreenName("SETTINGS SCREEN");
        //AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void shareFacebook() {
        String fullUrl = "https://m.facebook.com/sharer.php?u=..";
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setClassName("com.facebook.katana",
                    "com.facebook.katana.ShareLinkActivity");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "your title text");
            startActivity(sharingIntent);

        } catch (Exception e) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(fullUrl));
            startActivity(i);

        }
    }
    private Intent createInstagramIntent(Uri uriString) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriString);
        shareIntent.setPackage("com.instagram.android");
        return shareIntent;
    }
    private void shareusingsms(){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "12125551212");
        smsIntent.putExtra("sms_body",play_store);
        startActivity(smsIntent);
    }

    public void onShareClick(String appname) {
        Resources resources = getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(play_store));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        emailIntent.setType("message/rfc822");

        PackageManager pm = SettingsActivity.this.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, "share this message");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if(packageName.contains("twitter") ||
                    packageName.contains("facebook") ||
                    packageName.contains("what") ||
                    packageName.contains("mms") ||
                    packageName.contains("instagram") ||
                    packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if(packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT,play_store);
                } else if(packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                } else if(packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                } else if(packageName.contains("instagram")) {
//                    Uri imageUri = null;
//                    try {
//                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(SettingsActivity.this.getContentResolver(),
//                                BitmapFactory.decodeResource(getResources(), R.drawable.app_icon), null, null));
//
//                    } catch (NullPointerException e) {
//                    }
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
//                    Picasso.with(SettingsActivity.this).load(logo).into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                            Intent i = new Intent(Intent.ACTION_SEND);
//                            i.setType("image/*");
//                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
//                            i.putExtra(Intent.EXTRA_SUBJECT, html2text(title));
//                            i.putExtra(Intent.EXTRA_TEXT, html2text(share_text));
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.setPackage("com.instagram.android");
//                            try {
//                                startActivity(i);
//                            } catch (Exception e) {
//
//                                try {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
//                                } catch (android.content.ActivityNotFoundException anfe) {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
//                                }
//                            }
//
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable errorDrawable) {
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//                        }
//                    });

                }else if(packageName.contains("what")) {
//                    Uri imageUri = null;
//                    try {
//                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(SettingsActivity.this.getContentResolver(),
//                                BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon), null, null));
//                    } catch (NullPointerException e) {
//                    }
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_STREAM,imageUri);
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                    intent.putExtra(Intent.EXTRA_SUBJECT, title);
                    intent.setType("message/rfc822");
                }

                if(packageName.contains(appname)) {
                    startActivity(intent);
                    return;
                }
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);


    }
    public static String html2text(String html) {
        // return Jsoup.parse(html).text();
        return html;
    }
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(SettingsActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    public void get_details(){
        String url = null;
        try {
            url = Settings.SERVERURL+"settings.php";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage(Settings.getword(SettingsActivity.this,"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                progressDialog.dismiss();
                try {
                    title=jsonObject.getString("title");
                    logo=jsonObject.getString("logo");
                    email=jsonObject.getString("email");
                    phone=jsonObject.getString("phone");
                    play_store=jsonObject.getString("playstore_link");
                    facebook=jsonObject.getString("facebook");
                    twitter=jsonObject.getString("twitter");
                    instagram=jsonObject.getString("instagram");
//                    share_text=jsonObject.getString("share_str");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(SettingsActivity.this,Settings.getword(SettingsActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }


}
