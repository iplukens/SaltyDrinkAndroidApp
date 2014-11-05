package com.example.saltydrinkandroidapp;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

public class EditNameDialog extends AlertDialog {

	public EditNameDialog(Context context){
		super(context);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Edit Your Name");

		// Set up the input
		final EditText input = new EditText(context);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    public void onClick(DialogInterface dialog, int which) {
		    	if(input.getText().length() > 0) {
		    		try {
						GameModel.instanceOf().setPlayerUsername(input.getText().toString());
					} catch (JSONException e) {
						// move try catch logic elsewhere
						e.printStackTrace();
					}
		    	}
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});
		
		builder.show();
	}
}
