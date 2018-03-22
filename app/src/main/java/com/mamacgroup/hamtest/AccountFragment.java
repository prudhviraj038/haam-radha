package com.mamacgroup.hamtest;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 8/5/2016.kkkll
 */
public class AccountFragment extends android.support.v4.app.Fragment {
    FragmentTouchListner mCallBack;
    Switch notification_switch,sound_switch,night_switch;
    SharedPreferences sharedPreferences;
    String title,title_ar,title_fr,logo,email,phone,play_store,facebook,twitter,instagram,share_text,privacy_policy="";
    LinearLayout change_language,select_sources_notify,change_fontsize,contact_us_link,my_ll,pop_shade_ll,
            facebook_link,twitter_link,instagram_link,rating_link,add_source_link,report_bug_link,
    whatsup_share,facebook_share,twitter_share,instagram_share,google_share,linkin_share,email_share,sms_share,
    l_ll,s_ll,m_ll,x_ll,login_ll,signup_ll,my_acc_ll,login_signup_ll,logout_ll,logout_main_ll;
    MyTextView l_tv,m_tv,s_tv,x_tv,test_text;
    ImageView l_im,s_im,m_im,x_im;
    ViewFlipper viewFlipper;
    TextView settings_head_tv,push_settings_head_tv,login_tv,signup_tv,text_tv,my_accc_tv,logout_tv,logout_head,logout_pop_tv,choose_option,
            cancel_pop_tv,user_name,pp_tv;
    CircleImageView user_img;
    int news_item_fade_ti,news_item_ti;
    RelativeLayout main_rl;
    TextView select_sources_tv;
    Animation animation,animation2;
    public interface FragmentTouchListner{
        public  void back();
        public  void goto_setting_activity(String no,String id);
        public void subcatselected(Chanel chanel,String parent,String parent_name);
        public void setselected(String index);
        public void source();
        public void notify_sources();
        public void recreateActivity();
        public void ihave_completed();
        public void refresh_news();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NewsRecycleListActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LogoutUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();


        news_item_fade_ti = Session.getTheme(getActivity())?getActivity().getResources().getColor(R.color.news_item_fade_ti_nt):getActivity().getResources().getColor(R.color.news_item_fade_ti);
        news_item_ti = Session.getTheme(getActivity())?getActivity().getResources().getColor(R.color.settings_txt_first_nt):getActivity().getResources().getColor(R.color.settings_txt_first);

        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper3);
        user_img=(CircleImageView)view.findViewById(R.id.view4);
        settings_head_tv=(TextView)view.findViewById(R.id.settings_head_tv);
        push_settings_head_tv=(TextView)view.findViewById(R.id.push_settings_tv);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ImageView back_btn = (ImageView) view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.getDisplayedChild()==1){
                    viewFlipper.setDisplayedChild(0);
                    settings_head_tv.setText(Session.getword(getActivity(), "title_settings"));
                }else {
                    mCallBack.back();
                }
            }
        });

        notification_switch = (Switch) view.findViewById(R.id.notification_switch);
        sound_switch = (Switch) view.findViewById(R.id.sound_switch);
        night_switch = (Switch) view.findViewById(R.id.night_switch);
        notification_switch.setChecked(sharedPreferences.getBoolean("notify", true));

//        sound_switch.setChecked(sharedPreferences.getBoolean("sound", false));
        night_switch.setChecked(sharedPreferences.getBoolean("night", false));


        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notify", isChecked);
                editor.commit();
//                Session.sendRegistrationToServer(getActivity());
                if (notification_switch.isChecked()) {
                    select_sources_tv.setTextColor(news_item_ti);

                } else {
                    select_sources_tv.setTextColor(news_item_fade_ti);
                }


            }
        });
        pp_tv=(TextView)view.findViewById(R.id.pp_tv_acc);
        pp_tv.setText(Session.getword(getActivity(), "privacy_policy"));
        user_name=(TextView)view.findViewById(R.id.user_name_tv);
        logout_tv=(TextView)view.findViewById(R.id.logout_tv);
        logout_tv.setText(Session.getword(getActivity(), "signout"));
        logout_head=(TextView)view.findViewById(R.id.logout_tv_tv);
        logout_head.setText(Session.getword(getActivity(), "signout"));
        logout_pop_tv=(TextView)view.findViewById(R.id.logout_pop_tv);
        logout_pop_tv.setText(Session.getword(getActivity(), "signout"));
        cancel_pop_tv=(TextView)view.findViewById(R.id.cancel_pop_tv);
        cancel_pop_tv.setText(Session.getword(getActivity(), "cancel"));
        choose_option=(TextView)view.findViewById(R.id.choose_option);
        choose_option.setText(Session.getword(getActivity(), "choose_option"));
        login_tv=(TextView)view.findViewById(R.id.acc_login);
        login_tv.setText(Session.getword(getActivity(), "login"));
        signup_tv=(TextView)view.findViewById(R.id.acc_signup);
        signup_tv.setText(Session.getword(getActivity(),"signup"));
        text_tv=(TextView)view.findViewById(R.id.acc_text_tv);
        text_tv.setText(Session.getword(getActivity(),"text"));
        my_accc_tv=(TextView)view.findViewById(R.id.my_accc_tv);
        my_accc_tv.setText(Session.getword(getActivity(), "my_account"));
//        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("sound", isChecked);
//                editor.commit();
//            }
//        });
        night_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night", isChecked);
                editor.commit();
                mCallBack.recreateActivity();
            }
        });

        change_language = (LinearLayout) view.findViewById(R.id.change_language);
        select_sources_notify = (LinearLayout) view.findViewById(R.id.select_sources_for_notify);
        change_fontsize = (LinearLayout) view.findViewById(R.id.font_size_btn);
        contact_us_link = (LinearLayout) view.findViewById(R.id.contact_us);
        facebook_link= (LinearLayout) view.findViewById(R.id.facebook_link);
        twitter_link= (LinearLayout) view.findViewById(R.id.twitter_link);
        instagram_link= (LinearLayout) view.findViewById(R.id.instagram_link);
        rating_link= (LinearLayout) view.findViewById(R.id.rating_link);
        add_source_link= (LinearLayout) view.findViewById(R.id.add_source_link);
        report_bug_link= (LinearLayout) view.findViewById(R.id.report_bug_link);
        whatsup_share= (LinearLayout) view.findViewById(R.id.whatsup_share);
        facebook_share= (LinearLayout) view.findViewById(R.id.facebook_share);
        twitter_share= (LinearLayout) view.findViewById(R.id.twitter_share);
        instagram_share= (LinearLayout) view.findViewById(R.id.instagram_share);
        google_share= (LinearLayout) view.findViewById(R.id.google_share);
        linkin_share= (LinearLayout) view.findViewById(R.id.linkdin_share);
        email_share= (LinearLayout) view.findViewById(R.id.email_share);
        sms_share= (LinearLayout) view.findViewById(R.id.sms_share);
        login_ll= (LinearLayout) view.findViewById(R.id.login_acc_ll);
        signup_ll= (LinearLayout) view.findViewById(R.id.signup_acc_ll);
        my_acc_ll= (LinearLayout) view.findViewById(R.id.my_acccc);
        login_signup_ll= (LinearLayout) view.findViewById(R.id.sett_log_sign_ll);
        logout_main_ll= (LinearLayout) view.findViewById(R.id.logout_main_ll);
        logout_ll= (LinearLayout) view.findViewById(R.id.logout_pop_ll);
        main_rl= (RelativeLayout) view.findViewById(R.id.main_rl);
        my_ll= (LinearLayout) view.findViewById(R.id.sett_log_sign_llll);
        pop_shade_ll= (LinearLayout) view.findViewById(R.id.pop_shade_ll);

        my_acc_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyAccountActivity.class);
                startActivity(intent);
            }
        });
        logout_pop_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_ll.setVisibility(View.GONE);
                pop_shade_ll.setVisibility(View.GONE);
                animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                animation2.reset();
                logout_ll.setAnimation(animation2);
                Session.set_user_id(getActivity(), "-1","");
                Session.set_user_img(getActivity(), "-1");
                Intent intent = new Intent(getActivity(), NewsRecycleListActivity.class);
                intent.putExtra("feed_id", "0");
                intent.putExtra("login_check", "0");
                startActivity(intent);
            }
        });
        cancel_pop_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_ll.setVisibility(View.GONE);
                pop_shade_ll.setVisibility(View.GONE);
                animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                animation2.reset();
                logout_ll.setAnimation(animation2);

            }
        });
        pop_shade_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_ll.setVisibility(View.GONE);
                pop_shade_ll.setVisibility(View.GONE);
                animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                animation2.reset();
                logout_ll.setAnimation(animation2);
            }
        });
        my_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Session.get_user_id(getActivity()).equals("-1")) {
                    if(logout_ll.getVisibility()==View.GONE){
                        logout_ll.setVisibility(View.VISIBLE);
                        pop_shade_ll.setVisibility(View.VISIBLE);
                        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                        animation.reset();
                        logout_ll.setAnimation(animation);
                    }else{
                        logout_ll.setVisibility(View.GONE);
                        pop_shade_ll.setVisibility(View.GONE);
                        animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                        animation2.reset();
                        logout_ll.setAnimation(animation2);
                    }

                }
            }
        });
        pp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
                intent.putExtra("pp", privacy_policy);
                startActivity(intent);
            }
        });
        if(Session.get_user_id(getActivity()).equals("-1")){
            user_name.setText(Session.get_user_name(getActivity()));
//            logout_main_ll.setVisibility(View.GONE);
            login_signup_ll.setVisibility(View.VISIBLE);
            text_tv.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.GONE);
            Picasso.with(getActivity()).load(R.drawable.no_user_image).into(user_img);

        }else{
            user_name.setText(Session.get_user_name(getActivity()));
//            logout_main_ll.setVisibility(View.VISIBLE);
            login_signup_ll.setVisibility(View.GONE);
            text_tv.setVisibility(View.GONE);
            user_name.setVisibility(View.VISIBLE);
            if(Session.get_user_img(getActivity()).equals("-1"))
                Picasso.with(getActivity()).load(R.drawable.no_user_image).into(user_img);
            else
                Picasso.with(getActivity()).load(Session.get_user_img(getActivity())).into(user_img);
        }
        settings_head_tv.setText(Session.getword(getActivity(),"title_settings"));
        push_settings_head_tv.setText(Session.getword(getActivity(),"push_notifications"));
        TextView settings_tv=(TextView)view.findViewById(R.id.settings_tv);
        settings_tv.setText(Session.getword(getActivity(),"title_settings").toUpperCase());

        TextView change_lan_tv=(TextView)view.findViewById(R.id.change_lang_tv);
        change_lan_tv.setText(Session.getword(getActivity(),"title_select_sources"));

        select_sources_tv=(TextView)view.findViewById(R.id.select_sources_tv);
        select_sources_tv.setText(Session.getword(getActivity(),"sources_for_notification"));
        if(notification_switch.isChecked()){
            select_sources_tv.setTextColor(news_item_ti);
        }else{

            select_sources_tv.setTextColor(news_item_fade_ti);
        }


        TextView urgent_news_tv=(TextView)view.findViewById(R.id.urgent_news_tv);
        urgent_news_tv.setText(Session.getword(getActivity(),"urgent_news_notification"));
        TextView sound_tv=(TextView)view.findViewById(R.id.notify_tv);
        sound_tv.setText(Session.getword(getActivity(), "sound_notifications"));

        TextView night_tv=(TextView)view.findViewById(R.id.night_tv);
        night_tv.setText(Session.getword(getActivity(), "night_mode"));

        TextView font_tv=(TextView)view.findViewById(R.id.font_tv);
        font_tv.setText(Session.getword(getActivity(), "size_font_address"));

        TextView naqsh_app=(TextView)view.findViewById(R.id.app_tv);
        naqsh_app.setText(Session.getword(getActivity(),"title_nashrapp").toUpperCase());

        TextView share_tv=(TextView)view.findViewById(R.id.share_tv);
        share_tv.setText(Session.getword(getActivity(),"title_share_nashr").toUpperCase());

        TextView contact_tv=(TextView)view.findViewById(R.id.contact_us_tv);
        contact_tv.setText(Session.getword(getActivity(),"contact_us"));
        TextView facebook_tv=(TextView)view.findViewById(R.id.facebook_tv);
        facebook_tv.setText(Session.getword(getActivity(),"facebook"));
        TextView twitter_tv=(TextView)view.findViewById(R.id.twitter_tv);
        twitter_tv.setText(Session.getword(getActivity(),"twitter"));
        TextView instagram_tv=(TextView)view.findViewById(R.id.instagram_tv);
        instagram_tv.setText(Session.getword(getActivity(),"Instagram"));
        TextView rating_tv=(TextView)view.findViewById(R.id.rating_tv);
        rating_tv.setText(Session.getword(getActivity(),"rate_nashrapp"));
        TextView source_tv=(TextView)view.findViewById(R.id.addsource_tv);
        source_tv.setText(Session.getword(getActivity(),"suggest_add_source"));
        TextView report_tv=(TextView)view.findViewById(R.id.bug_tv);
        report_tv.setText(Session.getword(getActivity(),"report_bug"));

        TextView watsapp_tv=(TextView)view.findViewById(R.id.watspp_tv);
        watsapp_tv.setText(Session.getword(getActivity(),"whatsapp"));
        TextView facebook_share_tv=(TextView)view.findViewById(R.id.facebook_share_tv);
        facebook_share_tv.setText(Session.getword(getActivity(),"facebook"));
        TextView twitter_share_tv=(TextView)view.findViewById(R.id.twitter_share_tv);
        twitter_share_tv.setText(Session.getword(getActivity(),"twitter"));
        TextView instagram_share_tv=(TextView)view.findViewById(R.id.instagram_share_tv);
        instagram_share_tv.setText(Session.getword(getActivity(),"Instagram"));
        TextView google_share_tv=(TextView)view.findViewById(R.id.google_share_tv);
        google_share_tv.setText(Session.getword(getActivity(),"google_plus"));
        TextView linked_share_tv=(TextView)view.findViewById(R.id.linked_share_tv);
        linked_share_tv.setText(Session.getword(getActivity(),"linked_in"));
        TextView email_share_tv=(TextView)view.findViewById(R.id.email_share_tv);
        email_share_tv.setText(Session.getword(getActivity(),"email"));
        TextView sms_share_tv=(TextView)view.findViewById(R.id.sms_share_tv);
        sms_share_tv.setText(Session.getword(getActivity(),"sms"));

        test_text=(MyTextView)view.findViewById(R.id.test_text);
        test_text.setText(Session.getword(getActivity(),"change_font"));
        s_tv=(MyTextView)view.findViewById(R.id.text_s_tv);
        s_tv.setText(Session.getword(getActivity(), "font_small"));
        s_tv.setTextSize(15);

        m_tv=(MyTextView)view.findViewById(R.id.text_m_tv);
        m_tv.setText(Session.getword(getActivity(), "font_medium"));
        m_tv.setTextSize(20);

        l_tv=(MyTextView)view.findViewById(R.id.text_l_tv);
        l_tv.setText(Session.getword(getActivity(), "font_large"));
        l_tv.setTextSize(25);

        x_tv=(MyTextView)view.findViewById(R.id.text_x_tv);
        x_tv.setText(Session.getword(getActivity(), "font_xlarge"));
        x_tv.setTextSize(30);

        s_ll= (LinearLayout) view.findViewById(R.id.text_s_ll);
        m_ll= (LinearLayout) view.findViewById(R.id.text_m_ll);
        l_ll= (LinearLayout) view.findViewById(R.id.text_l_ll);
        x_ll= (LinearLayout) view.findViewById(R.id.text_x_ll);

        s_im=(ImageView)view.findViewById(R.id.text_s_im);
        m_im=(ImageView)view.findViewById(R.id.text_m_im);
        l_im=(ImageView)view.findViewById(R.id.text_l_im);
        x_im=(ImageView)view.findViewById(R.id.text_x_im);

        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        signup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        change_fontsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
                settings_head_tv.setText(Session.getword(getActivity(), "change_font"));
            }
        });

        int font_size = 20;
        try {
            font_size = sharedPreferences.getInt("font_sizee", 20);
        }catch (Exception ex){
            ex.printStackTrace();
        }

//        s_im.setVisibility(View.GONE);
//        m_im.setVisibility(View.VISIBLE);
//        l_im.setVisibility(View.GONE);
//        x_im.setVisibility(View.GONE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("font_sizee", 15);
//        editor.commit();
//        test_text.setTextSize(15);




        s_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_im.setImageResource(R.drawable.notify_sources_on);
                m_im.setImageResource(R.drawable.notify_sources_off);
                l_im.setImageResource(R.drawable.notify_sources_off);
                x_im.setImageResource(R.drawable.notify_sources_off);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("font_sizee", 15);
                editor.apply();
                test_text.setTextSize(15);
                mCallBack.refresh_news();
            }
        });
        m_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_im.setImageResource(R.drawable.notify_sources_off);
                m_im.setImageResource(R.drawable.notify_sources_on);
                l_im.setImageResource(R.drawable.notify_sources_off);
                x_im.setImageResource(R.drawable.notify_sources_off);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("font_sizee", 20);
                editor.apply();
                test_text.setTextSize(20);
                mCallBack.refresh_news();
            }
        });
        l_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_im.setImageResource(R.drawable.notify_sources_off);
                m_im.setImageResource(R.drawable.notify_sources_off);
                l_im.setImageResource(R.drawable.notify_sources_on);
                x_im.setImageResource(R.drawable.notify_sources_off);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("font_sizee", 25);
                editor.apply();
                test_text.setTextSize(25);
                mCallBack.refresh_news();
            }
        });
        x_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_im.setImageResource(R.drawable.notify_sources_off);
                m_im.setImageResource(R.drawable.notify_sources_off);
                l_im.setImageResource(R.drawable.notify_sources_off);
                x_im.setImageResource(R.drawable.notify_sources_on);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("font_sizee", 30);
                editor.apply();
                test_text.setTextSize(30);
                mCallBack.refresh_news();
            }
        });


        if(font_size==15){

            s_im.setImageResource(R.drawable.notify_sources_on);
            m_im.setImageResource(R.drawable.notify_sources_off);
            l_im.setImageResource(R.drawable.notify_sources_off);
            x_im.setImageResource(R.drawable.notify_sources_off);


        }else if(font_size==20){
            s_im.setImageResource(R.drawable.notify_sources_off);
            m_im.setImageResource(R.drawable.notify_sources_on);
            l_im.setImageResource(R.drawable.notify_sources_off);
            x_im.setImageResource(R.drawable.notify_sources_off);


        }else if(font_size == 25){

            s_im.setImageResource(R.drawable.notify_sources_off);
            m_im.setImageResource(R.drawable.notify_sources_off);
            l_im.setImageResource(R.drawable.notify_sources_on);
            x_im.setImageResource(R.drawable.notify_sources_off);



        }else if(font_size == 30){
            s_im.setImageResource(R.drawable.notify_sources_off);
            m_im.setImageResource(R.drawable.notify_sources_off);
            l_im.setImageResource(R.drawable.notify_sources_off);
            x_im.setImageResource(R.drawable.notify_sources_on);

        }


            change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_language_popup();
                mCallBack.source();
            }
        });

        select_sources_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notification_switch.isChecked())
                mCallBack.notify_sources();
            }
        });



        contact_us_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.goto_setting_activity("4","0");
            }
        });

        facebook_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
                startActivity(browserIntent);
            }catch (Exception exception){
                Toast.makeText(getActivity(),"invalid url",Toast.LENGTH_SHORT).show();
            }
            }
        });

        twitter_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                startActivity(browserIntent);
            }catch (Exception exception){
                Toast.makeText(getActivity(),"invalid url",Toast.LENGTH_SHORT).show();
            }
            }
        });
        instagram_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram));
                startActivity(browserIntent);
                }catch (Exception exception){
                    Toast.makeText(getActivity(),"invalid url",Toast.LENGTH_SHORT).show();
                }
            }
        });

        rating_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome"));
                //startActivity(browserIntent);
              //  https://play.google.com/store/apps/details?id=com.android.chrome

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.android.chrome")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.android.chrome")));
                }
            }
        });

        add_source_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_add_source_view();
                mCallBack.goto_setting_activity("1","0");
            }
        });

        report_bug_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show_reportbug_view();
                mCallBack.goto_setting_activity("2","0");
            }
        });
        facebook_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("face");
            }
        });
        whatsup_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("what");
            }
        });

        twitter_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("twit");
            }
        });

        instagram_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri imageUri = null;
//                try {
//                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
//                            BitmapFactory.decodeResource(getResources(), logo), null, null));
//                } catch (NullPointerException e) {
//
//                }
//                try {
//                    startActivity(createInstagramIntent(imageUri));
//                } catch (Exception e) {
//
//                    try {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
//                    }
//                }
//
//
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
                progressDialog.show();

                // progressDialog.setCancelable(false);

                Picasso.with(getActivity()).load(logo).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                        if(progressDialog!=null)
                            progressDialog.dismiss();

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
//                        i.putExtra(Intent.EXTRA_SUBJECT, html2text(news.title));
//                        i.putExtra(Intent.EXTRA_TEXT, html2text(news.link));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setPackage("com.instagram.android");
                        try {
                            startActivity(i);
                        } catch (Exception e) {

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
                            }
                        }

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

//                            if(progressDialog!=null)
//                                progressDialog.dismiss();

                    }
                });
            }
        });

        google_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("com.google.android.apps.plus");
            }
        });

        linkin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("com.linkedin.android");
            }
        });

        email_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareClick("com.google.android.gm");
            }
        });

        sms_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareusingsms();
            }
        });



        get_details();

        mCallBack.ihave_completed();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_UP)
                {
                    if(viewFlipper.getDisplayedChild()==1){
                        viewFlipper.setDisplayedChild(0);
                        return  true;
                    }else{
                        return false;
                    }


                }
                return false;
            }
        } );


    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("onresume","account_fragment");
        if(Session.get_user_id(getActivity()).equals("-1")){
//            logout_main_ll.setVisibility(View.GONE);
            login_signup_ll.setVisibility(View.VISIBLE);
            text_tv.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.GONE);
            user_name.setText(Session.get_user_name(getActivity()));
            Picasso.with(getActivity()).load(R.drawable.no_user_image).into(user_img);
        }else{
            user_name.setText(Session.get_user_name(getActivity()));
//            logout_main_ll.setVisibility(View.VISIBLE);
            login_signup_ll.setVisibility(View.GONE);
            text_tv.setVisibility(View.GONE);
            user_name.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(Session.get_user_img(getActivity())).into(user_img);
        }
    }

    private void show_language_popup(){


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(Session.getword(getActivity(),"change_language"));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("English");
        arrayAdapter.add("Arabic");
        arrayAdapter.add("French");


        builderSingle.setNegativeButton(
                Session.getword(getActivity(),"cancel"),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if(which==0){
                            Session.set_user_language(getActivity(),"en");
                            Intent intent = new Intent(getActivity(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else if(which==1){
                            Session.set_user_language(getActivity(),"ar");
                            Intent intent = new Intent(getActivity(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else if(which==2){
                            Session.set_user_language(getActivity(),"fr");
                            Intent intent = new Intent(getActivity(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                       /* AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                getActivity());
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
*/                    }
                });
        builderSingle.show();

            }
    public void shareFacebook() {
        String fullUrl = "https://m.facebook.com/sharer.php?u=..";
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setClassName("com.facebook.katana",
                    "com.facebook.katana.ShareLinkActivity");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "your title text");
            startActivity(sharingIntent);

        } catch (Exception e) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(fullUrl));
            startActivity(i);

        }
    }
    private Intent createInstagramIntent(Uri uriString) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriString);
        shareIntent.setPackage("com.instagram.android");
        return shareIntent;
    }
    private void shareusingsms(){

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("sms_body",play_store);
        try {
            startActivity(smsIntent);
        }catch (Exception ex){

        }

    }

    public void onShareClick(String appname) {
        Resources resources = getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        try {
            emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(play_store));
        }catch (Exception ex){
            emailIntent.putExtra(Intent.EXTRA_TEXT, play_store);
        }

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        emailIntent.setType("message/rfc822");

        PackageManager pm = getActivity().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, "share this message");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if(packageName.contains("twitter") ||
                    packageName.contains("facebook") ||
                    packageName.contains("what") ||
                    packageName.contains("mms") ||
                    packageName.contains("instagram") ||
                    packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if(packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT,play_store);
                } else if(packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
                    // will show the <meta content ="..."> text from that page with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                } else if(packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                } else if(packageName.contains("instagram")) {
//                    Uri imageUri = null;
//                    try {
//                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
//                                BitmapFactory.decodeResource(getResources(), R.drawable.app_icon), null, null));
//
//                    } catch (NullPointerException e) {
//                    }
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                    Picasso.with(getActivity()).load(logo).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("image/*");
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                            i.putExtra(Intent.EXTRA_SUBJECT, html2text(title));
                            i.putExtra(Intent.EXTRA_TEXT, html2text(share_text));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setPackage("com.instagram.android");
                            try {
                                startActivity(i);
                            } catch (Exception e) {

                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.instagram.android")));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
                                }
                            }

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                }else if(packageName.contains("what")) {
//                    Uri imageUri = null;
//                    try {
//                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
//                                BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon), null, null));
//                    } catch (NullPointerException e) {
//                    }
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_STREAM,imageUri);
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                    intent.putExtra(Intent.EXTRA_TEXT, play_store);
                    intent.putExtra(Intent.EXTRA_SUBJECT, title);
                    intent.setType("message/rfc822");
                }

                if(packageName.contains(appname)) {
                    startActivity(intent);
                    return;
                }
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);


    }
    public static String html2text(String html) {
        // return Jsoup.parse(html).text();
        return html;
    }
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void show_add_source_view(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());
        edittext.setHint("Type Here");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10,0,10,0);
        edittext.setLayoutParams(lp);

        alert.setMessage("What Source do you want us to add?");
        //alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();

    }
    private void show_reportbug_view(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());
        edittext.setHint("Type Here");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10,0,10,0);
        edittext.setLayoutParams(lp);
        alert.setMessage("Give description about the bug you have found");
        //alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String YouEditTextValue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();

    }



    public void get_details(){
        String url = null;
        try {
            url = Session.NOTIFY_SERVER_URL+"settings.php";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Session.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                if(progressDialog!=null)
                progressDialog.dismiss();

                try {
                    title=jsonObject.getString("title"+Session.get_append(getActivity()));
                    title_ar=jsonObject.getString("title_ar");
                    title_fr=jsonObject.getString("title_fr");
                    logo=jsonObject.getString("logo");
                    email=jsonObject.getString("email");
                    phone=jsonObject.getString("phone");
                    play_store=jsonObject.getString("share_str");
                    facebook=jsonObject.getString("facebook");
                    twitter=jsonObject.getString("twitter");
                    instagram=jsonObject.getString("instagram");
                    share_text=jsonObject.getString("share_str");
                    privacy_policy=jsonObject.getString("privacy_policy");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
              //  Toast.makeText(getActivity(), "Server not connected", Toast.LENGTH_SHORT).show();
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }
        });
// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }


}
