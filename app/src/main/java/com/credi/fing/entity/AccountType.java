package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.io.Serializable;

public class AccountType implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("id")
    private Integer idServeur;
    private String code;
    private String value;

    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public Integer getIdServeur() {
        return this.idServeur;
    }


    public void setIdServeur(Integer idServeur) {
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

    public AccountType fromJs(String js) {
        return new Gson().fromJson(js, AccountType.class);
    }


    public AccountTypeOption typeOption(){
        AccountTypeOption accountTypeOption=new AccountTypeOption();
        accountTypeOption.setCode(code);
        accountTypeOption.setId(idServeur);
        accountTypeOption.setValue(value);
        return accountTypeOption;
    }
}


