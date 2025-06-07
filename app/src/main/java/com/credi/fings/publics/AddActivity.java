package com.credi.fings.publics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.credi.fings.publics.service.ClickHandler;
import com.credi.fings.publics.service.SendHttp;
import com.google.android.material.button.MaterialButton;
import com.credi.fings.R;
import com.credi.fings.publics.service.HttpApi;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.EditeService;
import com.credi.fings.publics.service.impl.ListActivity;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.S;

import java.io.Serializable;

public class AddActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    MaterialButton fab;
    EditeObject editeObject;
    Class<?> t;
    TextView textView;
    public static Object object,nouvellValue;
    SwipeRefreshLayout swifeRefresh;
    ImageView back;
    Context context;
    EditeService ed;
    SendHttp sendHttp;
    public static boolean finish=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        linearLayout=findViewById(R.id.main);
        textView=findViewById(R.id.tx_text);
        context=this;
        sendHttp= ClickHandler.getSendHttp();
        editeObject= (EditeObject) getIntent().getSerializableExtra("object");
        if(editeObject==null&&getIntent().hasExtra("objectjs")){
            String jjs=getIntent().getStringExtra("objectjs");
          //  Dialogue.neutreDialog(jjs,""+editeObject.getaClass(),this).show();
            if(jjs!=null&&!jjs.isEmpty()){
                editeObject= (EditeObject) Ut.fromJs(jjs,EditeObject.class);
            }
        }else if(editeObject==null){
            editeObject= ListActivity.editeObject;
        }
        swifeRefresh=findViewById(R.id.swifeRefresh);
        back=findViewById(R.id.back);
        if(swifeRefresh!=null){
            swifeRefresh.setVisibility(View.GONE);
        }
        if(editeObject!=null){
            EditeObject o=editeObject;
            textView.setText(editeObject.getDesignation());
            t=editeObject.getaClass()==null?o.getObject().getClass():editeObject.getaClass();
            ed=new EditeService<>(t,o,this);
           // ed.setExclude(editeObject.getExclud());
            View view=ed.view();
            fab=findViewById(R.id.save);
            if(editeObject.getButtonLabel()!=null&&!editeObject.getButtonLabel().isEmpty()){
                fab.setText(editeObject.getButtonLabel());
            }
            //if(view!=null)
            linearLayout.addView(view);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean ok=ed.controle();
                    if(ok){
                        object=ed.getObject();
                        if(sendHttp!=null){
                            sendHttp.send(object,AddActivity.this);
                        }else {
                            if(editeObject.getPostUrl()==null){
                                if(editeObject.getNavigateClass()!=null){
                                    startActivity(new Intent(context,editeObject.getNavigateClass())
                                            .putExtra("editeObject",Ut.js(object)));
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }else {
                                    editeObject=null;
                                }
                                if(editeObject.isFinish())
                                {
                                    finish();
                                }

                            }else {
                                saveData(editeObject.getPostUrl(),object);
                            }
                        }
                    }else {
                        //Dialogue.neutreDialog(Ut.js(ed.getObject()),"",context).show();
                    }
                }
            });
        }else {
            Dialogue.neutreDialogF("Une erreur s'est produite","Erreur technique",this,this).show();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }
    private void saveData(String url, Object o) {
        new HttpApi(this).data(url,o,(ob,s)->{
            if(ob!=null){
                if(editeObject.getNavigateClass()!=null)
                    startActivity(new Intent(this, editeObject.getNavigateClass())
                            .putExtra("object",(Serializable)(Ut.getValue(o,editeObject.getId()))));
                object=null;
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            return ob;
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(finish){
            finish=false;
            finish();
        }else {
            if(EditeService.field!=null&&nouvellValue!=null){
                ed.update(nouvellValue);
            }
        }
    }
}