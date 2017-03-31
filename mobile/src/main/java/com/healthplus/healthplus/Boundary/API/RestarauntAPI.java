package com.healthplus.healthplus.Boundary.API;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.healthplus.healthplus.Entity.Activities.CalorieCheckActivity;
import com.healthplus.healthplus.Entity.Actors.Restaraunt;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VSRK on 9/23/2016.
 */

public class RestarauntAPI {

    public ServerAuthenticateListeners mServerAuthenticateListener;

    public static final int FOOD_FETCH_CODE=1;
    String restaraunt_suggestion_url="http://54.251.149.43/foodad/poori";


    public void getRestaraunt(String item,Context context) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Fetching suggestions..");
        dialog.show();

        if (mServerAuthenticateListener != null) {

            mServerAuthenticateListener.onRequestInitiated(FOOD_FETCH_CODE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, restaraunt_suggestion_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.v("restaraunt_api", response);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Type fooType = new TypeToken<ArrayList<Restaraunt>>() {
                    }.getType();
                    List<Restaraunt> posts = gson.fromJson(response, fooType);

                    mServerAuthenticateListener.onRequestCompleted(FOOD_FETCH_CODE,posts);

                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("err", "error");
                    mServerAuthenticateListener.onRequestError(FOOD_FETCH_CODE, ErrorDefinitions.ERROR_RESPONSE_INVALID);
                    dialog.dismiss();
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

        void onRequestCompleted(int code, List<Restaraunt> data);

        void onRequestError(int code, String message);

    }
}
