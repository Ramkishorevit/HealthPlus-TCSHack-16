package com.healthplus.healthplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

/**
 * Created by VSRK on 9/19/2016.
 */

public class DashBoardActivity extends Activity {

    TextView mTextView,run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub watchViewStub) {
                mTextView = (TextView) stub.findViewById(R.id.workouts);
                run=(TextView) stub.findViewById(R.id.pregnancy);
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(DashBoardActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                run.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(DashBoardActivity.this,StepCalculatorActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}
