package com.healthplus.healthplus.Entity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.healthplus.healthplus.Boundary.API.FoodScannerAPI;
import com.healthplus.healthplus.Entity.Actors.ItemScanner;
import com.healthplus.healthplus.R;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by ramkishorevs on 03/03/17.
 */

public class BarCodeScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler, FoodScannerAPI.ServerAuthenticateListeners {

    private ZBarScannerView mScannerView;
    FoodScannerAPI foodScannerAPI;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);
        foodScannerAPI= new FoodScannerAPI();
        foodScannerAPI.setServerAuthenticateListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        Log.v("contents", rawResult.getContents()); // Prints scan results
        Log.v("name", rawResult.getBarcodeFormat().getName());

        Toast.makeText(BarCodeScannerActivity.this,rawResult.getContents(),Toast.LENGTH_LONG).show();
        // Prints the scan format (qrcode, pdf417 etc.)

        try {

            if (rawResult.getContents().equals("8904004400229")) {

                foodScannerAPI.getFooddetails(rawResult.getContents());
            }

            else
            {
                Toast.makeText(BarCodeScannerActivity.this,"Sorry this item is not configured in our database",Toast.LENGTH_SHORT).show();
            }

            }catch (Exception e)
        {
            e.printStackTrace();
        }
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(int code) {


    }

    @Override
    public void onRequestCompleted(int code, Object data) {

        if (code==FoodScannerAPI.FOOD_SCANNER_CODE)
        {
             ItemScanner foodScannerAPI=(ItemScanner) data;

            if (foodScannerAPI.getItems().isEmpty())
            {
                startActivity(new Intent(BarCodeScannerActivity.this,BarCodeFoodDetail.class));
            }

            else
            {
                startActivity(new Intent(BarCodeScannerActivity.this,BarCodeFoodDetail.class));

            }
        }

    }

    @Override
    public void onRequestError(int code, String message) {

    }
}
