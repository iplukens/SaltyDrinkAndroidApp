package com.example.saltydrinkandroidapp;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
public class GameHandler {
	static GameHandler instance;
	private Activity context;
	private Button changeBetButton;
	private Button selectBet1Button;
	private Button selectBet2Button;
	private Button selectBet3Button;
	private ImageButton editNameButton;
	private boolean isDialogDisplayed = false;

	public static GameHandler instanceOf() {
		if (instance == null) {
			instance = new GameHandler();
		}
		return instance;
	}

	// TODO make this actually a singleton, or remove it from being a singleton
	public GameHandler(Looper looper) {
		instance = this;
	}

	/**
	 * Private constructor singleton
	 */
	private GameHandler() {
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

		editNameButton = (ImageButton) context
				.findViewById(R.id.editNameButton);
		editNameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				editName(v);
			}

		});

		selectBet1Button = (Button) context.findViewById(R.id.bettting1Drink);
		selectBet2Button = (Button) context.findViewById(R.id.bettting2Drink);
		selectBet3Button = (Button) context.findViewById(R.id.bettting3Drink);

		//On init is yellow
		selectBet1Button.setBackgroundColor(Color.parseColor("#ffd700"));
		
		selectBet1Button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setBet(1);
				selectBet1Button.setBackgroundColor(Color.parseColor("#ffd700"));
				setBidButtonBackgrounds(GameModel.instanceOf().color);
			}

		});

		selectBet2Button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setBet(2);
				selectBet2Button.setBackgroundColor(Color.parseColor("#ffd700"));
				setBidButtonBackgrounds(GameModel.instanceOf().color);
			}

		});

		selectBet3Button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setBet(3);
				selectBet3Button.setBackgroundColor(Color.parseColor("#ffd700"));
				setBidButtonBackgrounds(GameModel.instanceOf().color);
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
			setBidButtonBackgrounds(BetColor.RED);
			button.setBackgroundColor(Color.parseColor("#FF0000"));
		} else {
			setBidButtonBackgrounds(BetColor.BLUE);
			button.setBackgroundColor(Color.parseColor("#0000FF"));
		}
	}

	private void setBidButtonBackgrounds(BetColor color) {
		long bid = GameModel.instanceOf().getBetAmount();
		int backgroundColor = (color == BetColor.RED) ? Color.parseColor("#FF0000") : Color.parseColor("#0000FF");
		((Button) context.findViewById(R.id.bettingDirection)).setBackgroundColor(backgroundColor);
		if(bid != 1){
			((Button) context.findViewById(R.id.bettting1Drink)).setBackgroundColor(backgroundColor);
		}
		
		if(bid != 2){
			((Button) context.findViewById(R.id.bettting2Drink)).setBackgroundColor(backgroundColor);
		}
		
		if(bid != 3){
			((Button) context.findViewById(R.id.bettting3Drink)).setBackgroundColor(backgroundColor);
		}
	}
	
	public void updateBetStatus() {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new updateBetStatusThread(activity));
	}

	private void setBet(long value) {
		GameModel.instanceOf().setBetAmount(value);

	}

	public void updateOpponentBetInfo() {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new updateOpponentBetInfoThread(activity));
		
	}

	public void startOpponentBetInfo() {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new StartOpponentBetInfoThread(activity));
	}

	public void updatePlayerList() {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new UpdatePlayerListThread(activity));
		
	}

	public void updateMatchOutcome() {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new UpdateMatchOutcomeThread(activity));
	}
}
