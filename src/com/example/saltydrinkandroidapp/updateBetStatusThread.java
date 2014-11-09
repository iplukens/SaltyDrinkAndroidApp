package com.example.saltydrinkandroidapp;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

public class updateBetStatusThread implements Runnable {

	private Activity activity;

	public updateBetStatusThread(Activity activity) {
		this.activity = activity;
	}

	public void run() {
		TextView displayResults = (TextView) activity
				.findViewById(R.id.latestResults);
		switch (GameModel.instanceOf().getMostRecentBetStatus()) {
		case RED_WINS:
			displayResults.setBackgroundColor(Color.parseColor("#0000FF"));
			displayResults.setText("Red Wins");
			break;
		case BLUE_WINS:
			displayResults.setBackgroundColor(Color.parseColor("#FF0000"));
			displayResults.setText("Blue Wins");
			break;

		default:
			displayResults.setBackgroundColor(Color.parseColor("#CCCCCC"));
			displayResults.setText("Match is currently "
					+ GameModel.instanceOf().getMostRecentBetStatus()
							.toString());

		}
	}
}
