package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WhatToDoInFloodActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.whattodo_eartrhquake);

		TextView title = (TextView) findViewById(R.id.titlewhat);
		Button exit = (Button) findViewById(R.id.exitwhat);
		TextView t1 = (TextView) findViewById(R.id.t1);
		TextView t2 = (TextView) findViewById(R.id.t2);
		TextView t3 = (TextView) findViewById(R.id.t3);
		TextView t4 = (TextView) findViewById(R.id.t4);
		TextView dt1 = (TextView) findViewById(R.id.dt1);
		TextView dt2 = (TextView) findViewById(R.id.dt2);
		TextView dt3 = (TextView) findViewById(R.id.dt3);
		TextView dt4 = (TextView) findViewById(R.id.dt4);

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Typeface custom_font = Typeface.createFromAsset(getAssets(),
				"banglafont.ttf");
		title.setTypeface(custom_font);
		t1.setTypeface(custom_font);
		t2.setTypeface(custom_font);
		t3.setTypeface(custom_font);
		t4.setTypeface(custom_font);
		dt1.setTypeface(custom_font);
		dt2.setTypeface(custom_font);
		dt3.setTypeface(custom_font);
		dt4.setTypeface(custom_font);
		title.setText("বন্যায়  করনীয়");
		t1.setText("বন্যার আগেঃ");
		t2.setText("যখন বন্যা আসন্নঃ");
		t3.setText("বন্যার সময়ঃ");
		t4.setText("বন্যার পরেঃ");
		dt1.setText("১) প্লাবনভূমি থেকে দূরে বাড়ি নির্মাণ করুন।\n২) আপনার বাড়িতে বন্যার পানি প্রবেশ থামাতে, বাড়ির আশেপাশে সাময়িক বাঁধ নির্মাণ করুন।\n৩) যদি আপনার এলাকায় বন্যা হওয়ার সম্ভহাবনা থাকে তাহলে রেডিও, টেলিভিশন এ তার খবর নিন।\n\n");
		dt2.setText("১) প্রস্তুত থাকুন। প্রয়োজনীয় জিনিসপত্র ব্যাগে গুছিয়ে নিন। আর দরকারি ঔষধ ও পরিস্কারক যেমন সাবান, সেভলন নিতে ভুলবেন না।\n২) যদি আপনাকে বাড়ি থেকে দ্রুত নিরাপদ স্থানে চলে যেতে বলা হয় দ্রুত বের হয় যান।\n৩) বাড়ির প্রয়োজনীয় জিনিসপত্র উঁচু স্থানে রাখুন।\n৪) বৈদ্যুতিক জিনিসপত্র বন্ধ রাখুন এবং মেইন সুইচ বন্ধ রাখুন।\n৫) নিরাপদ পানি সংরক্ষণে রাখুন কারণ বন্যায় সময় নিরাপদ পানি পাওয়া কষ্টকর হয়।\n\n");
		dt3.setText("১) যেই পানিতে স্রত আছে তা এড়িয়ে চলুন। ৬ ইঞ্চি গভীর স্রত ও আপনাকে ফেলে দিতে পারে। \n২) যদি আপনাকে স্রতের মধ্যে দিয়ে চলতেই হয় তাহলে একটি শক্ত লাঠির সাহায্য চলাচল করুন। এবং লাঠির মাধ্যমে দেখুন চলার পথে কোন গরত/খাদ আছে কিনা।\n৩) বন্যার পানিতে সাঁতার কাটবেন না। বন্যার পানি শরীরে লাগলে সেই অংশ পরিষ্কারক দিয়ে ধুয়ে ফেলুন। কারণ বন্যার পানিতে অনেক রোগের জীবাণু থাকে।\n৪) উঁচু স্থানে চলে যান।\n৫) আপনি যদি পানিতে দাড়িয়ে থাকেন বা আপনি যদি শুষ্ক না থাকেন তাহলে বৈদ্যুতিক জিনিসপত্র ধরবেন না।\n\n");
		dt4.setText("১) নিরাপদ পানি ব্যাবহার করুন।\n২) বন্যার পানি এড়িয়ে চলুন। পানি তেল, পেট্রল, বা কাঁচামাল দ্বারা দূষিত হতে পারে।\n৩) যে পানিতে স্রত আছে তা এড়িয়ে চলুন।\n৪) যে সকল অঞ্চলে বন্যার পানি অনেক দিন ধরে আছে সেই সকল অঞ্চলের রাস্তাঘাট/সেতু দুর্বল হতে পারে তখন ওই সকল রাস্তায় চলাচলে সাবধানতা অবলম্বন করুন।\n৫) বিপদ কবলিত বিদ্যুতের লাইন থেকে দূরে থাকুন, এবং পাওয়ার কোম্পানিকে রিপোর্ট করুন।\n৬) কর্তৃপক্ষ নিরাপদ ইঙ্গিত দিলে বাড়ি ফিরুন।\n৭) বন্যা কবলিত বাড়ি ঘর থেকে দূরে থাকুন\n৮) নষ্ট জিনিসপত্র মাটিতে গর্ত করে ফেলে দিন।\n৯) ঘরের আসবাবপত্র পরিস্কার করে নিন কারণ এতে জীবাণু থাকতে পারে।\n\n");
	}

}
