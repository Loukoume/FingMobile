package com.credi.fings.publics.service.impl;

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
import com.credi.fings.publics.service.ClickHandler;
import com.credi.fings.publics.service.HttpApi;
import com.credi.fings.publics.service.RetourData;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.S;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EditeService<T> {
    private Class<T> t;
    private Context context;
    private EditeObject editeObject;
    public static Object object;

    private List<Control> controls,visibles;

    public static Attribut field;
    public static TextInputEditText inputEditText;
    public EditeService(Class<T> t, Context context) {
        ClickHandler.setOnItemViewClick(null);
        this.t = t;
        this.context = context;
        this.object=createInstance();
        this.controls=new ArrayList<>();
        this.visibles=new ArrayList<>();
        defaultValue();
    }
    public EditeService(Class<T> t,EditeObject editeObject, Context context) {
        ClickHandler.setOnItemViewClick(null);
        this.t = t;
        this.context = context;
        this.editeObject=editeObject;
        this.object=editeObject.getObject();
        this.controls=new ArrayList<>();
        this.visibles=new ArrayList<>();
        defaultValue();
    }
    public void setValue(Object object){
        if(field!=null){
            this.object=Ut.setField(field.getColonne(),this.object,object);
            inputEditText.setText(object.toString());
            field=null;
        }
    }
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    public boolean controle(){
        if(EditeService.object==null)return false;
        ClickHandler.setOnItemViewClick(null);
        boolean ok=true;
        for (Control c:controls){
            if(c.view==null){
                RetourData<Object> v=getValues(EditeService.object,c.attribut.getField()==null?c.attribut.getColonne():c.attribut.getField());
                if(c.attribut.isRequierd()&&(v==null||(v.getData()==null||v.getData().toString().isEmpty()))){
                    return false;
                }
            }else {
                TextInputLayout view= (TextInputLayout) c.view;
                RetourData<Object> v=getValues(EditeService.object,c.attribut.getField()==null?c.attribut.getColonne():c.attribut.getField());

                System.out.println(c.attribut.getColonne()+" cvv "+v);
                if(c.attribut.isRequierd()&&(v==null||v.getData()==null||v.getData().toString().isEmpty())){
                    view.setError("Champs obligatoire");
                    ok=false;
                }
            }

        }
        return ok;
    }

    public void visibility(List<Attribut> a,Object valeur){
        for (Attribut at:a){
            for (Control c:visibles){
                View view= c.view;
                if(c.attribut.equals(at) &&c.attribut.getVisible()!=null&& Objects.equals(c.attribut.getVisible().getValeurs(),valeur)){
                    view.setVisibility(View.VISIBLE);
                }else if(c.attribut.getVisible()!=null&&c.attribut.equals(at)){
                    view.setVisibility(View.GONE);
                }
            }
        }
    }


    private List<String> exclude;
    private List<String> fied;

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }

    public List<String> getFied() {
        return fied;
    }

    public void setFied(List<String> fied) {
        this.fied = fied;
    }
    public Object createInstance() {
        try {
            return t.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void defaultValue(){
        if(object!=null&&editeObject!=null&&editeObject.getAttribute()!=null){
            for (Attribut at:editeObject.getAttribute()){
                if(at.getDefaul()!=null){
                    object=Ut.setField(at.getColonne(),object,at.getDefaul());
                }
            }
        }
    }
    private void defaultVisible(){
        if(editeObject.getAttribute()!=null){
            for (Attribut at:editeObject.getAttribute()){
                if(at.getDefaul()!=null){
                    List<Attribut> visible=visibleOn(at);
                    visibility(visible,at.getDefaul());
                }else {
                    if(at.getVisible()!=null&&at.getVisible().getAttribut().getName()==null){
                      visibility(List.of(at),at.getVisible().getValeurs());
                    }
                }
            }
        }
    }


    private RetourData<Object> getValues(Object object, String fieldName) {
        // Vérifier si le champ n'est pas imbriqué
        if (!fieldName.contains(":")) {
            Object vo=Ut.getValue(object,fieldName);
            RetourData<Object> retourData=new RetourData();
            retourData.setData(vo);
            // retourData.setObject(field.getType());
            return retourData;
            //return getValue(object, fieldName);
        }

        RetourData<Object> retourData = new RetourData<>();
        String[] fields = fieldName.split(":");
        Object currentObject = object;
        //System.out.println(currentObject.getClass().getName()+"====Object="+fieldName);
        for (String field : fields) {
            if (currentObject == null) {
                return null; // Si un objet intermédiaire est null, on arrête la recherche
            }
            // System.out.println(currentObject.getClass().getName()+"====currentObject="+field);
            // Obtenir la valeur du champ actuel
            currentObject=Ut.getValue(currentObject,field);
          /*  if(currentObject!=null)
                System.out.println(currentObject.getClass()+"===retour=="+field);
            else System.out.println(" nulll "+field);*/
        }

        // Retourner le dernier RetourData obtenu
        retourData.setData(currentObject);
        retourData.setStatus("OK");
        return retourData;
    }


    private boolean hasField(Object object,String field){
        Field[] fields=object.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(f->f.getName().equals(field)).findFirst().isPresent();
    }

    private String getFieldNameForNestedObject(String parentFieldName, Object nestedObject) {
        List<String> colon= Arrays.stream(nestedObject.getClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        if(colon.contains(parentFieldName))return parentFieldName;
        return colon.get(0);
    }

    public String getType(Class<?> clazz){
        if(clazz == Boolean.class)return "boolean";
        if( clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Double.class ||
                clazz == Float.class)return "number";
        if(isPrimitiveOrWrapper(clazz))return "string";
        if(clazz == String.class)return "string";
        if(clazz == Date.class)return "date";
        return clazz.getName();
    }

    // Vérifier si la classe est de type primitif ou son équivalent wrapper
    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Double.class ||
                clazz == Float.class ||
                clazz == Boolean.class ||
                clazz == Character.class ||
                clazz == Byte.class ||
                clazz == Short.class||
                clazz == Enum.class;
    }
    private List<Attribut> colonnes(){
        if(editeObject!=null&&editeObject.getAttribute()!=null&&!editeObject.getAttribute().isEmpty()){
            return  editeObject.getAttribute();
        }

        if(exclude==null)exclude=new ArrayList<>();
        List<Attribut> columns = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(f -> !List.class.isAssignableFrom(f.getType())) // Exclut les champs de type List ou sous-types de List
                .peek(f -> f.setAccessible(true)) // Rendre le champ accessible si besoin
                .map(c->{
                    Attribut attribut=new Attribut(c.getName());

                    return attribut;
                })
                .filter(c->!exclude.contains(c))
                .collect(Collectors.toList());
        return columns;
    }

    public View getView(int i){
       if (i<controls.size()){
           Control control=controls.get(i);
           return control.view;
       }
       return null;
    }

    public View view(){
        if(t!=null&&context!=null){
            View view=  LayoutInflater.from(context).inflate(R.layout.add_layout,null,false);
            LinearLayout lmain=view.findViewById(R.id.lmain);
            List<Attribut> coln=colonnes();
            //  System.out.println("==colonnes=="+coln.stream().map(c->c.getColonne()).collect(Collectors.toList()));
            for (Attribut c:coln){
                if(c.getAttributs()!=null&&!c.getAttributs().isEmpty()){
                    if(c.getAttributs().size()==2){
                        View v=Ut.getView(context,R.layout.two_view);
                        LinearLayout l1=v.findViewById(R.id.view1);
                        LinearLayout l2=v.findViewById(R.id.view2);
                        Attribut c1=c.getAttributs().get(0);
                        Attribut c2=c.getAttributs().get(1);
                        View vs1=putAndGetValue(c1.getColonne(),c1);
                        l1.addView(vs1);
                        View vs2=putAndGetValue(c2.getColonne(),c2);
                        l2.addView(vs2);
                        lmain.addView(v);
                    }
                }else {
                    View vs=putAndGetValue(c.getColonne(),c);
                    if(vs!=null){
                        lmain.addView(vs);
                    }
                }

            }
            defaultVisible();
            return view;
        }
        return null;
    }

    private View putAndGetValue(String field,Attribut attribut){
        RetourData rt=getValues(object,field);
        // System.out.println(rt+"====rtData="+field);
        String v=null,format=null;
        String type=attribut.getType();
        v=rt!=null&&rt.getData()!=null?rt.getData().toString():null;
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
                    TextInputEditText editText=view.findViewById(R.id.id);
                    if(type.equals("number")){
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    controls.add(new Control(attribut,textInputLayout,TYP.INPUT));
                    visibles.add(new Control(attribut,view,TYP.INPUT));
                    if(v!=null)
                        editText.setText(v);
                    editText.addTextChangedListener(new TextWatcher() {
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
                                object=Ut.creatObject(object,t);
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
                    editText=view.findViewById(R.id.editTextMessage);

                    controls.add(new Control(attribut,textInputLayout,TYP.INPUT));
                    visibles.add(new Control(attribut,view,TYP.INPUT));
                    if(v!=null)
                        editText.setText(v);
                    editText.addTextChangedListener(new TextWatcher() {
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
                    editText=view.findViewById(R.id.id);
                    // Récupérez le TextInputLayout
                    textInputLayout = view.findViewById(R.id.textField);
                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                    if(v!=null)
                    {
                        if(rt.getData() != null && format != null){
                            editText.setText(S.date(v,"yyyy-MM-dd'T'HH:mm:ss",format));
                        }else
                            editText.setText(v);
                    }
                    String finalFormat = format;
                    editText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Composant(object,field,(finalFormat ==null?"yyyy-MM-dd'T'HH:mm:ss": finalFormat)).showDateTimePicker(context,attribut.getName(),editText,"edite"
                                    ,(finalFormat ==null?"yyyy-MM-dd'T'HH:mm:ss": finalFormat));
                        }
                    });
                    controls.add(new Control(attribut,textInputLayout,TYP.INPUT));
                    visibles.add(new Control(attribut,view,TYP.INPUT));
                    return view;
                case "dateString":
                    view=  LayoutInflater.from(context).inflate(R.layout.pick_date,null,false);
                    editText=view.findViewById(R.id.id);
                    // Récupérez le TextInputLayout
                    textInputLayout = view.findViewById(R.id.textField);
                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                    if(v!=null)
                    {
                        if(rt.getData() != null && format != null){
                            editText.setText(S.date(v,"yyyy-MM-dd'T'HH:mm:ss",format));
                        }else
                            editText.setText(v);
                    }
                     finalFormat = format;
                    editText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Composant(object,field,(finalFormat ==null?"yyyy-MM-dd'T'HH:mm:ss": finalFormat))
                                    .setDateString(true).showDateTimePicker(context,attribut.getName(),editText,"edite"
                                    ,(finalFormat ==null?"yyyy-MM-dd'T'HH:mm:ss": finalFormat));
                        }
                    });
                    controls.add(new Control(attribut,textInputLayout,TYP.INPUT));
                    visibles.add(new Control(attribut,view,TYP.INPUT));
                    return view;
                case "heur":
                    view=  LayoutInflater.from(context).inflate(R.layout.pick_date,null,false);
                    editText=view.findViewById(R.id.id);
                    // Récupérez le TextInputLayout
                    textInputLayout = view.findViewById(R.id.textField);
                    // Définissez le hint dynamiquement sur le TextInputLayout
                    textInputLayout.setHint(attribut.getName());
                    if(v!=null)
                    {
                        editText.setText(v);
                    }
                    finalFormat = format;
                    editText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Composant(object,field,"yyyy-MM-dd'T'HH:mm:ss").showTimePicker(context,attribut.getName(),editText,"edite"
                            );
                        }
                    });
                    controls.add(new Control(attribut,textInputLayout,TYP.INPUT));
                    visibles.add(new Control(attribut,view,TYP.INPUT));
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
        TextInputEditText editText=view.findViewById(R.id.id);
        controls.add(new Control(attribut,textInputLayout,TYP.INPUT));
        visibles.add(new Control(attribut,view,TYP.INPUT));
        if(v!=null){
            editText.setText(v);
        }
        if(attribut.getRequest()!=null&&attribut.getRequest().getUrl()!=null&&
                attribut.getRequest().getAttribut()==null&&!(type!=null&&type.equals("multiSelect")||type!=null&&type.equals("oneSelect"))){
            getData(attribut.getRequest().getUrl(),attribut.getRequest().getParam(),attribut);
        }

        String finalType = type;
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText=editText;
                if(finalType != null&&(finalType.equals("multiSelect")||finalType.equals("oneSelect"))){
                    if(attribut.getValues()!=null&&attribut.getValues().size()<=7){
                        Object  lb=Ut.getValue(object,field);
                        showSelectDialogue(attribut.getValues(),lb,attribut.getLabel(),field,editText,attribut);
                    }else {
                        // select activity
                        EditeService.field=attribut;
                        System.out.println("_object__"+object);
                        System.out.println("_field__"+field);
                        Object  lb=Ut.getValue(object,field);

                        context.startActivity(new Intent(context, SelectActivity.class)
                                .putExtra("attribut",attribut)
                                .putExtra("selected",Ut.js(lb)));
                    }
                }else {
                    if(attribut.getValues()!=null&&!attribut.getValues().isEmpty()){
                        ImageView ups=view.findViewById(R.id.ups);
                        List<Object> objects=attribut.getValues();
                        System.out.println(Ut.js(objects.get(0))+"===pop="+attribut.getLabel());

                        PopupMenu pop=S.popupMenu(ups,attribut.getLabel()==null? attribut.getValues().toArray(new String[0])
                                :objects.stream().filter(o->o!=null).map(o->
                                Ut.getValue(o,attribut.getLabel())
                        ).collect(Collectors.toList()).toArray(new String[0]));
                        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Object o=attribut.getValues().get(item.getItemId()-1);
                                List<Attribut> depend=dependOn(attribut);
                                List<Attribut> visible=visibleOn(attribut);
                                visibility(visible,o);
                                editText.setText(item.getTitle());

                                String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                                Object nouvelleObject=attribut.getValues().get(item.getItemId()-1);
                                Object nouvelleValeur=attribut.getField()==null?nouvelleObject:Ut.getValue(nouvelleObject,attribut.getField());
                                object=Ut.setField(fs,object,nouvelleValeur);


                                for (Attribut at:depend){
                                    setValues(at,o,at.getRequest());
                                }
                                return false;
                            }
                        });
                    } else if (attribut.getRequest()!=null) {
                        if(attribut.getRequest().getNavigateClasse()!=null){
                            inputEditText=editText;
                            EditeService.field=attribut;
                            context.startActivity(new Intent(context,attribut.getRequest().getNavigateClasse())
                                    .putExtra("select","true"));
                        }
                    }
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
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

    private void setValues(Attribut attribut,Object param,Request request){
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
        Object v=Ut.getValue(nouvelleValeur,field.getLabel());

        inputEditText.setText(v==null?"":v.toString());
        String fs=field.getColonne().contains(":")?field.getColonne().substring(0,field.getColonne().indexOf(":")):field.getColonne();
        object=Ut.setField(fs,object,nouvelleValeur);
        // Dialogue.neutreDialog(Ut.js(object)+"",""+fs,context).show();
        AddActivity.nouvellValue=null;
        //field=null;
        return field;
    }

    private List<Attribut> dependOn(Attribut attribut){
        List<Attribut> attributList=new ArrayList<>();
        if(editeObject.getAttribute()!=null)
            for (Attribut at:editeObject.getAttribute()){
                if(at.getRequest()!=null&&at.getRequest().getAttribut()!=null){
                    String nam=at.getRequest().getAttribut().getName(),cl=at.getRequest().getAttribut().getColonne();
                    if(nam.equals(attribut.getName())&&cl.equals(attribut.getColonne())){
                        attributList.add(at);
                    }
                }
            }
        return attributList;
    }
    private List<Attribut> visibleOn(Attribut attribut){
        List<Attribut> attributList=new ArrayList<>();
        if(editeObject.getAttribute()!=null)
            for (Attribut at:editeObject.getAttribute()){
                if(at.getVisible()!=null&&at.getVisible().getAttribut()!=null){
                    String nam=at.getVisible().getAttribut().getName(),
                            cl=at.getVisible().getAttribut().getColonne();
                    if(nam.equals(attribut.getName())&&cl.equals(attribut.getColonne())){
                        attributList.add(at);
                    }
                }
            }

        return attributList;
    }


    private  void showSelectDialogue(final List<Object> data,Object sec, String label,String field,TextInputEditText editText,
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
                editText.setText(selectService.getStringSelect());
                //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
            }
        });

    }

    class Control{
        private Attribut attribut;
        private View view;
        private TYP type;

        public Control(Attribut attribut, View view, TYP type) {
            this.attribut = attribut;
            this.view = view;
            this.type = type;
        }
    }

    enum TYP{
        TEXT,EDITE,INPUT
    }
}
