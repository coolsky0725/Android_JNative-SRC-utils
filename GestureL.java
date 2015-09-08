package com.fidku.jeloubeta.utils;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureL extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnGestureListener {
	static String currentGestureDetected;
    
    // Override s all the callback methods of GestureDetector.SimpleOnGestureListener
    @Override
    public boolean onSingleTapUp(MotionEvent ev) {
        currentGestureDetected=ev.toString();
        Log.i("GESTURE", "onSingleTapUp");
      return true;
    }
    
    @Override
    public void onShowPress(MotionEvent ev) {
        currentGestureDetected=ev.toString();
        Log.i("GESTURE", "onShowPress");
    }
    
    @Override
    public void onLongPress(MotionEvent ev) {
        currentGestureDetected=ev.toString();
        Log.i("GESTURE", "onLongPress");
    }
    
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        currentGestureDetected=e1.toString()+ "  "+e2.toString();
        Log.i("GESTURE", "onScroll");
      return true;
    }
    
    @Override
    public boolean onDown(MotionEvent ev) {
        currentGestureDetected=ev.toString();
        Log.i("GESTURE", "onDown");
      return true;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        currentGestureDetected=e1.toString()+ "  "+e2.toString();
       Log.i("GESTURE", "onFling");
      return true;
    }
}
