package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sriven on 6/7/2016.
 */
public class MyAccountActivity extends Activity {
    ImageView back;
    LinearLayout signout;
    TextView signup_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        setContentView(R.layout.my_account_activity);
        signout=(LinearLayout)findViewById(R.id.signout_ll);
        signup_tv=(TextView)findViewById(R.id.signout_tv);
        signup_tv.setText(Session.getword(this,"signout"));
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.set_user_id(MyAccountActivity.this,"-1","");
                Intent intent = new Intent(MyAccountActivity.this, NewsRecycleListActivity.class);
                intent.putExtra("feed_id", "0");
                intent.putExtra("login_check","0");
                startActivity(intent);
            }
        });
        back = (ImageView) findViewById(R.id.back_btn_my);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
