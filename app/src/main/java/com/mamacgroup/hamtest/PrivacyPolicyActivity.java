package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sriven on 6/7/2016.
 */
public class PrivacyPolicyActivity extends Activity {
    ImageView back;
    LinearLayout signout;
    TextView pp_tv,header;
    String pp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        setContentView(R.layout.privacy_policy_activity);
        pp=getIntent().getStringExtra("pp");
        pp_tv=(TextView)findViewById(R.id.pp_tv);
        pp_tv.setText(Html.fromHtml(pp));
        header=(TextView)findViewById(R.id.privacy_policy_head);
        header.setText(Session.getword(this, "privacy_policy"));
        back = (ImageView) findViewById(R.id.back_btn_pp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
