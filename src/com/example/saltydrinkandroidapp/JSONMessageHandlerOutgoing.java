package com.example.saltydrinkandroidapp;

import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessageHandlerOutgoing {
	
	/**
	 * Used to send JSON containing betResponses to server
	 * @param color you are betting on
	 * @param betAmount to place 
	 * @param gameId of game player wishes to best against
	 * @throws JSONException
	 */
	public static void sendBetResponseToServer(BetColor color, long betAmount, long gameId) throws JSONException {
		JSONObject response = new JSONObject();
		response.put("type", "BET");
		response.put("betColor", color.toString());
		response.put("betAmount", betAmount);
		response.put("gameId", gameId);
		response.put("token", Client.getToken());
		Client.send(response);
	}

	/**
	 * Used to send JSON containing RoomRequests to Server to ask for a status of the room.
	 * @param playerUsername userName for server to display other plays
	 * @param gameId that we would like information about
	 * @throws JSONException
	 */
	public static void sendRoomRequestToServer(String playerUsername, long gameId) throws JSONException {
		JSONObject request = new JSONObject();
		request.put("type", "ROOM");
		request.put("playerId", playerUsername);
		request.put("gameId", gameId);
		request.put("token", Client.getToken());
		Client.send(request);
	}
	
	/**
	 * Used to send JSON containing UserNameUpdates to Server to let the server tell
	 * participates our name has changed.
	 * @param playerUsername userName for server to display other plays
	 * @param gameId that we would like information about
	 * @throws JSONException
	 */
	public static void sendNameUpdateRequestToServer(String playerUsername, long gameId) throws JSONException {
		JSONObject request = new JSONObject();
		request.put("type", "NAME_UPDATE");
		request.put("playerId", playerUsername);
		request.put("gameId", gameId);
		request.put("token", Client.getToken());
		Client.send(request);
	}
	
}
