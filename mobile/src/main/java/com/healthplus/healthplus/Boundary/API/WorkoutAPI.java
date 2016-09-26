package com.healthplus.healthplus.Boundary.API;

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
import com.healthplus.healthplus.Entity.Actors.Workout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VSRK on 9/24/2016.
 */

public class WorkoutAPI {

    public ServerAuthenticateListeners mServerAuthenticateListener;

    public static final int RUN_FETCH_CODE=1;
    String run_suggestion_url="http://54.254.254.88:8081/workout?q=runnin";


    public void getRestaraunt(Context context) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Fetching suggestions..");
        dialog.show();

        if (mServerAuthenticateListener != null) {

            mServerAuthenticateListener.onRequestInitiated(RUN_FETCH_CODE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, run_suggestion_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.v("restaraunt_api", response);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Type fooType = new TypeToken<ArrayList<Workout>>() {
                    }.getType();
                    List<Workout> posts = gson.fromJson(response, fooType);

                    mServerAuthenticateListener.onRequestCompleted(RUN_FETCH_CODE,posts);

                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("err", "error");
                    mServerAuthenticateListener.onRequestError(RUN_FETCH_CODE, ErrorDefinitions.ERROR_RESPONSE_INVALID);
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

        void onRequestCompleted(int code, List<Workout> data);

        void onRequestError(int code, String message);

    }
}
