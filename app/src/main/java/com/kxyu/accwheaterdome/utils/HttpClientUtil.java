package com.kxyu.accwheaterdome.utils;

import com.kxyu.accwheaterdome.okhttp.OkHttpUtils;
import com.kxyu.accwheaterdome.okhttp.callback.StringCallback;

/**
 * Created by kxyu on 16-8-2.
 */
public class HttpClientUtil {
    private static final String XSCREEN_URL = "http://dataservice.accuweather.com/locations/v1/countries/ASI?apikey=9O04J0PS9qtx6vAuGvXOgOFFmMd4ybQJ";

    private static final String CONTENT_TYPE = "application/json";


    private static String getXscreenListUrl() {
        return XSCREEN_URL;
    }

    public static void useOkHttpPost(String jsonString, StringCallback callback) {
        OkHttpUtils
                .postString()
                .url(getXscreenListUrl())
                .content(jsonString)
                .build()
                .execute(callback);
    }
    public static void useOkHttpGet(String Url, StringCallback callback) {
        OkHttpUtils.get().url(Url).build().execute(callback);
    }
}
