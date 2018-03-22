package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by mac on 12/20/16.
 */

public class NoInternetActivity extends Activity{
    //title_offline
    //message_offline
    //check_again
    LinearLayout linearLayout;
    MyTextView1 header,message,btn_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        setContentView(R.layout.no_internet_activity);
        linearLayout = (LinearLayout)findViewById(R.id.no_internet_btn);
        header = (MyTextView1) findViewById(R.id.no_internet_header);
        message = (MyTextView1) findViewById(R.id.no_internet_message);
        btn_txt = (MyTextView1) findViewById(R.id.no_internet_btn_txt);
        header.setText(Session.getword(this,"title_offline"));
        message.setText(Session.getword(this,"message_offline"));
        btn_txt.setText(Session.getword(this,"check_again"));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart_app();
            }
        });


    }


    private void restart_app(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


}
