package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LiveWarningFramget extends Fragment {

	public String title1;
	public String title2;
	public String description1;
	public String description2;
	public String descriptionL1 = "";
	public String descriptionL2 = "";

	public TextView t1;
	public TextView t2;
	public TextView d1;
	public TextView d2;
	public boolean noInternet = true;
	CustomProgressBar progress;

	public static final String ARG_OS = "OS";
	int pos;
	String sql = "";

	View view;

	Typeface custom_font;
	public DatabaseHelper databaseHelper;

	ArrayList<String> city_list_en = new ArrayList<String>();
	ArrayList<String> city_list_bn = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.live_warning_layout, null);

		t1 = (TextView) view.findViewById(R.id.title1);
		t2 = (TextView) view.findViewById(R.id.title2);
		d1 = (TextView) view.findViewById(R.id.description1);
		d2 = (TextView) view.findViewById(R.id.description2);

		custom_font = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "banglafont.ttf");

		t1.setTypeface(custom_font);
		t2.setTypeface(custom_font);
		d1.setTypeface(custom_font);
		d2.setTypeface(custom_font);

		databaseHelper = new DatabaseHelper(view.getContext());

		try {
			databaseHelper.createDataBase();
			databaseHelper.openDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(view.getContext(), "Error: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		final Cursor c = databaseHelper
				.getResult("SELECT city_name, city_bangla FROM bangladesh");
		c.moveToFirst();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					city_list_en.add(c.getString(c.getColumnIndex("city_name")));
					city_list_bn.add(c.getString(c
							.getColumnIndex("city_bangla")));
				}
			}
		}).start();

		progress = new CustomProgressBar(getActivity());
		progress.setCancelable(true);
		progress.setIndeterminate(true);
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		try {
			progress.show();
		} catch (Exception ex) {
		}

		t1.setText("কালবৈশাখী ঝড় (Kalbaishakhi) সতর্কবাণী");
		t2.setText("দেশাভ্যন্তরস্থ নদীবন্দর (Inland Riverport) সতর্কবাণী");
		new AsyncTaskParser().execute();

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
				//
				// t1.setText("Kalbaishakhi Warning");
				// t2.setText("Inland Riverport Warning");

				t1.setText("কালবৈশাখী ঝড় (Kalbaishakhi) সতর্কবাণী");
				t2.setText(" দেশাভ্যন্তরস্থ নদীবন্দর (Inland Riverport) সতর্কবাণী");

				d1.setText(descriptionL1);
				d2.setText(descriptionL2);
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
			String html = "http://www.bmd.gov.bd/?/p/=Kalbaishakhi-Warning";
			Document doc;
			try {
				doc = Jsoup.connect(html).get();
				Elements link = doc.select("div.fwrap.justify");
				doc = Jsoup.parse(link.html());
				link = doc.select("h2");
				String linkText = link.text(); // "example""

				linkText = linkText.replaceAll("(\\s)+", " ");
				description1 = linkText;

			} catch (IOException ex) {
				Toast.makeText(getActivity(),
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getActivity(),
						MainActivity.class);
				startActivity(homeIntent);
			}

			html = "http://www.bmd.gov.bd/?/p/=Warning";

			try {
				doc = Jsoup.connect(html).get();
				Elements link = doc.select("div.fwrap.justify");
				doc = Jsoup.parse(link.html());
				// link = doc.select("p");
				String linkText = link.text(); // "example""

				linkText = linkText.replaceAll("(\\s)+", " ");
				description2 = linkText;

			} catch (IOException ex) {
				Toast.makeText(getActivity(),
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getActivity(),
						MainActivity.class);
				startActivity(homeIntent);
			}

			String tempSt1 = description1.toLowerCase();

			for (int i = 0; i < city_list_en.size(); i++) {
				String tempCity = String.valueOf(city_list_en.get(i))
						.toLowerCase();
				if (tempSt1.contains(tempCity)) {
					descriptionL1 += city_list_bn.get(i) + " ("
							+ city_list_en.get(i) + ")\n";
				}
			}

			String tempSt2 = description2.toLowerCase();

			for (int i = 0; i < city_list_en.size(); i++) {
				String tempCity = String.valueOf(city_list_en.get(i))
						.toLowerCase();
				if (tempSt2.contains(tempCity)) {
					descriptionL2 += city_list_bn.get(i) + " ("
							+ city_list_en.get(i) + ")\n";
				}
			}

		}

	}

}
