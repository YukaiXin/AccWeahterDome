package com.kxyu.accwheaterdome.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kxyu on 16-8-17.
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

    @SerializedName("TemperatureSummary")
    public TemperatureSummary temperatureSummary;




    public class Temperature{

        @SerializedName("Metric")
        public Metric metric;

        public class  Metric{

            @SerializedName("Value")
            public String Value;
        }
    }

    public class TemperatureSummary {

        @SerializedName("Past24HourRange")
        public Past24HourRange past24HourRange;

        public class Past24HourRange {

            @SerializedName("Minimum")
            public Minimum minimum;

            public class Minimum {

                @SerializedName("Metric")
                public Metric metric;

                public class Metric {

                    @SerializedName("Value")
                    public String Value;
                }

            }

            @SerializedName("Maximum")
            public Maximum maximum;

            public class Maximum {

                @SerializedName("Metric")
                public Metric metric;

                public class Metric {

                    @SerializedName("Value")
                    public String Value;
                }

            }
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
