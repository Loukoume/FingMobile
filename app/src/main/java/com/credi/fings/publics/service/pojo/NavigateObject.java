package com.credi.fings.publics.service.pojo;

import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.service.IBinder;
import com.credi.fings.publics.service.impl.EditeObject;

import java.io.Serializable;
import java.util.List;

public class NavigateObject implements Serializable {
    private Binder binder;
    private String title;
    private String subTitle;
    private EditeObject editeObject;
    private Boolean addButton;
    private String endPointSave;
    private String dataUrl;
    private Object postData;
    private Object object;
    private Class<?> aClass;
    private int typeData;
    private LocalData localData;
    private List<Object> values;
    private transient IBinder iBinder;

    public NavigateObject() {
    }

    public NavigateObject(Binder binder, EditeObject editeObject, Boolean addButton, String endPointSave, Class<?> aClass) {
        this.binder = binder;
        this.editeObject = editeObject;
        this.addButton = addButton;
        this.endPointSave = endPointSave;
        this.aClass = aClass;
    }

    public LocalData getLocalData() {
        return localData;
    }

    public NavigateObject setLocalData(LocalData localData) {
        this.localData = localData;
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public int getTypeData() {
        return typeData;
    }

    public IBinder getiBinder() {
        return iBinder;
    }

    public void setiBinder(IBinder iBinder) {
        this.iBinder = iBinder;
    }

    public NavigateObject setTypeData(int typeData) {
        this.typeData = typeData;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public NavigateObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public NavigateObject setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public Object getPostData() {
        return postData;
    }

    public NavigateObject setPostData(Object postData) {
        this.postData = postData;
        return this;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public NavigateObject setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
        return this;
    }

    public Binder getBinder() {
        return binder;
    }

    public NavigateObject setBinder(Binder binder) {
        this.binder = binder;
        return this;
    }

    public EditeObject getEditeObject() {
        return editeObject;
    }

    public NavigateObject setEditeObject(EditeObject editeObject) {
        this.editeObject = editeObject;
        return this;
    }

    public Boolean getAddButton() {
        return addButton;
    }

    public NavigateObject setAddButton(Boolean addButton) {
        this.addButton = addButton;
        return this;
    }

    public String getEndPointSave() {
        return endPointSave;
    }

    public NavigateObject setEndPointSave(String endPointSave) {
        this.endPointSave = endPointSave;
        return this;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public NavigateObject setaClass(Class<?> aClass) {
        this.aClass = aClass;
        return this;
    }
}
