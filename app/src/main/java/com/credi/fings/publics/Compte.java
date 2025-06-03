package com.credi.fings.publics;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.credi.fings.R;
import com.credi.fings.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fings.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fings.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Request;
import com.credi.fings.publics.service.impl.Visible;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Compte implements Serializable , BinderInterface {
    private String idLocal;
    @Expose
    @SerializedName("idCompte")
    private Long idServeur;
    private String nom;
    private String prenom;
    private String login;
    private String email;
    private String motDePasse;
    private String telephone;
    private Profil profile;
    @SerializedName("idMedia")
    private Media idMedia;
   // private NiveauHierarchique idNiveauHierarchique;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp modifiedAt;
    private String modifiedBy;
    private Timestamp deletedAt;
    private String deletedBy;
    private String niveau;


    private List<String> routes;


    private String localite;
    private Boolean salarie;


    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
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

    public String getNom() {
        return this.nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom() {
        return this.prenom;
    }


    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String getLogin() {
        return this.login;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return this.motDePasse;
    }


    public void setPassword(String password) {
        this.motDePasse = password;
    }


    public String getTelephone() {
        return this.telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Profil getProfile() {
        return this.profile;
    }


    public void setProfile(Profil profile) {
        this.profile = profile;
    }


    public Media getIdMedia() {
        return this.idMedia;
    }


    public void setIdMedia(Media idMedia) {
        this.idMedia = idMedia;
    }


    public String affTitre() {
        return nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

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

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Boolean getSalarie() {
        return salarie;
    }

    public void setSalarie(Boolean salarie) {
        this.salarie = salarie;
    }

    public String affSousTitre() {
        return "";
    }

    public String js() {
        return new Gson().toJson(this);
    }

    public Compte fromJs(String js) {
        return new Gson().fromJson(js, Compte.class);
    }


    @Override
    public Binder binder() {
        Binder binder=new Binder("nom&prenom","telephone?tel").setDefaultMenu();

        binder.setRowObject(new RowObject(R.layout.card_image_horiz_row, ElementRow.CIRC_MEDIA));

        binder.setMediaClick(new MenuContextuel(
                ActionMenu.NAVIGATE,AddActivity.class
        ));

        return binder;
    }

    @Override
    public List<Attribut> setAttribut() {
        List<Attribut> attributs= Arrays.asList(
                new Attribut("Profil","profile:libelle","oneSelect",true)
                        .setLabel("libelle")
                        .setRequest(new Request("profil/all")),
                new Attribut("Localité","localite",true)  ,
                new Attribut("Nom","nom",true)  ,
                new Attribut("Prénom","prenom",false)    ,
                new Attribut("Téléphone","telephone",true),
                new Attribut("Mot de passe","password",true),
                new Attribut("Login","login",true),
                new Attribut("Salarié?","salarie","boolean",false)
                        .setDefaul(true)

        );
        attributs.get(8).setVisible(new Visible(attributs.get(7),true));
        return attributs;
    }

    @Override
    public List<Attribut> setAttribut(List<Object> values) {
        return null;
    }

    @Override
    public List<Attribut> setAttribut(List<Object>... values) {
        List<Attribut> attributs= setAttribut();
        for(int i=0;i<values.length;i++){
            List<Object> value=values[i];
            if(i==1){
                attributs.stream().peek(
                        a->{
                            if(a.getName().equals("Profil")){
                                a.setValues(value);
                            }
                        }
                ).collect(Collectors.toList());
            }
        }

        return attributs;
    }
}


