package com.example.saltydrinkandroidapp;

import com.example.saltydrinkandroidapp.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.util.Base64;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection; 
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SaltyDrinker extends Activity {
	public static HttpEntity latestNetworkResponse;
	private String twitchStreamURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_salty_drinker);

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
	}

	private void setupVideo() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException, ExecutionException {

		
		AsyncTask<String, Void, HttpResponse> task = new NetworkTask().execute("http://api.twitch.tv/api/channels/saltybet/access_token");
		//JsonReader reader = new JsonReader(new BufferedReader(new java.io.InputStreamReader(connection.getInputStream())));
		HttpResponse response = task.get();
		BufferedReader serverResponse = new BufferedReader(new java.io.InputStreamReader(response.getEntity().getContent()));
		String line = serverResponse.readLine();
	    String token = URLEncoder.encode(line.split("\",\"sig")[0].split("token\":\"")[1].replaceAll("\\\\", ""), "UTF-8");
	    String sig = URLEncoder.encode(line.split("\",\"mobile_restricted")[0].split("sig\":\"")[1], "UTF-8");
		serverResponse.close(); 
		String test = "http://usher.twitch.tv/select/saltybet.json?nauthsig=" + sig + "&nauth=" + token + "&allow_source=true";
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
		//}
	    //serverResponse.close();
		//VideoView videoView = (VideoView)findViewById(R.id.twitchVideo);
		//  MediaController mc = new MediaController(this);
		//  videoView.setMediaController(mc);
		  
		//  String webAddress = "http://www.twitch.tv/saltybet";
		//  Uri uri = Uri.parse(webAddress);

		//  videoView.setVideoURI(uri);

		//  videoView.requestFocus();
		//  videoView.start();
		
	}

}
