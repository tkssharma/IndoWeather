package com.tarunsoft.weatherIndia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarunsoft.weatherIndia.modal.weather;
import com.tarunsoft.weatherIndia.utility.Util;

import java.util.Date;

public class ShowWeather extends AppCompatActivity {
    private static final String TAG = ShowWeather.class.getSimpleName();
    private Context mContext;
    private ImageView mIconView;
    private TextView mFriendlyDateView;
    private TextView mDateView;
    private TextView mDescriptionView;
    private TextView mHighTempView;
    private TextView mLowTempView;
    private TextView mHumidityView;
    private TextView mWindView;
    private TextView mPressureView;
    private String mForecastStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        mContext = this;
        mIconView = (ImageView) findViewById(R.id.detail_icon);
        mDateView = (TextView) findViewById(R.id.detail_date_textview);
        mFriendlyDateView = (TextView) findViewById(R.id.detail_day_textview);
        mDescriptionView = (TextView) findViewById(R.id.detail_forecast_textview);
        mHighTempView = (TextView) findViewById(R.id.detail_high_textview);
        mLowTempView = (TextView) findViewById(R.id.detail_low_textview);
        mHumidityView = (TextView) findViewById(R.id.detail_humidity_textview);
        mWindView = (TextView) findViewById(R.id.detail_wind_textview);
        mPressureView = (TextView) findViewById(R.id.detail_pressure_textview);
        weather mWeather = (weather) getIntent().getSerializableExtra(MainActivity.BOOK_DETAIL_KEY);
        LoadData(mWeather);

    }

    private void LoadData(weather mWeather) {

        int weatherId = mWeather.getWeatherId();

        mIconView.setImageResource(Util.getArtResourceForWeatherCondition(weatherId));

        // Read date from cursor and update views for day of week and date
        long date = mWeather.getDateTime();
        String friendlyDateText = Util.getDayName(mContext, (mWeather.getDateTime() * 1000L));
        //String dateText = Util.getFormattedMonthDay(mContext, Util.formatDate(Util.getDbDateString(new Date(mWeather.getDateTime() * 1000L))));
        String dateText = Util.formatDate(Util.getDbDateString(new Date(mWeather.getDateTime() * 1000L)));
        mFriendlyDateView.setText(friendlyDateText);
        mDateView.setText(dateText);

        // Read description from cursor and update view
        String description = mWeather.getDescription();
        mDescriptionView.setText(description);

        mIconView.setContentDescription(description);
        // Read high temperature from cursor and update view
        boolean isMetric = true;

        double high = mWeather.getHigh();
        String highString = Util.formatTemperature(mContext, high, true);
        mHighTempView.setText(highString);

        // Read low temperature from cursor and update view
        double low = mWeather.getLow();
        String lowString = Util.formatTemperature(mContext, low, true);
        mLowTempView.setText(lowString);

        // Read humidity from cursor and update view
        float humidity = mWeather.getHumidity();
        mHumidityView.setText(mContext.getString(R.string.format_humidity, humidity));

        // Read wind speed and direction from cursor and update view
        double windSpeedStr = mWeather.getWindSpeed();
        double windDirStr = mWeather.getWindDirection();
        mWindView.setText(Util.getFormattedWind(mContext, windSpeedStr, windDirStr));

        // Read pressure from cursor and update view
        double pressure = mWeather.getPressure();
        mPressureView.setText(mContext.getString(R.string.format_pressure, pressure));

        // We still need this for the share intent
        mForecastStr = String.format("%s - %s - %s/%s", dateText, description, high, low);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_weather, menu);
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent ot this ShareActionProvider. You can update this at any time,
        // like when the user selects a new piece of data they might like to share
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(TAG, "Share Action Provider is null?");
        }
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
            createShareForecastIntent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        // Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET) prevents
        // the new application for sharing to be part of the
        // activity stack
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mForecastStr + "#IndoWeather App ");
        return shareIntent;
    }
}
