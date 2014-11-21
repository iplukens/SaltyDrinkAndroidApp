package com.example.saltydrinkandroidapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessageHandlerIncoming {
	static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Used to stage incoming JSON and distribute to a more appropriate handler based on input
	 * @throws JSONException 
	 */
	public static void parseServerResponse(JSONObject response) throws JSONException{
		try {
			log.log(Level.INFO,"Recieved " + response.toString());
			ServerResponse responseType = ServerResponse.valueOf((String) (response.get("type")));

			switch (responseType) {
			case MATCH_UP:
				parseMatchUp(response);
				break;
			case RESULT:
				parseResult(response);
				break;
			case ROOM_STATE:
				 parseRoomState(response);
				 break;
			case TOKEN:
				 parseToken(response);
				 break;
			case OPPONENT_BID:
				parseOpponentBid(response);
				break;
			case BID_RESPONSE:
				break;
			default:
				//TODO log
				throw new JSONException("JSON did not contain a response the handler understands.");
			}
		} catch (IllegalArgumentException e) {
			// TODO Log
		}
	}
	
	/**
	 * This function fills out what our opponent is bidding. If their name has changed
	 * since the last update this is when we would update that as well
	 * @param response
	 * @throws JSONException
	 */
	private static void parseOpponentBid(JSONObject response) throws JSONException {
		GameModel.instanceOf().setOpponentBetAmount(response.getInt("bidAmount"));
		GameModel.instanceOf().setOpponentBetColor(response.getString("color"));
		GameModel.instanceOf().setOpponentName(response.getString("name"));
		
		GameHandler.instanceOf().updateOpponentBetInfo();
	}

	/**
	 * This is likely the largest function as it covers 5 cases.
	 * Based on our bet and our opponents bet we determine who the winner is
	 * We display that info back to the player
	 * @param response
	 * @throws JSONException
	 */
	private static void parseResult(JSONObject response)throws JSONException {
		//TODO finish
		ResultType result= ResultType.valueOf(response.getString("betStatus"));
		GameModel.instanceOf().handleResult(result);
		GameHandler.instanceOf().updateBetStatus();
		GameHandler.instanceOf().updateMatchOutcome();
	}

	/**
	 * This function figures out who our new opponent is an resets
	 * displays their bet info to us with questions, as we do not know
	 * what they are currently bidding.
	 * @param response
	 * @throws JSONException
	 */
	private static void parseMatchUp(JSONObject response)throws JSONException {
		String opponentName = response.getString("opponentName");
		GameModel.instanceOf().setOpponentName(opponentName);
		
		GameHandler.instanceOf().startOpponentBetInfo();
	}

	/**
	 * This method is called on first connection to server it asks to join a room
	 * Its primary function is to store the token the server gives us so we 
	 * can make future requests.
	 * It reinits the game when it is recieved.
	 * @param response
	 * @throws JSONException
	 */
	private static void parseToken(JSONObject response) throws JSONException {
		Client.setToken(response.getString("token"));
		JSONMessageHandlerOutgoing.sendRoomRequestToServer("Testing", (long) 1);
		GameModel.instanceOf().setupInitialGameState();
	}

	/**
	 * This function gets the status of the room from the server
	 * If there are other players in the room we also refresh that.
	 * This updates the UI to show the current status as well as the new list of players.
	 * @param response
	 * @throws JSONException
	 */
	private static void parseRoomState(JSONObject response) throws JSONException {
		GameModel.instanceOf().createPlayerList((JSONArray) response.get("playerSet"));
		GameModel.instanceOf().updateBetStatus(ResultType.valueOf(response.getString("betStatus")));
		GameHandler.instanceOf().updateBetStatus();
		GameHandler.instanceOf().updatePlayerList();
	}
}
