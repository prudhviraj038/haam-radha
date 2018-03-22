package com.mamacgroup.hamtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.Calendar;

public class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
    Context context;
    String feed_id="0";
    String type="";
    String name="";
    // This fires when a notification is opened by tapping on it.
    public ExampleNotificationOpenedHandler(Context context){
        this.context=context;
    }
    @Override
    public void notificationOpened(OSNotificationOpenResult result) {

        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String customKey;
        Log.e("dataaa",data.toString());
        if (data != null) {
            customKey = data.optString("news_id", null);
            if (data.has("news_id")) {
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
                String url = "app.haam.scheme://parameter0/" + customKey + "/parameter2";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }else if(data.has("feed_id")) {
                feed_id = data.optString("");
                Log.e("feed_idddd", feed_id);
                // Session.set_news_feed_id(this,feed_id);
                Intent intent = new Intent(context, PushNotificationNewsDetaiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("feed_id", feed_id);
//                    intent.putExtra("type", type);
//                    intent.putExtra("name", name);
//                    intent.putExtra("login_check", "0");
                context.startActivity(intent);
            }else{
                Toast.makeText(context,"custom key null", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }else{
            Intent intent = new Intent(context, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("notify","notify");
            context.startActivity(intent);
        }



//        if (data != null) {
//            if (data.has("type")) {
//                type = data.optString("type");
//            } else {
//                type = "";
//            }
//
//
//            if (data.has("name")) {
//
//                name = data.optString("name");
//
//            } else {
//                name = "channel_from_notification";
//            }
//            if (!type.equals("news")) {
//                Intent intent = new Intent(context, SplashActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            } else {
//                feed_id = data.optString("");
//                Log.e("feed_idddd", feed_id);
//                // Session.set_news_feed_id(this,feed_id);
//                Intent intent = new Intent(context, PushNotificationNewsDetaiActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("feed_id", feed_id);
////                    intent.putExtra("type", type);
////                    intent.putExtra("name", name);
////                    intent.putExtra("login_check", "0");
//                context.startActivity(intent);
//            }
//        }
//        }else{
//            Intent intent = new Intent(context, SplashActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra("notify","notify");
//            context.startActivity(intent);
//        }
//
//        if (actionType == OSNotificationAction.ActionType.ActionTaken)
//            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

        // The following can be used to open an Activity of your choice.
        // Replace - getApplicationContext() - with any Android Context.
//         Intent intent = new Intent(context, VideoPlayerActivity.class);
//         intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("news_id","372");
//        context.startActivity(intent);


        // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
        //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */
    }

}