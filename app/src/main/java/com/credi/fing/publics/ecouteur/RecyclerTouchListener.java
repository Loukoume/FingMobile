package com.credi.fing.publics.ecouteur;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Loukoume on 26 août 2023 21:29:52.
 */


public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private SwipeClickListener clickListener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context,
                                 final RecyclerView recyclerView,
                                 final SwipeClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e2!=null&&e1!=null){
                    View child = recyclerView.findChildViewUnder(e1.getX(), e1.getY());
                    if (child != null && clickListener != null) {
                        float diffX = e2.getX() - e1.getX();

                        if (Math.abs(diffX) > Math.abs(e2.getY() - e1.getY())) {
                            if (diffX > 100) {
                                // Swipe vers la droite
                                clickListener.onSwipeRight(child, recyclerView.getChildAdapterPosition(child));
                            } else if (diffX < -100) {
                                // Swipe vers la gauche
                                clickListener.onSwipeLeft(child, recyclerView.getChildAdapterPosition(child));
                            }
                        }
                    }
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

    /**
     * Interface pour gérer les clics, long clics et les swipes.
     */
    public interface SwipeClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
        void onSwipeLeft(View view, int position);
        void onSwipeRight(View view, int position);
    }
}

