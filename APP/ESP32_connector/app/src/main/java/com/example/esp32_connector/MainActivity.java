package com.example.esp32_connector;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button stream_start;
    Button stream_stop;
    Button app_end;
    Button horizon;
    Button vertical;
    //ImageView img;
    //ImageLoadTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stream_start = (Button)findViewById(R.id.start);
        stream_stop = (Button)findViewById(R.id.stop);
        app_end = (Button)findViewById(R.id.exit);
        horizon = (Button)findViewById(R.id.horizon);
        vertical = (Button)findViewById(R.id.vertical);
        //img = (ImageView)findViewById(R.id.stream);

        //final String url = "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2";
        //final String url = "http://192.168.35.227:81/stream";
        final String url = "http://yeomhome.iptime.org:7707/stream";
        final esp32_http conn = new esp32_http();
        /*
        stream_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                task = new ImageLoadTask(url, img);
                sendImageRequest(task);
            }
        });
        stream_stop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stopImageRequest(task);
            }
        });

         */
        final MjpegView viewer = (MjpegView) findViewById(R.id.mjpegView);
        viewer.setMode(MjpegView.MODE_FIT_WIDTH);
        viewer.setAdjustHeight(true);
        viewer.setUrl(url);
        stream_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewer.startStream();
                Toast myToast = Toast.makeText(MainActivity.this,
                        getString(R.string.toast_start_stream),
                        Toast.LENGTH_SHORT);
                myToast.show();

            }
        });
        stream_stop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                viewer.stopStream();
                Toast myToast = Toast.makeText(MainActivity.this,
                        getString(R.string.toast_stop_stream),
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
        app_end.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                finishAndRemoveTask();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        horizon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                conn.setHorizon();
                Toast myToast = Toast.makeText(MainActivity.this,
                        "hor",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
        vertical.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                conn.setVertical();
                Toast myToast = Toast.makeText(MainActivity.this,
                        "ver",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }
    /*
    private void sendImageRequest(ImageLoadTask task){
        try {
            task.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void stopImageRequest(ImageLoadTask task){
        try {
            if(task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                task.cancel(true);
                Log.d("hi", "task finished");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    */
}
