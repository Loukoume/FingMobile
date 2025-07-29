package com.credi.fing.publics.composant;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.ecouteur.GridSpacingItemDecoration;
import com.credi.fing.publics.ecouteur.Interface;
import com.credi.fing.publics.ecouteur.RecyclerTouchListener;
import com.credi.fing.publics.service.OnBindViewHolderAction;
import com.credi.fing.publics.service.impl.RecyclerHandler;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.service.interfacs.OnSelect;
import com.credi.fing.publics.utils.S;

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

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclierViewCp setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    private OnBindViewHolderAction onBindViewHolderAction;
    private OnSelect onLongClick;
    private OnSelect onClick;

    RecyclerHandler.OnSwipeRight onSwipeRight;
    RecyclerHandler.OnSwipeLeft onSwipeLeft;

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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RecyclerHandler.OnSwipeRight getOnSwipeRight() {
        return onSwipeRight;
    }

    public void setOnSwipeRight(RecyclerHandler.OnSwipeRight onSwipeRight) {
        this.onSwipeRight = onSwipeRight;
    }

    public RecyclerHandler.OnSwipeLeft getOnSwipeLeft() {
        return onSwipeLeft;
    }

    public void setOnSwipeLeft(RecyclerHandler.OnSwipeLeft onSwipeLeft) {
        this.onSwipeLeft = onSwipeLeft;
    }

    public RecyclierViewCp(Context context, int row_layout, List<Object> objects, OnBindViewHolderAction onBindViewHolderAction) {
        this.context = context;
        this.row_layout=row_layout;
        this.objects = objects;
        this.onBindViewHolderAction = onBindViewHolderAction;
    }

    public View view(){
        View view=recyclerView;
        if(recyclerView==null){
             view= Ut.getView(context,R.layout.recyclier_layout);
            recyclerView=view.findViewById(R.id.recycler_view);
            if(background>0){
                recyclerView.setBackgroundResource(background);
            }
        }
        preparerDatas();
        return view;
    }

    public void view(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        preparerDatas();
    }

    private void preparerDatas() {
        adapter = new Adapter(context, objects, row_layout, onBindViewHolderAction);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, numberItems);
        recyclerView.setLayoutManager(mLayoutManager);

        if (recyclerView.getItemDecorationCount() == 0)
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(numberItems, S.dpToPx(2, context.getResources()), true));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new RecyclerTouchListener.SwipeClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (onClick != null) {
                    onClick.select(objects.get(position), position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (onLongClick != null) {
                    onLongClick.select(objects.get(position), position);
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
        }));
    }

    /**
     * Met à jour les données du RecyclerView et notifie l'adaptateur.
     *
     * @param list nouvelle liste de données à afficher
     */
    public void updateList(List<Object> list) {
        if (adapter != null && list != null) {
            this.objects = list;
            adapter.setList(list); // ⚠️ Assurez-vous que Adapter a une méthode setList(List<Object>)
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Adapter ou données non initialisés", Toast.LENGTH_SHORT).show();
        }
    }

    public void fixedScrol(View fab){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Faire quelque chose pendant le défilement
                if (dy > 0) {
                    // Le défilement vers le bas
                    // Fais quelque chose ici
                    fab.setVisibility(View.GONE);
                } else {
                    // Le défilement vers le haut
                    // Fais quelque chose ici
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // État du défilement a changé
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Le défilement s'est arrêté
                    // Fais quelque chose ici
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    // Le défilement est en cours
                    // Fais quelque chose ici
                }
            }
        });
    }
    public void fixedScrol(View fab,View top){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Faire quelque chose pendant le défilement
                if (dy > 0) {
                    // Le défilement vers le bas
                    // Fais quelque chose ici
                    fab.setVisibility(View.GONE);
                    top.setVisibility(View.VISIBLE);
                } else {
                    // Le défilement vers le haut
                    // Fais quelque chose ici
                    fab.setVisibility(View.VISIBLE);
                    top.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // État du défilement a changé
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Le défilement s'est arrêté
                    // Fais quelque chose ici
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    // Le défilement est en cours
                    // Fais quelque chose ici
                }
            }
        });
    }


}
