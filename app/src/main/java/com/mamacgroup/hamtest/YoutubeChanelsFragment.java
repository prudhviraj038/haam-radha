package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class YoutubeChanelsFragment extends Fragment {

    ArrayList<VideoChanel> categories;
    FragmentTouchListner mCallBack;
    YoutubeChannelsAdapter youtubeChannelsAdapter;
    TextView label;

    RecyclerView  recyclerView;

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
        return inflater.inflate(R.layout.youtubechanelsfragment, container, false);
    }
    DatabaseHandler databaseHandler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        View view = getView();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setHasFixedSize(true);
        mCallBack.setselected("2");
        categories = new ArrayList<>();

        label = (TextView) view.findViewById(R.id.label1);
        label.setText(Session.getword(getActivity(), "title_select_sources"));

        ImageView back_btn = (ImageView) view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back();
            }
        });

        back_btn.setVisibility(View.VISIBLE);

        ImageView set_btn = (ImageView) view.findViewById(R.id.set_btn);
        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.setttings_btn_clicked();
            }
        });

        try {
            categories = (ArrayList<VideoChanel>) getArguments().getSerializable("chanels");
        }catch (Exception ex){

        }

        youtubeChannelsAdapter = new YoutubeChannelsAdapter(getContext(),categories);

        recyclerView.setAdapter(youtubeChannelsAdapter);



    }


}
