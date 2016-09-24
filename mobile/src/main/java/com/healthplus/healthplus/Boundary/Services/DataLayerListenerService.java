package com.healthplus.healthplus.Boundary.Services;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.healthplus.healthplus.Boundary.Managers.WorkOutProviders;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by VSRK on 9/19/2016.
 */

public class DataLayerListenerService extends WearableListenerService {

    private static final String LOG_TAG = "WearableListener";

    private static Handler handler;
    
    private static int currentValue=0;
    public int time=0;

    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler(Handler handler) {
        DataLayerListenerService.handler = handler;
        // send current value as initial value.
        if(handler!=null)
            handler.sendEmptyMessage(currentValue);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);

        String id = peer.getId();
        String name = peer.getDisplayName();

        Log.d(LOG_TAG, "Connected peer name & ID: " + name + "|" + id);
    }
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(LOG_TAG, "received a message from wear: " + messageEvent.getPath());
        // save the new heartbeat value
        Timer t=new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                time=time+1;
            }
        },1000,1000);

        currentValue = Integer.parseInt(messageEvent.getPath());
        Log.v("running_ok",String.valueOf(currentValue));

        ContentValues values=new ContentValues();
        values.put(WorkOutProviders.HEART_BEAT,String.valueOf(currentValue));
        values.put(WorkOutProviders.TIME,time);
        Uri uri=getContentResolver().insert(WorkOutProviders.CONTENT_URI,values);



        if(handler!=null) {
            // if a handler is registered, send the value as new message
            handler.sendEmptyMessage(currentValue);
        }
    }

}
