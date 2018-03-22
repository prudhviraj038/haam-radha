package com.mamacgroup.hamtest.HaamMerge;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.graphics.Color;
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

import java.util.HashMap;
import java.util.List;

//import com.google.android.gms.ads.formats.NativeAd;

public class SelectedCategoriesAdapter extends RecyclerView.Adapter<SelectedCategoriesAdapter.MyViewHolder> {

    public List<Category> moviesList;

    public HashMap<Integer,Category> dummyList;

    private Context context;
    SelectedCategoriesActivity homeFragment;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView title, base_rate, value,full_name;
        public ImageView flag_id,selected_tick;
        public Category rates;

        public MyViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.currency_symbol);
            base_rate = (TextView) view.findViewById(R.id.currency_baserate);
            value = (TextView) view.findViewById(R.id.currency_converted_value);
            full_name = (TextView) view.findViewById(R.id.currency_full_form);
            flag_id = (ImageView) view.findViewById(R.id.currency_flag);
            selected_tick = (ImageView)  view.findViewById(R.id.selcted_tick);
            view.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

            title.setBackgroundColor(Color.parseColor("black"));

            return false;
        }

        @Override
        public void onClick(View view) {

            if(!AppController.getInstance().selected_categories.contains(moviesList.get(getAdapterPosition()).id)){

                OneSignal.sendTag("cat_"+moviesList.get(getAdapterPosition()).id,moviesList.get(getAdapterPosition()).id);

                AppController.getInstance().selected_categories.add(moviesList.get(getAdapterPosition()).id);
                selected_tick.setImageResource(R.drawable.haam_cat_selected);
                for (int i = 0; i < AppController.getInstance().selected_categories.size(); i++) {
                    String temp="";
                    if(AppController.getInstance().selected_categories.size()<=1){
                        temp=AppController.getInstance().selected_categories.get(i);
//                        homeFragment.to_cat(temp);
                    }else{
                        temp=temp+","+AppController.getInstance().selected_categories.get(i);
//                        homeFragment.to_cat(temp);
                    }

                }
            }else{
                AppController.getInstance().selected_categories.remove(moviesList.get(getAdapterPosition()).id);
                OneSignal.sendTag("cat_" + moviesList.get(getAdapterPosition()).id, "0");
                selected_tick.setImageResource(R.drawable.haam_cat_unselected);
                for (int i = 0; i < AppController.getInstance().selected_categories.size(); i++) {
                    String temp="";
                    if(AppController.getInstance().selected_categories.size()<=1){
                        temp=AppController.getInstance().selected_categories.get(i);
//                        homeFragment.to_cat(temp);
                    }else{
                        temp=temp+","+AppController.getInstance().selected_categories.get(i);
//                        homeFragment.to_cat(temp);
                    }

                }
            }
            Log.e("selected",AppController.getInstance().selected_categories.toString());
        }
    }


    public SelectedCategoriesAdapter(List<Category> moviesList, Context context, SelectedCategoriesActivity homeFragment) {
        this.moviesList = moviesList;
        this.context = context;
        this.homeFragment = homeFragment;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.haam_select_currency_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Category rates = moviesList.get(position);
            holder.rates = rates;

            holder.title.setText(rates.title_ar);

//            holder.base_rate.setText(rates.base_rate);
//
//            holder.value.setText(rates.value);
//            holder.full_name.setText(rates.long_name);

            //  holder.flag_id.setImageResource(rates.flag_id);

            Picasso.with(context).load(rates.image).into(holder.flag_id);

        if(AppController.getInstance().selected_categories.contains(moviesList.get(position).id)){

            holder.selected_tick.setImageResource(R.drawable.haam_cat_selected);
        }else{
            holder.selected_tick.setImageResource(R.drawable.haam_cat_unselected);
        }
    }

    @Override
    public int getItemCount() {

        return moviesList.size();

    }

}