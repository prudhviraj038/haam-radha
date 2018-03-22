package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedChannelsAdapter extends BaseAdapter implements Filterable {
    Context context;

    ArrayList<Chanel> chanels;
    DatabaseHandler db;
    ArrayList<Chanel> chanels_all;
    PlanetFilter planetFilter;

    private static LayoutInflater inflater=null;
    public SelectedChannelsAdapter(Activity mainActivity,  ArrayList<Chanel> chanels) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.chanels = chanels;
        chanels_all = chanels;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHandler(context);

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

    public class Holder
    {
        TextView tv;
        MyTextView follow_btn;
        ImageView img;
        RelativeLayout follow_btn_click;
        ImageView follow_btn_image;
    }

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.sub_category_item_select,null);
        holder.tv=(TextView) rowView.findViewById(R.id.cat_name);
        holder.tv.setText(Html.fromHtml(chanels.get(position).get_ch_title(context)));
        holder.img=(ImageView) rowView.findViewById(R.id.cat_img);
        Picasso.with(context).load(chanels.get(position).ch_image).into(holder.img);
        holder.follow_btn_click = (RelativeLayout) rowView.findViewById(R.id.follow_btn_click);
        holder.follow_btn_image = (ImageView) rowView.findViewById(R.id.follow_btn_image);
        holder.follow_btn=(MyTextView) rowView.findViewById(R.id.follow_btn);
        holder.follow_btn.setText(Html.fromHtml(db.is_following(chanels.get(position).ch_id) ? Session.getword(context,"unfollow") : Session.getword(context,"follow")));

        if(db.is_following(chanels.get(position).ch_id)) {
            holder.follow_btn.setBackgroundResource(R.drawable.border_full_for_add);
            holder.follow_btn.setTextColor(Color.parseColor("white"));
            holder.follow_btn.setVisibility(View.INVISIBLE);
            holder.follow_btn_image.setVisibility(View.VISIBLE);
        }
        else {
            holder.follow_btn.setBackgroundResource(R.drawable.border_empty_for_add);
            holder.follow_btn.setTextColor(context.getResources().getColor(R.color.aa_app_blue));
            holder.follow_btn.setVisibility(View.VISIBLE);
            holder.follow_btn_image.setVisibility(View.INVISIBLE);

        }

        holder.follow_btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.is_following(chanels.get(position).ch_id)) {
                    db.deletePlaylist(chanels.get(position).ch_id);
                    add_ch("delete", chanels.get(position).ch_id);
                    chanels.remove(position);
                    notifyDataSetChanged();
                } else {

                    holder.follow_btn.setText(Html.fromHtml(db.is_following(chanels.get(position).ch_id) ? Session.getword(context,"unfollow") : Session.getword(context,"follow")));
                    if(db.is_following(chanels.get(position).ch_id)) {
                        holder.follow_btn.setBackgroundResource(R.drawable.border_full_for_add);
                        holder.follow_btn.setTextColor(Color.parseColor("white"));
                        holder.follow_btn.setVisibility(View.INVISIBLE);
                        holder.follow_btn_image.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.follow_btn.setBackgroundResource(R.drawable.border_empty_for_add);
                        holder.follow_btn.setTextColor(context.getResources().getColor(R.color.aa_app_blue));
                        holder.follow_btn.setVisibility(View.VISIBLE);
                        holder.follow_btn_image.setVisibility(View.INVISIBLE);
                    }

                }
//                Session.sendRegistrationToServer(context);
                Session.sendChannelsToServer(context,db.all_selected_channels_new("0"));
            }
        });

        return rowView;
    }


    public  void add_ch(String type,String id){
        String url = null;
        if(type.equals("add") )
            url = Session.NOTIFY_SERVER_URL+"add-channel2.php?member_id="+Session.get_user_id(context)+ "&channel_id="+id;
        else
            url = Session.NOTIFY_SERVER_URL+"remove-channel2.php?member_id="+Session.get_user_id(context)+ "&channel_id="+id;
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage(Session.getword(context, "please_wait"));
////        progressDialog.setMessage(Settings.getword(this, "please_wait"));
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
//                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(context, Session.getword(context,"server_not_connected"), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

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