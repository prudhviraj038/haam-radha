package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by mac on 6/11/17.
 */

public class SelectedCategoriesActivity extends Activity {

    RecyclerView recyclerView;
    ArrayList<Category> rates;
    ArrayList<Category> rates_all;
    SelectedCategoriesAdapter ratesAdapter;
    ImageView back;
    MyTextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.settingsforceRTLIfSupported(this);
        setContentView(R.layout.haam_fragment_add_currencies);
        title = (MyTextView) findViewById(R.id.select_cat_list);
        title.setText(Settings.getword(this, "Category List"));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        rates = new ArrayList<>();
        rates_all = new ArrayList<>();
        get_category();
        back=(ImageView)findViewById(R.id.back_sc_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final EditText editText = (EditText) findViewById(R.id.locations_search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   // getLocations(editText.getText().toString());

                    filter_data(editText.getText().toString());

                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }


                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() == 0) {
                    filter_data("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //setHasOptionsMenu(true);

    }

    @Override
    protected void onResume(){
        super.onPause();
//        AppController.getInstance().getDefaultTracker().setScreenName("SelectedCategoriesActivity");
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());
        try {
            Settings.get_minimizetime(this);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    private void filter_data(String filter){

        rates.clear();
        ratesAdapter.notifyDataSetChanged();

        if(!filter.equals("")) {
            for (int i = 0; i < rates_all.size(); i++) {
                if (rates_all.get(i).title.contains(filter))
                    rates.add(rates_all.get(i));
            }
            ratesAdapter.notifyDataSetChanged();
        }else{
            for (int i = 0; i < rates_all.size(); i++) {
                    rates.add(rates_all.get(i));
            }
            ratesAdapter.notifyDataSetChanged();
        }
    }
    public  void to_cat(String cat){

        String url = null;
        url = Settings.SERVERURL+"login.php?" + "email="+ cat;
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(SelectedCategoriesActivity.this, "please_wait"));
//        progressDialog.setMessage(Settings.getword(this, "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(SelectedCategoriesActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(SelectedCategoriesActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(SelectedCategoriesActivity.this, Settings.getword(SelectedCategoriesActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    public void get_category() {
        String url;
        url = Settings.SERVERURL+"category.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
//        pd.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                progressDialog.dismiss();
//                pd.setVisibility(View.GONE);
                rates.clear();

                Log.e("response is: ", jsonObject.toString());
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        Category prod = new Category(sub);
                            rates.add(prod);
                        if(Settings.getselectedCat(SelectedCategoriesActivity.this).equals("-1")){
                            AppController.getInstance().selected_categories.add(prod.id);
                        }

                    }
//                    title.setText(categories.get(0).getTitle(this));
                    ratesAdapter = new SelectedCategoriesAdapter(rates,SelectedCategoriesActivity.this,SelectedCategoriesActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SelectedCategoriesActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.getItemAnimator().setMoveDuration(1000);
//                    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(SelectedCategoriesActivity.this));
                    recyclerView.setAdapter(ratesAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(SelectedCategoriesActivity.this,Settings.getword(SelectedCategoriesActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
//                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Settings.setselectedCat(this, AppController.getInstance().selected_categories.toString());
    }
}
