package com.nubnasir.gmail.weatherbd;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactOperation extends Activity {

	String TITLE = "";
	String NAME = "";
	String PHONE = "";
	String operation;
	EditText name;
	EditText phone;
	private DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.emargency_contacts);

		TextView title = (TextView) findViewById(R.id.titlecontact);
		name = (EditText) findViewById(R.id.namecontact);
		phone = (EditText) findViewById(R.id.numbercontact);
		Button save = (Button) findViewById(R.id.savecontact);

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

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		Intent myIntent = getIntent();

		try {
			TITLE = myIntent.getStringExtra("TITLE");
			NAME = myIntent.getStringExtra("NAME");
			PHONE = myIntent.getStringExtra("PHONE");
			operation = myIntent.getStringExtra("OP");

		} catch (Exception ex) {
		}

		title.setText(TITLE);
		name.setText(NAME);
		phone.setText(PHONE);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (operation.equals("ADD")) {
					databaseHelper.addContact("" + name.getText(),
							"" + phone.getText());
				} else {
					databaseHelper.editContact(PHONE, "" + name.getText(), ""
							+ phone.getText());
				}
			}
		});

	}

}
