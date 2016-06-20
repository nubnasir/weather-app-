package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ImportantContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.important_contacts);

		TextView title = (TextView) findViewById(R.id.imtitle);
		Button exit = (Button) findViewById(R.id.imexit);

		TextView im1 = (TextView) findViewById(R.id.im1);
		TextView im2 = (TextView) findViewById(R.id.im2);
		TextView im3 = (TextView) findViewById(R.id.im3);

		Button imgo1 = (Button) findViewById(R.id.imgo1);
		Button imgo2 = (Button) findViewById(R.id.imgo2);
		Button imgo3 = (Button) findViewById(R.id.imgo3);

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		Typeface custom_font = Typeface.createFromAsset(getApplicationContext()
				.getAssets(), "banglafont.ttf");

		title.setTypeface(custom_font);
		im1.setTypeface(custom_font);
		im2.setTypeface(custom_font);
		im3.setTypeface(custom_font);

		imgo1.setTypeface(custom_font);
		imgo2.setTypeface(custom_font);
		imgo3.setTypeface(custom_font);

		title.setText("যোগাযোগ করুন");

		im1.setText("বাংলাদেশের বিভিন্ন অঞ্চলের বন্যার পানির উচ্চতা জানুন মানচিত্রের মাধ্যমে");
		imgo1.setText("ওয়েব সাইটে প্রবেশ করুন");
		imgo1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.ffwc.gov.bd"));
				startActivity(browserIntent);
			}
		});

		im2.setText("বাংলাদেশ আবহাওয়া অদিদপ্তর");
		imgo2.setText("ফোন করুন");
		imgo2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + "+88029135742"));
				startActivity(callIntent);
			}
		});

		im3.setText("ঝড় সতর্কতা কেন্দ্র");
		imgo3.setText("ফোন করুন");
		imgo3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + "+8801727314120"));
				startActivity(callIntent);
			}
		});

	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
