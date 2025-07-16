package com.credi.fing.publics.utils;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.credi.fing.R;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fing.publics.adapters.generiqueAdapter.Binder;
import com.credi.fing.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fing.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fing.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fing.publics.service.BinderInterface;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.Request;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class Media implements Serializable, BinderInterface {
    @Expose
    @SerializedName("idMedia")
    private Long idServeur;
    @SerializedName("id_media")

    private long idMedia;
    private String url;
    private String designation;
    private String type;
    private String nomTable;
    @SerializedName("id")

    private long id;
    private String modifiedBy;
    private Timestamp deletedAt;
    private String deletedBy;
    private String path;
    private String texte;
    private String miniaturePath;
    private String idLocal;
    private String size;

    public Long getIdServeur() {
        return this.idServeur;
    }


    public void setIdServeur(Long idServeur) {
        this.idServeur = idServeur;
    }


    public long getIdMedia() {
        return this.idMedia;
    }


    public void setIdMedia(long idMedia) {
        this.idMedia = idMedia;
    }


    public String getUrl() {
        return this.url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getDesignation() {
        return this.designation;
    }


    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getType() {
        return this.type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getNomTable() {
        return this.nomTable;
    }


    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }


    public long getId() {
        return this.id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }


    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }


    public String getDeletedBy() {
        return this.deletedBy;
    }


    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }


    public String getPath() {
        return this.path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getTexte() {
        return this.texte;
    }


    public void setTexte(String texte) {
        this.texte = texte;
    }


    public String getMiniaturePath() {
        return this.miniaturePath;
    }


    public void setMiniaturePath(String miniaturePath) {
        this.miniaturePath = miniaturePath;
    }


    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public String getSize() {
        return this.size;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public Media fromJs(String js) {
        return new Gson().fromJson(js, Media.class);
    }

    @Override
    public Binder binder() {
        Binder binder = new Binder("", "")
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
                new Attribut("Serveur", "idServeur", true),
                new Attribut("Media", "idMedia", true),
                new Attribut("Url", "url", true),
                new Attribut("Designation", "designation", true),
                new Attribut("Type", "type", true),
                new Attribut("Nomtable", "nomTable", true),
                new Attribut("", "id", true),
                new Attribut("Path", "path", true),
                new Attribut("Texte", "texte", true),
                new Attribut("Miniaturepath", "miniaturePath", true),
                new Attribut("Local", "idLocal", true),
                new Attribut("Size", "size", true)
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


