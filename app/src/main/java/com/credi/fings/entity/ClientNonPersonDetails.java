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

public class ClientNonPersonDetails implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idClientNonPersonDetails")
    private String idServeur;
    private Constitution constitution;
    private MainBusinessLine mainBusinessLine;

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


    public Constitution getConstitution() {
        return this.constitution;
    }


    public void setConstitution(Constitution constitution) {
        this.constitution = constitution;
    }


    public MainBusinessLine getMainBusinessLine() {
        return this.mainBusinessLine;
    }


    public void setMainBusinessLine(MainBusinessLine mainBusinessLine) {
        this.mainBusinessLine = mainBusinessLine;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public ClientNonPersonDetails fromJs(String js) {
        return new Gson().fromJson(js, ClientNonPersonDetails.class);
    }
}


