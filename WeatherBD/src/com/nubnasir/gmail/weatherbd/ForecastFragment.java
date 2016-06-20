package com.nubnasir.gmail.weatherbd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ForecastFragment extends Fragment {
	public static final String ARG_OS = "OS";
	int pos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.forcast_layout, null);
		Button weather = (Button) view.findViewById(R.id.weatherforcast);

		Typeface custom_font = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "banglafont.ttf");
		weather.setTypeface(custom_font);

		weather.setText("Weather Forecast\nআবহাওয়ার পূর্বাভাস");
		weather.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
						|| connectivityManager.getNetworkInfo(
								ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

					Intent myIntent = new Intent(getActivity(),
							ForecastActivity.class);
					myIntent.putExtra("TITLE", "Weather Forecast");
					myIntent.putExtra("LINK",
							"http://www.bmd.gov.bd/?/p/=Weather-Forecast");
					startActivity(myIntent);
				} else {

					Toast.makeText(
							getActivity(),
							"Internet is not connected. Please check your internet connection.",
							Toast.LENGTH_LONG).show();
				}

			}
		});

		Button dhaka = (Button) view.findViewById(R.id.dhakaneibourhood);
		dhaka.setTypeface(custom_font);
		dhaka.setText("Dhaka and Neighborhood\nঢাকা ও আশপাশ");

		dhaka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
						|| connectivityManager.getNetworkInfo(
								ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
					// we are connected to a network

					Intent myIntent = new Intent(getActivity(),
							ForecastActivity.class);
					myIntent.putExtra("TITLE",
							"Dhaka and Neighborhood");
					myIntent.putExtra("LINK",
							"http://www.bmd.gov.bd/?/p/=WEATHER-FORECAST-FOR-DHAKA-AND-NHOOD");
					startActivity(myIntent);
				} else {

					Toast.makeText(
							getActivity(),
							"Internet is not connected. Please check your internet connection.",
							Toast.LENGTH_LONG).show();
					return;
				}

			}
		});

		Button seabuletine = (Button) view.findViewById(R.id.seaforcast);
		seabuletine.setTypeface(custom_font);
		seabuletine.setText("Sea Bulletin\nসাগর বুলেটিন");

		seabuletine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
						|| connectivityManager.getNetworkInfo(
								ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
					// we are connected to a network

					Intent myIntent = new Intent(getActivity(),
							ForecastActivity.class);
					myIntent.putExtra("TITLE", "Sea Bulletin");
					myIntent.putExtra("LINK",
							"http://www.bmd.gov.bd/?/p/=SEA-BULLETING");
					startActivity(myIntent);
				} else {

					Toast.makeText(
							getActivity(),
							"Internet is not connected. Please check your internet connection.",
							Toast.LENGTH_LONG).show();
					return;
				}

			}
		});

		Button fisherman = (Button) view.findViewById(R.id.fishforcast);
		fisherman.setTypeface(custom_font);
		fisherman.setText("Fisherman Forecast\nজেলে পূর্বাভাস");

		fisherman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
						|| connectivityManager.getNetworkInfo(
								ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
					// we are connected to a network

					Intent myIntent = new Intent(getActivity(),
							ForecastActivity.class);
					myIntent.putExtra("TITLE", "Fisherman Forecast");
					myIntent.putExtra("LINK",
							"http://www.bmd.gov.bd/?/p/=Fishermen-Forecast");
					startActivity(myIntent);
				} else {

					Toast.makeText(
							getActivity(),
							"Internet is not connected. Please check your internet connection.",
							Toast.LENGTH_LONG).show();
					return;
				}

			}
		});

		Button fleet = (Button) view.findViewById(R.id.fleetforcast);
		fleet.setTypeface(custom_font);
		fleet.setText("Fleet Forecast\nদ্রুত পূর্বাভাস");

		fleet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
						|| connectivityManager.getNetworkInfo(
								ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
					// we are connected to a network

					Intent myIntent = new Intent(getActivity(),
							ForecastActivity.class);
					myIntent.putExtra("TITLE",
							"Fleet Forecast");
					myIntent.putExtra("LINK",
							"http://www.bmd.gov.bd/?/p/=Fleet-Forecast-107");
					startActivity(myIntent);
				} else {

					Toast.makeText(
							getActivity(),
							"Internet is not connected. Please check your internet connection.",
							Toast.LENGTH_LONG).show();
					return;
				}

			}
		});

		return view;
	}

}
