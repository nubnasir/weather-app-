package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence title;
	private CharSequence mDrawerTitle;
	private ArrayList<String> navMenuItems = new ArrayList<String>();
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private MyAdapter adapter;
	AlertDialog simpleDialog;
	private DatabaseHelper databaseHelper;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// getActionBar().setIcon(new
		// ColorDrawable(getResources().getColor(android.R.color.transparent)));

		title = mDrawerTitle = getSupportActionBar().getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		for (int i = 0; i < navMenuTitles.length; i++)
			navMenuItems.add(navMenuTitles[i]);
		adapter = new MyAdapter(this, navMenuItems, navMenuIcons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(adapter);

//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//
//			@Override
//			public void uncaughtException(Thread thread, Throwable ex) {
//				Toast.makeText(getApplicationContext(),
//						"Sorry something went wrong try again later.",
//						Toast.LENGTH_LONG).show();
//			}
//		});

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
			}

			public void onDrawerOpened(View drawerView) {
			}
		};

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.BLUE));

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setHomeButtonEnabled(true);

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

		int mag = databaseHelper.getSettings("service");

		if (mag == 1) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,
							com.nubnasir.gmail.weatherbd.NotifyService.class);
					MainActivity.this.startService(intent);

				}
			}).start();

		}

		if (savedInstanceState == null) {
			selectItem(0);
		}

		try {
			Intent myIntent = getIntent();
			int mypos = Integer.parseInt(myIntent.getStringExtra("POS"));
			selectItem(mypos);
		} catch (Exception ex) {
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.about:

			Intent aboutntent = new Intent(getApplicationContext(),
					AboutActivity.class);
			startActivity(aboutntent);

			break;
		case R.id.settings:

			Intent settingIntent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivity(settingIntent);

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void selectItem(int position) {
		if (position == 0) {

			MyFragment fragment = new MyFragment();
			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			fragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			mDrawerList.setItemChecked(position, true);
			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (position == 1) {

			AllCityListFragment myfragment = new AllCityListFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);
			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 2) {

			AllCityListFragment myfragment = new AllCityListFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);
			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 3) {

			AllCityListFragment myfragment = new AllCityListFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle("Bangladesh");
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 4) {

			SearchCityFragment myfragment = new SearchCityFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 5) {

			MissingCity myfragment = new MissingCity();

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 6) {

			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
					|| connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
				// we are connected to a network
			} else {

				Toast.makeText(
						MainActivity.this,
						"Internet is not connected. Please check your internet connection.",
						Toast.LENGTH_LONG).show();
				return;
			}

			EarthQuakeFragment myfragment = new EarthQuakeFragment();

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 7) {

			EarthQuakeBdMapFragment myfragment = new EarthQuakeBdMapFragment();

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 8) {

			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
					|| connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
				// we are connected to a network
			} else {

				Toast.makeText(
						MainActivity.this,
						"Internet is not connected. Please check your internet connection.",
						Toast.LENGTH_LONG).show();
				return;
			}

			BangladeshBMDFragment myfragment = new BangladeshBMDFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle("Bangladesh BMD");
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 9) {

			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
					|| connectivityManager.getNetworkInfo(
							ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
				// we are connected to a network
			} else {

				Toast.makeText(
						MainActivity.this,
						"Internet is not connected. Please check your internet connection.",
						Toast.LENGTH_LONG).show();
				return;
			}

			LiveWarningBDFragment myfragment = new LiveWarningBDFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 10) {

			ForecastFragment myfragment = new ForecastFragment();

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle("Forecast");
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (position == 11) {

			WarningSignalFragment myfragment = new WarningSignalFragment();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 12) {

			WhatToDoNaturalDigester myfragment = new WhatToDoNaturalDigester();

			Bundle args = new Bundle();
			args.putInt(MyFragment.ARG_OS, position);
			myfragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, myfragment).commit();
			mDrawerList.setItemChecked(position, true);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);

		} else if (position == 13) {

			Intent roomIntent = new Intent(getApplicationContext(),
					RoomConditionActivity.class);
			startActivity(roomIntent);

			getSupportActionBar().setTitle((navMenuTitles[position]));
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (position == 14) {
			onBackPressed();
		}
	}

	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Quit");
		builder.setMessage("Do you want to exit?");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setCancelable(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int pos) {

				finish();
				moveTaskToBack(true);
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int pos) {

			}
		});

		simpleDialog = builder.create();
		simpleDialog.show();

	}

}
