package com.credi.fing.publics.service.impl;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.ecouteur.Interface;
import com.credi.fing.publics.ecouteur.RecyclerTouchListener;

public class RecyclerHandler {
    private RecyclerView recyclerView;
    private Context context;
    private Adapter adapter;
    private String url;
    private Class<?> aClass;

    public RecyclerHandler(RecyclerView recyclerView, Context context, Adapter adapter, Class<?> aClass) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.adapter = adapter;
        this.aClass = aClass;
    }

    public interface ItemTouchListener {
        void execute(final int position, View view);
    }

    public interface ItemLongTouchListener {
        void execute(final int position, View view);
    }

    public interface OnSwipeLeft {
        void execute(final int position, View view);
    }

    public interface OnSwipeRight {
        void execute(final int position, View view);
    }


    private RecyclerTouchListener recyclerTouchListener; // Stocker la référence
    private RecyclerTouchListener recyclerTouchListenerWithSweep;

    public void setOnItemTouchListener(ItemTouchListener action, ItemLongTouchListener longAction) {

        if (recyclerTouchListener != null) {
            recyclerView.removeOnItemTouchListener(recyclerTouchListener);
        }


        recyclerTouchListener = new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.SwipeClickListener() {
            @Override
            public void onClick(View view, final int position) {
                adapter.setP(position);
                adapter.setaClass(aClass);
                if (action != null) {
                    action.execute(position, view);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (longAction != null) {
                    longAction.execute(position, view);
                }
            }

            @Override
            public void onSwipeLeft(View view, int position) {}

            @Override
            public void onSwipeRight(View view, int position) {}
        });

        recyclerView.addOnItemTouchListener(recyclerTouchListener);
    }

    public void setOnItemTouchListener(ItemTouchListener action, ItemLongTouchListener longAction, OnSwipeLeft onSwipeLeft, OnSwipeRight onSwipeRight) {
        if (recyclerTouchListenerWithSweep != null) {
            recyclerView.removeOnItemTouchListener(recyclerTouchListenerWithSweep);
        }

        recyclerTouchListenerWithSweep = new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.SwipeClickListener() {
            @Override
            public void onClick(View view, final int position) {
                adapter.setP(position);
                adapter.setaClass(aClass);
                if (action != null) {
                    action.execute(position, view);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (longAction != null) {
                    longAction.execute(position, view);
                }
            }

            @Override
            public void onSwipeLeft(View view, int position) {
                if (onSwipeLeft != null) {
                    view.animate().translationX(-600f).setDuration(300).start();
                    onSwipeLeft.execute(position, view);
                }
            }

            @Override
            public void onSwipeRight(View view, int position) {
                if (onSwipeRight != null) {
                    view.animate().translationX(600f).setDuration(300).start();
                    onSwipeRight.execute(position, view);
                }
            }
        });

        recyclerView.addOnItemTouchListener(recyclerTouchListenerWithSweep);

    }
}
