package com.credi.fings.publics.service.impl;

import java.io.Serializable;
import java.util.List;


public class EditeObject implements Serializable {
    private Object object;
    private Class<?> aClass;
   private String id;
    private List<String> exclud;
    private List<Attribut> attribute;
    private String designation;
    private String postUrl;
    private String buttonLabel;
    private Class<?> navigateClass;


    public String getButtonLabel() {
        return buttonLabel;
    }

    public EditeObject setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
        return this;
    }

    public Class<?> getNavigateClass() {
        return navigateClass;
    }

    public void setNavigateClass(Class<?> navigateClass) {
        this.navigateClass = navigateClass;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public List<Attribut> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribut> attribute) {
        this.attribute = attribute;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<String> getExclud() {
        return exclud;
    }

    public void setExclud(List<String> exclud) {
        this.exclud = exclud;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
