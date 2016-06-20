package com.nubnasir.gmail.weatherbd;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CustomProgressBar extends ProgressDialog {

	public CustomProgressBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			Method method = TextView.class.getMethod("setVisibility",
					Integer.TYPE);

			Field[] fields = this.getClass().getSuperclass()
					.getDeclaredFields();

			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase("mProgressNumber")) {
					field.setAccessible(true);
					TextView textView = (TextView) field.get(this);
					method.invoke(textView, View.GONE);
				} else if (field.getName().equalsIgnoreCase("mProgressPercent")) {
					field.setAccessible(true);
					TextView textView = (TextView) field.get(this);
					method.invoke(textView, View.GONE);
				}
			}
		} catch (Exception e) {
		}
	}
}