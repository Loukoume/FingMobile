package com.credi.fing.publics.acty;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;

import com.credi.fing.R;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.adapters.generiqueAdapter.PreparAdapter;
import com.credi.fing.publics.service.ClickHandler;
import com.credi.fing.publics.service.HttpApi;
import com.credi.fing.publics.service.OnItemViewClick;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.EditeService;
import com.credi.fing.publics.service.impl.RecyclerHandler;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.S;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SelectActivity extends AppCompatActivity {
    Context context;

    List<Object> data,totalsObjects;
    Object sec;
    String label;
    String field;
    public static List<Object> selection;
    Attribut attribut;
    ImageView mort, back;
    static TextView toolbar;
    static View vide;
    RecyclerView recyclerView;
    static Adapter adapter;
    LinearLayout lbouton,search,ltool,filter_type;
    static ProgressBar pb;
    static SwipeRefreshLayout swifeRefresh;
    MaterialButton outlinedButton;
    boolean multiselect=true;
    EditText search_bar;
    String type="T",qury="";
    ImageView filtre,search_icon,clear_search_icon;
    OnItemViewClick onItemViewClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context=this;
        toolbar = findViewById(R.id.tx_text);
        back = findViewById(R.id.back);
        mort = findViewById(R.id.mort);
        vide = findViewById(R.id.vides);
        toolbar.setText("Sélectionner".toUpperCase());
        recyclerView=findViewById(R.id.liste);
        pb = findViewById(R.id.pb);
        filter_type=findViewById(R.id.filter_type);
        lbouton=findViewById(R.id.lbouton);
        outlinedButton=findViewById(R.id.outlinedButton);
        search=findViewById(R.id.search);
        swifeRefresh = findViewById(R.id.swifeRefresh);
        search_icon=findViewById(R.id.search_icon);
        ltool=findViewById(R.id.normal);
        search_bar=findViewById(R.id.search_bar);
        clear_search_icon=findViewById(R.id.clear_search_icon);
        lbouton.setVisibility(View.VISIBLE);
        attribut= (Attribut) getIntent().getSerializableExtra("attribut");
        onItemViewClick= ClickHandler.getOnItemViewClick();
        String js=getIntent().getStringExtra("selected");
        if(js!=null&&!js.isEmpty()){
            selection= (List<Object>) Ut.fromJs(js,List.class);
        }
        LinearLayout ids=findViewById(R.id.id);
        if(ids!=null){
            View tool=Ut.getView(context,R.layout.tool_bar);
            ids.addView(tool,0);
        }
        // Dialogue.neutreDialog(js+"","",context).show();
        if(attribut!=null){
            filter_type.setVisibility(View.GONE);
            multiselect=attribut.getType().equalsIgnoreCase("multiSelect");
            outlinedButton.setText("Valider la sélection");
            if(selection==null){
                selection=new ArrayList<>();
            }
            data=attribut.getValues();
            label=attribut.getLabel();
            field=attribut.getColonne();

            LinearLayout lm=findViewById(R.id.lmain);

            if(data!=null&&!data.isEmpty()){
                //  Dialogue.neutreDialog(data+"","",context).show();
                totalsObjects=data;
                /*SelectService selectService= new SelectService(context,data,label)
                        .setTitle("Sélectionnez".toUpperCase())
                        .setMultiselect(true)
                        .setSelect(selection);

                View view=selectService.view();
                View id=view.findViewById(R.id.id);
                if(id!=null){
                    id.setVisibility(View.GONE);
                }
                View btn1 = Ut.getView(context, R.layout.outline_bouton);
                MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
                mtbt1.setText("Valider la sélection");
                lm.addView(view);
                lm.addView(btn1);

                mtbt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                        AddActivity.nouvellValue=selectService.getSelect();
                        if(EditeService.object!=null)
                          EditeService.object=Ut.setField(fs,EditeService.object,selectService.getSelect());
                        if(EditeService.inputEditText!=null)
                          EditeService.inputEditText.setText(selectService.getStringSelect());
                        finish();
                        //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
                    }
                });*/

                preparerDatas();
            }else if(attribut.getRequest()!=null&&attribut.getRequest().getUrl()!=null){
                getData(attribut.getRequest().getUrl());
            }else {
                Dialogue.neutreDialogF("Attribut non valide. Erreur technique","Paramètre invalide",context,this).show();
            }
        }else {
            Dialogue.neutreDialogF("Attribut non trouvé. Erreur technique","Paramètre invalide",context,this).show();
        }

        outlinedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.nouvellValue=
                        multiselect?(attribut.getValueField()==null?selection:
                                selection.stream().map(s-> Ut.getValue(s,attribut.getValueField())
                                        ).filter(Objects::nonNull)
                                        .collect(Collectors.toList())):(selection.isEmpty()?null:selection.get(0));

                if(field!=null){
                    String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;

                    System.out.println(fs+" fieldfs "+AddActivity.nouvellValue);
                    if(EditeService.object!=null)
                        EditeService.object=Ut.setField(fs,EditeService.object,AddActivity.nouvellValue);
                    if(EditeService.inputEditText!=null)
                        EditeService.inputEditText.setText(getStringSelect());
                    if(selection!=null){
                        selection=null;
                    }
                }else {
                    EditeService.object=AddActivity.nouvellValue;
                    AddActivity.object=AddActivity.nouvellValue;
                    if(selection!=null){
                        selection=null;
                    }
                }

                finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.VISIBLE);
                ltool.setVisibility(View.GONE);
                search_icon.setVisibility(View.GONE);
            }
        });

        clear_search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_bar.setText("");
                search_icon.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                ltool.setVisibility(View.VISIBLE);
            }
        });
        search_bar.setText("");

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                qury=editable.toString();
                filter(editable.toString());
            }
        });

    }
    private void filter(String query){
        if(query==null||query.isEmpty()){
            data=totalsObjects;
        }else {
            // System.out.println(" filtre "+query);
            data=totalsObjects.stream().filter(o->ok(o,query)
            ).collect(Collectors.toList());
        }
        swifeRefresh.setRefreshing(false);
        preparerDatas();
    }
    private boolean ok(Object o,String qury){
        Object valu=Ut.getValue(o,attribut.getLabel());
        // Object t=Ut.getValue(o,"idType:designation");
        if(valu!=null&&!valu.toString().isEmpty()){
            return (valu.toString().toLowerCase()).contains(qury.toLowerCase());
        }
        return false;
    }
    public String getStringSelect() {
        String v="";
        for (Object o:selection){
            v=v+", "+(label!=null?Ut.getValue(o,label):o.toString());
        }
        if (!v.isEmpty()){
            v=v.substring(2);
        }
        return v;
    }
    static ProgressBar pbc;
    int p,pSelected=-1;
    static Object object,objectSelected;
    boolean click=false;
    private  void preparerDatas() {
        /*Binder binder=new FilterBinder()
                .filterBinder(attribut.getLabel(),attribut.getSubLabel(),
                        attribut.getType()!=null&&attribut.getType().equalsIgnoreCase("multiSelect"))
                .setHideIcone(!attribut.isIcone()).setHideRadio(!attribut.isRadio());*/
        PreparAdapter preparAdapter=new PreparAdapter(context,adapter,recyclerView,data
                ,R.layout.card_image_horiz_row);
        preparAdapter.setBinder(null);
        preparAdapter.creatAdapter();
        adapter=preparAdapter.getAdapter();
        adapter.setSelection(selection);
        preparAdapter.updateInite();
        if(!attribut.isIcone()){
            lbouton.setVisibility(View.GONE);
        }else
            preparAdapter.fixedScrol(lbouton);
        RecyclerHandler handler=new RecyclerHandler(recyclerView,context,preparAdapter.getAdapter(), attribut.getT());

        handler.setOnItemTouchListener(
                (position,view)->{
                    if(onItemViewClick!=null){
                        onItemViewClick.onItemeClick(view,data.get(position),position);
                    }else {
                        if(!click){
                            click=true;
                            object = data.get(position);
                            adapter.setP(position);
                            adapter.setaClass(attribut.getT());
                            p = position;
                            add(object,attribut.getLabel());
                        }else {
                            click=false;
                        }
                    }
                },
                (p,view)->{

                });

    }
    public void add(Object x,String field){
        if(multiselect){
            int j=Ut.indexOf(selection,x,field);
            if(j!=-1){
                this.selection.remove(j);
            }
            else this.selection.add(x);
            // Dialogue.neutreDialog(selection.size()+"",j+"",context).show();
        }else {
            selection.clear();
            selection.add(object);
            pSelected=Ut.indexOf(data,objectSelected,attribut.getLabel());
            if(pSelected!=-1){
                adapter.notifyItemChanged(pSelected);
            }
        }
        adapter.setSelection(selection);

        adapter.notifyItemChanged(p);
        objectSelected=object;
        //click=false;
    }
    private void getData(String url) {

        new HttpApi(context)
                .setProgressBar(pb)
                .datas(url,attribut.getRequest()!=null?attribut.getRequest().getParam():null,
                        (o,s)->{
                            if(o!=null){
                                data=o;
                                totalsObjects=o;
                                preparerDatas();
                            }else {
                                S.toast(context,s);
                            }
                        }
                );



    }

    @Override
    protected void onDestroy() {
        ClickHandler.setOnItemViewClick(null);
        super.onDestroy();
    }
}