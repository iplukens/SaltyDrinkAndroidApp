package com.example.saltydrinkandroidapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

public class GameModel {
	static GameModel instance;
	BetColor color = BetColor.RED;
	private long betAmount = 1;
	Set<String> players = new HashSet<String>();
	boolean isBettingLocked = false;
	ResultType mostRecentBetStatus = ResultType.CLOSED;
	String opponentName = "SALTY_BOT";
	
	//TODO these should be setup and come from elsewhere
	private long gameId = 1;
	private String playerUsername = "Player";
	int opponentBetAmount = 0;
	BetColor opponentBetColor = BetColor.UNKNOWN;
	
	public static GameModel instanceOf() {
		if (instance == null) {
			instance = new GameModel();
		}
		return instance;
	}

	private GameModel() {
	}


	public void updateBetStatus(ResultType status) {
		mostRecentBetStatus = status;
	}

	public void createPlayerList(JSONArray listOfPlayers) throws JSONException {
		players = new HashSet<String>();
		
		if(listOfPlayers!= null){
			for(int i =0; i < listOfPlayers.length(); i++){
				players.add(listOfPlayers.getString(i).toString());
			}
		}
	}

	/**
	 * This function needs a refactor gets result and updates the UI based on it.
	 * @param result
	 * @throws JSONException
	 */
	public void handleResult(ResultType result) throws JSONException {
		// TODO update results on UI;
		switch (result) {
		case BLUE_WINS:
			displayWinner(result);
			break;
		case CANCELED:
			break;
		case CLOSED:
			JSONMessageHandlerOutgoing.sendBetResponseToServer(color, betAmount, gameId);
			break;
		case OPEN:
			resetBets();
			break;
		case RED_WINS:
			displayWinner(result);
			break;
		case TIE:
			displayWinner(result);
			break;
		default:
			break;
		}
		updateBetStatus(result);
		
	}


	private void resetBets() {
		isBettingLocked = false;
		color = BetColor.RED;
		betAmount = 1;
	}

	private void displayWinner(ResultType result) {
		// TODO Auto-generated method stub

	}

	private void updateScores() {
		// TODO Auto-generated method stub

	}

	private void lockBets() {
		isBettingLocked = true;
		// Disable bet changing on main page
	}

	public void setupInitialGameState() {
		color = BetColor.RED;
		betAmount = 1;
	}

	public BetColor changeBet() {
		if (color == BetColor.RED) {
			color = BetColor.BLUE;
		} else {
			color = BetColor.RED;
		}
		return color;
	}


	public long getBetAmount() {
		return betAmount;
	}

	public ResultType getMostRecentBetStatus() {
		return mostRecentBetStatus;
	}

	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}

	public void setOpponentBetAmount(int betAmount) {
		opponentBetAmount = betAmount;
	}

	public void setOpponentBetColor(String betColor) {
		opponentBetColor = BetColor.valueOf(betColor);
	}

	public void setPlayerUsername(String name) throws JSONException {
		playerUsername = name;	
		JSONMessageHandlerOutgoing.sendNameUpdateRequestToServer(playerUsername, gameId);
	}

	public void setBetAmount(long value) {
		betAmount = value;
	}
}
