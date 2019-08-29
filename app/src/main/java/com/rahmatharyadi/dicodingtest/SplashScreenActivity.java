package com.rahmatharyadi.dicodingtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rahmatharyadi.dicodingtest.utility.Constanta;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends Activity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, Constanta.SPLASH_DELAY_TIME);
    }
}