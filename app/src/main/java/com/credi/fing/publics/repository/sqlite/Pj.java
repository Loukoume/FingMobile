package com.credi.fing.publics.repository.sqlite;

import java.util.List;

public class Pj {
    private List<Attribut> attributs;
    private String table;

    public Pj(List<Attribut> attributs, String table) {
        this.attributs = attributs;
        this.table = table;
    }

    public List<Attribut> getAttributs() {
        return attributs;
    }

    public void setAttributs(List<Attribut> attributs) {
        this.attributs = attributs;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
