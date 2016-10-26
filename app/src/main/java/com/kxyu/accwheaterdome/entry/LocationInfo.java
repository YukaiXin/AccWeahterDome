package com.kxyu.accwheaterdome.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kxyu on 16-8-16.
 */
public class LocationInfo {





    public class Location {

       @SerializedName("ID")
       public String ID;

        @SerializedName("LocalizedName")
        public String LocalizedName;

        @SerializedName("EnglishName")
        public String EnglishName;

    }
}