package com.credi.fings.publics.service;

import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Request;

import java.util.ArrayList;
import java.util.List;

public interface BinderInterface {
    Binder binder();
     List<Attribut> setAttribut();
     List<Attribut> setAttribut(List<Object> values);

    default List<Attribut> setAttribut(List<Object>... values) {
        // Implémentation par défaut
        return new ArrayList<>();
    }
    default void setRequest(String nam, Request request) {
       List<Attribut> attributs=setAttribut();
       for (Attribut at:attributs){
           if(at.getName().equals(nam)||at.getColonne().equals(nam)){
               at.setRequest(request);
               break;
           }
       }
    }

    default List<Attribut> setAttribut(List<Object> values,List<Attribut>  attributs, String field,String label) {
        return attributs;
    }
}
