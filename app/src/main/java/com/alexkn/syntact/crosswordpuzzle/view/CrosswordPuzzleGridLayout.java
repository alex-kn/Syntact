package com.alexkn.syntact.crosswordpuzzle.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class CrosswordPuzzleGridLayout extends GridLayout {

    private GestureDetectorCompat detectorCompat;



    public CrosswordPuzzleGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public CrosswordPuzzleGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CrosswordPuzzleGridLayout(Context context) {
        super(context);

    }

    public void init(Context ctx) {
        detectorCompat = new GestureDetectorCompat(ctx, new GridGestureListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detectorCompat.onTouchEvent(event);
    }

    public class GridGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scrollBy(Math.round(distanceX), Math.round(distanceY));
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
