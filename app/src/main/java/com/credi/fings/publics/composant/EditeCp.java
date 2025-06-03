package com.credi.fings.publics.composant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.credi.fings.R;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.acty.SelectActivity;
import com.credi.fings.publics.service.HttpApi;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Composant;
import com.credi.fings.publics.service.impl.EditeService;
import com.credi.fings.publics.service.impl.Request;
import com.credi.fings.publics.service.impl.SelectService;
import com.credi.fings.publics.service.impl.TextViewHandler;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.S;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditeCp {
    private Context context;
    private Object object;
    private Attribut attribut;
    private Class<?> t;
    private TextInputEditText inputEditText;

    public EditeCp(Context context, Object object, Attribut attribut) {
        this.context = context;
        this.object = object;
        this.attribut = attribut;
    }

    public Class<?> getT() {
        return t;
    }

    public EditeCp setT(Class<?> t) {
        this.t = t;
        return this;
    }

    public TextInputEditText getInputEditText() {
        return inputEditText;
    }

    public EditeCp setInputEditText(TextInputEditText inputEditText) {
        this.inputEditText = inputEditText;
        return this;
    }

    @SuppressLint("MissingInflatedId")
    private View view(){
        String field=attribut.getColonne();
        Object rt=Ut.getValue(object,field);
        // System.out.println(rt+"====rtData="+field);
        String v,format=null;
        String type=attribut.getType();
        v=rt!=null?rt.toString():null;
        if(type!=null){
            int idx=type.indexOf("|");
            if(idx>0){
                format=type.substring(idx+1);
                type=type.substring(0,idx);
            }
            switch (type){
                case "string":case "number":
                    if(v!=null&&v.endsWith(".0")){
                        v=v.replace(".0","");
                    }
                    View view=  LayoutInflater.from(context).inflate(R.layout.input_mt,null,false);
                    // Récupérez le TextInputLayout
                    TextInputLayout textInputLayout = view.findViewById(R.id.textField);

                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                     inputEditText=view.findViewById(R.id.id);
                    if(type.equals("number")){
                        inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    if(v!=null)
                        inputEditText.setText(v);
                    inputEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(!s.toString().isEmpty()){
                                textInputLayout.setError(null);
                            }
                            if(t!=null&& !(t.getSimpleName().equals("LinkedTreeMap"))){
                                // System.out.println(" txc "+t);
                                // System.out.println(" oob1  "+Ut.js(object));
                                object= Ut.creatObject(object,t);
                                //System.out.println(" oob2  "+Ut.js(object));
                            }
                            object=Ut.setField(field,object,s.toString());
                            //System.out.println(" oob3  "+Ut.js(object));
                        }
                    });
                    return view;
                case "text":
                    view=  LayoutInflater.from(context).inflate(R.layout.input_text_area_mt,null,false);
                    // Récupérez le TextInputLayout
                    textInputLayout = view.findViewById(R.id.textInputLayout);

                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                    inputEditText=view.findViewById(R.id.editTextMessage);

                     if(v!=null)
                        inputEditText.setText(v);
                    inputEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(!s.toString().isEmpty()){
                                textInputLayout.setError(null);
                            }
                            System.out.println(object+" ffield "+field);
                            object=Ut.setField(field,object,s.toString());
                        }
                    });
                    return view;
                case "date":
                    view=  LayoutInflater.from(context).inflate(R.layout.pick_date,null,false);
                    inputEditText=view.findViewById(R.id.id);
                    // Récupérez le TextInputLayout
                    textInputLayout = view.findViewById(R.id.textField);
                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                    if(v!=null)
                    {
                        if(format != null){
                            inputEditText.setText(S.date(v,"yyyy-MM-dd'T'HH:mm:ss",format));
                        }else
                            inputEditText.setText(v);
                    }
                    String finalFormat = format;
                    inputEditText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new com.credi.fings.publics.service.impl.Composant(object,field,"yyyy-MM-dd'T'HH:mm:ss").showDateTimePicker(context,attribut.getName(),inputEditText,"edite"
                                    ,(finalFormat ==null?"yyyy-MM-dd'T'HH:mm:ss": finalFormat));
                        }
                    });
                    return view;
                case "heur":
                    view=  LayoutInflater.from(context).inflate(R.layout.pick_date,null,false);
                    inputEditText=view.findViewById(R.id.id);
                    // Récupérez le TextInputLayout
                    textInputLayout = view.findViewById(R.id.textField);
                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                    if(v!=null)
                    {
                        inputEditText.setText(v);
                    }
                    finalFormat = format;
                    inputEditText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Composant(object,field,"yyyy-MM-dd'T'HH:mm:ss").showTimePicker(context,attribut.getName(),inputEditText,"edite"
                            );
                        }
                    });
                     return view;
                case "boolean":
                    view=  LayoutInflater.from(context).inflate(R.layout.switch_material,null,false);
                    SwitchMaterial switchMaterial=view.findViewById(R.id.id_switch);
                    // Récupérez le TextInputLayout
                    // Définissez le hint dynamiquement sur le TextInputLayout
                    switchMaterial.setText(attribut.getName());
                    if(v!=null)
                    {
                        boolean bv= v.equals("true");
                        switchMaterial.setChecked(bv);
                    }
                    switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            object=Ut.setField(field,object,b);
                        }
                    });
                    return view;

            }
        }

        View view=  LayoutInflater.from(context).inflate(R.layout.inpu_drop_mt,null,false);
        // Récupérez le TextInputLayout
        TextInputLayout textInputLayout = view.findViewById(R.id.textField);

        // Définissez le hint dynamiquement sur le TextInputLayout
        textInputLayout.setHint(attribut.getName());
         inputEditText=view.findViewById(R.id.id);
        if(v!=null){
            inputEditText.setText(v);
        }
        if(attribut.getRequest()!=null&&attribut.getRequest().getUrl()!=null&&
                attribut.getRequest().getAttribut()==null&&!(type!=null&&type.equals("multiSelect")||type!=null&&type.equals("oneSelect"))){
            getData(attribut.getRequest().getUrl(),attribut.getRequest().getParam(),attribut);
        }

        String finalType = type;
        inputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalType != null&&(finalType.equals("multiSelect")||finalType.equals("oneSelect"))){
                    if(attribut.getValues()!=null&&attribut.getValues().size()<=7){
                        Object  lb=Ut.getValue(object,field);
                        showSelectDialogue(attribut.getValues(),lb,attribut.getLabel(),field,inputEditText,attribut);
                    }else {
                        // select activity
                        EditeService.field=attribut;
                        //System.out.println("_object__"+object);
                        //System.out.println("_field__"+field);
                        Object  lb=Ut.getValue(object,field);
                        // Dialogue.neutreDialog(Ut.js(lb)+"","",context).show();
                        context.startActivity(new Intent(context, SelectActivity.class)
                                .putExtra("attribut",attribut)
                                .putExtra("selected",Ut.js(lb)));
                    }
                }else {
                    if(attribut.getValues()!=null&&!attribut.getValues().isEmpty()){
                        ImageView ups=view.findViewById(R.id.ups);
                        List<Object> objects=attribut.getValues();
                        //System.out.println(Ut.js(objects.get(0))+"===pop="+attribut.getLabel());

                        PopupMenu pop=S.popupMenu(ups,attribut.getLabel()==null? attribut.getValues().toArray(new String[0])
                                :objects.stream().filter(o->o!=null).map(o->
                                Ut.getValue(o,attribut.getLabel())
                        ).collect(Collectors.toList()).toArray(new String[0]));
                        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Object o=attribut.getValues().get(item.getItemId()-1);
                                  inputEditText.setText(item.getTitle());

                                String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                                Object nouvelleObject=attribut.getValues().get(item.getItemId()-1);
                                Object nouvelleValeur=attribut.getField()==null?nouvelleObject:Ut.getValue(nouvelleObject,attribut.getField());
                                object=Ut.setField(fs,object,nouvelleValeur);
                                return false;
                            }
                        });
                    } else if (attribut.getRequest()!=null) {
                        if(attribut.getRequest().getNavigateClasse()!=null){
                            EditeService.field=attribut;
                            context.startActivity(new Intent(context,attribut.getRequest().getNavigateClasse())
                                    .putExtra("select","true"));
                        }
                    }
                }
            }
        });

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    textInputLayout.setError(null);
                }
            }
        });

        return view;
    }

    private void setValues(Attribut attribut, Object param, Request request){
        String url=request.getUrl();
        if(url.contains("/{")){
            String v=url.substring(url.indexOf("/{")+1);
            v=v.replace("{","").replace("}","");
            url=url.substring(0,url.indexOf("/{"));
            url=url+"/"+(Ut.getValue(param,v)!=null?Ut.getValue(param,v).toString().replace(".0",""):"0");
            getData(url,null,attribut);
        }else
            getData(request.getUrl(),param,attribut);
    }

    private void getData(String url,Object o,Attribut attribut) {
        if(attribut.getRequest().getPathVariable()!=null){

        }
        TextView textView=new TextView(context);
        HttpApi httpApi=new HttpApi(url,textView,context);
        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()){
                case "Ok":
                    attribut.setValues(copy(httpApi.getList(),Ut.getValueType(object,attribut.getColonne())));
                    break;
                case "local":
                    attribut.setValues(copy(httpApi.getList(),Ut.getValueType(object,attribut.getColonne())));
                    break;
                case "technique":
                    attribut.setValues(copy(httpApi.getList(),Ut.getValueType(object,attribut.getColonne())));
                    break;
            }
            /*Dialogue.neutreDialog(textView.getText()+"  "+httpApi.getList().size()+"  "+Ut.js(o)
                    ,""+url,context).show();*/
        });
        if(o==null){
            httpApi.getAllDatas();
        }else {
            httpApi.getAllDatas(o);
        }
    }

    private List<Object> copy(List<Object> objects,Class<?> t){
        // Dialogue.neutreDialog(objects.size()+""," "+t.getSimpleName(),context).show();
        List<Object> objectList=new ArrayList<>();
        for (Object o:objects){
            Object oc=Ut.creatObject(o,t);
            // System.out.println((oc==null?" null ":oc.toString())+"   "+t.getSimpleName()+" "+(o==null?" null ":o.toString()));
            objectList.add(oc);
        }
        return objectList;
    }

    public Attribut update(Object nouvelleValeur){
        Object v=Ut.getValue(nouvelleValeur,attribut.getLabel());

        inputEditText.setText(v==null?"":v.toString());
        String fs=attribut.getColonne().contains(":")?attribut.getColonne().substring(0,attribut.getColonne().indexOf(":")):attribut.getColonne();
        object=Ut.setField(fs,object,nouvelleValeur);
        // Dialogue.neutreDialog(Ut.js(object)+"",""+fs,context).show();
        AddActivity.nouvellValue=null;
        //field=null;
        return attribut;
    }
    
    private  void showSelectDialogue(final List<Object> data,Object sec, String label,String field,TextInputEditText inputEditText,
                                     Attribut attribut) {
        View dialogView = Ut.getView(context,R.layout.add_layout);
        LinearLayout lm=dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();

        List<Object> selection;
        if(sec==null) selection=new ArrayList<>();
        else selection= (List<Object>) sec;

        SelectService selectService= new SelectService(context,data,label,attribut,null)
                .setTitle("Sélectionnez".toUpperCase())
                .setMultiselect(true)
                .setSelect(selection);

        View view=selectService.view(alertDialog);

        View btn1 = Ut.getView(context, R.layout.outline_bouton);
        MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
        mtbt1.setText("Valider la sélection");
        lm.addView(view);
        lm.addView(btn1);


        alertDialog.show();
        mtbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                object=Ut.setField(fs,object,selectService.getSelect());
                inputEditText.setText(selectService.getStringSelect());
                //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
            }
        });

    }
}
