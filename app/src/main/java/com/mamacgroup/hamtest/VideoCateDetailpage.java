package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.http.Url;

import static com.mamacgroup.hamtest.R.id.recyclerView;

//import com.bumptech.glide.Glide;

/**
 * Created by HP on 7/26/2016.
 */
public class VideoCateDetailpage extends Fragment {
    ImageView cover_image,ch_image;
    MyTextView ch_title,member_text;
    TextView label,follow;
    ListView listView;
    ArrayList<News> news;
    News_listAdapter news_list_adapter;
    FragmentTouchListner mCallBack;
    String url = Session.SERVER_URL+"feeds-new3.php?";
    DatabaseHandler db;
    VideoChanel chanel;
    ImageView back_btn;
    boolean no_image;
    MultiViewTypeActAdapter adapter;
    CustomRecylerView mRecyclerView;
    LinearLayout progressBar,progressBarfooter,follow_ll;
    NewsRecycleListActivity activity=new NewsRecycleListActivity();
    LinearLayoutManager linearLayoutManager;
    private static int firstVisibleInListview;
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
        return inflater.inflate(R.layout.voideocatdetailfragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
            chanel = (VideoChanel) getArguments().getSerializable("chanel");

        ch_image = (ImageView)view.findViewById(R.id.chanel_video_img);
        Picasso.with(getActivity()).load(chanel.ch_image).into(ch_image);
        back_btn = (ImageView) view.findViewById(R.id.back_btn_v);
        progressBar=(LinearLayout) view.findViewById(R.id.progressBar_v);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back();
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        mRecyclerView = (CustomRecylerView) view.findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        firstVisibleInListview = linearLayoutManager.findFirstVisibleItemPosition();

        mRecyclerView.setItemAnimator(null);


        follow_ll = (LinearLayout) view.findViewById(R.id.follow_btn_ll_video);
        label = (TextView) view.findViewById(R.id.label_v);
        follow = (TextView) view.findViewById(R.id.follow_btn_tvvv);

//        ch_title = (com.mamacgroup.hamtest.MyTextView) view.findViewById(R.id.ch_tit);
        listView = (ListView) view.findViewById(R.id.listView);

        news = new ArrayList<>();
        get_video_news();
    }
    private void get_video_news(){

        progressBar.setVisibility(View.VISIBLE);
//        progressBarfooter.setVisibility(View.GONE);
        news.clear();
        String temp_url= "http://news.haamapp.com/apiv2/yt-videos.php?chanel="+chanel.ch_id;
        final  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(temp_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("response", jsonArray.toString());
              /*  if(progressDialog!=null)
                    progressDialog.dismiss();
*/                  progressBar.setVisibility(View.GONE);
//                progressBarfooter.setVisibility(View.GONE);



                for(int i=0;i<jsonArray.length();i++){

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.e("json", jsonObject.toString());
                        news.add(new News(jsonObject,getActivity(),"video"));




                    } catch (JSONException e) {
                        // adapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }

                }
                adapter = new MultiViewTypeActAdapter(news,getActivity(),no_image,activity);
                mRecyclerView.setAdapter(adapter);
                if(jsonArray.length()==0)
                    adapter.end_of_news = true;
                else
                    adapter.end_of_news = false;

                adapter.notifyItemChanged(adapter.getItemCount()-1);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* if(progressDialog!=null)
                    progressDialog.dismiss();
               */
                try{
                    progressBarfooter.setVisibility(View.GONE);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

                if(volleyError.toString().equals("com.android.volley.TimeoutError")){

                }
                else{
                    progressBar.setVisibility(View.GONE);
//                    progressBarfooter.setVisibility(View.GONE);
                }
                Log.e("error", volleyError.toString());
//                Toast.makeText(getActivity(),volleyError.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}