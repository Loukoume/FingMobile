package com.credi.fings.publics;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.credi.fings.R;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fings.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fings.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Request;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ListeFonte implements Serializable, BinderInterface {
    private String idLocal;
    @Expose
    @SerializedName("idListeFonte")
    private Long idServeur;
    private String libelle;
    private String url;

    public String getIdLocal() {
        return this.idLocal;
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
        return this.libelle;
    }


    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    public String getUrl() {
        return this.url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public ListeFonte fromJs(String js) {
        return new Gson().fromJson(js, ListeFonte.class);
    }

    @Override
    public Binder binder() {
        Binder binder = new Binder("libelle", "url")
                .setDefaultMenu().setFontFamily("url");

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
                new Attribut("Local", "idLocal", true),
                new Attribut("Serveur", "idServeur", true),
                new Attribut("Libelle", "libelle", true),
                new Attribut("Url", "url", true)
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


