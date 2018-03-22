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
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//import com.bumptech.glide.Glide;
//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.CustomEvent;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
//import com.google.android.gms.ads.doubleclick.PublisherAdView;
////import com.google.android.gms.analytics.HitBuilders;
//import com.google.firebase.analytics.FirebaseAnalytics;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
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


/**
 * Created by HP on 7/26/2016.
 */
public class NewsDetailFragment extends Fragment {
    SharedPreferences sharedPreferences;
    FragmentTouchListner mCallBack;

    CrystalSeekbar seekBarCompat;
    News news;

    private ListView mDrawerList1;
    TextView normal,bold,urgent,pop_up_label;
    ImageView font_adjust;
    LinearLayout share_ll,mPublisherAdView;
    ImageView instagram_share,facebook_share,twitter_share,email_share,watsapp_share,ins_share,face_share,twi_share,wat_share
            ,gplus_share,lin_share,sms_share,ema_share,close,close_slider,video_hint;
    LinearLayout font_adjust_view;
    LinearLayout detail_scroll,read_ll;
    RelativeLayout news_img_layout;
    String feed_id="0";
    Float X;
    Float Y;
    ViewMoreAdapter viewMoreAdapter;
    ViewFlipper vf;
    private LinearLayout mDemoSliderLayout;
    SliderLayout mDemoSlider;
    TextView time,time2,time3,read_tv,view_more,vm_ch_title;
    TextView font_size_heading,font_style_heading;
    boolean loaded=false;
//    private FirebaseAnalytics mFirebaseAnalytics;
    boolean save_preferences = false;
    Switch night_switch;
    RecyclerView rl;
    LinearLayout header_tab ;
    ArrayList<News> newss;
    LinearLayout vm_ll;



    public interface FragmentTouchListner {


        public void setselected(String index);
        public Animation get_animation(Boolean enter,Boolean loaded);
        public void back();
        public void newsclickednofinish(News news);
        public void tohome();
        public  void goto_setting_activity(String no,String id);
        public  void to_slidingActivity(ArrayList<String> images);
        public void chanel_selected(Chanel chanel);
        public void ihave_completed_hide_footer();
        public void recreateActivity();
        public void hideProgress();


    }

    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
//        AppController.getInstance().getDefaultTracker().setScreenName("NewsDetailPage");
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
//        AppController.getInstance().kTracker.event("PageVisited","NewsDetailPage");
//        Answers.getInstance().logCustom(new CustomEvent("NewsDetailPage"));


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NewsRecycleListActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LogoutUser");
        }
    }

  /*  @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter,loaded);
    }
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            return inflater.inflate(R.layout.newsdetailfragment, container, false);

    }
    String DEBUG_TAG = "debug";
    ImageView imageView;

    private void recordImageView() {
        String id =  news.id;
        String name = news.title;

        // [START image_view_event]
        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "detail");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // [END image_view_event]
    }
    Typeface tf_normal,tf_bold;



    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        newss=new ArrayList<>();
        header_tab = (LinearLayout) view.findViewById(R.id.header);
        vm_ll = (LinearLayout) view.findViewById(R.id.vm_ll);
        view_more = (TextView) view.findViewById(R.id.viewmore_des_page_news);
        view_more.setText(Session.getword(getActivity(),"More News"));
        vm_ch_title = (TextView) view.findViewById(R.id.ch_name_des_page_news);
        Log.e("Count",Session.count_get(getActivity()));
        if(Session.count_get(getActivity()).equals("4")){
            vm_ll.setVisibility(View.GONE);
        }else{
            vm_ll.setVisibility(View.VISIBLE);
            Session.count_set(getActivity(),String.valueOf(Integer.parseInt(Session.count_get(getActivity()))+1));
        }

        save_preferences = false;

         tf_normal = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTArabic-Roman.ttf");
         tf_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTArabic-Bold.ttf");


//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        vf=(ViewFlipper)view.findViewById(R.id.viewFlipper5);
        night_switch = (Switch) view.findViewById(R.id.night_switch);
        night_switch.setChecked(sharedPreferences.getBoolean("night", false));
        night_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night", isChecked);
                editor.commit();

                Session.set_news_feed_id(getActivity(), feed_id);

                mCallBack.recreateActivity();

            }
        });

        TextView night_tv=(TextView)view.findViewById(R.id.night_tv);
        night_tv.setText(Session.getword(getActivity(), "night_mode"));



        // mCallBack.setselected(getArguments().getString("parent"));

        feed_id = getArguments().getString("feed_id");

        if (getArguments().containsKey("news")) {
            news = (News) getArguments().getParcelable("news");
            feed_id = news.id;
            display_dat(view);
        } else {

            get_news(view);
        }
//        recordImageView();



        header_tab.post(new Runnable() {
            @Override
            public void run() {
                Log.e("header_ht",String.valueOf(header_tab.getHeight()));
                header_height = header_tab.getHeight();
                //height is ready
            }
        });
        rl=(RecyclerView)view.findViewById(R.id.rv_view_more);
        get_latest_news(news.id);

    }


        private void display_dat(View view) {
//            vf.setDisplayedChild(0);
            ArrayList<Integer> prgmImages = new ArrayList<>();
            ArrayList<String> prgmTitles = new ArrayList<>();

            if(!news.link.equals(""))
            prgmImages.add(R.drawable.open_in_browser);

            prgmImages.add(R.drawable.copy_link_icon);
            prgmImages.add(R.drawable.flag_icon);
            prgmImages.add(R.drawable.three_dots);
            mDemoSliderLayout = (LinearLayout) view.findViewById(R.id.img_slider);
            mDemoSlider = new SliderLayout(getActivity());
            mDemoSlider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
           mDemoSliderLayout.addView(mDemoSlider);
            if(!news.link.equals(""))
            prgmTitles.add(Session.getword(getActivity(),"open_in_browser"));

            prgmTitles.add(Session.getword(getActivity(),"copy_link"));
            prgmTitles.add(Session.getword(getActivity(),"title_report_abuse"));
            prgmTitles.add(Session.getword(getActivity(),"show_more"));

            pop_up_label  = (TextView) view.findViewById(R.id.pop_up_label);
            pop_up_label.setText(Session.getword(getActivity(),"share_news"));


            mDrawerList1 = (ListView) view.findViewById(R.id.listView2);
            mDrawerList1.setAdapter(new ShareListAdapter(getActivity(), prgmTitles, prgmImages));

            mDrawerList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            try {
                                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.link));
                                //startActivity(browserIntent);
                                read_ll.performClick();
                            } catch (Exception exception) {
                                Toast.makeText(getActivity(), "invalid url", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:

                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText(news.link, news.link);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getActivity(), "text copied", Toast.LENGTH_SHORT).show();
                            break;
//                    case 2:
//
//                        break;
                        case 2:
                            mCallBack.goto_setting_activity("0", news.id);
//                        Toast.makeText(getActivity(),"report has been submitted",Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, news.link);
                            sendIntent.setType("text/plain");
                            getActivity().startActivity(sendIntent);
                            share_ll.setVisibility(View.GONE);
                            break;
                    }
                    share_ll.setVisibility(View.GONE);
                }
            });
            share_ll = (LinearLayout) view.findViewById(R.id.share_ll);


      /*  if(share_ll.getVisibility()==View.VISIBLE)
            share_ll.setVisibility(View.GONE);
        else
            share_ll.setVisibility(View.VISIBLE);

      */
            read_tv = (TextView) view.findViewById(R.id.read_more_tv);
            read_tv.setText(Session.getword(getActivity(), "click here for more details"));
            read_ll = (LinearLayout) view.findViewById(R.id.read_more_ll);
            news_img_layout = (RelativeLayout) view.findViewById(R.id.news_img_layout);
            video_hint = (ImageView) view.findViewById(R.id.video_hint);
            instagram_share = (ImageView) view.findViewById(R.id.instagram_share);
            facebook_share = (ImageView) view.findViewById(R.id.facebook_share);
            twitter_share = (ImageView) view.findViewById(R.id.twitter_share);
            email_share = (ImageView) view.findViewById(R.id.email_share);
            watsapp_share = (ImageView) view.findViewById(R.id.watsapp_share);

            ins_share = (ImageView) view.findViewById(R.id.ins_share);
            ins_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    instagram_share.performClick();
                }
            });
            face_share = (ImageView) view.findViewById(R.id.face_share);
            face_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    facebook_share.performClick();
                }
            });
            twi_share = (ImageView) view.findViewById(R.id.twi_share);
            twi_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    twitter_share.performClick();
                }
            });
            ema_share = (ImageView) view.findViewById(R.id.ema_share);
            ema_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email_share.performClick();
                }
            });
            wat_share = (ImageView) view.findViewById(R.id.wat_share);
            wat_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watsapp_share.performClick();
                }
            });
//            gplus_share = (ImageView) view.findViewById(R.id.gplus_share);
//            gplus_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//            lin_share = (ImageView) view.findViewById(R.id.lin_share);
//            lin_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
            sms_share = (ImageView) view.findViewById(R.id.ssms_share);
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


            close = (ImageView) view.findViewById(R.id.close_img);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    share_ll.setVisibility(View.GONE);
                }
            });

            instagram_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
                    progressDialog.show();

                   // progressDialog.setCancelable(false);

                    Picasso.with(getActivity()).load(news.insta_img).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            if(progressDialog!=null)
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
                            if(progressDialog!=null)
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




            seekBarCompat = (CrystalSeekbar) view.findViewById(R.id.materialSeekBar);
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
            font_size_heading = (TextView) view.findViewById(R.id.fontsize_heading);
            font_size_heading.setText(Session.getword(getActivity(),"change_font_size"));

            font_style_heading = (TextView) view.findViewById(R.id.font_style_heading);
            font_style_heading.setText(Session.getword(getActivity(),"change_font_style"));


            final TextView description = (TextView) view.findViewById(R.id.news_description_big);


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
            detail_scroll = (LinearLayout) view.findViewById(R.id.detail_scroll);

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

            imageView = (ImageView) view.findViewById(R.id.news_img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(video_hint.getVisibility()==View.VISIBLE ){

                        video_hint.performClick();
                    }else{
                        Intent intent = new Intent(getActivity(), ImageZoomActivity.class);
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
//                                    Intent intent = new Intent(getActivity(), ImageZoomActivity.class);
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

            normal = (TextView) view.findViewById(R.id.text_normal);
            normal.setText(Session.getword(getActivity(),"normal"));
            bold = (TextView) view.findViewById(R.id.text_bold);
            bold.setText(Session.getword(getActivity(),"bold"));
            font_adjust_view = (LinearLayout) view.findViewById(R.id.font_adjust_view);
            font_adjust_view.setVisibility(View.GONE);
            font_adjust = (ImageView) view.findViewById(R.id.font_adjust);
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
                    bold.setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
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
                    normal.setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
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

            final ImageView world = (ImageView) view.findViewById(R.id.world_details);

            world.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webview_activity = new Intent(getActivity(),WebviewActivity.class);
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


            ImageView share_btn = (ImageView) view.findViewById(R.id.share_btn_detail);
            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    share_ll.setVisibility(View.VISIBLE);

                }
            });
            TextView ch_title = (TextView) view.findViewById(R.id.news_title);
            ch_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallBack.chanel_selected(news.chanels);
                }
            });

            ch_title.setText(Html.fromHtml(news.chanels.get_ch_title(getActivity())));
            ImageView ch_imageView = (ImageView) view.findViewById(R.id.logo);
            ch_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        mCallBack.chanel_selected(news.chanels);
                }
            });


            Picasso.with(getActivity()).load(news.chanels.ch_image).into(ch_imageView);

            TextView tittle = (TextView) view.findViewById(R.id.news_descri);
            tittle.setText(Html.fromHtml(news.title));
            int default_size = sharedPreferences.getInt("font_size_m", 20);
            Log.e("text_size",String.valueOf(default_size));
            seekBarCompat.setMinStartValue(default_size-20).apply();
            description.setTextSize(default_size);
            description.setText(Html.fromHtml(news.data));
            vm_ch_title.setText(news.chanels.ch_title_ar);


            LinearLayout is_urgent = (LinearLayout) view.findViewById(R.id.is_urgent);
            urgent = (TextView) view.findViewById(R.id.urgent_text);
            urgent.setText(Session.getword(getActivity(), "urgent"));
            time = (TextView) view.findViewById(R.id.news_time);
            time.setText(news.get_time(getActivity()));

            time2 = (TextView) view.findViewById(R.id.news_time22);
            time2.setText(news.get_time(getActivity()));

            time3 = (TextView) view.findViewById(R.id.news_time3);
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
                DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
                defaultSliderView.image(news.img.get(i)).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView baseSliderView) {
                        mCallBack.to_slidingActivity(news.img);

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
                //ImageLoader imageLoader = CustomVolleyRequest.getInstance(getActivity())
                  //      .getImageLoader();
                //imageLoader.get(news.image, ImageLoader.getImageListener(imageView,
                  //      R.drawable.loading, R.drawable
                    //            .app_icon));

                //  Glide.with(getActivity()).load(news.image).into(imageView);
                //mDemoSlider.setVisibility(View.GONE);
                try {
                    Picasso.with(getActivity()).load(news.image).placeholder(R.drawable.loading).noFade().into(imageView);
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

                        Intent intent = new Intent(getActivity(), AndroidVideoPlayerActivity.class);
                        intent.putExtra("video", news.mp4);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), YoutubePlayer.class);
                        intent.putExtra("video", news.video);
                        startActivity(intent);
                    }

                }
            });

            final ImageView back_img = (ImageView) view.findViewById(R.id.back_img);
            back_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPause();
                    mCallBack.back();
                }
            });


            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener( new View.OnKeyListener()
            {
                @Override
                public boolean onKey( View v, int keyCode, KeyEvent event )
                {
                    if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_UP)
                    {
                        if(share_ll.getVisibility()==View.VISIBLE){
                            share_ll.setVisibility(View.GONE);
                            return true;
                        }

                        else if(font_adjust_view.getVisibility() == View.VISIBLE){
                            font_adjust_view.setVisibility(View.GONE);
                            return true;
                        }
                        else {
                            onPause();
                            mCallBack.back();
                            return true;
                        }
                    }
                    return true;
                }
            } );



            DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
            if(databaseHandler.news_opened(news.id).equals("0")){
                databaseHandler.addnews(news.id,news.id);
            }
            else {
                Log.e("alredy", "vived");
            }

            update_views();

            mCallBack.hideProgress();
            mCallBack.ihave_completed_hide_footer();


            mPublisherAdView = (LinearLayout) view.findViewById(R.id.publisherAdViewDetails);
            if(Session.get_ad_id(getActivity()).equals("-1")){
                mPublisherAdView.setVisibility(View.GONE);
            }else {
                mPublisherAdView.setVisibility(View.VISIBLE);
                mPublisherAdView.removeAllViews();
//                PublisherAdView mPublisherAdView1;
//                mPublisherAdView1 = new PublisherAdView(getActivity());
//                mPublisherAdView1.setAdSizes(new AdSize(300, 250));
//                mPublisherAdView1.setAdUnitId(Session.get_ad_id(getActivity()));
//                PublisherAdRequest adRequest = new PublisherAdRequest.Builder().
//                        // addTestDevice("6CF13E43F2584625AF6152F65DAC084E").
//                                //addTestDevice("DF05CE517F21FBE0F3D2BC342BBEBCD9").
//                                //addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR).
//                                build();
//                mPublisherAdView.addView(mPublisherAdView1);
//                mPublisherAdView1.loadAd(adRequest);
//                mPublisherAdView1.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdLoaded() {
//                        // Code to be executed when an ad finishes loading.
////                        Session.set_ad_id(getActivity(),"-1");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int errorCode) {
//                        // Code to be executed when an ad request fails.
//                    }
//
//                    @Override
//                    public void onAdOpened() {
//                        // Code to be executed when an ad opens an overlay that
//                        // covers the screen.
//                    }
//
//                    @Override
//                    public void onAdLeftApplication() {
//                        // Code to be executed when the user has left the app.
//                    }
//
//                    @Override
//                    public void onAdClosed() {
//                        // Code to be executed when when the user is about to return
//                        // to the app after tapping on an ad.
//                    }
//                });
            }
//

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
            File file =  new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void get_news(final View view){
        vf.setDisplayedChild(1);
        String url = Session.SERVER_URL+"feeds-new3.php?feed_id="+feed_id;
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
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
                    mCallBack.back();

                }else{
                    vf.setDisplayedChild(0);
                    for(int i=0;i<jsonArray.length();i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        news = new News(jsonObject,getActivity());

                        display_dat(view);
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
             //   Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();

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
    private void get_latest_news(final String id) {
        String url = "http://slave.naqshapp.com/apiv2/feeds-related.php?news_id="+id;
        try {
            Log.e("url_view_more", url);
             final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("please_wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
//        temp_url=temp_url
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.e("response", jsonArray.toString());
               if(progressDialog!=null)
                    progressDialog.dismiss();
                    newss.clear();

                    if(jsonArray.length()==0){
                        //Toast.makeText(getActivity(),"There is no new feeds at this moment",Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("json", jsonObject.toString());
                            newss.add(new News(jsonObject,getActivity()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    viewMoreAdapter=new ViewMoreAdapter(getActivity(),newss,mCallBack);
                    rl.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rl.setItemAnimator(new DefaultItemAnimator());
                    rl.getItemAnimator().setMoveDuration(1000);
                    rl.setAdapter(viewMoreAdapter);
                    viewMoreAdapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
               if(progressDialog!=null)
                    progressDialog.dismiss();
                    Log.e("error", volleyError.toString());
                }
            });
            AppController.getInstance().addToRequestQueue(jsonArrayRequest,"latest");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
