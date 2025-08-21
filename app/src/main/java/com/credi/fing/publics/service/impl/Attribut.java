package com.credi.fing.publics.service.impl;

import com.credi.fing.publics.dataTable.Head;
import com.credi.fing.publics.utils.S;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Attribut implements Serializable {
    private String name;
    private String colonne;
    private String type;
    private boolean icone=true;
    private boolean requierd;
    private Class<?> t;
    private boolean griser;
    private Object defaul;
    private boolean radio;
    private Visible visible;
    private List<Object> values;
    private List<Object> valuesSelected;
    private String label;
    private String subLabel;
    private String field;
    private String valueField;
    private Request request;
    private List<Head> heads;

    private List<Attribut> attributs;

    public Request getRequest() {
        return request;
    }

    public String getField() {
        return field;
    }

    public Attribut setField(String field) {
        this.field = field;
        return this;
    }

    public List<Attribut> getAttributs() {
        return attributs;
    }

    public boolean isGriser() {
        return griser;
    }



    @Override
    public int hashCode() {
        return Objects.hashCode(getColonne());
    }

    public void setGriser(boolean griser) {
        this.griser = griser;
    }

    public List<Head> getHeads() {
        return heads;
    }

    public Attribut setHeads(List<Head> heads) {
        this.heads = heads;
        return this;
    }

    public boolean isIcone() {
        return icone;
    }

    public Attribut setIcone(boolean icone) {
        this.icone = icone;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColonne(String colonne) {
        this.colonne = colonne;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setT(Class<?> t) {
        this.t = t;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public Attribut setSubLabel(String subLabel) {
        this.subLabel = subLabel;
        return this;
    }

    public Attribut setAttributs(List<Attribut> attributs) {
        this.attributs = attributs;
        return this;
    }

    public String getValueField() {
        return valueField;
    }

    public Attribut setValueField(String valueField) {
        this.valueField = valueField;
        return this;
    }

    public List<Object> getValuesSelected() {
        return valuesSelected;
    }

    public Attribut setValuesSelected(List<Object> valuesSelected) {
        this.valuesSelected = valuesSelected;
        return this;
    }

    public Attribut setRequest(Request request) {
        this.request = request;
        return this;
    }

    public Visible getVisible() {
        return visible;
    }

    public Attribut setVisible(Visible visible) {
        this.visible = visible;
        return this;
    }

    public String getLabel() {
        if(label!=null||values==null)
            return label;
        if(values.isEmpty()){
            return null;
        }
        Object val=values.get(0);
        if(Ut.isPrimitiveOrWrapper(val.getClass())){
            return null;
        }
        return Arrays.stream(val.getClass().getDeclaredFields())
                .map(Field::getName)
                .filter(f->f.equalsIgnoreCase("libelle")||
                        f.equalsIgnoreCase("designation")||
                        f.equalsIgnoreCase("nom")||
                        f.equalsIgnoreCase("nam")||
                        f.equalsIgnoreCase("title")||
                        f.equalsIgnoreCase("prenom")||
                        f.equalsIgnoreCase("name")||
                        f.equalsIgnoreCase("titre"))

                .findFirst().orElse(null);
    }

    public Class<?> getT() {
        return t;
    }

    public boolean isRequierd() {
        return requierd;
    }

    public void setRequierd(boolean requierd) {
        this.requierd = requierd;
    }

    public Attribut setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public Attribut setValues(List<Object> values) {
        this.values = values;
        return this;
    }
    public Attribut setValuess(List<Object> values) {
        this.values = values;
        return this;
    }
    public Attribut setValues(Object... values) {
        this.values=new ArrayList<>();
        for (Object o:values){
            this.values.add(o);
        }
        return this;
    }

    public Attribut() {
    }

    public Attribut(String colonne) {
        this.colonne = colonne;
        this.name= S.phrase(colonne);
        this.type="string";
    }

    public Attribut(String colonne,boolean requierd) {
        this.colonne = colonne;
        this.name= S.phrase(colonne);
        this.type="string";
        this.requierd=requierd;
    }

    public Attribut(String name, String colonne,boolean requierd) {
        this.name = name;
        this.colonne = colonne;
        this.type="string";
        this.requierd=requierd;
    }

    public Attribut(String name, String colonne, String type,boolean requierd) {
        this.name = name;
        this.colonne = colonne;
        this.type = type;
        this.requierd=requierd;
    }
    public Attribut(String name, String colonne, String type,boolean requierd,List<Object> objects) {
        this.name = name;
        this.colonne = colonne;
        this.type = type;
        this.requierd=requierd;
        this.values=objects;
    }

    public Attribut(String name, String colonne, String type, Class<?> t,boolean requierd) {
        this.name = name;
        this.colonne = colonne;
        this.type = type;
        this.t = t;
        this.requierd=requierd;
    }

    public String getName() {
        return name;
    }

    public String getColonne() {
        return colonne;
    }

    public String getType() {
        return type;
    }



    public Attribut setDefaul(Object defaul) {
        this.defaul=defaul;
        return this;
    }

    public Object getDefaul() {
        return defaul;
    }

    public Attribut setRadio(boolean radio) {
        this.radio = radio;
        return this;
    }

    public boolean isRadio() {
        return radio;
    }
}
