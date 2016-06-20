package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RoomConditionActivity extends Activity implements
		SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mTemp;
	private Sensor mPressure;
	private Sensor mHumidity;
	private String temperatureString;
	private String pressureString;
	private String humidityString;
	private String msg = "Your device ";

	private TextView room_temp;
	private TextView room_temp_converted;
	private TextView room_pressure_atm;
	private TextView room_pressure_hpa;
	private TextView room_humidity;
	private TextView room_msg;
	private Button back_button;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.temperature_layout);


		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			Toast.makeText(getApplicationContext(),
					"This android version doesn't support required sensor.",
					Toast.LENGTH_LONG).show();
		}

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mTemp = mSensorManager
				.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		mHumidity = mSensorManager
				.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

		room_temp = (TextView) findViewById(R.id.roomtemp);
		room_temp_converted = (TextView) findViewById(R.id.convertedroomtemp);
		room_pressure_hpa = (TextView) findViewById(R.id.roompressuerhpa);
		room_pressure_atm = (TextView) findViewById(R.id.roompressueratm);
		room_humidity = (TextView) findViewById(R.id.roomhumidity);
		room_msg = (TextView) findViewById(R.id.msgsensor);
		back_button = (Button) findViewById(R.id.roombackbutton);
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		room_msg.setText(msg);
	}

	@Override
	public final void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do something here if sensor accuracy changes.
		switch (accuracy) {
		case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
			msg += "sensor has high accuracy! ";
			break;
		case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
			msg += "sensor has medium accuracy! ";
			break;
		case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
			msg += "sensor has low accuracy! ";
			break;
		case SensorManager.SENSOR_STATUS_UNRELIABLE:
			msg += "sensor has unreliable accuracy! ";
			break;
		default:
			break;
		}

		room_msg.setText(msg);
	}

	@Override
	public final void onSensorChanged(SensorEvent event) {
		float sensorValue = event.values[0];
		// Do something with this sensor data.

		int currType = event.sensor.getType();

		switch (currType) {
		case Sensor.TYPE_AMBIENT_TEMPERATURE:
			temperatureString = sensorValue + " \u00B0C";
			double kelvin = sensorValue + 273.15;
			double farenhite = sensorValue * (9 / 5) + 32;
			room_temp.setText(temperatureString);
			room_temp_converted.setText(kelvin + " K, " + farenhite
					+ " \u00B0F");
			break;
		case Sensor.TYPE_PRESSURE:
			pressureString = sensorValue + " hPa";
			double atm = sensorValue * 0.0009869;
			room_pressure_hpa.setText(pressureString);
			room_pressure_atm.setText(atm + " atm");
			break;
		case Sensor.TYPE_RELATIVE_HUMIDITY:
			humidityString = sensorValue + "%";
			room_humidity.setText(humidityString);
			break;
		default:
			break;
		}

		msg += " Finished computing";
		room_msg.setText(msg);
		mTemp = null;
		mPressure = null;
		mHumidity = null;
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// Register a listener for the sensor.
		super.onResume();
		if (mTemp == null) {
			msg += "doesn't have an ambient temperature sensor! ";
		} else {
			mSensorManager.registerListener(this, mTemp,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
		if (mPressure == null) {
			msg += "doesn't have a pressuer sensor! ";
		} else {
			mSensorManager.registerListener(this, mPressure,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
		if (mHumidity == null) {
			msg += "doesn't have a humidity sensor! ";
		} else {
			mSensorManager.registerListener(this, mHumidity,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		room_msg.setText(msg);
	}

	@Override
	protected void onPause() {
		// Be sure to unregister the sensor when the activity pauses.
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
}