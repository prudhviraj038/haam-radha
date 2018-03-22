package com.mamacgroup.hamtest;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class VideoChanel implements Serializable {
        String ch_id,parent_id,ch_title,ch_title_ar,ch_title_fr,ch_image,cover_image,count,status;
        boolean like = false;


        VideoChanel(JSONObject jsonObject1, String t){


            try {
                if(t.equals("4")) {
                    ch_id =    jsonObject1.getString("id");
                    ch_title = jsonObject1.getString("title");
                    ch_image = jsonObject1.getString("image");
                }

                else {
                    ch_id = jsonObject1.getString("id");
                    like = AppController.getInstance().getDatabaseHandler().is_following(ch_id);
                    if (!t.equals("1")) {
                        ch_title = jsonObject1.getString("title");
                        ch_title_ar = jsonObject1.getString("title");
                        ch_title_fr = jsonObject1.getString("title");
                    } else {
                        ch_title = decode_base64(jsonObject1.getString("title"));
                        ch_title_ar = decode_base64(jsonObject1.getString("title"));
                        ch_title_fr = decode_base64(jsonObject1.getString("title"));
                    }
                    ch_image = jsonObject1.getString("image");
                    cover_image = jsonObject1.getString("image");
                    count = "0";
                    status = "0";
                    parent_id = "0";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    byte[] data64;
    String temp;
    public String get_ch_title(Context context) {
        if (Session.get_user_language(context).equals("ar"))
            return ch_title_ar;
        else if (Session.get_user_language(context).equals("fr"))
            return ch_title_fr;
        else
            return ch_title;
    }


    private  String decode_base64(String encodedstring) {
        // temp = encodedstring;

//        try {
//            data64 = Base64.decode(encodedstring, Base64.DEFAULT);
//            temp = new String(data64, "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//            if (temp != null)
//                return temp;
//
        return encodedstring;

    }



}