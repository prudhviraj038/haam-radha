package com.mamacgroup.hamtest;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SectionedItem implements Serializable {

        String type="0";
        Categories categories=null;
        Chanel chanel=null;
        String header=null;
        String image="";


       SectionedItem( Categories categories){

            this.categories = categories;
            type="1";
        }

        SectionedItem( Chanel chanel){

        this.chanel = chanel;
            type="2";
        }
        SectionedItem( String header){

        this.header = header;
            type="0";
        }

}