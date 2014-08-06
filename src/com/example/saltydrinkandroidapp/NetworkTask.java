package com.example.saltydrinkandroidapp;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

public class NetworkTask  extends AsyncTask<String, Void, HttpResponse> {
    @Override
    protected HttpResponse doInBackground(String... params) {
        String link = params[0];
        HttpGet request = new HttpGet(link);
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        try {
            return client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        client.close();
    }
    }
}
