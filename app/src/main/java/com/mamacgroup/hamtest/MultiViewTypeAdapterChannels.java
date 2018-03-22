package com.mamacgroup.hamtest;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapterChannels extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Chanel> dataSet;
    Context mContext;
    NewsRecycleListActivity mCallback;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {


        TextView tv;
        CircleImageView img;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.tv=(TextView) itemView.findViewById(R.id.cat_name);
            this.img=(CircleImageView) itemView.findViewById(R.id.cat_img);

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


    public MultiViewTypeAdapterChannels(ArrayList<Chanel> data, Context context,NewsRecycleListActivity mCallback) {
        this.dataSet = data;
        this.mContext = context;
        this.mCallback = mCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view;
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_item, parent, false);
                return new TextTypeViewHolder(view);
    }






    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final Chanel object = dataSet.get(listPosition);

        if (object != null) {

                //    ((TextTypeViewHolder) holder).txtType.setText(object.text);
            ((TextTypeViewHolder) holder).tv.setText(object.get_ch_title(mContext));

            Picasso.with(mContext).load(object.ch_image).into(((TextTypeViewHolder) holder).img);

            ((TextTypeViewHolder) holder).img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.single_channel_news(object.ch_id);
                }
            });

            ((TextTypeViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.single_channel_news(object.ch_id);
                }
            });



            }
        }
    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(dataSet.size()));
        return dataSet.size();
    }


}
