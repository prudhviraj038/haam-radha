package com.mamacgroup.hamtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.CustomEvent;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.CLIPBOARD_SERVICE;

//import com.bumptech.glide.Glide;


/**
 * Created by HP on 7/26/2016.
 */
public class PushNotificationNewsDetaiActivity extends BaseActivity {
    SharedPreferences sharedPreferences;
    CrystalSeekbar seekBarCompat;
    News news;

    private ListView mDrawerList1;
    TextView normal,bold,urgent,pop_up_label;
    ImageView font_adjust;
    LinearLayout share_ll;
    ImageView instagram_share,facebook_share,twitter_share,email_share,watsapp_share,ins_share,face_share,twi_share,wat_share
            ,gplus_share,lin_share,sms_share,ema_share,close,close_slider,video_hint;
    LinearLayout font_adjust_view;
    LinearLayout detail_scroll,read_ll;
    RelativeLayout news_img_layout;
    String feed_id="0";
    Float X;
    Float Y;
    ViewFlipper vf;
    private LinearLayout mDemoSliderLayout;
    SliderLayout mDemoSlider;
    TextView time,time2,time3,read_tv;
    TextView font_size_heading,font_style_heading;
    boolean loaded=false;
 //   private FirebaseAnalytics mFirebaseAnalytics;
    boolean save_preferences = false;
    Switch night_switch;
    LinearLayout header_tab ;
    String DEBUG_TAG = "debug";
    ImageView imageView;

    private void recordImageView() {
        String id =  news.id;
        String name = news.title;

        // [START image_view_event]
        Bundle bundle = new Bundle();
       // bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        //bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "detail");
        //mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // [END image_view_event]
    }
    Typeface tf_normal,tf_bold;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Session.forceRTLIfSupported(this);
        setContentView(R.layout.newsdetailfragment);

        header_tab = (LinearLayout) findViewById(R.id.header);


        save_preferences = false;

         tf_normal = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueLTArabic-Roman.ttf");
         tf_bold = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueLTArabic-Bold.ttf");


       // mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        vf=(ViewFlipper)findViewById(R.id.viewFlipper5);
        night_switch = (Switch) findViewById(R.id.night_switch);
        night_switch.setChecked(sharedPreferences.getBoolean("night", false));
        night_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night", isChecked);
                editor.commit();

                Session.set_news_feed_id(PushNotificationNewsDetaiActivity.this,feed_id);

                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });

        TextView night_tv=(TextView)findViewById(R.id.night_tv);
        night_tv.setText(Session.getword(this, "night_mode"));



        // mCallBack.setselected(getArguments().getString("parent"));

        feed_id = getIntent().getStringExtra("feed_id");
        header_tab.post(new Runnable() {
            @Override
            public void run() {
                Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                header_height = header_tab.getHeight();
                //height is ready
            }
        });
        get_news();
    }


        private void display_dat() {
//            vf.setDisplayedChild(0);
            ArrayList<Integer> prgmImages = new ArrayList<>();
            ArrayList<String> prgmTitles = new ArrayList<>();

            if(!news.link.equals(""))
            prgmImages.add(R.drawable.open_in_browser);

            prgmImages.add(R.drawable.copy_link_icon);
            prgmImages.add(R.drawable.flag_icon);
            prgmImages.add(R.drawable.three_dots);
            mDemoSliderLayout = (LinearLayout) findViewById(R.id.img_slider);
            mDemoSlider = new SliderLayout(this);
            mDemoSlider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mDemoSliderLayout.addView(mDemoSlider);
            if(!news.link.equals(""))
            prgmTitles.add(Session.getword(this,"open_in_browser"));

            prgmTitles.add(Session.getword(this,"copy_link"));
            prgmTitles.add(Session.getword(this, "title_report_abuse"));
            prgmTitles.add(Session.getword(this,"show_more"));

            pop_up_label  = (TextView) findViewById(R.id.pop_up_label);
            pop_up_label.setText(Session.getword(this, "share_news"));
            share_ll = (LinearLayout) findViewById(R.id.share_ll);


      /*  if(share_ll.getVisibility()==View.VISIBLE)
            share_ll.setVisibility(View.GONE);
        else
            share_ll.setVisibility(View.VISIBLE);

      */
            read_tv = (TextView) findViewById(R.id.read_more_tv);
            read_tv.setText(Session.getword(this, "click here for more details"));
            read_ll = (LinearLayout) findViewById(R.id.read_more_ll);
            news_img_layout = (RelativeLayout) findViewById(R.id.news_img_layout);
            video_hint = (ImageView) findViewById(R.id.video_hint);
            instagram_share = (ImageView) findViewById(R.id.instagram_share);
            facebook_share = (ImageView) findViewById(R.id.facebook_share);
            twitter_share = (ImageView) findViewById(R.id.twitter_share);
            email_share = (ImageView) findViewById(R.id.email_share);
            watsapp_share = (ImageView) findViewById(R.id.watsapp_share);

            ins_share = (ImageView) findViewById(R.id.ins_share);
            ins_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    instagram_share.performClick();
                }
            });
            face_share = (ImageView) findViewById(R.id.face_share);
            face_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    facebook_share.performClick();
                }
            });
            twi_share = (ImageView) findViewById(R.id.twi_share);
            twi_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    twitter_share.performClick();
                }
            });
            ema_share = (ImageView) findViewById(R.id.ema_share);
            ema_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email_share.performClick();
                }
            });
            wat_share = (ImageView) findViewById(R.id.wat_share);
            wat_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watsapp_share.performClick();
                }
            });
//            gplus_share = (ImageView) findViewById(R.id.gplus_share);
//            gplus_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//            lin_share = (ImageView) findViewById(R.id.lin_share);
//            lin_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
            sms_share = (ImageView) findViewById(R.id.ssms_share);
            sms_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String smsBody=html2text(news.whatsapp_str);
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("sms_body", smsBody);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);


                }
            });


            close = (ImageView) findViewById(R.id.close_img);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    share_ll.setVisibility(View.GONE);
                }
            });

            instagram_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final ProgressDialog progressDialog = new ProgressDialog(PushNotificationNewsDetaiActivity.this);
                    progressDialog.setMessage(Session.getword(PushNotificationNewsDetaiActivity.this,"please_wait"));
                    progressDialog.show();

                   // progressDialog.setCancelable(false);

                    Picasso.with(PushNotificationNewsDetaiActivity.this).load(news.insta_img).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            if (progressDialog != null)
                                progressDialog.dismiss();

                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("image/*");
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                            i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
                            i.putExtra(Intent.EXTRA_TEXT, html2text(news.link));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setPackage("com.instagram.android");
                            try {
                                startActivity(i);
                            } catch (Exception e) {

                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
                                }
                            }

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            if (progressDialog != null)
                                progressDialog.dismiss();

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

//                            if(progressDialog!=null)
//                                progressDialog.dismiss();

                        }
                    });
                }
            });

            facebook_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    //     i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                    //     i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
                    i.putExtra(Intent.EXTRA_TEXT, html2text(news.facebook_str));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.facebook.katana");
                    try {
                        startActivity(i);
                    } catch (Exception e) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.facebook.katana")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.facebook.katana")));
                        }
                    }
                }
            });

            twitter_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    //     i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                    //     i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
                    i.putExtra(Intent.EXTRA_TEXT, html2text(news.twitter_str));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.twitter.android");
                    try {
                        startActivity(i);
                    } catch (Exception e) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.twitter.android")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.twitter.android")));
                        }
                    }

                }
            });

            email_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
                    i.putExtra(Intent.EXTRA_TEXT, html2text(news.mail_str));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.google.android.gm");
                    try {
                        startActivity(i);
                    } catch (Exception e) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.android.gm")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.gm")));
                        }
                    }

                                    }
            }) ;
            watsapp_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
                            i.putExtra(Intent.EXTRA_TEXT, html2text(news.whatsapp_str));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setPackage("com.whatsapp");
                            try {
                                startActivity(i);
                            } catch (Exception e) {

                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.whatsapp")));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.whatsapp")));
                                }
                            }



                }
            });




            seekBarCompat = (CrystalSeekbar) findViewById(R.id.materialSeekBar);
            seekBarCompat.setMaxValue(0);
            seekBarCompat.setMaxValue(20);
            seekBarCompat.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number value) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("font_size_m", 20 + value.intValue());
                    Log.e("text_size_slider",String.valueOf(20 + value.intValue()));
                    editor.apply();

                }
            });
            font_size_heading = (TextView) findViewById(R.id.fontsize_heading);
            font_size_heading.setText(Session.getword(this,"change_font_size"));

            font_style_heading = (TextView) findViewById(R.id.font_style_heading);
            font_style_heading.setText(Session.getword(this,"change_font_style"));


            final TextView description = (TextView) findViewById(R.id.news_description_big);


            seekBarCompat.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue) {
                    description.setTextSize(20 + minValue.intValue());
                }
            });


//            seekBarCompat.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                    description.setTextSize(18 + progress);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putInt("font_size", 18 + progress);
//                    editor.commit();
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//
//                }
//            });
            detail_scroll = (LinearLayout)findViewById(R.id.detail_scroll);

//            detail_scroll.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    int action = MotionEventCompat.getActionMasked(event);
//
//                    switch (action) {
//                        case (MotionEvent.ACTION_DOWN):
//                            Log.d(DEBUG_TAG, "Action was DOWN");
//                            X = event.getX();
//                            Y = event.getY();
//                            return true;
//                        case (MotionEvent.ACTION_MOVE):
//                            Log.d(DEBUG_TAG, "Action was MOVE");
//                            return true;
//                        case (MotionEvent.ACTION_UP):
//                            Log.d(DEBUG_TAG, "Action was UP");
//
//                            if (Math.abs(Y - event.getY()) <= 50)
//                                if (X < event.getX()) {
//                                    Log.d(DEBUG_TAG, "Action was RIGHT");
//                                    onPause();
//                                    mCallBack.back();
//                                } else if (X != event.getX()) {
//                                    //  imageView.performClick();
//                                    Log.d(DEBUG_TAG, "Action was LEFT");
//                                }
//                            return true;
//                        case (MotionEvent.ACTION_CANCEL):
//                            Log.d(DEBUG_TAG, "Action was CANCEL");
//                            return true;
//                        case (MotionEvent.ACTION_OUTSIDE):
//                            Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
//                                    "of current screen element");
//                            return true;
//                        default:
//                            return false;
//                    }
//                }
//            });

            imageView = (ImageView)findViewById(R.id.news_img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(video_hint.getVisibility()==View.VISIBLE ){

                        video_hint.performClick();
                    }else{
                        Intent intent = new Intent(PushNotificationNewsDetaiActivity.this, ImageZoomActivity.class);
                        intent.putExtra("url", news.image);
                        intent.putExtra("title", news.title);
                        startActivity(intent);

                    }

                }
            });

//            imageView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    int action = MotionEventCompat.getActionMasked(event);
//
//                    switch (action) {
//                        case (MotionEvent.ACTION_DOWN):
//                            Log.d(DEBUG_TAG, "Action was DOWN");
//                            X = event.getX();
//                            Y = event.getY();
//                            return true;
//                        case (MotionEvent.ACTION_MOVE):
//                            Log.d(DEBUG_TAG, "Action was MOVE");
//                            return true;
//                        case (MotionEvent.ACTION_UP):
//                            Log.d(DEBUG_TAG, "Action was UP");
//                            if (Math.abs(Y - event.getY()) <= 50)
//                                if (X < event.getX()) {
//                                    Log.d(DEBUG_TAG, "Action was RIGHT");
//                                    onPause();
//                                    mCallBack.back();
//                                } else {
//                                    //  imageView.performClick();
//                                    Intent intent = new Intent(this, ImageZoomActivity.class);
//                                    intent.putExtra("url", news.image);
//                                    intent.putExtra("title", news.title);
//                                    startActivity(intent);
//
//                                    Log.d(DEBUG_TAG, "Action was LEFT");
//                                }
//                            return true;
//                        case (MotionEvent.ACTION_CANCEL):
//                            Log.d(DEBUG_TAG, "Action was CANCEL");
//                            return true;
//                        case (MotionEvent.ACTION_OUTSIDE):
//                            Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
//                                    "of current screen element");
//                            return true;
//                        default:
//                            return false;
//                    }
//                }
//            });

            normal = (TextView) findViewById(R.id.text_normal);
            normal.setText(Session.getword(this,"normal"));
            bold = (TextView) findViewById(R.id.text_bold);
            bold.setText(Session.getword(this,"bold"));
            font_adjust_view = (LinearLayout) findViewById(R.id.font_adjust_view);
            font_adjust_view.setVisibility(View.GONE);
            font_adjust = (ImageView) findViewById(R.id.font_adjust);
            font_adjust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (font_adjust_view.getVisibility() == View.GONE)
                        font_adjust_view.setVisibility(View.VISIBLE);
                    else
                        font_adjust_view.setVisibility(View.GONE);
                }
            });

            font_adjust_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normal.setTextColor(Color.parseColor("#ffffff"));
                    bold.setTextColor(PushNotificationNewsDetaiActivity.this.getResources().getColor(R.color.aa_app_blue));
                    normal.setBackgroundResource(R.drawable.border_full_appcolor_font);
                    bold.setBackgroundResource(R.drawable.border_empty_appcolor_font);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("font_style", "normal");
                    editor.commit();
                    description.setTypeface(tf_normal);
                }
            });

            bold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bold.setTextColor(Color.parseColor("#ffffff"));
                    normal.setTextColor(PushNotificationNewsDetaiActivity.this.getResources().getColor(R.color.aa_app_blue));
                    bold.setBackgroundResource(R.drawable.border_full_appcolor_font);
                    normal.setBackgroundResource(R.drawable.border_empty_appcolor_font);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("font_style", "bold");
                    editor.commit();
                    description.setTypeface(tf_bold);

                }
            });
            if (sharedPreferences.getString("font_style", "-1").equals("bold")) {
                bold.performClick();
            }else{
                normal.performClick();
            }

            final ImageView world = (ImageView)findViewById(R.id.world_details);

            world.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webview_activity = new Intent(PushNotificationNewsDetaiActivity.this,WebviewActivity.class);
                    webview_activity.putExtra("link",news.link);
                 //   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.link));
                    startActivity(webview_activity);
                }
            });

            if (news.link.equals("")) {

                read_ll.setVisibility(View.GONE);
                world.setVisibility(View.GONE);

            } else {
                read_ll.setVisibility(View.VISIBLE);
                world.setVisibility(View.VISIBLE);

            }
            read_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    world.performClick();
                }
            });


            ImageView share_btn = (ImageView)findViewById(R.id.share_btn_detail);
            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    share_ll.setVisibility(View.VISIBLE);

                }
            });
            TextView ch_title = (TextView)findViewById(R.id.news_title);
            ch_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mCallBack.chanel_selected(news.chanels);

                }
            });

            ch_title.setText(Html.fromHtml(news.chanels.get_ch_title(this)));
            ImageView ch_imageView = (ImageView)findViewById(R.id.logo);
            ch_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                        mCallBack.chanel_selected(news.chanels);
                }
            });


            Picasso.with(this).load(news.chanels.ch_image).into(ch_imageView);

            TextView tittle = (TextView) findViewById(R.id.news_descri);
            tittle.setText(Html.fromHtml(news.title));
            int default_size = sharedPreferences.getInt("font_size_m", 20);
            Log.e("text_size",String.valueOf(default_size));
            seekBarCompat.setMinStartValue(default_size-20).apply();
            description.setTextSize(default_size);
            description.setText(Html.fromHtml(news.data));


            LinearLayout is_urgent = (LinearLayout) findViewById(R.id.is_urgent);
            urgent = (TextView)findViewById(R.id.urgent_text);
            urgent.setText(Session.getword(this, "urgent"));
            time = (TextView)findViewById(R.id.news_time);
            time.setText(news.get_time(this));

            time2 = (TextView)findViewById(R.id.news_time22);
            time2.setText(news.get_time(this));

            time3 = (TextView)findViewById(R.id.news_time3);
            time3.setText(news.now);

            if (news.is_urgent.equals("1")) {
                is_urgent.setVisibility(View.VISIBLE);
                time.setVisibility(View.GONE);
                time2.setVisibility(View.VISIBLE);
            } else {
                is_urgent.setVisibility(View.GONE);
                time2.setVisibility(View.GONE);
                time.setVisibility(View.VISIBLE);

            }

            for (int i = 0; i < news.img.size(); i++) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(this);
                defaultSliderView.image(news.img.get(i)).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView baseSliderView) {
                        Intent intent=new Intent(PushNotificationNewsDetaiActivity.this,SlidingActivity.class);
                        intent.putStringArrayListExtra("images", news.img);
                        startActivity(intent);

                    }
                });
                mDemoSlider.addSlider(defaultSliderView);
            }

            if (news.img.size() == 0) {
                news_img_layout.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
//                mDemoSlider.setVisibility(View.GONE);
            } else if (news.img.size() == 1) {
                news_img_layout.setVisibility(View.VISIBLE);

                imageView.setVisibility(View.VISIBLE);
                //ImageLoader imageLoader = CustomVolleyRequest.getInstance(this)
                  //      .getImageLoader();
                //imageLoader.get(news.image, ImageLoader.getImageListener(imageView,
                  //      R.drawable.loading, R.drawable
                    //            .app_icon));

                //  Glide.with(this).load(news.image).into(imageView);
                //mDemoSlider.setVisibility(View.GONE);
                try {
                    Picasso.with(this).load(news.image).placeholder(R.drawable.loading).noFade().into(imageView);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
//                mDemoSlider.setVisibility(View.GONE);

            } else {
                news_img_layout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
//                mDemoSlider.setVisibility(View.VISIBLE);
            }

            if (news.video.equals("") && news.mp4.equals("")) {
                video_hint.setVisibility(View.GONE);

            } else {
                video_hint.setVisibility(View.VISIBLE);
            }


            video_hint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!news.mp4.equals("")) {

                        Intent intent = new Intent(PushNotificationNewsDetaiActivity.this, AndroidVideoPlayerActivity.class);
                        intent.putExtra("video", news.mp4);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(PushNotificationNewsDetaiActivity.this, YoutubePlayer.class);
                        intent.putExtra("video", news.video);
                        startActivity(intent);
                    }

                }
            });

            final ImageView back_img = (ImageView)findViewById(R.id.back_img);
            back_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PushNotificationNewsDetaiActivity.this, NewsRecycleListActivity.class);
                    intent.putExtra("feed_id", "0");
                    intent.putExtra("login_check","1");
                    startActivity(intent);
                    finish();
                }
            });
            

            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            if(databaseHandler.news_opened(news.id).equals("0")){
                databaseHandler.addnews(news.id,news.id);
            }
            else {
                Log.e("alredy", "vived");
            }

            update_views();



        }



    String convertDate(String inputDate) {
        //2016-08-03 04:00:09
        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;

        try {
            date = theDateFormat.parse(inputDate);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        theDateFormat = new SimpleDateFormat("dd MMM ");

        return theDateFormat.format(date);
    }




    private String get_different_dates(String date) {
        String temp = "Now";


        long seconds = Long.parseLong(date);
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;


        if(days == 0) {
            if(hours==0) {
                temp = String.valueOf(minutes) + (minutes <= 1 ? " minute" : " minutes");
            }
            else
                temp = String.valueOf(hours) + (hours == 1 ?" hour":" hours");
        }
        else if(days<7)
            temp = days<=1? "1 day": String.valueOf(days)+" days";
        else if(days < 365)
            temp = String.valueOf(days/7) + (days/7==1?" week":" weeks");
        else if(days < 365)
            temp = String.valueOf(days/30) + (days/30==1?" month":" months");
        else
            temp = String.valueOf(days/365) + (days/365==1?" year":" years");
        return temp + " Ago";

    }




    @Override
    public void onPause() {
        super.onPause();

//        try {
//            Class.forName("android.webkit.WebView")
//                    .getMethod("onPause", (Class[]) null)
//                    .invoke(webview, (Object[]) null);
//
//        } catch(ClassNotFoundException cnfe) {
//
//        } catch(NoSuchMethodException nsme) {
//
//        } catch(InvocationTargetException ite) {
//
//        } catch (IllegalAccessException iae) {
//
//        }



    }

    public static String html2text(String html) {
       // return Jsoup.parse(html).text();
        return html;
    }
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void get_news(){
        vf.setDisplayedChild(1);
        String url = Session.SERVER_URL+"feeds-new3.php?feed_id="+feed_id;
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Session.getword(this,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
               if(progressDialog!=null)
                    progressDialog.dismiss();


//                 progressBar.setVisibility(View.GONE);


                if(jsonArray.length()==0){

                    Log.e("haam_test","haam_test");
                    Intent intent = new Intent(PushNotificationNewsDetaiActivity.this, NewsRecycleListActivity.class);
                    intent.putExtra("feed_id", "0");
                    intent.putExtra("login_check","1");
                    startActivity(intent);
                    finish();

                }else{
                    vf.setDisplayedChild(0);
                    for(int i=0;i<jsonArray.length();i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        news = new News(jsonObject,PushNotificationNewsDetaiActivity.this);

                        display_dat();
                    } catch (Exception e) {
//                        news_list_adapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }
                }
        }}}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog!=null)
                    progressDialog.dismiss();

//                progressBar.setVisibility(View.GONE);
                Log.e("error", volleyError.toString());
             //   Toast.makeText(this,volleyError.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }



    private void update_views(){

        String url = Session.NOTIFY_SERVER_URL+"inc_cnt.php?feed_id="+news.id;
        Log.e("inc_url",url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("inc_cnt",jsonArray.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);

    }



    boolean isBig=true;
    int header_height = 0;

    public void animate(final View v)
    {
        if(!isBig){

            if(header_height==0){
                header_tab.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                        header_height = header_tab.getHeight();
                        //height is ready
                    }
                });

            }else{
                ValueAnimator va = ValueAnimator.ofInt(0, header_height);
                va.setDuration(100);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        v.getLayoutParams().height = value.intValue();
                        v.requestLayout();
                    }
                });
                va.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isBig = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                va.start();
               // isBig = true;

            }




        }
        else{

            if(header_height==0){
                header_tab.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                        header_height = header_tab.getHeight();
                        //height is ready
                    }
                });

            }else {


                ValueAnimator va = ValueAnimator.ofInt(header_height, 0);
                va.setDuration(100);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        v.getLayoutParams().height = value.intValue();
                        v.requestLayout();
                    }
                });

                va.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isBig = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                va.start();
               // isBig = false;
            }

        }
    }

}
