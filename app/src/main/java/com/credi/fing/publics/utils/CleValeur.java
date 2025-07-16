package com.credi.fing.publics.utils;

public class CleValeur {
   private   Integer index;
   private Character valeur;
    private String valeur2;

    public CleValeur(Integer index, Character valeur) {
        this.index = index;
        this.valeur = valeur;
    }

    public CleValeur(Integer index, Character valeur, String valeur2) {
        this.index = index;
        this.valeur = valeur;
        this.valeur2 = valeur2;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Character getValeur() {
        return valeur;
    }

    public void setValeur(Character valeur) {
        this.valeur = valeur;
    }

    public String getValeur2() {
        return valeur2;
    }

    public void setValeur2(String valeur2) {
        this.valeur2 = valeur2;
    }
}
