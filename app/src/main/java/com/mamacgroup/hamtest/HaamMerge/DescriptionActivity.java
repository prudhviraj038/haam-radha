package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.analytics.HitBuilders;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
//import com.squareup.picasso.Picasso;


public class DescriptionActivity extends Activity implements  GestureDetector.OnGestureListener {
    GestureDetector gestureDetector;
    ImageView img,ch_img,back;
    News news;
    TextView normal,bold,urgent,pop_up_label;
    ImageView font_adjust,share_btn;
    LinearLayout font_adjust_view;
    TextView des;
    MyTextViewBold title;
    LinearLayout ll;
    TextView font_size_heading,font_style_heading;
    CrystalSeekbar seekBarCompat;
    Typeface tf_normal,tf_bold;
    SharedPreferences sharedPreferences;
    SliderLayout sliderLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.hamm_description_activity);
        gestureDetector = new GestureDetector(this);
//        if(getActionBar()!=null){
//            getActionBar().hide();
//        }else if(getSupportActionBar()!=null){
//            getSupportActionBar().hide();
//        }
        tf_normal = Typeface.createFromAsset(this.getAssets(), "fonts/geeza_pro.ttf");
        tf_bold = Typeface.createFromAsset(this.getAssets(), "fonts/geeza_pro_bold.ttf");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        img=(ImageView)findViewById(R.id.des_imgg);
        back=(ImageView)findViewById(R.id.des_back_img);
        ch_img=(ImageView)findViewById(R.id.des_page_ch_img);
        des=(TextView)findViewById(R.id.des_tvv);
        title=(MyTextViewBold)findViewById(R.id.des_page_title);
        ll=(LinearLayout)findViewById(R.id.ll_main_anim);
        news=(News)getIntent().getSerializableExtra("news");
        Picasso.with(DescriptionActivity.this).load(news.ch_image).into(ch_img);
        Picasso.with(DescriptionActivity.this).load(news.image).fit().into(img);
        share_btn = (ImageView) findViewById(R.id.share_btn_des);

        sliderLayout=(SliderLayout)findViewById(R.id.img_slider);
        if(news.images.size()==0){
            sliderLayout.setVisibility(View.GONE);
        }else{
            sliderLayout.setVisibility(View.VISIBLE);
        }
        if(news.images.size()==1){
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            sliderLayout.stopAutoCycle();
        }else{
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            sliderLayout.startAutoCycle();
        }
        for (int i = 0; i < news.images.size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(DescriptionActivity.this);
            defaultSliderView.image(news.images.get(i)).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    Intent intent=new Intent(DescriptionActivity.this,SlidingActivity.class);
                    intent.putStringArrayListExtra("images", news.images);
                    intent.putExtra("title", news.title);
                    startActivity(intent);

                }
            });
            sliderLayout.addSlider(defaultSliderView);
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescriptionActivity.this, ImageZoomActivity.class);
                intent.putExtra("url", news.image);
                intent.putExtra("title", news.title);
                startActivity(intent);
            }
        });
//        if(news.image.equals("")) {
//            img.setVisibility(View.GONE);
//        }else{
//            img.setVisibility(View.VISIBLE);
//        }

        if(news.description.equals("")){
            des.setText("");
        }else {
            byte[] data = Base64.decode(news.description, Base64.DEFAULT);
            String text = new String(data, StandardCharsets.UTF_8);
            des.setText(Html.fromHtml(text));
        }

//        ll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                gestureDetector.onTouchEvent(motionEvent);
//                return false;
//            }
//        });
        title.setText(Html.fromHtml(news.title));
//        ll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.e("motion", motionEvent.toString());
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.e("motion", "motion");
//                    onBackPressed();
//                }
//                return false;
//            }
//        });
//        img.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.e("motion", motionEvent.toString());
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.e("motion", "motion");
//                    onBackPressed();
//                }
//                return false;
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        normal = (TextView) findViewById(R.id.text_normal);
        normal.setText(Settings.getword(DescriptionActivity.this,"Normal"));
        bold = (TextView) findViewById(R.id.text_bold);
        bold.setText(Settings.getword(DescriptionActivity.this, "Bold"));
        font_adjust_view = (LinearLayout) findViewById(R.id.font_adjust_view);
        font_adjust_view.setVisibility(View.GONE);
        font_adjust = (ImageView)findViewById(R.id.font_adjust);
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

        des.setTypeface(tf_normal);
        normal.performClick();
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normal.setTextColor(Color.parseColor("#ffffff"));
                bold.setTextColor(Color.parseColor("#A71C21"));
                normal.setBackgroundResource(R.drawable.border_full_appcolor_font);
                bold.setBackgroundResource(R.drawable.border_empty_appcolor_font);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("font_style", "normal");
//                editor.commit();
                des.setTypeface(tf_normal);
            }
        });

        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bold.setTextColor(Color.parseColor("#ffffff"));
                normal.setTextColor(Color.parseColor("#A71C21"));
                bold.setBackgroundResource(R.drawable.border_full_appcolor_font);
                normal.setBackgroundResource(R.drawable.border_empty_appcolor_font);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("font_style", "bold");
//                editor.commit();
                des.setTypeface(tf_bold);

            }
        });
//        if (sharedPreferences.getString("font_style", "-1").equals("bold")) {
//            bold.performClick();
//        }else{
//            normal.performClick();
//        }
        seekBarCompat = (CrystalSeekbar) findViewById(R.id.materialSeekBar);
        seekBarCompat.setMaxValue(0);
        seekBarCompat.setMaxValue(20);
        seekBarCompat.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number value) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("font_size_m", 20 + value.intValue());
                Log.e("text_size_slider", String.valueOf(20 + value.intValue()));
                editor.apply();

            }
        });
        font_size_heading = (TextView)findViewById(R.id.fontsize_heading);
        font_size_heading.setText(Settings.getword(DescriptionActivity.this, "Font Size"));

        font_style_heading = (TextView) findViewById(R.id.font_style_heading);
        font_style_heading.setText(Settings.getword(DescriptionActivity.this, "Font Style"));

        seekBarCompat.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                des.setTextSize(20 + minValue.intValue());
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, news.share_str);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "share"));

            }
        });
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
        //AppController.getInstance().getDefaultTracker().setScreenName("NEWSDETAIL SCREEN");
       // AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_up, R.anim.exit_to_down);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (motionEvent1.getY() - motionEvent.getY() > 50) {

//            Toast.makeText(VideoPlayerActivity.this , " Swipe Down " , Toast.LENGTH_LONG).show();
//            onBackPressed();
            Log.e("Swipe Down", "Swipe Down");
            return true;
        }
        return true;
    }
}
