package com.credi.fing.publics.service.impl;


import java.io.Serializable;

public class Request implements Serializable {
    private Attribut attribut;
    private String url;
    private Object param;
    private String pathVariable;
    private Class<?> navigateClasse;

    public Request() {
    }

    public String getPathVariable() {
        return pathVariable;
    }

    public Request setPathVariable(String pathVariable) {
        this.pathVariable = pathVariable;
        return this;
    }

    public Request(Attribut attribut, String url) {
        this.attribut = attribut;
        this.url = url;
    }
    public Request(String url) {
        this.url = url;
    }

    public Object getParam() {
        return param;
    }

    public Request setParam(Object param) {
        this.param = param;
        return this;
    }

    public Class<?> getNavigateClasse() {
        return navigateClasse;
    }

    public Request setNavigateClasse(Class<?> navigateClasse) {
        this.navigateClasse = navigateClasse;
        return this;
    }

    public Attribut getAttribut() {
        return attribut;
    }

    public Request setAttribut(Attribut attribut) {
        this.attribut = attribut;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
