package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.io.Serializable;

public class Operation implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idOperation")
    private String idServeur;
    private String type;
    private String motif;
    private Double montant;

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


    public String getType() {
        return this.type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public Double getMontant() {
        return this.montant;
    }


    public void setMontant(Double montant) {
        this.montant = montant;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public Operation fromJs(String js) {
        return new Gson().fromJson(js, Operation.class);
    }
}


