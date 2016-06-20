package com.nubnasir.gmail.weatherbd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MissingCity extends Fragment {
	public static final String ARG_OS = "OS";
	int pos;
	String missingCityName = "";
	String missingCountryName = "";
	TextView cityText;
	TextView countryText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.missing_city, null);
		cityText = (TextView) view
				.findViewById(R.id.missingcityname);
		countryText = (TextView) view
				.findViewById(R.id.missingcountryname);
		Button goButton = (Button) view.findViewById(R.id.gotocity);

		goButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				missingCityName = "" + cityText.getText();
				missingCityName = missingCityName.trim();
				missingCountryName = "" + countryText.getText();
				missingCountryName = missingCountryName.trim();
				if (missingCityName.length() > 1
						&& missingCountryName.length() > 3) {
					Intent missWeather = new Intent(getActivity(),
							MissingWeatherReport.class);
					missWeather.putExtra("CITY", missingCityName);
					missWeather.putExtra("COUNTRY", missingCountryName);
					startActivity(missWeather);
				}
			}
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void setArguments(Bundle args) {
		pos = args.getInt(ARG_OS);
	}

}
