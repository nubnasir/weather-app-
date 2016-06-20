package com.nubnasir.gmail.weatherbd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class EarthQuakeBdMapFragment extends Fragment {

	public static final String ARG_OS = "OS";
	int pos;
	View view;
	ImageView iv1, iv3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.earth_quake_map_bd, null);

		
		iv1 = (ImageView) view.findViewById(R.id.eqiv1);
		iv3 = (ImageView) view.findViewById(R.id.eqiv3);

		iv1.setImageResource(R.drawable.edz_bd);
		iv3.setImageResource(R.drawable.eq_map_bd);

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
