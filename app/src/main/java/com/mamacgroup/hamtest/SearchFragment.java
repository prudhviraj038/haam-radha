package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by HP on 7/26/2016.
 */


public class SearchFragment extends Fragment implements AbsListView.OnScrollListener {

    ListView listView;
    LinearLayout progressBar;
    ArrayList<News> newses;
    News_listAdapter news_list_adapter;
    FragmentTouchListner mCallBack;
    MyTextView label,no_source;
    ImageView back_btn;
    String temp="";
    EditText search_edit;
    String url = Session.SERVER_URL+"feeds-new3.php?";
    int start=0,end=10;
    DatabaseHandler db;
    String tab_id = MainActivity.world_id;

    public interface FragmentTouchListner{
        public void back();
        public void newsclickednofinish(News news);
        public void ihave_completed();
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
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if(getArguments().containsKey("tab_id"))
            tab_id = getArguments().getString("tab_id");

        db = new DatabaseHandler(getActivity());
        newses = new ArrayList<>();
        customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
        back_btn = (ImageView) view.findViewById(R.id.search_back_btn);
        listView = (ListView) view.findViewById(R.id.listView3);
        listView.setOnScrollListener(this);
        label = (MyTextView) view.findViewById(R.id.search_title);
        label.setText(Session.getword(getActivity(),"search"));
        no_source=(MyTextView)view.findViewById(R.id.no_result_search);
        no_source.setText(Session.getword(getActivity(), "no_feeds"));

        progressBar = (LinearLayout) view.findViewById(R.id.progressBar34);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Session.count_set(getActivity(),"0");
                mCallBack.newsclickednofinish(newses.get(position));
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back();
            }
        });
        news_list_adapter=new News_listAdapter(getActivity(),newses,(NewsRecycleListActivity) mCallBack);
        listView.setAdapter(news_list_adapter);

        search_edit = (EditText) view.findViewById(R.id.et_search);
        search_edit.setHint(Session.getword(getActivity(), "empty_search"));

//        search_edit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    if (s.length() > 2){
//
//                        temp=s.toString();
//                        get_news("search=" + URLEncoder.encode(s.toString(), "utf-8"), true);
//
//                    }
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                   // categoryAdapter.getFilter().filter(search_edit.getText().toString());
                    if(search_edit.getText().toString().length()>0)
                    {
                        temp=search_edit.getText().toString();
                        try {
                            get_news("search=" + URLEncoder.encode(search_edit.getText().toString(), "utf-8"), true);
                            View view = getActivity().getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                    return true;
                }
                return false;
            }
        });

        ArrayList<String> tab_names = new ArrayList<>();
        tab_names.add(Session.getword(getActivity(),"search_my_sources"));
        tab_names.add(Session.getword(getActivity(),"search_all"));
        display_custom(tab_names);

        mCallBack.ihave_completed();

    }



    String temp_url = "";
    int search_type = 0;

    private void get_news(String url_append,boolean clear_data){
        progressBar.setVisibility(View.VISIBLE);
        if(clear_data) {
            newses.clear();
            start = newses.size();
            Log.e("start1", String.valueOf(start));
        }else {
            start = newses.size();
            Log.e("start2",String.valueOf(start));
        }

            temp_url="";

            if(search_type==1)

                temp_url = url+url_append+"&search_type="+get_search_str(tab_id);

            else
                temp_url = url + url_append+"&channels="+db.selected_channels(tab_id);


        Log.e("url", temp_url);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/                  progressBar.setVisibility(View.GONE);
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        newses.add(new News(jsonObject,getActivity()));

                    } catch (JSONException e) {
                        news_list_adapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }



                }
                if(newses.size() == 0 ) {
                    no_source.setVisibility(View.VISIBLE);
                }else{
                    no_source.setVisibility(View.GONE);
                }
                if(start==0) {

                    //  news_list_adapter = new News_listAdapter(getActivity(), news);
                    listView.setAdapter(news_list_adapter);
                    news_list_adapter.notifyDataSetChanged();
                }else{
                    news_list_adapter.notifyDataSetChanged();
                    listView.setSelection(start);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                progressBar.setVisibility(View.GONE);
                Log.e("error", volleyError.toString());
//                Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
    private int preLast;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView lw, int firstVisibleItem, int visibleItemCount, int totalItemCount) {



                // Make your calculation stuff here. You have all your
                // needed info from the parameters of this function.

                // Sample calculation to determine if the last
                // item is fully visible.
                final int lastItem = firstVisibleItem + visibleItemCount;

                if(lastItem == totalItemCount)
                {
                    if(preLast!=lastItem)
                    {
                        //to avoid multiple calls for last item
                        Log.d("Last", "Last");
                        preLast = lastItem;
                        start=newses.size();
                       // get_news("key=" + temp,false);
                    }
                }

    }
    ArrayList<com.mamacgroup.hamtest.MyTextView1> custom_names;
    LinearLayout customs_in_header;


    public void display_custom(final ArrayList<String> jsonArray) {

        custom_names = new ArrayList<>();
        customs_in_header.removeAllViewsInLayout();
        customs_in_header.setVisibility(View.VISIBLE);

        for (int i=0;i<jsonArray.size();i++){
            final  com.mamacgroup.hamtest.MyTextView1 temp = new com.mamacgroup.hamtest.MyTextView1(getActivity());
            try {
                temp.setText(jsonArray.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT, 1f);
            temp.setLayoutParams(params);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER);
            temp.setTextSize(16);
            temp.setTextColor(Color.parseColor("white"));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);

            final int finalI = i;
            final int finalI1 = i;

            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(Color.parseColor("white"));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                    }

                    temp.setBackgroundResource(R.drawable.border_full_appcolor);
                    temp.setTextColor(Color.parseColor("white"));

                    search_type=finalI;
                    Log.e("search_type",String.valueOf(search_type));
                    try {
                        get_news("search=" + URLEncoder.encode(search_edit.getText().toString(), "utf-8"), true);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // customizeFagment.custom_item_selected(finalI1);


                }
            });

            custom_names.add(temp);

        }

        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(0).setTextColor(Color.parseColor("white"));
        search_type=0;

        for(int r=custom_names.size()-1;r>=0;r--)
            customs_in_header.addView(custom_names.get(r));

    }

    private String get_search_str(String id_str){

        if(id_str.equals(MainActivity.world_id))
            return MainActivity.world_id_str;

       else if(id_str.equals(MainActivity.sports_id))
            return MainActivity.sports_id_str;

        else if(id_str.equals(MainActivity.economy_id))
            return MainActivity.economy_id_str;

        else
            return MainActivity.world_id_str;

    }

}
