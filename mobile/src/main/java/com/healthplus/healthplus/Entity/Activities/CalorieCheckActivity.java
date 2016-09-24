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

    RecyclerView recyclerView,suggestions_recview;
    List<Food.Data> data;
    FoodAPI foodAPI;
    RestarauntAPI restarauntAPI;
    View popupContainer,suggestionscontainer;
    SlidingUpPanelLayout layout;
    String food_name;
    Button cal_btn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calorie_check_activity);
        init();
        setinit();
        setdata();
    }

    private void init()
    {
        foodAPI=new FoodAPI();
        restarauntAPI=new RestarauntAPI();
        food_name=getIntent().getExtras().getString("food_name");
        popupContainer = findViewById(R.id.popup_container);
        cal_btn=(Button)findViewById(R.id.cal_btn);
        suggestionscontainer=findViewById(R.id.suggestions_container);
        layout=(SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        suggestions_recview=(RecyclerView)findViewById(R.id.recycler_view_suggestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        suggestions_recview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setinit()
    {

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CalorieCheckActivity.this,CalorieAnaysisActivity.class);
                startActivity(intent);
            }
        });


        layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                suggestionscontainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if (layout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED)
                {
                    suggestionscontainer.setVisibility(View.VISIBLE);
                }
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
            restarauntAPI.setServerAuthenticateListener(this);
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
            holder.serving_type.setText("Serving Type: "+data.get(position).getServing());
            holder.carbs.setText(data.get(position).getNutrition().getCarbs());
            holder.fats.setText(data.get(position).getNutrition().getCholesterol());
            holder.protiens.setText(data.get(position).getNutrition().getProtein());

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
                                restarauntAPI.getRestaraunt(data.get(position).getName(), CalorieCheckActivity.this);

                                    int cal1=Integer.parseInt(data.get(position).getNutrition().getCalories().replaceAll(",","").trim());

                                    int cal=Integer.parseInt(popup_quantity.getText().toString())*cal1;

                                    ContentValues contentValues=new ContentValues();
                                    contentValues.put(CaloriesProviders.CALORIES,String.valueOf(cal));
                                    contentValues.put(CaloriesProviders.TIME, String.valueOf(Calendar.getInstance().getTime()));
                                    Uri uri=getContentResolver().insert(CaloriesProviders.CONTENT_URI,contentValues);
                                    dialog.dismiss();



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
            layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            suggestions_recview.setAdapter(new SuggestionsAdapter(posts));

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

        TextView protiens,carbs,fats,item_name,serving_type;
        CardView card_holder;

        public FoodViewHolder(View itemView) {
            super(itemView);
            this.protiens=(TextView)itemView.findViewById(R.id.protiens);
            this.carbs=(TextView)itemView.findViewById(R.id.carbs);
            this.fats=(TextView)itemView.findViewById(R.id.fats);
            this.item_name=(TextView)itemView.findViewById(R.id.item_name);
            this.serving_type=(TextView)itemView.findViewById(R.id.serving_type);
            this.card_holder=(CardView)itemView.findViewById(R.id.card_holder);
        }
    }
}
