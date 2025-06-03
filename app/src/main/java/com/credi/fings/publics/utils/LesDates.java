package com.credi.fings.publics.utils;
import java.util.ArrayList;

public class LesDates {
    private int jours;
    private int mois;
    private int annee;

    public LesDates(int jours, int mois, int annee) {
        this.jours = jours;
        this.mois = mois;
        this.annee = annee;
    }
    public LesDates() {
    }

    public int getJours() {
        return jours;
    }

    public int getMois() {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setJours(int jours) {
        this.jours = jours;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String toString(){
        return this.jours+"/"+this.mois+"/"+this.annee;
    }

    public boolean est_divisible_par(double nbr,int n){
        return ((int)(nbr/n)-(nbr/n))==0;
    }

    public int nbr_jr(int m,int ann){
        int r=0;
        switch(m){
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                r=31;
                break;
            case 4:case 6:case 9:case 11:
                r=30;
                break;
            case 2:
                if(est_divisible_par(ann, 4)) r=29;else
                    r=28;
                break;
        }
        return r;
    }

    public int nbr_jr(int ann){
        int r;
        if(est_divisible_par(ann, 4)) r=366;else
            r=365;
        return r;
    }

    public int nombr_jour(LesDates d){
        if(this.annee==d.annee){
            if(this.mois==d.mois){
                return Math.abs(this.jours-d.jours);
            }else {
                int j;
                if(this.mois<d.mois){
                    j=(nbr_jr(this.mois,this.annee)-this.jours);
                    for(int k=this.mois+1;k<d.mois;k++){
                        j=j+nbr_jr(k,this.annee);
                    }
                    j=j+d.jours;
                }else {
                    j=(nbr_jr(d.mois,d.annee)-d.jours);
                    for(int k=d.mois+1;k<this.mois;k++){
                        j=j+nbr_jr(k,d.annee);
                    }
                    j=j+this.jours;
                }
                return j;
            }
        }else {
            LesDates d1=new LesDates(31,12,this.annee),d2=new LesDates(1,1,d.annee);
            int j=this.nombr_jour(d1);
            for(int k=this.annee+1;k<d.annee;k++){
                j=j+nbr_jr(k);
            }
            j=j+d2.nombr_jour(d);
            return j;
        }
    }

    public int nombr_moi(LesDates d){
        return (int)(this.nombr_jour(d)/30.5);
    }

    public LesDates mois_svant(){
        int m=this.mois,a=this.annee;
        if(m==12){

            m=1;a++;
        }else m++;
        return new LesDates(1,m,a);
    }
    public LesDates mois_avant(){
        int  m=this.mois,a=this.annee;
        if(m==1){
            m=12;a--;
        }else m--;
        return new LesDates(nbr_jr(m, a),m,a);
    }

    public LesDates mois_fin(){
        return new LesDates(nbr_jr(mois, annee),mois,annee);
    }

    public LesDates ajouter_jour(int jr){
        int j=this.jours,m=this.mois,a=this.annee,r;
        LesDates d=new LesDates(j, m, a);
        if(jr>0){
            r=nbr_jr(m, a)-j;
            while(jr>=r){
                jr=jr-r;
                if(jr==0) d=d.mois_fin();else
                    d= d.mois_svant();
                r=nbr_jr(d.getMois(),d.getAnnee());
            }
            if(jr==0) jr=d.getJours(); else jr=jr+d.jours;
            d=new LesDates(jr, d.getMois(), d.getAnnee());
        }else{
            r=j;jr=-jr;
            while(jr>=r){
                jr=jr-r;
                d= d.mois_avant();
                r=nbr_jr(d.getMois(),d.getAnnee());
            }
            d=new LesDates(r-jr, d.getMois(), d.getAnnee());
        }
        return d;
    }

    public ArrayList<ArrayList<String>> rempli_mois(int p_jr,int nb_jr){
        int k=nb_jr;
        ArrayList<String> sem=new ArrayList<>(); ArrayList<ArrayList<String>> rt=new ArrayList<>();
        for(int i=1;i<p_jr;i++)  sem.add("");int i=1,c=0;for(i=1;i<=7+1-p_jr;i++)  sem.add(i+"");rt.add(sem);
        nb_jr=nb_jr+p_jr-8;
        while(nb_jr!=0){
            sem=new ArrayList<>();
            if(nb_jr>=7){
                for(int j=0;j<7;j++){
                    sem.add(i+"");i++;nb_jr--;
                }
            }else{
                for(int j=i;j<=k;j++){
                    sem.add(i+"");i++;c++;
                }

                nb_jr=0;
            }
            rt.add(sem);
        }
        return rt;
    }
    public int j_svt(int j){
        int i=j+1;if(j==7) i=1;
        return i;
    }
    public int j_avt(int j){
        int i;
        if(j==1) i=7;else i=j-1;
        return i;
    }
    public int trouv_pr_jr(int annee){
        int na=annee-2000,bs=na/4,jr=7;
        if(na<0){
            na=-na;
            bs=-bs+1;
            for(int i=0;i<bs;i++){
                jr=j_avt(jr); jr=j_avt(jr);
            }
            na=na-bs;
            for(int i=0;i<na;i++){
                jr=j_avt(jr);
            }
        }else{
            for(int i=0;i<bs;i++){
                jr=j_svt(jr); jr=j_svt(jr);
            }
            na=na-bs;
            for(int i=0;i<na;i++){
                jr=j_svt(jr);
            }
            if((int)(annee/4.0)-annee/4.0==0) jr=j_avt(jr);
        }

        return jr;
    }

    public ArrayList<String> lmois(){
        ArrayList<String> lm=new ArrayList<>();
        lm.add("JANVIER");lm.add("FEVRIER");lm.add("MARS");lm.add("AVRIL");lm.add("MAI");lm.add("JUIN");
        lm.add("JUILLET");lm.add("AOUT");lm.add("SEPTEMBRE");lm.add("OCTOBRE");lm.add("NOVEMBRE");lm.add("DECEMBRE");
        return lm;
    }
    public ArrayList<String> ljrs(){
        ArrayList<String> lm=new ArrayList<>();
        lm.add("L");lm.add("M");lm.add("M");lm.add("J");lm.add("V");lm.add("S");  lm.add("D");
        return lm;
    }

    public ArrayList<ArrayList<ArrayList<String>>> rempli_annee(int annee){
        ArrayList<ArrayList<String>> mois=new ArrayList<>();
        ArrayList<String> ls=new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> ann=new ArrayList<>();
        int p=trouv_pr_jr(annee),i=1;
        ArrayList<String> lm=lmois();
        for(String m:lm){
            ls.add(m);mois.add(ls);
            mois.add(ljrs());ann.add(mois);
            mois=rempli_mois(p, nbr_jr(i, annee));
            ann.add(mois);
            p=j_svt(mois.get(mois.size()-1).size());
            mois=new ArrayList<>();ls=new ArrayList<>();
            i++;
        }
        return ann;
    }

    public String aff_anne(int annee){
        ArrayList<ArrayList<ArrayList<String>>> ann=rempli_annee(annee);
        String rt=" Calandrier de "+annee+"\n\n";
        for(ArrayList<ArrayList<String>> m:ann){
            for(ArrayList<String> s:m){
                for(String j:s){
                    rt=rt+" "+j;
                }
                rt=rt+"\n";
            }
            rt=rt+"\n\n";
        }
        return rt;
    }
}
