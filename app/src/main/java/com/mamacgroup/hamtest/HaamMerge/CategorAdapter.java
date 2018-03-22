package com.mamacgroup.hamtest.HaamMerge;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.google.android.gms.ads.formats.NativeAd;

public class CategorAdapter extends RecyclerView.Adapter<CategorAdapter.MyViewHolder> {

    public List<Category>  categories;

    public HashMap<Integer,Category> dummyList;

    private Context context;
    SelectedCategoriesActivity homeFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView title, base_rate, value,des;
        public ImageView flag_id,selected_tick;
        public Category rates;
        SqureImageView img;
        public MyViewHolder(View view) {

            super(view);
            des=(MyTextView) view.findViewById(R.id.cat_title_des);
            title=(MyTextView) view.findViewById(R.id.cat_title_adp);
            img=(SqureImageView) view.findViewById(R.id.cat_img_adp);
            view.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

            title.setBackgroundColor(Color.parseColor("black"));

            return false;
        }

        @Override
        public void onClick(View view) {
            Settings.setWishid(context, "0");
            Intent intent = new Intent(context, VideoPlayerActivity.class);
//                View sharedView =holder.img ;
//                String transitionName =context.getString(R.string.transition_name_image);
            intent.putExtra("news",rates.newses);
            intent.putExtra("last",rates.last_id);
            intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, sharedView, transitionName);
//                context.startActivity(intent, transitionActivityOptions.toBundle());
            context.startActivity(intent);

        }
    }


    public CategorAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Category rates = categories.get(position);
            holder.rates = rates;
            holder.title.setText(rates.title_ar);
            Picasso.with(context).load(rates.image).fit().into(holder.img);
        if(AppController.getInstance().cat_images.has(categories.get(position).id)){
            Log.e("cat_img", "cat_img");
            try {
                if(AppController.getInstance().cat_images.getString(categories.get(position).id).equals("0")){
                    Picasso.with(context).load(categories.get(position).image).fit().into(holder.img);
                    holder.des.setText(categories.get(position).des);
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(0);

                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    holder.img.setColorFilter(filter);
                }else {
                    Picasso.with(context).load(AppController.getInstance().cat_images.getString(categories.get(position).id)).into(holder.img);
                    holder.des.setText(AppController.getInstance().cat_des.getString(categories.get(position).id));
                }
            } catch (JSONException e) {

            }
        }else {
            Picasso.with(context).load(categories.get(position).image).fit().into(holder.img);
            holder.des.setText(categories.get(position).des);
        }

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

        return categories.size();

    }

}