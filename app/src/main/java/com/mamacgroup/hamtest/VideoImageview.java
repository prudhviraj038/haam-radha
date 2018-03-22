package com.mamacgroup.hamtest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Chinni on 15-10-2015.
 */
public class VideoImageview extends android.support.v7.widget.AppCompatImageView {

    public VideoImageview(Context context) {
        super(context);
    }

    public VideoImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoImageview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override protected void onMeasure(int widthMeasureSpec,
                                       int heightMeasureSpec) {
//   let the default measuring occur, then force the desired aspect ratio
//   on the view (not the drawable).
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
//force a 4:3 aspect ratio
        long height = Math.round(width*0.55);
        setMeasuredDimension(width, (int)height);
    }
}
