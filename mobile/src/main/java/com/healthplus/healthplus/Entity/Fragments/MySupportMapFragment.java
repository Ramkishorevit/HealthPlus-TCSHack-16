package com.healthplus.healthplus.Entity.Fragments;

/**
 * Created by VSRK on 9/24/2016.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;


import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by vsramkishore on 11/4/16.
 */
public class MySupportMapFragment extends SupportMapFragment {
    public View mOriginalContentView;
    public MapWrapperLayout mMapWrapperLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        mOriginalContentView = super.onCreateView(inflater, parent, savedInstanceState);
        mMapWrapperLayout = new MapWrapperLayout(getActivity());
        mMapWrapperLayout.addView(mOriginalContentView);
        return mMapWrapperLayout;
    }

    @Override
    public View getView() {
        return mOriginalContentView;
    }

    public void setOnDragListener(MapWrapperLayout.OnDragListener onDragListener) {
        mMapWrapperLayout.setOnDragListener(onDragListener);
    }
}