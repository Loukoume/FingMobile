package com.credi.fings.publics.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.credi.fings.publics.Compte;

import java.util.ArrayList;
import java.util.List;

public class LesConnectes {
    private List<Compte> Comptes;

    public LesConnectes(List<Compte> Comptes) {
        this.Comptes = Comptes;
    }

    public LesConnectes() {
    }

    public List<Compte> getComptes() {
        return Comptes;
    }

    public void setComptes(List<Compte> Comptes) {
        this.Comptes = Comptes;
    }

    public String json(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

    public LesConnectes fromJs(String js){
        Gson gson=new Gson();
       try{
           return gson.fromJson(js,LesConnectes.class);
       }catch (Exception e){
           List<Compte> cl=new ArrayList<>();
           return new LesConnectes(cl);
       }
    }
    public List<Compte> comptes(Context c){
        String js= MonFichier.lire(c,"LesConnectes");
        if(js.length()==0)return new ArrayList<>();
        return fromJs(js).Comptes;
    }
    public Compte compte(Context c){
        String js= MonFichier.lire(c,"LesConnectes");
        if(js.length()==0||fromJs(js).Comptes.size()==0)return null;
        return fromJs(js).Comptes.get(0);
    }

    public void add(Context c,Compte ct){
        List<Compte> l=comptes(c);
        for(Compte cp:l){
            if(cp.getIdServeur()==ct.getIdServeur())return;
        }
        l.add(ct);
        MonFichier.ecrire(c,"LesConnectes",new LesConnectes(l).json());
    }
    public void remove(Context c,Compte ct){
        List<Compte> l=comptes(c);int i=0;
        for(Compte cp:l){
            if(cp.getIdServeur().equals(ct.getIdServeur())){
                l.remove(i);
                 MonFichier.ecrire(c,"LesConnectes",new LesConnectes(l).json());
                return;
            }
            i++;
        }
    }

    public void update(Context c,Compte ct){
        List<Compte> l=comptes(c);int i=0;
        for(Compte cp:l){
            if(cp.getIdServeur()==(ct.getIdServeur())){
                l.remove(i);l.add(ct);
                MonFichier.ecrire(c,"LesConnectes",new LesConnectes(l).json());
                return;
            }
            i++;
        }
    }

}
