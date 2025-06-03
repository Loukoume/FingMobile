package com.credi.fings.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fings.publics.AddActivity;
import com.credi.fings.R;
import com.credi.fings.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fings.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fings.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Request;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class SubStatus implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idSubStatus")
    private String idServeur;
    private String code;
    private String value;
    private Boolean none;
    private Boolean inactive;
    private Boolean dormant;
    private Boolean escheat;
    private Boolean block;
    private Boolean blockCredit;
    private Boolean blockDebit;

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


    public Boolean getNone() {
        return this.none;
    }


    public void setNone(Boolean none) {
        this.none = none;
    }


    public Boolean getInactive() {
        return this.inactive;
    }


    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }


    public Boolean getDormant() {
        return this.dormant;
    }


    public void setDormant(Boolean dormant) {
        this.dormant = dormant;
    }


    public Boolean getEscheat() {
        return this.escheat;
    }


    public void setEscheat(Boolean escheat) {
        this.escheat = escheat;
    }


    public Boolean getBlock() {
        return this.block;
    }


    public void setBlock(Boolean block) {
        this.block = block;
    }


    public Boolean getBlockCredit() {
        return this.blockCredit;
    }


    public void setBlockCredit(Boolean blockCredit) {
        this.blockCredit = blockCredit;
    }


    public Boolean getBlockDebit() {
        return this.blockDebit;
    }


    public void setBlockDebit(Boolean blockDebit) {
        this.blockDebit = blockDebit;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public SubStatus fromJs(String js) {
        return new Gson().fromJson(js, SubStatus.class);
    }
}


