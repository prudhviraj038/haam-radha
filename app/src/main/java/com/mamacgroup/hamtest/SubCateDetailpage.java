package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
//import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by HP on 7/26/2016.
 */
public class SubCateDetailpage extends Fragment {
    ImageView cover_image,ch_image;
    com.mamacgroup.hamtest.MyTextView member_count,ch_title,member_text;
    com.mamacgroup.hamtest.MyTextView follow_btn;
    TextView label;
    ListView listView;
    ArrayList<News> news;
    News_listAdapter news_list_adapter;
    FragmentTouchListner mCallBack;
    String url = Session.SERVER_URL+"feeds-new3.php?";
    DatabaseHandler db;
    Chanel chanel;
    String chanel_id;
    String parent_name="";
    LinearLayout progressBar,progressBarfooter;
    RelativeLayout follow_btn_click;
    ImageView follow_btn_image;
    int ch_count=0;
    String last_loaded_id="";
    public interface FragmentTouchListner{
        public void newsclickednofinish(News news);
        public void setselected(String index);
        public void back();
        public void refresh_lst_back();

        public void ihave_completed() ;

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
        return inflater.inflate(R.layout.subcatdetailfragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            parent_id="";

         View view = getView();
        ImageView back_btn = (ImageView)view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallBack.back();
            }
        });


        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_progress, null, false);
        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.detailpage_header, null, false);

        cover_image = (ImageView) headerView.findViewById(R.id.cover_image);
        ch_image = (ImageView)headerView.findViewById(R.id.ch_logo);
        label = (TextView) view.findViewById(R.id.label);
        ch_title = (com.mamacgroup.hamtest.MyTextView) headerView.findViewById(R.id.ch_title);
        member_count = (com.mamacgroup.hamtest.MyTextView) headerView.findViewById(R.id.member_count);
        member_text = (MyTextView) headerView.findViewById(R.id.member_text);
        follow_btn=(com.mamacgroup.hamtest.MyTextView) headerView.findViewById(R.id.follow_btn);
        follow_btn_click = (RelativeLayout) headerView.findViewById(R.id.follow_btn_click);
        follow_btn_image = (ImageView) headerView.findViewById(R.id.follow_btn_image);
        progressBarfooter = (LinearLayout) footerView.findViewById(R.id.progressBarfooter);
        customs_in_header = (LinearLayout) view.findViewById(R.id.custom_header_layout);
        listView=(ListView)view.findViewById(R.id.listView);

        news = new ArrayList<>();
        news_list_adapter=new News_listAdapter(getActivity(), news,(NewsRecycleListActivity) mCallBack);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                    Session.count_set(getActivity(),"0");
                    mCallBack.newsclickednofinish(news.get(position));
            }
        });

        if(!getArguments().containsKey("chanels")){
            listView.addHeaderView(headerView);
                    }


        listView.addFooterView(footerView);
        listView.setAdapter(news_list_adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                Log.e("list listener",String.valueOf(i)+","+String.valueOf(i1)+","+String.valueOf(i2));
                if((news.size()-2)==i && last_loaded_id==""){
                    get_news("chanels=" + chanel.ch_id,true);
                }
            }
        });



        follow_btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (db.is_following(chanel.ch_id)) {

                    db.deletePlaylist(chanel.ch_id);
                    ch_count--;
                    member_count.setText(String.valueOf(ch_count));
                    add_ch("delete", chanel.ch_id);

                } else {
                    if(parent_id.equals("")){
                        get_chanel(true);
                    }else{
                        db.addPlaylist(chanel.ch_id, parent_id, "1");
                        ch_count++;
                        member_count.setText(String.valueOf(ch_count));
                        add_ch("add",chanel.ch_id);
                    }


                }
                follow_btn.setText(db.is_following(chanel.ch_id) ? Session.getword(getActivity(),"unfollow") : Session.getword(getActivity(),"follow"));
                //    holder.follow_btn.setText(Html.fromHtml(db.is_following(chanels.get(position).ch_id) ? Session.getword(context,"unfollow") : Session.getword(context,"follow")));

                if (db.is_following(chanel.ch_id)) {
                    follow_btn.setBackgroundResource(R.drawable.border_full_for_add);
                    follow_btn.setTextColor(Color.parseColor("white"));
                    follow_btn_image.setVisibility(View.VISIBLE);
                    follow_btn.setVisibility(View.INVISIBLE);
                } else {
                    follow_btn.setBackgroundResource(R.drawable.border_empty_for_add);
                    follow_btn.setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
                    follow_btn_image.setVisibility(View.INVISIBLE);
                    follow_btn.setVisibility(View.VISIBLE);

                }

                Session.sendChannelsToServer(getActivity(),db.all_selected_channels_new("0"));
                mCallBack.refresh_lst_back();

            }
        });








        if(getArguments().containsKey("parent_id"))
         parent_id = getArguments().getString("parent_id");

        if(getArguments().containsKey("parent_id"))
         parent_name = getArguments().getString("parent_name");


        db = new DatabaseHandler(getActivity());



        if(getArguments().containsKey("chanel")){
            Log.e("from","chanel");
            chanel = (Chanel)getArguments().getSerializable("chanel");
            chanel_id = chanel.ch_id;
            update_view();

        }else if(getArguments().containsKey("chanel_id")){
            chanel_id = getArguments().getString("chanel_id");
            Log.e("from","chanel_id");
            get_chanel(false);

        }else if(getArguments().containsKey("chanels")){
            chanel_id = getArguments().getString("chanels");
            Log.e("from","chanel_id");
            get_news("chanels=" + chanel_id,false);
        }


        mCallBack.ihave_completed();


    }
    public  void add_ch(String type,String id){
        String url = null;
        if(type.equals("add") )
            url = Session.NOTIFY_SERVER_URL+"add-channel2.php?member_id="+Session.get_user_id(getActivity())+ "&channel_id="+id;
        else
            url = Session.NOTIFY_SERVER_URL+"remove-channel2.php?member_id="+Session.get_user_id(getActivity())+ "&channel_id="+id;
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(Session.getword(getActivity(), "please_wait"));
////        progressDialog.setMessage(Settings.getword(this, "please_wait"));
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
//                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), Session.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    private void update_view(){
        Picasso.with(getActivity()).load(chanel.cover_image).into(cover_image);
        label.setText(chanel.get_ch_title(getActivity()));
        Picasso.with(getActivity()).load(chanel.ch_image).into(ch_image);
        ch_title.setText(chanel.get_ch_title(getActivity()));
        member_text.setText(Session.getword(getActivity(),"followers"));
        member_count.setText(chanel.count);
        ch_count = Integer.parseInt(chanel.count);
        follow_btn.setText(db.is_following(chanel.ch_id) ? Session.getword(getActivity(),"unfollow") : Session.getword(getActivity(),"follow"));

        if(db.is_following(chanel.ch_id)) {
            follow_btn.setBackgroundResource(R.drawable.border_full_for_add);
            follow_btn.setTextColor(Color.parseColor("white"));
            follow_btn_image.setVisibility(View.VISIBLE);
            follow_btn.setVisibility(View.INVISIBLE);

        }
        else {
            follow_btn.setBackgroundResource(R.drawable.border_empty_for_add);
            follow_btn.setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
            follow_btn_image.setVisibility(View.INVISIBLE);
            follow_btn.setVisibility(View.VISIBLE);

        }
        get_news("chanels=" + chanel.ch_id,false);


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
            temp.setTextSize(16);            temp.setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
            temp.setBackgroundResource(R.drawable.border_empty_appcolor);
            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < jsonArray.size(); j++) {
                        custom_names.get(j).setTextColor(getActivity().getResources().getColor(R.color.aa_app_blue));
                        custom_names.get(j).setBackgroundResource(R.drawable.border_empty_appcolor);
                    }

                    temp.setBackgroundResource(R.drawable.border_full_appcolor);
                    temp.setTextColor(Color.parseColor("white"));
                    tabclicked(finalI2);

                }
            });

            custom_names.add(temp);
            customs_in_header.addView(temp);
        }
        custom_names.get(0).setBackgroundResource(R.drawable.border_full_appcolor);
        custom_names.get(0).setTextColor(Color.parseColor("white"));

    }

    private void get_news(String url_append,boolean clear){
        String fi_url=url;
        if(!clear) {
            news.clear();
            Log.e("url",url+url_append);
             fi_url=url+url_append;
        }else{
            Log.e("url",url+url_append+"&last_id="+news.get(news.size()-1).id);
            fi_url=url+url_append+"&last_id="+news.get(news.size()-1).id;
            last_loaded_id=news.get(news.size()-1).id;
        }
//        Log.e("url",url+url_append);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("please_wait");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(fi_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
//                if(progressDialog!=null)
//                    progressDialog.dismiss();

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
                    if(jsonArray.length()==0){
                       // Toast.makeText(getActivity(),"no feeds to display",Toast.LENGTH_SHORT).show();
                    }
                }

                //news_list_adapter=new News_listAdapter(getActivity(),news,(MainActivity) mCallBack);
                //listView.setAdapter(news_list_adapter);

                news_list_adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                if(progressDialog!=null)
//                    progressDialog.dismiss();
                progressBarfooter.setVisibility(View.GONE);

                Log.e("error",volleyError.toString());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private void tabclicked(int index){

        switch (index){
            case 0:
                get_news("category="+MainActivity.sports_id,false);
                break;
            case 1:
                get_news("chanels="+db.selected_channels(MainActivity.sports_id),false);
                break;

        }
    }

    private void get_chanel(final boolean update){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = Session.NOTIFY_SERVER_URL+"channels2.php?chanels="+chanel_id;
        Log.e("url", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response",jsonArray.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

                try {


                    chanel = new Chanel(jsonArray.getJSONObject(0),"0");
                    chanel_id = chanel.ch_id;
                    parent_id = chanel.parent_id;

                    if(update) {

                        follow_btn_click.performClick();
                    }
                    else {

                        update_view();
                        get_news("chanels=" + chanel.ch_id,false);
                    }

                } catch (JSONException e) {

                   // Toast.makeText(getActivity(),Session.getword(getActivity(),"please_try_again"),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

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

String parent_id;

    private void get_parent(){
        String url = Session.SERVER_URL+"chanel_parent.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    parent_id = jsonArray.getJSONObject(0).getString("id");

                }catch (Exception ex){
                    parent_id = "-1";

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                parent_id = "-1";
            }
        });
    }

}
