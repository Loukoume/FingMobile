package com.credi.fings.publics.utils;

public class M {
    private String tb[];

    public M(String[] tb) {
        this.tb=tb;
    }

    public String[] getTb() {
        return tb;
    }

    public void setTb(String[] tb) {
        this.tb = tb;
    }

    public M remov(int n){
        if(n>=this.tb.length)return this;
        String tb[]=new String[this.tb.length-1];
        int j=0;
        for(int i=0;i<this.tb.length;i++){
           if(i!=n){
               tb[j]=this.tb[i];j++;
           }
        }
        return new M(tb);
    }
}

