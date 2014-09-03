package com.example.saltydrinkandroidapp;

public class GameModel {
	static GameModel instance;
	BetColor color = BetColor.RED;
	
	public static GameModel instanceOf() {
		if(instance == null){
			instance = new GameModel();
		}
		return instance;
	}

	private GameModel(){
		
	}

	public void update(ServerResponse response) {
		switch (response){
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
		Client.send(color.toString());	
	}

	public void setupInitialGameState() {
		color = BetColor.RED;
	}

	public BetColor changeBet() {
		if(color == BetColor.RED){
			color = BetColor.BLUE;
		}
		else {
			color = BetColor.RED;
		}
		Client.send(color.toString());	
		return color;
	}
}
