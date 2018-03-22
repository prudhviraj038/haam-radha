package com.mamacgroup.hamtest;

/**
 * Created by mac on 2/14/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mamacgroup.hamtest.HaamMerge.Category;
import com.mamacgroup.hamtest.HaamMerge.CategoryActivity;
import com.mamacgroup.hamtest.HaamMerge.Settings;
import com.mamacgroup.hamtest.HaamMerge.VideoPlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HaamAdapter extends RecyclerView.Adapter<HaamAdapter.MyViewHolder> {

    private List<String> moviesList;
    private List<String> moviesList_names;
    private List<String> moviesList_images;
    ArrayList<Category> categories;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView title, year, genre;
        public TextView ch_title;


        public MyViewHolder(View view) {
            super(view);
            title = (ImageView) view.findViewById(R.id.haam_image);
            ch_title = (TextView) view.findViewById(R.id.haam_adap_text);

                    }
    }


    public HaamAdapter(Context context, List<String> moviesList, List<String> moviesList_images, List<String> moviesList_names,
                       ArrayList<Category> categories) {

        this.moviesList = moviesList;
        this.moviesList_names = moviesList_names;
        this.moviesList_images = moviesList_images;
        this.categories = categories;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.haam_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // Movie movie = moviesList.get(position);
        //holder.title.setText(movie.getTitle());
        //holder.genre.setText(movie.getGenre());
        //holder.year.setText(movie.getYear());

//        Picasso.with(context).load(moviesList_images.get(position)).into(holder.title);
        Log.e("cat_data",categories.get(position).all_viewed);
        if(categories.get(position).all_viewed.equals("1")){
            holder.title.setImageResource(R.drawable.fill_circle);
        }else{
            holder.title.setImageResource(R.drawable.lined_circle);
        }
        holder.ch_title.setText(moviesList_names.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setWishid(context, "0");
                Intent intent = new Intent(context, VideoPlayerActivity.class);
//                View sharedView = img1;
//                String transitionName = getString(R.string.transition_name_image);
                intent.putExtra("news",categories.get(position).newses);
                intent.putExtra("last",categories.get(position).last_id);
                intent.putExtra("cat_act","cat");
                Log.e("cat_data",categories.get(position).toString());
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CategoryActivity.this, sharedView, transitionName);
//                CategoryActivity.this.startActivity(intent);
                context.startActivity(intent);
    }
});
          }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
