package com.mamacgroup.hamtest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mac on 3/8/17.
 */

public class ChipView extends FrameLayout {

    public ChipView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chip_view, this);
    }

    public void displayItem(String text) {
        ((TextView)findViewById(R.id.chipTextView)).setText(text);
    }
    public void setImage(String name,Context context) {
        Picasso.with(context).load(name).fit().into(((ImageView) findViewById(R.id.chipImageView)));
    }

    public void setLike(boolean like,Context context) {
        if(like)
             ((ImageView) findViewById(R.id.like_icon)).setColorFilter(ContextCompat.getColor(context,R.color.aa_menu_text_selected));
        else
            ((ImageView) findViewById(R.id.like_icon)).setColorFilter(ContextCompat.getColor(context,R.color.aa_menu_text));


    }

    public ImageView get_like_btn(){
        return  ((ImageView) findViewById(R.id.like_icon));
    }

}