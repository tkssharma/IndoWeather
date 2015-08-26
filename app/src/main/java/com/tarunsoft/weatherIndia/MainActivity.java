package com.tarunsoft.weatherIndia;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.apache.http.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.tarunsoft.weatherIndia.utility.AlertDialogManager;
import com.tarunsoft.weatherIndia.client.WeatherClient;
import com.tarunsoft.weatherIndia.modal.WeatherToday;
import com.tarunsoft.weatherIndia.modal.weather;
import com.tarunsoft.weatherIndia.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

WeatherClient client;
ProgressBar progress;
final AlertDialogManager alert = new AlertDialogManager();
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
private Context mContext;
private String mForecastStr;
private ShareActionProvider mShareActionProvider;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
mContext = this;
progress = (ProgressBar) findViewById(R.id.progress);
day = (TextView) findViewById(R.id.detail_day_textview);
date = (TextView) findViewById(R.id.detail_date_textview);
highTemp = (TextView) findViewById(R.id.detail_high_textview);
lowTemp = (TextView) findViewById(R.id.detail_low_textview);
Poster = (ImageView) findViewById(R.id.detail_icon);
Forecast = (TextView) findViewById(R.id.detail_forecast_textview);
Humidity = (TextView) findViewById(R.id.detail_humidity_textview);
Wind = (TextView) findViewById(R.id.detail_wind_textview);
Pressure = (TextView) findViewById(R.id.detail_pressure_textview);
Visibility = (TextView) findViewById(R.id.detail_visibility_textview);
Pptmm = (TextView) findViewById(R.id.detail_pptmm_textview);
fetchWeatherData(new Util().getSavedLocation(mContext));
//
weatherBtn = (Button) findViewById(R.id.showweather_button);
    weatherBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, showWeather.class));

        }
    });
}



private void fetchWeatherData(String query) {
    progress.setVisibility(ProgressBar.VISIBLE);

    if(weather.getCurrentWeatherData() == null) {

        WeatherClient.get(query, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.out.println(response.toString());


                try {
                    if (response != null) {
                        progress.setVisibility(ProgressBar.GONE);

                        WeatherToday curentWeather = weather.getCurrentJSON(response, mContext);
                        day.setText(curentWeather.getDay());
                        date.setText(curentWeather.getDate());
                        highTemp.setText(curentWeather.getHighTemp());
                        lowTemp.setText(curentWeather.getLowTemp());

                        Forecast.setText(curentWeather.getForecast());
                        Humidity.setText(curentWeather.getHumidity());
                        Wind.setText(curentWeather.getWind());
                        Pressure.setText(curentWeather.getPressure());
                        Visibility.setText(curentWeather.getVisibility());
                        Pptmm.setText(curentWeather.getPptmm());
                        Picasso.with(mContext).load(curentWeather.getPoster()).into(Poster);
                        progress.setVisibility(ProgressBar.GONE);
                        // seralize other json data to java obj
                        if (response != null) {
                            JSONObject jsonobj = response.getJSONObject("data");
                            if (jsonobj != null) {
                                JSONArray jsonarray = jsonobj.getJSONArray("weather");
                                if (jsonarray != null) {
                                    weather.fromJson(jsonarray);
                                }
                            }
                        }
                    }
                } catch (JSONException e1) {
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
        });
    }
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
if (id == R.id.action_refresh) {
    return true;
}

return super.onOptionsItemSelected(item);
}

private Intent createShareIntent(){
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + "#WORLDWIDE WEATHER");
    return shareIntent;
}
}
