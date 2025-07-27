package com.credi.fing.entity;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String date;
    private String motif;
    private String type;
    private String montant;

    public Transaction() {
    }

    public Transaction(String motif) {
        this.motif = motif;
    }

    public Transaction(String motif, String type, String montant) {
        this.motif = motif;
        this.type = type;
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }
}
