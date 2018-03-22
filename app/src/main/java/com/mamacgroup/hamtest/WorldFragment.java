package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.firebase.crash.FirebaseCrash;
//import com.google.firebase.iid.FirebaseInstanceId;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 7/26/2016.
 */


public class WorldFragment extends Fragment implements AbsListView.OnScrollListener {
    ListView listView;
    GridView gridView;
    int start=0,end=10;
    int type=0;
    String tab_id=MainActivity.world_id;

    LinearLayout progressBar,progressBarfooter;


    ArrayList<News> news;
    ArrayList<News> latest_news;

    News_listAdapter news_list_adapter;
    News_listAdapternoimage news_list_adapternoimage;

   private FragmentTouchListner mCallBack;

    String url = Session.SERVER_URL+"feeds-new3.php?";
    String url_appednd;
    String url_appednd_search="";

    LinearLayout search_view,cancel,search_view_back;
    RelativeLayout search_rl;
    EditText search_edit;
    ImageView sesrch_close;
    ViewFlipper viewFlipper;

    int temp=0;
    int t=0;
    DatabaseHandler db;
   // ViewFlipper viewFlipper;
    LinearLayout pop_up_layout,add_source_ll;
    int selected =0;
    int pre_selected =0;
    LinearLayout new_feeds_btn_ll;

    MyTextView label,cancel_tv,no_source,add_source,pop_up_label;

    ImageView search_btn,settings_btn;
    TextView new_feeds_btn;

    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout page_change_listner;

//    private RecyclerView recyclerView;
//    private RecyclerAdapter mAdapter;

    ViewPager viewPager;
    MyPagerAdapter adapterViewPager;

    int news_item_selected_ti,news_item_ti;



    public interface FragmentTouchListner {

        public void newsclicked(News news);
        public void setselected(String index);
        public  void setttings_btn_clicked();
        public  void source();
        public  void search();
        public void present(String index);
        public void catselected(String id);


        }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (MainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LogoutUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCallBack.setselected("0");

        return inflater.inflate(R.layout.worldfragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
                final View view = getView();
//        FirebaseInstanceId.getInstance().getToken();

        type= getArguments().getInt("type",0);

        Log.e("type",String.valueOf(type));

        if(type==0)
            tab_id=MainActivity.world_id;
        else if(type==2)
            tab_id=MainActivity.sports_id;
        else if(type==1)
            tab_id=MainActivity.economy_id;


        news_item_selected_ti = Session.getTheme(getActivity())?getActivity().getResources().getColor(R.color.aa_header_txt_bg_selected_nt):getActivity().getResources().getColor(R.color.aa_header_txt_bg_selected);
        news_item_ti = Session.getTheme(getActivity())?getActivity().getResources().getColor(R.color.aa_header_txt_bg_unselected):getActivity().getResources().getColor(R.color.aa_header_txt_bg_unselected);

        mCallBack.setselected(String.valueOf(type));
        mCallBack.present(String.valueOf(type));

        viewPager = (ViewPager) view.findViewById(R.id.view3);
        header_tab = (LinearLayout) view.findViewById(R.id.header_tab);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        if(type==0)
        viewPager.setCurrentItem(3);
        else
        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

                if(type==0) {

                    custom_names.get(3 - position).performClick();
                }

                else {
                    custom_names.get(1 - position).performClick();
                }

                viewPager.getAdapter().notifyDataSetChanged();

                animate_header(false);


            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//
//        mAdapter = new RecyclerAdapter(news);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
        db = new DatabaseHandler(getActivity());

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        new_feeds_btn_ll = (LinearLayout) view.findViewById(R.id.new_feeds_btn_ll);
        new_feeds_btn = (TextView) view.findViewById(R.id.new_feeds_btn);
        page_change_listner = (RelativeLayout) view.findViewById(R.id.page_change_listner);
        listView=(ListView)view.findViewById(R.id.listView);

//        listView.setOnTouchListener(new OnSwipeTouchListner(getActivity()){
//            public void onSwipeTop() {
//
//            }
//            public void onSwipeRight() {
//
//                if(selected!=0)
//                 custom_names.get(selected-1).performClick();
//
//            }
//            public void onSwipeLeft() {
//                if(selected!=custom_names.size()-1)
//                    custom_names.get(selected+1).performClick();
//
//            }
//            public void onSwipeBottom() {
//
//            }
//        });

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        swipeRefreshLayout.setRefreshing(true);
                        get_latest_news("1");
                    }
                }
        );

        new_feeds_btn_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setSelection(0);
               // listView.smoothScrollToPosition(0);
                new_feeds_btn_ll.setVisibility(View.GONE);
                t=0;
                set_refresh_timer();

            }
        });

        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_progress, null, false);
        progressBarfooter = (LinearLayout) footerView.findViewById(R.id.progressBarfooter);

        label = (MyTextView) view.findViewById(R.id.lable);

        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper);
        no_source = (MyTextView) view.findViewById(R.id.no_source);
        no_source.setText(Session.getword(getActivity(),"error_no_channels_subscribed"));

        pop_up_label = (MyTextView) view.findViewById(R.id.pop_up_label);
        pop_up_label.setText(Session.getword(getActivity(),"title_select_sources"));

        add_source = (MyTextView) view.findViewById(R.id.add_source_tv);
        add_source.setText(Session.getword(getActivity(),"title_add_source"));
        add_source_ll = (LinearLayout) view.findViewById(R.id.add_source_ll);
        pop_up_layout = (LinearLayout) view.findViewById(R.id.pop_up_layout);
        pop_up_layout.setVisibility(View.GONE);
        cancel = (LinearLayout) view.findViewById(R.id.cancel_source_ll);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                pop_up_layout.setVisibility(View.GONE);
                for (int j = 0; j < custom_names.size(); j++) {
                    custom_names.get(j).setTextColor(Color.parseColor("white"));
                    custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                }
                //  custom_names.get(pre_selected).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
                // custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_empty_appcolor);

                custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_full_appcolor);
                custom_names.get(pre_selected).setTextColor(Color.parseColor("white"));
                selected=pre_selected;

            }
        });


        cancel_tv = (MyTextView) view.findViewById(R.id.cancel_source_tv);
        cancel_tv.setText(Session.getword(getActivity(),"cancel"));
        label.setText(Session.getword(getActivity(),"tab_news"));

        progressBar = (LinearLayout) view.findViewById(R.id.progressBar);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        search_btn = (ImageView) view.findViewById(R.id.search_btn);
        settings_btn = (ImageView) view.findViewById(R.id.set_btn);

        search_view_back = (LinearLayout) view.findViewById(R.id.search_view_back);
        search_rl= (RelativeLayout) view.findViewById(R.id.search_rl);
     //   Transition transition = new Slide(Gravity.BOTTOM);
       // search_rl.setLayoutTransition(transition);de
        search_view = (LinearLayout) view.findViewById(R.id.search_view);
        sesrch_close = (ImageView) view.findViewById(R.id.search_close);
        sesrch_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url_appednd_search="";
                search_view.setVisibility(View.GONE);
            }
        });
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        search_edit.setHint(Session.getword(getActivity(), "empty_search"));
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.length() > 2)
                        get_news("key=" + URLEncoder.encode(s.toString(), "utf-8"), true,true);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.setttings_btn_clicked();
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.search();
//                if(search_view.getVisibility()==View.VISIBLE) {
//                    search_view.setVisibility(View.GONE);
//                    search_view_back.setVisibility(View.VISIBLE);
//                    url_appednd_search="";
//                    get_news(url_appednd,true);
//                }
//                else {
//
//                    search_view.setVisibility(View.VISIBLE);
//
//                    search_view_back.setVisibility(View.GONE);
//
//                }

            }
        });


        //viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        gridView = (GridView)view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select_list_item(position,true,true);
                temp=position;
            }
        });


                customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);



        listView.setScrollingCacheEnabled(false);
        listView.setAnimationCacheEnabled(false);
        listView.setSmoothScrollbarEnabled(true);

        boolean pauseOnScroll = false; // or true
        boolean pauseOnFling = true; // or false

        listView.addFooterView(footerView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(news.get(position).type.equals("news")){
                        mCallBack.newsclicked(news.get(position));
                    }else{
                        if (news.get(position).video.equals("") && news.get(position).mp4.equals("")) {
                            Intent intent = new Intent(getActivity(), WebviewActivity.class);
                            intent.putExtra("link", news.get(position).link);
                            startActivity(intent);
                        }else{
                            if(!news.get(position).mp4.equals("")){
                                Intent intent = new Intent(getActivity(), AndroidVideoPlayerActivity.class);
                                intent.putExtra("video", news.get(position).mp4);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getActivity(), YoutubePlayer.class);
                                intent.putExtra("video", news.get(position).video);
                                startActivity(intent);
                            }
                        }
                    }

                }
            });
                news = new ArrayList<>();
        latest_news = new ArrayList<>();
                news_list_adapter=new News_listAdapter(getActivity(),news,(MainActivity) mCallBack);

        news_list_adapternoimage=new News_listAdapternoimage(getActivity(),news,(MainActivity) mCallBack);
                listView.setAdapter(news_list_adapter);

                ArrayList<String> tab_names = new ArrayList<>();



                    tab_names.add(Session.getword(getActivity(),"latest"));
                    tab_names.add(Session.getword(getActivity(),"source"));
                if(type==0){
                    tab_names.add(Session.getword(getActivity(),"most_view"));
                    tab_names.add(Session.getword(getActivity(),"urgent"));
                }


                display_custom(tab_names);

        if(db.all_selected_channels(MainActivity.world_id).equals("0")) {
//            Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
            viewFlipper.setDisplayedChild(1);
            add_source_ll.setVisibility(View.VISIBLE);
            no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
        }
        else {
            label.setText(Session.getword(getActivity(),"latest"));
            viewFlipper.setDisplayedChild(0);
           // get_news("channels=" + db.all_selected_channels(MainActivity.world_id),true,true);
        }
        add_source_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.source();
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (pop_up_layout.getVisibility() == View.VISIBLE ) {
                        pop_up_layout.setVisibility(View.GONE);
                        for (int j = 0; j < custom_names.size(); j++) {
                            custom_names.get(j).setTextColor(Color.parseColor("white"));
                            custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                        }
                        //  custom_names.get(pre_selected).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
                        // custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_empty_appcolor);

                        custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_full_appcolor);
                        custom_names.get(pre_selected).setTextColor(Color.parseColor("white"));
                        selected=pre_selected;

                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

      //  set_refresh_timer();
//        try{
//            Log.e("token",FirebaseInstanceId.getInstance().getToken());
//            FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//


        }

    ArrayList<MyTextView1> custom_names;
    LinearLayout customs_in_header;


    public void display_custom(final ArrayList<String> jsonArray) {

        custom_names = new ArrayList<>();
        customs_in_header.removeAllViewsInLayout();
        customs_in_header.setVisibility(View.VISIBLE);
        for (int i=0;i<jsonArray.size();i++){
            final  MyTextView1 temp = new MyTextView1(getActivity());
            try {
                temp.setText(jsonArray.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            temp.setLayoutParams(params);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER);
            temp.setTextSize(16);
            temp.setTextColor(Color.parseColor("white"));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);

            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(Color.parseColor("white"));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                    }

                    custom_names.get(finalI).setBackgroundResource(R.drawable.border_full_appcolor);
                    custom_names.get(finalI).setTextColor(Color.parseColor("white"));
                    Log.e(String.valueOf(finalI2),String.valueOf(selected));

                    if(type==0)
                        viewPager.setCurrentItem(3-finalI,true);
                    else
                        viewPager.setCurrentItem(1-finalI,true);

                    if(finalI2==selected){


                        adapterViewPager.update_fragment(viewPager.getCurrentItem());

                    }else {

                        //tabclicked(finalI2, true,true);
                        pre_selected=selected;
                        selected=finalI2;
                    }
                }
            });

            custom_names.add(temp);

        }
        for (int j = 0; j < jsonArray.size(); j++) {
            custom_names.get(j).setTextColor(Color.parseColor("white"));
            custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
        }
        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(0).setTextColor(Color.parseColor("white"));

        for(int r=custom_names.size()-1;r>=0;r--)
            customs_in_header.addView(custom_names.get(r));

    }


    public void select_list_item(int posi,boolean type,boolean show_prog){
//        if (selected == 2) {
//            Log.e("cat",db.selected_channels(categories.get(posi).id));
//            Log.e("cattt",categories.get(posi).id);
//
//            if (db.selected_channels(categories.get(posi).id).equals("0")) {
////            Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
//                pop_up_layout.setVisibility(View.GONE);
//                viewFlipper.setDisplayedChild(1);
//                add_source_ll.setVisibility(View.VISIBLE);
//                no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
//            } else {
//                viewFlipper.setDisplayedChild(0);
//                label.setText(Session.getword(getActivity(), categories.get(posi).get_title(getActivity())));
//                get_news("chanels=" + db.selected_channels(categories.get(posi).id), type,show_prog);
//            }
//
//        } else{

            viewFlipper.setDisplayedChild(0);
            label.setText(chanels.get(posi).get_ch_title(getActivity()));
            get_news("chanels=" + chanels.get(posi).ch_id, type,show_prog);

       // }



    }
    String temp_url = "";

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
            start = news.size();
            Log.e("start1", String.valueOf(start));
            preLast=0;
            news_list_adapter.notifyDataSetChanged();
            news_list_adapternoimage.notifyDataSetChanged();
        }else {
            start = news.size();
            Log.e("start2",String.valueOf(start));
        }
        pop_up_layout.setVisibility(View.GONE);
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
                        news_list_adapternoimage.notifyDataSetChanged();
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
                }
                if(start==0) {

                      //  news_list_adapter = new News_listAdapter(getActivity(), news);
                        if(selected==2) {
                            listView.setAdapter(news_list_adapternoimage);
                            news_list_adapter.notifyDataSetChanged();
                        }
                        else {
                            listView.setAdapter(news_list_adapter);
                            news_list_adapternoimage.notifyDataSetChanged();
                        }
                }else{
                    int x = listView.getScrollX();
                    int t=listView.getMaxScrollAmount();
                    news_list_adapter.notifyDataSetChanged();
                    news_list_adapternoimage.notifyDataSetChanged();
                    listView.setScrollX(x);
                    //listView.setSelection(start);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                if(volleyError.toString().equals("com.android.volley.TimeoutError"))

                        if(news.size()>0){
                            if(selected!=custom_names.size()-3){

                                tabclicked(selected,false,false);
                            }
                            else{
                                select_list_item(temp,false,false);
                            }
                        }
                        else{
                            if(selected!=custom_names.size()-3){

                                tabclicked(selected,false,true);
                            }
                            else{
                                select_list_item(temp,false,true);
                            }
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

    private void tabclicked(int index,boolean clear_data,boolean show_progress){
        viewFlipper.setDisplayedChild(0);
        switch (index){
            case 0:
                label.setText(Session.getword(getActivity(),"latest"));
                if(db.all_selected_channels(MainActivity.world_id).equals("0")) {
//                    Toast.makeText(getActivity(),"You have not selected any sources",Toast.LENGTH_SHORT).show();
                    viewFlipper.setDisplayedChild(1);
                    add_source_ll.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
                }
                else {

                    get_news("channels=" + db.all_selected_channels(MainActivity.world_id), clear_data,show_progress);
                }


                break;
            case 1:

                    label.setText(Session.getword(getActivity(), "source"));
                    pop_up_label.setText(Session.getword(getActivity(), "title_select_sources"));
                    get_chanels(db.all_selected_channels(MainActivity.world_id));

                break;

            case 2:
                    label.setText(Session.getword(getActivity(),"country"));

                    /*pop_up_label.setText(Session.getword(getActivity(), "select_country"));
                    get_categories();
*/
                if(db.all_selected_channels(MainActivity.world_id).equals("0")) {
                    viewFlipper.setDisplayedChild(1);
                    add_source_ll.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
                }
                else {

                    get_news("channels=" + db.all_selected_channels(MainActivity.world_id), clear_data,show_progress);
                }
                break;

            case 3:
                label.setText(Session.getword(getActivity(),"urgent"));
                if(db.all_selected_channels(MainActivity.world_id).equals("0")) {
//                    Toast.makeText(getActivity(),"You have not selected any sources",Toast.LENGTH_SHORT).show();
                    viewFlipper.setDisplayedChild(1);
                    add_source_ll.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
                }
                else {
                    get_news("type=urgent&channels=" + db.all_selected_channels(MainActivity.world_id), clear_data,show_progress);
                }
                break;

        }
    }

    ArrayList<Categories> categories;
    ArrayList<Chanel> chanels;
    PopupListAdapter categoryAdapter ;
    PopupListAdapterChanels categoryAdapterChanels ;

    private void get_categories(){
       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
       */ String url = Session.SERVER_URL+"categories.php?type=country";
        progressBar.setVisibility(View.VISIBLE);
        Log.e("url",url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                progressBar.setVisibility(View.GONE);
                categories =new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        categories.add(new Categories(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(categories.size()==0){
                    viewFlipper.setDisplayedChild(1);
                    add_source_ll.setVisibility(View.GONE);
                    no_source.setText(Session.getword(getActivity(), "empty_no_channels_in_category"));
                }else {
                    viewFlipper.setDisplayedChild(0);
                    categoryAdapter = new PopupListAdapter(getActivity(), categories);
                    gridView.setAdapter(categoryAdapter);
                    pop_up_layout.setVisibility(View.VISIBLE);
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                pop_up_layout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Log.e("error", volleyError.toString());
               // Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }



    private void get_chanels(String list){
        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please_wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
       */
        progressBar.setVisibility(View.VISIBLE);
        String url = Session.NOTIFY_SERVER_URL+"channels2.php?chanels="+list;
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                chanels =new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        chanels.add(new Chanel(jsonObject,"0"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                categoryAdapterChanels = new PopupListAdapterChanels(getActivity(),chanels);
                gridView.setAdapter(categoryAdapterChanels);
                pop_up_layout.setVisibility(View.VISIBLE);
                categoryAdapterChanels.notifyDataSetChanged();
                if(chanels.size()==0){
                    pop_up_layout.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(),"You have not selected any sources",Toast.LENGTH_SHORT).show();
                    viewFlipper.setDisplayedChild(1);
                    add_source_ll.setVisibility(View.VISIBLE);
                    no_source.setText(Session.getword(getActivity(), "error_no_channels_subscribed"));
//                    for (int j = 0; j < custom_names.size(); j++) {
//                        custom_names.get(j).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
//                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
//                    }
//                  //  custom_names.get(pre_selected).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
//                   // custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_empty_appcolor);
//
//                    custom_names.get(pre_selected).setBackgroundResource(R.drawable.border_full_appcolor);
//                    custom_names.get(pre_selected).setTextColor(Color.parseColor("white"));
//                    selected=pre_selected;

                }else{
                    viewFlipper.setDisplayedChild(0);
                    pop_up_layout.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /*if(progressDialog!=null)
                    progressDialog.dismiss();
                */
                progressBar.setVisibility(View.GONE);
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    private int preLast;
// Initialization stuff.
//    yourListView.setOnScrollListener(this);

// ... ... ...

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount)
    {

        switch(lw.getId())
        {
            case R.id.listView:

                // Make your calculation stuff here. You have all your
                // needed info from the parameters of this function.

                // Sample calculation to determine if the last

                // item is fully visible.
                final int lastItem = firstVisibleItem + visibleItemCount;

                if(lastItem == totalItemCount-25)
                {
                    if(preLast!=lastItem)

                    {final int delay = 1000*60;
                        //to avoid multiple calls for last item
                        Log.d("Last", "Last");
                        preLast = lastItem;
                        start=news.size();

                        if(selected!=custom_names.size()-3){

                            tabclicked(selected,false,false);
                        }
                        else{
                            select_list_item(temp,false,false);
                        }



                    }
                }
        }
    }
    final Handler h = new Handler();
    final Handler h1 = new Handler();
    final int delay = 1000*60;//milliseconds
    final int delay1 = 1000*10;

    final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.e("time","ticked");
            if(t==0) {
                h.postDelayed(this, delay);
                // tabclicked(selected,true);
                get_latest_news("0");
            }else{
                if(getView()!=null) {
                    h1.postDelayed(this, delay1);
                    // tabclicked(selected,true);
                    new_feeds_btn_ll.setVisibility(View.GONE);
                    t = 0;
                    set_refresh_timer();
                }else{
                    t = 0;
                    set_refresh_timer();
                }

            }
        }
    };

    private void set_refresh_timer(){
        if(t==0)
            h.postDelayed(r, delay);
        else
            h1.postDelayed(r, delay1);
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        if(t==0) {
            h.removeCallbacks(r);
        }else{
            h1.removeCallbacks(r);

        }

        Log.e("timer","removed");
    }

    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        if(t==0) {
            h.removeCallbacks(r);
            h.postDelayed(r, delay);
        }else{
            h1.removeCallbacks(r);
            h1.postDelayed(r,delay1);

        }
//        AppController.getInstance().getDefaultTracker().setScreenName("GeneralNews");
//        AppController.getInstance().getDefaultTracker().send(new HitBuilders.ScreenViewBuilder().build());

        Log.e("timer","added");
    }

    private void get_latest_news(final String value) {

        try {
            pop_up_layout.setVisibility(View.GONE);
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
                                set_refresh_timer();

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

            if(news.size()>0)
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        private  int NUM_ITEMS = 4;
        HashMap<Integer,UpdatableFragment> fragment;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragment = new HashMap<Integer, UpdatableFragment>();

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            if(type==0)
                return 4;
            else
                return 2;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            if(type==0)
            switch (position) {


                case 0: // Fragment # 0 - This will show FirstFragment
                    fragment.put(0,NewsRecycleListFragment.newInstance(0, "Page # 1", (MainActivity) mCallBack ,tab_id));
                    return (Fragment) fragment.get(0);

                case 1: // Fragment # 0 - This will show FirstFragment different title
                    fragment.put(1,NewsRecycleListFragment.newInstance(1, "Page # 2",(MainActivity) mCallBack,tab_id));
                    return (Fragment) fragment.get(1);
                case 2: // Fragment # 1 - This will show SecondFragment
                    fragment.put(2,NewsListFragmentWithChannels.newInstance(2, "Page # 3",tab_id));
                    return (Fragment) fragment.get(2);
                case 3: // Fragment # 1 - This will show SecondFragment
                    fragment.put(3,NewsRecycleListFragment.newInstance(3, "Page # 4", (MainActivity) mCallBack,tab_id));
                    return (Fragment) fragment.get(3);
                default:
                    return null;
            }


            else
                switch (position) {


                    case 0: // Fragment # 1 - This will show SecondFragment
                                fragment.put(0,NewsListFragmentWithChannels.newInstance(2, "Page # 3",tab_id));
                                return (Fragment) fragment.get(0);
                    case 1: // Fragment # 1 - This will show SecondFragment
                                fragment.put(1,NewsRecycleListFragment.newInstance(3, "Page # 4", (MainActivity) mCallBack,tab_id));
                                return (Fragment) fragment.get(1);
                    default:
                        return null;
                }

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }




        @Override
        public int getItemPosition(Object object) {
            if (object instanceof UpdatableFragment) {
                ((UpdatableFragment) object).update();
            }
            //don't return POSITION_NONE, avoid fragment recreation.
            return super.getItemPosition(object);
        }


        public void update_fragment(int pos){
            fragment.get(pos).update();
        }

    }

    LinearLayout header_tab;
    public void animate_header(boolean direction){

//        if(direction)
//            header_tab.setVisibility(View.GONE);
//        else
//            header_tab.setVisibility(View.VISIBLE);

    }


}
