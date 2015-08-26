package com.tarunsoft.weatherIndia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tarunsoft.weatherIndia.adapter.WeatherAdapter;
import com.tarunsoft.weatherIndia.client.WeatherClient;
import com.tarunsoft.weatherIndia.modal.weather;
import com.tarunsoft.weatherIndia.utility.Util;

import java.util.ArrayList;

public class showWeather extends AppCompatActivity {

    private ListView weatherView;
    private WeatherAdapter weatherAdapter;
    WeatherClient client;
    private Context mContext;
    ProgressBar progress;
    public static final String BOOK_DETAIL_KEY = "book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        mContext = this;
        progress = (ProgressBar) findViewById(R.id.progressView);
        weatherView = (ListView) findViewById(R.id.WeatherLst);
        ArrayList<weather> aWeather = new ArrayList<weather>();
        weatherAdapter = new WeatherAdapter(this, aWeather);
        weatherView.setAdapter(weatherAdapter);
        fetchWeatherListData(new Util().getSavedLocation(mContext));
        //setupBookSelectedListener();
    }

    private void fetchWeatherListData(String query) {
        progress.setVisibility(ProgressBar.VISIBLE);

        ArrayList<weather>  dayWiseData =  weather.getDaywiseWeather();
        weatherAdapter.clear();
        // Load model objects into the adapter
        for (weather aWeather : dayWiseData) {
            weatherAdapter.add(aWeather); // add book through the adapter
        }
        weatherAdapter.notifyDataSetChanged();
        progress.setVisibility(ProgressBar.GONE);
        /*WeatherClient.get(query, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.out.println(response.toString());
                try {

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Pull out the first event on the public timeline
                // Do something with the response
                System.out.println(response.toString());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;

        }

        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
