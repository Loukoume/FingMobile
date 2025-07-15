package com.credi.fings.publics.utils;

import android.content.Context;

import com.credi.fings.entity.User;
import com.google.gson.Gson;
import com.credi.fings.publics.Compte;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LesConnectes {
    private List<User> Comptes;

    public LesConnectes(List<User> Comptes) {
        this.Comptes = Comptes;
    }

    public LesConnectes() {
    }

    public List<User> getComptes() {
        return Comptes;
    }

    public void setComptes(List<User> Comptes) {
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
           List<User> cl=new ArrayList<>();
           return new LesConnectes(cl);
       }
    }
    public List<User> comptes(Context c){
        String js= MonFichier.lire(c,"LesConnectes");
        if(js.length()==0)return new ArrayList<>();
        return fromJs(js).Comptes;
    }
    public User compte(Context c){
        String js= MonFichier.lire(c,"LesConnectes");
        if(js.length()==0||fromJs(js).Comptes.size()==0)return null;
        return fromJs(js).Comptes.get(0);
    }

    public void add(Context c,User ct){
        List<User> l=comptes(c);
        for(User cp:l){
            if(Objects.equals(cp.getUserId(), ct.getUserId()))return;
        }
        l.add(ct);
        MonFichier.ecrire(c,"LesConnectes",new LesConnectes(l).json());
    }
    public void remove(Context c,User ct){
        List<User> l=comptes(c);int i=0;
        System.out.println("ll=="+l);
        for(User cp:l){
            System.out.println();
            if(cp.getUserId().equals(ct.getUserId())){
                l.remove(i);
                 MonFichier.ecrire(c,"LesConnectes",new LesConnectes(l).json());
                return;
            }
            i++;
        }
    }

    public void update(Context c,User ct){
        List<User> l=comptes(c);int i=0;
        for(User cp:l){
            if(Objects.equals(cp.getUserId(), ct.getUserId())){
                l.remove(i);l.add(ct);
                MonFichier.ecrire(c,"LesConnectes",new LesConnectes(l).json());
                return;
            }
            i++;
        }
    }

}
