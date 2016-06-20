package com.nubnasir.gmail.weatherbd;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EarthQuakesListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> mag;
	private final ArrayList<String> title;
	private final ArrayList<String> time;
	private final ArrayList<String> tsunami;

	public EarthQuakesListAdapter(Activity context, ArrayList<String> mag,
			ArrayList<String> title, ArrayList<String> time,ArrayList<String> tsunami) {
		super(context, R.layout.earth_quaks_list, mag);
		this.context = context;
		this.mag = mag;
		this.title = title;
		this.time = time;
		this.tsunami=tsunami;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.earth_quaks_list, null, true);
		

		Typeface custom_font = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "magnitude.ttf");
		TextView magview = (TextView) rowView.findViewById(R.id.magnetude);
		TextView titleview = (TextView) rowView.findViewById(R.id.ertitle);
		TextView timeview = (TextView) rowView.findViewById(R.id.ertime);
		TextView tsunamiview = (TextView) rowView.findViewById(R.id.ertsunami);

		magview.setTypeface(custom_font);
		magview.setText(mag.get(position));
		titleview.setText(title.get(position));
		timeview.setText(time.get(position));
		tsunamiview.setText(tsunami.get(position));
		

		double magnitude = Double.parseDouble(mag.get(position));

		if (magnitude < 2) {
			magview.setBackgroundColor(Color.rgb(0, 205, 0));
		} else if (magnitude >= 2 && magnitude < 4) {
			magview.setBackgroundColor(Color.rgb(205, 205, 0));
		} else if (magnitude >= 4 && magnitude < 5) {
			magview.setBackgroundColor(Color.rgb(255, 106, 106));
		} else if (magnitude >= 5 && magnitude < 6) {
			magview.setBackgroundColor(Color.rgb(238, 44, 44));
		} else if (magnitude >= 6 && magnitude < 7) {
			magview.setBackgroundColor(Color.rgb(255, 60, 60));
		} else if (magnitude >= 7 && magnitude < 8) {
			magview.setBackgroundColor(Color.rgb(255, 60, 0));
		} else if (magnitude >= 8) {
			magview.setBackgroundColor(Color.rgb(255, 0, 0));
		}

		return rowView;
	}
}