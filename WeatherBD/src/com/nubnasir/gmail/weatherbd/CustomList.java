package com.nubnasir.gmail.weatherbd;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomList extends CursorAdapter {

	CheckBox favoriteBox;
	TextView cityName, countryName;
	LinearLayout linearLayout;
	ArrayList<String> city_name;
	ArrayList<String> country_name;
	ArrayList<String> country_short;
	ArrayList<CheckBox> check_box;
	Context cont;

	public CustomList(Context context, Cursor c, ArrayList<String> city_name,
			ArrayList<String> country_name, ArrayList<String> country_short,
			ArrayList<CheckBox> check_box) {
		super(context, c, 0);
		this.city_name = city_name;
		this.country_name = country_name;
		this.country_short = country_short;
		this.check_box = check_box;
		cont=context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.all_city_list, parent, false);

		return retView;
	}

	@Override
	public void bindView(final View view, Context context, Cursor cursor) {
		

		linearLayout = (LinearLayout) view.findViewById(R.id.listlayoutbody);
		
		cityName = (TextView) view.findViewById(R.id.cityName);
		String cityS = cursor.getString(cursor.getColumnIndex("city_name"));
		cityName.setText(cityS);
		// city_name.add(cityS);

		countryName = (TextView) view.findViewById(R.id.countryName);
		String conS = cursor.getString(cursor.getColumnIndex("country_name"));
		countryName.setText(conS);
		// country_name.add(conS);
		// country_short.add(""+cursor.getString(cursor
		// .getColumnIndex("country_short")));

		favoriteBox = (CheckBox) view.findViewById(R.id.favoriteCheckBox);
		// check_box.add(favoriteBox);
		if (cursor.getInt(cursor.getColumnIndex("favorite")) == 0) {
			favoriteBox.setChecked(false);
			linearLayout.setBackgroundColor(Color.rgb(30, 144, 255));
		} else {
			favoriteBox.setChecked(true);
			linearLayout.setBackgroundColor(Color.rgb(34, 139, 34));
		}

	}
}