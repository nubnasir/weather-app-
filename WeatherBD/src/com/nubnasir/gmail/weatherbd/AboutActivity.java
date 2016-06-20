package com.nubnasir.gmail.weatherbd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AboutActivity  extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_layout);
	}

}
