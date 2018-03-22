package com.mamacgroup.hamtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.AdListener;
//import com.facebook.ads.AdSettings;
//import com.facebook.ads.NativeAd;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
//import com.google.android.gms.ads.doubleclick.PublisherAdView;
//import com.mamacgroup.hamtest.tooltips.Tooltip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeActAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<News> dataSet;
    Context mContext;
    int total_types;
    MediaPlayer mPlayer;
    SharedPreferences sharedPreferences;
    private boolean fabStateVolume = false;
    Picasso picasso;
    boolean no_image;
    NewsRecycleListActivity mCallback;
    DatabaseHandler databaseHandler;

    ColorMatrix matrix_bw = new ColorMatrix();
    ColorMatrix matrix_cl = new ColorMatrix();
    ColorMatrixColorFilter filter_bw;
    ColorMatrixColorFilter filter_cl;

    int news_item_ti;
    int news_item_fade_ti;

    int lastPosition = -1;

    boolean end_of_news = false;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {


        TextView ch_name,news_title;
        TextView  news_time;
        TextView  news_time_urgent;
        TextView  urgent;
        ImageView   share_btn_item;

        CircleImageView ch_logo;
        LinearLayout is_urgent;



        public TextTypeViewHolder(View itemView) {
            super(itemView);

            this.ch_name = (TextView) itemView.findViewById(R.id.news_ch_title);
            this.news_time = (TextView) itemView.findViewById(R.id.news_time2);
            this.news_time_urgent = (TextView) itemView.findViewById(R.id.news_timee);
            this.urgent = (TextView) itemView.findViewById(R.id.is_urgent_tv);

            this.news_title = (TextView) itemView.findViewById(R.id.news_title);
            this.ch_logo = (CircleImageView) itemView.findViewById(R.id.logo);
            this.is_urgent = (LinearLayout) itemView.findViewById(R.id.is_urgent);
            this.share_btn_item = (ImageView) itemView.findViewById(R.id.share_btn_item);

        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        ImageView video_hint;

        TextView ch_name,news_title;
        TextView  news_time;
        TextView  news_time_urgent;
        CircleImageView ch_logo;
        LinearLayout is_urgent;
        TextView  urgent;
        ImageView   share_btn_item;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

           // this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.news_img);
            this.ch_name = (TextView) itemView.findViewById(R.id.news_ch_title);
            this.news_time = (TextView) itemView.findViewById(R.id.news_time2);
            this.news_time_urgent = (TextView) itemView.findViewById(R.id.news_timee);
            this.urgent = (TextView) itemView.findViewById(R.id.is_urgent_tv);

            this.news_title = (TextView) itemView.findViewById(R.id.news_title);
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


    public static class FBTypeViewHolder extends RecyclerView.ViewHolder {

//        private NativeAd nativeAd;
        ImageView image;


        public FBTypeViewHolder(View itemView) {
            super(itemView);

            // this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.news_img);

        }

    }





    public static class FooterTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        ProgressBar progressBar;


        public FooterTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.end_of_news);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar3);

        }
    }



    public static class SquareTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        ProgressBar progressBar;

        VideoImageview image;
        ImageView video_hint;

        TextView ch_name,news_title;
        TextView  news_time;
        TextView  news_time_urgent;
        CircleImageView ch_logo;
        LinearLayout is_urgent;
        TextView  urgent;
        ImageView   share_btn_item;



        public SquareTypeViewHolder(View itemView) {
            super(itemView);

            //this.txtType = (TextView) itemView.findViewById(R.id.end_of_news);
            //this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar3);

            this.image = (VideoImageview) itemView.findViewById(R.id.news_img);
            this.ch_name = (TextView) itemView.findViewById(R.id.news_ch_title);
            this.news_time = (TextView) itemView.findViewById(R.id.news_time2);
            this.news_time_urgent = (TextView) itemView.findViewById(R.id.news_timee);
            this.urgent = (TextView) itemView.findViewById(R.id.is_urgent_tv);

            this.news_title = (TextView) itemView.findViewById(R.id.news_title);
            this.ch_logo = (CircleImageView) itemView.findViewById(R.id.logo);
            this.is_urgent = (LinearLayout) itemView.findViewById(R.id.is_urgent);
            this.video_hint = (ImageView) itemView.findViewById(R.id.video_hint);
            this.share_btn_item = (ImageView) itemView.findViewById(R.id.share_btn_item);


        }





    }





    public MultiViewTypeActAdapter(ArrayList<News> data, Context context, boolean no_image, NewsRecycleListActivity mCallback) {
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
        boolean end_of_news = false;

    }

    private void load_chanel_image(String url,CircleImageView circleImageView){
       Picasso.with(mContext).load(url).into(circleImageView);
    }

    private void load_news_image(String url,ImageView imageView){

        Picasso.with(mContext).load(url).into(imageView);
    }


    private void share_news(News news,View aView){

//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, whatsapp_str);
//                    sendIntent.setType("text/plain");
//                    mContext.startActivity(sendIntent);

//         show_share(aView);

        mCallback.show_new_popup(aView,news);


    }


    private void go_to_news_detail(News news){

        if(news.link.equals(news.video)){
            Intent intent = new Intent(mContext, YoutubePlayer.class);
            intent.putExtra("video", news.video);
            mContext.startActivity(intent);


        }else {
            Session.count_set(mContext, "0");
            mCallback.newsclicked(news);
        }
    }
    private void go_to_chanel_detail(News news){
        mCallback.chanel_selected_clear(news.chanels);
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
            case News.FB_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_fb_ad, parent, false);
                return new FBTypeViewHolder(view);

            case News.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_google_ad, parent, false);
                return new AudioTypeViewHolder(view);

            case News.FOOTER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress, parent, false);
                return new FooterTypeViewHolder(view);


            case News.square_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_square, parent, false);
                return new SquareTypeViewHolder(view);


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
            case 4:
                return News.FB_TYPE;

            case 5:
                return News.square_TYPE;

            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {



        final News object = dataSet.get(listPosition);


        if (object != null && listPosition!=dataSet.size()-1) {




            switch (object.view_type) {




                case News.square_TYPE:
                    //  ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                    // ((ImageTypeViewHolder) holder).image.setImageResource(object.data);


                    if(no_image){
                        final SquareTypeViewHolder holderminno = (SquareTypeViewHolder) holder;

                        holderminno.ch_name.setText(object.chanels.get_ch_title(mContext));
                        load_chanel_image(object.chanels.ch_image,holderminno.ch_logo);

                        ((TextTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go_to_chanel_detail(object);
                            }
                        });
                        holderminno.ch_logo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go_to_chanel_detail(object);
                            }
                        });



                        holderminno.news_time.setText(object.get_time(mContext));
                        holderminno.news_time_urgent.setText(object.get_time(mContext));
                        holderminno.news_title.setTextSize(sharedPreferences.getInt("font_sizee", 20));
                        holderminno.urgent.setText(Session.getword(mContext, "urgent"));
                        holderminno.news_title.setText(Html.fromHtml(object.title));

                        holderminno.news_title.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holderminno.news_title.setTextColor(news_item_fade_ti);
                                go_to_news_detail(object);
                            }
                        });

                        if(object.is_urgent.equals("1")){

                            holderminno.is_urgent.setVisibility(View.VISIBLE);
                            holderminno.news_time_urgent.setVisibility(View.GONE);
                            holderminno.news_time.setVisibility(View.VISIBLE);



                        }

                        else{
                            holderminno.is_urgent.setVisibility(View.GONE);
                            holderminno.news_time_urgent.setVisibility(View.VISIBLE);
                            holderminno.news_time.setVisibility(View.GONE);

                        }


                        if(databaseHandler.news_opened(object.id).equals("0")){


                            holderminno.news_title.setTextColor(news_item_ti);

                        }else{

                            holderminno.news_title.setTextColor(news_item_fade_ti);
                        }


                        holderminno.share_btn_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                share_news(object,holderminno.share_btn_item);

                            }
                        });






                    }else{

                        ((SquareTypeViewHolder) holder).ch_name.setText(object.chanels.get_ch_title(mContext));
                        ((SquareTypeViewHolder) holder).news_time.setText(object.get_time(mContext));
                        ((SquareTypeViewHolder) holder).news_time_urgent.setText(object.get_time(mContext));
                        ((SquareTypeViewHolder) holder).news_title.setTextSize(sharedPreferences.getInt("font_sizee", 20));
                        ((SquareTypeViewHolder) holder).urgent.setText(Session.getword(mContext, "urgent"));

                        ((SquareTypeViewHolder) holder).news_title.setText(Html.fromHtml(object.title));
                        load_chanel_image(object.chanels.ch_image,((SquareTypeViewHolder) holder).ch_logo);

                        load_news_image(object.image,((SquareTypeViewHolder) holder).image);


                        ((SquareTypeViewHolder) holder).news_title.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((SquareTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
                                ((SquareTypeViewHolder) holder).image.setColorFilter(filter_bw);
                                go_to_news_detail(object);
                            }
                        });

                        ((SquareTypeViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((SquareTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
                                ((SquareTypeViewHolder) holder).image.setColorFilter(filter_bw);
                                go_to_news_detail(object);
                            }
                        });
                        ((SquareTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go_to_chanel_detail(object);
                            }
                        });
                        ((SquareTypeViewHolder) holder).ch_logo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                go_to_chanel_detail(object);
                            }
                        });

                        if(object.is_urgent.equals("1")){
                            ((SquareTypeViewHolder) holder).is_urgent.setVisibility(View.VISIBLE);
                            ((SquareTypeViewHolder) holder).news_time_urgent.setVisibility(View.GONE);
                            ((SquareTypeViewHolder) holder).news_time.setVisibility(View.VISIBLE);
                        }

                        else{
                            ((SquareTypeViewHolder) holder).is_urgent.setVisibility(View.GONE);
                            ((SquareTypeViewHolder) holder).news_time_urgent.setVisibility(View.VISIBLE);
                            ((SquareTypeViewHolder) holder).news_time.setVisibility(View.GONE);
                        }


                        if(databaseHandler.news_opened(object.id).equals("0")){

                            ((SquareTypeViewHolder) holder).news_title.setTextColor(news_item_ti);
                            ((SquareTypeViewHolder) holder).image.setColorFilter(null);


                        }else{

                            ((SquareTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
                            ((SquareTypeViewHolder) holder).image.setColorFilter(filter_bw);
                        }

                        if (object.video.equals("") && object.mp4.equals("")){
                            ((SquareTypeViewHolder) holder).video_hint.setVisibility(View.GONE);

                        }else{
                            ((SquareTypeViewHolder) holder).video_hint.setVisibility(View.VISIBLE);
                        }

                        ((SquareTypeViewHolder) holder).share_btn_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                share_news(object,((SquareTypeViewHolder) holder).share_btn_item);
                            }
                        });
                    }
                    break;



                case News.TEXT_TYPE:
                    final TextTypeViewHolder holdermin = (TextTypeViewHolder) holder;
                    //((TextTypeViewHolder) holder).txtType.setText(object.News);
                    holdermin.ch_name.setText(object.chanels.get_ch_title(mContext));
                    holdermin.news_time.setText(object.get_time(mContext));
                    holdermin.news_time_urgent.setText(object.get_time(mContext));
                    holdermin.news_title.setText(Html.fromHtml(object.title));

                    holdermin.news_title.setTextSize(sharedPreferences.getInt("font_sizee", 20));
                    holdermin.urgent.setText(Session.getword(mContext, "urgent"));
                    load_chanel_image(object.chanels.ch_image,holdermin.ch_logo);

                    holdermin.news_title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holdermin.news_title.setTextColor(news_item_fade_ti);

                            go_to_news_detail(object);
                        }
                    });

                    holdermin.ch_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            go_to_chanel_detail(object);
                        }
                    });
                    holdermin.ch_logo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            go_to_chanel_detail(object);
                        }
                    });

                    if(object.is_urgent.equals("1")){

                        holdermin.is_urgent.setVisibility(View.VISIBLE);
                        holdermin.news_time_urgent.setVisibility(View.GONE);
                        holdermin.news_time.setVisibility(View.VISIBLE);

                    }

                    else{

                        holdermin.is_urgent.setVisibility(View.GONE);
                        holdermin.news_time_urgent.setVisibility(View.VISIBLE);
                        holdermin.news_time.setVisibility(View.GONE);

                    }



                    if(databaseHandler.news_opened(object.id).equals("0")){


                        holdermin.news_title.setTextColor(news_item_ti);


                    }else{

                        holdermin.news_title.setTextColor(news_item_fade_ti);
                    }

                    holdermin.share_btn_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            share_news(object,holdermin.share_btn_item);
                        }
                    });



                    break;

                case News.IMAGE_TYPE:
                  //  ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                   // ((ImageTypeViewHolder) holder).image.setImageResource(object.data);


                    if(no_image){
                        final TextTypeViewHolder holderminno = (TextTypeViewHolder) holder;

                        holderminno.ch_name.setText(object.chanels.get_ch_title(mContext));
                        load_chanel_image(object.chanels.ch_image,holderminno.ch_logo);

                        ((TextTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go_to_chanel_detail(object);
                            }
                        });
                        holderminno.ch_logo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go_to_chanel_detail(object);
                            }
                        });



                        holderminno.news_time.setText(object.get_time(mContext));
                        holderminno.news_time_urgent.setText(object.get_time(mContext));
                        holderminno.news_title.setTextSize(sharedPreferences.getInt("font_sizee", 20));
                        holderminno.urgent.setText(Session.getword(mContext, "urgent"));
                        holderminno.news_title.setText(Html.fromHtml(object.title));

                        holderminno.news_title.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                holderminno.news_title.setTextColor(news_item_fade_ti);
                                go_to_news_detail(object);
                            }
                        });

                        if(object.is_urgent.equals("1")){

                            holderminno.is_urgent.setVisibility(View.VISIBLE);
                            holderminno.news_time_urgent.setVisibility(View.GONE);
                            holderminno.news_time.setVisibility(View.VISIBLE);



                        }

                        else{
                            holderminno.is_urgent.setVisibility(View.GONE);
                            holderminno.news_time_urgent.setVisibility(View.VISIBLE);
                            holderminno.news_time.setVisibility(View.GONE);

                        }


                        if(databaseHandler.news_opened(object.id).equals("0")){


                            holderminno.news_title.setTextColor(news_item_ti);

                        }else{

                            holderminno.news_title.setTextColor(news_item_fade_ti);
                        }


                        holderminno.share_btn_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                share_news(object,holderminno.share_btn_item);

                            }
                        });






                    }else{

                        ((ImageTypeViewHolder) holder).ch_name.setText(object.chanels.get_ch_title(mContext));
                        ((ImageTypeViewHolder) holder).news_time.setText(object.get_time(mContext));
                        ((ImageTypeViewHolder) holder).news_time_urgent.setText(object.get_time(mContext));
                        ((ImageTypeViewHolder) holder).news_title.setTextSize(sharedPreferences.getInt("font_sizee", 20));
                        ((ImageTypeViewHolder) holder).urgent.setText(Session.getword(mContext, "urgent"));

                        ((ImageTypeViewHolder) holder).news_title.setText(Html.fromHtml(object.title));
                        load_chanel_image(object.chanels.ch_image,((ImageTypeViewHolder) holder).ch_logo);

                        load_news_image(object.image,((ImageTypeViewHolder) holder).image);


                        ((ImageTypeViewHolder) holder).news_title.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
                                ((ImageTypeViewHolder) holder).image.setColorFilter(filter_bw);
                                go_to_news_detail(object);
                            }
                        });

                        ((ImageTypeViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
                                ((ImageTypeViewHolder) holder).image.setColorFilter(filter_bw);
                                go_to_news_detail(object);
                            }
                        });
                        ((ImageTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go_to_chanel_detail(object);
                            }
                        });
                        ((ImageTypeViewHolder) holder).ch_logo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                go_to_chanel_detail(object);
                            }
                        });

                        if(object.is_urgent.equals("1")){
                            ((ImageTypeViewHolder) holder).is_urgent.setVisibility(View.VISIBLE);
                            ((ImageTypeViewHolder) holder).news_time_urgent.setVisibility(View.GONE);
                            ((ImageTypeViewHolder) holder).news_time.setVisibility(View.VISIBLE);
                        }

                        else{
                            ((ImageTypeViewHolder) holder).is_urgent.setVisibility(View.GONE);
                            ((ImageTypeViewHolder) holder).news_time_urgent.setVisibility(View.VISIBLE);
                            ((ImageTypeViewHolder) holder).news_time.setVisibility(View.GONE);
                        }


                        if(databaseHandler.news_opened(object.id).equals("0")){

                            ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_ti);
                            ((ImageTypeViewHolder) holder).image.setColorFilter(null);


                        }else{

                            ((ImageTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
                            ((ImageTypeViewHolder) holder).image.setColorFilter(filter_bw);
                        }

                        if (object.video.equals("") && object.mp4.equals("")){
                            ((ImageTypeViewHolder) holder).video_hint.setVisibility(View.GONE);

                        }else{
                            ((ImageTypeViewHolder) holder).video_hint.setVisibility(View.VISIBLE);
                        }

                        ((ImageTypeViewHolder) holder).share_btn_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                share_news(object,((ImageTypeViewHolder) holder).share_btn_item);
                            }
                        });
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
//                    mPublisherAdView.setAdSizes(new AdSize(300, 250));
//                    mPublisherAdView.setAdUnitId(object.title);
//                    PublisherAdRequest adRequest = new PublisherAdRequest.Builder().
//                            // addTestDevice("6CF13E43F2584625AF6152F65DAC084E").
//                            //addTestDevice("DF05CE517F21FBE0F3D2BC342BBEBCD9").
//                            //addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR).
//                                    build();
//                    ((AudioTypeViewHolder) holder).mPublisherAdView.addView(mPublisherAdView);
//                    mPublisherAdView.loadAd(adRequest);
//                    mPublisherAdView.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdLoaded() {
//                            // Code to be executed when an ad finishes loading.
//                            Session.set_ad_id(mContext,object.title);
//                        }
//
//                        @Override
//                        public void onAdFailedToLoad(int errorCode) {
//                            // Code to be executed when an ad request fails.
//                        }
//
//                        @Override
//                        public void onAdOpened() {
//                            // Code to be executed when an ad opens an overlay that
//                            // covers the screen.
//                        }
//
//                        @Override
//                        public void onAdLeftApplication() {
//                            // Code to be executed when the user has left the app.
//                        }
//
//                        @Override
//                        public void onAdClosed() {
//                            // Code to be executed when when the user is about to return
//                            // to the app after tapping on an ad.
//                        }
//                    });





                    break;

                case News.FB_TYPE:
//                {
//                    ((FBTypeViewHolder) holder).nativeAd = new NativeAd(mContext, "411516915893862_411529395892614");
//
//                    ((FBTypeViewHolder) holder).nativeAd.setAdListener(new AdListener() {
//
//                        @Override
//                        public void onError(Ad ad, AdError error) {
//                            Log.e("error",error.getErrorMessage());
//                        }
//
//                        @Override
//                        public void onAdLoaded(Ad ad) {
//                            if (ad != ((FBTypeViewHolder) holder).nativeAd) {
//                                return;
//                            }
//
//                            String titleForAd = ((FBTypeViewHolder) holder).nativeAd.getAdTitle();
//                            NativeAd.Image coverImage = ((FBTypeViewHolder) holder).nativeAd.getAdCoverImage();
//                            NativeAd.Image iconForAd = ((FBTypeViewHolder) holder).nativeAd.getAdIcon();
//                            String socialContextForAd = ((FBTypeViewHolder) holder).nativeAd.getAdSocialContext();
//                            String titleForAdButton = ((FBTypeViewHolder) holder).nativeAd.getAdCallToAction();
//                            String textForAdBody = ((FBTypeViewHolder) holder).nativeAd.getAdBody();
//                            NativeAd.Rating appRatingForAd =((FBTypeViewHolder) holder).nativeAd.getAdStarRating();
//
//                            Picasso.with(mContext).load(coverImage.getUrl()).fit().into(((FBTypeViewHolder) holder).image);
//
//                            // Add code here to create a custom view that uses the ad properties
//                            // For example:
//                        }
//
//                        @Override
//                        public void onAdClicked(Ad ad) {
//                            Log.e("click",ad.getPlacementId());
//
//                        }//
//
//                        @Override
//                        public void onLoggingImpression(Ad ad) {
//                            Log.e("log",ad.getPlacementId());
//                        }
//                    });
//
//                    AdSettings.addTestDevice("3c0ca8829ade95ae56789713886bb388");
//
//
//
//                    ((FBTypeViewHolder) holder).nativeAd.loadAd();


//                    ((FBTypeViewHolder) holder).ch_name.setText(object.chanels.get_ch_title(mContext));
//                    ((FBTypeViewHolder) holder).news_time.setText(object.get_time(mContext));
//                    ((FBTypeViewHolder) holder).news_time_urgent.setText(object.get_time(mContext));
//                    ((FBTypeViewHolder) holder).news_title.setTextSize(sharedPreferences.getInt("font_sizee", 20));
//                    ((FBTypeViewHolder) holder).urgent.setText(Session.getword(mContext, "urgent"));
//
//                    ((FBTypeViewHolder) holder).news_title.setText(Html.fromHtml(object.title));
//                    load_chanel_image(object.chanels.ch_image,((FBTypeViewHolder) holder).ch_logo);
//                    load_news_image(object.image,((FBTypeViewHolder) holder).image);
//
//
//                    ((FBTypeViewHolder) holder).news_title.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ((FBTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
//                            ((FBTypeViewHolder) holder).image.setColorFilter(filter_bw);
//                            go_to_news_detail(object);
//                        }
//                    });
//
//                    ((FBTypeViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ((FBTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
//                            ((FBTypeViewHolder) holder).image.setColorFilter(filter_bw);
//
//                            go_to_news_detail(object);
//                        }
//                    });
//                    ((FBTypeViewHolder) holder).ch_name.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            go_to_chanel_detail(object);
//                        }
//                    });
//                    ((FBTypeViewHolder) holder).ch_logo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            go_to_chanel_detail(object);
//                        }
//                    });
//
//                    if(object.is_urgent.equals("1")){
//                        ((FBTypeViewHolder) holder).is_urgent.setVisibility(View.VISIBLE);
//                        ((FBTypeViewHolder) holder).news_time_urgent.setVisibility(View.GONE);
//                        ((FBTypeViewHolder) holder).news_time.setVisibility(View.VISIBLE);
//                    }
//
//                    else{
//                        ((FBTypeViewHolder) holder).is_urgent.setVisibility(View.GONE);
//                        ((FBTypeViewHolder) holder).news_time_urgent.setVisibility(View.VISIBLE);
//                        ((FBTypeViewHolder) holder).news_time.setVisibility(View.GONE);
//                    }
//
//
//                    if(databaseHandler.news_opened(object.id).equals("0")){
//
//                        ((FBTypeViewHolder) holder).news_title.setTextColor(news_item_ti);
//                        ((FBTypeViewHolder) holder).image.setColorFilter(null);
//
//
//                    }else{
//
//                        ((FBTypeViewHolder) holder).news_title.setTextColor(news_item_fade_ti);
//                        ((FBTypeViewHolder) holder).image.setColorFilter(filter_bw);
//                    }
//
//                    if (object.video.equals("") && object.mp4.equals("")){
//                        ((FBTypeViewHolder) holder).video_hint.setVisibility(View.GONE);
//
//                    }else{
//                        ((FBTypeViewHolder) holder).video_hint.setVisibility(View.VISIBLE);
//                    }
//
//                    ((FBTypeViewHolder) holder).share_btn_item.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            share_news(object.whatsapp_str,((FBTypeViewHolder) holder).share_btn_item);
//                        }
//                    });
                }


//                    break;







//            }
//        }else {
//
//
//            ((FooterTypeViewHolder) holder).txtType.setText(Session.getword(mContext, "end_of_news"));
//
//            if (end_of_news) {
//                ((FooterTypeViewHolder) holder).txtType.setVisibility(View.VISIBLE);
//                ((FooterTypeViewHolder) holder).progressBar.setVisibility(View.GONE);
//
//            } else {
//                ((FooterTypeViewHolder) holder).txtType.setVisibility(View.GONE);
//                ((FooterTypeViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
//            }
//
//






        }




    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private PopupWindow mPopupWindow;


    private void show_share(View view){

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.tooltip_textview,null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAsDropDown(view,-50,0);

      //  mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);


    }

}
