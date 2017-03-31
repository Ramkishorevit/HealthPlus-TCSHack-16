package com.healthplus.healthplus.Boundary.API;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.healthplus.healthplus.Control.AppController;

/**
 * Created by ramkishorevs on 03/03/17.
 */

public class TwilioAPI {

    public static final String ACCOUNT_SID = "AC5680d0e2f396c61513413183f4436f7a";
    public static final String AUTH_TOKEN = "AIzaSyCc3Esu3UU2yybH6s3OLEyjv5uCAxyJP20";


    public void makecall(final Context context)
    {


        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.setMessage("Contacting your friend emergency..");
        dialog.show();

        StringRequest request = new StringRequest("http://54.255.128.0/call?phone=7708150636", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("Response:%n %s", response.toString());
                Toast.makeText(context,"Activated Relax !",Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                Toast.makeText(context,"Some error try again",Toast.LENGTH_SHORT).show();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(300000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        AppController.getInstance().addToRequestQueue(request);
}


}
