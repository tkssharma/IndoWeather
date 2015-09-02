package com.tarunsoft.weatherIndia.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarunsoft.weatherIndia.R;
import com.tarunsoft.weatherIndia.modal.weather;
import com.tarunsoft.weatherIndia.utility.Util;

import java.util.ArrayList;

/**
 * Created by tsharma3 on 8/23/2015.
 */
public class WeatherAdapter extends ArrayAdapter<weather> {

    public final String TAG = WeatherAdapter.class.getSimpleName();
    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;
    private Context mContext;
    private boolean mUseTodayLayout;

    public WeatherAdapter(Context context, ArrayList<weather> aWeather) {
        super(context, 0, aWeather);
        this.mContext = context;
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        // There are two different layouts
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final weather mWeather = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int viewType = getItemViewType(position);
            int layoutId;

            switch (viewType) {
                case VIEW_TYPE_TODAY:
                    layoutId = R.layout.list_item_forecast_today;
                    break;
                default:
                    layoutId = R.layout.list_item_forecast;
            }
            convertView = inflater.inflate(layoutId, parent, false);

            viewHolder.iconView = (ImageView) convertView.findViewById(R.id.list_item_icon);
            viewHolder.dateView = (TextView) convertView.findViewById(R.id.list_item_date_textview);
            viewHolder.descriptionView = (TextView) convertView.findViewById(R.id.list_item_forecast_textview);
            viewHolder.highTempView = (TextView) convertView.findViewById(R.id.list_item_high_textview);
            viewHolder.lowTempView = (TextView) convertView.findViewById(R.id.list_item_low_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        String dateString = mWeather.getDateTime() + "";

        // Find TextView and set formatted date on it
        viewHolder.dateView.setText(Util.formatDate(dateString).toString());

        // Read weather forecast from cursor
        String description = mWeather.getDescription();
        // Find TextView and set weather forecast on it
        viewHolder.descriptionView.setText(description);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Util.isMetric(mContext);

        // Read high temperature from cursor
        Double high = mWeather.getHigh();
        viewHolder.highTempView.setText(Util.formatTemperature(mContext, high, isMetric));

        // Read low temperature from cursor
        Double low = mWeather.getLow();

        int weatherId = mWeather.getWeatherId();
        // Use placeholder image for now
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_TODAY:
                // Get weather icon
                Log.d(TAG, "get weather icon");
                viewHolder.iconView.setImageResource(Util.getArtResourceForWeatherCondition(weatherId));
                break;
            case VIEW_TYPE_FUTURE_DAY:
                viewHolder.iconView.setImageResource(Util.getIconResourceForWeatherCondition(weatherId));
                break;
        }
        return convertView;
    }

    private static class ViewHolder {
        public ImageView iconView;
        public TextView dateView;
        public TextView descriptionView;
        public TextView highTempView;
        public TextView lowTempView;
    }
}
