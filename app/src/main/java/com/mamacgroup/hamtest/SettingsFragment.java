package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
 * Created by HP on 7/26/2016.
 */


public class SettingsFragment extends Fragment {

    GridView listView;
    RecyclerView channels_listview;
    ArrayList<Categories> categories;
    ArrayList<SectionedItem> sectionedItems;
    ArrayList<SectionedItem> sectionedItems_all;

    CategoryAdapter categoryAdapter ;
    SourcesMainAdapter sourcesMainAdapter;
    MultiViewTypeAdapterSectionedChannels sectionedAdapter;
    News_listAdapter news_list_adapter;
    FragmentTouchListner mCallBack;
    TextView label;
    ViewFlipper viewFlipper;
    EditText search_edit;

    public interface FragmentTouchListner {
        public  void back();
        public void catselected(Categories categories);
        public void setselected(String index);
        public  void setttings_btn_clicked();
        public  void chanel_selected(Chanel ch);
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
        return inflater.inflate(R.layout.settingsfragmentnews, container, false);
    }
    DatabaseHandler databaseHandler;
   // RecyclerView chipRecyclerView;
    //ListChipsAdapter listChipsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
         databaseHandler = new DatabaseHandler(getActivity());
        View view = getView();
         //chipRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        //chipRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //chipRecyclerView.setHasFixedSize(true);

        mCallBack.setselected("1");

        categories = new ArrayList<>();
        sectionedItems = new ArrayList<>();
        //listChipsAdapter = new ListChipsAdapter(categories,getContext());
        //chipRecyclerView.setAdapter(listChipsAdapter);

        sourcesMainAdapter = new SourcesMainAdapter(getActivity(),categories,getChildFragmentManager());
        categoryAdapter = new CategoryAdapter(getActivity(),categories);
                customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
        channels_listview = (RecyclerView) view.findViewById(R.id.recyclerView);
        channels_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
                listView=(GridView)view.findViewById(R.id.listView);
         viewFlipper = (ViewFlipper)view.findViewById(R.id.viewFlipper2);
        viewFlipper.setDisplayedChild(1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.catselected(categories.get(position));
            }
        });

//        channels_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mCallBack.chanel_selected(chanels.get(position));
//            }
//        });


        label = (TextView) view.findViewById(R.id.label1);
        label.setText(Session.getword(getActivity(), "title_select_sources"));
        listView.setAdapter(categoryAdapter);

                ArrayList<String> tab_names = new ArrayList<>();
                tab_names.add(Session.getword(getActivity(),"settings_all"));
                tab_names.add(Session.getword(getActivity(),"settings_my_sources"));
                //display_custom(tab_names);

        ImageView back_btn = (ImageView) view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back();
            }
        });

        back_btn.setVisibility(View.INVISIBLE);

        ImageView set_btn = (ImageView) view.findViewById(R.id.set_btn);
        set_btn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.setttings_btn_clicked();
            }
        });

        set_btn.setVisibility(View.INVISIBLE);

        //get_categories();

       // get_chanels(databaseHandler.all_selected_channels_new("0"));

        search_edit = (EditText) view.findViewById(R.id.et_search);
        search_edit.setHint(Session.getword(getActivity(), "empty_search"));


        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                    sectionedItems.clear();
                    sectionedAdapter.notifyDataSetChanged();
                    for(int i=0;i<sectionedItems_all.size();i++){
                        sectionedItems.add(sectionedItems_all.get(i));
                    }

                    sectionedAdapter.notifyDataSetChanged();


                    return true;
                }
                return false;
            }
        });

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("before",String.valueOf(s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s != null) {

                        if(s.toString().length()>0){

                            sectionedItems.clear();
                            sectionedAdapter.notifyDataSetChanged();

                            for (int i = 0; i < sectionedItems_all.size(); i++) {
                                if (sectionedItems_all.get(i).type.equals("2")) {
                                    if(sectionedItems_all.get(i).chanel.ch_title.contains(s) ||
                                            sectionedItems_all.get(i).chanel.ch_title_ar.contains(s)) {
                                        sectionedItems.add(sectionedItems_all.get(i));
                                        Log.e("aerch",String.valueOf(s));

                                    }
                                }
                            }
                            sectionedAdapter.notifyDataSetChanged();
                        }
                     else {
                            sectionedItems.clear();
                            sectionedAdapter.notifyDataSetChanged();
                            for(int i=0;i<sectionedItems_all.size();i++){
                                sectionedItems.add(sectionedItems_all.get(i));
                            }

                            sectionedAdapter.notifyDataSetChanged();
                        }
                }else{
                        sectionedItems.clear();
                        sectionedAdapter.notifyDataSetChanged();
                        for(int i=0;i<sectionedItems_all.size();i++){
                            sectionedItems.add(sectionedItems_all.get(i));
                        }

                        sectionedAdapter.notifyDataSetChanged();

                    }


                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.e("after",String.valueOf(s.toString()));

            }
        });

        get_chanels(databaseHandler.all_selected_channels_new("0"));

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
            temp.setTextSize(12);
            temp.setTextColor(getResources().getColor(R.color.aa_app_blue));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);

            final int finalI = i;
            final int finalI1 = i;

            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(getResources().getColor(R.color.aa_app_blue));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                    }

                    temp.setBackgroundResource(R.drawable.border_full_appcolor);
                    temp.setTextColor(Color.parseColor("white"));
                   // customizeFagment.custom_item_selected(finalI1);
                    viewFlipper.setDisplayedChild(finalI);

                    if(finalI==1)
                        get_chanels(databaseHandler.all_selected_channels_new("0"));

                }
            });

            custom_names.add(temp);

        }

        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(0).setTextColor(Color.parseColor("white"));

        for(int r=custom_names.size()-1;r>=0;r--)
            customs_in_header.addView(custom_names.get(r));



        custom_names.get(1).performClick();
        customs_in_header.setVisibility(View.GONE);

    }



    private void get_categories(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Session.SERVER_URL+"categories.php";
        Log.e("cat_url", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());

              //  get_chanels(databaseHandler.all_selected_channels("0"));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                    }
                },2000);

                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        categories.add(new Categories(jsonObject));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                categoryAdapter.notifyDataSetChanged();

              //  sourcesMainAdapter.notifyDataSetChanged();
               // listChipsAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               // get_chanels(databaseHandler.all_selected_channels("0"));
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    private void displayselectedchannels(){

    }

    ArrayList<Chanel> chanels;
    SelectedChannelsAdapter selectedChannelsAdapter;



    private void get_chanels(String list){


        String url;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
       // progressBar.setVisibility(View.VISIBLE);
        if(Session.get_user_id(getActivity()).equals("-1")) {
            url = Session.NOTIFY_SERVER_URL + "chanels_cat.php?chanels=" + list;
        }else{
            url = Session.NOTIFY_SERVER_URL + "chanels_cat.php?chanels=" + list+"&member_id="+Session.get_user_id(getActivity());
        }
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

              //  progressBar.setVisibility(View.GONE);



                categories =new ArrayList<>();
                sectionedItems.clear();


                if(jsonArray.length()>0){
                    sectionedItems.add(new SectionedItem(Session.getword(getActivity(),"Categories")));
                }

                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Categories categories = new Categories(jsonObject);
                        sectionedItems.add(new SectionedItem(categories));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Categories categories = new Categories(jsonObject);
                        sectionedItems.add(new SectionedItem(categories.get_title(getActivity())));

                        for(int j=0;j<categories.chanels.size();j++){

                            sectionedItems.add(new SectionedItem(categories.chanels.get(j)));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                sectionedItems_all = new ArrayList<>(sectionedItems);
                sectionedAdapter  = new MultiViewTypeAdapterSectionedChannels(sectionedItems,getActivity(),(NewsRecycleListActivity)mCallBack);
                channels_listview.setAdapter(sectionedAdapter);

                sectionedAdapter.notifyDataSetChanged();


//                if(chanels.size()==0){
//                  //  Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
//                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog!=null)
                    progressDialog.dismiss();

              //  progressBar.setVisibility(View.GONE);
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }



}
