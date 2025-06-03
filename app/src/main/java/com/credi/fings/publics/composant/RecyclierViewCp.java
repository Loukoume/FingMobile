package com.credi.fings.publics.composant;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fings.R;
import com.credi.fings.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fings.publics.ecouteur.GridSpacingItemDecoration;
import com.credi.fings.publics.ecouteur.Interface;
import com.credi.fings.publics.ecouteur.OnClick;
import com.credi.fings.publics.ecouteur.RecyclerTouchListener;
import com.credi.fings.publics.service.OnBindViewHolderAction;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.service.interfacs.OnSelect;
import com.credi.fings.publics.utils.S;

import java.util.List;

public class RecyclierViewCp {
    private Context context;
    private List<Object> objects;
    private Adapter adapter;
    private int row_layout;
    private int numberItems=1;
    private RecyclerView recyclerView;
    private int background;

    public int getBackground() {
        return background;
    }

    public RecyclierViewCp setBackground(int background) {
        this.background = background;
        return this;
    }

    private OnBindViewHolderAction onBindViewHolderAction;
    private OnSelect onLongClick;
    private OnSelect onClick;

    public int getNumberItems() {
        return numberItems;
    }

    public RecyclierViewCp setNumberItems(int numberItems) {
        this.numberItems = numberItems;
        return this;
    }

    public void setOnLongClick(OnSelect onLongClick) {
        this.onLongClick = onLongClick;
    }

    public void setOnClick(OnSelect onClick) {
        this.onClick = onClick;
    }

    public RecyclierViewCp(Context context, int row_layout, List<Object> objects, OnBindViewHolderAction onBindViewHolderAction) {
        this.context = context;
        this.row_layout=row_layout;
        this.objects = objects;
        this.onBindViewHolderAction = onBindViewHolderAction;
    }

    public View view(){
        View view= Ut.getView(context,R.layout.recyclier_layout);
        recyclerView=view.findViewById(R.id.recycler_view);
        if(background>0){
            recyclerView.setBackgroundResource(background);
        }
        preparerDatas();
        return view;
    }

    private void preparerDatas() {
      adapter=new Adapter(context,objects,row_layout,onBindViewHolderAction);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, numberItems);
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount()==0)
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(numberItems, S.dpToPx(2,context.getResources()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new Interface() {

            @Override
            public void onClick(View view, int position) {
                if(onClick!=null){
                    onClick.select(objects.get(position),position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(onLongClick!=null){
                    onLongClick.select(objects.get(position),position);
                }
            }
        }));
    }


}
