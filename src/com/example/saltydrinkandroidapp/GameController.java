package com.example.saltydrinkandroidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This class updates the game model.
 * 
 * @author Chris
 * 
 */
public class GameController {
	static GameController instance;
	private Activity context;
	private Button changeBetButton;
	private ImageButton editNameButton;
	private boolean isDialogDisplayed = false;
	
	public static GameController instanceOf() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	
	/**
	 * Private constructor singleton
	 */
	private GameController() {
		
	}

	public void setContextAndSetupController(Context context) {
		this.context = (Activity) context;
		setupController();
	}

	private void setupController() {
		changeBetButton = (Button) context.findViewById(R.id.bettingDirection);
		changeBetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeBet(v);
			}
		});
		
		editNameButton = (ImageButton) context.findViewById(R.id.editNameButton);
		editNameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				editName(v);
			}

		});
		
	}


	private void editName(View v) {
		if (!isDialogDisplayed) {
			isDialogDisplayed = true;
			new EditNameDialog(context);
		}
		isDialogDisplayed = false;
	}
	
	private void changeBet(View view) {
		BetColor color = GameModel.instanceOf().changeBet();
		Button button = (Button) context.findViewById(R.id.bettingDirection);
		if (color == BetColor.RED) {
			button.setBackgroundColor(Color.parseColor("#FF0000"));
		} else {
			button.setBackgroundColor(Color.parseColor("#0000FF"));
		}
	}

	public void displayBetStatus() {
		TextView displayResults = (TextView) context
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

	public void displayOpponent(View view, String opponentName) {
		TextView opponentNameText = (TextView) context
				.findViewById(R.id.opponentName);
		opponentNameText.setText(opponentName);
	}
}
