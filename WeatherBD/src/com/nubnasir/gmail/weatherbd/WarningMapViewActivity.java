package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WarningMapViewActivity extends Activity {

	private GoogleMap googleMap;

	public ArrayList<Double> LAT = new ArrayList<Double>();
	public ArrayList<Double> LON = new ArrayList<Double>();
	public ArrayList<String> ENG_NAME = new ArrayList<String>();
	public ArrayList<String> BAN_NAME = new ArrayList<String>();

	public ArrayList<Double> AC_LAT = new ArrayList<Double>();
	public ArrayList<Double> AC_LON = new ArrayList<Double>();
	public ArrayList<String> AC_ENG_NAME = new ArrayList<String>();
	public ArrayList<String> AC_BAN_NAME = new ArrayList<String>();

	public ArrayList<Marker> AC_MARKER = new ArrayList<Marker>();

	String TITLE;
	String BT_TITLE;
	String DES;
	int TYPE;
	TextView title;
	Button whattodo, animate;
	Button exit;
	Marker myRealPos;
	int animate_count = 0;
	int map_view_counter = 0;
	Typeface custom_font;

	private DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.warning_map_view);

		Intent myIntent = getIntent();
		TITLE = myIntent.getStringExtra("TITLE");
		BT_TITLE = myIntent.getStringExtra("BT_TITLE");
		DES = myIntent.getStringExtra("DES");
		TYPE = Integer.parseInt(myIntent.getStringExtra("TYPE"));

		title = (TextView) findViewById(R.id.eqtitle);
		whattodo = (Button) findViewById(R.id.whattodo);
		animate = (Button) findViewById(R.id.animateBT);
		exit = (Button) findViewById(R.id.exitmap);

		custom_font = Typeface.createFromAsset(getAssets(), "banglafont.ttf");

		title.setTypeface(custom_font);
		title.setText(TITLE);

		whattodo.setTypeface(custom_font);
		whattodo.setText(BT_TITLE);

		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (map_view_counter == 0) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				} else if (map_view_counter == 1) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				} else if (map_view_counter == 2) {
					googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				} else {
					googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					map_view_counter = -1;
				}
				map_view_counter++;
			}
		});

		whattodo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (TYPE == 1) {
					Intent whatIntent = new Intent(getApplicationContext(),
							WhatToDoinKalboishakhi.class);
					startActivity(whatIntent);
				} else {
					Intent whatIntent = new Intent(getApplicationContext(),
							WhatToDoInCyclone.class);
					startActivity(whatIntent);
				}
			}
		});

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		animate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				animate_count = 0;
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(23.684994, 90.356331), 6), 5000,
						MyCancelableCallback);

			}
		});

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

		final Cursor c = databaseHelper
				.getResult("SELECT city_name, city_bangla, lat, lon FROM bangladesh");
		c.moveToFirst();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			ENG_NAME.add(c.getString(c.getColumnIndex("city_name")));
			BAN_NAME.add(c.getString(c.getColumnIndex("city_bangla")));
			LAT.add(Double.parseDouble(c.getString(c.getColumnIndex("lat"))));
			LON.add(Double.parseDouble(c.getString(c.getColumnIndex("lon"))));
		}
		databaseHelper.close();

		String tempSt1 = DES.toLowerCase();

		for (int i = 0; i < ENG_NAME.size(); i++) {
			String tempCity = ENG_NAME.get(i).toLowerCase();

			if (tempCity.equalsIgnoreCase("Coxs Bazar")) {
				tempCity = "cox";
			}
			if (tempSt1.contains(tempCity)) {
				AC_ENG_NAME.add(ENG_NAME.get(i));
				AC_BAN_NAME.add(BAN_NAME.get(i));
				AC_LAT.add(LAT.get(i));
				AC_LON.add(LON.get(i));
			}
		}

		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}

			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		} catch (Exception e) {
			e.printStackTrace();
		}

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < AC_ENG_NAME.size() - 1; i++) {
					for (int j = i + 1; j < AC_ENG_NAME.size(); j++) {

						double temp1 = AC_LAT.get(i);
						double temp2 = AC_LAT.get(j);
						if (temp2 > temp1) {
							String ename1 = AC_ENG_NAME.get(i);
							String bname1 = AC_BAN_NAME.get(i);
							double lat1 = AC_LAT.get(i);
							double lon1 = AC_LON.get(i);

							AC_ENG_NAME.set(i, AC_ENG_NAME.get(j));
							AC_BAN_NAME.set(i, AC_BAN_NAME.get(j));
							AC_LAT.set(i, AC_LAT.get(j));
							AC_LON.set(i, AC_LON.get(j));

							AC_ENG_NAME.set(j, ename1);
							AC_BAN_NAME.set(j, bname1);
							AC_LAT.set(j, lat1);
							AC_LON.set(j, lon1);
						}
					}
				}

				for (int i = 0; i < AC_ENG_NAME.size(); i++) {
					LatLng globalPosition = new LatLng(AC_LAT.get(i), AC_LON
							.get(i));
					Marker warnPos = googleMap.addMarker(new MarkerOptions()
							.position(globalPosition)
							.title(AC_ENG_NAME.get(i))
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
					AC_MARKER.add(warnPos);
				}

			}
		});

		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				23.684994, 90.356331), 6));

	}

	CancelableCallback MyCancelableCallback = new CancelableCallback() {

		@Override
		public void onFinish() {

			if (animate_count > 0)
				AC_MARKER.get(animate_count - 1).showInfoWindow();

			if (animate_count > 0)
				showToast(AC_BAN_NAME.get(animate_count - 1) + " ("
						+ AC_ENG_NAME.get(animate_count - 1) + ")");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TODO Auto-generated method stub
			if (animate_count < AC_ENG_NAME.size()) {

				LatLng animateLocation = new LatLng(AC_LAT.get(animate_count),
						AC_LON.get(animate_count));

				googleMap.animateCamera(
						CameraUpdateFactory.newLatLngZoom(animateLocation, 10),
						6000, MyCancelableCallback);
				animate_count++;
			}
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}
	};

	public void showToast(String myText) {

		LayoutInflater inflater = getLayoutInflater();

		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) findViewById(R.id.toastlayout));

		TextView text = (TextView) layout.findViewById(R.id.toasttext);

		text.setTypeface(custom_font);
		text.setText(myText);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM, 0, 50);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();

	}
}
