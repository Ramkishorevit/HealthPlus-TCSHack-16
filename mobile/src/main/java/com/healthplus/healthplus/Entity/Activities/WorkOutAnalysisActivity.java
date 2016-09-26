package com.healthplus.healthplus.Entity.Activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.healthplus.healthplus.Boundary.API.WorkoutAPI;
import com.healthplus.healthplus.Boundary.Managers.WorkOutProviders;
import com.healthplus.healthplus.Entity.Actors.Workout;
import com.healthplus.healthplus.Entity.Fragments.RootMap;
import com.healthplus.healthplus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by VSRK on 9/23/2016.
 */
public class WorkOutAnalysisActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,WorkoutAPI.ServerAuthenticateListeners {

    private static final int LOADER_ID = 0x01;
    LineChart lineChart;
    TextView ad_name, ad_url;
    ImageView ad_image;
    CardView card_ad;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_analysis_activity);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        init();
    }

    private void init()
    {

        lineChart=(LineChart)findViewById(R.id.analysis_chart);
        card_ad = (CardView) findViewById(R.id.card_ad);
        ad_name = (TextView) findViewById(R.id.ad_name);
        ad_url = (TextView) findViewById(R.id.ad_url);
        ad_image = (ImageView) findViewById(R.id.ad_image);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse("content://com.healthplus.healthplus/workouts"),new String[]{WorkOutProviders.HEART_BEAT, WorkOutProviders.TIME},null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();

        while (!data.isAfterLast()) {
            Log.v("text", data.getString(0));
            Log.v("text1",data.getString(1));
            data.moveToNext();
        }

        setUpGraph(data);


    }

    private void setUpGraph(Cursor data) {

        data.moveToFirst();

        ArrayList<Entry> entries=new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int c=0;
        while (!data.isAfterLast()) {
            entries.add(new Entry(Integer.parseInt(data.getString(0)),c));
            c=c+1;
            data.moveToNext();
        }

        for (int k=0;k<entries.size();k++)
        {
            labels.add(String.valueOf(k));
        }

            LineDataSet dataset = new LineDataSet(entries,"# of Calls");



        LineData data1 = new LineData(labels, dataset);
        lineChart.setData(data1);
        lineChart.setDescription("Workout Analysis");
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setFillColor(getResources().getColor(R.color.orange_deep));
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        lineChart.highlightValue(2,2);
        lineChart.animateY(5000);
        lineChart.setClickable(true);
        lineChart.setPinchZoom(true);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setHighlightPerTapEnabled(true);
        WorkoutAPI workoutAPI=new WorkoutAPI();
        workoutAPI.setServerAuthenticateListener(WorkOutAnalysisActivity.this);
        workoutAPI.getRestaraunt(WorkOutAnalysisActivity.this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(int code) {


    }

    @Override
    public void onRequestCompleted(int code, List<Workout> data) {
        Random r = new Random();
        int i1 = r.nextInt(data.size()-1 - 0) + 0;
       // card_ad.setVisibility(View.VISIBLE);
        ad_name.setText(data.get(i1).getName());
        Picasso.with(WorkOutAnalysisActivity.this).load(data.get(i1).getImage()).into(ad_image);
        ad_url.setText(data.get(i1).getUrl());
    }

    @Override
    public void onRequestError(int code, String message) {

    }
}
