package com.credi.fing.publics.adapters.generiqueAdapter;

import java.io.Serializable;

public class SwitchBinder implements Serializable {
    private String field;
    private Object respons;
    private Object value;
    private Object defaultValue;

    public SwitchBinder(String field, Object respons, Object value) {
        this.field = field;
        this.respons = respons;
        this.value = value;
    }

    public SwitchBinder(String field, Object respons, Object value, Object defaultValue) {
        this.field = field;
        this.respons = respons;
        this.value = value;
        this.defaultValue = defaultValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRespons() {
        return respons;
    }

    public void setRespons(Object respons) {
        this.respons = respons;
    }

    public Object getValue() {
        return value;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
