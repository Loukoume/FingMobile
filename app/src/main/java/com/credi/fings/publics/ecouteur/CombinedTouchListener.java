package com.credi.fings.publics.ecouteur;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.credi.fings.publics.OnClickView;

public class CombinedTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private final OnClickView onClickView;
    private final int k;

    public CombinedTouchListener(Context context, int k, OnClickView onClickView) {
        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.onClickView = onClickView;
        this.k = k;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true; // Nécessaire pour détecter les événements
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (onClickView != null) {
                onClickView.onClick(null, -2); // -2 pour identifier un clic simple
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (onClickView != null) {
                onClickView.onClick(null, -1); // -1 pour identifier un long press
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        if (onClickView != null && k == 1) {
                            onClickView.onClick(null, k); // Swipe Right
                        }
                    } else {
                        if (onClickView != null && k == 2) {
                            onClickView.onClick(null, k); // Swipe Left
                        }
                    }
                    return true;
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        if (onClickView != null && k == 3) {
                            onClickView.onClick(null, k); // Swipe Bottom
                        }
                    } else {
                        if (onClickView != null && k == 0) {
                            onClickView.onClick(null, k); // Swipe Top
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}

