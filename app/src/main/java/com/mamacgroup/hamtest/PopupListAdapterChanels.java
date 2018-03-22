package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

//import com.bumptech.glide.Glide;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopupListAdapterChanels extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Chanel> categoriess;
    ArrayList<Chanel> categoriess_all;
    PlanetFilter planetFilter;
    private static LayoutInflater inflater=null;
    public PopupListAdapterChanels(Activity mainActivity, ArrayList<Chanel> categories) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.categoriess=categories;
        categoriess_all = categories;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categoriess.size();
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
        com.mamacgroup.hamtest.MyTextView tv;
        CircleImageView img;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.popup_item, null);
        holder.tv=(com.mamacgroup.hamtest.MyTextView) rowView.findViewById(R.id.cat_name);
        holder.tv.setText(categoriess.get(position).get_ch_title(context));
        holder.img=(CircleImageView) rowView.findViewById(R.id.cat_img);
        Picasso.with(context).load(categoriess.get(position).ch_image).into(holder.img);
        return rowView;
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
                results.values = categoriess;
                results.count = categoriess.size();
            }
            else {
// We perform filtering operation
                List<Chanel> nPlanetList = new ArrayList<>();

                for (Chanel p : categoriess_all) {


                    if (p.ch_title.contains(String.valueOf(constraint)) ||
                            p.ch_title_ar.contains(String.valueOf(constraint)))
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
              //  categoriess = (ArrayList<Chanel>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){
                categoriess=categoriess_all;
                notifyDataSetChanged();
            }
            else {
                categoriess = (ArrayList<Chanel>) results.values;
                notifyDataSetChanged();
            }
        }

    }


}