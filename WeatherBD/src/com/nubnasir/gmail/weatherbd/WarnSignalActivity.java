package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WarnSignalActivity extends Activity {

	private Button back_button;
	private String title;
	private CustomListForWarnNumber listAdapter;
	private int position;
	private TextView title_tv;
	private ListView myList;
	private String[] n_sea_bangla = { "সংকেত নম্বর",
			"১নং দূরবর্তী সতর্ক সংকেত", "২নং দূরবর্তী হুঁশিয়ারী সংকেত",
			"৩নং স্থানীয় সতর্ক সংকেত", "৪নং স্থানীয় হুঁশিয়ারী সংকেত",
			"৫নং বিপদ সংকেত", "৬নং বিপদ সংকেত", "৭নং বিপদ সংকেত",
			"৮নং মহাবিপদ সংকেত", "৯নং মহাবিপদ সংকেত", "১০নং মহাবিপদ সংকেত",
			"১১নং যোগাযোগ বিচ্ছিন্ন সংকেত" };
	private String[] n_sea_english = { "Signals",
			"Distant Cautionary Signal No. I", "Distant Warning Signal No. II",
			"Local Cautionary Signal No.III", "Local Warning Signal No.IV",
			"Danger Signal No. V", "Danger Signal No. VI",
			"Danger Signal No. VII", "Great Danger Signal No. VIII",
			"Great Danger Signal No. IX", "Great Danger Signal No. X",
			"Failure of Communication No. XI" };
	private String[] n_river_bangla = { "সংকেত নম্বর", "১নং নৌ সতর্ক সংকেত",
			"২নং নৌ হুঁশিয়ারী সংকেত", "৩নং নৌ বিপদসংকেত", "৪নং নৌ মহাবিপদ" };
	private String[] n_river_english = { "Signals", "Cautionary Signal No. I",
			"Warning Signal No. II", "Danger Signal No. III",
			"Great Danger Signal No. IV" };
	private String[] d_sea_bangla = {
			"সংকেত সমূহের অর্থ",
			"জাহাজ ছেড়ে যাওয়ার পর দুর্যোগপূর্ণ আবহাওয়া সম্মুখীন হতে পারে। দূরবর্তী এলাকায় একটি ঝড়ো হাওয়ার অঞ্চল রয়েছে, যেখানে বাতাসের গতিবেগ ঘন্টায় ৬১ কিঃমিঃ যা সামূদ্রিক ঝড়ে পরিণত হতে পারে।",
			"দূরেগভীর সাগরে একটি ঝড় সৃষ্টি হয়েছে। সেখানে বাতাসের একটানা গতিবেগ ঘন্টায় ৬২-৮৮ কিঃমিঃ। বন্দর এখনই ঝড়ে কবলিত হবে না, তবে বন্দর ত্যাগকারী জাহাজ পথি মধ্যে বিপদে পড়তে পারে।",
			"বন্দরও বন্দরে নোঙ্গরকরা জাহাজগুলো দুর্যোগ কবলিত হওয়ার আশঙ্খা রয়েছে। বন্দরে ঝড়ো হাওয়া বয়ে যেতে পারে এবং ঘূর্ণি বাতাসের একটানা গতিবেগ ঘন্টায় ৪০-৫০ কিঃমিঃ হতে পারে।",
			"বন্দর ঘূর্ণিঝড় কবলিত। বাতাসের সম্ভাব্য গতিবেগ ঘন্টায় ৫১-৬১কিঃমিঃ তবে ঘূর্ণিঝড়ের চূড়ান্ত প্রস্তুতি নেওয়ার মতো তেমন বিপজ্জনক সময় এখনো আসেনি।",
			"বন্দর ছোট বা মাঝারী তীব্রতার ঝঞ্ঝাবহুল একসামূদ্রিক ঝড়ের কবলে নিপতিত। ঝড়ে বাতাসের সর্বোচ্চ একটানা গতিবেগ ঘন্টায় ৬২-৮৮ কিঃমিঃ। ঝড়টি বন্দরকে বামদিকে রেখে উপকূল অতিক্রম করতে পারে।",
			"বন্দর ছোট বা মাঝারী তীব্রতার ঝঞ্ঝাবহুল এক সামূদ্রিক ঝড়ের কবলে নিপতিত। ঝড়ে বাতাসের সর্বোচ্চ একটানা গতিবেগ ঘন্টায় ৬২-৮৮ কিঃমিঃ। ঝড়টি বন্দরকে ডানদিকে রেখে উপকূল অতিক্রম করতে পারে।",
			"বন্দর ছোট বা মাঝারী তীব্রতার ঝঞ্ঝাবহুল এক সামূদ্রিক ঘূণিঝড়ের কবলে নিপতিত। ঝড়ে বাতাসের সর্বোচ্চ একটানা গতিবেগ ঘন্টায় ৬২-৮৮ কিঃমিঃ। ঝড়টি বন্দরকে উপর বা নিকট দিয়ে উপকূল অতিক্রম করতে পারে।",
			"বন্দর প্রচন্ড বা সর্বোচ্চ তীব্রতার ঝঞ্ঝাবিক্ষুব্ধ ঘূণিঝড়ের কবলে পড়তে পারে। ঝড়ে বাতাসের সর্বোচ্চ একটানাগতিবেগ ঘন্টায় ৮৯ কিঃমিঃ বা তার উর্দ্ধে হতে পারে। প্রচন্ড ঝড়টি বন্দরকে বামদিকে রেখে উপকূল অতিক্রম করবে।",
			"বন্দর প্রচন্ড বা সর্বোচ্চ তীব্রতার ঝঞ্ঝাবিক্ষুব্ধ এক সামূদ্রিক ঘূণিঝড়ের কবলে নিপতিত। ঝড়ে বাতাসের সর্বোচ্চ একটানা গতিবেগ ঘন্টায় ৮৯ কিঃমিঃ বা তার উর্দ্ধে হতে পারে। প্রচন্ড ঝড়টি বন্দরকে ডানদিকে রেখে উপকূল অতিক্রম করবে।",
			"বন্দর প্রচন্ড বা সর্বোচ্চ তীব্রতার ঝঞ্ঝাবিক্ষুব্ধ এক সামূদ্রিক ঘূণিঝড়ের কবলে নিপতিত। ঝড়ে বাতাসের সর্বোচ্চ একটানা গতিবেগ ঘন্টায় ৮৯ কিঃমিঃ বা তার উর্দ্ধে হতে পারে। প্রচন্ড ঝড়টি বন্দরের উপর বা নিকট দিয়ে উপকূল অতিক্রম করবে।",
			"আবহাওয়া বিপদ সংকেত প্রদানকারী কেন্দ্রের সাথে সকল যোগাযোগ বিচ্ছিন্ন হয়ে পড়েছে এবং স্থানীয় কর্মকর্তা আবহাওয়া অত্যন্ত দুর্যোগপূর্ণ বলে মনে করেন।" };
	private String[] d_sea_english = {
			"Meanings",
			"There is a region of squally weather (wind speed of 61 kms/hour) in the distant sea where a storm may form.",
			"A storm (wind speed of 62-88 kms/hour) has formed in the distant deep sea. Ships may fall into danger if they leave harbour,",
			"The port is threatened by squally weather (wind speed of 40-50 kms/hour).",
			"The port is threatened by a storm (wind speed of 51-61 kms/hour) but it doesn't appear that the danger is as yet sufficiently great to justify extreme precautionary measures.",
			"The port will experience severe weather from a storm of slight or moderate intensity (wind speed of 62-88 kms/hour) that is expected to cross the coast to the south of Chittagong port or Cox's Bazar port and to the east of Mongla port.",
			"The port will experience severe weather from a storm of slight or moderate intensity (wind speed of 62-88 kms/hour) that is expected to cross the coast to the north of the port of Chittagong or Cox's Bazar and to the west of the port of Mongla.",
			"The port will experience severe weather from a storm of light or moderate intensity (wind speed of 62-88 kms/hour) that is expected to cross over or near the port.",
			"The port will experience severe weather from a storm of great intensity (wind speed of 89 kms/hour or more) that is expected to cross the coast to the south of the port of Chittagong or Cox's Bazar and to the east of the port of Mongal.",
			"The port will experience severe weather from a storm of great intensity (wind speed of 89 kms/hour or more) that is expected to cross the coast to the north or the port of Chittagong or Cox's Bazar and to the west of the port of Mongla.",
			"The port will experience severe weather from a storm of great intensity (wind speed of 89 kms/hour or more) that is expected to cross over or near the port.",
			"Communications with the Storm Warning Centre have broken down and local officers consider that a devastating cyclone is following." };
	private String[] d_river_bangla = {
			"সংকেত সমূহের অর্থ",
			"বন্দর এলাকা ক্ষণস্থায়ী ঝড়ো আবহাওয়ার কবলে নিপতিত হওয়ার সম্ভাবনা রয়েছে। ঘন্টায় সর্বোচ্চ ৬০কিঃমিঃ গতিবেগের কালবৈশাখী ক্ষেত্রেও এই সংকেত প্রদর্শিত হয়। এই সংকেত আবহাওয়ার চলতি অবস্থার উপর সতর্ক নজর রাখারও তাগিদদেয়।",
			"বন্দর এলাকা নিম্নচাপের সমতূল্য তীব্রতার একটি ঝড়যার গতিবেগ ঘন্টায় অনুর্দ্ধ ৬১কিঃমিঃ বা একটি কালবৈশাখী ঝড়, যার বাতাসের গতিবেগ ৬১কিঃমিঃ বা তদুর্দ্ধ। নৌ-যান এদের যে কোনটির কবলে নিপতিত হওয়ার সম্ভাবনা রয়েছে।৬৫ ফুট বা তা রকম দৈর্ঘ্যবিশিষ্ট নৌ-যানকে দ্রত নিরাপদ আশ্রয়ে যেতে হবে।",
			"বন্দর এলাকা ঝড়ে কবলিত। ঘন্টায় সর্বোচ্চ একটানা ৬২-৮৮কিঃমিঃ পর্যন্ত গতিবেগের একটি সামূদ্রিক ঝড় সহসাই বন্দর এলাকায় আঘাত হানতে পারে। সকল প্রকার নৌ-যানকে অবিলম্বে নিরাপদ আশ্রয়গ্রহণ করতে হবে।",
			"সংকেত বন্দর এলাকা একটি প্রচন্ড বা সর্বোচ্চ তীব্রতার সামূদ্রিক ঝড়ে কবলিত এবং সহসাই বন্দর এলাকায় আঘাত হানবে। ঝড়ে বাতাসের সর্বোচ্চ একটানা গতিবেগ ঘন্টায় ৮৯কিঃমিঃ বা তদুর্দ্ধ। সকল প্রকার নৌ-যানকে নিরাপদ আশ্রয় থাকতে হবে।" };
	private String[] d_river_english = {
			"Meanings",
			"The area is threatened by squally winds (wind speed of 60 km/hour) of transient nature. This signal is also hoisted during nor’westers.",
			"A storm (wind speed of 61 kms/hour) or a nor’wester (wind speed 61 km/hour or more) is likely to strike the area (Vessels of 65 feet and under in length are to seek shelter immediately.",
			"A storm (wind speed of 62-88 km/hour or more) is likely to strike the area soon (all vessels will seek shelter immediately).",
			"A violent storm (wind speed of 89 km/hour or more) will strike the area soon (all Vessels will take shelter immediately)." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.warning_signal_details_layout);

		Intent weatherIntent = getIntent();

		position = Integer.parseInt(weatherIntent.getStringExtra("POSITION"));
		title_tv = (TextView) findViewById(R.id.warntitle);
		myList = (ListView) findViewById(R.id.listView);
		back_button = (Button) findViewById(R.id.backbuttonWarn);

		Typeface custom_font = Typeface.createFromAsset(getAssets(),
				"banglafont.ttf");
		title_tv.setTypeface(custom_font);

		if (position == 1) {
			title_tv.setText("সমুদ্র বন্দরের জন্য সংকেত সমূহ");

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					listAdapter = new CustomListForWarnNumber(
							WarnSignalActivity.this, n_sea_bangla, d_sea_bangla);

					myList.setAdapter(listAdapter);
				}
			});
		}

		else if (position == 2) {
			title_tv.setText("Signals for Maritime Ports");

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					listAdapter = new CustomListForWarnNumber(
							WarnSignalActivity.this, n_sea_english,
							d_sea_english);

					myList.setAdapter(listAdapter);
				}
			});
		} else if (position == 3) {
			title_tv.setText("নদী বন্দরের জন্য সংকেত সমূহ");

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					listAdapter = new CustomListForWarnNumber(
							WarnSignalActivity.this, n_river_bangla,
							d_river_bangla);

					myList.setAdapter(listAdapter);
				}
			});
		} else if (position == 4) {
			title_tv.setText("Signals for Inland River Ports");

			new Handler().post(new Runnable() {
				@Override
				public void run() {
					listAdapter = new CustomListForWarnNumber(
							WarnSignalActivity.this, n_river_english,
							d_river_english);

					myList.setAdapter(listAdapter);
				}
			});
		}

		back_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

}
