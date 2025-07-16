package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.io.Serializable;

public class DepositType implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idDepositType")
    private String idServeur;
    private String code;
    private String value;

    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public String getIdServeur() {
        return this.idServeur;
    }


    public void setIdServeur(String idServeur) {
        this.idServeur = idServeur;
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

    public DepositType fromJs(String js) {
        return new Gson().fromJson(js, DepositType.class);
    }
}


