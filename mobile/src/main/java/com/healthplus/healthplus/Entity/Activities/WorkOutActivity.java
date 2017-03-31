package com.healthplus.healthplus.Entity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.healthplus.healthplus.Boundary.Services.DataLayerListenerService;
import com.healthplus.healthplus.Control.Utils;
import com.healthplus.healthplus.R;

/**
 * Created by VSRK on 9/19/2016.
 */

public class WorkOutActivity extends AppCompatActivity {

    TextView heartbeat,tv1;
    Button workoutstate;
    Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activity);
        init();
        setinit();
    }

    private void init() {
        heartbeat = (TextView)findViewById(R.id.heartbeat_text);
        workoutstate=(Button)findViewById(R.id.workout_state);
        tv1=(TextView)findViewById(R.id.tv1);
        DataLayerListenerService.setHandler(handler);
    }

    private void setinit()
    {
        tv1.setTypeface(new Utils().getFontType(this));
        heartbeat.setTypeface(new Utils().getFontType(this));


        workoutstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workoutstate.getText().equals("START"))
                {
                    workoutstate.setText("STOP");
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            // message from API client! message from wear! The contents is the heartbeat.
                            if(heartbeat!=null)
                                heartbeat.setText(Integer.toString(msg.what)+" bpm");
                        }

                    };

                }
                else if (workoutstate.getText().equals("STOP"))
                {
                    DataLayerListenerService.setHandler(null);

                    Intent intent=new Intent(WorkOutActivity.this,WorkOutAnalysisActivity.class);
                    intent.putExtra("type","normal");
                    startActivity(intent);

                }

            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        // register our handler with the DataLayerService. This ensures we get messages whenever the service receives something.
        DataLayerListenerService.setHandler(handler);

    }




}