package com.credi.fings.publics.drawer.menu;

import androidx.annotation.NonNull;

import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Album;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu implements Serializable,Cloneable {
    private String titre;
    private String idObjet;
    @SerializedName("idMenu")
    @Expose
    private Long idServeur;
    private String url;
    private String icon;
    private String module;
    private String id;
    private String idParent;
    private List<Menu> fils;

    @NonNull
    @Override
    protected Menu clone() {
        try {
            return (Menu) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Menu> getFils() {
        return fils;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(Long idServeur) {
        this.idServeur = idServeur;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public void setFils(List<Menu> fils) {
        this.fils = fils;
    }
    public Album albom(Menu m){
        Album album=new Album();
        album.setJson(Ut.js(m));
        //System.out.println(album.getJson());
        //System.out.println(Arrays.stream(m.getClass().getDeclaredFields()).map(s->s.getName()).collect(Collectors.toList()));
       // System.out.println("====x=====");
        album.setName(m.titre);
        album.setId(m.id);
        album.setSeparator(m.url==null&&m.icon==null);
       // album.setUp(m.getFils()!=null);
        return album;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "titre='" + titre + '\'' +
                ", idObjet='" + idObjet + '\'' +
                ", idServeur=" + idServeur +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", module='" + module + '\'' +
                ", id='" + id + '\'' +
                ", idParent='" + idParent + '\'' +
                ", fils=" + fils +
                '}';
    }

    public Menu fromJs(String js){
        //System.out.println("=js="+js);
        return new Gson().fromJson(js, Menu.class);
    }
    public String js(){
        return new Gson().toJson(this);
    }
    public List<Menu> fromListJs(String js){
        //System.out.println("=js="+js);
        return new Gson().fromJson(js,Cl.class).datas;
    }
    public String js(List<Menu> menus){
        return new Gson().toJson(new Cl(menus));
    }

    public List<Album> alboms(List<Menu> o) {
        List<Menu> menus=new ArrayList<>();
        for (Menu ob:o){
           boolean sep=ob.url==null&&ob.icon==null;
            System.out.printf(" ob= "+ob+"");
           if(sep){
               List<Menu> fls=ob.fils;
               if(fls!=null){
                   Menu b=ob.clone();
                   b.setFils(null);
                   menus.add(b);
                   menus.addAll(fls);
               }else {
                   menus.add(ob);
               }
           }else {
               menus.add(ob);
           }
        }
        return menus.stream().map(e->albom(e)).collect(Collectors.toList());
    }

    static class Cl implements Serializable {
        private List<Menu> datas;

        public Cl(List<Menu> datas) {
            this.datas = datas;
        }

        public List<Menu> getDatas() {
            return datas;
        }

        public void setDatas(List<Menu> datas) {
            this.datas = datas;
        }
    }
}
