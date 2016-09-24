package com.healthplus.healthplus.Entity.Fragments;

/**
 * Created by VSRK on 9/24/2016.
 */

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by vsramkishore on 11/4/16.
 */
public class MapWrapperLayout extends FrameLayout {
    private OnDragListener mOnDragListener;

    public MapWrapperLayout(Context context) {
        super(context);
    }

    public interface OnDragListener {
        public void onDrag(MotionEvent motionEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mOnDragListener != null) {
            mOnDragListener.onDrag(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setOnDragListener(OnDragListener mOnDragListener) {
        this.mOnDragListener = mOnDragListener;
    }

}