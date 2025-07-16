package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.io.Serializable;

public class Group implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idGroup")
    private String idServeur;
    private String libelle;

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


    public String getLibelle() {
        return this.libelle;
    }


    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public Group fromJs(String js) {
        return new Gson().fromJson(js, Group.class);
    }
}


