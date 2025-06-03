package com.credi.fings.publics.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {
    private String idLocal;
    @Expose
    @SerializedName("idAlbum")
    private String idServeur;
    @SerializedName("id")

    private String id;
    private String name;
    private String etat;
    private int photo;
    private String json;
    private int img;
    private boolean up;
    private boolean separator;

    public Album(String s, int cover) {
        this.name=s;
        this.img=cover;
        this.photo=cover;
    }
    public Album(String s, int cover,int img) {
        this.name=s;
        this.img=img;
        this.photo=cover;
    }
    public Album() {
    }

    public int getImg() {
        return img;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean getUp() {
        return up;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getIdLocal() {
        return this.idLocal;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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


    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEtat() {
        return this.etat;
    }


    public void setEtat(String etat) {
        this.etat = etat;
    }


    public int getPhoto() {
        return this.photo;
    }


    public void setPhoto(int photo) {
        this.photo = photo;
    }


    public String affTitre() {
        return name;
    }

    public String affSousTitre() {
        return "";
    }

    public boolean isSeparator() {
        return separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }
}


