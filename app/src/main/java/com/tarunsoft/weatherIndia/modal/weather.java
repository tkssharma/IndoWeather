package com.tarunsoft.weatherIndia.modal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tsharma3 on 8/23/2015.
 */
public class weather implements Serializable {
    // Location information
    final static String OWM_CITY = "city";
    final static String OWM_CITY_NAME = "name";
    final static String OWM_COORD = "coord";
    final static String OWM_COORD_LAT = "lat";
    final static String OWM_COORD_LONG = "lon";
    // Weather information. Each day's forecast info is an element of the "list" array
    final static String OWM_LIST = "list";
    final static String OWM_DATETIME = "dt";
    final static String OWM_PRESSURE = "pressure";
    final static String OWM_HUMIDITY = "humidity";
    final static String OWM_WINDSPEED = "speed";
    final static String OWM_WIND_DIRECTION = "deg";
    // All temperatures are children of the "temp" object
    final static String OWM_TEMPERATURE = "temp";
    final static String OWM_MAX = "max";
    final static String OWM_MIN = "min";
    final static String OWM_WEATHER = "weather";
    final static String OWM_DESCRIPTION = "main";
    final static String OWM_WEATHER_ID = "id";
    private static ArrayList<weather> DaywiseWeather;
    private long dateTime;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private double windDirection;
    private double high;
    private double low;
    private String description;
    private int weatherId;

    public static ArrayList<weather> GetJSONData(JSONObject jsonobj) {

        try {
            JSONArray weatherArray = jsonobj.getJSONArray(OWM_LIST);

            JSONObject cityJson = jsonobj.getJSONObject(OWM_CITY);
            String cityName = cityJson.getString(OWM_CITY_NAME);
            JSONObject coordJSON = cityJson.getJSONObject(OWM_COORD);
            double cityLatitude = coordJSON.getDouble(OWM_COORD_LAT);
            double cityLongitude = coordJSON.getDouble(OWM_COORD_LONG);

            // Get and insert the new weather information into the database
            DaywiseWeather = new ArrayList<weather>(weatherArray.length());

            for (int i = 0; i < weatherArray.length(); i++) {
                //  These are the values that will be collected
                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);
                weather mWeather = weather.fromJson(dayForecast);
                DaywiseWeather.add(mWeather);


            }
        } catch (JSONException EX) {
            EX.printStackTrace();
        }
        return DaywiseWeather;
    }

    public static weather fromJson(JSONObject dayForecast) {
        weather aWeather = new weather();
        try {
            long dateTime;
            double pressure;
            int humidity;
            double windSpeed;
            double windDirection;
            double high;
            double low;
            String description;
            int weatherId;

            dateTime = dayForecast.getLong(OWM_DATETIME);
            pressure = dayForecast.getDouble(OWM_PRESSURE);
            humidity = dayForecast.getInt(OWM_HUMIDITY);
            windSpeed = dayForecast.getDouble(OWM_WINDSPEED);
            windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION);
            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);
            weatherId = weatherObject.getInt(OWM_WEATHER_ID);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            aWeather.setDateTime(dateTime);
            aWeather.setDescription(description);
            aWeather.setHigh(high);
            aWeather.setLow(low);
            aWeather.setHumidity(humidity);
            aWeather.setWindDirection(windDirection);
            aWeather.setPressure(pressure);
            aWeather.setWeatherId(weatherId);
            aWeather.setWindSpeed(windSpeed);

        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
        // Return new object
        return aWeather;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }
}
