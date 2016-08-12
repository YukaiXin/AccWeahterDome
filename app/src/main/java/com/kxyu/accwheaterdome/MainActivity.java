package com.kxyu.accwheaterdome;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    public EditText mSrearchCity;
    public TextView mDisplayCityWheater;
    String j1 = null;
    String j2 = null;
    String key;

    LocationManager locationManager;
    Location location;

    HttpURLConnection urlConnection = null;
    String result = "";
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSrearchCity = (EditText) findViewById(R.id.cityName);
        mDisplayCityWheater = (TextView) findViewById(R.id.display_weahter);




    }


    public void checkWeather(View view) {

        DownloadTask task = new DownloadTask();
        //hide keyboard on button press
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(mSrearchCity.getWindowToken(), 0);

        Log.i("kxyu","  ss"+1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("kxyu","  ss"+location);
        Toast.makeText(getApplication(),location.toString(),Toast.LENGTH_SHORT).show();
     //   http://api.accuweather.com/locations/v1/cities/geoposition/search.json?q=40, -73&apikey={your key}
        try {
            j1 = task.execute("http://api.accuweather.com/locations/v1/cities/geoposition/search.json?q="+ mSrearchCity.getText().toString() + "&apikey=c272988005344bafb66feba23e8b306e").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
                return "FAILED";
            }
        }

        @Override
        protected void onPostExecute(String j1) {
            super.onPostExecute(j1);

            // print key value

            try {
                JSONArray arr = new JSONArray(j1);
                for (int i = 0; i<arr.length(); i++){

                    JSONObject jsonPart = arr.getJSONObject(i);
                    key = jsonPart.getString("Key");

                }


                try {
                    DownloadTask weather = new DownloadTask();
                    j2 = weather.execute("http://apidev.accuweather.com/currentconditions/v1/"+ key + ".json?language=zh&apikey=c272988005344bafb66feba23e8b306e").get();

                    JSONArray weatherArray = new JSONArray(j2);
                    for (int i = 0; i<arr.length(); i++){

                        JSONObject weatherPart = weatherArray.getJSONObject(i);
                        mDisplayCityWheater.setText(weatherPart.getString("WeatherText")+ " " + weatherPart.getString("Temperature"));

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
