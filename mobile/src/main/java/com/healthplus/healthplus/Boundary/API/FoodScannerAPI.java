package com.healthplus.healthplus.Boundary.API;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.healthplus.healthplus.Control.AppController;
import com.healthplus.healthplus.Control.ErrorDefinitions;
import com.healthplus.healthplus.Entity.Actors.ItemScanner;
import com.healthplus.healthplus.Entity.Actors.Restaraunt;
import com.healthplus.healthplus.Entity.Actors.Run;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkishorevs on 03/03/17.
 */

public class FoodScannerAPI {


    String food_scanner_url="https://api.upcitemdb.com/prod/trial/lookup?upc=";


    ServerAuthenticateListeners mServerAuthenticateListener;

    public static final int FOOD_SCANNER_CODE=1;



    public void getFooddetails(String upc)
    {
        if (mServerAuthenticateListener != null) {

            mServerAuthenticateListener.onRequestInitiated(FOOD_SCANNER_CODE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,food_scanner_url+upc , new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.v("foodScanner", response);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    ItemScanner scanner=gson.fromJson(response,ItemScanner.class);

                    mServerAuthenticateListener.onRequestCompleted(FOOD_SCANNER_CODE,scanner);



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("err", "error");
                    mServerAuthenticateListener.onRequestError(FOOD_SCANNER_CODE, ErrorDefinitions.ERROR_RESPONSE_INVALID);

                }
            });

            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }




    public void setServerAuthenticateListener(ServerAuthenticateListeners listener) {

        mServerAuthenticateListener = listener;
    }

    public interface ServerAuthenticateListeners {

        void onRequestInitiated(int code);

        void onRequestCompleted(int code);

        void onRequestCompleted(int code, Object data);

        void onRequestError(int code, String message);

    }
}
