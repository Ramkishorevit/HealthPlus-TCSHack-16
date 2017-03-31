package com.healthplus.healthplus.Entity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.healthplus.healthplus.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ramkishorevs on 04/03/17.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);

        final Intent intent = new Intent(this, MainActivity.class);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                startActivity(intent);
                finishscreen();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 4000);


    }
    private void finishscreen() {
        this.finish();
    }

}
