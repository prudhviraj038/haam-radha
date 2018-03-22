package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mamacgroup.hamtest.R;

/**
 * Created by mac on 12/20/16.
 */

public class NoInternetActivity extends Activity{
    //title_offline
    //message_offline
    //check_again
    LinearLayout linearLayout;
    MyTextView header,message,btn_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.no_internet_activity);
        linearLayout = (LinearLayout)findViewById(R.id.no_internet_btn);
        header = (MyTextView) findViewById(R.id.no_internet_header);
        message = (MyTextView) findViewById(R.id.no_internet_message);
        btn_txt = (MyTextView) findViewById(R.id.no_internet_btn_txt);
        header.setText("غير متصل على الانترنت");
        message.setText("غير متصل بالانترنت");
        btn_txt.setText("تحقق مرة اخرى");
        header.setText(Settings.getword(this,"offline"));
        message.setText(Settings.getword(this,"No InternetConnection"));
        btn_txt.setText(Settings.getword(this,"check again"));

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
