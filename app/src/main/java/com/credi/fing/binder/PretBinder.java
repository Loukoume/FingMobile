package com.credi.fing.binder;

import com.credi.fing.R;
import com.credi.fing.activity.RevuPretActivity;
import com.credi.fing.entity.Pret;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fing.publics.adapters.generiqueAdapter.Binder;
import com.credi.fing.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fing.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fing.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fing.publics.service.BinderInterface;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.EditeObject;
import com.credi.fing.publics.service.impl.Request;
import com.credi.fing.publics.service.pojo.NavigateObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class PretBinder implements Serializable, BinderInterface {
    @Override
    public Binder binder() {
        Binder binder = new Binder("null", "")
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
                new Attribut("Numéro du compte", "idServeur","object", true)
                        .setValues("000000002","000000001"),
                new Attribut("Produit de crédit", "idLoanType","object", true)
                        .setValues("Prêt conso","Prêt immobilier")
                        .setDefaul("Prêt conso"),
                new Attribut("Objet du prêt", "objet","object", true)
                        .setValues("Travaux","Construction")
                        .setDefaul("Travaux"),
                new Attribut("Le montant principal", "montant","number", true),
                new Attribut("Monnaie", "idSavingsTimeline", false),
                new Attribut("Date limite de soumission", "","date|dd MM yyyy", false),
                new Attribut("Date de paiement attendue", "","date|dd MM yyyy", false)

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

    public EditeObject editeObject() {
        EditeObject editeObject = new EditeObject();
        editeObject.setObject(new Pret());
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Demander un prêt".toUpperCase()));
        editeObject.setButtonLabel("REVUE");
        editeObject.setNavigateClass(RevuPretActivity.class);
        editeObject.setaClass(Pret.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }

    public NavigateObject navigateObject() {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(Pret.class)
                .setTitle("Pret".toUpperCase())
                .setEndPointSave("pret")
                .setDataUrl("pret/all")
                .setPostData(new Pret())
                .setAddButton(true)
                .setEditeObject(editeObject());

        return navigateObject;
    }

    public EditeObject editeObject(Object object) {
        EditeObject editeObject = new EditeObject();
        editeObject.setObject(object);
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Ajouter ").toUpperCase());
        editeObject.setaClass(Pret.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }

    public NavigateObject navigateObject(Object object, EditeObject editeObject) {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(Pret.class)
                .setTitle("Pret".toUpperCase())
                .setEndPointSave("pret")
                .setDataUrl("pret/all")
                .setPostData(object)
                .setAddButton(true)
                .setEditeObject(editeObject);

        return navigateObject;
    }
}


