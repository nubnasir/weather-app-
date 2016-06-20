package com.nubnasir.gmail.weatherbd;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private DatabaseHelper databaseHelper;
	int magnitude = 0;
	int range = 1500;
	int service = 1;
	int all_eq = 1;

	CheckBox eqservice;
	RadioButton all_eq_service, specific_eq_service;
	EditText eq_mag, eq_range;
	Button saveBt, exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_layout);

		eqservice = (CheckBox) findViewById(R.id.eqservice);
		all_eq_service = (RadioButton) findViewById(R.id.radio0);
		specific_eq_service = (RadioButton) findViewById(R.id.radio1);
		eq_mag = (EditText) findViewById(R.id.eq_mag);
		eq_range = (EditText) findViewById(R.id.eq_range);
		saveBt = (Button) findViewById(R.id.apply);
		exit = (Button) findViewById(R.id.exitsettings);

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

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		saveBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (eqservice.isChecked()) {
					service = 1;
				} else {
					service = 0;
				}
				if (all_eq_service.isChecked()) {
					all_eq = 1;
				} else {
					all_eq = 0;
				}

				try {
					magnitude = Integer.parseInt("" + eq_mag.getText());
				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(),
							"Invalid Magnitude", Toast.LENGTH_LONG).show();
					return;
				}
				try {
					range = Integer.parseInt("" + eq_range.getText());
				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(),
							"Invalid earthquake range", Toast.LENGTH_LONG)
							.show();
					return;
				}

				try {
					databaseHelper.updateSettings(magnitude, range, service,
							all_eq);

					if (service == 1) {

						Intent intent = new Intent(
								SettingsActivity.this,
								com.nubnasir.gmail.weatherbd.NotifyService.class);
						SettingsActivity.this.startService(intent);

					} else {

						Intent intent = new Intent();
						intent.setAction(NotifyService.ACTION);
						intent.putExtra("RQS", NotifyService.RQS_STOP_SERVICE);
						sendBroadcast(intent);

					}

					Toast.makeText(getApplicationContext(), "Settings changed",
							Toast.LENGTH_LONG).show();
				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(), "Invalid data",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		Cursor cursor = databaseHelper.getResult("SELECT value FROM settings;");

		try {
			cursor.moveToFirst();
			magnitude = cursor.getInt((cursor.getColumnIndex("value")));
			cursor.moveToNext();
			range = cursor.getInt((cursor.getColumnIndex("value")));
			cursor.moveToNext();
			service = cursor.getInt((cursor.getColumnIndex("value")));
			cursor.moveToNext();
			all_eq = cursor.getInt((cursor.getColumnIndex("value")));
		} catch (Exception ex) {
		}

		eq_mag.setText("" + magnitude);
		eq_range.setText("" + range);

		if (service == 1) {
			eqservice.setChecked(true);
		} else {
			eqservice.setChecked(false);
		}
		if (all_eq == 1) {
			all_eq_service.performClick();
		} else {
			specific_eq_service.performClick();
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			databaseHelper.close();
		} catch (Exception ex) {
		}
	}

}
