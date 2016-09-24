package com.healthplus.healthplus.Entity.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.healthplus.healthplus.Boundary.Services.ActionHeadService;
import com.healthplus.healthplus.Entity.Fragments.RootMap;
import com.healthplus.healthplus.R;

public class MainActivity extends AppCompatActivity {

    CardView calorie_module,workout_module,run_module;
    ImageView image_workout;
    EditText food_name;

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
        calorie_module=(CardView)findViewById(R.id.calorie_module);
        workout_module=(CardView)findViewById(R.id.workout_module);
        image_workout=(ImageView)findViewById(R.id.image_workout);
        food_name=(EditText)findViewById(R.id.food_name);
        run_module=(CardView)findViewById(R.id.run_module);
    }

    private void setinit()
    {
        run_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RootMap.class));
            }
        });

        calorie_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(food_name.getText().toString())) {

                    Intent intent = new Intent(MainActivity.this, CalorieCheckActivity.class);
                    intent.putExtra("food_name",food_name.getText().toString());
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this,"Please enter the food name",Toast.LENGTH_SHORT).show();
            }
        });

        workout_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, WorkOutActivity.class);

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
}
