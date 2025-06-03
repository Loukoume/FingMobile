package com.credi.fings.entity;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fings.publics.AddActivity;
import com.credi.fings.R;
import com.credi.fings.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fings.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fings.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Request;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class LoanDate implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idLoanDate")
    private String idServeur;
    private LocalDate submittedOnDate;
    private String submittedByUsername;
    private String submittedByFirstname;
    private String submittedByLastname;
    private LocalDate approvedOnDate;
    private String approvedByUsername;
    private String approvedByFirstname;
    private String approvedByLastname;
    private LocalDate expectedDisbursementDate;
    private LocalDate actualDisbursementDate;
    private String disbursedByUsername;
    private String disbursedByFirstname;
    private String disbursedByLastname;
    private LocalDate expectedMaturityDate;

    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public String getIdServeur() {
        return this.idServeur;
    }


    public void setIdServeur(String idServeur) {
        this.idServeur = idServeur;
    }


    public LocalDate getSubmittedOnDate() {
        return this.submittedOnDate;
    }


    public void setSubmittedOnDate(LocalDate submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
    }


    public String getSubmittedByUsername() {
        return this.submittedByUsername;
    }


    public void setSubmittedByUsername(String submittedByUsername) {
        this.submittedByUsername = submittedByUsername;
    }


    public String getSubmittedByFirstname() {
        return this.submittedByFirstname;
    }


    public void setSubmittedByFirstname(String submittedByFirstname) {
        this.submittedByFirstname = submittedByFirstname;
    }


    public String getSubmittedByLastname() {
        return this.submittedByLastname;
    }


    public void setSubmittedByLastname(String submittedByLastname) {
        this.submittedByLastname = submittedByLastname;
    }


    public LocalDate getApprovedOnDate() {
        return this.approvedOnDate;
    }


    public void setApprovedOnDate(LocalDate approvedOnDate) {
        this.approvedOnDate = approvedOnDate;
    }


    public String getApprovedByUsername() {
        return this.approvedByUsername;
    }


    public void setApprovedByUsername(String approvedByUsername) {
        this.approvedByUsername = approvedByUsername;
    }


    public String getApprovedByFirstname() {
        return this.approvedByFirstname;
    }


    public void setApprovedByFirstname(String approvedByFirstname) {
        this.approvedByFirstname = approvedByFirstname;
    }


    public String getApprovedByLastname() {
        return this.approvedByLastname;
    }


    public void setApprovedByLastname(String approvedByLastname) {
        this.approvedByLastname = approvedByLastname;
    }


    public LocalDate getExpectedDisbursementDate() {
        return this.expectedDisbursementDate;
    }


    public void setExpectedDisbursementDate(LocalDate expectedDisbursementDate) {
        this.expectedDisbursementDate = expectedDisbursementDate;
    }


    public LocalDate getActualDisbursementDate() {
        return this.actualDisbursementDate;
    }


    public void setActualDisbursementDate(LocalDate actualDisbursementDate) {
        this.actualDisbursementDate = actualDisbursementDate;
    }


    public String getDisbursedByUsername() {
        return this.disbursedByUsername;
    }


    public void setDisbursedByUsername(String disbursedByUsername) {
        this.disbursedByUsername = disbursedByUsername;
    }


    public String getDisbursedByFirstname() {
        return this.disbursedByFirstname;
    }


    public void setDisbursedByFirstname(String disbursedByFirstname) {
        this.disbursedByFirstname = disbursedByFirstname;
    }


    public String getDisbursedByLastname() {
        return this.disbursedByLastname;
    }


    public void setDisbursedByLastname(String disbursedByLastname) {
        this.disbursedByLastname = disbursedByLastname;
    }


    public LocalDate getExpectedMaturityDate() {
        return this.expectedMaturityDate;
    }


    public void setExpectedMaturityDate(LocalDate expectedMaturityDate) {
        this.expectedMaturityDate = expectedMaturityDate;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public LoanDate fromJs(String js) {
        return new Gson().fromJson(js, LoanDate.class);
    }
}


