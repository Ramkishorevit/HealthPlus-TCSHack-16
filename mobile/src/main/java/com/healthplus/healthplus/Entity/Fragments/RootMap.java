package com.healthplus.healthplus.Entity.Fragments;

/**
 * Created by VSRK on 9/24/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.healthplus.healthplus.Boundary.API.RunAPI;
import com.healthplus.healthplus.Entity.Activities.MainActivity;
import com.healthplus.healthplus.Entity.Actors.Run;
import com.healthplus.healthplus.R;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by vsramkishore on 11/4/16.
 */
public class RootMap extends AppCompatActivity implements RunAPI.ServerAuthenticateListeners,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    public static boolean mMapIsTouched = false;

    Projection projection;
    public double latitude;
    boolean Is_MAP_Moveable;
    public double longitude;
    List<LatLng> val=new ArrayList<LatLng>();
    int MAP_ENABLED_STATE=1;
    double totalDistance=0;
    GoogleApiClient mGoogleApiClient;
    CardView card_ad;
    LocationRequest mLocationRequest;



    // Button zoomin,zoomout;
    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    List<LatLng> placepoints=new ArrayList<LatLng>();

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    private boolean FAB_Status = false;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    Button btn_submit;
    SeekBar myseekbar;
    RunAPI runAPI;
    TextView ad_name,ad_url;
    ImageView ad_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_run_tracker);
        card_ad=(CardView)findViewById(R.id.card_ad);
        ad_name=(TextView)findViewById(R.id.ad_name);
        ad_url=(TextView)findViewById(R.id.ad_url);
        ad_image=(ImageView)findViewById(R.id.ad_image);





        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        MySupportMapFragment customMapFragment = ((MySupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mMap = customMapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMyLocationEnabled(true);
        myseekbar=(SeekBar)findViewById(R.id.mySeekBar);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        myseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int prevProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int diff = progress - prevProgress;
                if(diff > 0){
                    mMap.animateCamera(CameraUpdateFactory.zoomOut());

                }
                else{
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());

                }
                prevProgress = progress;
                Log.v("prog",String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        FrameLayout fram_map = (FrameLayout)findViewById(R.id.fram_map);
        // Button btn_draw_State = (Button) v.findViewById(R.id.btn_draw_State);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);

//Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab2_hide);

        //     zoomin=(Button)v.findViewById(R.id.zoom_in);zoomout=(Button)v.findViewById(R.id.zoom_out);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FAB_Status == false) {
//Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
//Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RootMap.this, "Generated", Toast.LENGTH_SHORT).show();

                MAP_ENABLED_STATE=1;
                for (int i = 0; i + 1 < val.size(); i++) {
                    double a=val.get(i).latitude;
                    double b=val.get(i).longitude;
                    double c=val.get(i+1).latitude;
                    double d=val.get(i+1).longitude;
                    totalDistance += distance(a, b,c,d);
                }
                totalDistance/=1000;
                Toast.makeText(RootMap.this,"Total distance (km) : "+totalDistance,Toast.LENGTH_LONG).show();
                String destination=String.valueOf(val.get(val.size()-1).latitude+","+val.get(val.size()-1).longitude);
                Log.v("des",destination);
                runAPI=new RunAPI();
                runAPI.setServerAuthenticateListener(RootMap.this);

                runAPI.getRestaraunt(RootMap.this,String.valueOf(val.get(val.size()-1).latitude),String.valueOf(val.get(val.size()-1).longitude));





                //Intent intent=new Intent(getActivity(),CostEstimation.class);
                //startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RootMap.this, "You can now draw your route", Toast.LENGTH_SHORT).show();
                MAP_ENABLED_STATE=0;

            }
        });


       /* btn_draw_State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP_ENABLED_STATE = 0;
            }
        });*/

    /* zoomout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mMap.animateCamera(CameraUpdateFactory.zoomIn());
           }
       });
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });*/



        fram_map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MAP_ENABLED_STATE == 0) {

                    float x = event.getX();
                    float y = event.getY();

                    int x_co = Math.round(x);
                    int y_co = Math.round(y);

                    Log.v("x,y", String.valueOf(x_co));

                    projection = mMap.getProjection();
                    Point x_y_points = new Point(x_co, y_co);


                    LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);

                    Log.v("latlngtouched", String.valueOf(latLng));


                    int eventaction = event.getAction();
                    switch (eventaction) {
                        case MotionEvent.ACTION_DOWN:
                            // finger touches the screen

                            Log.v("touched_pos", String.valueOf(latitude));
                            val.add(latLng);

                        case MotionEvent.ACTION_MOVE:
                            // finger moves on the screen
                            val.add(latLng);

                        case MotionEvent.ACTION_UP:
                            // finger leaves the screen
                            Draw_Map();
                            break;
                    }

                    return true;
                }
                else {
                    Log.v("nothing_enabled","nothing enabled");
                }
                return true;
            }

        });


    }





    @Override
    public void onLocationChanged(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()),12);
        mMap.animateCamera(cameraUpdate);
    }



    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }





    public void Draw_Map() {
        PolylineOptions polylineOptions=new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(6);
        polylineOptions.addAll(val);
        mMap.addPolyline(polylineOptions);
    }


    private void expandFAB() {
//Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);
//Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);
//Floating Action Button 3

    }
    private void hideFAB() {
//Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);
//Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);
    }

    public float distance (double lat_a, double lng_a, double lat_b, double lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }



    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();

    }

    @Override
    public void onRequestInitiated(int code) {

    }

    @Override
    public void onRequestCompleted(int code) {

    }

    @Override
    public void onRequestCompleted(int code, List<Run> data) {

        Random r = new Random();
        int i1 = r.nextInt(data.size()-1 - 0) + 0;
        card_ad.setVisibility(View.VISIBLE);
        ad_name.setText(data.get(i1).getName());
        Picasso.with(RootMap.this).load(data.get(i1).getImage()).into(ad_image);
        ad_url.setText(data.get(i1).getUrl());
    }

    @Override
    public void onRequestError(int code, String message) {

    }
}