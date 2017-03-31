package com.healthplus.healthplus.Entity.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthplus.healthplus.Boundary.API.TwilioAPI;
import com.healthplus.healthplus.Boundary.Services.ActionHeadService;
import com.healthplus.healthplus.Control.Utils;
import com.healthplus.healthplus.Entity.Fragments.RootMap;
import com.healthplus.healthplus.R;

public class MainActivity extends AppCompatActivity {

    ImageView calorie_module,workout_module,run_module;
    ImageView image_workout;
    EditText food_name;
    TextView tv1,tv2,tv3,tv4,tv5;
    TextView cal,hbeat,distance;
    ProgressDialog progressDialog,progressDialog1;
    LinearLayout gym,running;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setinit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, ActionHeadService.class));
    }

    private void init()
    {
        calorie_module=(ImageView) findViewById(R.id.food_module);
        workout_module=(ImageView) findViewById(R.id.workout_module);
        image_workout=(ImageView)findViewById(R.id.sos);

        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);

        running=(LinearLayout)findViewById(R.id.running);

        progressDialog=new ProgressDialog(this);
        progressDialog1=new ProgressDialog(this);

        cal=(TextView)findViewById(R.id.calcount);
        hbeat=(TextView)findViewById(R.id.avg_hb);
        distance=(TextView)findViewById(R.id.distance);
        gym=(LinearLayout)findViewById(R.id.gym);





        tv1.setTypeface(new Utils().getFontType(this));
        tv2.setTypeface(new Utils().getFontType(this));
        tv3.setTypeface(new Utils().getFontType(this));
        tv4.setTypeface(new Utils().getFontType(this));
        tv5.setTypeface(new Utils().getFontType(this));


        cal.setTypeface(new Utils().getFontType(this));
        hbeat.setTypeface(new Utils().getFontType(this));
        distance.setTypeface(new Utils().getFontType(this));

        SharedPreferences preferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        hbeat.setText(preferences.getString("heart","NA"));
        distance.setText(preferences.getString("distance","NA"));

      //  new TwilioAPI().makecall();
    }

    private void setinit()
    {



       /* run_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RootMap.class));
            }
        });
*/




        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,RootMap.class));

            }
        });


        image_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TwilioAPI().makecall(MainActivity.this);
            }
        });



        calorie_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                progressDialog.show();
                progressDialog.setContentView(R.layout.food_menu_dialog);

                LinearLayout manual,barcode;
                TextView tv1,tv2,tv3;

                tv1=(TextView) progressDialog.findViewById(R.id.tv1);
                tv2=(TextView) progressDialog.findViewById(R.id.tv2);
                tv3=(TextView) progressDialog.findViewById(R.id.tv3);


                tv1.setTypeface(new Utils().getFontType(MainActivity.this));
                tv2.setTypeface(new Utils().getFontType(MainActivity.this));
                tv3.setTypeface(new Utils().getFontType(MainActivity.this));

                manual=(LinearLayout) progressDialog.findViewById(R.id.manual);
                barcode=(LinearLayout) progressDialog.findViewById(R.id.barcode);

                manual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progressDialog.dismiss();
                        startActivity(new Intent(MainActivity.this,CalorieCheckActivity.class));
                    }
                });


                barcode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.dismiss();
                        startActivity(new Intent(MainActivity.this,BarCodeScannerActivity.class));
                    }
                });





            }
        });


        workout_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RootMap.class));
            }
        });



        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                progressDialog1.show();
                progressDialog1.setContentView(R.layout.food_menu_dialog);

                LinearLayout pregnancy,normal;
                TextView tv1,tv2,tv3;

                tv1=(TextView) progressDialog1.findViewById(R.id.tv1);
                tv2=(TextView) progressDialog1.findViewById(R.id.tv2);
                tv3=(TextView) progressDialog1.findViewById(R.id.tv3);


                tv2.setText("Pregnancy mode");
                tv3.setText("Workout mode");


                tv1.setTypeface(new Utils().getFontType(MainActivity.this));
                tv2.setTypeface(new Utils().getFontType(MainActivity.this));
                tv3.setTypeface(new Utils().getFontType(MainActivity.this));

                normal=(LinearLayout) progressDialog1.findViewById(R.id.manual);
                pregnancy=(LinearLayout) progressDialog1.findViewById(R.id.barcode);

                pregnancy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainActivity.this, PregnancyActivity.class);


                        progressDialog1.dismiss();


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                    makeSceneTransitionAnimation(MainActivity.this, image_workout, getString(R.string.accept));

                            startActivity(intent, options.toBundle());
                        }
                        else {
                            startActivity(intent);
                        }
                    }
                });


                normal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, WorkOutActivity.class);

                        progressDialog1.dismiss();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                    makeSceneTransitionAnimation(MainActivity.this, image_workout, getString(R.string.accept));

                            startActivity(intent, options.toBundle());
                        }
                        else {
                            startActivity(intent);
                        }
                    }
                });












            }
        });
    }
}
