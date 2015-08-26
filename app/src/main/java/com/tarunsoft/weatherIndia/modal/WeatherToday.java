package com.tarunsoft.weatherIndia.modal;



/**
 * Created by tsharma3 on 8/25/2015.
 */
public class WeatherToday {
    private String Poster;
    private String day;
    private String date;
    private String highTemp;
    private String lowTemp;
    private String Forecast;
    private String Humidity;
    private String Wind;
    private String Pressure;

    public String getVisibility() {
        return Visibility;
    }

    public void setVisibility(String visibility) {
        Visibility = visibility;
    }

    public String getPptmm() {
        return Pptmm;
    }

    public void setPptmm(String pptmm) {
        Pptmm = pptmm;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getForecast() {
        return Forecast;
    }

    public void setForecast(String forecast) {
        Forecast = forecast;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    private String Visibility;
    private String Pptmm;


    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }
}
