package com.mamacgroup.hamtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
//import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<News> dataSet;
    Context mContext;
    int total_types;
    MediaPlayer mPlayer;
    SharedPreferences sharedPreferences;
    private boolean fabStateVolume = false;
    Picasso picasso;
    boolean no_image;
    MainActivity mCallback;
    DatabaseHandler databaseHandler;

    ColorMatrix matrix_bw = new ColorMatrix();
    ColorMatrix matrix_cl = new ColorMatrix();
    ColorMatrixColorFilter filter_bw;
    ColorMatrixColorFilter filter_cl;

    int news_item_ti;
    int news_item_fade_ti;



    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {


        MyTextView1 ch_name,news_title;
        MyTextView  news_time;
        MyTextView  news_time_urgent;
        MyTextView  urgent;
        ImageView   share_btn_item;

        CircleImageView ch_logo;
        LinearLayout is_urgent;



        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.ch_name = (MyTextView1) itemView.findViewById(R.id.news_ch_title);
            this.news_time = (MyTextView) itemView.findViewById(R.id.news_time2);
            this.news_time_urgent = (MyTextView) itemView.findViewById(R.id.news_timee);
            this.urgent = (MyTextView) itemView.findViewById(R.id.is_urgent_tv);

            this.news_title = (MyTextView1) itemView.findViewById(R.id.news_title);
            this.ch_logo = (CircleImageView) itemView.findViewById(R.id.logo);
            this.is_urgent = (LinearLayout) itemView.findViewById(R.id.is_urgent);
            this.share_btn_item = (ImageView) itemView.findViewById(R.id.share_btn_item);



        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        ImageView video_hint;

        MyTextView1 ch_name,news_title;
        MyTextView  news_time;
        MyTextView  news_time_urgent;
        CircleImageView ch_logo;
        LinearLayout is_urgent;
        MyTextView  urgent;
        ImageView   share_btn_item;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

           // this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.news_img);
            this.ch_name = (MyTextView1) itemView.findViewById(R.id.news_ch_title);
            this.news_time = (MyTextView) itemView.findViewById(R.id.news_time2);
            this.news_time_urgent = (MyTextView) itemView.findViewById(R.id.news_timee);
            this.urgent = (MyTextView) itemView.findViewById(R.id.is_urgent_tv);

            this.news_title = (MyTextView1) itemView.findViewById(R.id.news_title);
            this.ch_logo = (CircleImageView) itemView.findViewById(R.id.logo);
            this.is_urgent = (LinearLayout) itemView.findViewById(R.id.is_urgent);
            this.video_hint = (ImageView) itemView.findViewById(R.id.video_hint);
            this.share_btn_item = (ImageView) itemView.findViewById(R.id.share_btn_item);

        }

    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {


        LinearLayout mPublisherAdView;


        public AudioTypeViewHolder(View itemView) {
            super(itemView);

         //   this.txtType = (TextView) itemView.findViewById(R.id.type);
         //   this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);

            this.mPublisherAdView = (LinearLayout) itemView.findViewById(R.id.publisherAdView);


        }

    }


    public static class FooterTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;

        public FooterTypeViewHolder(View itemView) {
            super(itemView);


        }

    }




    public MultiViewTypeAdapter(ArrayList<News> data, Context context,boolean no_image,MainActivity mCallback) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.no_image = no_image;
        this.mCallback = mCallback;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        databaseHandler = new DatabaseHandler(context);
        matrix_bw.setSaturation(0);
        matrix_cl.setSaturation(100);
        filter_bw = new ColorMatrixColorFilter(matrix_bw);
        filter_cl = new ColorMatrixColorFilter(matrix_cl);


        news_item_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_ti_nt):context.getResources().getColor(R.color.news_item_ti);
        news_item_fade_ti = Session.getTheme(context)?context.getResources().getColor(R.color.news_item_fade_ti_nt):context.getResources().getColor(R.color.news_item_fade_ti);


    }

    private void load_chanel_image(String url,CircleImageView circleImageView){
       Picasso.with(mContext).load(url).tag("someTag").fit().noFade().into(circleImageView);
    }

    private void load_news_image(String url,ImageView imageView){

        Picasso.with(mContext).load(url).tag("someTag").fit().noFade().into(imageView);
    }

    private void share_news(String whatsapp_str){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, whatsapp_str);
        sendIntent.setType("text/plain");
        mContext.startActivity(sendIntent);

    }


    private void go_to_news_detail(News news){
        mCallback.newsclicked(news);
    }
    private void go_to_chanel_detail(News news){
        mCallback.chanel_selected(news.chanels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {

            case News.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_no_image, parent, false);
                return new TextTypeViewHolder(view);
            case News.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
                return new ImageTypeViewHolder(view);
            case News.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_google_ad, parent, false);
                return new AudioTypeViewHolder(view);

            case News.FOOTER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress, parent, false);
                return new FooterTypeViewHolder(view);


        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        if(position==dataSet.size()-1)
            return News.FOOTER_TYPE;


        switch (dataSet.get(position).view_type) {
            case 0:
                return News.TEXT_TYPE;
            case 1:
                if(no_image)
                return News.TEXT_TYPE;
                else
                return News.IMAGE_TYPE;
            case 2:
                return News.AUDIO_TYPE;

            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final News object = dataSet.get(listPosition);


        if (object != null && listPosition!=dataSet.size()-1) {




            switch (object.view_type) {

                case News.TEXT_TYPE:
                    final TextTypeViewHolder holdermin = (TextTypeViewHolder) holder;
                    //((TextTypeViewHolder) holder).txtType.setText(object.News);
                    holdermin.ch_name.setText(object.chanels.get_ch_title(mContext));
                    holdermin.news_time.setText(object.get_time(mContext));
                    holdermin.news_time_urgent.setText(object.get_time(mContext));
                    holdermin.news_title.setText(object.title);
                    holdermin.news_title.setTextSize(sharedPreferences.getInt("font_sizee", 17));
                    holdermin.urgent.setText(Session.getword(mContext, "urgent"));
                    load_chanel_image(object.chanels.ch_image,holdermin.ch_logo);

//                    holdermin.news_title.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            holdermin.news_title.setTextColor(news_item_fade_ti);
//
//                            go_to_news_detail(object);
//                        }
//                    });
//
//                    holdermin.ch_name.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            go_to_chanel_detail(object);
//                        }
//                    });
//                    holdermin.ch_logo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            go_to_chanel_detail(object);
//                        }
//                    });
//
//                    if(object.is_urgent.equals("1")){
//
//                        holdermin.is_urgent.setVisibility(View.VISIBLE);
//                        holdermin.news_time_urgent.setVisibility(View.GONE);
//                        holdermin.news_time.setVisibility(View.VISIBLE);
//
//                    }
//
//                    else{
//
//                        holdermin.is_urgent.setVisibility(View.GONE);
//                        holdermin.news_time_urgent.setVisibility(View.VISIBLE);
//                        holdermin.news_time.setVisibility(View.GONE);
//
//                    }
//
//
//
//                    if(databaseHandler.news_opened(object.id).equals("0")){
//
//
//                        holdermin.news_title.setTextColor(news_item_ti);
//
//
//                    }else{
//
//                        holdermin.news_title.setTextColor(news_item_fade_ti);
//                    }
//
//                    holdermin.share_btn_item.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            share_news(object.whatsapp_str);
//                        }
//                    });

                    break;

                case News.IMAGE_TYPE:
                  //  ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                   // ((ImageTypeViewHolder) holder).image.setImageResource(object.data);


                    if(no_image){
                        final TextTypeViewHolder holderminno = (TextTypeViewHolder) holder;

                        holderminno.ch_name.setText(object.chanels.get_ch_title(mContext));
                        load_chanel_image(object.chanels.ch_image,holderminno.ch_logo);

//                        ((TextTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                go_to_chanel_detail(object);
//                            }
//                        });
//                        holderminno.ch_logo.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                go_to_chanel_detail(object);
//                            }
//                        });



                        holderminno.news_time.setText(object.get_time(mContext));
                        holderminno.news_time_urgent.setText(object.get_time(mContext));
                        holderminno.news_title.setTextSize(sharedPreferences.getInt("font_sizee", 17));
                        holderminno.urgent.setText(Session.getword(mContext, "urgent"));
                        holderminno.news_title.setText(object.title);

//                        holderminno.news_title.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                holderminno.news_title.setTextColor(news_item_fade_ti);
//                                go_to_news_detail(object);
//                            }
//                        });
//
//                        if(object.is_urgent.equals("1")){
//
//                            holderminno.is_urgent.setVisibility(View.VISIBLE);
//                            holderminno.news_time_urgent.setVisibility(View.GONE);
//                            holderminno.news_time.setVisibility(View.VISIBLE);
//
//
//
//                        }
//
//                        else{
//                            holderminno.is_urgent.setVisibility(View.GONE);
//                            holderminno.news_time_urgent.setVisibility(View.VISIBLE);
//                            holderminno.news_time.setVisibility(View.GONE);
//
//                        }
//
//
//                        if(databaseHandler.news_opened(object.id).equals("0")){
//
//
//                            holderminno.news_title.setTextColor(news_item_ti);
//
//                        }else{
//
//                            holderminno.news_title.setTextColor(news_item_fade_ti);
//                        }
//                        holderminno.share_btn_item.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                share_news(object.whatsapp_str);
//                            }
//                        });
//





                    }else{

                        ((ImageTypeViewHolder) holder).ch_name.setText(object.chanels.get_ch_title(mContext));
                        ((ImageTypeViewHolder) holder).news_time.setText(object.get_time(mContext));
                        ((ImageTypeViewHolder) holder).news_time_urgent.setText(object.get_time(mContext));
                        ((ImageTypeViewHolder) holder).news_title.setTextSize(sharedPreferences.getInt("font_sizee", 17));
                        ((ImageTypeViewHolder) holder).urgent.setText(Session.getword(mContext, "urgent"));

                        ((ImageTypeViewHolder) holder).news_title.setText(object.title);
                        load_chanel_image(object.chanels.ch_image,((ImageTypeViewHolder) holder).ch_logo);
                        load_news_image(object.image,((ImageTypeViewHolder) holder).image);


//                        ((ImageTypeViewHolder) holder).news_title.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
//                                ((ImageTypeViewHolder) holder).image.setColorFilter(filter_bw);
//                                go_to_news_detail(object);
//                            }
//                        });
//
//                        ((ImageTypeViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
//                                ((ImageTypeViewHolder) holder).image.setColorFilter(filter_bw);
//
//                                go_to_news_detail(object);
//                            }
//                        });
//                        ((ImageTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                go_to_chanel_detail(object);
//                            }
//                        });
//                        ((ImageTypeViewHolder) holder).ch_logo.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                go_to_chanel_detail(object);
//                            }
//                        });

//                        if(object.is_urgent.equals("1")){
//                            ((ImageTypeViewHolder) holder).is_urgent.setVisibility(View.VISIBLE);
//                            ((ImageTypeViewHolder) holder).news_time_urgent.setVisibility(View.GONE);
//                            ((ImageTypeViewHolder) holder).news_time.setVisibility(View.VISIBLE);
//                        }
//
//                        else{
//                            ((ImageTypeViewHolder) holder).is_urgent.setVisibility(View.GONE);
//                            ((ImageTypeViewHolder) holder).news_time_urgent.setVisibility(View.VISIBLE);
//                            ((ImageTypeViewHolder) holder).news_time.setVisibility(View.GONE);
//                        }
//
//
//                        if(databaseHandler.news_opened(object.id).equals("0")){
//
//                            ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_ti);
//                            ((ImageTypeViewHolder) holder).image.setColorFilter(null);
//
//
//                        }else{
//
//                            ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
//                            ((ImageTypeViewHolder) holder).image.setColorFilter(filter_bw);
//                        }
//
//                        if (object.video.equals("") && object.mp4.equals("")){
//                            ((ImageTypeViewHolder) holder).video_hint.setVisibility(View.GONE);
//
//                        }else{
//                            ((ImageTypeViewHolder) holder).video_hint.setVisibility(View.VISIBLE);
//                        }
//
//                        ((ImageTypeViewHolder) holder).share_btn_item.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                share_news(object.whatsapp_str);
//                            }
//                        });



                    }

                    break;

                case News.AUDIO_TYPE:

                   // ((AudioTypeViewHolder) holder).txtType.setText(object.text);


//                    ((AudioTypeViewHolder) holder).fab.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            if (fabStateVolume) {
//                                if (mPlayer.isPlaying()) {
//                                    mPlayer.stop();
//
//                                }
//                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.volume);
//                                fabStateVolume = false;
//
//                            } else {
//                                mPlayer = MediaPlayer.create(mContext, R.raw.sound);
//                                mPlayer.setLooping(true);
//                                mPlayer.start();
//                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.mute);
//                                fabStateVolume = true;
//
//                            }
//                        }
//                    });

                    ((AudioTypeViewHolder) holder).mPublisherAdView.removeAllViews();

//                    PublisherAdView mPublisherAdView;
//                    mPublisherAdView = new PublisherAdView(mContext);
//                    mPublisherAdView.setAdSizes(new AdSize(300,250));
//                    mPublisherAdView.setAdUnitId(object.title);
//                    PublisherAdRequest adRequest = new PublisherAdRequest.Builder().
//                            // addTestDevice("6CF13E43F2584625AF6152F65DAC084E").
//                            //addTestDevice("DF05CE517F21FBE0F3D2BC342BBEBCD9").
//                            //addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR).
//                                    build();
//                    ((AudioTypeViewHolder) holder).mPublisherAdView.addView(mPublisherAdView);
//                    mPublisherAdView.loadAd(adRequest);
//


                    break;



            }
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
