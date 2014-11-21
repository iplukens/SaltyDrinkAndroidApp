package com.example.saltydrinkandroidapp;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

public class updateBetStatusThread implements Runnable {

	private Activity activity;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public updateBetStatusThread(Activity activity) {
		this.activity = activity;
	}

	public void run() {
		TextView displayResults = (TextView) activity
				.findViewById(R.id.latestResults);
		ArrayList<Button> buttons = new ArrayList<Button>();
		buttons.add((Button) activity.findViewById(R.id.bettingDirection));
		buttons.add((Button) activity.findViewById(R.id.bettting1Drink));
		buttons.add((Button) activity.findViewById(R.id.bettting2Drink));
		buttons.add((Button) activity.findViewById(R.id.bettting3Drink));
		lockButtons();
		switch (GameModel.instanceOf().getMostRecentBetStatus()) {
		case RED_WINS:
			displayResults.setBackgroundColor(Color.parseColor("#FF0000"));
			displayResults.setText("Red Wins");
			break;
		case BLUE_WINS:
			displayResults.setBackgroundColor(Color.parseColor("#0000FF"));
			displayResults.setText("Blue Wins");
			break;

		default:
			if(GameModel.instanceOf().getMostRecentBetStatus() == ResultType.OPEN || GameModel.instanceOf().getMostRecentBetStatus() == ResultType.CANCELED){
				unlockButtons();
			}
			
			displayResults.setBackgroundColor(Color.parseColor("#CCCCCC"));
			displayResults.setText("Match is currently "
					+ GameModel.instanceOf().getMostRecentBetStatus()
							.toString());

		}
	}

	private void lockButtons() {
		for(Button button : buttons){
			button.setEnabled(false);
		}
	}

	private void unlockButtons() {
		for(Button button : buttons){
			button.setEnabled(true);
		}
	}
}
