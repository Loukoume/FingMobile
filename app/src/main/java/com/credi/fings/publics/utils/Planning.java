package com.credi.fings.publics.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Planning {
    private int id;
    private String date;
    private String libelle;
    private String heur_debu;
    private String heur_fin;
    private String description;
    private String matiere;
    private List<Integer> exos;

    public String getLibelle() {
        return libelle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Planning copier(){
        Planning p=new Planning();
        p.setHeur_fin(this.getHeur_fin());
        p.setHeur_debu(this.getHeur_debu());
        p.setId(this.getId());
        p.setDescription(this.getDescription());
        p.setMatiere(this.getMatiere());
        p.setExos(this.getExos());
        p.setLibelle(this.getLibelle());
        p.setDate(this.getDate());
        return p;
    }

    public Planning() {
        this.matiere="Aucun programme";
        this.libelle="";
    }

    public Planning(String libelle) {
        this.libelle = libelle;
    }

    public boolean vide(){
        boolean oui=eq(this.getHeur_fin(),this.getHeur_debu());
        return this.getMatiere().equals("Aucun programme")||oui;
    }

    public boolean valide(){
        return petit(this.getHeur_debu(),this.getHeur_fin());
    }

    public boolean passe(Context c){
        String a= MonFichier.lire(c,"date"),b;
        SimpleDateFormat sp=new SimpleDateFormat("dd MM yyyy");
        Date date=null,auj=new Date();b=sp.format(auj);
        try {
             date=sp.parse(a);
             auj=sp.parse(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date!=null&&!a.equals(b)){

            if(date.before(auj)) return true;
        }
        if(a.equals(b)){
            String h=S.heur();
            if(eq(this.getHeur_fin(),h)) return true;
            return petit(this.getHeur_fin(),h);
        }
        return false;
    }
    public boolean aujourd(Context c){
        String a= MonFichier.lire(c,"date"),b;
        SimpleDateFormat sp=new SimpleDateFormat("dd MM yyyy");
        Date auj=new Date();
        b=sp.format(auj);
        List<String> l=new Primitive().coupen_en(a," "),ll=new Primitive().coupen_en(b," ");
        for(int i=0;i<l.size();i++){
            if(Integer.parseInt(l.get(i))!=Integer.parseInt(ll.get(i)))return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getHeur_debu() {
        return heur_debu;
    }

    public void setHeur_debu(String heur_debu) {
        this.heur_debu = heur_debu;
    }

    public String getHeur_fin() {
        return heur_fin;
    }

    public void setHeur_fin(String heur_fin) {
        this.heur_fin = heur_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public List<Integer> getExos() {
        return exos;
    }

    public void setExos(List<Integer> exos) {
        this.exos = exos;
    }

    public String toGson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

    public Planning enPlanning(String s){
        Gson gson=new Gson();
        return gson.fromJson(s,Planning.class);
    }

    public int duree_min(){
        String a=heur_debu,b=heur_fin,r;
        List<String> l1=h_m(a),l2=h_m(b);
        int x1=Integer.parseInt(l1.get(0)),x2=Integer.parseInt(l1.get(1))
                ,y1=Integer.parseInt(l2.get(0)),y2=Integer.parseInt(l2.get(1)),m;
        if(x1==y1)return y2-x2;
       return  60-x2+(y1-x1-1)*60+y2;
    }
    public int duree_s(){
        String a=heur_debu,b=heur_fin,r;
        List<String> l1=h_m(a),l2=h_m(b);
        int x1=Integer.parseInt(l1.get(0)),x2=Integer.parseInt(l1.get(1))
                ,y1=Integer.parseInt(l2.get(0)),y2=Integer.parseInt(l2.get(1)),m;
        if(x1==y1)return y2-x2;
        return  60-x2+(y1-x1-1)*60+y2;
    }

    public String duree(){
       int p=this.duree_min();
       if(p<60) return p+"min";
       String h=(p/60)+"h "+(p%60);
        return  h;
    }

    public List<String> h_m(String s){
        Primitive p=new Primitive();
       // if(s.contains(":")) s=p.viderEspace(s);
        List<String> l=p.coupen_en(s,":");
        if(l.size()==1)l.add("0");
        return l;
    }

    public boolean petit(String s1,String s2){
        List<String> l1=h_m(s1),l2=h_m(s2);
        if(l1.isEmpty()||l2.isEmpty())return false;
         if(Integer.parseInt(l1.get(0))==Integer.parseInt(l2.get(0))){
            return Integer.parseInt(l1.get(1))<Integer.parseInt(l2.get(1));
        }
        return Integer.parseInt(l1.get(0))<Integer.parseInt(l2.get(0));
    }

    public boolean eq(String s1,String s2){
        List<String> l1=h_m(s1),l2=h_m(s2);
        if(l1.isEmpty()||l2.isEmpty())return false;
        if(Integer.parseInt(l1.get(0))==Integer.parseInt(l2.get(0))){
            return Integer.parseInt(l1.get(1))==Integer.parseInt(l2.get(1));
        }
        return false;
    }

    public boolean contient(String h){
      return petit(this.getHeur_debu(),h)&&petit(h,this.heur_fin)||eq(this.getHeur_debu(),h);
    }

    public String reste(){
        int b,r;
        String h= S.heurs();
        Planning p=this.copier();
        p.setHeur_debu(h);b=p.duree_min();
        r=b;
      //  Systeme.out.println(a+"-----"+b+"  h== "+h);
        if(r<60)return r+" mn";
       return ""+(r/60)+"h "+(r%60);
    }

    public boolean avant(Planning p){
        return petit(this.getHeur_debu(),p.getHeur_debu());
    }

    public static List<Planning> filter(List<Planning> l){
        List<Planning> r=new ArrayList<>(),rt=new ArrayList<>(),au;
        for(Planning p:l){
            if(p.duree_min()>0){
              r.add(p);
            }
        }
        Planning a;
      while (!r.isEmpty()){
         a=r.get(0);au=new ArrayList<>();
        boolean oui=true;int i=1,n=r.size();
        while (oui&&i<n){
            oui=r.get(i).getMatiere().equalsIgnoreCase(a.getMatiere())&&
                    r.get(i).getLibelle().equalsIgnoreCase(a.getLibelle()) ;
            if(oui) i++;
        }
        for(int j=0;j<i;j++) au.add(r.get(j));
        if(au.size()==1)rt.add(a);
        else {
            Planning pp=a.copier();
            pp.setHeur_fin(au.get(i-1).getHeur_fin());
            rt.add(pp);
        }
        r.removeAll(au);
      }
      return rt;
    }

}
