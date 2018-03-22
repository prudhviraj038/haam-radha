package com.mamacgroup.hamtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.bumptech.glide.Glide;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<YoutubeCategories> dataSet;
    Context mContext;
    NewsRecycleListActivity mCallback;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
           // this.tv=(TextView) itemView.findViewById(R.id.cat_name);
            this.img=(ImageView) itemView.findViewById(R.id.video_cat_image);

           // this.txtType = (TextView) itemView.findViewById(R.id.type);
            //this.cardView = (CardView) itemView.findViewById(R.id.card_view);

        }

    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        ImageView image;

        public ChannelViewHolder(View itemView) {
            super(itemView);

           // this.txtType = (TextView) itemView.findViewById(R.id.type);
            //this.image = (ImageView) itemView.findViewById(R.id.background);

        }

    }


    public MultiViewTypeAdapterCategory(ArrayList<YoutubeCategories> data, Context context, NewsRecycleListActivity mCallback) {
        this.dataSet = data;
        this.mContext = context;
        this.mCallback = mCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view;
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_category_item, parent, false);
                return new TextTypeViewHolder(view);
    }






    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final YoutubeCategories object = dataSet.get(listPosition);

        if (object != null) {

                //    ((TextTypeViewHolder) holder).txtType.setText(object.text);
            //((TextTypeViewHolder) holder).tv.setText(object.get_ch_title(mContext));

            Picasso.with(mContext).load(object.image).into(((TextTypeViewHolder) holder).img);

            ((TextTypeViewHolder) holder).img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mCallback.youtube_chanels_fragment(object.chanels_all);
                }
            });

//            ((TextTypeViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mCallback.single_channel_news(object.ch_id);
//                }
//            });



            }
        }
    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(dataSet.size()));
        return dataSet.size();
    }


}
