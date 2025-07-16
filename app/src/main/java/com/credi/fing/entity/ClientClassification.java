package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.io.Serializable;

public class ClientClassification implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idClientClassification")
    private String idServeur;
    private Boolean active;
    private Boolean mandatory;

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


    public Boolean getActive() {
        return this.active;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }


    public Boolean getMandatory() {
        return this.mandatory;
    }


    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public ClientClassification fromJs(String js) {
        return new Gson().fromJson(js, ClientClassification.class);
    }
}


