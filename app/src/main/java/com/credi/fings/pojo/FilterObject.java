package com.credi.fings.pojo;

import java.util.List;

public class FilterObject {
    private List<Integer> annees;
    private List<Integer> equipement;
    private List<Integer> secteurs;


    public FilterObject() {
    }

    public List<Integer> getAnnees() {
        return annees;
    }

    public void setAnnees(List<Integer> annees) {
        this.annees = annees;
    }


    public List<Integer> getEquipement() {
        return equipement;
    }

    public void setEquipement(List<Integer> equipement) {
        this.equipement = equipement;
    }

    public List<Integer> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Integer> secteurs) {
        this.secteurs = secteurs;
    }
}
