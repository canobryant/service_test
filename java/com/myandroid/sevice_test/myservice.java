package com.myandroid.sevice_test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.BufferedWriter;
import java.util.Timer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.widget.TextView;
import android.widget.Toast;

public class myservice  extends Service {
    public static final String TAG = "MyService";
    private Timer  timer = new Timer(true);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand() executed");
        Toast.makeText(this, "service start ", Toast.LENGTH_SHORT).show();
        mytimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stop();
        Toast.makeText(this, "service end ", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void mytimer(){
        //Log.v("time", "time:" + str);
        //Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask(){
            public void run() {
                //todo something
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                String str = formatter.format(curDate);
                Log.v("time", "time:" + str);
                write(str);
            }
        };
        timer.schedule(timerTask, 0, 5000); //(taskName, wait(ms), repet(ms))

        //timer.cancel();
    }
    public void stop(){
        timer.cancel();
    }
    public void write(String str){
        TextView tv1 = new TextView(this);
        try
        {
            //FileWriter fw = new FileWriter("/sdcard/output.txt", false);//指定路徑
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "output.txt";
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
            bw.write(str);
            bw.newLine();
            bw.close();
            tv1.setText("檔案寫入成功");
        }
        catch(IOException e)
        {
            tv1.setText("檔案寫入失敗");
        }
    }
}