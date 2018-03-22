package com.mamacgroup.hamtest.HaamMerge;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 4/5/17.
 */
public class News implements Serializable {
     public String id, title, title_ar, image, cat_id, cat_title, cat_title_ar, cat_image, ch_id, ch_title, ch_title_ar, ch_image, ch_color, video_script,
            small_video, large_video, m3u8_url, external_link, description, time, share_str,ch_image_white,last_id="0",about;
    ArrayList<String> images;
    public News(JSONObject jsonObject) {
        images=new ArrayList<>();
        try {
            Log.e("json_cat",jsonObject.toString());
            id = jsonObject.getString("id");
            title = jsonObject.getString("title");
            title_ar = jsonObject.getString("title_ar");
            cat_id = jsonObject.getJSONObject("category").getString("id");
            cat_title = jsonObject.getJSONObject("category").getString("title");
            cat_title_ar = jsonObject.getJSONObject("category").getString("title_ar");
            cat_image = jsonObject.getJSONObject("category").getString("image");
            ch_id = jsonObject.getJSONObject("channel").getString("id");
            ch_title = jsonObject.getJSONObject("channel").getString("title");
            ch_title_ar = jsonObject.getJSONObject("channel").getString("title_ar");
            ch_image = jsonObject.getJSONObject("channel").getString("image");
            ch_image_white = jsonObject.getJSONObject("channel").getString("image_white");
            ch_color = jsonObject.getJSONObject("channel").getString("color");
            image = jsonObject.getString("image");
            video_script = jsonObject.getString("video_script");
            small_video = jsonObject.getString("small_video");
            small_video = small_video.replace("start.php?f=", "");
            large_video = jsonObject.getString("large_video");
            m3u8_url = jsonObject.getString("m3u8_url");
            external_link = jsonObject.getString("external_link");
            description = jsonObject.getString("description");
            about = jsonObject.getString("about");
            time = jsonObject.getString("time");
            if (jsonObject.has("share_str")) {
                share_str = jsonObject.getString("share_str");
            } else {
                share_str = small_video;
            }
            for(int i=0;i<jsonObject.getJSONArray("images").length();i++){
                this.images.add(jsonObject.getJSONArray("images").get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    public String getTitle(Context context) {
//        if(Settings.get_user_language(context).equals("ar"))
//            return title_ar;
//        else
//            return  title;
//    }
//    public class Images implements Serializable {
//        String image;
//        Images(String jsonObject) {
//          image=jsonObject;
//
//        }
//    }
}
