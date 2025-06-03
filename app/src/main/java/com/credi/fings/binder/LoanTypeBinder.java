package com.credi.fings.binder;

import com.credi.fings.R;
import com.credi.fings.entity.LoanType;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fings.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fings.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.Request;
import com.credi.fings.publics.service.pojo.NavigateObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class LoanTypeBinder implements Serializable, BinderInterface {
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
                new Attribut("Code", "code", true),
                new Attribut("Value", "value", true)
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
        editeObject.setObject(new LoanType());
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Ajouter " + this.getClass().getSimpleName()).toUpperCase());
        editeObject.setaClass(LoanType.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }

    public NavigateObject navigateObject() {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(LoanType.class)
                .setTitle("LoanType".toUpperCase())
                .setEndPointSave("loan_type")
                .setDataUrl("loan_type/all")
                .setPostData(new LoanType())
                .setAddButton(true)
                .setEditeObject(editeObject());

        return navigateObject;
    }

    public EditeObject editeObject(Object object) {
        EditeObject editeObject = new EditeObject();
        editeObject.setObject(object);
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Ajouter ").toUpperCase());
        editeObject.setaClass(LoanType.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }

    public NavigateObject navigateObject(Object object, EditeObject editeObject) {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(LoanType.class)
                .setTitle("LoanType".toUpperCase())
                .setEndPointSave("loan_type")
                .setDataUrl("loan_type/all")
                .setPostData(object)
                .setAddButton(true)
                .setEditeObject(editeObject);

        return navigateObject;
    }
}


