package com.example.saltydrinkandroidapp;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class updateOpponentBetInfoThread implements Runnable {

	private Activity activity;

	public updateOpponentBetInfoThread(Activity activity) {
		this.activity = activity;
	}

	public void run() {

		//TODO this should be made prettier
		TextView opponentNameText = (TextView) activity
				.findViewById(R.id.opponentName);
		opponentNameText.setText(GameModel.instanceOf().opponentName);
		
		TextView opponentBidAmount = (TextView) activity
				.findViewById(R.id.opponentBidAmount);
		opponentBidAmount.setText(GameModel.instanceOf().opponentBetAmount);
		
		TextView opponentBidColor = (TextView) activity
				.findViewById(R.id.opponentBidColor);
		opponentBidColor.setText(GameModel.instanceOf().opponentBetColor.toString());
	}

}
