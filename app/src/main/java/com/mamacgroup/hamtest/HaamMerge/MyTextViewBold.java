package com.mamacgroup.hamtest.HaamMerge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyTextViewBold extends android.widget.TextView {

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        appearance();
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextViewBold(Context context) {
        super(context);
        init(context);
    }
    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            super.draw(canvas);
        }
    }
    public void  appearance(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
//        this.setTextSize(getResources().getDimension(R.dimen.material_micro_text_size));
        this.setLineSpacing(-10,1);
//        this.setBackgroundColor(Color.parseColor("#99000000"));
//        this.setGravity(Gravity.CENTER_VERTICAL);
//        this.setTextColor(getResources().getColor(R.color.white));
//        this.setText("Not Started");
    }
    private void init(Context context) {
        if (!isInEditMode()) {

                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/geeza_pro_bold.ttf");
                setTypeface(tf);
            }

    }


}