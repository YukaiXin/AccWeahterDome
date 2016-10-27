package com.kxyu.accwheaterdome.weather;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kxyu.accwheaterdome.R;
import com.kxyu.accwheaterdome.entry.WeatherData;
import com.kxyu.accwheaterdome.okhttp.callback.StringCallback;
import com.kxyu.accwheaterdome.utils.HttpClientUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by kxyu on 16-10-26.
 */
public class WeatherShow extends RelativeLayout{


    View mView;
    WeatherData weatherData;
    String key;
    String LocationName = null;
    String apiKey = "c272988005344bafb66feba23e8b306e";
    Context mContext;


    ImageView mWeatherIcon;
    TextView  mLocationName;
    RelativeLayout relativeLayout;
    TextView  mWeatherText;
    public static Location mLocation = null;

    public WeatherShow(Context context) {
        super(context, null);
    }

    public WeatherShow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherShow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, 0, 0);
    }


    public WeatherShow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView(context);
    }

    private View initView(Context context) {

        mView = inflate(context, R.layout.weather_layout, this);
        relativeLayout = (RelativeLayout) mView.findViewById(R.id.weather_layout);
        relativeLayout.setBackgroundResource(R.mipmap.shanghai);
        mWeatherIcon = (ImageView) mView.findViewById(R.id.weather_icon);
        mLocationName = (TextView) mView.findViewById(R.id.location_name);
        return mView;
    }


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

    private void initWeatherItemView() {



     //   double value = Double.valueOf(weatherData.temperature.metric.Value);

//        mTemperature.setText(Math.round(value) + "℃");
//        mHumidity.setText(weatherData.RelativeHumidity + "%");
//        mWeatherText.setText(weatherData.WeatherText);
//        mDewPoint.setText(weatherData.dewPoint.metric.Value + "℃");
//        mPressure.setText(weatherData.pressure.metric.Value + "mb");
//        Log.i("kxyu_weather", "id : " + weatherData.WeatherIcon + "   " + LocationName);
        if (TextUtils.isEmpty(LocationName)) {
            LocationName = "Delhi";
        }
        mLocationName.setText(LocationName);
        final String packageName = mContext.getPackageName();
//        Log.d("shunli", "packageName --> " + packageName);
//        int imageResId = Constant.weatherId.get(weatherData.WeatherIcon);
        Resources resources = mContext.getResources();
        int  imageID = resources.getIdentifier("weather_" + weatherData.WeatherIcon, "mipmap", packageName);
        mWeatherIcon.setImageResource(imageID);
//        if (imageResId > 0) {
//            mWeatherIcon.setImageResource(imageResId);
//        } else {
//            mWeatherIcon.setImageResource(Constant.weatherId.get(1));
//        }
//        mWeatherItem.setOnClickListener(this);
    }


    private void getWeather(String url, final boolean req) {

        HttpClientUtil.useOkHttpGet(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("kxyu_weather", "ERROR : "+e);
//                Gson gson = new Gson();
//                LocationName = FileUtils.readFile(getContext(), WEATHER_FILE_ONE);
//                String txt = FileUtils.readFile(getContext(), WEATHER_FILE_TWO);
//                if (!TextUtils.isEmpty(txt)) {
//                    weatherData = gson.fromJson(txt.trim(), WeatherData.class);
                   initWeatherItemView();
//                }
            }

            @Override
            public void onResponse(String response, int id) throws JSONException {
                Log.i("kxyu_weather", response);
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
                        Log.i("kxyu_weather","success "+e);
                    }
//                    FileUtils.writeFile(getContext(), WEATHER_FILE_TWO, response, true);
                    initWeatherItemView();
                }

            }
        });
    }
}
