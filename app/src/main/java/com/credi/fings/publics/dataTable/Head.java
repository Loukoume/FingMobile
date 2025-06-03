package com.credi.fings.publics.dataTable;

import java.io.Serializable;

public class Head implements Serializable {
    private String libelle;
    private String colonne;
    public int width;

    public Head() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Head(String libelle, String colonne,int width) {
        this.libelle = libelle;
        this.colonne = colonne;
        this.width=width;
    }
    public Head(String libelle, String colonne) {
        this.libelle = libelle;
        this.colonne = colonne;
        this.width=60;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getColonne() {
        return colonne;
    }

    public void setColonne(String colonne) {
        this.colonne = colonne;
    }

    @Override
    public String toString() {
        return "{" + "'" + libelle + '\'' +",'" + colonne + '\'' +"," + width + '}';
    }
}
