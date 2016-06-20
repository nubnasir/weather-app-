package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WhatToDoInCyclone extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.whattodo_in_cyclone);

		TextView title = (TextView) findViewById(R.id.titlewhat);
		Button exit = (Button) findViewById(R.id.exitwhat);
		TextView t1 = (TextView) findViewById(R.id.t1);
		TextView t2 = (TextView) findViewById(R.id.t2);
		TextView t3 = (TextView) findViewById(R.id.t3);
		TextView dt1 = (TextView) findViewById(R.id.dt1);
		TextView dt2 = (TextView) findViewById(R.id.dt2);
		TextView dt3 = (TextView) findViewById(R.id.dt3);

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
		dt1.setTypeface(custom_font);
		dt2.setTypeface(custom_font);
		dt3.setTypeface(custom_font);
		title.setText("ঘূর্ণিঝড় ও জলোচ্ছ্বাসে করনীয়");
		t1.setText("ঘূর্ণিঝড় ও জলোচ্ছ্বাসের পূর্বে (স্বাভাবিক অবস্থায়) করনীয়:");
		t2.setText("ঘূর্ণিঝড় ও জলোচ্ছ্বাসের পূর্বাভাস পাবার পর করণীয়:");
		t3.setText("ঘূর্ণিঝড় ও জলোচ্ছ্বাসের পরবর্তী করণীয়:");
		dt1.setText("দুর্যোগের সময় কোন এলাকার লোক কোন আশ্রয়ে যাবে, গরু মহিষাদি কোথায় থাকবে তা আগে ঠিক করে রাখুন এবং জায়গা চি‎িনয়ে রাখুন।\n\nবাড়িতে, গ্রামে, রাস্তায় ও বাঁধের উপর গাছ লাগান।\n\nযথাসম্ভব উঁচু স্থানে শক্ত করে ঘর তৈরি করুন। পাকা ভিত্তির উপর লোহার বা কাঠের পিলার এবং ফ্রেম দিয়ে তার উপর ছাউনি দিন। ছাউনিতে টিন ব্যবহার না করা ভাল। কারণ ঝড়ের সময় টিন উড়ে মানুষ ও গবাদি পশুকে আহত করতে পারে। তবে ০.৫ মি. মি. পুরুত্ব বিশিষ্ট টিন ও জেহুক ব্যবহার করা যেতে পারে।\n\nউঁচু জায়গায় টিউবওয়েল স্থাপন করুন, যাতে জলোচ্ছ্বাসের লোনা ও ময়লা পানি টিউবওয়েলে ঢুকতে না পারে।\n\nজেলেনৌকা, লঞ্চ ও ট্রলারে রেডিও রাখুন। সকাল, দুপুর ও বিকালে আবহাওয়ার পূর্বাভাস শোনার অভ্যাস করুন।\n\nসম্ভব হলে বাড়িতে কিছু প্রাথমিক চিকিৎসার সরঞ্জাম (ব্যান্ডেজ, ডেটল প্রভৃতি) রাখুন।\n\nজলোচ্ছ্বাসের পানির প্রকোপ থেকে রক্ষায় নানারকম শস্যের বীজ সংরক্ষণের ব্যবস্থা নিন।\n\nবাড়িতে এবং রাস্তায় নারিকেল, কলাগাছ, বাঁশ, তাল, কড়ই ও অন্যান্য শক্ত গাছপালা লাগান। এসব গাছ ঝড় ও জলোচ্ছ্বাসের বেগ কমিয়ে দেয়। এর ফলে মানুষ দুর্যোগের কবল থেকে বাঁচতে পারে।\n\nনারী-পুরুষ, ছেলে-মেয়ে প্রত্যেকেরই সাঁতার শেখা উচিত।\n\nঘূর্ণিঝড় আশ্রয়কেন্দ্রে বা অন্য আশ্রয়ে যাবার সময় কি কি জরুরি জিনিস সঙ্গে নেয়া যাবে এবং কি কি জিনিস মাটিতে পুঁতে রাখা হবে তা ঠিক করে সে অনুসারে প্রস্তুতি নেয়া উচিত।\n\nআর্থিক সামর্থ থাকলে ঘরের মধ্যে একটি পাকা গর্ত করুন। জলোচ্ছ্বাসের পূর্বে এই পাকা গর্তের মধ্যে অতি প্রয়োজনীয় জিনিসপত্র রাখতে পারবেন।\n\nডায়ারিয়া মহামারীর প্রতি সচেতন দৃষ্টি রাখতে হবে। শিশুদের ডায়ারিয়া হলে কিভাবে খাবার স্যালাইন তৈরি করতে হবে সে বিষয়ে পরিবারের সকলকে প্রশিক্ষণ দিন।\n\nঘূর্ণিঝড়ের মাসগুলোতে বাড়িতে মুড়ি, চিড়া, বিস্কুটজাতীয় শুকনো খাবার রাখা ভাল।\n\nনোংরা পানি কিভাবে ফিটকারি বা ফিল্টার দ্বারা খাবার ও ব্যবহারের উপযোগী করা যায় সে বিষয়ে মহিলাদের এবং আপনার পরিবারের অন্যান্য সদস্যদের প্রশিক্ষণ দিন।\n\nঘূর্ণিঝড়ের পরে বৃষ্টি হয়। বৃষ্টির পানি ধরে রাখার ব্যবস্থা করুন। বৃষ্টির পানি বিশুদ্ধ। মাটির বড় হাঁড়িতে বা ড্রামে পানি রেখে তার মুখ ভালভাবে আটকিয়ে রাখতে হবে যাতে পোকা-মাকড়, ময়লা-আবর্জনা ঢুকতে না পারে।\n");
		dt2.setText("আপনার ঘরগুলোর অবস্থা পরীক্ষা করুন, আরও মজবুত করার জন্য ব্যবস্থা গ্রহণ করুন। যেমন মাটিতে খুঁটি পুঁতে দড়ি দিয়ে ঘরের বিভিন্ন অংশ বাঁধা।\n\nসিপিপি এর স্বেচ্ছাসেবকদের সঙ্গে যোগাযোগ করুন এবং তাদের পরামর্শ অনুযায়ী প্রস্তুতি নিন।\n\nবিপদসংকেত পাওয়া মাত্র বাড়ির মেয়ে, শিশু ও বৃদ্ধাদের আগে নিকটবর্তী নিরাপদ স্থানে বা আশ্রয়কেন্দ্রে পোঁছে দিতে প্রস্তুত হোন এবং অপসারণ নির্দেশের পরে সময় নষ্ট না করে দ্রুত আশ্রয়কেন্দ্রে যান।\n\nবাড়ি ছেড়ে যাবার সময় আগুন নিভিয়ে যাবেন।\n\nআপনার অতি প্রয়োজনীয় কিছু দ্রব্যসামগ্রী যেমন- ডাল, চাল, দিয়াশলাই, শুক্নো কাঠ, পানি ফিটকিরি, চিনি, নিয়মিত ব্যবহৃত ওষুধ, বইপত্র, ব্যান্ডেজ, তুলা, ওরস্যালাইন ইত্যাদি পানি নিরোধন পলিথিন ব্যাগে ভরে গর্তে রেখে ঢাকনা দিয়ে পুঁতে রাখুন।\n\nআপনার গরু-ছাগল নিকটস্থ উঁচু বাঁধে অথবা কিল্লা বা উঁচুস্থানে রাখুন। কোন অবস্থায়ই গোয়াল ঘরে বেঁধে রাখবেন না। কোন উঁচু জায়গা না থাকলে ছেড়ে দিন, বাঁচার চেষ্টা করতে দিন।\n\nশক্ত গাছের সাথে কয়েক গোছা লম্বা মোটা শক্ত রশি বেঁধে রাখুন। রশি ধরে অথবা রশির সাথে নিজেকে বেঁধে রাখুন যাতে প্রবল ঝড়ে ও জলোচ্ছ্বাসে ভাসিয়ে নিতে না পারে।\n\nআশ্রয় নেয়ার জন্য নির্ধারিত বাড়ির আশেপাশে গাছের ডালপালা আসন্ন ঝড়ের পূর্বেই কেটে রাখুন, যাতে ঝড়ে গাছগুলো ভেঙে বা উপড়িয়ে না যায়।\n\nরেডিওতে প্রতি ১৫ মিনিট পর পর ঘূর্ণিঝড়ের খবর শুনতে থাকুন।\n\nদলিলপত্র ও টাকা-পয়সা পলিথিনে মুড়ে নিজের শরীরের সঙ্গে বেঁধে রাখুন অথবা সুনির্দিষ্ট স্থানে পরিবারের সদস্যদের জানিয়ে মাটিতে পুঁতে রাখুন।\n\nটিউবওয়েলের মাথা খুলে পৃথকভাবে সংরক্ষণ করতে হবে এবং টিউবওয়েলের খোলা মুখ পলিথিন দিয়ে ভালভাবে আটকে রাখতে হবে যাতে ময়লা বা লবণাক্ত পানি টিউবওয়েলের মধ্যে প্রবেশ না করতে পারে।\n");
		dt3.setText("রাস্তা-ঘাটের উপর উপড়ে পড়া গাছপালা সরিয়ে ফেলুন যাতে সহজে সাহায্যকারী দল আসতে পারে এবং দ্রুত যোগাযোগ সম্ভব হয়।\n\nআশ্রয়কেন্দ্র হতে মানুষকে বাড়ি ফিরতে সাহায্য করুন এবং নিজের ভিটায় বা গ্রামে অন্যদের মাথা গোঁজার ঠাঁই করে দিন।\n\nঅতি দ্রুত উদ্ধার দল নিয়ে খাল, নদী, পুকুর ও সমুদ্রে ভাসা বা বনাঞ্চলে বা কাদার মধ্যে আটকে পড়া লোকদের উদ্ধার করুন।\n\nঘূর্ণিঝড় ও জলোচ্ছ্বাসে ক্ষতিগ্রস্ত জনসাধারণ যাতে শুধু এনজিও বা সরকারি সাহায্যের অপেক্ষায় বসে না থেকে নিজে যেন অন্যকে সাহায্য করে সে বিষয়ে সচেষ্ট হতে হবে।\n\nরিলিফের মুখাপেক্ষি না হয়ে নিজের পায়ে দাঁড়াতে সচেষ্ট হোন। রিলিফের পরিবর্তে কাজ করুন। কাজের সুযোগ সৃষ্টি করুন। রিলিফ যেন মানুষকে কর্মবিমুখ না করে কাজে উৎসাহী করে সেভাবে রিলিফ বিতরণ করতে হবে।\n\nদ্বীপের বা চরের নিকটবর্তী কাদার মধ্যে আটকে পড়া লোকদের উদ্ধারের জন্য দলবদ্ধ হয়ে দড়ি ও নৌকার সাহায্যে লোক উদ্ধারকর্ম আরম্ভ করুন। কাদায় আটকে পড়া লোকের কাছে দড়ি বা বাঁশ পৌঁছে দিয়ে তাকে উদ্ধার কাজে সাহায্য করা যায়।\n\nঝড় একটু কমলেই ঘর থেকে বের হবেন না। পরে আরও প্রবল বেগে অন্যদিক থেকে ঝড় আসার সম্ভাবনা বেশি থাকে।\n\nপুকুরের বা নদীর পানি ফুটিয়ে পান করুন। বৃষ্টির পানি ধরে রাখুন।\n\nনারী, বৃদ্ধ, প্রতিবন্ধী ও অসুস্থ লোকদের জন্য বিশেষ ব্যবস্থায় ত্রাণ বন্টন (আলাদা লাইনে) করুন।\n\nদ্রুত উৎপাদনশীল ধান ও শাক-সব্জির জন্য জমি প্রস্তুত করুন, বীজ সংগ্রহ করুন এবং কৃষিকাজ শুরু করুন যাতে যথাসম্ভব তাড়াতাড়ি ফসল ঘরে আসে।\n");
	}

}