package com.mamacgroup.hamtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.bumptech.glide.Glide;

/**
 * Created by mac on 2/3/17.
 */

public class YoutubeChannelsAdapter extends RecyclerView.Adapter<YoutubeChannelsAdapter.SimpleViewHolder> {
    private static final int COUNT = 100;

    private final Context mContext;
    private int mCurrentItemId = 0;
    ArrayList<VideoChanel> categories;
    MainActivity mCallback;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        ImageView like_icon;
        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.cat_name);
            img = (ImageView) view.findViewById(R.id.cat_img);
            like_icon = (ImageView) view.findViewById(R.id.like_icon);
        }
    }

    public YoutubeChannelsAdapter(Context context, ArrayList<VideoChanel> categories) {
        mContext = context;
        this.categories = categories;

    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.livetv_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {

        holder.title.setText(categories.get(position).get_ch_title(mContext));
        Picasso.with(mContext).load(categories.get(position).ch_image).into(holder.img);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // gotolivetv(categories.get(position));
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  gotolivetv(categories.get(position));
            }
        });


        holder.like_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!AppController.getInstance().getDatabaseHandler().is_following(categories.get(position).ch_id)){

                    AppController.getInstance().getDatabaseHandler().addPlaylist(categories.get(position).ch_id,"999","0");
                   // holder.like_icon.setColorFilter(ContextCompat.getColor(mContext,R.color.aa_menu_text_selected));
                    holder.like_icon.setImageResource(R.drawable.haam_cat_selected);
                }else{
                    AppController.getInstance().getDatabaseHandler().deletePlaylist(categories.get(position).ch_id);
                    //holder.like_icon.setColorFilter(ContextCompat.getColor(mContext,R.color.aa_menu_text));
                    holder.like_icon.setImageResource(R.drawable.haam_cat_unselected);

                }
            }
        });


        if(AppController.getInstance().getDatabaseHandler().is_following(categories.get(position).ch_id)){
            //holder.like_icon.setColorFilter(ContextCompat.getColor(mContext,R.color.aa_menu_text_selected));

            holder.like_icon.setImageResource(R.drawable.haam_cat_selected);


        }else{
           // holder.like_icon.setColorFilter(ContextCompat.getColor(mContext,R.color.aa_menu_text));
            holder.like_icon.setImageResource(R.drawable.haam_cat_unselected);

        }


    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
      //  mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
      //  mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    private void gotolivetv(LiveChannels.Chanel chanel){
        Log.e("ex_link",chanel.external_link);
        if (!chanel.external_link.equals("")) {
            Intent webview_activity = new Intent(mContext,WebviewActivity.class);
            webview_activity.putExtra("link", chanel.external_link);
            //   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.link));
            mContext.startActivity(webview_activity);
        }else if (!chanel.youtube.equals("")) {
            Intent intent = new Intent(mContext, YoutubePlayer.class);
            intent.putExtra("video", chanel.youtube);
            intent.putExtra("name", chanel.get_ch_title(mContext));
            mContext.startActivity(intent);
        } else {
//            Intent intent = new Intent(mContext, JWPlayerViewExample.class);
//            intent.putExtra("jw_url", chanel.link);
//            intent.putExtra("name", chanel.get_ch_title(mContext));
//            mContext.startActivity(intent);
        }
    }
}