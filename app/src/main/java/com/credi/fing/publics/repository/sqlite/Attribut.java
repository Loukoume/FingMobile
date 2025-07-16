package com.credi.fing.publics.repository.sqlite;
public class Attribut{
    private String nom;
    private String type;
    private String privilege;

    public Attribut(String nom, String type, String privilege) {
        this.nom = nom;
        this.type = type;
        this.privilege = privilege;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrivilege() {
        if(privilege==null) return " ";
        return " "+privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
