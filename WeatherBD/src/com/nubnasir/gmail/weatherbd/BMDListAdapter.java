package com.nubnasir.gmail.weatherbd;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BMDListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> city;
	private final ArrayList<String> minTemp;
	private final ArrayList<String> maxTemp;
	private final ArrayList<String> sunSet;
	private final ArrayList<String> sunRise;

	public BMDListAdapter(Activity context, ArrayList<String> city, ArrayList<String> min_temp,
			ArrayList<String> max_temp, ArrayList<String> sun_set, ArrayList<String> sun_rise) {
		super(context, R.layout.list_bmd, city);
		this.context = context;
		this.city = city;
		this.minTemp = min_temp;
		this.maxTemp = max_temp;
		this.sunSet = sun_set;
		this.sunRise = sun_rise;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_bmd, null, true);
		TextView city_name = (TextView) rowView.findViewById(R.id.citybmd);
		TextView mintemp = (TextView) rowView.findViewById(R.id.mintempbmd);
		TextView maxtemp = (TextView) rowView.findViewById(R.id.maxtempbmd);
		TextView sunset = (TextView) rowView.findViewById(R.id.sunsetbmd);
		TextView sunrise = (TextView) rowView.findViewById(R.id.sunrisebmd);
		city_name.setText(city.get(position));
		mintemp.setText(minTemp.get(position));
		maxtemp.setText(maxTemp.get(position));
		sunset.setText(sunSet.get(position));
		sunrise.setText(sunRise.get(position));
		return rowView;
	}
}