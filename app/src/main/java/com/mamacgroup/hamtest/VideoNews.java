package com.mamacgroup.hamtest;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 30-07-2016.
 */


public class VideoNews implements Parcelable {

    String id, title, image, data,type, link, is_urgent, now, insta_img, gallery_link = "", facebook_str, twitter_str, whatsapp_str, mail_str, video,mp4,m3u8, times,
            time, time_ar, time_fr, times2;
    Chanel chanels;
    ArrayList<String> img;

    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;
    public static final int AUDIO_TYPE=2;
    public static final int FB_TYPE=4;
    public static final int FOOTER_TYPE=3;

    public int view_type;
    Context context;
    private int mData;

//    {
//        "id": "321",
//            "title": "10 Need-To-Know Vegan Hacks",
//            "description": "Get the recipe: https:\/\/tasty.co\/compilation\/vegan-hacks\n\nReserve the One Top: http:\/\/bit.ly\/2v0iast\n\nCheck us out on Facebook! - facebook.com\/buzzfeedtasty\n\nCredits: https:\/\/www.buzzfeed.com\/bfmp\/videos\/46309\n\n\nMUSIC\nLicensed via Audio Network",
//            "chanel": {
//        "id": "5",
//                "title": "Tasty",
//                "image": "http:\/\/news.haamapp.com\/uploads\/yt\/51518603935.jpg"
//    },
//        "video_link": "cQs2fKBQfe8",
//            "default_image": "https:\/\/i.ytimg.com\/vi\/cQs2fKBQfe8\/default.jpg",
//            "medium": "https:\/\/i.ytimg.com\/vi\/cQs2fKBQfe8\/mqdefault.jpg",
//            "high": "https:\/\/i.ytimg.com\/vi\/cQs2fKBQfe8\/hqdefault.jpg",
//            "standard": "https:\/\/i.ytimg.com\/vi\/cQs2fKBQfe8\/sddefault.jpg",
//            "maxres": "https:\/\/i.ytimg.com\/vi\/cQs2fKBQfe8\/maxresdefault.jpg",
//            "time": "\u0645\u0646\u0630 7 \u0633\u0627\u0639\u0627\u062a"
//    }



    VideoNews(JSONObject jsonObject, Context context) {

        this.context = context;
        img=new ArrayList<>();
        image = "";
        title = "";

        try {

            id = jsonObject.getString("id");
            image = decode_base64(jsonObject.getString("high"));
            type = "news";

            if(type.equals("double_add") || type.equals("double_video")) {
                title = jsonObject.getString("title");
                title = title.replace("\\", "");

                view_type = VideoNews.AUDIO_TYPE;

            }else{
                title = decode_base64(jsonObject.getString("title"));
                if(image.equals(""))
                    view_type = VideoNews.TEXT_TYPE;
                    else
                view_type = VideoNews.IMAGE_TYPE;

            }



          //  type = "add";

            link= "";
            link = decode_base64(jsonObject.getString("video_link"));
            JSONObject jsonObject2 = jsonObject.getJSONObject("chanel");
            chanels = new Chanel(jsonObject2,"4");
            video = jsonObject.getString("video_link");
            mp4 = jsonObject.getString("video_link");
            m3u8 = jsonObject.getString("video_link");
            times = jsonObject.getString("time");
            time = jsonObject.getString("time");
            time_ar = jsonObject.getString("time");
            time_fr = jsonObject.getString("time");
            times2 = jsonObject.getString("time");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    byte[] data64;
    String temp;

    private  String decode_base64(String encodedstring){

         return encodedstring;
    }

    public String get_time(Context context) {
        if (Session.get_user_language(context).equals("ar"))
            return time_ar;
        else if (Session.get_user_language(context).equals("fr"))
            return time_fr;
        else
            return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


    }


    public static final Creator<VideoNews> CREATOR
            = new Creator<VideoNews>() {
        public VideoNews createFromParcel(Parcel in) {
            return new VideoNews(in);
        }

        public VideoNews[] newArray(int size) {
            return new VideoNews[size];
        }
    };

    private VideoNews(Parcel in) {
        mData = in.readInt();
    }

//    public class Chanel implements Serializable {
//        String ch_id, ch_title, ch_title_ar, ch_title_fr, ch_image,ch_count,cover_image;
//
//        Chanel(JSONObject jsonObject1) {
//            try {
//                ch_id = jsonObject1.getString("id");
//                ch_title = jsonObject1.getString("title");
//                ch_title_ar = jsonObject1.getString("title_ar");
//                ch_title_fr = jsonObject1.getString("title_fr");
//                ch_image = jsonObject1.getString("image");
//                ch_count = jsonObject1.getString("count");
//                cover_image = jsonObject1.getString("cover_image");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//
//
//        public String get_ch_title(Context context) {
//            if (Session.get_user_language(context).equals("ar"))
//                return ch_title_ar;
//            else if (Session.get_user_language(context).equals("fr"))
//                return ch_title_fr;
//            else
//                return ch_title;
//        }
//    }


}