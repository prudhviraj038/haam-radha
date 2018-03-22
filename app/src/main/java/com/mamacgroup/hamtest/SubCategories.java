package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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



public class SubCategories extends Fragment {

    GridView listView;
    Categories categories;
    SubCategoryAdapter categoryAdapter;
    FragmentTouchListner mCallBack;
    EditText search_edit;
    TextView label;


    public interface FragmentTouchListner{
        public  void back();
        public void subcatselected(Chanel chanel,String parent,String parent_name);
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
        Log.e("anattach","called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("oncreateview","called");


        return inflater.inflate(R.layout.subcatfragment, container, false);



    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        Log.e("onActivitycreated","called");


        View view = getView();
       // mCallBack.setselected("4");

        LinearLayout empty_click = (LinearLayout) view.findViewById(R.id.emptyclick);

        empty_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        ArrayList<String> tab_names = new ArrayList<>();
                tab_names.add("New Sources");
                tab_names.add("Edit Sources");
        customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
                customs_in_header.setVisibility(View.GONE);

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

        search_edit = (EditText) view.findViewById(R.id.et_search);
        search_edit.setHint(Session.getword(getActivity(), "empty_search"));

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    categoryAdapter.getFilter().filter(s);
                }catch (Exception ex){
                    ex.printStackTrace();
                }




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

                label = (TextView) view.findViewById(R.id.label);
        label.setText("");
        listView=(GridView)view.findViewById(R.id.listView);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mCallBack.subcatselected(categories.chanels.get(position),categories.id,categories.title);
//            }
//        });


        if(getArguments().containsKey("categorie"))
        {

            categories = (Categories)getArguments().getSerializable("categorie");
            label.setText(Html.fromHtml(categories.get_title(getActivity())));
            categoryAdapter = new SubCategoryAdapter(getActivity(),categories,(NewsRecycleListActivity) mCallBack);
            listView.setAdapter(categoryAdapter);

        }else{

            if(getArguments().containsKey("id"))
            get_categories(getArguments().getString("id"));
                 else
                get_categories(getArguments().getString("4"));

        }




    }

    ArrayList<com.mamacgroup.hamtest.MyTextView> custom_names;
    LinearLayout customs_in_header;

    public void display_custom(final ArrayList<String> jsonArray) {

        custom_names = new ArrayList<>();
        customs_in_header.removeAllViewsInLayout();
        customs_in_header.setVisibility(View.VISIBLE);
        for (int i=0;i<jsonArray.size();i++){
            final  com.mamacgroup.hamtest.MyTextView temp = new com.mamacgroup.hamtest.MyTextView(getActivity());
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

            temp.setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);
            final int finalI = i;
            final int finalI1 = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                    }

                    temp.setBackgroundResource(R.drawable.border_full_appcolor);
                    temp.setTextColor(Color.parseColor("white"));
                   // customizeFagment.custom_item_selected(finalI1);


                }
            });

            custom_names.add(temp);
            customs_in_header.addView(temp);
        }
        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(0).setTextColor(Color.parseColor("white"));
    }

    private void get_categories(String cat_id){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Session.SERVER_URL+"categories.php?category_id="+cat_id;
        Log.e("url0",url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        categories = new Categories(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                label.setText(Html.fromHtml(categories.get_title(getActivity())));
                categoryAdapter = new SubCategoryAdapter(getActivity(),categories,(NewsRecycleListActivity) mCallBack);
                listView.setAdapter(categoryAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("fragment","onresume");
    }

}
