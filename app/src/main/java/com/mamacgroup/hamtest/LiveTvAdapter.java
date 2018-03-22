package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

public class LiveTvAdapter extends RecyclerView.Adapter<LiveTvAdapter.SimpleViewHolder>{
    Context context;
    LiveChannels categoriess;
    String user_language = "en";
    private static LayoutInflater inflater=null;
    public LiveTvAdapter(Activity mainActivity, LiveChannels categories) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.categoriess=categories;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        user_language = Session.get_user_language(context);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    @Override
    public LiveTvAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LiveTvAdapter.SimpleViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder
    {
        com.mamacgroup.hamtest.MyTextView tv;
        ImageView img;

    }


//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//        Holder holder=new Holder();
//        View rowView;
//        if(convertView==null){
//            rowView = inflater.inflate(R.layout.livetv_item, null);
//        }else{
//            rowView= convertView;
//        }
//
//        holder.tv=(com.mamacgroup.hamtest.MyTextView) rowView.findViewById(R.id.cat_name);
////        if(user_language.equals("fr"))
////         holder.tv.setText(categoriess.get(position).title_fr);
////        else  if(user_language.equals("ar"))
////            holder.tv.setText(categoriess.get(position).title_ar);
////        else
////            holder.tv.setText(categoriess.get(position).title);
//        holder.tv.setText(categoriess.chanels.get(position).get_ch_title(context));
//        holder.img=(ImageView) rowView.findViewById(R.id.cat_img);
//        Glide.with(context).load(categoriess.chanels.get(position).ch_image).into(holder.img);
//        return rowView;
//    }
//



}