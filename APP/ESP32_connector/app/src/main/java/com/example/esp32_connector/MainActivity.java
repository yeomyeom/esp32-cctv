package com.example.esp32_connector;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnStreamStart;
    Button btnAppShutdown;
    Switch swiHorizon;
    Switch swiVertical;
    RadioButton radioXGA;
    RadioButton radioVGA;
    RadioGroup radioGroup;
    Boolean streaming;
    BackgroundTask streamTask;
    String horizon;
    String vertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStreamStart = (Button)findViewById(R.id.start);
        btnAppShutdown = (Button)findViewById(R.id.exit);
        swiHorizon = (Switch)findViewById(R.id.horizon);
        swiVertical = (Switch)findViewById(R.id.vertical);
        radioXGA = (RadioButton) findViewById(R.id.radioXGA);
        radioVGA = (RadioButton) findViewById(R.id.radioVGA);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        streaming = false;
        horizon = "0";
        vertical = "0";
        //String mainUrl = "http://192.168.35.192";
        String mainUrl = "http://yeomhome.iptime.org";

        //String streamUrl = mainUrl + ":81/stream";
        String streamUrl = mainUrl + ":7707/stream";

        final MjpegView viewer = (MjpegView) findViewById(R.id.mjpegView);
        viewer.setMode(MjpegView.MODE_FIT_WIDTH);
        viewer.setAdjustHeight(true);
        viewer.setUrl(streamUrl);

        //final String controlUrl = mainUrl + ":80/control?";
        final String controlUrl = mainUrl + ":8808/control?";

        //시작버튼 클릭
        btnStreamStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(btnStreamStart.getText().equals(getString(R.string.stream_start))){
                    //스트림 시작
                    btnStreamStart.setText(R.string.stream_stop);
                    streaming = true;
                    streamTask = streamStart(viewer);
                }else{
                    //스트림 종료
                    btnStreamStart.setText(R.string.stream_start);
                    streaming = false;
                    streamStop(streamTask, viewer);
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                esp32_http conn = new esp32_http(controlUrl);
                switch(checkedId){
                    case R.id.radioVGA:
                        conn.setResolution("6");
                        conn.execute();
                        break;
                    case R.id.radioXGA:
                        conn.setResolution("8");
                        conn.execute();
                        break;
                    default:
                        break;
                }
                conn = new esp32_http(controlUrl);
                conn.setQuality("35");
                conn.execute();
            }
        });
        //수직 변경 스위치 클릭
        swiHorizon.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleHorizon(controlUrl);
            }
        });
        //수평 변경 스위치 클릭
        swiVertical.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleVertical(controlUrl);
            }
        });
        //앱 종료 스위치 클릭
        btnAppShutdown.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                finishAndRemoveTask();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
    private BackgroundTask streamStart(MjpegView mjpegController){
        streamTask = new BackgroundTask();//다 thread로 넘기거나 새로운 객체로 생성할것!
        streamTask.execute(mjpegController);
        Toast.makeText(MainActivity.this,
                getString(R.string.toast_start_stream),
                Toast.LENGTH_SHORT).show();
        return streamTask;
    }
    private void streamStop(BackgroundTask streamTask, MjpegView mjpegController){
        if(streamTask != null) {
            streamTask.shutdown(mjpegController);
            streamTask.cancel(false);
            Toast.makeText(MainActivity.this,
                    getString(R.string.toast_stop_stream),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void toggleHorizon(String controlUrl){
        esp32_http conn = new esp32_http(controlUrl);//재사용 불가해서 계속 생성해야함
        if(horizon.equals("0")){
            horizon = "1";
        }else{
            horizon = "0";
        }
        conn.setHorizon(horizon);
        conn.execute();
    }
    private void toggleVertical(String controlUrl){
        esp32_http conn = new esp32_http(controlUrl);
        if(vertical.equals("0")){
            vertical = "1";
        }else{
            vertical = "0";
        }
        conn.setVertical(vertical);
        conn.execute();
    }
}
