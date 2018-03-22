package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.google.android.gms.analytics.HitBuilders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by HP on 7/26/2016.
 */
public class LiveTvFragment extends Fragment {

    GridView listView;
    ArrayList<LiveChannels> categories;
    ArrayList<LiveChannels> categories_all;


    FragmentTouchListner mCallBack;
    MyTextView1 label;
    ImageView settings_btn;
    ArrayList<String> tab_names;
    TextView coming_soon_text;

    LinearLayout search_layout;


    public interface FragmentTouchListner {

        public void livetvselected(LiveChannels liveChannels);

        public void setselected(String index);

        public void present(String index);

        public void back();

        public void setttings_btn_clicked();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.livetvfragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        mCallBack.setselected("3");
        mCallBack.present("3");

        categories = new ArrayList<>();
        categories_all = new ArrayList<>();

        get_details();

        search_layout = (LinearLayout) view.findViewById(R.id.search_layout);
        settings_btn = (ImageView) view.findViewById(R.id.set_btn_live);
        search_edit = (EditText) view.findViewById(R.id.et_search);
        search_edit.setHint(Session.getword(getActivity(),"empty_search"));

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().equals(""))
                {
                    recycler_view();
                }
                else{
                    recycler_view_search(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
        listView = (GridView) view.findViewById(R.id.listView);
        coming_soon_text = (TextView) view.findViewById(R.id.coming_soon_text);
        coming_soon_text.setText(Session.getword(getActivity(), "live_tv_coming_soon"));
        tab_names = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // mCallBack.livetvselected(categories.get(position));

//                if (!categories.get(selected).chanels.get(position).youtube.equals("")) {
//                    Intent intent = new Intent(getActivity(), YoutubePlayer.class);
//                    intent.putExtra("video", categories.get(selected).chanels.get(position).youtube);
//
//                    intent.putExtra("name", categories.get(selected).chanels.get(position).get_ch_title(getActivity()));
//
//                    startActivity(intent);
                Log.e("external_link",categories.get(selected).chanels.get(position).external_link);
                if(!categories.get(selected).chanels.get(position).external_link.equals("")){
                    Intent webview_activity = new Intent(getActivity(),WebviewActivity.class);
                    webview_activity.putExtra("link", categories.get(selected).chanels.get(position).external_link);
                    //   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.link));
                    startActivity(webview_activity);
                } else {
//                    Intent intent = new Intent(getActivity(), JWPlayerViewExample.class);
//                    intent.putExtra("jw_url", categories.get(selected).chanels.get(position).link);
//                    intent.putExtra("name", categories.get(selected).chanels.get(position).get_ch_title(getActivity()));
//                    startActivity(intent);
                }
            }
        });

//        listView.setAdapter(categoryAdapter);

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.setttings_btn_clicked();
            }
        });
        ImageView back_img = (ImageView) view.findViewById(R.id.back_btn);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.back();
            }
        });
        com.mamacgroup.hamtest.MyTextView tittle = (com.mamacgroup.hamtest.MyTextView) view.findViewById(R.id.label2);
        tittle.setText(Session.getword(getActivity(), "title_tv_live"));



        mRecyclerView = (RecyclerView) view.findViewById(R.id.live_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));


    }

    ArrayList<com.mamacgroup.hamtest.MyTextView1> custom_names;
    LinearLayout customs_in_header;
    int selected = 0;

    public void display_custom(final ArrayList<String> jsonArray) {

        custom_names = new ArrayList<>();
        customs_in_header.removeAllViewsInLayout();
        customs_in_header.setVisibility(View.VISIBLE);

        for (int i = 0; i < jsonArray.size(); i++) {
            final com.mamacgroup.hamtest.MyTextView1 temp = new com.mamacgroup.hamtest.MyTextView1(getActivity());
            try {
                temp.setText(jsonArray.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            temp.setLayoutParams(params);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER);
            temp.setTextSize(12);
            temp.setTextColor(getResources().getColor(R.color.aa_app_blue));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);
            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(getResources().getColor(R.color.aa_app_blue));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);

                    }

                    temp.setBackgroundResource(R.drawable.border_full_appcolor);
                    temp.setTextColor(Color.parseColor("white"));


                   // mAdapter = new SimpleAdapter(getActivity(), categories.get(finalI2).chanels);

                    selected = finalI2;
                    if(finalI2==0) {
                        recycler_view_liked();
                        search_layout.setVisibility(View.GONE);
                    }

                    else {
                        recycler_view();
                        search_layout.setVisibility(View.VISIBLE);
                        search_edit.setText("");
                    }

                }
            });

            custom_names.add(temp);
            customs_in_header.addView(temp);
        }

        custom_names.get(custom_names.size() - 1).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(custom_names.size() - 1).setTextColor(Color.parseColor("white"));
        custom_names.get(custom_names.size() - 1).performClick();
    }

    ProgressDialog progressDialog = null;



    private void get_categories() {

//        try {
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage(Session.getword(getActivity(), "please_wait"));
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//
//        }catch (Exception exception){
//
//        }

        String url = Session.SERVER_URL + "tv-latest.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());

                if (progressDialog != null)
                    progressDialog.dismiss();

                for (int i=0;i<jsonArray.length();i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        categories_all.add(new LiveChannels(jsonObject));

                        Log.e("names", jsonObject.getString("title"));
                        Log.e("e_link", jsonObject.getJSONArray("tvs").getJSONObject(i).getString("external_link"));
                        //tab_names.add(jsonObject.getString("title" + Session.get_append(getActivity())));
                     //   Log.e("names", tab_names.get(tab_names.size() - 1));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


//                if(categories.size()==0){
//
//                }else{
//                    for(int i=0;i<categories.size();i++) {
//                        tab_names.add(categories.get(i).get_title(getActivity()));
//                    }
//                }

                try {
                    tab_names.add(Session.getword(getActivity(),"my_live_channels"));
                    tab_names.add(Session.getword(getActivity(),"all_live_channels"));
                    display_custom(tab_names);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mCallBack.back();
                }

//                categoryAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Log.e("error", volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    public void get_details() {
        String url = null;
        try {
            url = Session.SERVER_URL + "settings.php";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);

        try {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(Session.getword(getActivity(), "please_wait"));
            progressDialog.setCancelable(false);
            progressDialog.show();

        } catch (Exception exception) {

        }
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                //  progressDialog.dismiss();
                try {
                    String live_tv = jsonObject.getString("live_tv_android");
                    if (live_tv.equals("1")) {
                        get_categories();
                        coming_soon_text.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    } else {
                        if (progressDialog != null)
                            progressDialog.dismiss();

                        coming_soon_text.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);

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

                if (progressDialog != null)
                    progressDialog.dismiss();
            }
        });
// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
//        AppController.getInstance().getDefaultTracker().setScreenName("LiveTv");
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());


    }


    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;
    EditText search_edit;


private void recycler_view(){

    //Your RecyclerView.Adapter


    //This is the code to provide a sectioned grid
    categories.clear();
    for(int i=0;i<categories_all.size();i++){
        categories.add(categories_all.get(i));
    }



    int count=0;
    List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<SectionedGridRecyclerViewAdapter.Section>();
    ArrayList<LiveChannels.Chanel> chanels = new ArrayList<>();

    for(int j=0;j<categories.size();j++) {


        sections.add(new SectionedGridRecyclerViewAdapter.Section(count, categories.get(j).get_title(getActivity()),"head"));
        //count++;

        for (int i = 0; i < categories.get(j).chanels.size(); i++) {


            if (categories.get(j).chanels.get(i).ch_id.equals("0")) {
                Log.e("add_position",String.valueOf(i));
                sections.add(new SectionedGridRecyclerViewAdapter.Section(count, categories.get(j).chanels.get(i).link,"add"));
                //count++;

            }else{
                chanels.add(categories.get(j).chanels.get(i));
                count++;
            }


        }



    }

    mAdapter = new SimpleAdapter(getActivity(),chanels);

    SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
    SectionedGridRecyclerViewAdapter mSectionedAdapter = new
            SectionedGridRecyclerViewAdapter(getActivity(),R.layout.news_item_live_ad,R.id.publisherAdView,mRecyclerView,mAdapter);
    mSectionedAdapter.setSections(sections.toArray(dummy));
    mRecyclerView.setAdapter(mSectionedAdapter);

}

    private void recycler_view_liked(){

        //Your RecyclerView.Adapter


        //This is the code to provide a sectioned grid
       // categories = new ArrayList<>(categories_all);

        categories.clear();
        for(int i=0;i<categories_all.size();i++){
            categories.add(categories_all.get(i));
        }


        Log.e("categories_size",String.valueOf(categories.size()));
        Log.e("categories__all_size",String.valueOf(categories_all.size()));


        int count=0;
        List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<SectionedGridRecyclerViewAdapter.Section>();
        ArrayList<LiveChannels.Chanel> chanels = new ArrayList<>();

        for(int j=0;j<categories.size();j++) {

          //  sections.add(new SectionedGridRecyclerViewAdapter.Section(count, categories.get(j).get_title(getActivity()),"head"));
            //count++;

            for (int i = 0; i < categories.get(j).chanels.size(); i++) {

                if(AppController.getInstance().databaseHandler.is_following(categories.get(j).chanels.get(i).ch_id)){
                    count++;
                    if (categories.get(j).chanels.get(i).ch_title.equals("double_add")) {
                        sections.add(new SectionedGridRecyclerViewAdapter.Section(count, categories.get(j).chanels.get(i).link,"add"));
                        //categories.get(j).chanels.remove(i);
                    }else{
                        chanels.add(categories.get(j).chanels.get(i));
                    }

                }
            }

        }
        mAdapter = new SimpleAdapter(getActivity(),chanels);

        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getActivity(),R.layout.news_item_live_ad,R.id.publisherAdView,mRecyclerView,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        mRecyclerView.setAdapter(mSectionedAdapter);

    }


    private void recycler_view_search(String search){

        //Your RecyclerView.Adapter


        //This is the code to provide a sectioned grid

        categories.clear();
        for(int i=0;i<categories_all.size();i++){
            categories.add(categories_all.get(i));
        }


        Log.e("categories_size",String.valueOf(categories.size()));
        Log.e("categories__all_size",String.valueOf(categories_all.size()));


        int count=0;
        List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<SectionedGridRecyclerViewAdapter.Section>();
        ArrayList<LiveChannels.Chanel> chanels = new ArrayList<>();

        for(int j=0;j<categories.size();j++) {

            //sections.add(new SectionedGridRecyclerViewAdapter.Section(count, categories.get(j).get_title(getActivity()),"head"));
            //count++;

            for (int i = 0; i < categories.get(j).chanels.size(); i++) {

                //count++;


                if(categories.get(j).chanels.get(i).ch_title.toLowerCase().contains(search.toLowerCase())  || categories.get(j).chanels.get(i).ch_title_ar.toLowerCase().contains(search.toLowerCase())){
                    count++;
                    if (categories.get(j).chanels.get(i).ch_title.equals("double_add")) {
                        //sections.add(new SectionedGridRecyclerViewAdapter.Section(count, categories.get(j).chanels.get(i).link,"add"));
                        // categories.get(j).chanels.remove(i);
                    }else{
                        chanels.add(categories.get(j).chanels.get(i));
                    }

                }
            }

            mAdapter = new SimpleAdapter(getActivity(),chanels);

        }

        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getActivity(),R.layout.news_item_live_ad,R.id.publisherAdView,mRecyclerView,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        mRecyclerView.setAdapter(mSectionedAdapter);

    }



}
