package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
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
public class NotifySourcesFragment extends Fragment {


    ListView channels_listview;
    ArrayList<Categories> categories;
    FragmentTouchListner mCallBack;
    MyTextView label;
    ViewFlipper viewFlipper;
    LinearLayout tab_holder ;
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
        return inflater.inflate(R.layout.notifysourcesfragment, container, false);
    }
    DatabaseHandler databaseHandler;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
         databaseHandler = new DatabaseHandler(getActivity());

        View view = getView();
        mCallBack.setselected("4");
        categories = new ArrayList<>();
                customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
        channels_listview = (ListView) view.findViewById(R.id.channels_listview);

         viewFlipper = (ViewFlipper)view.findViewById(R.id.viewFlipper2);
            viewFlipper.setDisplayedChild(1);

        tab_holder = (LinearLayout) view.findViewById(R.id.tab_holder);
      //  tab_holder.setVisibility(View.GONE);
        label = (MyTextView) view.findViewById(R.id.label1);
        label.setText(Session.getword(getActivity(), "sources_for_notification"));
                ArrayList<String> tab_names = new ArrayList<>();
                tab_names.add(Session.getword(getActivity(),"select_all"));
                tab_names.add(Session.getword(getActivity(),"cancel_all"));
                display_custom(tab_names);
        ImageView back_btn = (ImageView) view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back();
            }
        });

        ImageView set_btn = (ImageView) view.findViewById(R.id.set_btn);
        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.setttings_btn_clicked();
            }
        });




        // get_categories();

        get_chanels(databaseHandler.all_selected_channels_new("0"));

        search_edit = (EditText) view.findViewById(R.id.et_search);
        search_edit.setHint(Session.getword(getActivity(), "empty_search"));
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    selectedChannelsAdapter.getFilter().filter(s);
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
            TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
            temp.setLayoutParams(params);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER);
            temp.setTextSize(16);
            temp.setTextColor(Color.parseColor("white"));
          //  temp.setBackgroundResource(R.drawable.border_empty_appcolor);
            final int finalI = i;
            final int finalI1 = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    for (int j = 0; j < jsonArray.size(); j++) {
//                        custom_names.get(j).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
//                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
//                    }
//
//                    temp.setBackgroundResource(R.drawable.border_full_appcolor);
//                    temp.setTextColor(Color.parseColor("white"));

                    if(finalI==0){
                        try {
                            databaseHandler.select_all_channels();
                            selectedChannelsAdapter.notifyDataSetChanged();
//                            Session.sendRegistrationToServer(getActivity());
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            databaseHandler.deselect_all_channels();
                            selectedChannelsAdapter.notifyDataSetChanged();
//                            Session.sendRegistrationToServer(getActivity());
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                }
            });

            custom_names.add(temp);

        }
//        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
//        custom_names.get(0).setTextColor(Color.parseColor("white"));

        for(int r=custom_names.size()-1;r>=0;r--)
            customs_in_header.addView(custom_names.get(r));



    }




    ArrayList<Chanel> chanels;
    NotificationEnabledChannelsAdapter selectedChannelsAdapter;

    private void get_chanels(String list){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);

       // progressBar.setVisibility(View.VISIBLE);
        String url = Session.NOTIFY_SERVER_URL+"channels2.php?chanels="+list+"&member_id="+Session.get_user_id(getActivity());
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
                        chanels.add(new Chanel(jsonObject,"0"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               // categoryAdapterChanels = new PopupListAdapterChanels(getActivity(),chanels);
                selectedChannelsAdapter = new NotificationEnabledChannelsAdapter(getActivity(),chanels,(NewsRecycleListActivity) mCallBack);
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
