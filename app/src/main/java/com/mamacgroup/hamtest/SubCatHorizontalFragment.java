package com.mamacgroup.hamtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 3/8/17.
 */

public class SubCatHorizontalFragment extends Fragment implements UpdatableFragment {
    @Override
    public void update() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sub_category_item, container, false);

        return view;
    }

}
