package com.example.saltydrinkandroidapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionToServer {
	 ObjectInputStream in;
	 ObjectOutputStream out;
     Socket socket;
     boolean run = true;
     
     ConnectionToServer(Socket socket) throws IOException {
         this.socket = socket;
         out =new ObjectOutputStream(socket.getOutputStream());
         in = new ObjectInputStream(socket.getInputStream());


         Thread read = new Thread(){
             public void run(){
                 while(run){
                     try{
                         Object obj = in.readObject();
                         if(obj!= null) {
                         JSONObject json = new JSONObject((String)obj); //Turn obj back to JSONObject
                         Client.enqueueMessage(json);
                         }
                     }
                     catch(IOException e){ 
                    	 e.printStackTrace(); 
                    	 run = false;
                    	 
                     } catch (JSONException e) {
						// TODO Auto-generated catch block
                    	run = false;
						e.printStackTrace();
					} catch (ClassNotFoundException e){
						 e.printStackTrace(); 
                    	 run = false;
					}
                 }
             }
         };

         read.setDaemon(true);
         read.start();
     }

     public boolean getActive(){
    	 return true;
     }
     
     public void write(JSONObject response) throws IOException {
         out.writeObject(response.toString());
         out.flush();
     }


}
