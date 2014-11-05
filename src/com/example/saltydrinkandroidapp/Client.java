package com.example.saltydrinkandroidapp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class Client extends AsyncTask<Void, Void, Void> {
	private static ConnectionToServer server;
	private static LinkedBlockingQueue<Object> messages = new LinkedBlockingQueue<Object>();
	private Socket socket;
	private int port;
	private String IP;
	private static String token = "";
	static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			socket = new Socket(IP, port);
			server = new ConnectionToServer(socket);
			
			while (server.getActive()) {
				Object message = messages.poll();
				if (message != null) {
					JSONObject response = (JSONObject) message;
					JSONMessageHandlerIncoming.parseServerResponse(response);	
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Client(String IPAddress, int port) throws IOException {
		IP = IPAddress;
		this.port = port;
	}

	public static void send(JSONObject response) {
		try {
			server.write(response);
			log.log(Level.INFO,"Sent " + response.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void enqueueMessage(Object obj) {
		messages.add(obj);
	}
	
	public static String getToken(){
		return token;
	}
	
	public static void setToken(String tokenValue){
		token = tokenValue;
	}
}
