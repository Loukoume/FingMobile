package com.credi.fings.publics.service.pojo;

import java.io.Serializable;

public class LocalData implements Serializable {
    private String table;
    public String value;
    private String condition;
    private Class<?> dataClasse;



    public Class<?> getDataClasse() {
        return dataClasse;
    }

    public void setDataClasse(Class<?> dataClasse) {
        this.dataClasse = dataClasse;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
