package com.kxyu.accwheaterdome.weather;

import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.kxyu.accwheaterdome.HttpClientUtil;
import com.kxyu.accwheaterdome.entry.WeatherData;
import com.kxyu.accwheaterdome.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by kxyu on 16-10-26.
 */
public class WeatherShow {

    WeatherData weatherData;
    String key;
    String LocationName = null;
    String apiKey = "9O04J0PS9qtx6vAuGvXOgOFFmMd4ybQJ";

    public void loadWheaterCard(Location location) {
        Log.d("weather_debug", "(loadWheaterCard)location = " + location);

        String URL = "http://apidev.accuweather.com/currentconditions/v1/202396.json?language=en&apikey=" + apiKey + "&details=true";
        if (location != null) {
            String Lo = String.valueOf(location.getLongitude());
            String La = String.valueOf(location.getLatitude());
            URL = "http://apidev.accuweather.com/locations/v1/cities/geoposition/search?q=" + La + "," + Lo + "&apikey=" + apiKey;
            getWeather(URL, true);
        } else {
            getWeather(URL, false);
        }

    }

    private void getWeather(String url, final boolean req) {

        HttpClientUtil.useOkHttpPost(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
//                Gson gson = new Gson();
//                LocationName = FileUtils.readFile(getContext(), WEATHER_FILE_ONE);
//                String txt = FileUtils.readFile(getContext(), WEATHER_FILE_TWO);
//                if (!TextUtils.isEmpty(txt)) {
//                    weatherData = gson.fromJson(txt.trim(), WeatherData.class);
//                    initWeatherItemView();
//                }
            }

            @Override
            public void onResponse(String response, int id) throws JSONException {
                if (TextUtils.isEmpty(response)) return;
                Gson gson = new Gson();

                if (req == true) {
                    response = "[" + response + "]";
                    JSONArray arr = new JSONArray(response);
                    JSONObject jsonPart = arr.getJSONObject(0);
                    Log.i("kxyu_weather", " LocationName : " + LocationName);
                    LocationName = jsonPart.getString("EnglishName");
                   // FileUtils.writeFile(getContext(), WEATHER_FILE_ONE, LocationName, true);
                    key = jsonPart.getString("Key");
                    String url = "http://apidev.accuweather.com/currentconditions/v1/" + key + ".json?language=en&apikey=c272988005344bafb66feba23e8b306e&details=true";
                    getWeather(url, false);
                } else {
                    response = response.substring(1, response.length() - 1);
                    try {
                        weatherData = gson.fromJson(response.trim(), WeatherData.class);
                    } catch (Exception e) {
                    }
//                    FileUtils.writeFile(getContext(), WEATHER_FILE_TWO, response, true);
//                    initWeatherItemView();
                }

            }
        });
    }
}
