package com.kxyu.accwheaterdome.okhttp.builder;


import com.kxyu.accwheaterdome.okhttp.request.PostFileRequest;
import com.kxyu.accwheaterdome.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by mseven on 5/25/16.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}
