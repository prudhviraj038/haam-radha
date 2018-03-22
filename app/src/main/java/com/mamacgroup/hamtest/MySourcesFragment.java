package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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


public class MySourcesFragment extends Fragment {

    GridView listView;
    ListView channels_listview;
    ArrayList<Categories> categories;
    CategoryAdapter categoryAdapter ;
    SourcesMainAdapter sourcesMainAdapter;
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
        return inflater.inflate(R.layout.settingsfragment, container, false);
    }
    DatabaseHandler databaseHandler;

     //RecyclerView chipRecyclerView;
    //ListChipsAdapter listChipsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
         databaseHandler = new DatabaseHandler(getActivity());
        View view = getView();
         //chipRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        //chipRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //chipRecyclerView.setHasFixedSize(true);

        mCallBack.setselected("4");

        categories = new ArrayList<>();
        //listChipsAdapter = new ListChipsAdapter(categories,getContext());
        //chipRecyclerView.setAdapter(listChipsAdapter);

        sourcesMainAdapter = new SourcesMainAdapter(getActivity(),categories,getChildFragmentManager());
        categoryAdapter = new CategoryAdapter(getActivity(),categories);
                customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
        channels_listview = (ListView) view.findViewById(R.id.channels_listview);
                listView=(GridView)view.findViewById(R.id.listView);
         viewFlipper = (ViewFlipper)view.findViewById(R.id.viewFlipper2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.catselected(categories.get(position));
            }
        });
        label = (TextView) view.findViewById(R.id.label1);
        label.setText(Session.getword(getActivity(), "title_select_sources"));
        listView.setAdapter(categoryAdapter);

                ArrayList<String> tab_names = new ArrayList<>();
                tab_names.add(Session.getword(getActivity(),"settings_all"));
                tab_names.add(Session.getword(getActivity(),"settings_my_sources"));
                display_custom(tab_names);

        ImageView back_btn = (ImageView) view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back();
            }
        });

        //back_btn.setVisibility(View.INVISIBLE);

        ImageView set_btn = (ImageView) view.findViewById(R.id.set_btn);
        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.setttings_btn_clicked();
            }
        });

        set_btn.setVisibility(View.INVISIBLE);

        get_categories();

       // get_chanels(databaseHandler.all_selected_channels_new("0"));

        search_edit = (EditText) view.findViewById(R.id.et_search);
        search_edit.setHint(Session.getword(getActivity(), "empty_search"));

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                    selectedChannelsAdapter.getFilter().filter(s);
                   // listChipsAdapter.getFilter().filter(s);

//                    for(int i=0;i<listChipsAdapter.adapters.size();i++){
//
//                        listChipsAdapter.adapters.get(i).getFilter().filter(s, new Filter.FilterListener() {
//                            @Override
//                            public void onFilterComplete(int i) {
//                                Log.e("filter","completed");
//                            }
//                        });
//
//                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.MATCH_PARENT, 1f);
            temp.setLayoutParams(params);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER);
            temp.setTextSize(15);
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



        custom_names.get(0).performClick();
       // customs_in_header.setVisibility(View.GONE);

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
    SelectedChannelsAdapterSettings selectedChannelsAdapter;

    private void get_chanels(String list){
        String url;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);

       // progressBar.setVisibility(View.VISIBLE);
        if(Session.get_user_id(getActivity()).equals("-1")) {
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list;
        }else{
            url = Session.NOTIFY_SERVER_URL + "channels2.php?chanels=" + list+"&member_id="+Session.get_user_id(getActivity());
        }
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

              //  progressBar.setVisibility(View.GONE);
                chanels =new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(!databaseHandler.is_following(new Chanel(jsonObject,"0").ch_id)){
                            databaseHandler.addPlaylist(new Chanel(jsonObject,"0").ch_id, new Chanel(jsonObject,"0").parent_id, "1");
                        }
                        chanels.add(new Chanel(jsonObject,"0"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               // categoryAdapterChanels = new PopupListAdapterChanels(getActivity(),chanels);
                selectedChannelsAdapter = new SelectedChannelsAdapterSettings(getActivity(),chanels);
                channels_listview.setAdapter(selectedChannelsAdapter);
                selectedChannelsAdapter.notifyDataSetChanged();
                if(chanels.size()==0){
                  //  Toast.makeText(getActivity(), "You have not selected any sources", Toast.LENGTH_SHORT).show();
                }
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
