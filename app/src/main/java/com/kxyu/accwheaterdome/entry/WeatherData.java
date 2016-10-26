package com.kxyu.accwheaterdome.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kxyu on 16-10-26.
 */
public class WeatherData {

    @SerializedName("LocalObservationDateTime")
    public String LocalObservationDateTime;

    @SerializedName("EpochTime")
    public String EpochTime;

    @SerializedName("WeatherText")
    public String WeatherText;

    @SerializedName("WeatherIcon")
    public String WeatherIcon;

    @SerializedName("IsDayTime")
    public String IsDayTime;

   @SerializedName("Temperature")
    public Temperature temperature;

    @SerializedName("MobileLink")
    public String MobileLink;

    @SerializedName("RelativeHumidity")
    public String RelativeHumidity;

    @SerializedName("DewPoint")
    public DewPoint dewPoint;

    @SerializedName("Pressure")
    public Pressure pressure;

    public class Temperature{

        @SerializedName("Metric")
        public Metric metric;

        public class  Metric{

            @SerializedName("Value")
            public String Value;

            @SerializedName("Unit")
            public String Unit;

            @SerializedName("UnitType")
            public String UnitType;
        }
    }

    public class Pressure{

        @SerializedName("Metric")
        public Metric metric;

        public class  Metric{

            @SerializedName("Value")
            public String Value;

            @SerializedName("Unit")
            public String Unit;

            @SerializedName("UnitType")
            public String UnitType;
        }
    }

    public class DewPoint{

        @SerializedName("Metric")
        public Metric metric;

        public class  Metric{

            @SerializedName("Value")
            public String Value;

            @SerializedName("Unit")
            public String Unit;

            @SerializedName("UnitType")
            public String UnitType;
        }
    }
    public String toString() {


        return "LocalObservationDateTime : " + LocalObservationDateTime + "\n" +
                "EpochTime : " + EpochTime + "\n" +
                "WeatherIcon : "+ WeatherIcon+"\n"+
                "WeatherText : " + WeatherText + "\n" +
                "IsDayTime : " + IsDayTime +"\n"+
                "Temperature Value : "+temperature.metric.Value+"\n"+
                "MobileLink : "+MobileLink+"\n"+
                "RelativeHumidity : "+RelativeHumidity;
    }
}
