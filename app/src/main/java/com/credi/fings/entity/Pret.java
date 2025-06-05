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

public class Pret implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idPret")
    private String idServeur;
    @SerializedName("idLoanType")

    private LoanType idLoanType;
    private Double montant;
    @SerializedName("idSavingsAccount")

    private SavingsAccount idSavingsAccount;
    @SerializedName("idSavingsTimeline")

    private SavingsDate idSavingsTimeline;
    private String objet;
    private String client;

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


    public LoanType getIdLoanType() {
        return this.idLoanType;
    }


    public void setIdLoanType(LoanType idLoanType) {
        this.idLoanType = idLoanType;
    }


    public Double getMontant() {
        return this.montant;
    }


    public void setMontant(Double montant) {
        this.montant = montant;
    }


    public SavingsAccount getIdSavingsAccount() {
        return this.idSavingsAccount;
    }


    public void setIdSavingsAccount(SavingsAccount idSavingsAccount) {
        this.idSavingsAccount = idSavingsAccount;
    }



    public String getObjet() {
        return this.objet;
    }


    public void setObjet(String objet) {
        this.objet = objet;
    }


    public String getClient() {
        return this.client;
    }


    public void setClient(String client) {
        this.client = client;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public Pret fromJs(String js) {
        return new Gson().fromJson(js, Pret.class);
    }
}


