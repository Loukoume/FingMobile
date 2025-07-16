package com.credi.fing.publics.service.pojo;

import java.io.Serializable;

public class Navig implements Serializable {
    private String libelle;
    private int num;
    private NavigateObject navigateObject;
    private Class<?> aClass;
    private Object object;
    private int icon;


    public Navig(NavigateObject navigateObject) {
        this.navigateObject = navigateObject;
    }

    public Navig() {
    }

    public int getIcon() {
        return icon;
    }

    public Navig setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getLibelle() {
        return libelle;
    }

    public Navig setLibelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public int getNum() {
        return num;
    }

    public Navig setNum(int num) {
        this.num = num;
        return this;
    }

    public NavigateObject getNavigateObject() {
        return navigateObject;
    }

    public Navig setNavigateObject(NavigateObject navigateObject) {
        this.navigateObject = navigateObject;
        return this;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public Navig setaClass(Class<?> aClass) {
        this.aClass = aClass;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public Navig setObject(Object object) {
        this.object = object;
        return this;
    }
}
