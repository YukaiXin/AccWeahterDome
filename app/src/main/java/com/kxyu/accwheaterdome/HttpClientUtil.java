package com.kxyu.accwheaterdome;

import com.kxyu.accwheaterdome.okhttp.OkHttpUtils;
import com.kxyu.accwheaterdome.okhttp.callback.StringCallback;

/**
 * Created by kxyu on 16-8-2.
 */
public class HttpClientUtil {
    private static final String XSCREEN_URL = "http://dataservice.accuweather.com/locations/v1/countries/ASI?apikey=9O04J0PS9qtx6vAuGvXOgOFFmMd4ybQJ";
    private static final String LIST_URL = "102";
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
}
