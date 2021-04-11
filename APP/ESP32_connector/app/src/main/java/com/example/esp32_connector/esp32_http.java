package com.example.esp32_connector;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class esp32_http {
    String horizon;
    String vertical;

    public esp32_http(){
        this.horizon = "0";
        this.vertical = "0";
    }

    public void setHorizon(){
        String url;
        final URL _url;

        if(horizon.equals("1")){
            horizon = "0";
            url = "http://yeomhome.iptime.org:8808/control?var=hmirror&val=0";
        }else{
            horizon = "1";
            url = "http://yeomhome.iptime.org:8808/control?var=hmirror&val=1";
        }
        //url = String.format("http://yeomhome.iptime.org:8808/control?var=hmirror&val=%s", horizon);
        try {
            _url = new URL(url);
            @SuppressLint("StaticFieldLeak")
            AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
                @Override
                protected Void doInBackground(String... strings) {
                    HttpURLConnection conn;
                    try {
                        conn = (HttpURLConnection) _url.openConnection();
                        conn.connect();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            asyncTask.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setVertical(){
        String url;
        final URL _url;
        if(vertical.equals("1")){
            vertical = "0";
            url = "http://yeomhome.iptime.org:8808/control?var=vflip&val=0";
        }else{
            vertical = "1";
            url = "http://yeomhome.iptime.org:8808/control?var=vflip&val=1";
        }
        //url = String.format("http://yeomhome.iptime.org:8808/control?var=vflip&val=%s", vertical);
        try {
            _url = new URL(url);
            @SuppressLint("StaticFieldLeak")
            AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
                @Override
                protected Void doInBackground(String... strings) {
                    HttpURLConnection conn;
                    try {
                        conn = (HttpURLConnection) _url.openConnection();
                        conn.connect();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            asyncTask.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
