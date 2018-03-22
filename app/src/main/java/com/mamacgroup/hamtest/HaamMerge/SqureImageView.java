package com.mamacgroup.hamtest.HaamMerge;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SqureImageView extends ImageView {

    public SqureImageView(Context context) {
        super(context);
    }

    public SqureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = (int) (width*1.8);
        setMeasuredDimension(width,height);
    }

}