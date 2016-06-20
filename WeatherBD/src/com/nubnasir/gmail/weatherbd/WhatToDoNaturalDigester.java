package com.nubnasir.gmail.weatherbd;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class WhatToDoNaturalDigester extends Fragment {
	public static final String ARG_OS = "OS";
	int pos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.whattodo_in_disaster, null);
		Button earthquake = (Button) view.findViewById(R.id.earthquake);

		Typeface custom_font = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "banglafont.ttf");
		earthquake.setTypeface(custom_font);

		earthquake.setText("ভূমিকম্পে করনীয়");
		earthquake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),
						WhatToDoEarthquakeActivity.class);
				startActivity(myIntent);

			}
		});

		Button cyclone = (Button) view.findViewById(R.id.cyclone);
		cyclone.setTypeface(custom_font);
		cyclone.setText("ঘূর্ণিঝড় ও জলোচ্ছ্বাসে করনীয়");

		cyclone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent myIntent = new Intent(getActivity(),
						WhatToDoInCyclone.class);
				startActivity(myIntent);

			}
		});

		Button kalboishakhi = (Button) view.findViewById(R.id.kalboishakhi);
		kalboishakhi.setTypeface(custom_font);
		kalboishakhi.setText("ঝড়-বজ্রপাতে করনীয়");

		kalboishakhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					Intent myIntent = new Intent(getActivity(),
							WhatToDoinKalboishakhi.class);
					
					startActivity(myIntent);

			}
		});
		

		Button flood = (Button) view.findViewById(R.id.flood);
		flood.setTypeface(custom_font);
		flood.setText("বন্যায় করনীয়");

		flood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					Intent myIntent = new Intent(getActivity(),
							WhatToDoInFloodActivity.class);
					
					startActivity(myIntent);

			}
		});
		return view;
	}

}
