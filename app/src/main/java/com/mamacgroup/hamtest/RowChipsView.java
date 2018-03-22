package com.mamacgroup.hamtest;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mac on 3/8/17.
 */

public class RowChipsView extends FrameLayout {

    public RowChipsView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.single_row, this);
        ((RecyclerView)findViewById(R.id.recyclerViewHorizontal)).setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ((RecyclerView)findViewById(R.id.recyclerViewHorizontal)).setHasFixedSize(true);
    }

    public void setAdapter(ChipsAdapter adapter) {
        ((RecyclerView)findViewById(R.id.recyclerViewHorizontal)).setAdapter(adapter);
    }
    public void setName(String name) {
        ((TextView)findViewById(R.id.cat_name)).setText(name);
    }

}