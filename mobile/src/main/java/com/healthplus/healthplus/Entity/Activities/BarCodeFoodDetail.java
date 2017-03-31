package com.healthplus.healthplus.Entity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.healthplus.healthplus.Control.Utils;
import com.healthplus.healthplus.R;

/**
 * Created by ramkishorevs on 04/03/17.
 */

public class BarCodeFoodDetail extends AppCompatActivity {



    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,carbs,fats,protiens,calories,item_name;
    Button manual_add_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_food_detail);

        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        tv6=(TextView)findViewById(R.id.tv6);
        tv7=(TextView)findViewById(R.id.tv7);
        tv8=(TextView)findViewById(R.id.tv8);
        manual_add_btn=(Button)findViewById(R.id.manual_add_btn);

        carbs=(TextView)findViewById(R.id.carbs);
        item_name=(TextView)findViewById(R.id.item_name);
        protiens=(TextView)findViewById(R.id.protien);
        calories=(TextView)findViewById(R.id.calories);
        fats=(TextView)findViewById(R.id.fats);


        tv1.setTypeface(new Utils().getFontType(this));
        tv2.setTypeface(new Utils().getFontType(this));
        tv3.setTypeface(new Utils().getFontType(this));
        tv4.setTypeface(new Utils().getFontType(this));
        tv5.setTypeface(new Utils().getFontType(this));
        tv6.setTypeface(new Utils().getFontType(this));
        tv7.setTypeface(new Utils().getFontType(this));
        tv8.setTypeface(new Utils().getFontType(this));
        manual_add_btn.setTypeface(new Utils().getFontType(this));

        carbs.setTypeface(new Utils().getFontType(this));
        item_name.setTypeface(new Utils().getFontType(this));
        protiens.setTypeface(new Utils().getFontType(this));
        calories.setTypeface(new Utils().getFontType(this));
        fats.setTypeface(new Utils().getFontType(this));


        manual_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BarCodeFoodDetail.this,CalorieCheckActivity.class));
            }
        });


    }
}
