package com.example.saltydrinkandroidapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SaltyDrinker extends Activity {
	protected static final String TAG = "Salty";
	private String twitchStreamURL;
	public Handler resultHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_salty_drinker);
		GameHandler.instanceOf().setContextAndSetupController(this);
		ViewController.instanceOf().setContext(this);
		
		 try {
			setupVideo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  VideoView videoView = (VideoView)findViewById(R.id.twitchVideo);
		  MediaController mc = new MediaController(this);
		  videoView.setMediaController(mc);


		  Uri uri = Uri.parse(twitchStreamURL);
		  
		  videoView.setVideoURI(uri);

		  videoView.requestFocus();
		  videoView.start();
		  try {
			Client client = new Client("192.168.0.102", 11112);
			client.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}	  
	}

	private void setupVideo() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException, ExecutionException {

		AsyncTask<String, Void, HttpResponse> task = new NetworkTask().execute("http://api.twitch.tv/api/channels/saltybet/access_token");
		
		HttpResponse response = task.get();
		BufferedReader serverResponse = new BufferedReader(new java.io.InputStreamReader(response.getEntity().getContent()));
		String line = serverResponse.readLine();
	    String token = URLEncoder.encode(line.split("\",\"sig")[0].split("token\":\"")[1].replaceAll("\\\\", ""), "UTF-8");
	    String sig = URLEncoder.encode(line.split("\",\"mobile_restricted")[0].split("sig\":\"")[1], "UTF-8");
		serverResponse.close(); 
		task = new NetworkTask().execute("http://usher.twitch.tv/select/saltybet.json?nauthsig=" + sig + "&nauth=" + token + "&allow_source=true");
		response = task.get();
		serverResponse = new BufferedReader(new java.io.InputStreamReader(response.getEntity().getContent()));

	    line = null;
	    while((line = serverResponse.readLine())!= null){
	    	if(line.contains("http")){
	    		twitchStreamURL = line;
	    		break;
	    	}
	    }
	    serverResponse.close();		
	}	
}
