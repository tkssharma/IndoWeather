package com.tarunsoft.weatherIndia.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.tarunsoft.weatherIndia.R;
import com.tarunsoft.weatherIndia.modal.weather;

import java.util.ArrayList;

/**
 * Created by tsharma3 on 8/23/2015.
 */
public class WeatherAdapter extends ArrayAdapter<weather> {

    public WeatherAdapter(Context context, ArrayList<weather> aWeather) {
        super(context, 0, aWeather);
    }

    private static class ViewHolder {
        public ImageView poster;
        public TextView mintemp;
        public TextView maxtemp;
        public TextView date;
        public TextView uvindex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final weather mWeather = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          convertView = inflater.inflate(R.layout.item_weather, parent, false);
            viewHolder.poster = (ImageView)convertView.findViewById(R.id.posterMain);
            viewHolder.mintemp = (TextView)convertView.findViewById(R.id.minTemp);
            viewHolder.maxtemp = (TextView)convertView.findViewById(R.id.maxTemp);
            viewHolder.date = (TextView)convertView.findViewById(R.id.dayToday);
            viewHolder.uvindex = (TextView)convertView.findViewById(R.id.uvIndex);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.mintemp.setText(mWeather.getMintempC());
        viewHolder.maxtemp.setText(mWeather.getMaxtempC());

        viewHolder.date.setText(mWeather.getDate());
        viewHolder.uvindex.setText(mWeather.getUvIndex());
        Picasso.with(getContext()).load(Uri.parse("http://cdn.worldweatheronline.net//images//wsymbols01_png_64//wsymbol_0001_sunny.png")).error(R.drawable.ic_clear).into(viewHolder.poster);
        // Return the completed view to render on screen
        return convertView;
    }
}
