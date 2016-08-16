package com.kxyu.accwheaterdome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kxyu.accwheaterdome.entry.LocationInfo;
import com.kxyu.accwheaterdome.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by kxyu on 16-8-16.
 */
public class WeatherActivity extends AppCompatActivity{

    LocationInfo mLocation;


    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);


    }


    private void getPhoto() {
        HashMap<String, String> parm = new HashMap<>();
//        parm.put("categories", "jokes");
//        parm.put("action", "prev");
//        parm.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFuZHJvaWQ6bGV3YV8xMjQiLCJhY2NvdW50X3R5cGUiOjIsInVzZXJfaWQiOiI1NzNiZDY2Y2NkZmZiYzc3NDY1NjZiYjAiLCJleHAiOjE0NzEzMTU1MzR9.6ZTYeceNLsuQt_suGV9tH_pfbGLptE0pggL0pM3H2Ic");
//        parm.put("read_tag", "");
        Gson gson = new Gson();
        HttpClientUtil.useOkHttpPost(gson.toJson(parm), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {


                Gson gson = new Gson();

                mLocation = gson.fromJson(response, LocationInfo.class);

               // Log.i("kxyuyuyu"," SIZE : "+ mLocation.Location.)
            }
        });
    }


}
