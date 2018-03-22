package com.mamacgroup.hamtest;

/**
 * Created by mac on 3/14/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class CustomRecylerView extends RecyclerView {

    Context context;

    public CustomRecylerView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomRecylerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecylerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
     //   velocityY *= 2;
        return super.fling(velocityX, velocityY);
    }
}