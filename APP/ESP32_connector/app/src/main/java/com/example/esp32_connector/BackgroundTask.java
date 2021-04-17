package com.example.esp32_connector;


import android.os.AsyncTask;
// < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형
//https://youngest-programming.tistory.com/11
public class BackgroundTask extends AsyncTask<MjpegView, Void, Void> {

    @Override
    // ... 은 길이가 정해져 있지 않은 배열을 의미함
    protected Void doInBackground(MjpegView... mjpeg) {
        mjpeg[0].startStream();
        return null;
    }

    protected void onCancelled(MjpegView mjpeg){
        super.onCancelled();
        mjpeg.stopStream();
    }

    public void shutdown(MjpegView mjpeg) {
        mjpeg.stopStream();
    }
}
