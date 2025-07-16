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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Media implements Serializable , BinderInterface {
    private String idLocal;
    @Expose
    @SerializedName("idMedia")
    private Long idServeur;
    private String url;
    private String type;
    private String fileNam;
    private String tableNam;
    @SerializedName("id")

    private String id;

    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;
    private Timestamp deletedAt;
    private String deletedBy;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public Long getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(Long idServeur) {
        this.idServeur = idServeur;
    }

    public String getUrl() {
        return this.url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getType() {
        return this.type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getFileNam() {
        return this.fileNam;
    }


    public void setFileNam(String fileNam) {
        this.fileNam = fileNam;
    }


    public String getTableNam() {
        return this.tableNam;
    }


    public void setTableNam(String tableNam) {
        this.tableNam = tableNam;
    }


    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String affTitre() {
        return url;
    }

    public String affSousTitre() {
        return "";
    }

    public String js() {
        return new Gson().toJson(this);
    }

    public Media fromJs(String js) {
        return new Gson().fromJson(js, Media.class);
    }


    @Override
    public Binder binder() {
        Binder binder=new Binder("designation","syntax");
        binder.setMenus(Arrays.asList(new MenuContextuel("Supprimer", ActionMenu.DELETE),new MenuContextuel("Modifier",ActionMenu.UPDATE),
                new MenuContextuel("Quitter", ActionMenu.FINISH)));

        binder.setRowClick(new MenuContextuel(
                ActionMenu.NAVIGATE, AddActivity.class
        ));
        binder.setRowObject(new RowObject(R.layout.card_image_horiz_row, ElementRow.SIMPL_MEDIA)
                .putDrawableIcon(R.drawable.baseline_add_call_24));

        binder.setMediaClick(new MenuContextuel(
                ActionMenu.NAVIGATE,AddActivity.class
        ));

        return binder;
    }

    @Override
    public List<Attribut> setAttribut() {
        return Arrays.asList(
                new Attribut("Opérateur","idOperateur:designation","object",true)  ,
                new Attribut("Désignation","designation",true)    ,
                new Attribut("La syntaxe","syntax",true)
        );
    }

    @Override
    public List<Attribut> setAttribut(List<Object> values) {
        List<Attribut> attributs= Arrays.asList(
                new Attribut("Opérateur","idOperateur:designation","object",true)  ,
                new Attribut("Désignation","designation",true)    ,
                new Attribut("La syntaxe","syntax",true)
        );
        attributs.stream().peek(
                a->{
                    if(a.getName().equals("Opérateur")){
                        a.setLabel("designation");
                    }
                }
        ).collect(Collectors.toList());
        return attributs;
    }
}


