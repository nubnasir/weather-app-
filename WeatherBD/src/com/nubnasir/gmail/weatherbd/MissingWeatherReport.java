package com.nubnasir.gmail.weatherbd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MissingWeatherReport extends Activity {

	private String city;
	private String country;
	private TextView city_name;
	private TextView country_name;
	private TextView w_temp;
	private TextView w_description;
	private TextView w_minTemp;
	private TextView w_maxTemp;
	private TextView w_pressure;
	private TextView w_humidity;
	private TextView w_speed;
	private TextView w_deg;
	private TextView w_sunset;
	private TextView w_sunrise;
	private TextView w_time_zone;
	private TextView w_convertedTemp;
	private ImageView w_weathre_image;
	public String city_with_country;

	private double fahrenheit;
	private double celcius;
	// ArrayList<String> jsonArrayList=new ArrayList<String>();

	private Button back_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_layout);

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED
				|| connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
			// we are connected to a network
		} else {

			Toast.makeText(
					MissingWeatherReport.this,
					"Internet is not connected. Please check your internet connection.",
					Toast.LENGTH_LONG).show();
			finish();
		}

		Intent weatherIntent = getIntent();

		city = weatherIntent.getStringExtra("CITY");
		country = weatherIntent.getStringExtra("COUNTRY");

		city_name = (TextView) findViewById(R.id.wcityname);
		country_name = (TextView) findViewById(R.id.wcountryname);

		w_temp = (TextView) findViewById(R.id.temp);
		w_minTemp = (TextView) findViewById(R.id.mintemp);
		w_maxTemp = (TextView) findViewById(R.id.maxtemp);
		w_description = (TextView) findViewById(R.id.description);

		w_pressure = (TextView) findViewById(R.id.pressure);
		w_humidity = (TextView) findViewById(R.id.humidity);

		w_speed = (TextView) findViewById(R.id.windspeed);
		w_deg = (TextView) findViewById(R.id.winddegree);

		w_sunrise = (TextView) findViewById(R.id.sunrise);
		w_sunset = (TextView) findViewById(R.id.sunset);
		w_time_zone = (TextView) findViewById(R.id.timezone);

		w_convertedTemp = (TextView) findViewById(R.id.convertedtemp);

		w_weathre_image = (ImageView) findViewById(R.id.weatherimage);

		back_button = (Button) findViewById(R.id.backbutton);

		back_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		city_name.setText(city);
		country_name.setText(country);

		new AsyncTaskParser().execute();
	}

	private class AsyncTaskParser extends AsyncTask<Void, Void, Boolean> {

		private String jsonResponseString;
		private CustomProgressBar progress;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progress = new CustomProgressBar(MissingWeatherReport.this);
			progress.setCancelable(true);
			progress.setIndeterminate(true);
			progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// progress.setMax(100);
			progress.show();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			Boolean result = false;

			jsonResponseString = WebResponse
					.webConnection("http://api.openweathermap.org/data/2.5/weather?q="
							+ city + "," + country);
			if (jsonResponseString != null) {

				publishProgress();
				result = true;
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Void... value) {

			try {
				JSONObject wholeJsonObject = new JSONObject(jsonResponseString);

				if (wholeJsonObject != null) {
					JSONObject geoNamesArray = wholeJsonObject
							.getJSONObject("main");

					String temp = "" + geoNamesArray.getDouble("temp");

					celcius = Double.parseDouble(temp) - 273.15;
					fahrenheit = (9 / 5) * celcius + 32;
					String my_celc = "" + celcius;

					w_temp.setText(""
							+ my_celc.substring(0, my_celc.indexOf('.') + 2)
							+ "\u00B0C");

					my_celc = "" + fahrenheit;
					w_convertedTemp.setText(temp + "K, "
							+ my_celc.substring(0, my_celc.indexOf('.') + 2)
							+ "\u00B0F");

					double tempmin = geoNamesArray.getDouble("temp_min");
					w_minTemp.setText(("" + (tempmin - 273.15)).substring(0,
							("" + (tempmin - 273.15)).indexOf('.') + 2)
							+ "\u00B0C");
					double tempmax = geoNamesArray.getDouble("temp_max");
					w_maxTemp.setText(("" + (tempmax - 273.15)).substring(0,
							("" + (tempmax - 273.15)).indexOf('.') + 2)
							+ "\u00B0C");

					progress.setProgress(30);

					String pressure = "" + geoNamesArray.getDouble("pressure");
					w_pressure.setText("" + pressure + "KPa");
					String humidity = "" + geoNamesArray.getDouble("humidity");
					w_humidity.setText("" + humidity + "%");

					progress.setProgress(40);

					geoNamesArray = wholeJsonObject.getJSONObject("wind");
					String speed = "" + geoNamesArray.getDouble("speed");
					w_speed.setText("" + speed + "mps");
					String deg = "" + geoNamesArray.getDouble("deg");
					w_deg.setText("" + deg + "\u00B0");

					progress.setProgress(60);

					JSONArray jsonArray = wholeJsonObject
							.getJSONArray("weather");
					w_description.setText("Text is here");
					if (jsonArray != null && jsonArray.length() > 0) {

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							if (jsonObject != null) {
								String weather_main = jsonObject
										.getString("main");

								if (weather_main.equalsIgnoreCase("clear")) {
									w_weathre_image
											.setImageResource(R.drawable.weezle_sun);
								} else if (weather_main
										.equalsIgnoreCase("rain")) {
									w_weathre_image
											.setImageResource(R.drawable.weezle_rain);
								} else if (weather_main
										.equalsIgnoreCase("clouds")) {
									w_weathre_image
											.setImageResource(R.drawable.weezle_max_cloud);
								} else {
									w_weathre_image
											.setImageResource(R.drawable.weezle_cloud_sun);
								}

								weather_main += " - "
										+ jsonObject.getString("description");
								w_description.setText(weather_main);

							}

						}
					}

					progress.setProgress(80);

					try {
						DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
						dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

						geoNamesArray = wholeJsonObject.getJSONObject("sys");
						String sunset = "" + geoNamesArray.getLong("sunset");
						Long timestamp = (Long.parseLong(sunset) + 6 * 3600) * 1000;
						String date = dateFormat.format(timestamp);

						w_sunset.setText(date);

						String sunrise = "" + geoNamesArray.getLong("sunrise");
						timestamp = (Long.parseLong(sunrise) + 6 * 3600) * 1000;
						date = dateFormat.format(timestamp);
						w_sunrise.setText(date);

						w_time_zone.setText("BDT");
					} catch (Exception ex) {
						w_sunrise.setText("---");
						w_sunset.setText("---");
					}

					progress.setProgress(100);
					progress.dismiss();
				}
			} catch (Exception e) {

				Toast.makeText(MissingWeatherReport.this,
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
				finish();
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result == true) {
				// Log.e(getClass().getName(), "Json Parsing Succesfull");
			} else {
				Toast.makeText(MissingWeatherReport.this,
						"Unable to get "+city+", "+country,
						Toast.LENGTH_LONG).show();
				finish();
			}
		}

	}

}
