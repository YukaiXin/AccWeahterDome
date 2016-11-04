package com.kxyu.accwheaterdome.weather;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class WeatherShow extends RelativeLayout implements View.OnClickListener{

    public final static int ININT_WEATHER = 1;
    public final String DEFAULT_CITY = "上海";
    String apiKey = "c272988005344bafb66feba23e8b306e";
    public final String DEFAULT_CITY_URL ="http://apidev.accuweather.com/currentconditions/v1/106577.json?language=zh&apikey=" + apiKey + "&details=true";
    WeatherActivity weatherActivity;
    View mView;
    WeatherData weatherData;
    String key;
    String LocationName = null;
    Context mContext;
    private TextView mWeatherText;
    private TextView mTemperature;
    private TextView mHumidity;
    private TextView mWeather_Range_temperature;
    public Handler mHandler;
    private ImageView mRetryBtn;

    ImageView mWeatherIcon;
    TextView  mLocationName;
    RelativeLayout relativeLayout;


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

    public void setWeatherActivity(WeatherActivity weatherActivity){
        this.weatherActivity = weatherActivity;
    }

    private View initView(Context context) {


        mView = inflate(context, R.layout.weather_layout, this);
        relativeLayout = (RelativeLayout) mView.findViewById(R.id.weather_layout);
        mWeatherIcon = (ImageView) mView.findViewById(R.id.weather_icon);
        mLocationName = (TextView) mView.findViewById(R.id.weather_location_tv);

        mTemperature = (TextView) findViewById(R.id.weather_temperature_tv);
        mWeatherText = (TextView) findViewById(R.id.weather_text);
        mHumidity = (TextView) findViewById(R.id.weather_humidity);
        mWeather_Range_temperature = (TextView) findViewById(R.id.weather_HL_temperature);
        mRetryBtn = (ImageView) findViewById(R.id.retry);
        mRetryBtn.setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ININT_WEATHER:
                        initWeatherItemView();
                        break;
                }
            }
        };

        return mView;
    }


    public void loadWheaterCard(Location location) {
        Log.d("weather_debug", "(loadWheaterCard)location = " + location);

        String URL = DEFAULT_CITY_URL;
        if (location != null) {
            String Lo = String.valueOf(location.getLongitude());
            String La = String.valueOf(location.getLatitude());
            URL = "http://apidev.accuweather.com/locations/v1/cities/geoposition/search?q=" + La + "," + Lo + "&apikey=" + apiKey+"&language=zh";
            getWeather(URL, true);
        } else {
            getWeather(URL, false);
        }

    }

    private void initWeatherItemView() {


        final String packageName = mContext.getPackageName();
        Resources resources = mContext.getResources();
        int  imageID = resources.getIdentifier("weather_" + weatherData.WeatherIcon, "mipmap", packageName);
        mWeatherIcon.setImageResource(imageID);

        double value = Double.valueOf(weatherData.temperature.metric.Value);

        mTemperature.setText(Math.round(value) + "°");
        mHumidity.setText(weatherData.RelativeHumidity + "%");
        mWeatherText.setText(weatherData.WeatherText);

        double miniValue = Double.valueOf(weatherData.temperatureSummary.past24HourRange.minimum.metric.Value);
        double maxiValue = Double.valueOf(weatherData.temperatureSummary.past24HourRange.maximum.metric.Value);

        mTemperature.setText(Math.round(value) + "°");

        mWeather_Range_temperature.setText(Math.round(miniValue)+ "°/"+Math.round(maxiValue)+"°");
        Log.i("kxyu_weather", "id : " + weatherData.WeatherIcon + "   " + LocationName);
        if (TextUtils.isEmpty(LocationName)) {
            LocationName = DEFAULT_CITY;
        }
        mLocationName.setText(LocationName);

    }

    private void getWeather(String url, final boolean req) {

        HttpClientUtil.useOkHttpGet(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("kxyu_weather", "ERROR : "+e);
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
                    LocationName = jsonPart.getString("LocalizedName");
                    key = jsonPart.getString("Key");
                    String url = "http://apidev.accuweather.com/currentconditions/v1/" + key + ".json?language=zh&apikey="+apiKey+"&details=true";
                    getWeather(url, false);
                } else {
                    response = response.substring(1, response.length() - 1);
                    try {
                        weatherData = gson.fromJson(response.trim(), WeatherData.class);
                    } catch (Exception e) {
                        Log.i("kxyu_weather","success "+e);
                    }
                    mHandler.sendEmptyMessage(ININT_WEATHER);

                }

            }
        });
    }
    private void showRefreshAnimation(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.retry){
            showRefreshAnimation(mContext, mRetryBtn);
            if(weatherActivity != null){
                weatherActivity.getLocation();
            }
        }
    }
}
