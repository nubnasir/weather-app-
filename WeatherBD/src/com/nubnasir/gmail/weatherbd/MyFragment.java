package com.nubnasir.gmail.weatherbd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MyFragment extends Fragment {
	public static final String ARG_OS = "OS";
	int pos;
	private int home_background;

	private Random random = new Random();
	Date dNow = new Date();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_layout, null);

		SimpleDateFormat ft = new SimpleDateFormat("HH.mm");
		double current_hour = Double.parseDouble("" + ft.format(dNow));

		DigitalClock dc = (DigitalClock) view.findViewById(R.id.digitalClock1);
		Typeface custom_font = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "time.ttf");
		dc.setTypeface(custom_font);
		TextView welcomeText = (TextView) view.findViewById(R.id.welcometext);
		Typeface custom_font2 = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "welcome.ttf");
		welcomeText.setTypeface(custom_font2);

		TextView greetingText = (TextView) view.findViewById(R.id.greetingtext);
		Typeface custom_font3 = Typeface.createFromAsset(inflater.getContext()
				.getAssets(), "greeting.ttf");
		greetingText.setTypeface(custom_font3);

		Button infoBT = (Button) view.findViewById(R.id.infoBT);

		infoBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),
						ImportantContactActivity.class);
				startActivity(myIntent);
			}
		});

		home_background = R.drawable.v_1;
		if (current_hour >= 5 && current_hour < 6) {
			home_background = R.drawable.v_1;
			greetingText.setText("The Sun is waking up!");

			dc.setTextColor(Color.rgb(245, 245, 245));
			welcomeText.setTextColor(Color.rgb(245, 245, 245));
			greetingText.setTextColor(Color.rgb(245, 245, 245));

		} else if (current_hour >= 6 && current_hour < 12) {

			int new_int = random.nextInt(3) + 1;
			if (new_int == 1) {
				home_background = R.drawable.m_1;
			} else if (new_int == 2) {
				home_background = R.drawable.m_2;
			} else if (new_int == 3) {
				home_background = R.drawable.m_3;
			} else if (new_int == 4) {
				home_background = R.drawable.m_4;
			}
			greetingText.setText("It's Morning!");

			dc.setTextColor(Color.rgb(25, 25, 112));
			welcomeText.setTextColor(Color.rgb(25, 25, 112));
			greetingText.setTextColor(Color.rgb(25, 25, 112));

		} else if (current_hour >= 12 && current_hour < 17) {

			int new_int = random.nextInt(1) + 1;

			if (new_int == 1) {
				home_background = R.drawable.a_1;
			} else if (new_int == 2) {
				home_background = R.drawable.a_2;
			}

			greetingText.setText("Good Afternoon!");

			dc.setTextColor(Color.rgb(25, 25, 112));
			welcomeText.setTextColor(Color.rgb(25, 25, 112));
			greetingText.setTextColor(Color.rgb(25, 25, 112));

		} else if (current_hour >= 17 && current_hour < 20) {

			int new_int = random.nextInt(3) + 1;

			if (new_int == 1) {
				home_background = R.drawable.e_1;
			} else if (new_int == 2) {
				home_background = R.drawable.e_2;
			} else if (new_int == 3) {
				home_background = R.drawable.e_3;
			} else if (new_int == 4) {
				home_background = R.drawable.e_4;
			}

			dc.setTextColor(Color.rgb(205, 201, 201));
			welcomeText.setTextColor(Color.rgb(205, 201, 201));
			greetingText.setTextColor(Color.rgb(205, 201, 201));

			greetingText.setText("Good Evening!");
		} else if ((current_hour >= 20 && current_hour <= 23.9)
				|| (current_hour >= 0 && current_hour < 5)) {

			int new_int = random.nextInt(3) + 1;

			if (new_int == 1) {
				home_background = R.drawable.n_1;
			} else if (new_int == 2) {
				home_background = R.drawable.n_2;
			} else if (new_int == 3) {
				home_background = R.drawable.n_3;
			} else if (new_int == 4) {
				home_background = R.drawable.n_4;
			}

			greetingText.setText("Good Night!");

			dc.setTextColor(Color.rgb(255, 250, 250));
			welcomeText.setTextColor(Color.rgb(255, 250, 250));
			greetingText.setTextColor(Color.rgb(255, 250, 250));

		}

		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackgroundDrawable(getResources().getDrawable(
					home_background));
		} else {
			view.setBackground(getResources().getDrawable(home_background));
		}

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
