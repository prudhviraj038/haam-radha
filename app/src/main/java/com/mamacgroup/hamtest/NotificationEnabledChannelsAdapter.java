package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NotificationEnabledChannelsAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Chanel> chanels;
    ArrayList<Chanel> chanels_all;
    PlanetFilter planetFilter;
    DatabaseHandler db;
    String user_lan;
    NewsRecycleListActivity mCallback;
    private static LayoutInflater inflater=null;

    public NotificationEnabledChannelsAdapter(Activity mainActivity, ArrayList<Chanel> chanels,MainActivity activity) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.chanels = chanels;
        chanels_all = chanels;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHandler(context);
        user_lan=Session.get_user_language(context);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
       // this.mCallback=activity;
    }

    public NotificationEnabledChannelsAdapter(Activity mainActivity, ArrayList<Chanel> chanels,NewsRecycleListActivity activity) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.chanels = chanels;
        chanels_all = chanels;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHandler(context);
        user_lan=Session.get_user_language(context);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        this.mCallback=activity;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chanels.size();

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

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;

    }

    public class Holder
    {
        MyTextView tv;
        MyTextView follow_btn;
        ImageView img;
        RelativeLayout follow_btn_click;
        ImageView follow_btn_image;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        if(convertView == null)
            convertView = inflater.inflate(R.layout.sub_category_item_notify, null);

        holder.tv=(MyTextView) convertView.findViewById(R.id.cat_name);
        if(user_lan.equals("fr"))
        holder.tv.setText(Html.fromHtml(chanels.get(position).ch_title_fr));
       else if(user_lan.equals("ar"))
            holder.tv.setText(Html.fromHtml(chanels.get(position).ch_title_ar));
        else
            holder.tv.setText(Html.fromHtml(chanels.get(position).ch_title));

        holder.img=(ImageView) convertView.findViewById(R.id.cat_img);
        Picasso.with(context).load(chanels.get(position).ch_image).placeholder(R.drawable.loading).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.chanel_selected(chanels.get(position));            }
        });
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.chanel_selected(chanels.get(position));

        }
        });

        holder.follow_btn=(MyTextView) convertView.findViewById(R.id.follow_btn);
        holder.follow_btn_click = (RelativeLayout) convertView.findViewById(R.id.follow_btn_click);
        holder.follow_btn_image = (ImageView) convertView.findViewById(R.id.follow_btn_image);
        holder.follow_btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.is_notification_enabled(chanels.get(position).ch_id)) {

                    db.updatenotify(chanels.get(position).ch_id,"0");

                } else {

                    db.updatenotify(chanels.get(position).ch_id,"1");


                }

                holder.follow_btn.setText(Html.fromHtml(db.is_notification_enabled(chanels.get(position).ch_id) ? Session.getword(context,"unfollow") : Session.getword(context,"follow")));

                if(db.is_notification_enabled(chanels.get(position).ch_id)) {
                    holder.follow_btn_image.setImageResource(R.drawable.notify_sources_on);


                }
                else {

                    holder.follow_btn_image.setImageResource(R.drawable.notify_sources_off);

                    // holder.follow_btn_click.performClick();

                }

//                Session.sendRegistrationToServer(context);

            }

        });

        holder.follow_btn.setText(Html.fromHtml(db.is_notification_enabled(chanels.get(position).ch_id) ? Session.getword(context,"unfollow") : Session.getword(context,"follow")));

        if(db.is_notification_enabled(chanels.get(position).ch_id)) {
            holder.follow_btn_image.setImageResource(R.drawable.notify_sources_on);


        }
        else {

            holder.follow_btn_image.setImageResource(R.drawable.notify_sources_off);

           // holder.follow_btn_click.performClick();

        }

        return convertView;



    }

//    private FirebaseAnalytics mFirebaseAnalytics;

    private class PlanetFilter extends Filter {
        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            clear_all=false;
            if (constraint == null || constraint.length() == 0) {
                clear_all=true;
// No filter implemented we return all the list
                results.values = chanels;
                results.count = chanels.size();
            }
            else {
// We perform filtering operation
                List<Chanel> nPlanetList = new ArrayList<>();

                for (Chanel p : chanels_all) {


                    if (p.get_ch_title(context).contains(String.valueOf(constraint)))
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            if (results.count == 0) {
//                restaurants = (ArrayList<Restaurants>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){
                chanels=chanels_all;
                notifyDataSetChanged();
            }
            else {
                chanels = (ArrayList<Chanel>) results.values;
                notifyDataSetChanged();
            }
        }

    }



}