package com.example.saltydrinkandroidapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionToServer {
	 ObjectInputStream in;
     PrintWriter out;
     Socket socket;
     
     ConnectionToServer(Socket socket) throws IOException {
         this.socket = socket;
         out = new PrintWriter(new ObjectOutputStream(socket.getOutputStream()), true);
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
     
     public void write(String obj) {
         out.print(obj);
     }


}
