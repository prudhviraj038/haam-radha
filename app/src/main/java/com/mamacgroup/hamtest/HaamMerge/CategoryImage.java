package com.mamacgroup.hamtest.HaamMerge;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 4/5/17.
 */
public class CategoryImage implements Serializable {
    String id,image;

    CategoryImage(String cat_id,String url){

       this.id=cat_id;
        this.image = url;

    }
//    public String getTitle(Context context) {
//        if(Settings.get_user_language(context).equals("ar"))
//            return title_ar;
//        else
//            return  title;
//    }
}
