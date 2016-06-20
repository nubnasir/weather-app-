package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class BangladeshBMDFragment extends Fragment {
	private BMDListAdapter bmdAdapter;
	ArrayList<String> city_name = new ArrayList<String>();
	ArrayList<String> minTemp = new ArrayList<String>();
	ArrayList<String> maxTemp = new ArrayList<String>();
	ArrayList<String> sunSet = new ArrayList<String>();
	ArrayList<String> sunRise = new ArrayList<String>();
	CustomProgressBar progress;
	public boolean noInternet = true;

	public static final String ARG_OS = "OS";
	int pos;
	String sql = "";

	ListView list;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bangladesh_bmd, null);

		list = (ListView) view.findViewById(R.id.citylistbmd);
		list.setFocusable(false);

		progress = new CustomProgressBar(getActivity());
		progress.setCancelable(true);
		progress.setIndeterminate(true);
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (noInternet) {

					ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					if (connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
							|| connectivityManager.getNetworkInfo(
									ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
						// we are connected to a network
					} else {

						Toast.makeText(
								getActivity(),
								"Internet is not connected. Please check your internet connection.",
								Toast.LENGTH_LONG).show();
						noInternet = false;

						Intent homeIntent = new Intent(getActivity(),
								MainActivity.class);
						startActivity(homeIntent);
					}

				}

			}
		}).start();

		new AsyncTaskParser().execute();

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

	private class AsyncTaskParser extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			jsupDataRecevier();
			publishProgress();
			return true;
		}

		@Override
		protected void onProgressUpdate(Void... value) {
			try {

				bmdAdapter = new BMDListAdapter(getActivity(), city_name,
						minTemp, maxTemp, sunSet, sunRise);

				list.setAdapter(bmdAdapter);

				progress.setProgress(100);
				progress.dismiss();
				noInternet = false;
			} catch (Exception ex) {
				Toast.makeText(getActivity(),
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getActivity(),
						MainActivity.class);
				startActivity(homeIntent);
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

		}

		public void jsupDataRecevier() {
			String html = "http://www.bmd.gov.bd/?/home/";
			Document doc;
			try {
				doc = Jsoup.connect(html).get();
				Elements link = doc.select("div").attr("class", "forecastbox ");
				String linkText = link.text();

				linkText = linkText.substring(0,
						linkText.indexOf("| Bangladesh"));

				linkText = linkText.replace("| ", "--");
				String modifiedText[] = linkText.split("--");

				int j;
				boolean flag = true; // will determine when the sort is finished
				String temp;

				while (flag) {
					flag = false;
					for (j = 0; j < modifiedText.length - 1; j++) {
						if (modifiedText[j]
								.compareToIgnoreCase(modifiedText[j + 1]) > 0) { // ascending
																					// sort
							temp = modifiedText[j];
							modifiedText[j] = modifiedText[j + 1]; // swapping
							modifiedText[j + 1] = temp;
							flag = true;
						}
					}
				}

				Scanner inp;
				for (int i = 0; i < modifiedText.length; i++) {

					inp = new Scanner(modifiedText[i].trim());
					String distric = modifiedText[i].substring(0,
							modifiedText[i].indexOf(':'));
					inp = new Scanner(modifiedText[i].substring(
							modifiedText[i].indexOf(":"),
							modifiedText[i].length()));
					inp.next();
					String maxTemperature = inp.next();
					maxTemperature = inp.next().substring(0, 4);

					String minTemperature = inp.next() + inp.next();
					minTemperature = inp.next().substring(0, 4);

					inp.next();
					inp.next();
					inp.next();

					String sunrise = inp.next().replace('-', ':') + " "
							+ inp.next();

					inp.next();
					inp.next();
					inp.next();
					String sunset = inp.next().replace('-', ':') + " "
							+ inp.next();

					if (sunset.contains("..") && sunrise.contains("..")
							&& maxTemperature.contains("..")
							&& minTemperature.contains("..")) {
					} else {

						city_name.add(distric);
						maxTemp.add(maxTemperature);
						minTemp.add(minTemperature);
						sunRise.add(sunrise);
						sunSet.add(sunset);

					}
				}

			} catch (IOException ex) {
				Toast.makeText(getActivity(), "Sorry try again later",
						Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getActivity(),
						MainActivity.class);
				startActivity(homeIntent);
			}
		}

	}

}
