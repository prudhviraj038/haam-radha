package com.mamacgroup.hamtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;



/**
 * Created by sriven on 6/7/2016.
 */
public class WebviewActivity extends Activity {
    ImageView back;
    WebView webView;
    String link;
    LinearLayout progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.forceRTLIfSupported(this);
        setContentView(R.layout.web_view_activity);
        back=(ImageView)findViewById(R.id.wv_back_img);
        webView=(WebView)findViewById(R.id.webView4);
        link=getIntent().getStringExtra("link");

        webView.getSettings().setJavaScriptEnabled(true);
        progressbar = (LinearLayout) findViewById(R.id.progressBar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.setWebViewClient(new WebViewController());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                progressbar.setVisibility(View.VISIBLE);
                if(progress == 100)
                    progressbar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(link);
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        try {
            AppController.getInstance().cancelPendingRequests();
            Session.set_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        try {
            Session.get_minimizetime(this);
        }catch(Exception ex){
            ex.printStackTrace();
        }


    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
