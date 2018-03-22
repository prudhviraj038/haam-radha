package com.mamacgroup.hamtest;

/**
 * Created by mac on 12/28/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> moviesList;

    class ViewHolder0 extends RecyclerView.ViewHolder {
        Context context;
        com.mamacgroup.hamtest.MyTextView time, time2, urgent;
        com.mamacgroup.hamtest.MyTextView1 ch_title, title;
        ImageView news_img, logo, share_btn, video_hint;
        LinearLayout is_urgent;

        public ViewHolder0(View itemView,Context context){
            super(itemView);
            this.context = context;
            ch_title = (MyTextView1)itemView.findViewById(R.id.news_ch_title);
            title = (MyTextView1)itemView.findViewById(R.id.news_title);
            logo =  (ImageView) itemView.findViewById(R.id.logo);

            news_img = (ImageView) itemView.findViewById(R.id.news_img);


        }

    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        Context context;
        com.mamacgroup.hamtest.MyTextView time, time2, urgent;
        com.mamacgroup.hamtest.MyTextView1 ch_title, title;
        ImageView  logo, share_btn;
        LinearLayout is_urgent;

        public ViewHolder1(View itemView,Context context){
            super(itemView);
            this.context = context;
            ch_title = (MyTextView1)itemView.findViewById(R.id.news_ch_title);
            title = (MyTextView1)itemView.findViewById(R.id.news_title);
            logo =  (ImageView) itemView.findViewById(R.id.logo);
        }

    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        Context context;
        com.mamacgroup.hamtest.MyTextView1  title_ad;
        ImageView  video_hint_ad;
        GifImageView ad_gif;
        SquareImageview img_ad;

        public ViewHolder2(View itemView,Context context){
            super(itemView);
            this.context = context;
            title_ad = (MyTextView1) itemView.findViewById(R.id.news_title_ad);
            img_ad = (SquareImageview) itemView.findViewById(R.id.news_img_ad);
            video_hint_ad = (ImageView) itemView.findViewById(R.id.video_hint_ad);
        }

    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(moviesList.get(position).type.equals("news")){

            if(moviesList.get(position).image.equals(""))
            return 1;
            else
                return 0;

        }else{
            return 2;
        }



    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType){
            case 0:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item, parent, false);

                return new ViewHolder0(itemView,parent.getContext());
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item_no_image, parent, false);

                return new ViewHolder1(itemView,parent.getContext());
            case 2:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item_ad, parent, false);

                return new ViewHolder2(itemView,parent.getContext());



        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        News news = moviesList.get(position);
        switch (holder.getItemViewType()){
            case 0:

                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                viewHolder0.ch_title.setText(news.chanels.get_ch_title(viewHolder0.context));
                Picasso.with(viewHolder0.context).load(news.chanels.ch_image).into(viewHolder0.logo);
                Picasso.with(viewHolder0.context).load(news.image).into(viewHolder0.news_img);

                break;

            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1)holder;
                viewHolder1.ch_title.setText(news.chanels.get_ch_title(viewHolder1.context));
                Picasso.with(viewHolder1.context).load(news.chanels.ch_image).into(viewHolder1.logo);

                break;
            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                viewHolder2.title_ad.setText(Session.getword(viewHolder2.context,"Sponsered"));
                //Picasso.with(viewHolder2.context).load(news.image).asGif().error(R.drawable.loading).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder2.img_ad);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public RecyclerAdapter(List<News> moviesList) {
        this.moviesList = moviesList;
    }

}