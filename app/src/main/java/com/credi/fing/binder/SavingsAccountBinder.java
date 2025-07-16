package com.credi.fing.binder;

import com.credi.fing.R;
import com.credi.fing.entity.SavingsAccount;
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

public class SavingsAccountBinder implements Serializable, BinderInterface {
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
                new Attribut("Accountno", "accountNo", true),
                new Attribut("Productid", "productId", true),
                new Attribut("Productname", "productName", true),
                new Attribut("Shortproductname", "shortProductName", true),
                new Attribut("Status", "status", true),
                new Attribut("Currency", "currency", true),
                new Attribut("Accountbalance", "accountBalance", true),
                new Attribut("Accounttype", "accountType", true),
                new Attribut("Date", "Date", true),
                new Attribut("Substatus", "subStatus", true),
                new Attribut("Lastactivetransactiondate", "lastActiveTransactionDate", true),
                new Attribut("Deposittype", "depositType", true)
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
        editeObject.setObject(new SavingsAccount());
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Ajouter " + this.getClass().getSimpleName()).toUpperCase());
        editeObject.setaClass(SavingsAccount.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }

    public NavigateObject navigateObject() {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(SavingsAccount.class)
                .setTitle("SavingsAccount".toUpperCase())
                .setEndPointSave("savings_account")
                .setDataUrl("savings_account/all")
                .setPostData(new SavingsAccount())
                .setAddButton(true)
                .setEditeObject(editeObject());

        return navigateObject;
    }

    public EditeObject editeObject(Object object) {
        EditeObject editeObject = new EditeObject();
        editeObject.setObject(object);
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Ajouter ").toUpperCase());
        editeObject.setaClass(SavingsAccount.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }

    public NavigateObject navigateObject(Object object, EditeObject editeObject) {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(SavingsAccount.class)
                .setTitle("SavingsAccount".toUpperCase())
                .setEndPointSave("savings_account")
                .setDataUrl("savings_account/all")
                .setPostData(object)
                .setAddButton(true)
                .setEditeObject(editeObject);

        return navigateObject;
    }
}


