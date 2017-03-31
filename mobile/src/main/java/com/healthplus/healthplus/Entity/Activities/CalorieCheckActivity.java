package com.healthplus.healthplus.Entity.Activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthplus.healthplus.Boundary.API.FoodAPI;
import com.healthplus.healthplus.Boundary.API.RestarauntAPI;
import com.healthplus.healthplus.Boundary.Managers.CaloriesProviders;
import com.healthplus.healthplus.Control.Utils;
import com.healthplus.healthplus.Entity.Actors.Food;
import com.healthplus.healthplus.Entity.Actors.Restaraunt;
import com.healthplus.healthplus.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


/**
 * Created by VSRK on 8/23/2016.
 */

public class CalorieCheckActivity extends AppCompatActivity implements FoodAPI.ServerAuthenticateListener {

    RecyclerView recyclerView;
    List<Food.Data> data;
    FoodAPI foodAPI;
    RestarauntAPI restarauntAPI;
    View popupContainer,suggestionscontainer;
    String food_name;
    Button cal_btn;
    ImageView search;
    EditText food;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calorie_check_activity);
        init();
        setinit();
       // setdata();
    }

    private void init()
    {
        foodAPI=new FoodAPI();

        food=(EditText)findViewById(R.id.food);
        search=(ImageView) findViewById(R.id.search);

        food_name= food.getText().toString();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        food.setTypeface(new Utils().getFontType(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setinit()
    {


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdata();
            }
        });
    }

    private void setdata()
    {
        foodAPI.setServerAuthenticateListener(CalorieCheckActivity.this);
        foodAPI.getfooddetails(CalorieCheckActivity.this,food_name);
    }

    @Override
    public void onRequestInitiated(int code) {

        Log.v("req_initiated",String.valueOf(code));
    }

    @Override
    public void onRequestCompleted(int code) {

        Log.v("request_ok",String.valueOf(code));
    }

    @Override
    public void onRequestCompleted(int code, List<Food.Data> data) {

        this.data=data;
        recyclerView.setAdapter(new FoodAdapter());
        Log.v("reqcompleted","dataset");
    }

    @Override
    public void onRequestError(int code, String message) {

        Log.v("error",message);
    }


    private class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> implements RestarauntAPI.ServerAuthenticateListeners{


        public FoodAdapter() {
//            restarauntAPI.setServerAuthenticateListener(this);
        }

        @Override
        public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(CalorieCheckActivity.this).inflate(R.layout.food_details_row,parent,false);
            FoodViewHolder foodViewHolder=new FoodViewHolder(v);
            return foodViewHolder;
        }

        @Override
        public void onBindViewHolder(final FoodViewHolder holder, final int position) {

            holder.item_name.setText(data.get(position).getName());

            holder.calories.setText(data.get(position).getNutrition().getCalories());


            holder.card_holder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    final Dialog dialog=new Dialog(CalorieCheckActivity.this,R.style.Theme_D1NoTitleDim);
                    dialog.setContentView(R.layout.popup_view);
                    dialog.show();
                    TextView item_name=(TextView)dialog.findViewById(R.id.item_name_popup);
                    TextView carbs=(TextView)dialog.findViewById(R.id.carbs_popup);
                    TextView protiens=(TextView)dialog.findViewById(R.id.protiens_popup);
                    TextView fats=(TextView)dialog.findViewById(R.id.fats_popup);
                    TextView calories=(TextView)dialog.findViewById(R.id.calorie_popup);
                    carbs.setText(data.get(position).getNutrition().getCarbs());
                    protiens.setText(data.get(position).getNutrition().getProtein());
                    fats.setText(data.get(position).getNutrition().getCholesterol());
                    calories.setText(data.get(position).getNutrition().getCalories());
                    item_name.setText(data.get(position).getName());
                    TextView add,cancel;
                    final EditText popup_quantity;
                    add=(TextView)dialog.findViewById(R.id.add_btn);
                    cancel=(TextView)dialog.findViewById(R.id.cancel_btn);
                    popup_quantity=(EditText)dialog.findViewById(R.id.quantity_popup);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(popup_quantity.getText().toString())) {

                                    int cal1=Integer.parseInt(data.get(position).getNutrition().getCalories().replaceAll(",","").trim());

                                    int cal=Integer.parseInt(popup_quantity.getText().toString())*cal1;

                                    ContentValues contentValues=new ContentValues();
                                    contentValues.put(CaloriesProviders.CALORIES,String.valueOf(cal));
                                    contentValues.put(CaloriesProviders.TIME, String.valueOf(Calendar.getInstance().getTime()));
                                    Uri uri=getContentResolver().insert(CaloriesProviders.CONTENT_URI,contentValues);
                                    dialog.dismiss();


                                    Toast.makeText(CalorieCheckActivity.this,"Food item added",Toast.LENGTH_SHORT).show();



                            }
                            else
                            {
                                Toast.makeText(CalorieCheckActivity.this,"Please enter the quantity",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {

            return data.size();
        }


        @Override
        public void onRequestInitiated(int code) {
            Log.v("initiated","initiated");
        }

        @Override
        public void onRequestCompleted(int code) {

        }

        @Override
        public void onRequestCompleted(int code, List<Restaraunt> posts) {
            Log.v("tsgok","tagok");


        }

        @Override
        public void onRequestError(int code, String message) {

        }

        private class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {

            List<Restaraunt> posts;

            public SuggestionsAdapter(List<Restaraunt> posts) {
                this.posts=posts;
            }

            @Override
            public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(CalorieCheckActivity.this).inflate(R.layout.restaraunt_suggestions_row,parent,false);
                SuggestionViewHolder suggestionViewHolder=new SuggestionViewHolder(view);
                return suggestionViewHolder;
            }

            @Override
            public void onBindViewHolder(SuggestionViewHolder holder, int position) {
              holder.name.setText(posts.get(position).getName());
                Log.v("gotnames",posts.get(position).getName());
                Picasso.with(CalorieCheckActivity.this).load(posts.get(position).getAddress()).into(holder.place_image);

            }

            @Override
            public int getItemCount() {
                return posts.size();
            }
        }

        private class SuggestionViewHolder extends RecyclerView.ViewHolder {

            ImageView place_image;
            TextView name;
            public SuggestionViewHolder(View itemView) {
                super(itemView);
                this.name=(TextView)itemView.findViewById(R.id.place_name);
                this.place_image=(ImageView) itemView.findViewById(R.id.image_place);
            }
        }
    }

    private class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView item_name,calories,tv1;
        CardView card_holder;

        public FoodViewHolder(View itemView) {
            super(itemView);
            this.card_holder=(CardView) itemView.findViewById(R.id.card_holder);
            this.calories=(TextView) itemView.findViewById(R.id.calories);
            this.item_name=(TextView)itemView.findViewById(R.id.item_name);
            this.tv1=(TextView) itemView.findViewById(R.id.tv1);
            tv1.setTypeface(new Utils().getFontType(CalorieCheckActivity.this));
            calories.setTypeface(new Utils().getFontType(CalorieCheckActivity.this));
            item_name.setTypeface(new Utils().getFontType(CalorieCheckActivity.this));

        }
    }
}
