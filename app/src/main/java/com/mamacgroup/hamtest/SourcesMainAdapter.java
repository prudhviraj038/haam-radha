package com.mamacgroup.hamtest;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mac on 3/8/17.
 */

public class SourcesMainAdapter extends BaseAdapter {
    Context context;
    ArrayList<Categories> categoriess;
    private static LayoutInflater inflater=null;
    FragmentManager fragmentManager;
    public SourcesMainAdapter(Activity mainActivity, ArrayList<Categories> categories,FragmentManager fragmentManager) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.categoriess=categories;
        this.fragmentManager = fragmentManager;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoriess.size();
    }

    @Override
    public Object getItem(int i) {
        return i;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder
    {
      //  ViewPager viewPager;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=new Holder();
        if(view==null)

        view = inflater.inflate(R.layout.sources_main_adapter_item, null);

       // holder.viewPager = (ViewPager) view.findViewById(R.id.main_cat_pager);

        //MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fragmentManager);

        //holder.viewPager.setAdapter(myPagerAdapter);

        return view;
    }




    private class MyPagerAdapter extends FragmentPagerAdapter {
        private  int NUM_ITEMS = 4;
        HashMap<Integer,UpdatableFragment> fragment;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragment = new HashMap<Integer, UpdatableFragment>();

        }

        // Returns total number of pages
        @Override
        public int getCount() {

            return 10;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

                return new SubCatHorizontalFragment();
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }




        @Override
        public int getItemPosition(Object object) {
            if (object instanceof UpdatableFragment) {
                ((UpdatableFragment) object).update();
            }
            //don't return POSITION_NONE, avoid fragment recreation.
            return super.getItemPosition(object);
        }


        public void update_fragment(int pos){
            fragment.get(pos).update();
        }

        @Override
        public float getPageWidth(int position) {
            return 0.3f;
        }

    }

}
