package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WhatToDoinKalboishakhi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.whattodo_in_kalboishakhi);

		TextView title = (TextView) findViewById(R.id.titlewhat);
		Button exit = (Button) findViewById(R.id.exitwhat);
		TextView dt1 = (TextView) findViewById(R.id.dt1);

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
		dt1.setTypeface(custom_font);
		title.setText("ঝড়-বজ্রপাতে করনীয়");
		dt1.setText("১) নিরাপদ আশ্রয়ে যেতে হবে।\n\n২) বজ্রপাত আশপাশের ধাতব পদার্থকে আকর্ষণ করে। তাই সঙ্গে ধাতব বস্তু (আংটি, চাবি, কাস্তে, কোদাল, মোবাইল) রাখবেন না।\n\n৩) বাড়িতে থাকলেও করলে জানালার গ্রিল, থেকে দূরে থাকতে হবে।\n\n৪) বিদ্যুতের সব সুইচ বন্ধ রাখুন। এসময়ে কোনো কিছু চার্জ দেবেন না। আর চার্জ দেওয়া অবস্থায় কখনোই মোবাইল ফোনে কথা বলবেন না।\n\n৫) বাইরে থাকলে উঁচু গাছপালা ও বিদ্যুতের লাইন থেকে দূরে থাকুন বজ্রপাত হলে উঁচু গাছপালা বা বিদ্যুতের খুঁটিতে বজ্রপাতের সম্ভাবনা বেশি থাকে।\n\n৬) টিভি-ফ্রিজ থেকে সাবধান বজ্রপাতের সময় বৈদ্যুতিক সংযোগযুক্ত সব যন্ত্রপাতি স্পর্শ করা থেকে বিরত থাকুন। টিভি, ফ্রিজ ইত্যাদি বন্ধ করা থাকলেও ধরবেন না। বজ্রপাতের আভাষ পেলে আগেই এগুলোর প্লাগ খুলে সম্পূর্ণ বিচ্ছিন্ন করুন।\n\n৭) রাস্তায় হাঁটার সময় অবশ্যই লক্ষ্য রাখতে হবে। কারণ বিদ্যুতের তার ছিঁড়ে দুর্ঘটনার সম্ভাবনা থাকে। উপরন্তু কাছাকাছি কোথাও বাজ পড়লে বিদ্যুত্‍স্পৃষ্ট হওয়ার সম্ভাবনাও থেকে যায়।\n\n৮) চার পাশে খেয়াল রাখুন বজ্রপাতের সময় রাস্তায় চলাচলের সময় আশেপাশে খেয়াল রাখুন। যে দিকে বাজ পড়ার প্রবণতা বেশি সে দিক বর্জন করুন।\n\n৯) বজ্রপাতের সময় গাড়ির ভেতরে থাকলে সম্ভব হলে গাড়িটি নিয়ে কোনো কংক্রিটের ছাউনির নিচে আশ্রয় নিন। গাড়ির ভেতরের ধাতব বস্তু স্পর্শ করা থেকে বিরত থাকুন। গাড়ির কাচেও হাত দেবেন না।\n\n১০) সবচেয়ে জরুরি বিষয় হচ্ছে কেউ যদি বজ্রপাতে আহত হয় তবে তাকে দ্রুত হাসপাতালে নিতে হবে।\n\n");
	}

}
