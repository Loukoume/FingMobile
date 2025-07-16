package com.credi.fing.publics.utils;
public class Donnee {
    private String clet;
    private String valeur;


    public Donnee(String clet, String valeur) {
        this.clet = clet;
        this.valeur = valeur;
    }

    public String getClet() {
        return clet;
    }

    public void setClet(String clet) {
        this.clet = clet;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
