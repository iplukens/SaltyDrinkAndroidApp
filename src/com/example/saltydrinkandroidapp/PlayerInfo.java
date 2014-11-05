package com.example.saltydrinkandroidapp;

public class PlayerInfo {
	private String playerName;
	private int drinksDrunk;
	private int playerIdNumber;
	
	public PlayerInfo(String playerName, int drinksDrunk, int playerIdNumber){
		this.setPlayerName(playerName);
		this.setDrinksDrunk(drinksDrunk);
		this.setPlayerIdNumber(playerIdNumber);
	}

	/**
	 * @return the playerIdNumber
	 */
	public int getPlayerIdNumber() {
		return playerIdNumber;
	}

	/**
	 * @param playerIdNumber the playerIdNumber to set
	 */
	public void setPlayerIdNumber(int playerIdNumber) {
		this.playerIdNumber = playerIdNumber;
	}

	/**
	 * @return the drinksDrunk
	 */
	public int getDrinksDrunk() {
		return drinksDrunk;
	}

	/**
	 * @param drinksDrunk the drinksDrunk to set
	 */
	public void setDrinksDrunk(int drinksDrunk) {
		this.drinksDrunk = drinksDrunk;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	
}
