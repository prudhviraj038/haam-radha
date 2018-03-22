package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;

import java.util.ArrayList;

/**
 * Created by HP on 19-Sep-16.
 */


public class FirstTimeScreenActivity extends Activity {
    MyTextView title,next_tv,skip_tv;
    LinearLayout next_ll,skip_ll;
    VideoView videoView;
    ArrayList<String> titles;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.haam_firsttime_screen);
        titles=new ArrayList<>();
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"firstWord"));
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"secondWord"));
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"thirdWord"));
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"forthWord"));
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"fifthWord"));
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"sixWord"));
        titles.add(Settings.getword(FirstTimeScreenActivity.this,"sevenWord"));
        title=(MyTextView)findViewById(R.id.video_titlee);
        skip_tv=(MyTextView)findViewById(R.id.video_skip_tv);
        skip_tv.setText(Settings.getword(FirstTimeScreenActivity.this, "Skip"));
        skip_ll=(LinearLayout)findViewById(R.id.video_skip_ll);

        next_tv=(MyTextView)findViewById(R.id.video_next_tv);

        next_ll=(LinearLayout)findViewById(R.id.video_next_ll);
        title.setText(titles.get(0));
        next_tv.setText(Settings.getword(FirstTimeScreenActivity.this, "Next"));
        videoView=(VideoView)findViewById(R.id.videoView);
//        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
//        params.width =  metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        videoView.setLayoutParams(params);
//        videoView.

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
        videoView.setVideoURI(uri);
        videoView.start();
        skip_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.is_first_set(FirstTimeScreenActivity.this, "1");
                Intent intent = new Intent(FirstTimeScreenActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        next_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                videoView.stopPlayback();
                if (i == 2) {
                    title.setText(titles.get(i - 1));
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video2);
                    videoView.setVideoURI(uri);
                    videoView.start();
                } else if (i == 3) {
                    title.setText(titles.get(i - 1));
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video3);
                    videoView.setVideoURI(uri);
                    videoView.start();
                } else if (i == 4) {
                    title.setText(titles.get(i - 1));
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video4);
                    videoView.setVideoURI(uri);
                    videoView.start();
                } else if (i == 5) {
                    title.setText(titles.get(i - 1));
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video5);
                    videoView.setVideoURI(uri);
                    videoView.start();
                } else if (i == 6) {
                    title.setText(titles.get(i - 1));
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video6);
                    videoView.setVideoURI(uri);
                    videoView.start();
                    next_tv.setText(Settings.getword(FirstTimeScreenActivity.this, "Start"));
//                } else if (i == 7) {
//                    title.setText(titles.get(i - 1));
//                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video7);
//                    videoView.setVideoURI(uri);
//                    videoView.start();
//                    next_tv.setText(Settings.getword(FirstTimeScreenActivity.this, "Start"));
                } else {
                    Settings.is_first_set(FirstTimeScreenActivity.this, "1");
                    Intent intent = new Intent(FirstTimeScreenActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.setLooping(true);

            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        try {
            AppController.getInstance().cancelPendingRequests();
            Settings.set_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
//        AppController.getInstance().getDefaultTracker().setScreenName("FirstTimeScreenActivity");
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        }catch(Exception ex){
            ex.printStackTrace();
        }


    }
}
