package com.credi.fings.publics.adapters.generiqueAdapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fings.publics.service.OnBindViewHolderAction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.credi.fings.publics.ecouteur.GridSpacingItemDecoration;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.interfacs.CrudInterface;
import com.credi.fings.publics.utils.S;

import java.util.List;

public class PreparAdapter {
    private Adapter adapter;
    private Context context;
    private RecyclerView recyclerView;
    private List<Object> objects;
    private Binder binder;
    private String url;
    private CrudInterface crudInterface;
    private Class<?> aClass;
    boolean save;
    private int id;
    private  List<Attribut> attributs;

    public PreparAdapter(Context context) {
        this.context = context;
    }

    public PreparAdapter setAdapter(Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public PreparAdapter setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public PreparAdapter setObjects(List<Object> objects) {
        this.objects = objects;
        return this;
    }

    public CrudInterface getCrudInterface() {
        return crudInterface;
    }

    public PreparAdapter setCrudInterface(CrudInterface crudInterface) {
        this.crudInterface = crudInterface;
        return this;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public PreparAdapter setaClass(Class<?> aClass) {
        this.aClass = aClass;
        return this;
    }

    public boolean isSave() {
        return save;
    }

    public PreparAdapter setSave(boolean save) {
        this.save = save;
        return this;
    }

    public int getId() {
        return id;
    }

    public PreparAdapter setId(int id) {
        this.id = id;
        return this;
    }

    public PreparAdapter(Context context, Adapter adapter, RecyclerView recyclerView, List<Object> objects, int id) {
        this.context=context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.objects=objects;
        this.id=id;
    }

    public PreparAdapter(Context context,Adapter adapter, RecyclerView recyclerView,List<Object> objects,String url,int id) {
        this.context=context;
        this.adapter = adapter;
        this.url=url;
        this.recyclerView = recyclerView;
        this.objects=objects;
        this.id=id;
        init();
    }

    public PreparAdapter(Context context,Adapter adapter, RecyclerView recyclerView, Binder binder,List<Object> objects,String url,int id,Class<?> aClass,boolean save) {
        this.context=context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.binder = binder;
        this.objects=objects;
        this.url=url;
        this.id=id;
        this.aClass=aClass;
        this.save=save;
        init();
    }
    public PreparAdapter(Context context,Adapter adapter, RecyclerView recyclerView, Binder binder,List<Object> objects,String url,int id,Class<?> aClass) {
        this.context=context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.binder = binder;
        this.objects=objects;
        this.url=url;
        this.id=id;
        this.aClass=aClass;
        this.save=true;
        init();
    }

    public void creatAdapter(){
        adapter = new Adapter(context, objects,url,binder, id,aClass,save);
    }
    public void updateInite(){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount()==0)
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, S.dpToPx(2,context.getResources()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setAdapter(adapter);
    }
    public PreparAdapter init(CrudInterface crudInterface, OnBindViewHolderAction onBindViewHolderAction){
        adapter = new Adapter(context, objects,url,binder, id,aClass,save,crudInterface,onBindViewHolderAction);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount()==0)
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, S.dpToPx(2,context.getResources()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setAdapter(adapter);
        return this;
    }
    public void init(){
        adapter = new Adapter(context, objects,url,binder, id,aClass,save);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount()==0)
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, S.dpToPx(2,context.getResources()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setAdapter(adapter);
    }

    public Binder getBinder() {
        return binder;
    }

    public PreparAdapter setBinder(Binder binder) {
        this.binder = binder;
        return this;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public String getUrl() {
        return url;
    }

    public PreparAdapter setUrl(String url) {
        this.url = url;
        return this;
    }

    public List<Attribut> getAttributs() {
        return attributs;
    }

    public PreparAdapter setAttributs(List<Attribut> attributs) {
        this.attributs = attributs;
        adapter.setAttributs(attributs);
        return this;
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

    public static void fixedScrol(FloatingActionButton fab,RecyclerView recyclerView){
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
}
