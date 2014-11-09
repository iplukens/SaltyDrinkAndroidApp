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
	
	private static void parseOpponentBid(JSONObject response) throws JSONException {
		GameModel.instanceOf().setOpponentBetAmount(response.getInt("bidAmount"));
		GameModel.instanceOf().setOpponentBetColor(response.getString("color"));
	}

	private static void parseResult(JSONObject response)throws JSONException {
		//TODO find betStatus
		ResultType result= ResultType.valueOf(response.getString("betStatus"));
		GameModel.instanceOf().handleResult(result);
	}

	private static void parseMatchUp(JSONObject response)throws JSONException {
		String opponentName = response.getString("opponentName");
		GameModel.instanceOf().setOpponentName(opponentName);
	}

	private static void parseToken(JSONObject response) throws JSONException {
		Client.setToken(response.getString("token"));
		JSONMessageHandlerOutgoing.sendRoomRequestToServer("Testing", (long) 1);
		GameModel.instanceOf().setupInitialGameState();
	}

	private static void parseRoomState(JSONObject response) throws JSONException {
		GameModel.instanceOf().createPlayerList((JSONArray) response.get("playerSet"));
		GameModel.instanceOf().updateBetStatus(ResultType.valueOf(response.getString("betStatus")));
		
	}
}
