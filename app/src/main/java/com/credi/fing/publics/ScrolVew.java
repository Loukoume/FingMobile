package com.credi.fing.publics;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import com.credi.fing.R;
import com.credi.fing.pojo.Line;
import com.credi.fing.publics.ecouteur.OnClick;
import com.credi.fing.publics.service.HttpApi;
import com.credi.fing.publics.service.impl.TextViewHandler;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;

import java.util.List;

public class ScrolVew {
    private List<Object> objects;
    private Context context;
    private Object object_aux;
    private LinearLayout main;
    private OnClick onClickAction;
    private int index;
    ProgressBar pbc;
    public ScrolVew(List<Object> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    public Object getObject_aux() {
        return object_aux;
    }

    public void setObject_aux(Object object_aux) {
        this.object_aux = object_aux;
    }
    public int lastIndes(){
        return main.getChildCount();
    }

    public View view(OnClick onClickAction){
        this.onClickAction=onClickAction;
         View view= S.findView(context, R.layout.lmain);
         main=view.findViewById(R.id.lmain);
         pbc=view.findViewById(R.id.pbc);
         //, Context context,String texte
        int p=0;
        for (Object object:objects){
            add(p,object);
            p++;
        }
        return view;
    }
    public void add(int p, Object object) {
        View lmain=S.findView(context,R.layout.main);
        LinearLayout mn=lmain.findViewById(R.id.lmain);
        Object tx=   object ==null?"": Ut.getValue(object,"text"),
                st= object ==null?null:Ut.getValue(object,"style"),
                tp= object ==null?"":Ut.getValue(object,"type");
        String text=tx==null?"":tx.toString();
        String type=tp==null?"":tp.toString();
        Style style=st==null?null:(Style)Ut.creatObject(st, Style.class);
        Line line=new Line(text,context);
        View vline=line.line(style);
        mn.addView(vline);
        text=line.getReste();
        Style style1=style!=null?style.clone():null;
        if(style1!=null){
            style1.setMargeTop(0);
            style1.setMargeLeft(0);
        }
        while (!text.isEmpty()){
            line=new Line(text,context);
            vline=line.line(style1);
            mn.addView(vline);
            text=line.getReste();
        }
        //Dialogue.neutreDialog(Ut.js(object),""+p,context).show();
        main.addView(lmain);
        final int finalP = p;
        lmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object_aux= object;
                index= finalP;
                onClickAction.execute(object);
            }
        });
        lmain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (type.equals("TITRE")){
                    String[] m={"Supprimer","Ajouter du contenu","Inserer un titre au dessu","Inserer un titre en dessous"};
                    PopupMenu pop=S.popupMenu(v,m);
                    pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            object_aux= object;
                            index= finalP;
                            switch (item.getItemId()){
                                case 1:
                                    deleteData("titre/delete_ligne", object);
                                    break;
                                case 2:

                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                            }
                            return false;
                        }
                    });
                }
                return false;
            }
        });
    }
    public void update(Object object){
        View lmain=main.getChildAt(index);
        LinearLayout mn=lmain.findViewById(R.id.lmain);
        mn.removeAllViews();
        Object tx=   object==null?"": Ut.getValue(object,"text"),
                st= object==null?null:Ut.getValue(object,"style");
        String text=tx==null?"":tx.toString();
        Style style=st==null?null:(Style)Ut.creatObject(st, Style.class);
        Line line=new Line(text,context);
        View vline=line.line(style);
        mn.addView(vline);
        text=line.getReste();
        Style style1=style!=null?style.clone():null;
        if(style1!=null){
            style1.setMargeTop(0);
            style1.setMargeLeft(0);
        }
        while (!text.isEmpty()){
            line=new Line(text,context);
            vline=line.line(style1);
            mn.addView(vline);
            text=line.getReste();
        }
    }

    private void deleteData(String url, Object o) {
        TextView textView = new TextView(context);
        pbc.setVisibility(View.VISIBLE);
        HttpApi httpApi = new HttpApi(url, textView, context);
        httpApi.getDatas(o);
        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()) {
                case "Ok":
                    pbc.setVisibility(View.GONE);
                    try {
                        main.removeViewAt(index);
                    }catch (Exception e){

                    }
                    index=0;
                    object_aux=null;
                    break;
                case "Error":
                    pbc.setVisibility(View.GONE);
                    //list.addAll(httpApi.getList());
                    break;
                case "technique":
                    pbc.setVisibility(View.GONE);
                    // list.addAll(httpApi.getList());
                    break;
            }
        });
    }
    private void addData(String url, Object o) {
        TextView textView = new TextView(context);
        pbc.setVisibility(View.VISIBLE);
        HttpApi httpApi = new HttpApi(url, textView, context);
        httpApi.getDatas(o);
        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()) {
                case "Ok":
                    pbc.setVisibility(View.GONE);
                    try {
                       // main.addView(index);
                    }catch (Exception e){

                    }
                    index=0;
                    object_aux=null;
                    break;
                case "Error":
                    pbc.setVisibility(View.GONE);
                    //list.addAll(httpApi.getList());
                    break;
                case "technique":
                    pbc.setVisibility(View.GONE);
                    // list.addAll(httpApi.getList());
                    break;
            }
        });
    }
}
