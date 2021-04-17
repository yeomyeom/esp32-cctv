package com.example.esp32_connector;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class esp32_http extends AsyncTask<Void, Void, Void>{
    String homeUrl;
    URL executeUrl;
    HttpURLConnection conn;

    public esp32_http(String homeUrl){
        this.homeUrl = homeUrl;
    }

    public void setHorizon(String horizon){
        String url;
        url = String.format("%svar=hmirror&val=%s",homeUrl ,horizon);
        setUrl(url);
    }
    public void setVertical(String vertical){
        String url;
        url = String.format("%svar=vflip&val=%s",homeUrl, vertical);
        setUrl(url);
    }
    public void setResolution(String i){
        String url;
        url = String.format("%svar=framesize&val=%s",homeUrl, i);
        setUrl(url);
    }
    public void setQuality(String i){
        String url;
        url = String.format("%svar=quality&val=%s",homeUrl, i);
        setUrl(url);
    }
    private void setUrl(String url){
        try {
            executeUrl = new URL(url);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36";
        try {
            conn = (HttpURLConnection) executeUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.getInputStream();
            //conn.connect(); //이거 말 안들음
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
