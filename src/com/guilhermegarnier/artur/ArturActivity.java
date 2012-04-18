package com.guilhermegarnier.artur;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class ArturActivity extends Activity {
	protected TextView weeksLabel;
	protected TextView percentageLabel;
	protected TextView countdownLabel;

	protected CountDownTimer brewCountDownTimer;
	private TimerTask timer;

	private Date birthDate;
	private float weeks;
	private double percentage;
	private String countdown;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		weeksLabel = (TextView) findViewById(R.id.weeks);
		percentageLabel = (TextView) findViewById(R.id.percentage);
		countdownLabel = (TextView) findViewById(R.id.countdown);

		// TODO: fazer a data funcionar decentemente (isso eh 30/05/2012!!!)
		birthDate = new Date(112, 4, 30);
		startTimer();
	}

	private void calculate() {
		Date now = new Date();
		float diff = birthDate.getTime() - now.getTime();
		weeks = 40 - (diff / (1000 * 60 * 60 * 24 * 7));
		percentage = weeks / 0.4;
		calculateCountdown(diff);
	}

	private void calculateCountdown(float diff) {
		double days = Math.floor(diff / (1000 * 60 * 60 * 24));
		diff -= (days * 1000 * 60 * 60 * 24);
		double hour = Math.floor(diff / (1000 * 60 * 60));
		diff -= (hour * 1000 * 60 * 60);
		double minute = Math.floor(diff / (1000 * 60));
		diff -= (minute * 1000 * 60);
		double second = Math.floor(diff / 1000);
		countdown = String.format("%.0fd %02.0f:%02.0f:%02.0f", days, hour,
				minute, second);
	}

	private void showValues() {
		weeksLabel.setText(String.format("%.2f", weeks));
		percentageLabel.setText(String.format("%.2f%%", percentage));
		countdownLabel.setText(countdown);
	}

	private void startTimer() {
		final Handler handler = new Handler();
		timer = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						calculate();
						showValues();
					}
				});
			}
		};

		Timer t = new Timer();
		t.schedule(timer, 0, 1000);
	}
}