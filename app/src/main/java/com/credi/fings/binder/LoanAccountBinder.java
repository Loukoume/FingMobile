package com.credi.fings.binder;

import com.credi.fings.R;
import com.credi.fings.activity.PagerActivity;
import com.credi.fings.activity.RevuPretActivity;
import com.credi.fings.entity.LoanAccount;
import com.credi.fings.pojo.LoanPojo;
import com.credi.fings.pojo.LoanProductResponse;
import com.credi.fings.pojo.ProductOption;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoanAccountBinder implements Serializable, BinderInterface {
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
        return Collections.emptyList();
    }


    public List<Attribut> setAttributs(LoanProductResponse loanProductResponse,
                                       List<String> comptes) {
        List<ProductOption> options=loanProductResponse.getProductOptions();
        if(options==null)options=new ArrayList<>();
        List<Attribut> attributs = Arrays.asList(
                new Attribut("Produit de crédit", "productOption","object", false)
                        .setValuess(options.stream().map(ss->(ProductOption) ss).collect(Collectors.toList())).setLabel("name"),
                 new Attribut("Montant principal", "principal","number", true),
                new Attribut("Date limite de soumission", "submittedOnDate","dateString|dd MMMM yyyy", false),
                new Attribut("Date de paiement attendue", "expectedDisbursementDate","dateString|dd MMMM yyyy", false)
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

    public EditeObject editeObject(LoanProductResponse loanProductResponse,
                                   List<String> comptes) {
        EditeObject editeObject = new EditeObject();
        editeObject.setObject(new LoanPojo());
        editeObject.setAttribute(setAttributs(loanProductResponse,comptes));
        editeObject.setDesignation(("Demander un prêt").toUpperCase());
        editeObject.setaClass(LoanPojo.class);
        editeObject.setNavigateClass(RevuPretActivity.class);
        editeObject.setFinish(false);
        editeObject.setButtonLabel("REVUE");
        return editeObject;
    }

    public NavigateObject navigateObject(LoanProductResponse loanProductResponse,
                                         List<String> comptes) {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(LoanPojo.class)
                .setTitle("LoanAccount".toUpperCase())
                .setEndPointSave("loan_account")
                .setDataUrl("loan_account/all")
                .setPostData(new LoanPojo())
                .setAddButton(true)
                .setEditeObject(editeObject(loanProductResponse,comptes));

        return navigateObject;
    }

  /*  public EditeObject editeObject(Object object) {
        EditeObject editeObject = new EditeObject();
        editeObject.setObject(object);
        editeObject.setAttribute(setAttributs());
        editeObject.setDesignation(("Ajouter ").toUpperCase());
        editeObject.setaClass(LoanAccount.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }*/

    public NavigateObject navigateObject(Object object, EditeObject editeObject) {
        NavigateObject navigateObject = new NavigateObject()
                .setBinder(binder())
                .setaClass(LoanAccount.class)
                .setTitle("LoanAccount".toUpperCase())
                .setEndPointSave("loan_account")
                .setDataUrl("loan_account/all")
                .setPostData(object)
                .setAddButton(true)
                .setEditeObject(editeObject);

        return navigateObject;
    }
}


