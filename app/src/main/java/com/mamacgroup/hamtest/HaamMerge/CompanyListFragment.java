package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class CompanyListFragment extends Fragment {
    FragmentTouchListner mCallBack;
    GridView gv;
    MyTextView no_news,pd_tv;
    LinearLayout pd;
    ArrayList<News> newses;
    NewsAdapter newsAdapter;
    String cat_id="";
    int page=0;
    int preLast=0;
    SwipeRefreshLayout swipeRefreshLayout;

    public interface FragmentTouchListner {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            //mCallBack = (CompanySlidingActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.haam_backup_categorylist, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        newses=new ArrayList<>();
        cat_id=(String)getArguments().getSerializable("id");
        Log.e("cat_id",cat_id);
        no_news = (MyTextView) view.findViewById(R.id.no_arc_tv);
        no_news.setText(Settings.getword(getActivity(), "No News in this Category"));
        pd_tv=(MyTextView)view.findViewById(R.id.pd_tv_catttttt);
        pd_tv.setText(Settings.getword(getActivity(),"Loading"));
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh_arc);
        gv = (GridView)view.findViewById(R.id.gridView_neews);
        pd=(LinearLayout)view.findViewById(R.id.progressBar_llll);
        newsAdapter=new NewsAdapter(getActivity(),newses);
        get_news("0");
        gv.setAdapter(newsAdapter);

        newsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        swipeRefreshLayout.setRefreshing(true);
                        get_news("0");
                    }
                }
        );
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                final int last=i+i1;
                if(last>=i2){
                    if (preLast != last) {
                        preLast = last;
                        page++;
                        get_news("1");
                    }
                }

            }
        });
    }
        public static CompanyListFragment newInstance(String s,String id) {
            CompanyListFragment companyListFragment = new CompanyListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            companyListFragment.setArguments(bundle);
            return companyListFragment;
        }


    public void get_news(final String t) {
        String url;

        if(cat_id.equals("wish")){
            url = Settings.SERVERURL + "news.php?news="+android.text.TextUtils.join(",", AppController.getInstance().selected_channels);
        }else{
            url = Settings.SERVERURL + "news.php?category_id="+cat_id+"&page="+String.valueOf(page);
        }

        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait....");
//        progressDialog.setCancelable(false);
        pd.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                pd.setVisibility(View.GONE);
                if(t.equals("0")) {
                    newses.clear();
                }
                swipeRefreshLayout.setRefreshing(false);
                Log.e("response is: ", jsonObject.toString());
                try {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        Picasso.with(getActivity()).load(sub.getString("image")).fetch();
                        News prod = new News(sub);
                        newses.add(prod);
                    }
                    newsAdapter.notifyDataSetChanged();
                    if(newses.size()==0){
                        no_news.setVisibility(View.VISIBLE);
                    }else{
                        no_news.setVisibility(View.GONE);
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
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                pd.setVisibility(View.GONE);
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}