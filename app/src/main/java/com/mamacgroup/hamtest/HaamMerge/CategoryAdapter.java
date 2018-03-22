package com.mamacgroup.hamtest.HaamMerge;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.mamacgroup.hamtest.AppController;
import com.mamacgroup.hamtest.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;
    ArrayList<Category> categories;
    public CategoryAdapter(Activity mainActivity, ArrayList<Category> categories) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = categories;
//        this.clientsListFragment = clientsListFragment;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categories.size();
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
        MyTextView f_title,no_items;
        MyTextViewBold title,des;
        ImageView app,play;
        RelativeLayout v1,v2;
        SqureImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.haam_category_item, null);
        else
            rowView = convertView;
        holder.title=(MyTextViewBold) rowView.findViewById(R.id.cat_title_adp);
        holder.title.setText(categories.get(position).title_ar);
        holder.des=(MyTextViewBold) rowView.findViewById(R.id.cat_title_des);
        holder.img=(SqureImageView) rowView.findViewById(R.id.cat_img_adp);
        Picasso.with(context).load(categories.get(position).image).fit().into(holder.img);
//        Glide.with(context).load(categories.get(position).image).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(context, "0");
                Intent intent = new Intent(context, VideoPlayerActivity.class);
//                View sharedView =holder.img ;
//                String transitionName =context.getString(R.string.transition_name_image);
                intent.putExtra("news",categories.get(position).newses);
                intent.putExtra("last",categories.get(position).last_id);
                intent.putExtra("cat_act","cat");
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, sharedView, transitionName);
//                context.startActivity(intent, transitionActivityOptions.toBundle());
                context.startActivity(intent);
            }
        });
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
        return rowView;
        
    }

}