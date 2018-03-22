package com.mamacgroup.hamtest;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chinni on 30-07-2016.
 */
public class LiveChannels implements Serializable {
    String id,title,title_ar,title_fr,image,link,youtube;
    ArrayList<Chanel> chanels;
    LiveChannels(JSONObject jsonObject){
        chanels=new ArrayList<>();
        try {
            id=jsonObject.getString("id");
            title=jsonObject.getString("title");
            title_ar=jsonObject.getString("title_ar");
            title_fr=jsonObject.getString("title_fr");
            image=jsonObject.getString("image");




          for(int i=0;i<jsonObject.getJSONArray("tvs").length();i++){
                JSONObject jsonObject2 =jsonObject.getJSONArray("tvs").getJSONObject(i);
                Chanel chanel=new Chanel(jsonObject2);
                chanels.add(chanel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public String get_title(Context context) {
        if (Session.get_user_language(context).equals("ar"))
            return title_ar;
        else if (Session.get_user_language(context).equals("fr"))
            return title_fr;
        else
            return title;
    }
    public class Chanel implements Serializable{
        String ch_id,ch_title,ch_title_ar,ch_title_fr,ch_image,link,external_link,youtube,add_url;
        Chanel(JSONObject jsonObject1){
            try {
                ch_id=jsonObject1.getString("id");
                ch_title=jsonObject1.getString("title");
                ch_title_ar=jsonObject1.getString("title_ar");
                ch_title_fr=jsonObject1.getString("title_fr");
                ch_image=jsonObject1.getString("image");
                link=jsonObject1.getString("link");
                external_link=jsonObject1.getString("external_link");
                youtube=jsonObject1.getString("youtube");
                try {
                    add_url = jsonObject1.getString("ad_url");
                }catch (Exception ex){
                    add_url = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator=";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public String get_ch_title(Context context) {
            try {
                if (Session.get_user_language(context).equals("ar"))
                    return ch_title_ar;
                else if (Session.get_user_language(context).equals("fr"))
                    return ch_title_fr;
                else
                    return ch_title;
            }catch (Exception ex){
                return  ch_title;
            }
        }
    }
}
