package com.mamacgroup.hamtest.HaamMerge;


import android.app.Activity;
import android.support.v4.app.ActivityOptionsCompat;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.mamacgroup.hamtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;
    ArrayList<News> categories;
    public NewsAdapter(Activity mainActivity, ArrayList<News> categories) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = categories;
//        this.clientsListFragment = clientsListFragment;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        MyTextViewBold title;
        MyTextView time,no_items;
        ImageView app,ch_img;
        RelativeLayout v1,v2;
        SqureImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        if (convertView == null)
            rowView = inflater.inflate(R
                    .layout.news_item, null);
        else
            rowView = convertView;
        holder.title=(MyTextViewBold) rowView.findViewById(R.id.news_title_adp);
        holder.title.setText(categories.get(position).about);
        holder.time=(MyTextView) rowView.findViewById(R.id.news_time_news);
        holder.time.setText(categories.get(position).time);
        holder.img=(SqureImageView) rowView.findViewById(R.id.news_img_adp);
        holder.ch_img=(ImageView) rowView.findViewById(R.id.back_ch_img);
        Picasso.with(context).load(categories.get(position).ch_image).into(holder.ch_img);
        Picasso.with(context).load(categories.get(position).image).fit().into(holder.img);
//        Glide.with(context).load(categories.get(position).image).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(context, "0");
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                ArrayList<News> news = new ArrayList<News>();
                news.add(categories.get(position));
                intent.putExtra("news",news);
                intent.putExtra("last","0");
                intent.putExtra("cat_act","0");
//                View sharedView =holder.img ;
//                String transitionName =context.getString(R.string.transition_name_image);
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, sharedView, transitionName);
                context.startActivity(intent);
            }
        });
        return rowView;
        
    }

}