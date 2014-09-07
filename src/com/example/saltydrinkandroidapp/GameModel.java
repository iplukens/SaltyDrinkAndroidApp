package com.example.saltydrinkandroidapp;

import org.json.JSONException;
import org.json.JSONObject;

public class GameModel {
	static GameModel instance;
	BetColor color = BetColor.RED;

	public static GameModel instanceOf() {
		if (instance == null) {
			instance = new GameModel();
		}
		return instance;
	}

	private GameModel() {

	}

	public void update(JSONObject response) throws JSONException {
		ServerResponse.valueOf((String) (response.get("type")));
		switch (ServerResponse.valueOf((String) (response.get("type")))) {
		case RESULT:
			parseResult(response.get("result"));
		default:
			// TODO throw
			break;
		}

	}

	private void parseResult(Object response) {
		ResultType result = ResultType.valueOf((String) response);
		switch (result) {
		case BLUE_WINS:
			break;
		case CANCELED:
			break;
		case CLOSED:
			sendBet();
			break;
		case OPEN:
			break;
		case RED_WINS:
			break;
		case TIE:
			break;
		default:
			break;
		}
	}

	private void sendBet() {
		changeBet();
	}

	public void setupInitialGameState() {
		color = BetColor.RED;
	}

	public BetColor changeBet() {
		if (color == BetColor.RED) {
			color = BetColor.BLUE;
		} else {
			color = BetColor.RED;
		}
		JSONObject response = new JSONObject();
		try {
			response.put("type", "BET");
			response.put("bet", color.toString());
			Client.send(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return color;
	}

}
