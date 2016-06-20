package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListForWarnNumber  extends ArrayAdapter<String> {
	private final Activity context;
	private String warn_nubmer[];
	private String warn_description[];

	public CustomListForWarnNumber(Activity context, String warn_number[], String warn_description[]) {
		super(context, R.layout.list_bmd, warn_number);
		this.context = context;
		this.warn_nubmer=warn_number;
		this.warn_description=warn_description;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.warn_signal_row, null, true);
		TextView warnNumber = (TextView) rowView.findViewById(R.id.warnNumber);
		TextView warnDescription = (TextView) rowView.findViewById(R.id.warnDescription);

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),"banglafont.ttf");
        warnNumber.setTypeface(custom_font);
		warnDescription.setTypeface(custom_font);
		
		
		warnNumber.setText(warn_nubmer[position]);
		warnDescription.setText(warn_description[position]);
		return rowView;
	}
}