package com.mamacgroup.hamtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
//import com.google.android.gms.ads.doubleclick.PublisherAdView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

public class News_listAdapter extends BaseAdapter{
    Context context;
    ArrayList<News> newses;
    SharedPreferences sharedPreferences;
    String user_lan = "en";
    private static LayoutInflater inflater=null;
    NewsRecycleListActivity mCallback;

    DatabaseHandler databaseHandler ;

    ColorMatrix matrix_bw = new ColorMatrix();
    ColorMatrix matrix_cl = new ColorMatrix();
    ColorMatrixColorFilter filter_bw;
    ColorMatrixColorFilter filter_cl;



    int news_item_ti;
    int news_item_fade_ti;

    public News_listAdapter(Context mainActivity, ArrayList<News> newses, MainActivity mCallback) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.newses = newses;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.e("adapter_size",String.valueOf(newses.size()));
        user_lan=Session.get_user_language(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        databaseHandler = new DatabaseHandler(mainActivity);

        matrix_bw.setSaturation(0);
        matrix_cl.setSaturation(100);
        filter_bw = new ColorMatrixColorFilter(matrix_bw);
        filter_cl = new ColorMatrixColorFilter(matrix_cl);

        news_item_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_ti_nt):context.getResources().getColor(R.color.news_item_ti);
        news_item_fade_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_fade_ti_nt):context.getResources().getColor(R.color.news_item_fade_ti);

       // this.mCallback = mCallback;

    }

    public News_listAdapter(Context mainActivity, ArrayList<News> newses, NewsRecycleListActivity mCallback) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.newses = newses;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.e("adapter_size",String.valueOf(newses.size()));
        user_lan=Session.get_user_language(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


       // imageLoader = AppController.getInstance().getImageLoader();
        databaseHandler = new DatabaseHandler(mainActivity);

        matrix_bw.setSaturation(0);
        matrix_cl.setSaturation(100);
        filter_bw = new ColorMatrixColorFilter(matrix_bw);
        filter_cl = new ColorMatrixColorFilter(matrix_cl);

        news_item_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_ti_nt):context.getResources().getColor(R.color.news_item_ti);
        news_item_fade_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_fade_ti_nt):context.getResources().getColor(R.color.news_item_fade_ti);

        this.mCallback = mCallback;

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

    public class Holder {

        public class NewsHolder {
            TextView time, time2, urgent;
            TextView ch_title, title;
            ImageView news_img, logo, share_btn, video_hint;
            LinearLayout is_urgent;
        }

        public class NewsHolderNoImage {
            TextView time, time2, urgent;
            TextView ch_title, title;
            ImageView  logo, share_btn;
            LinearLayout is_urgent;
        }


        public class ADHolder {
            TextView time, time2, urgent;
            TextView ch_title, title, title_ad;
            ImageView news_img, logo, share_btn, video_hint, video_hint_ad;
            LinearLayout is_urgent;
            GifImageView ad_gif;
            SquareImageview img_ad;
            LinearLayout mPublisherAdView;
        }

        public class VideoADHolder {
            TextView time, time2, urgent;
            TextView ch_title, title, title_ad;
            ImageView news_img, logo, share_btn, video_hint, video_hint_ad;
            LinearLayout is_urgent;
            GifImageView ad_gif;
            SquareImageview img_ad;
            LinearLayout mPublisherAdView;
        }



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
    public int getViewTypeCount() {
        return 4;
    }

    //double_video
    //double_add
    @Override
    public int getItemViewType(int position) {

        if (newses.get(position).type.equals("news"))
        {
            if(newses.get(position).image.equals(""))
                return 2;
            else
                return 0;

        }
        else if(newses.get(position).type.equals("double_add"))
            return 1;
        else
            return 1;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder.NewsHolder newsViewHolder = null;
        Holder.NewsHolderNoImage newsnoimageViewHolder = null;
        Holder.ADHolder adViewHolder = null;
        int type = getItemViewType(position);
        if(type == 0){
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.news_item, null);
                newsViewHolder = new Holder().new NewsHolder();
                newsViewHolder.ch_title = (TextView) convertView.findViewById(R.id.news_ch_title);
                newsViewHolder.title = (TextView) convertView.findViewById(R.id.news_title);
                newsViewHolder.time = (TextView) convertView.findViewById(R.id.news_timee);
                newsViewHolder.time2 = (TextView) convertView.findViewById(R.id.news_time2);
                newsViewHolder.urgent = (TextView) convertView.findViewById(R.id.is_urgent_tv);
                newsViewHolder.video_hint = (ImageView) convertView.findViewById(R.id.video_hint);
                newsViewHolder.urgent.setText(Session.getword(context, "urgent"));
                newsViewHolder.is_urgent = (LinearLayout) convertView.findViewById(R.id.is_urgent);
                newsViewHolder.news_img = (ImageView) convertView.findViewById(R.id.news_img);
                newsViewHolder.share_btn = (ImageView) convertView.findViewById(R.id.share_btn_item);
                newsViewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
                convertView.setTag(newsViewHolder);
            }else{
                    newsViewHolder = (Holder.NewsHolder)convertView.getTag();
            }
            newsViewHolder.title.setTextSize(sharedPreferences.getInt("font_sizee", 17));
            newsViewHolder.ch_title.setText(newses.get(position).chanels.get_ch_title(context));
            newsViewHolder.title.setText(Html.fromHtml(newses.get(position).title));
            Log.e("title", newses.get(position).title);
            newsViewHolder.time.setText(newses.get(position).get_time(context));
            newsViewHolder.time2.setText(newses.get(position).get_time(context));
            Picasso.with(context).load(newses.get(position).chanels.ch_image).into(newsViewHolder.logo);
            newsViewHolder.news_img.setImageResource(R.drawable.loading);

            //imageLoader.displayImage(newses.get(position).image, newsViewHolder.news_img);



//                imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(newsViewHolder.news_img,
//                        R.drawable.loading, R.drawable
//                                .app_icon));

         //   Glide.with(context).load(newses.get(position).image).error(R.drawable.loading).placeholder(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(newsViewHolder.news_img);
           //                 newsViewHolder.news_img.setVisibility(View.VISIBLE);

                Picasso.with(context).load(newses.get(position).image).placeholder(R.drawable.loading).noFade().into(newsViewHolder.news_img);




            if(databaseHandler.news_opened(newses.get(position).id).equals("0")){

                newsViewHolder.news_img.setColorFilter(null);
                newsViewHolder.title.setTextColor(news_item_ti);

                }else{
                    newsViewHolder.news_img.setColorFilter(filter_bw);
                    newsViewHolder.title.setTextColor(news_item_fade_ti);
                }





            if (newses.get(position).is_urgent.equals("1")) {
                newsViewHolder.is_urgent.setVisibility(View.VISIBLE);
                newsViewHolder.time2.setVisibility(View.VISIBLE);
                newsViewHolder.time.setVisibility(View.GONE);
            } else {
                newsViewHolder.is_urgent.setVisibility(View.GONE);
                newsViewHolder.time.setVisibility(View.VISIBLE);
                newsViewHolder.time2.setVisibility(View.GONE);

            }

            if (newses.get(position).video.equals("") && newses.get(position).mp4.equals("")) {
                newsViewHolder.video_hint.setVisibility(View.GONE);
            } else {
                newsViewHolder.video_hint.setVisibility(View.VISIBLE);
            }

            final Holder.NewsHolder finalNewsViewHolder = newsViewHolder;
            newsViewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        finalNewsViewHolder.news_img.setColorFilter(filter_bw);
                        finalNewsViewHolder.title.setTextColor(news_item_fade_ti);

                    Session.count_set(context,"0");
                    mCallback.newsclickednofinish(newses.get(position));

                }
            });
            newsViewHolder.news_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        finalNewsViewHolder.news_img.setColorFilter(filter_bw);
                        finalNewsViewHolder.title.setTextColor(news_item_fade_ti);

                    Session.count_set(context,"0");
                    mCallback.newsclickednofinish(newses.get(position));
                }
            });

            newsViewHolder.ch_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.chanel_selected(newses.get(position).chanels);
                }
            });

            newsViewHolder.logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.chanel_selected(newses.get(position).chanels);
                }
            });
            newsViewHolder.share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, newses.get(position).whatsapp_str);
//                    sendIntent.setType("text/plain");
//                    context.startActivity(sendIntent);
//

                    mCallback.show_new_popup(v,newses.get(position));

                }
            });



        }else if(type == 2){
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.news_item_no_image, null);
                newsnoimageViewHolder = new Holder().new NewsHolderNoImage();

                newsnoimageViewHolder.ch_title = (TextView) convertView.findViewById(R.id.news_ch_title);
                newsnoimageViewHolder.title = (TextView) convertView.findViewById(R.id.news_title);
                newsnoimageViewHolder.time = (TextView) convertView.findViewById(R.id.news_timee);
                newsnoimageViewHolder.time2 = (TextView) convertView.findViewById(R.id.news_time2);
                newsnoimageViewHolder.urgent = (TextView) convertView.findViewById(R.id.is_urgent_tv);
                newsnoimageViewHolder.urgent.setText(Session.getword(context, "urgent"));
                newsnoimageViewHolder.is_urgent = (LinearLayout) convertView.findViewById(R.id.is_urgent);
                newsnoimageViewHolder.share_btn = (ImageView) convertView.findViewById(R.id.share_btn_item);
                newsnoimageViewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
                convertView.setTag(newsnoimageViewHolder);

            }else{
                newsnoimageViewHolder = (Holder.NewsHolderNoImage)convertView.getTag();
            }
            newsnoimageViewHolder.title.setTextSize(sharedPreferences.getInt("font_sizee", 17));
            newsnoimageViewHolder.ch_title.setText(newses.get(position).chanels.get_ch_title(context));
            newsnoimageViewHolder.title.setText(Html.fromHtml(newses.get(position).title));
            Log.e("title", newses.get(position).title);
            newsnoimageViewHolder.time.setText(newses.get(position).get_time(context));
            newsnoimageViewHolder.time2.setText(newses.get(position).get_time(context));
            Picasso.with(context).load(newses.get(position).chanels.ch_image).into(newsnoimageViewHolder.logo);


            if (newses.get(position).is_urgent.equals("1")) {
                newsnoimageViewHolder.is_urgent.setVisibility(View.VISIBLE);
                newsnoimageViewHolder.time2.setVisibility(View.VISIBLE);
                newsnoimageViewHolder.time.setVisibility(View.GONE);
            } else {
                newsnoimageViewHolder.is_urgent.setVisibility(View.GONE);
                newsnoimageViewHolder.time.setVisibility(View.VISIBLE);
                newsnoimageViewHolder.time2.setVisibility(View.GONE);

            }

            newsnoimageViewHolder.share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, newses.get(position).whatsapp_str);
//                    sendIntent.setType("text/plain");
//                    context.startActivity(sendIntent);

                    mCallback.show_new_popup(v,newses.get(position));
                }
            });

            newsnoimageViewHolder.ch_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.chanel_selected(newses.get(position).chanels);
                }
            });

            newsnoimageViewHolder.logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.chanel_selected(newses.get(position).chanels);
                }
            });

            final Holder.NewsHolderNoImage finalNewsnoimageViewHolder = newsnoimageViewHolder;
            newsnoimageViewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        finalNewsnoimageViewHolder.title.setTextColor(news_item_fade_ti);

                    Session.count_set(context,"0");
                    mCallback.newsclickednofinish(newses.get(position));

                }
            });

            if(databaseHandler.news_opened(newses.get(position).id).equals("0")){


                newsnoimageViewHolder.title.setTextColor(news_item_ti);

            }else{

                newsnoimageViewHolder.title.setTextColor(news_item_fade_ti);
            }



        }


        else if(type==1){
            if (convertView==null) {
                convertView = inflater.inflate(R.layout.news_item_google_ad, null);
                adViewHolder = new Holder().new ADHolder();
                adViewHolder.img_ad = (SquareImageview) convertView.findViewById(R.id.news_img_ad);
                adViewHolder.ad_gif=(GifImageView)convertView.findViewById(R.id.ad_gif);
                adViewHolder.video_hint_ad= (ImageView) convertView.findViewById(R.id.video_hint_ad);
                adViewHolder.title_ad = (com.mamacgroup.hamtest.MyTextView1) convertView.findViewById(R.id.news_title_ad);
                adViewHolder.mPublisherAdView = (LinearLayout) convertView.findViewById(R.id.publisherAdView);


//                adViewHolder.mPublisherAdView.setAdSizes(new AdSize(300,250));
//                adViewHolder.mPublisherAdView.setAdUnitId(newses.get(position).title);
//                Log.e("add_id",newses.get(position).title);
//                adViewHolder.mPublisherAdView.loadAd(adRequest);


                convertView.setTag(adViewHolder);

            }else{
                adViewHolder = (Holder.ADHolder) convertView.getTag();
            }

            adViewHolder.title_ad.setText(Session.getword(context, "sponsered"));
            adViewHolder.mPublisherAdView.removeAllViews();

//            PublisherAdView mPublisherAdView;
//            mPublisherAdView = new PublisherAdView(context);
//            mPublisherAdView.setAdSizes(new AdSize(300,250));
//            mPublisherAdView.setAdUnitId(newses.get(position).title);
//            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().
//                    // addTestDevice("6CF13E43F2584625AF6152F65DAC084E").
//                    //addTestDevice("DF05CE517F21FBE0F3D2BC342BBEBCD9").
//                    //addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR).
//                            build();
//            adViewHolder.mPublisherAdView.addView(mPublisherAdView);
//            mPublisherAdView.loadAd(adRequest);


//            if (newses.get(position).video.equals("") && newses.get(position).mp4.equals("")) {
//                adViewHolder.video_hint_ad.setVisibility(View.GONE);
//            } else {
//                adViewHolder.video_hint_ad.setVisibility(View.VISIBLE);
//            }
//            if (newses.get(position).image.endsWith("gif")) {
//                Log.e("ch_image", newses.get(position).image);
//                Glide.with(context).load(newses.get(position).image).asGif().error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(adViewHolder.img_ad);
//            }else {
//                          Glide.with(context).load(newses.get(position).image).error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.ALL).into(adViewHolder.img_ad);
//
//            }


            //DF05CE517F21FBE0F3D2BC342BBEBCD9


        }


        else{
            if (convertView==null) {

                convertView = inflater.inflate(R.layout.news_item_google_video_ad, null);
//                videoADHolder = new Holder().new VideoADHolder();
//                videoADHolder.img_ad = (SquareImageview) convertView.findViewById(R.id.news_img_ad);
//                videoADHolder.ad_gif=(GifImageView)convertView.findViewById(R.id.ad_gif);
//                videoADHolder.video_hint_ad= (ImageView) convertView.findViewById(R.id.video_hint_ad);
//                videoADHolder.title_ad = (com.mamacgroup.hamtest.MyTextView1) convertView.findViewById(R.id.news_title_ad);
//                videoADHolder.mPublisherAdView = (LinearLayout) convertView.findViewById(R.id.publisherAdView);

//                PublisherAdRequest adRequest = new PublisherAdRequest.Builder().
//                        // addTestDevice("6CF13E43F2584625AF6152F65DAC084E").
//                        //addTestDevice("DF05CE517F21FBE0F3D2BC342BBEBCD9").
//                        //addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR).
//                                build();
//                videoADHolder.mPublisherAdView.setAdSizes(new AdSize(300,250));
//                videoADHolder.mPublisherAdView.setAdUnitId(newses.get(position).title);
//                Log.e("add_id",newses.get(position).title);
//                videoADHolder.mPublisherAdView.loadAd(adRequest);

 //               convertView.setTag(videoADHolder);

                Log.e("vide","aa");
                Log.e("add_tittle", newses.get(position).type);

            }else{
                //videoADHolder = (Holder.VideoADHolder) convertView.getTag();

                Log.e("vide","aa");
                Log.e("add_tittle", newses.get(position).type);
            }

//            videoADHolder.title_ad.setText(Session.getword(context, "sponsered"));
//
//            videoADHolder.mPublisherAdView.removeAllViews();
//
//            PublisherAdView mPublisherAdView;
//            mPublisherAdView = new PublisherAdView(context);
//            mPublisherAdView.setAdSizes(new AdSize(300,250));
//            mPublisherAdView.setAdUnitId(newses.get(position).title);
//            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().
//                    // addTestDevice("6CF13E43F2584625AF6152F65DAC084E").
//                    //addTestDevice("DF05CE517F21FBE0F3D2BC342BBEBCD9").
//                    //addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR).
//                            build();
//            videoADHolder.mPublisherAdView.addView(mPublisherAdView);
//            mPublisherAdView.loadAd(adRequest);



//            if (newses.get(position).video.equals("") && newses.get(position).mp4.equals("")) {
//                adViewHolder.video_hint_ad.setVisibility(View.GONE);
//            } else {
//                adViewHolder.video_hint_ad.setVisibility(View.VISIBLE);
//            }
//            if (newses.get(position).image.endsWith("gif")) {
//                Log.e("ch_image", newses.get(position).image);
//                Glide.with(context).load(newses.get(position).image).asGif().error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(adViewHolder.img_ad);
//            }else {
//                          Glide.with(context).load(newses.get(position).image).error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.ALL).into(adViewHolder.img_ad);
//
//            }


            //DF05CE517F21FBE0F3D2BC342BBEBCD9


                    }



        return convertView;

//        View rowView;
//
//
//        if (newses.get(position).type.equals("news")) {
//            rowView = inflater.inflate(R.layout.haam_news_item, null);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            holder.ch_title = (com.mamacgroup.naqsh.MyTextView1) rowView.findViewById(R.id.news_ch_title);
//            holder.title = (com.mamacgroup.naqsh.MyTextView1) rowView.findViewById(R.id.news_title);
//            holder.time = (com.mamacgroup.naqsh.MyTextView) rowView.findViewById(R.id.news_timee);
//            holder.time2 = (com.mamacgroup.naqsh.MyTextView) rowView.findViewById(R.id.news_time2);
//            holder.urgent = (com.mamacgroup.naqsh.MyTextView) rowView.findViewById(R.id.is_urgent_tv);
//            holder.video_hint = (ImageView) rowView.findViewById(R.id.video_hint);
//            holder.urgent.setText(Session.getword(context, "urgent"));
//            holder.is_urgent = (LinearLayout) rowView.findViewById(R.id.is_urgent);
//            holder.news_img = (ImageView) rowView.findViewById(R.id.news_img);
//
//            holder.share_btn = (ImageView) rowView.findViewById(R.id.share_btn_item);
//            holder.logo = (ImageView) rowView.findViewById(R.id.logo);
//            holder.title.setTextSize(sharedPreferences.getInt("font_sizee", 17));
//            holder.ch_title.setText(newses.get(position).chanels.get_ch_title(context));
//            holder.title.setText(Html.fromHtml(newses.get(position).title));
//            Log.e("title", newses.get(position).title);
//            holder.time.setText(newses.get(position).get_time(context));
//            holder.time2.setText(newses.get(position).get_time(context));
//
//            if (newses.get(position).image.equals(""))
//                holder.news_img.setVisibility(View.GONE);
//
//            else {
//                ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
//                        .getImageLoader();
//                imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(holder.news_img,
//                        R.drawable.loading, R.drawable
//                                .app_icon));
////            Glide.with(context).load(newses.get(position).image).error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.news_img);
//                holder.news_img.setVisibility(View.VISIBLE);
//            }
//            Log.e("ch_image", newses.get(position).chanels.ch_image);
//            Glide.with(context).load(newses.get(position).chanels.ch_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.logo);
//            if (newses.get(position).is_urgent.equals("1")) {
//                holder.is_urgent.setVisibility(View.VISIBLE);
//                holder.time2.setVisibility(View.VISIBLE);
//                holder.time.setVisibility(View.GONE);
//            } else {
//                holder.is_urgent.setVisibility(View.GONE);
//                holder.time.setVisibility(View.VISIBLE);
//                holder.time2.setVisibility(View.GONE);
//
//            }
//            holder.share_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, newses.get(position).link);
//                    sendIntent.setType("text/plain");
//                    context.startActivity(sendIntent);
//                }
//            });
//
//            if (newses.get(position).video.equals("") && newses.get(position).mp4.equals("")) {
//                holder.video_hint.setVisibility(View.GONE);
//            } else {
//                holder.video_hint.setVisibility(View.VISIBLE);
//            }


//            return rowView;
//        } else {
//            rowView = inflater.inflate(R.layout.news_item_ad, null);
//            holder.img_ad = (SquareImageview) rowView.findViewById(R.id.news_img_ad);
//            holder.ad_gif=(GifImageView)rowView.findViewById(R.id.ad_gif);
//            holder.video_hint_ad= (ImageView) rowView.findViewById(R.id.video_hint_ad);
//            holder.title_ad = (com.mamacgroup.naqsh.MyTextView1) rowView.findViewById(R.id.news_title_ad);

//            holder.title_ad.setText(Session.getword(context, "Sponsered"));
//            if (newses.get(position).video.equals("") && newses.get(position).mp4.equals("")) {
//                holder.video_hint_ad.setVisibility(View.GONE);
//            } else {
//                holder.video_hint_ad.setVisibility(View.VISIBLE);
//            }
//            if (newses.get(position).image.endsWith("gif")) {
//                Log.e("ch_image", newses.get(position).image);
//                holder.img_ad.setVisibility(View.VISIBLE);
//                Glide.with(context).load(newses.get(position).image).asGif().error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img_ad);
////                ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
////                        .getImageLoader();
////                imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(holder.ad_gif,
////                        R.drawable.loading, R.drawable
////                                .app_icon));
//                //            Glide.with(context).load(newses.get(position).image).error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.news_img);
//                holder.ad_gif.setVisibility(View.GONE);
//            }else {
//                holder.ad_gif.setVisibility(View.GONE);
////                ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
////                        .getImageLoader();
////                    imageLoader.get(newses.get(position).image, ImageLoader.getImageListener(holder.img_ad,
////                            R.drawable.loading, R.drawable
////                                    .app_icon));
//                          Glide.with(context).load(newses.get(position).image).error(R.mipmap.app_icon).placeholder(R.drawable.logonaqsh).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_ad);
//                holder.img_ad.setVisibility(View.VISIBLE);
//
//            }
//            return rowView;
//        }

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