package com.credi.fings.publics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.credi.fings.R;
import com.credi.fings.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fings.publics.adapters.generiqueAdapter.PreparAdapter;
import com.credi.fings.publics.repository.Repository;
import com.credi.fings.publics.repository.sqlite.Data;
import com.credi.fings.publics.service.HttpApi;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.RecyclerHandler;
import com.credi.fings.publics.service.impl.TextViewHandler;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.S;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListeFonteActivity extends AppCompatActivity {
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
    ListeFonte liste_fonte;
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
        toolbar.setText("Listefonte".toUpperCase());
        recyclerView=findViewById(R.id.liste);
        pb = findViewById(R.id.pb);
        fab=findViewById(R.id.fab);
        swifeRefresh = findViewById(R.id.swifeRefresh);
      //  fab.setVisibility(View.VISIBLE);
        liste_fonte= (ListeFonte) getIntent().getSerializableExtra("liste_fonte");
        editeObject=new EditeObject();
        // o.setIdOperateur((Operateur) operateur);
        editeObject.setDesignation("Ajouter un utilisateur");
        editeObject.setAttribute(new ListeFonte().setAttribut());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editeObject!=null){
                    liste_fonte =new ListeFonte();
                    editeObject.setObject(liste_fonte);
                    news=true;
                    startActivity(new Intent(context, AddActivity.class)
                            .putExtra("object",editeObject));
                }

            }
        });
        getData();
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
    public static Object object;
    @Override
    protected void onRestart() {
        super.onRestart();
        if (AddActivity.object != null && Ut.getValue(AddActivity.object,"idServeur") == null && Ut.getValue(AddActivity.object,"idLocal") == null) {
            repository=new Repository(Data.class,context);
            Object data=repository.save(new Data(Ut.js(AddActivity.object),"liste_fonte"));
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
        PreparAdapter preparAdapter=new PreparAdapter(context,adapter,recyclerView,new ListeFonte().binder(),objects,"mes_cours_liste_fonte",R.layout.card_image_horiz_row, ListeFonte.class);
        preparAdapter.setAttributs(new ListeFonte().setAttribut());
        adapter=preparAdapter.getAdapter();

        RecyclerHandler handler=new RecyclerHandler(recyclerView,context,preparAdapter.getAdapter(), ListeFonte.class);

        handler.setOnItemTouchListener(
                (position,view)->{
                    object = objects.get(position);
                    adapter.setP(position);
                    adapter.setaClass(ListeFonte.class);
                    p = position;
                    pbc = view.findViewById(R.id.pbc);
                    finish();
                },
                (p,view)->{

                });

    }
    public static Set<String> listAllFiles(String dir) {
        File folder = new File(dir);
        // Afficher le chemin absolu pour vérifier si le dossier est correctement ciblé
        System.out.println(new File("ici.txt").getAbsolutePath()+" Chemin absolu : " + folder.getAbsolutePath());
        // Vérifier si le chemin est valide et s'il s'agit bien d'un dossier
        if (folder.exists() && folder.isDirectory()) {
            return Stream.of(folder.listFiles())
                    .filter(File::isFile)  // S'assurer de ne prendre que les fichiers
                    .map(File::getName)
                    .collect(Collectors.toSet());
        }

        // Retourner un ensemble vide si le dossier est invalide ou vide
        return new ArraySet<>();
    }
    private void getData() {
        objects=new ArrayList<>();
        List<String> fils= Arrays.asList("courgette","ml","mm","mr","italiannoregular");
        for (String f:fils){
            com.credi.fings.publics.ListeFonte sty=new ListeFonte();
            sty.setLibelle(f);
            sty.setIdServeur(1L);
            sty.setUrl("@font/"+f);
            objects.add(sty);
        }
        preparerDatas();
    }
    private void updateData() {
        TextView textView=new TextView(this);
        HttpApi httpApi=new HttpApi("mes_cours_liste_fonte/update",textView,this);
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
 private void getData(String url,Object o,List<Object> list) {
        TextView textView=new TextView(this);
        HttpApi httpApi=new HttpApi(url,textView,this);
        httpApi.getAllDatas(o);

        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()){
                case "Ok":
                   list.addAll(httpApi.getList());
                    break;
                case "Error":
                    list.addAll(httpApi.getList());
                    break;
                case "technique":
                    list.addAll(httpApi.getList());
                    break;
            }
        });
    }}
