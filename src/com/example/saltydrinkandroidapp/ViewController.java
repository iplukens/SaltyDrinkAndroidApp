package com.example.saltydrinkandroidapp;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;

public class ViewController {
	static ViewController instance;
	private Activity context;
	private Button changeBetButton;
	private ImageButton editNameButton;
	
	public static ViewController instanceOf() {
		if (instance == null) {
			instance = new ViewController();
		}
		return instance;
	}
	
	/**
	 * Private constructor singleton
	 */
	private ViewController() {
		
	}
	
	public void setContext(Context context) {
		this.context = (Activity) context;
	}
}
