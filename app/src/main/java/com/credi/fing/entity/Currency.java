package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.io.Serializable;

public class Currency implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idCurrency")
    private String idServeur;
    private String code;
    private String name;
    private Integer decimalPlaces;
    private String displaySymbol;
    private String nameCode;
    private String displayLabel;

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


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Integer getDecimalPlaces() {
        return this.decimalPlaces;
    }


    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }


    public String getDisplaySymbol() {
        return this.displaySymbol;
    }


    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }


    public String getNameCode() {
        return this.nameCode;
    }


    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }


    public String getDisplayLabel() {
        return this.displayLabel;
    }


    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public Currency fromJs(String js) {
        return new Gson().fromJson(js, Currency.class);
    }
}


