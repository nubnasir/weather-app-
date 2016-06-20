package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LiveWarningBDFragment extends Fragment {

	public String title1;
	public String title2;
	public String description1;
	public String description2;

	public Button t1;
	public Button t2;
	public Button M1;
	public Button M2;
	public LinearLayout l1;
	public LinearLayout l2;

	public boolean noInternet = true;
	CustomProgressBar progress;
	Typeface custom_font;

	private DatabaseHelper databaseHelper;

	ArrayList<String> warning_list_en1 = new ArrayList<String>();
	ArrayList<String> warning_list_bn1 = new ArrayList<String>();
	ArrayList<String> warning_list_en2 = new ArrayList<String>();
	ArrayList<String> warning_list_bn2 = new ArrayList<String>();

	ArrayList<String> city_list_en = new ArrayList<String>();
	ArrayList<String> city_list_bn = new ArrayList<String>();

	public static final String ARG_OS = "OS";
	int pos;
	String sql = "";
	public int month = 1;

	TextView tv1;
	TextView tv2;

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.live_warning_bd, null);

		t1 = (Button) view.findViewById(R.id.warningtilte1);
		t2 = (Button) view.findViewById(R.id.warningtilte2);
		M1 = (Button) view.findViewById(R.id.warningMAP1);
		M2 = (Button) view.findViewById(R.id.warningMAP2);
		l1 = (LinearLayout) view.findViewById(R.id.warninglayout1);
		l2 = (LinearLayout) view.findViewById(R.id.warninglayout2);
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);

		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date now = new Date();
		month = Integer.parseInt(dateFormat.format(now));

		t1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),
						ViewMessageDialogActivity.class);
				myIntent.putExtra("TITLE",
						"কালবৈশাখী ঝড় (Kalbaishakhi) সতর্কবাণী");
				myIntent.putExtra("MSG", description1);
				startActivity(myIntent);
			}
		});

		t2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),
						ViewMessageDialogActivity.class);
				myIntent.putExtra("TITLE",
						"দেশাভ্যন্তরস্থ নদীবন্দর (Inland Riverport) সতর্কবাণী");
				myIntent.putExtra("MSG", description2);
				startActivity(myIntent);
			}
		});

		M1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),
						WarningMapViewActivity.class);
				if (month >= 3 && month <= 5) {
					myIntent.putExtra("TITLE",
							"কালবৈশাখী ঝড় (Kalbaishakhi) সতর্কবাণী");
				} else if (month >= 6 && month <= 8) {
					myIntent.putExtra("TITLE",
							"ভারী বৃষ্টিপাত (Heavy Rainfall) সতর্কবাণী");
				} else {
					myIntent.putExtra("TITLE", "No warning");
				}
				myIntent.putExtra("BT_TITLE", "ঝড়-বজ্রপাতে করনীয়");
				myIntent.putExtra("DES", description1);
				myIntent.putExtra("TYPE", "1");
				startActivity(myIntent);
			}
		});

		M2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),
						WarningMapViewActivity.class);
				myIntent.putExtra("TITLE",
						"দেশাভ্যন্তরস্থ নদীবন্দর (Inland Riverport) সতর্কবাণী");
				myIntent.putExtra("BT_TITLE", "ঘূর্ণিঝড় ও জলোচ্ছ্বাসে করনীয়");
				myIntent.putExtra("DES", description2);
				myIntent.putExtra("TYPE", "2");
				startActivity(myIntent);
			}
		});

		custom_font = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "banglafont.ttf");
		t1.setTypeface(custom_font);
		t2.setTypeface(custom_font);
		M1.setTypeface(custom_font);
		M2.setTypeface(custom_font);
		tv1.setTypeface(custom_font);
		tv2.setTypeface(custom_font);

		M1.setText("মানচিত্র দেখান");
		M2.setText("মানচিত্র দেখান");

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

				if (month >= 3 && month <= 5) {
					t1.setText("কালবৈশাখী ঝড় (Kalbaishakhi) সতর্কবাণী");
				} else if (month >= 6 && month <= 8) {
					t1.setText("ভারী বৃষ্টিপাত (Heavy Rainfall) সতর্কবাণী");
				} else {
					t1.setText("No warning");
				}
				t2.setText("দেশাভ্যন্তরস্থ নদীবন্দর (Inland Riverport) সতর্কবাণী");
				tv1.setText("যে সকল জেলায় সতর্কতা রয়েছে");
				tv2.setText("যে সকল জেলায় সতর্কতা রয়েছে");

				// d1.setText(description1);
				// d2.setText(description2);
				progress.setProgress(100);
				progress.dismiss();
				noInternet = false;

				for (int i = 0; i < warning_list_en1.size(); i++) {
					TextView cityTV = new TextView(getActivity());
					cityTV.setTypeface(custom_font);
					cityTV.setTextColor(Color.rgb(105, 105, 105));
					cityTV.setMaxLines(2);
					// cityTV.setAlpha((float) 0.6);
					cityTV.setText(warning_list_bn1.get(i) + " ("
							+ warning_list_en1.get(i) + ")");
					cityTV.setPadding(5, 5, 5, 5);
					if (i % 2 == 0) {
						cityTV.setBackgroundColor(Color.rgb(230, 230, 250));
					} else {
						cityTV.setBackgroundColor(Color.rgb(255, 240, 245));
					}
					cityTV.setTextSize(20);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 2, 0, 2);
					params.gravity = Gravity.CENTER;
					cityTV.setLayoutParams(params);
					cityTV.setGravity(Gravity.CENTER);

					l1.addView(cityTV);
				}

				if (warning_list_en1.size() == 0) {
					TextView cityTV = new TextView(getActivity());
					cityTV.setTypeface(custom_font);
					cityTV.setTextColor(Color.rgb(105, 105, 105));
					cityTV.setMaxLines(2);
					// cityTV.setAlpha((float) 0.6);
					cityTV.setText("কোন সতর্কতা পাওয়া যায়নি");
					cityTV.setPadding(5, 5, 5, 5);
					cityTV.setBackgroundColor(Color.rgb(230, 230, 250));

					cityTV.setTextSize(20);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 2, 0, 2);
					params.gravity = Gravity.CENTER;
					cityTV.setLayoutParams(params);
					cityTV.setGravity(Gravity.CENTER);

					l1.addView(cityTV);
				}

				for (int i = 0; i < warning_list_en2.size(); i++) {
					TextView cityTV = new TextView(getActivity());
					cityTV.setTypeface(custom_font);
					cityTV.setTextColor(Color.rgb(105, 105, 105));
					cityTV.setMaxLines(2);
					// cityTV.setAlpha((float) 0.6);
					cityTV.setText(warning_list_bn2.get(i) + " ("
							+ warning_list_en2.get(i) + ")");
					cityTV.setPadding(5, 5, 5, 5);
					if (i % 2 == 0) {
						cityTV.setBackgroundColor(Color.rgb(230, 230, 250));
					} else {
						cityTV.setBackgroundColor(Color.rgb(255, 240, 245));
					}
					cityTV.setTextSize(20);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 2, 0, 2);
					params.gravity = Gravity.CENTER;
					cityTV.setLayoutParams(params);
					cityTV.setGravity(Gravity.CENTER);

					l2.addView(cityTV);
				}

				if (warning_list_en2.size() == 0) {
					TextView cityTV = new TextView(getActivity());
					cityTV.setTypeface(custom_font);
					cityTV.setTextColor(Color.rgb(105, 105, 105));
					cityTV.setMaxLines(2);
					// cityTV.setAlpha((float) 0.6);
					cityTV.setText("কোন সতর্কতা পাওয়া যায়নি");
					cityTV.setPadding(5, 5, 5, 5);
					cityTV.setBackgroundColor(Color.rgb(230, 230, 250));

					cityTV.setTextSize(20);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 2, 0, 2);
					params.gravity = Gravity.CENTER;
					cityTV.setLayoutParams(params);
					cityTV.setGravity(Gravity.CENTER);

					l1.addView(cityTV);
				}

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
			String html = "";
			if (month >= 3 && month <= 5) {
				html = "http://www.bmd.gov.bd/?/p/=Kalbaishakhi-Warning";
			} else if (month >= 6 && month <= 8) {
				html = "http://www.bmd.gov.bd/?/p/=Heavy-Rainfall-Warning-153";
			}else{
				html="";
			}
			Document doc;
			try {
				doc = Jsoup.connect(html).get();
				Elements link = doc.select("div.fwrap.justify");
				doc = Jsoup.parse(link.html());
				//link = doc.select("h2");
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

			html = "http://www.bmd.gov.bd/?/p/=Inland-Riverport-Warning";

			try {
				doc = Jsoup.connect(html).get();
				Elements link = doc.select("div.fwrap.justify");
				doc = Jsoup.parse(link.html());
				link = doc.select("p");
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
				String tempCity = city_list_en.get(i).toLowerCase();
				if (tempCity.equalsIgnoreCase("Coxs Bazar")) {
					tempCity = "cox";
				}
				if (tempSt1.contains(tempCity)) {
					warning_list_en1.add(city_list_en.get(i));
					warning_list_bn1.add(city_list_bn.get(i));
				}
			}

			String tempSt2 = description2.toLowerCase();

			for (int i = 0; i < city_list_en.size(); i++) {
				String tempCity = city_list_en.get(i).toLowerCase();
				if (tempCity.equalsIgnoreCase("Coxs Bazar")) {
					tempCity = "cox";
				}
				if (tempSt2.contains(tempCity)) {
					warning_list_en2.add(city_list_en.get(i));
					warning_list_bn2.add(city_list_bn.get(i));
				}
			}
		}

	}

}
