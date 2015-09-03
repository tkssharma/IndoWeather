package com.tarunsoft.weatherIndia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.tarunsoft.weatherIndia.adaptor.WeatherAdapter;
import com.tarunsoft.weatherIndia.client.WeatherClient;
import com.tarunsoft.weatherIndia.modal.weather;
import com.tarunsoft.weatherIndia.utility.AlertDialogManager;
import com.tarunsoft.weatherIndia.utility.Util;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String BOOK_DETAIL_KEY = "book";
    final AlertDialogManager alert = new AlertDialogManager();
    WeatherClient client;
    ProgressBar progress;
    private ImageView Poster;
    private TextView day;
    private TextView date;
    private TextView highTemp;
    private TextView lowTemp;
    private TextView Forecast;
    private TextView Humidity;
    private TextView Wind;
    private TextView Pressure;
    private Button weatherBtn;
    private TextView Visibility;
    private TextView Pptmm;
    private ArrayList<weather> weatherData;
    private Context mContext;
    private String mForecastStr;
    private ShareActionProvider mShareActionProvider;
    private WeatherAdapter mForecastAdapter;
    private ListView mWeatherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        progress = (ProgressBar) findViewById(R.id.progressView);
        weatherData = new ArrayList<weather>();
        mWeatherView = (ListView) findViewById(R.id.listview_forecast);
        mForecastAdapter = new WeatherAdapter(mContext, weatherData);
        mWeatherView.setAdapter(mForecastAdapter);
        setupRowSelectedListener();

        fetchWeatherData(new Util().getPreferredLocation(mContext));

    }

    public void setupRowSelectedListener() {
        mWeatherView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(MainActivity.this, ShowWeather.class);
                intent.putExtra(BOOK_DETAIL_KEY, mForecastAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void fetchWeatherData(String query) {
        progress.setVisibility(ProgressBar.VISIBLE);

        WeatherClient.get(query, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.out.println(response.toString());

                try {
                    if (response != null) {
                        ArrayList<weather> dayWiseData = weather.GetJSONData(response);
                          if(weatherData != null ) {weatherData.clear(); }
                        // Load model objects into the adapter
                        for (weather aWeather : dayWiseData) {
                            weatherData.add(aWeather); // add book through the adapter
                            }
                        }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    }

                progress.setVisibility(ProgressBar.GONE);
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
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                // fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                MainActivity.this.setTitle(query);
                fetchWeatherData(query);
                fetchWeatherData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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
        if (id == R.id.action_share) {
            // Get the provider and hold onto it to set/change the share intent.
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            // If onLoadFinished happens before this, we can go ahead and set the share intent now.
            if (mForecastStr != null) {
                mShareActionProvider.setShareIntent(createShareIntent());
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + "#WORLDWIDE WEATHER");
        return shareIntent;
    }
}
