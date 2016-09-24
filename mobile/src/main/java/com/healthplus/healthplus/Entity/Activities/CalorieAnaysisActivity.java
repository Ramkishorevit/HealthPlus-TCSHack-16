package com.healthplus.healthplus.Entity.Activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.healthplus.healthplus.Boundary.Managers.CaloriesProviders;
import com.healthplus.healthplus.R;

import java.util.ArrayList;

/**
 * Created by VSRK on 9/24/2016.
 */

public class CalorieAnaysisActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

        LineChart lineChart;
    private static final int LOADER_ID = 0x01;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.calorie_analysis_activity);
            lineChart=(LineChart)findViewById(R.id.analysis_chart);
            getSupportLoaderManager().initLoader(LOADER_ID,null,this);

    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse("content://com.healthplus.healthplus.plus/diets"),new String[]{CaloriesProviders.CALORIES, CaloriesProviders.TIME},null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        while(!data.isAfterLast())
        {
        Log.v("datasets",data.getString(0));
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

        data.moveToFirst();
       while (!data.isAfterLast())
       {
           labels.add(data.getString(1));
           data.moveToNext();
       }

        LineDataSet dataset = new LineDataSet(entries,"# of Calls");


        LineData data1 = new LineData(labels, dataset);
        lineChart.setData(data1);
        lineChart.setDescription("Calorie Analysis");
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

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

