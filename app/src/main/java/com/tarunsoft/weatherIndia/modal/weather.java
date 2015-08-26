package com.tarunsoft.weatherIndia.modal;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Created by tsharma3 on 8/23/2015.
*/
public class weather implements Serializable {
    private String date;
    private String    hourly;
    private String    maxtempC;
    private String    maxtempF;
    private String    mintempC;
    private String    mintempF;
    private String    uvIndex;
    private static WeatherToday currentWeatherData;
    //private ArrayList<HoursData> HourWiseWeather;
   private static ArrayList<weather> DaywiseWeather;

    public static WeatherToday getCurrentWeatherData() {
        return currentWeatherData;
    }

    public static void setCurrentWeatherData(WeatherToday currentWeatherData) {
        weather.currentWeatherData = currentWeatherData;
    }

    public static WeatherToday getCurrentJSON(JSONObject jsonObject , Context ctx) {
WeatherToday aWeatherToday = new WeatherToday();
try {
    // Deserialize json into object fields
    // Check if a cover edition is available
    if (jsonObject != null) {
        JSONObject jsonobj = jsonObject.getJSONObject("data");
        if (jsonobj != null) {
            JSONArray jsonarray = jsonobj.getJSONArray("current_condition");
            System.out.print(jsonarray);
            JSONObject weatherData = jsonarray.getJSONObject(0);


            JSONArray requestArray = jsonobj.getJSONArray("request");
            JSONObject requestData = requestArray.getJSONObject(0);
            // Get the docs json array
            if (weatherData.getString("observation_time") != null) {
                aWeatherToday.setDay("Today");

            }
            if (weatherData.getString("observation_time") != null) {
                if (requestData.getString("query") != null) {
                    aWeatherToday.setDate(requestData.getString("query") + ":\n" + weatherData.getString("observation_time"));
                } else {
                    aWeatherToday.setDate(weatherData.getString("observation_time"));
                }

            }
            if (weatherData.getString("temp_C") != null) {
                aWeatherToday.setHighTemp(weatherData.getString("temp_C") + (char) 0x00B0);
            }
            if (weatherData.getString("temp_F") != null) {
                aWeatherToday.setLowTemp(weatherData.getString("temp_F") + (char) 0x00B0);
            }
            if (weatherData.getString("pressure") != null) {
                aWeatherToday.setPressure("Pressure:  " + weatherData.getString("pressure")+"hPa");
            }
            if (weatherData.getString("windspeedMiles") != null) {
                aWeatherToday.setWind("WindspeedMiles:  " + weatherData.getString("windspeedMiles") + "Km/h NW");
            }
            if (weatherData.getString("humidity") != null) {
                aWeatherToday.setHumidity("Humidity:  " + weatherData.getString("humidity") +"%");
            }

            if (weatherData.getString("visibility") != null) {
                aWeatherToday.setVisibility("Visibility  " + weatherData.getString("visibility"));
            }
            if (weatherData.getString("precipMM") != null) {
                aWeatherToday.setPptmm("precipMM:  " + weatherData.getString("precipMM"));
            }

            if (weatherData.getJSONArray("weatherIconUrl") != null) {
                JSONArray array = weatherData.getJSONArray("weatherIconUrl");
                aWeatherToday.setPoster(array.getJSONObject(0).getString("value"));

            }
            if (weatherData.getJSONArray("weatherDesc") != null) {
                JSONArray array = weatherData.getJSONArray("weatherDesc");
                aWeatherToday.setForecast(array.getJSONObject(0).getString("value"));
            }

        }


    }

}catch(Exception e1){
    e1.printStackTrace();
    return null;
}
// Return new object
return aWeatherToday;
}


public static ArrayList<weather> fromJson(JSONArray jsonArray) {
DaywiseWeather = new ArrayList<weather>(jsonArray.length());
// Process each result in json array, decode and convert to business
// object
for (int i = 0; i < jsonArray.length(); i++) {
    JSONObject weatherJSON = null;
    try {
        weatherJSON = jsonArray.getJSONObject(i);
    } catch (Exception e) {
        e.printStackTrace();
        continue;
    }
    weather mWeather = weather.fromJson(weatherJSON);
    if (mWeather != null) {
        DaywiseWeather.add(mWeather);
    }
}
return DaywiseWeather;
}

public static weather fromJson(JSONObject jsonObject) {
weather aWeather = new weather();
try {
    aWeather.setDate(jsonObject.getString("date"));
    aWeather.setMaxtempC("Max Temp C :" + jsonObject.getString("maxtempC"));
    aWeather.setMintempC("Min Temp C :" + jsonObject.getString("mintempC"));
    aWeather.setMaxtempF("Max Temp F :" +jsonObject.getString("maxtempF"));
    aWeather.setMintempF("Max Temp F :" +jsonObject.getString("mintempF"));
    aWeather.setDate(jsonObject.getString("date"));

    }catch(Exception e1){
        e1.printStackTrace();
        return null;
    }
    // Return new object
    return aWeather;
}




// Return comma separated author list when there is more than one author
private static String getAuthor(final JSONObject jsonObject) {
try {
    final JSONArray authors = jsonObject.getJSONArray("author_name");
    int numAuthors = authors.length();
    final String[] authorStrings = new String[numAuthors];
    for (int i = 0; i < numAuthors; ++i) {
        authorStrings[i] = authors.getString(i);
    }
    return TextUtils.join(", ", authorStrings);
} catch (JSONException e) {
    return "";
}
}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHourly() {
        return hourly;
    }

    public void setHourly(String hourly) {
        this.hourly = hourly;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getMaxtempF() {
        return maxtempF;
    }

    public void setMaxtempF(String maxtempF) {
        this.maxtempF = maxtempF;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getMintempF() {
        return mintempF;
    }

    public void setMintempF(String mintempF) {
        this.mintempF = mintempF;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public static ArrayList<weather> getDaywiseWeather() {
        return DaywiseWeather;
    }

    public static void setDaywiseWeather(ArrayList<weather> daywiseWeather) {
        DaywiseWeather = daywiseWeather;
    }
}
