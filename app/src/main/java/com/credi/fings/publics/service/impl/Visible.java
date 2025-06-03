package com.credi.fings.publics.service.impl;

import java.io.Serializable;

public class Visible implements Serializable {
    private Attribut attribut;
    private Object valeurs;

    public Visible(Attribut attribut, Object valeurs) {
        this.attribut = attribut;
        this.valeurs = valeurs;
    }

    public Attribut getAttribut() {
        return attribut;
    }

    public void setAttribut(Attribut attribut) {
        this.attribut = attribut;
    }

    public Object getValeurs() {
        return valeurs;
    }

    public void setValeurs(Object valeurs) {
        this.valeurs = valeurs;
    }
}
