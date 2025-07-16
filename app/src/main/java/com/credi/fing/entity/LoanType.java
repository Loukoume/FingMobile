package com.credi.fing.entity;

import com.google.gson.Gson;

import com.credi.fing.R;

import java.io.Serializable;

public class LoanType implements Serializable {
    private String idLocal;

    private Long id;
    private String code;
    private String value;

    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getValue() {
        return this.value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public LoanType fromJs(String js) {
        return new Gson().fromJson(js, LoanType.class);
    }
}


