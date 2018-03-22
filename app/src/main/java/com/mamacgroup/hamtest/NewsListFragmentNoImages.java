package com.mamacgroup.hamtest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 1/11/17.
 */

public class NewsListFragmentNoImages extends Fragment implements UpdatableFragment{

    News_listAdapternoimage news_list_adapter;
    ListView listView;
    ArrayList<News> news;
    ArrayList<News> latest_news;
    LinearLayout progressBar,progressBarfooter;
    String url = Session.SERVER_URL+"feeds-new3.php?";
    String url_appednd;
    String url_appednd_search="";

    ViewFlipper viewFlipper;
    int temp=0;
    int t=0;
    DatabaseHandler db;
    LinearLayout new_feeds_btn_ll;
    TextView new_feeds_btn;
    SwipeRefreshLayout swipeRefreshLayout;
    MyTextView label,cancel_tv,no_source,add_source,pop_up_label;
    LinearLayout pop_up_layout,add_source_ll;
    WorldFragment.FragmentTouchListner mCallBack;

    private String title;
    private int page;
    String tab_id;

    final Handler h = new Handler();
    final int delay = 1000*60;//milliseconds
    final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.e("time","ticked");
            if(news.size()>0)
                get_latest_news("0");
        }
    };

    public static NewsListFragmentNoImages newInstance(int page, String title,String tab_id) {
        NewsListFragmentNoImages fragmentFirst = new NewsListFragmentNoImages();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("someId",tab_id);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        tab_id = getArguments().getString("someId","0");
        mCallBack = (MainActivity) getParentFragment().getActivity();
    }
    WorldFragment worldFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.newslistfragment, container, false);

        worldFragment = (WorldFragment) getParentFragment();
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        new_feeds_btn_ll = (LinearLayout) view.findViewById(R.id.new_feeds_btn_ll);
        new_feeds_btn_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setSelection(0);
                // listView.smoothScrollToPosition(0);
                new_feeds_btn_ll.setVisibility(View.GONE);

            }
        });

        new_feeds_btn = (TextView) view.findViewById(R.id.new_feeds_btn);
        listView=(ListView)view.findViewById(R.id.listView);
        listView.setScrollingCacheEnabled(false);
        listView.setAnimationCacheEnabled(false);
        listView.setSmoothScrollbarEnabled(true);
        listView.setOnScrollListener(new OnVerticalScrollDirectionListner() {
            @Override
            public void onVerticalDirectionChanged(ListenerArgs args) {
                if (args.isScrollable()) {
                    if (!args.isTopItemReached()) {
                        if (args.isScrollingUp()) {
                            // We've scrolled up the list to see the top items

                            worldFragment.animate_header(false);
                        } else {
                            // We've scrolled down the list to see the bottom items

                            worldFragment.animate_header(true);

                            if(args.islastreached())
                            {
                                get_news(url_appednd,false,false);
                            }
                        }
                    } else {
                        // The first item (in adapter) gets visible.
                    }
                } else {
                    // The ListView is not scrollable.
                }
            }});

        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_progress, null, false);
        progressBarfooter = (LinearLayout) footerView.findViewById(R.id.progressBarfooter);
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper);
        label = (MyTextView) view.findViewById(R.id.lable);
        no_source = (MyTextView) view.findViewById(R.id.no_source);
        no_source.setText(Session.getword(getActivity(),"error_no_channels_subscribed"));
        add_source = (MyTextView) view.findViewById(R.id.add_source_tv);
        add_source.setText(Session.getword(getActivity(),"title_add_source"));
        add_source_ll = (LinearLayout) view.findViewById(R.id.add_source_ll);
        add_source_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.catselected(tab_id);
            }
        });

        progressBar = (LinearLayout) view.findViewById(R.id.progressBar);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        swipeRefreshLayout.setRefreshing(true);
                        get_latest_news("1");
                    }
                }
        );
        news = new ArrayList<>();
        latest_news = new ArrayList<>();

        news_list_adapter=new News_listAdapternoimage(getActivity(),news,(MainActivity) mCallBack);


        listView.setAdapter(news_list_adapter);


        db = new DatabaseHandler(getActivity());

        if(db.selected_channels(MainActivity.world_id).equals("0")) {
//            Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
            viewFlipper.setDisplayedChild(1);
            add_source_ll.setVisibility(View.VISIBLE);
            no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
        }
        else {
            //label.setText(Session.getword(getActivity(),"latest"));
            viewFlipper.setDisplayedChild(0);
            get_news("channels=" + db.selected_channels(tab_id),true,true);
        }

        set_refresh_timer();
        return  view;


    }


    private void set_refresh_timer(){


        h.postDelayed(r, delay);


    }


    String temp_url = "";


    private void get_latest_news(final String value) {

        try {

            temp_url = url + url_appednd + "&first_id=" + news.get(0).id;

            Log.e("url", temp_url);
            // final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setMessage("please_wait");
            //progressDialog.show();
            //progressDialog.setCancelable(false);
//        temp_url=temp_url
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.e("response", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    latest_news.clear();
                    if(jsonArray.length()==0){
                        //Toast.makeText(getActivity(),"There is no new feeds at this moment",Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("json", jsonObject.toString());
                            latest_news.add(new News(jsonObject,getActivity()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("lates_news",String.valueOf(latest_news.size()));
                        if(latest_news.size()>0){
                            if(value.equals("0")) {
                                new_feeds_btn.setText(latest_news.size()+ " "+ Session.getword(getActivity(),"scroll_up_message"));
                                t=1;
                                new_feeds_btn_ll.setVisibility(View.VISIBLE);
                                for(int j=latest_news.size()-1;j>=0;j--){
                                    try {
                                        news.add(0,latest_news.get(j));
                                    }catch (Exception ex){
                                        break;
                                    }
                                }
                                int t1=listView.getMaxScrollAmount();
                                int x=listView.getScrollX();
                                int scroll_posi=t1-x;
                                news_list_adapter.notifyDataSetChanged();
//                                listView.smoothScrollToPosition(0);
                                listView.setScrollX(listView.getMaxScrollAmount()-scroll_posi);
                                swipeRefreshLayout.setRefreshing(false);

                              //  set_refresh_timer();

                            }else {
                                for(int j=latest_news.size()-1;j>=0;j--){
                                    try {
                                        news.add(0,latest_news.get(j));
                                    }catch (Exception ex){
                                        break;
                                    }
                                }
                                news_list_adapter.notifyDataSetChanged();
                                listView.smoothScrollToPosition(0);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                        }

                        //news_list_adapter.notifyDataSetChanged();
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

                    //  Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();

                }
            });

            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void get_news(String url_append,boolean clear_data,boolean show_progress){
        if(show_progress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBarfooter.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            progressBarfooter.setVisibility(View.VISIBLE);
        }

        String lastid = "";

        if(clear_data) {
            news.clear();
            news_list_adapter.notifyDataSetChanged();

        }else {

        }

        if(news.size()==0){
            if(url_append.equals("")) {
                temp_url = url + url_append + url_appednd_search;
            }
            else {
                temp_url = url + url_append + "&" + url_appednd_search;
            }

        }else{
            if(url_append.equals("")) {
                temp_url = url + url_append + url_appednd_search + "last_id=" + news.get(news.size()-1).id ;
            }
            else {
                temp_url = url + url_append + "&" + url_appednd_search + "last_id=" + news.get(news.size()-1).id ;
            }

        }

        Log.e("url", temp_url);
        url_appednd = url_append;
        // final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setMessage("please_wait");
        //progressDialog.show();
        //progressDialog.setCancelable(false);
//        temp_url=temp_url
        final  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/                  progressBar.setVisibility(View.GONE);
                progressBarfooter.setVisibility(View.GONE);
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        news.add(new News(jsonObject,getActivity()));

                    } catch (JSONException e) {
                        news_list_adapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }



                }
                if(news.size() == 0 ) {
                    viewFlipper.setDisplayedChild(1);
                    no_source.setText(Session.getword(getActivity(), "no_feeds"));
                    add_source_ll.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(),"no feeds to display",Toast.LENGTH_SHORT).show();
                }else{
                    viewFlipper.setDisplayedChild(0);
                    int x = listView.getScrollX();
                    int t=listView.getMaxScrollAmount();
                    news_list_adapter.notifyDataSetChanged();
                    listView.setScrollX(x);
                }
                    //listView.setSelection(start);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                if(volleyError.toString().equals("com.android.volley.TimeoutError")){

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    progressBarfooter.setVisibility(View.GONE);
                }
                Log.e("error", volleyError.toString());
//                Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void update() {
        listView.setSelection(0);
    }
}
