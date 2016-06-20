package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NotifyService extends Service {

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;
	int i = 0;

	public double BANGLADESH_LON = 90.356331;
	public double BANGLADESH_LAT = 23.684994;

	NotifyServiceReceiver notifyServiceReceiver;

	private static final int MY_NOTIFICATION_ID = 1;
	private static final int BD_NOTIFICATION_ID = 2;
	private NotificationManager notificationManager;
	private Notification myNotification;

	private int notification_validity = 0;

	private String ids = "";

	private String msg1 = "";

	private String msg2 = "";
	Timer timer;
	public DatabaseHelper databaseHelper;

	public int settingsMAG;
	public int settingsRANGE;
	public int settingsALL_EQ;

	private String MAG;
	private String TITLE;
	private String LON;
	private String LAT;
	private String TSUNAMI;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		notifyServiceReceiver = new NotifyServiceReceiver();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		timer = new Timer();
		TimerTask task;

		databaseHelper = new DatabaseHelper(getApplicationContext());

		try {
			databaseHelper.createDataBase();
			databaseHelper.openDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new AsyncTaskParser().execute();
			}
		};
		timer.schedule(task, 5000, 20000);
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(notifyServiceReceiver);

		try {
			databaseHelper.close();
		} catch (Exception ex) {
		}
		timer.cancel();
		timer.purge();

		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public class NotifyServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			int rqs = arg1.getIntExtra("RQS", 0);
			if (rqs == RQS_STOP_SERVICE) {
				stopSelf();
			}
		}
	}

	private class AsyncTaskParser extends AsyncTask<Void, Void, Boolean> {

		private String jsonResponseString;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			Boolean result = false;

			jsonResponseString = WebResponse
					.webConnection("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson");
			if (jsonResponseString != null) {

				publishProgress();
				result = true;
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Void... value) {

			try {

				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
						|| connectivityManager.getNetworkInfo(
								ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
					// we are connected to a network
				} else {
					return;
				}

				// Log.e("notify_validity", "" + notification_validity);

				settingsMAG = databaseHelper.getSettings("magnitude");
				settingsRANGE = databaseHelper.getSettings("range");
				settingsALL_EQ = databaseHelper.getSettings("all_eq");

				ids = databaseHelper.getDisaster("last_eq");

				// Log.e("IDS", ids);
				// Log.e("Notification Check", settingsMAG + " " + settingsRANGE
				// + " " + settingsALL_EQ);
				JSONObject wholeJsonObject = new JSONObject(jsonResponseString);

				if (wholeJsonObject != null) {

					JSONArray jsonArray = wholeJsonObject
							.getJSONArray("features");

					if (jsonArray != null && jsonArray.length() > 0) {

						for (int i = 0; i < 1; i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							if (jsonObject != null) {

								JSONObject properties = jsonObject
										.getJSONObject("properties");
								msg1 = "" + properties.getString("place");
								double notifyMag = properties.getDouble("mag");

								TITLE = msg1;
								MAG = "" + notifyMag;

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

								msg2 = _time;

								int tsun = properties.getInt("tsunami");

								JSONObject geometry = jsonObject
										.getJSONObject("geometry");
								JSONArray coordinates = geometry
										.getJSONArray("coordinates");

								double lon = coordinates.getDouble(0);
								double lat = coordinates.getDouble(1);
								String depth = "" + coordinates.getDouble(2);

								if (tsun == 1) {
									TSUNAMI = "Tsunami: YES, Depth: " + depth
											+ "km";
								} else if (tsun == 0) {
									TSUNAMI = "Tsunami: NO, Depth: " + depth
											+ "km";
								} else {
									TSUNAMI = "Tsunami: NO DATA, Depth: "
											+ depth + "km";
								}

								LAT = "" + lat;
								LON = "" + lon;

								String temp = "" + properties.getString("ids");

								// Log.e("TEST", notifyMag + " " + " " + _time);
								if (!temp.equals(ids)) {

									// Log.e("notify id", "not matched");
									if (settingsALL_EQ == 0) {
										// Log.e("ALL_EQ", "0");
										if (DistanceCalculator.distance(
												BANGLADESH_LAT, BANGLADESH_LON,
												lat, lon, 'K') <= settingsRANGE
												&& notifyMag >= settingsMAG) {

											notification_validity = 2;

											// Log.e("notify_validity", "2");
										}
									} else {
										// Log.e("ALL_EQ", "1");
										// Log.e("notify_validity", "1");
										notification_validity = 1;
									}

									if (DistanceCalculator.distance(
											BANGLADESH_LAT, BANGLADESH_LON,
											lat, lon, 'K') <= 425) {
										// Log.e("notify_validity", "3");
										notification_validity = 3;
									}
									databaseHelper.updateDisaster("last_eq",
											temp);
								} else {
									// Log.e("notify_validity", "0");
									notification_validity = 0;
								}

							}

						}
					}
				}

			} catch (Exception e) {
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			// Log.e("notify_validity", "" + notification_validity);
			if (notification_validity == 1 || notification_validity == 2) {

				IntentFilter intentFilter = new IntentFilter();
				intentFilter.addAction(ACTION);
				registerReceiver(notifyServiceReceiver, intentFilter);

				// Send Notification
				notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				myNotification = new Notification(R.drawable.ic_launcher,
						"Earthquak Occurs!", System.currentTimeMillis());
				Context context = getApplicationContext();
				String notificationTitle = msg1;// notification_validity+" "+settingsMAG+" "+settingsRANGE+" "+settingsALL_EQ;
				String notificationText = msg2;
				Intent myIntent = new Intent(context,
						EarthquakeDetailsActivity.class);

				myIntent.putExtra("MAG", MAG);
				myIntent.putExtra("TITLE", TITLE);
				myIntent.putExtra("LON", LON);
				myIntent.putExtra("LAT", LAT);
				myIntent.putExtra("TSUNAMI", TSUNAMI);

				PendingIntent pendingIntent = PendingIntent.getActivity(
						getBaseContext(), 0, myIntent,
						Intent.FLAG_ACTIVITY_NEW_TASK);
				myNotification.defaults |= Notification.DEFAULT_SOUND;
				myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
				myNotification.setLatestEventInfo(context, notificationTitle,
						notificationText, pendingIntent);
				notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
			} else if (notification_validity == 3) {
				notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				myNotification = new Notification(R.drawable.eq_map_bd_icon,
						"Earthquak very Near/Inside in Bangladesh!",
						System.currentTimeMillis());
				Context context = getApplicationContext();
				String notificationTitle = msg1;// notification_validity+" "+settingsMAG+" "+settingsRANGE+" "+settingsALL_EQ;;
				String notificationText = msg2;
				Intent myIntent = new Intent(context,
						EarthquakeDetailsActivity.class);

				myIntent.putExtra("MAG", MAG);
				myIntent.putExtra("TITLE", TITLE);
				myIntent.putExtra("LON", LON);
				myIntent.putExtra("LAT", LAT);
				myIntent.putExtra("TSUNAMI", TSUNAMI);

				PendingIntent pendingIntent = PendingIntent.getActivity(
						getBaseContext(), 0, myIntent,
						Intent.FLAG_ACTIVITY_NEW_TASK);

				myNotification.sound = Uri.parse("android.resource://"
						+ getPackageName() + "/" + R.raw.eq_alert);
				myNotification.defaults = Notification.DEFAULT_LIGHTS
						| Notification.DEFAULT_VIBRATE;

				myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
				myNotification.setLatestEventInfo(context, notificationTitle,
						notificationText, pendingIntent);
				notificationManager.notify(BD_NOTIFICATION_ID, myNotification);
			}

			notification_validity = 0;
		}
	}
}
