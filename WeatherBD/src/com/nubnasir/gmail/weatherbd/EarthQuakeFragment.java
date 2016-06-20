package com.nubnasir.gmail.weatherbd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class EarthQuakeFragment extends Fragment {
	public static final String ARG_OS = "OS";
	int pos;

	ArrayList<String> magnitude = new ArrayList<String>();
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> time = new ArrayList<String>();
	ArrayList<String> tsunami = new ArrayList<String>();
	ArrayList<String> latList = new ArrayList<String>();
	ArrayList<String> lonList = new ArrayList<String>();
	String ids = "";
	View view;
	ListView earth_list;

	EarthQuakesListAdapter customAdapter;

	String url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson";
	LinearLayout setting_layout;

	RadioButton radio0, radio1, radio2, radio3;

	AsyncTaskParser myTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.earth_quake_layout, null);

		earth_list = (ListView) view.findViewById(R.id.earthList);
		radio0 = (RadioButton) view.findViewById(R.id.eqradio0);
		radio1 = (RadioButton) view.findViewById(R.id.eqradio1);
		radio2 = (RadioButton) view.findViewById(R.id.eqradio2);
		radio3 = (RadioButton) view.findViewById(R.id.eqradio3);
		setting_layout = (LinearLayout) view.findViewById(R.id.settingEqlist);

		radio0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				earth_list.setVisibility(View.GONE);

				myTask.cancel(true);

				url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_hour.geojson";
				myTask = new AsyncTaskParser();
				myTask.execute();
			}
		});

		radio1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				earth_list.setVisibility(View.GONE);
				myTask.cancel(true);

				url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson";
				myTask = new AsyncTaskParser();
				myTask.execute();
			}
		});

		radio2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				earth_list.setVisibility(View.GONE);

				myTask.cancel(true);

				url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.geojson";
				myTask = new AsyncTaskParser();
				myTask.execute();
			}
		});

		radio3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				earth_list.setVisibility(View.GONE);

				myTask.cancel(true);

				url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_month.geojson";
				myTask = new AsyncTaskParser();
				myTask.execute();
			}
		});

		myTask = new AsyncTaskParser();
		myTask.execute();

		earth_list
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View myview,
							int position, long id) {

						Intent eqDetails = new Intent(getActivity(),
								EarthquakeDetailsActivity.class);
						eqDetails.putExtra("TITLE", title.get(position));
						eqDetails.putExtra("MAG", magnitude.get(position));
						eqDetails.putExtra("LON", lonList.get(position));
						eqDetails.putExtra("LAT", latList.get(position));
						eqDetails.putExtra("TSUNAMI", tsunami.get(position));

						startActivity(eqDetails);
					}
				});

		return view;
	}

	private class AsyncTaskParser extends AsyncTask<Void, Void, Boolean> {

		private String jsonResponseString;

		// private CustomProgressBar progress;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			magnitude.clear();
			title.clear();
			time.clear();
			tsunami.clear();
			latList.clear();
			lonList.clear();

			// progress = new CustomProgressBar(getActivity());
			// progress.setCancelable(true);
			// progress.setIndeterminate(true);
			// progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// // progress.setMax(100);
			// progress.show();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			Boolean result = false;

			jsonResponseString = WebResponse.webConnection(url);
			if (jsonResponseString != null) {
				if (isCancelled()) {
					return false;
				}
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

					JSONArray jsonArray = wholeJsonObject
							.getJSONArray("features");

					if (jsonArray != null && jsonArray.length() > 0) {

						for (int i = 0; i < jsonArray.length(); i++) {

							if (isCancelled()) {
								break;
							}
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							if (jsonObject != null) {

								JSONObject properties = jsonObject
										.getJSONObject("properties");
								magnitude.add("" + properties.getDouble("mag"));
								title.add("" + properties.getString("place"));
								String startTime = ""
										+ properties.getLong("time");

								DateFormat dateFormat = new SimpleDateFormat(
										"hh:mm a");
								dateFormat.setTimeZone(TimeZone
										.getTimeZone("UTC"));
								Long timestamp = Long.parseLong(startTime);
								String date = dateFormat.format(timestamp);

								String _time = date + " UTC, ";

								timestamp = Long.parseLong(startTime) + 6 * 3600 * 1000;
								date = dateFormat.format(timestamp);

								_time += date + " BDT";

								time.add(_time);

								int tsun = properties.getInt("tsunami");

								// if (once) {
								ids = "" + properties.getString("ids");

								JSONObject geometry = jsonObject
										.getJSONObject("geometry");
								JSONArray coordinates = geometry
										.getJSONArray("coordinates");

								String lon = "" + coordinates.getDouble(0);
								String lat = "" + coordinates.getDouble(1);
								String depth = "" + coordinates.getDouble(2);

								lonList.add(lon);
								latList.add(lat);

								// Toast.makeText(getActivity(),
								// lan+", "+lat+", "+depth,
								// Toast.LENGTH_LONG).show();

								if (tsun == 1) {
									tsunami.add("Tsunami: YES, Depth: " + depth
											+ "km");
								} else if (tsun == 0) {
									tsunami.add("Tsunami: NO, Depth: " + depth
											+ "km");
								} else {
									tsunami.add("Tsunami: NO DATA, Depth: "
											+ depth + "km");
								}
								// }

							}

						}
					}
				}

			} catch (Exception e) {

				Toast.makeText(getActivity(),
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (isCancelled()) {
				return;
			}
			earth_list.setVisibility(View.VISIBLE);

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					customAdapter = new EarthQuakesListAdapter(getActivity(),
							magnitude, title, time, tsunami);

					earth_list.setAdapter(customAdapter);
				}
			});

			// progress.setProgress(100);
			// progress.dismiss();

			if (result == true) {
				// Log.e(getClass().getName(), "Json Parsing Succesfull");
			} else {
				Toast.makeText(getActivity(),
						"Sorry unable to get data, Try again later",
						Toast.LENGTH_LONG).show();
			}
		}
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
