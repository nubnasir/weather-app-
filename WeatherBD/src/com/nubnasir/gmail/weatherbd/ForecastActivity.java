package com.nubnasir.gmail.weatherbd;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForecastActivity extends Activity {

	private String title_string;
	private String link_string;
	private String desc_english_string="";
	private TextView title;
	private TextView desc_english;

	private Button back_button;
	public boolean noInternet = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forcast_result_layout);

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED
				|| connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
			// we are connected to a network
		} else {

			Toast.makeText(
					ForecastActivity.this,
					"Internet is not connected. Please check your internet connection.",
					Toast.LENGTH_LONG).show();
			finish();
		}

		Intent weatherIntent = getIntent();
		title_string = weatherIntent.getStringExtra("TITLE");
		link_string = weatherIntent.getStringExtra("LINK");

		title = (TextView) findViewById(R.id.forecasttitle);
		desc_english = (TextView) findViewById(R.id.forecastdesc);
		back_button = (Button) findViewById(R.id.backbuttonforecast);

		title.setText(title_string);

		back_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});


		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (noInternet) {

					ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					if (connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
							|| connectivityManager.getNetworkInfo(
									ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
						// we are connected to a network
					} else {

						Toast.makeText(
								getApplicationContext(),
								"Internet is not connected. Please check your internet connection.",
								Toast.LENGTH_LONG).show();
						noInternet = false;

						Intent homeIntent = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(homeIntent);
					}

				}

			}
		}).start();


		new AsyncTaskParser().execute();
	}

	private class AsyncTaskParser extends AsyncTask<Void, Void, Boolean> {

		private CustomProgressBar progress;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progress = new CustomProgressBar(ForecastActivity.this);
			progress.setCancelable(true);
			progress.setIndeterminate(true);
			progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setMax(100);
			progress.show();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

			jsupDataRecevier();
			publishProgress();

			return true;
		}

		@Override
		protected void onProgressUpdate(Void... value) {

			desc_english.setText(desc_english_string);
			
			progress.setProgress(100);
			progress.dismiss();

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result == true) {
				// Log.e(getClass().getName(), "Json Parsing Succesfull");
			} else {
				Toast.makeText(ForecastActivity.this,
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
				finish();
			}
		}

		public void jsupDataRecevier() {
			String html = link_string;
			Document doc;
			try {
				doc = Jsoup.connect(html).get();
				Elements link = doc.select("div.fwrap.justify");
				doc = Jsoup.parse(link.html());
				link = doc.select("p");
				String linkText = link.text(); // "example""

				linkText = linkText.replaceAll("(\\s)+", " ");
				linkText = linkText.replace(". ", ".\n\n");
				desc_english_string = linkText;

			} catch (IOException ex) {
				Toast.makeText(getApplicationContext(),
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(homeIntent);
			}
		}

	}

}
