package com.kxyu.accwheaterdome;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kxyu.accwheaterdome.entry.LocationInfo;

/**
 * Created by kxyu on 16-8-16.
 */
public class WeatherActivity extends Activity {

    final String TAG = "kxyu_GPS";

    LocationInfo mLocation;
    TextView LocationName;
    LocationManager locationManager;
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("kxyu_gps", "Permission ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        LocationName = (TextView) findViewById(R.id.location_name);
        openGPSSettings();
        getLocation();
    }


    private void openGPSSettings() {
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
    }

    private void getLocation() {
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        Log.i("kxyu_gps","provider :  "+provider +"  provider ： "+locationManager.getAllProviders());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("kxyu_gps","Permission ");
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER); // 通过GPS获取位置
        updateToNewLocation(location);
//        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1 * 1000, 5,
                locationListener);
    }

    private void updateToNewLocation(Location location) {
        TextView tv1;

        if (location != null) {
            double  latitude = location.getLatitude();
            double longitude= location.getLongitude();
            LocationName.setText("L : "+String.valueOf(latitude)+"\n"+"H : "+String.valueOf(longitude));
        } else {
            LocationName.setText("无法获取地理信息");
        }

    }
    private LocationListener locationListener = new LocationListener() {
        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
            updateToNewLocation(location);
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());


        }

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "当前GPS状态为可见状态");


                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "当前GPS状态为服务区外状态");

                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");

                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }
            Location location = locationManager.getLastKnownLocation(provider);
            updateToNewLocation (location);
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {

        }


    };


}
