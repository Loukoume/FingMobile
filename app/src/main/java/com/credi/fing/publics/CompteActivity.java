package com.credi.fing.publics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.credi.fing.R;
import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.adapters.generiqueAdapter.PreparAdapter;
import com.credi.fing.publics.repository.Repository;
import com.credi.fing.publics.repository.sqlite.Data;
import com.credi.fing.publics.service.HttpApi;
import com.credi.fing.publics.service.impl.EditeObject;
import com.credi.fing.publics.service.impl.TextViewHandler;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;

import java.util.List;

public class CompteActivity extends AppCompatActivity {
    static LinearLayout drawer, lmain, lbotom;
    static Context context;
    ImageView mort, back;
    static TextView toolbar;
    static View vide;
    RecyclerView recyclerView;
    static Adapter adapter;
    FloatingActionButton fab;
    static EditeObject editeObject;
    static ProgressBar pb;
    static SwipeRefreshLayout swifeRefresh;
    Compte compte,parent;
    boolean news=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = this;
        toolbar = findViewById(R.id.tx_text);
        back = findViewById(R.id.back);
        mort = findViewById(R.id.mort);
        lmain = findViewById(R.id.lmain);
        vide = findViewById(R.id.vides);
        toolbar.setText("Utilisateurs".toUpperCase());
        recyclerView=findViewById(R.id.liste);
        pb = findViewById(R.id.pb);
        fab=findViewById(R.id.fab);
        swifeRefresh = findViewById(R.id.swifeRefresh);
        if(parent==null)
         fab.setVisibility(View.VISIBLE);
        else fab.setVisibility(View.GONE);
        compte= (Compte) getIntent().getSerializableExtra("compte");
        editeObject=new EditeObject();
        // o.setIdOperateur((Operateur) operateur);
        editeObject.setDesignation("Ajouter un utilisateur");
        editeObject.setAttribute(new Compte().setAttribut());
       // parent=compte.getCompteParent();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editeObject!=null){
                    Compte cp=new Compte();
                    //cp.setCompteParent(compte);
                   // cp.setProfile("Agent commercial");
                    editeObject.setObject(cp);
                    news=true;
                    startActivity(new Intent(context, AddActivity.class)
                            .putExtra("object",editeObject));
                }

            }
        });

            getData("compte/by_boutique");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editeObject=null;
        AddActivity.object=null;
    }

    Repository repository;
    static ProgressBar pbc;
    static List<Object> objects;
    int p;
    static Object object;
    @Override
    protected void onRestart() {
        super.onRestart();
        if (AddActivity.object != null && Ut.getValue(AddActivity.object,"idServeur") == null && Ut.getValue(AddActivity.object,"idLocal") == null) {
            repository=new Repository(Data.class,context);
            Object data=repository.save(new Data(Ut.js(AddActivity.object),"compte"));
            objects.add(0,AddActivity.object);
            adapter.setData(data);
            adapter.notifyItemInserted(0);
            recyclerView.scrollToPosition(0);
        } else {
            if (AddActivity.object != null && Ut.getValue(AddActivity.object,"idServeur") != null) {
                updateData();
            }
        }

    }
    private  void preparerDatas() {
        PreparAdapter preparAdapter=new PreparAdapter(context,adapter,recyclerView,new Compte().binder(),objects,"compte",R.layout.card_image_horiz_row, Compte.class);
        preparAdapter.setAttributs(new Compte().setAttribut());
        adapter=preparAdapter.getAdapter();

        /*RecyclerHandler handler=new RecyclerHandler(recyclerView,context,preparAdapter.getAdapter(), Operation.class);

        handler.setOnItemTouchListener(
                (position,view)->{
                    object = objects.get(position);
                    adapter.setP(position);
                    adapter.setaClass(Compte.class);
                    p = position;
                    pbc = view.findViewById(R.id.pbc);
                },
                (p,view)->{

                });*/

    }
    private void getData(String url) {
        TextView textView=new TextView(this);
        HttpApi httpApi=new HttpApi(url,textView,this);
        httpApi.getAllDatas(parent==null?compte:parent);
        pb.setVisibility(View.VISIBLE);
        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()){
                case "Ok":
                    if(pb!=null) pb.setVisibility(View.GONE);
                    objects=httpApi.getList();
                    preparerDatas();
                    break;
                case "Error":
                    if(pb!=null)  pb.setVisibility(View.GONE);
                    S.toast(context,httpApi.getMessage());
                    objects=httpApi.getList();
                    preparerDatas();
                    break;
                case "technique":
                    if(pb!=null) pb.setVisibility(View.GONE);
                    S.toast(context,"Erreur technique");
                    objects=httpApi.getList();
                    preparerDatas();
                    break;
            }
        });
    }
    private void updateData() {
        TextView textView=new TextView(this);
        HttpApi httpApi=new HttpApi("compte/update",textView,this);
        httpApi.getDatas(AddActivity.object);
        pbc.setVisibility(View.VISIBLE);
        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()){
                case "Ok":
                    if(pbc!=null) pbc.setVisibility(View.GONE);
                    object=httpApi.getObject();
                    objects.set(p, object);
                    // Dialogue.neutreDialog(object+"","",context).show();
                    adapter.notifyItemChanged(p);
                    break;
                case "Error":
                    if(pbc!=null)  pbc.setVisibility(View.GONE);
                    S.toast(context,httpApi.getMessage());
                    break;
                case "technique":
                    if(pbc!=null) pbc.setVisibility(View.GONE);
                    S.toast(context,"Erreur technique");
                    break;
            }
        });
    }
}