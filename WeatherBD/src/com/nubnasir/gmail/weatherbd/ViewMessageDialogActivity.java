package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewMessageDialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_message_dialog);

		TextView title = (TextView) findViewById(R.id.title);
		TextView msg = (TextView) findViewById(R.id.msg);
		Button exit = (Button) findViewById(R.id.exitbutton);

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		Typeface custom_font = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "banglafont.ttf");

		Intent myIntent = getIntent();

		String TITLE = myIntent.getStringExtra("TITLE");
		String MSG = myIntent.getStringExtra("MSG");

		title.setTypeface(custom_font);
		

		title.setText(TITLE);
		msg.setText(MSG);

	}

}
