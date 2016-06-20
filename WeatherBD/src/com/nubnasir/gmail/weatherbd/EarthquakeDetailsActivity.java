package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EarthquakeDetailsActivity extends Activity {

	private GoogleMap googleMap;
	public LatLng eqLoc;
	public LatLng myLoc;
	String MAG;
	String TITLE;
	String LON;
	String LAT;
	String TSUNAMI;
	TextView title;
	Button whattodo;
	Button exit;
	Marker myRealPos;

	int map_view_counter = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.google_map_view);

		Intent myIntent = getIntent();
		MAG = myIntent.getStringExtra("MAG");
		TITLE = myIntent.getStringExtra("TITLE");
		LON = myIntent.getStringExtra("LON");
		LAT = myIntent.getStringExtra("LAT");
		TSUNAMI = myIntent.getStringExtra("TSUNAMI");

		title = (TextView) findViewById(R.id.eqtitle);
		whattodo = (Button) findViewById(R.id.whattodo);
		exit = (Button) findViewById(R.id.exitmap);

		title.setText(TITLE);

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

		Typeface custom_font = Typeface.createFromAsset(getAssets(),
				"banglafont.ttf");
		whattodo.setTypeface(custom_font);

		whattodo.setText("ভূমিকম্পে করনীয়");

		whattodo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent whatIntent = new Intent(getApplicationContext(),
						WhatToDoEarthquakeActivity.class);
				startActivity(whatIntent);
			}
		});

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		eqLoc = new LatLng(Double.parseDouble(LAT), Double.parseDouble(LON));
		myLoc = new LatLng(23.684994, 90.356331);

		int duration = (int) (DistanceCalculator.distance(23.684994, 90.356331,
				Double.parseDouble(LAT), Double.parseDouble(LON), 'K') + 0.5);

		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}
			googleMap.setMyLocationEnabled(true);
			// googleMap.setLocationSource(this);
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			Marker myPos = googleMap.addMarker(new MarkerOptions()
					.position(myLoc)
					.title("Bangladesh")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

			myPos.showInfoWindow();

			Marker eqPos = googleMap.addMarker(new MarkerOptions()
					.position(eqLoc)
					.title(MAG + " " + TSUNAMI)
					.snippet(
							(int) (DistanceCalculator.distance(23.684994,
									90.356331, Double.parseDouble(LAT),
									Double.parseDouble(LON), 'K') + 0.5)
									+ " km from Bangladesh")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

			eqPos.showInfoWindow();

			// Move the camera instantly to hamburg with a zoom of 15.
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 6));

			// Zoom in, animating the camera.

			googleMap.animateCamera(
					CameraUpdateFactory.newLatLngZoom(eqLoc, 6), duration,
					new CancelableCallback() {

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub

						}
					});

			// googleMap.animateCamera(CameraUpdateFactory.zoomTo(1), 2000,
			// null);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
