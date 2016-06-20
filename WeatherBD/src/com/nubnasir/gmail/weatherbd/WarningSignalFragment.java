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

public class WarningSignalFragment  extends Fragment {
	
	public String seaBangla="সমুদ্র বন্দরের জন্য সংকেত সমূহ";
	public String seaEnglish="Signals for Maritime Ports";
	public String riverBangla="নদী বন্দরের জন্য সংকেত সমূহ";
	public String riverEnglish="Signals for Inland River Ports";
	public Button sea_warn_bangla;
	public Button sea_warn_english;
	public Button river_warn_bangla;
	public Button river_warn_english;

	public static final String ARG_OS = "OS";
	int pos;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.warning_signal_layout, null);
		
		sea_warn_bangla=(Button)view.findViewById(R.id.sea1);
		sea_warn_english=(Button)view.findViewById(R.id.sea2);
		river_warn_bangla=(Button)view.findViewById(R.id.river1);
		river_warn_english=(Button)view.findViewById(R.id.river2);
		

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),"banglafont.ttf");
        sea_warn_bangla.setTypeface(custom_font);
		river_warn_bangla.setTypeface(custom_font);
		
		sea_warn_bangla.setText(seaBangla);
		sea_warn_english.setText(seaEnglish);
		river_warn_bangla.setText(riverBangla);
		river_warn_english.setText(riverEnglish);
		
		sea_warn_bangla.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent warnIntent=new Intent("com.nubnasir.gmail.weatherbd.WARNSIGNALACTIVITY");
				warnIntent.putExtra("POSITION", "1");
				startActivity(warnIntent);
			}
		});
		

		sea_warn_english.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent warnIntent=new Intent("com.nubnasir.gmail.weatherbd.WARNSIGNALACTIVITY");
				warnIntent.putExtra("POSITION", "2");
				startActivity(warnIntent);
			}
		});


		river_warn_bangla.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent warnIntent=new Intent("com.nubnasir.gmail.weatherbd.WARNSIGNALACTIVITY");
				warnIntent.putExtra("POSITION", "3");
				startActivity(warnIntent);
			}
		});

		river_warn_english.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent warnIntent=new Intent("com.nubnasir.gmail.weatherbd.WARNSIGNALACTIVITY");
				warnIntent.putExtra("POSITION", "4");
				startActivity(warnIntent);
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void setArguments(Bundle args) {
		pos = args.getInt(ARG_OS);
	}

}
