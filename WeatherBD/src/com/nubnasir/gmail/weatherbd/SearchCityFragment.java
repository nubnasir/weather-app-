package com.nubnasir.gmail.weatherbd;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class SearchCityFragment extends Fragment {
	private CustomList customAdapter;
	private DatabaseHelper databaseHelper;
	ArrayList<String> city_name = new ArrayList<String>();
	ArrayList<String> country_name = new ArrayList<String>();
	ArrayList<String> country_short = new ArrayList<String>();
	ArrayList<CheckBox> check_box = new ArrayList<CheckBox>();

	public static final String ARG_OS = "OS";
	int pos;
	String sql = "";

	ListView list;
	EditText searchText;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_layout, null);

		databaseHelper = new DatabaseHelper(view.getContext());

		try {
			databaseHelper.createDataBase();
			databaseHelper.openDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(view.getContext(), "Error: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		list = (ListView) view.findViewById(R.id.searchCitylist);
		list.setFocusable(false);

		searchText = (EditText) view.findViewById(R.id.searchText);

		searchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() >= 1) {
					city_name.clear();
					country_name.clear();
					country_short.clear();
					check_box.clear();

					sql = "SELECT _id, city_name, country_name, country_short, favorite FROM city WHERE city_name Like '"
							+ s.toString() + "%';";

					final Cursor cursor = databaseHelper.getResult(sql);

					cursor.moveToFirst();
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
									.moveToNext()) {

								city_name.add(cursor.getString(cursor
										.getColumnIndex("city_name")));
								country_name.add(cursor.getString(cursor
										.getColumnIndex("country_name")));
								country_short.add(cursor.getString(cursor
										.getColumnIndex("country_short")));

							}
						}
					}).start();

					new Handler().post(new Runnable() {
						@Override
						public void run() {
							customAdapter = new CustomList(getActivity(),
									databaseHelper.getResult(sql), city_name,
									country_name, country_short, check_box);
							list.setAdapter(customAdapter);
						}
					});
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View myview,
					int position, long id) {

				Intent weatherItent = new Intent(
						"com.nubnasir.gmail.weatherbd.WEATHERREPORT");
				weatherItent.putExtra("CITY", city_name.get(position));
				weatherItent.putExtra("COUNTRY", country_name.get(position));
				weatherItent.putExtra("COUNTRY_SHORT",
						country_short.get(position));

				startActivity(weatherItent);

			}
		});

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View list_view,
					int pos, long arg3) {

				CheckBox cb = (CheckBox) list_view
						.findViewById(R.id.favoriteCheckBox);
				LinearLayout linearLayout = (LinearLayout) list_view
						.findViewById(R.id.listlayoutbody);

				cb.performClick();
				if (cb.isChecked()) {
					databaseHelper.updateFavoriteList(city_name.get(pos),
							country_name.get(pos), 1);

					linearLayout.setBackgroundColor(Color.rgb(34, 139, 34));

					Toast.makeText(
							getActivity(),
							city_name.get(pos) + ", " + country_name.get(pos)
									+ " is added to favorite list",
							Toast.LENGTH_LONG).show();
				} else {
					databaseHelper.updateFavoriteList(city_name.get(pos),
							country_name.get(pos), 0);

					linearLayout.setBackgroundColor(Color.rgb(30, 144, 255));
					
					Toast.makeText(
							getActivity(),
							city_name.get(pos) + ", " + country_name.get(pos)
									+ " is removed from favorite list",
							Toast.LENGTH_LONG).show();
				}

				return true;
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			databaseHelper.close();
		} catch (Exception ex) {
		}
	}

}
