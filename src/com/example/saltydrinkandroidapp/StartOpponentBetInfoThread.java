package com.example.saltydrinkandroidapp;

import android.app.Activity;
import android.widget.TextView;

public class StartOpponentBetInfoThread implements Runnable {
	private Activity activity;

	public StartOpponentBetInfoThread(Activity activity) {
		this.activity = activity;
	}

	public void run() {

		//TODO finish this
		TextView opponentNameText = (TextView) activity
				.findViewById(R.id.opponentName);
		opponentNameText.setText(GameModel.instanceOf().opponentName);
		
		TextView opponentBidAmount = (TextView) activity
				.findViewById(R.id.opponentBidAmount);
		opponentBidAmount.setText("?");
		
		TextView opponentBidColor = (TextView) activity
				.findViewById(R.id.opponentBidColor);
		opponentBidColor.setText("Mystery");
	}

}
