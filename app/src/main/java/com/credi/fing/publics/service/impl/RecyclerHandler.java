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
        void execute( final int position,View view);
    }
    public interface ItemLongTouchListener {
        void execute( final int position,View view);
    }

    public void setOnItemTouchListener(ItemTouchListener action,ItemLongTouchListener longAction){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new Interface() {
            @Override
            public void onClick(View view, final int position) {
                adapter.setP(position);
                adapter.setaClass(aClass);
                if(action!=null){

                    action.execute(position,view);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(longAction!=null){
                    longAction.execute(position,view);
                }
            }
        }));
    }
}
