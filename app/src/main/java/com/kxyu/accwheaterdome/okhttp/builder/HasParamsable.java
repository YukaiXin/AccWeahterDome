package com.kxyu.accwheaterdome.okhttp.builder;

import java.util.Map;

/**
 * Created by mseven on 5/25/16.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
