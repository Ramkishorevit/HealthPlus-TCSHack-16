package com.healthplus.healthplus.Boundary.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.healthplus.healthplus.Control.AppController;
import com.healthplus.healthplus.Control.ErrorDefinitions;
import com.healthplus.healthplus.Entity.Actors.Food;

import java.util.List;

/**
 * Created by VSRK on 8/23/2016.
 */
public class FoodAPI {

    public ServerAuthenticateListener mServerAuthenticateListener;

    public static final int FOOD_FETCH_CODE=1;

    List<Food.Data> data;


    private String FOOD_DETAILS_URL="http://api.fitsquare.in/v1/food?q=";

    public void getfooddetails(Context context,String name) {

        if (mServerAuthenticateListener!=null) {

            final ProgressDialog dialog=new ProgressDialog(context);
            dialog.setMessage("Fetching details..");
            dialog.show();

            mServerAuthenticateListener.onRequestInitiated(FOOD_FETCH_CODE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, FOOD_DETAILS_URL + name, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    dialog.dismiss();

                    if (response != null) {

                        Log.v("response", response);

                        GsonBuilder gsonBuilder=new GsonBuilder();

                        Gson gson=gsonBuilder.create();
                        Food food= gson.fromJson(response,Food.class);

                        Log.v("status", String.valueOf(food.status));

                          if (food.status==true)
                          {
                            mServerAuthenticateListener.onRequestCompleted(FOOD_FETCH_CODE,food.getData());
                          }
                        else
                        {
                            mServerAuthenticateListener.onRequestError(FOOD_FETCH_CODE,ErrorDefinitions.ERROR_RESPONSE_INVALID);

                        }

                    } else {

                        mServerAuthenticateListener.onRequestError(FOOD_FETCH_CODE, ErrorDefinitions.ERROR_RESPONSE_NULL);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Log.v("errorresponse",error.getMessage());
                    mServerAuthenticateListener.onRequestError(FOOD_FETCH_CODE, error.getMessage());
                    dialog.dismiss();

                }
            });


            AppController.getInstance().addToRequestQueue(stringRequest);

        }
        else
        {
            return;
        }
    }

    public void setServerAuthenticateListener(ServerAuthenticateListener listener) {

        mServerAuthenticateListener = listener;
    }

    public interface ServerAuthenticateListener {

        void onRequestInitiated(int code);

        void onRequestCompleted(int code);

        void onRequestCompleted(int code, List<Food.Data> data);

        void onRequestError(int code, String message);

    }
}
