package com.credi.fing.publics;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.credi.fing.R;
import com.credi.fing.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fing.publics.adapters.generiqueAdapter.Binder;
import com.credi.fing.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fing.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fing.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fing.publics.service.BinderInterface;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.Request;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Style implements Serializable, BinderInterface,Cloneable {
    private String idLocal;
    @Expose
    @SerializedName("idStyle")
    private Long idServeur;
    private String libelle;
    private String fontFamily;
    private Integer taille;
    private String couleur;
    private String fonte;
    private Integer margeLeft;
    private boolean appliquerMargeLeftSurToutesLignes;
    private Integer margeTop;
    private boolean upercase;
    private String style;

    public boolean isAppliquerMargeLeftSurToutesLignes() {
        return appliquerMargeLeftSurToutesLignes;
    }

    public void setAppliquerMargeLeftSurToutesLignes(boolean appliquerMargeLeftSurToutesLignes) {
        this.appliquerMargeLeftSurToutesLignes = appliquerMargeLeftSurToutesLignes;
    }

    public String getIdLocal() {
        return this.idLocal;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public Long getIdServeur() {
        return this.idServeur;
    }


    public void setIdServeur(Long idServeur) {
        this.idServeur = idServeur;
    }


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getTaille() {
        return taille==null?0:taille;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getFonte() {
        return fonte;
    }

    public void setMargeTop(Integer margeTop) {
        this.margeTop = margeTop;
    }

    public boolean isUpercase() {
        return upercase;
    }

    public void setUpercase(boolean upercase) {
        this.upercase = upercase;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }


    public Integer getMargeLeft() {
        return this.margeLeft!=null?this.margeLeft*10:0;
    }


    public void setMargeLeft(Integer margeLeft) {
        this.margeLeft = margeLeft;
    }


    public Integer getMargeTop() {
        return this.margeTop;
    }

    @Override
    public Style clone() {
        try {
            return (Style) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }



    public String js() {
        return new Gson().toJson(this);
    }

    public Style fromJs(String js) {
        return new Gson().fromJson(js, Style.class);
    }

    @Override
    public Binder binder() {
        Binder binder = new Binder("libelle", "couleur")
                .setFontFamily("fonte")
                .setDefaultMenu();

        binder.setRowClick(new MenuContextuel(
                ActionMenu.NAVIGATE, AddActivity.class
        ));
        binder.setRowObject(new RowObject(R.layout.card_image_horiz_row, ElementRow.SIMPL_MEDIA)
        );

        binder.setMediaClick(new MenuContextuel(
                ActionMenu.NAVIGATE, AddActivity.class
        ));

        return binder;
    }

    @Override
    public List<Attribut> setAttribut() {
        List<Attribut> attributs = Arrays.asList(
                new Attribut("Libelle", "libelle", true),
                new Attribut("Taille", "taille","number", true),
                new Attribut("Couleur", "couleur","object", true)
                        .setValues("ROUGE","NOIRE","BLEU","VERT","ROSE","CAFE","ROUGE SOMBRE"),
                new Attribut("Fonte", "fonte","object", false)
                        .setRequest(new Request().setNavigateClasse(ListeFonteActivity.class)),
                new Attribut("Marge gauche", "margeLeft","number", true),
                new Attribut("Marge haut", "margeTop","number", true),
                new Attribut("TEXTE EN MAJUSCULE", "upercase","object", true)
                        .setValues(Arrays.asList("OUI","NON")),
                new Attribut("Style", "style","object", true)
                        .setValues("NORMAL","GRAS","ITALIC")
        );
        /*attributs.get(3).setValues(Arrays.asList("TAUX","INTERVAL"));
        Request request=new Request(attributs.get(0),"tmobile_operation/find_by_operateur");
        attributs.get(1).setRequest(request);
        attributs.get(1).setLabel("designation");*/
        return attributs;
    }

    @Override
    public List<Attribut> setAttribut(List<Object> values) {
        List<Attribut> attributs = setAttribut();
        return attributs;
    }

    @Override
    public void setRequest(String nam, Request request) {
        BinderInterface.super.setRequest(nam, request);
    }
}


