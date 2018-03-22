package com.mamacgroup.hamtest;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyTextView1 extends android.widget.TextView {

    public MyTextView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        appearance();

    }

    public MyTextView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView1(Context context) {
        super(context);
        init(context);
    }
    public void  appearance(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
//        this.setTextSize(getResources().getDimension(R.dimen.material_micro_text_size));
        this.setLineSpacing(-10,1);
//        this.setGravity(Gravity.CENTER_VERTICAL);
//        this.setTextColor(getResources().getColor(R.color.white));
//        this.setText("Not Started");
    }

    private void init(Context context) {
        if (!isInEditMode()) {

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTArabic-Roman.ttf");
                setTypeface(tf);



            }

    }


}