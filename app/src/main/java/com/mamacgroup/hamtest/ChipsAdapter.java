package com.mamacgroup.hamtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 3/8/17.
 */

public class ChipsAdapter extends RecyclerView.Adapter implements Filterable {

    private ArrayList<Chanel> chipsArray;
    private ArrayList<Chanel> chipsArray_all;
    private Context context;
    PlanetFilter planetFilter;

    public ChipsAdapter(ArrayList<Chanel> chipsArray, Context context) {
        this.chipsArray = chipsArray;
        this.chipsArray_all = chipsArray;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipViewHolder(new ChipView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ChipView)holder.itemView).displayItem(chipsArray.get(position).ch_title);
        ((ChipView)holder.itemView).setImage(chipsArray.get(position).ch_image,context);
        ((ChipView)holder.itemView).setLike(chipsArray.get(position).like,context);

        ((ChipView)holder.itemView).get_like_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chipsArray.get(position).like)
                    AppController.getInstance().databaseHandler.deletePlaylist(chipsArray.get(position).ch_id);
                else
                    AppController.getInstance().databaseHandler.addPlaylist(chipsArray.get(position).ch_id,chipsArray.get(position).parent_id,"1");

                chipsArray.get(position).like = !chipsArray.get(position).like;
                ((ChipView)holder.itemView).setLike(chipsArray.get(position).like,context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chipsArray.size();
    }

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;

    }

    private class ChipViewHolder extends RecyclerView.ViewHolder {

        ImageView like_btn;

        public ChipViewHolder(View itemView) {
            super(itemView);

        }
    }

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
                results.values = chipsArray_all;
                results.count = chipsArray_all.size();
            }
            else {
// We perform filtering operation
                List<Chanel> nPlanetList = new ArrayList<>();

                    for(Chanel c : chipsArray_all)
                    {
                        if (c.ch_title.contains(String.valueOf(constraint))){
                            Log.e("title",c.ch_title);
                            nPlanetList.add(c);
                        }

                    }
                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }


            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,Filter.FilterResults results) {
            if (results.count == 0) {
               // chipsArray = (ArrayList<Chanel>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){

                chipsArray = chipsArray_all;
                notifyDataSetChanged();
            }
            else {
                chipsArray = (ArrayList<Chanel>) results.values;
                notifyDataSetChanged();
            }
        }

    }

}