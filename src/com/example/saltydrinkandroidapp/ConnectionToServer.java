package com.example.saltydrinkandroidapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionToServer {
	 ObjectInputStream in;
	 ObjectOutputStream out;
     Socket socket;
     
     ConnectionToServer(Socket socket) throws IOException {
         this.socket = socket;
         out =new ObjectOutputStream(socket.getOutputStream());
         in = new ObjectInputStream(socket.getInputStream());


         Thread read = new Thread(){
             public void run(){
                 while(true){
                     try{
                         Object obj = in.readObject();
                         if(obj!= null) {
                         Client.enqueueMessage(obj);
                         }
                     }
                     catch(IOException | ClassNotFoundException e){ e.printStackTrace(); }
                 }
             }
         };

         read.setDaemon(true);
         read.start();
     }

     public boolean getActive(){
    	 return true;
     }
     
     public void write(String obj) throws IOException {
         out.writeObject(obj);
         out.flush();
     }


}
