package com.mamacgroup.hamtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class News_listAdapternoimage extends BaseAdapter{
    Context context;
    ArrayList<News> newses;
    SharedPreferences sharedPreferences;
    String user_lan = "en";
    MainActivity mCallback;
    private static LayoutInflater inflater=null;
    int news_item_ti;
    int news_item_fade_ti;
    DatabaseHandler databaseHandler ;

    public News_listAdapternoimage(Context mainActivity, ArrayList<News> newses, MainActivity mCallback) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.newses = newses;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.e("adapter_size",String.valueOf(newses.size()));
        user_lan=Session.get_user_language(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mCallback = mCallback;
        news_item_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_ti_nt):context.getResources().getColor(R.color.news_item_ti);
        news_item_fade_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_fade_ti_nt):context.getResources().getColor(R.color.news_item_fade_ti);
        databaseHandler = new DatabaseHandler(mainActivity);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return newses.size();
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
        MyTextView time,time2,urgent;
        MyTextView1 ch_title,title;
        ImageView logo,share_btn;
        LinearLayout is_urgent;
    }
    String convertDate(String inputDate) {
        //2016-08-03 04:00:09
        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;

        try {
            date = theDateFormat.parse(inputDate);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        theDateFormat = new SimpleDateFormat("dd MMM ");

        return theDateFormat.format(date);
    }
    public void refill(ArrayList<News> newses) {
        this.newses.clear();
        this.newses.addAll(newses);
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder= null;


        if(convertView==null) {
            convertView = inflater.inflate(R.layout.news_item_no_image, null);
            holder = new Holder();

            holder.ch_title = (MyTextView1) convertView.findViewById(R.id.news_ch_title);
            holder.title = (MyTextView1) convertView.findViewById(R.id.news_title);
            holder.time = (MyTextView) convertView.findViewById(R.id.news_timee);
            holder.time2 = (MyTextView) convertView.findViewById(R.id.news_time2);
            holder.urgent = (MyTextView) convertView.findViewById(R.id.is_urgent_tv);
            holder.is_urgent = (LinearLayout) convertView.findViewById(R.id.is_urgent);
            holder.share_btn = (ImageView) convertView.findViewById(R.id.share_btn_item);
            holder.logo = (ImageView) convertView.findViewById(R.id.logo);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.urgent.setText(Session.getword(context,"urgent"));
        holder.title.setTextSize(sharedPreferences.getInt("font_sizee", 15));
        holder.ch_title.setText(newses.get(position).chanels.get_ch_title(context));
        holder.title.setText(Html.fromHtml(newses.get(position).title));
        holder.time.setText(newses.get(position).get_time(context));
        holder.time2.setText(newses.get(position).get_time(context));
        Picasso.with(context).load(newses.get(position).chanels.ch_image).into(holder.logo);
        if(databaseHandler.news_opened(newses.get(position).id).equals("0")){


            holder.title.setTextColor(news_item_ti);

        }else{

            holder.title.setTextColor(news_item_fade_ti);
        }

        if(newses.get(position).is_urgent.equals("1")){
            holder.is_urgent.setVisibility(View.VISIBLE);
            holder.time2.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.GONE);
        }
        else {
            holder.is_urgent.setVisibility(View.GONE);
            holder.time.setVisibility(View.VISIBLE);
            holder.time2.setVisibility(View.GONE);
        }

        holder.share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, newses.get(position).whatsapp_str);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });

        holder.ch_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.chanel_selected(newses.get(position).chanels);
            }
        });

        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.chanel_selected(newses.get(position).chanels);
            }
        });

        final Holder finalHolder = holder;
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finalHolder.title.setTextColor(news_item_fade_ti);


                mCallback.newsclicked(newses.get(position));

            }
        });

        return convertView;
    }

    private String get_different_dates(String date) {
        String temp = "Now";


            long seconds = Long.parseLong(date);
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;


                if(days == 0) {
                    if(hours==0) {
                        temp = String.valueOf(minutes) + (minutes <= 1 ? " minute" : " minutes");
                    }
                    else
                        temp = String.valueOf(hours) + (hours == 1 ?" hour":" hours");
                }
                else if(days<7)
                    temp = days<=1? "1 day": String.valueOf(days)+" days";
                else if(days < 365)
                    temp = String.valueOf(days/7) + (days/7==1?" week":" weeks");
                    else if(days < 365)
                      temp = String.valueOf(days/30) + (days/30==1?" month":" months");
                else
                    temp = String.valueOf(days/365) + (days/365==1?" year":" years");
                return temp + " Ago";

    }



}