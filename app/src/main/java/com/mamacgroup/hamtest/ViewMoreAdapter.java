package com.mamacgroup.hamtest;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.google.android.gms.ads.formats.NativeAd;

public class ViewMoreAdapter extends RecyclerView.Adapter<ViewMoreAdapter.MyViewHolder> {

//    public List<Category>  categories;

//    public HashMap<Integer,Category> dummyList;
    private ArrayList<News> news;
    private Context context;
    NewsDetailFragment activity;
    NewsDetailFragment.FragmentTouchListner mCallBack;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView title, price, value,full_name;
        public ImageView img,selected_tick;
        public MyViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.news_title_vm);
            price=(TextView) view.findViewById(R.id.news_time2_vm);
            img=(ImageView) view.findViewById(R.id.news_img_vm);
            view.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

            title.setBackgroundColor(Color.parseColor("black"));

            return false;
        }

        @Override
        public void onClick(View view) {
            mCallBack.newsclickednofinish(news.get(getAdapterPosition()));
        }
    }


    public ViewMoreAdapter(Context context, ArrayList<News> news, NewsDetailFragment.FragmentTouchListner mCallBack) {
        this.context = context;
        this.news = news;
        this.mCallBack = mCallBack;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_for_viewmore_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(news.get(position).image.equals("")){
            holder.img.setVisibility(View.GONE);
        }else{
            holder.img.setVisibility(View.VISIBLE);
            Picasso.with(context).load(news.get(position).image).into(holder.img);
        }
        holder.title.setText(news.get(position).title);
        holder.price.setText(news.get(position).time_ar);

    }
    public class POJO {
        String activityValue;
        String activityValueText;
        String activityEmptyText;
        boolean activityCardEmptyViewVisibility;
        boolean activityCardViewVisibility;
    }
    @Override
    public int getItemCount() {

        return news.size();

    }

}