package com.credi.fings.publics.dataTable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.credi.fings.R;
import com.credi.fings.publics.service.HttpApi;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Dialogue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataTableActivity extends AppCompatActivity {
    Attribut attribut;
    Context context;
    List<Object> data,totalsObjects;
    LinearLayout lmain,sheet;
    View vides;
    DataTable dataTable;
    ProgressBar progresbar;
    TextView toolbar;
    ImageView back,mort,search_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lmain=findViewById(R.id.lmain);
        progresbar=findViewById(R.id.progresbar);
        sheet=findViewById(R.id.sheet);
        vides=findViewById(R.id.vides);
        context=this;
        lmain.addView(Ut.getView(context,R.layout.tool_bar));
        toolbar = findViewById(R.id.tx_text);
        back = findViewById(R.id.back);
        mort = findViewById(R.id.mort);
        search_icon=findViewById(R.id.search_icon);

        attribut= (Attribut) getIntent().getSerializableExtra("attribut");
        if(attribut!=null){
            toolbar.setText(attribut.getLabel());
            data=attribut.getValues();
            if(data!=null&&!data.isEmpty()){
                totalsObjects=data;
                preparerDatas();
            }else if(attribut.getRequest()!=null&&attribut.getRequest().getUrl()!=null){
                new HttpApi(context)
                        .setProgressBar(progresbar)
                        .datas(attribut.getRequest().getUrl(),attribut.getRequest().getParam(),(o,s)->{
                    if(o!=null){
                        data=o;
                        totalsObjects=o;
                        preparerDatas();
                    }else {
                        Dialogue.neutreDialogF(s,"Alerte",context,this).show();
                    }
                });
            }else {
                Dialogue.neutreDialogF("Attribut non valide. Erreur technique","Paramètre invalide",context,this).show();
            }
        }else {
            Dialogue.neutreDialogF("Attribut non trouvé. Erreur technique","Paramètre invalide",context,this).show();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        vides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private  void preparerDatas() {
        dataTable=new DataTable(context).setObjects(data).setHead(attribut.getHeads());
        View view=dataTable.view();
        if(lmain.getChildCount()==2)lmain.removeViewAt(1);
           lmain.addView(view);
    }

    private void back(){
        // Définir l'écouteur avant d'exécuter la tâche

    }
    private List<Object> search(Object o,List<Attribut> attributs){
        List<Object> objects=new ArrayList<>();
        if(totalsObjects!=null){
            objects=totalsObjects.stream().filter(
                    ob->isEquals(ob,o,attributs)
            ).collect(Collectors.toList());
        }
        return objects;
    }
    private boolean isEquals(Object o1,Object o2,List<Attribut> attributs){
        for (Attribut a:attributs){
            boolean ok=Ut.equals(o1,o2,a.getColonne());
            if(!ok){
                return false;
            }
        }
       return true;
    }

}