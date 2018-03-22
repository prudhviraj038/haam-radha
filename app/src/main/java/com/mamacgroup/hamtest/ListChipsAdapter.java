package com.mamacgroup.hamtest;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 3/8/17.
 */

public class ListChipsAdapter extends RecyclerView.Adapter  implements Filterable{

    private ArrayList<Categories> chipsArray;
    private ArrayList<Categories> chipsArray_all;
    private Context context;
   PlanetFilter planetFilter;
    HashMap<Integer,ChipsAdapter> adapters;

    public ListChipsAdapter(ArrayList<Categories> chipsArray,Context context) {
        this.chipsArray = chipsArray;
        this.chipsArray_all = this.chipsArray;
        this.context = context;
        adapters = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipsViewHolder(new RowChipsView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapters.put(position,new ChipsAdapter(chipsArray.get(position).chanels,context));

        ((RowChipsView)holder.itemView).setAdapter(adapters.get(position));
        ((RowChipsView)holder.itemView).setName(chipsArray.get(position).title);
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

    private class ChipsViewHolder extends RecyclerView.ViewHolder {

        public ChipsViewHolder(View itemView) {
            super(itemView);
        }
    }



    private class PlanetFilter extends Filter {

        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
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
                List<Categories> nPlanetList = new ArrayList<>();

                for ( int i=0;i<chipsArray_all.size();i++) {

                      //  p.chanels.clear();
                    Categories p = chipsArray_all.get(i);
                        int flag = 0;
                        for(Chanel c : p.chanels_all)
                        {
                    if (c.ch_title.contains(String.valueOf(constraint))){
                      //  Log.e("title",c.ch_title);
                        flag++;
                    }
                        }
                    if(flag>0) {
                        nPlanetList.add(p);
                        final int finalI = i;
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                // Code here will run in UI thread
//                                adapters.get(finalI).getFilter().filter(constraint);
                            }
                        });

                    }
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

                chipsArray = chipsArray_all;
                notifyDataSetChanged();
            }
            else {
                chipsArray = (ArrayList<Categories>) results.values;
                notifyDataSetChanged();
            }
        }

    }


}