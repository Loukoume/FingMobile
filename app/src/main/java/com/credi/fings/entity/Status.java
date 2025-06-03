package com.credi.fings.entity;

import com.google.gson.Gson;
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

public class Status implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idStatus")
    private String idServeur;
    private String code;
    private String value;
    private Boolean pendingApproval;
    private Boolean waitingForDisbursal;
    private Boolean active;
    private Boolean closedObligationsMet;
    private Boolean closedWrittenOff;
    private Boolean closedRescheduled;
    private Boolean closed;
    private Boolean overpaid;
    private Boolean submittedAndPendingApproval;
    private Boolean approved;
    private Boolean rejected;
    private Boolean withdrawnByApplicant;
    private Boolean prematureClosed;
    private Boolean transferInProgress;
    private Boolean transferOnHold;
    private Boolean matured;

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


    public String getCode() {
        return this.code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getValue() {
        return this.value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public Boolean getPendingApproval() {
        return this.pendingApproval;
    }


    public void setPendingApproval(Boolean pendingApproval) {
        this.pendingApproval = pendingApproval;
    }


    public Boolean getWaitingForDisbursal() {
        return this.waitingForDisbursal;
    }


    public void setWaitingForDisbursal(Boolean waitingForDisbursal) {
        this.waitingForDisbursal = waitingForDisbursal;
    }


    public Boolean getActive() {
        return this.active;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }


    public Boolean getClosedObligationsMet() {
        return this.closedObligationsMet;
    }


    public void setClosedObligationsMet(Boolean closedObligationsMet) {
        this.closedObligationsMet = closedObligationsMet;
    }


    public Boolean getClosedWrittenOff() {
        return this.closedWrittenOff;
    }


    public void setClosedWrittenOff(Boolean closedWrittenOff) {
        this.closedWrittenOff = closedWrittenOff;
    }


    public Boolean getClosedRescheduled() {
        return this.closedRescheduled;
    }


    public void setClosedRescheduled(Boolean closedRescheduled) {
        this.closedRescheduled = closedRescheduled;
    }


    public Boolean getClosed() {
        return this.closed;
    }


    public void setClosed(Boolean closed) {
        this.closed = closed;
    }


    public Boolean getOverpaid() {
        return this.overpaid;
    }


    public void setOverpaid(Boolean overpaid) {
        this.overpaid = overpaid;
    }


    public Boolean getSubmittedAndPendingApproval() {
        return this.submittedAndPendingApproval;
    }


    public void setSubmittedAndPendingApproval(Boolean submittedAndPendingApproval) {
        this.submittedAndPendingApproval = submittedAndPendingApproval;
    }


    public Boolean getApproved() {
        return this.approved;
    }


    public void setApproved(Boolean approved) {
        this.approved = approved;
    }


    public Boolean getRejected() {
        return this.rejected;
    }


    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }


    public Boolean getWithdrawnByApplicant() {
        return this.withdrawnByApplicant;
    }


    public void setWithdrawnByApplicant(Boolean withdrawnByApplicant) {
        this.withdrawnByApplicant = withdrawnByApplicant;
    }


    public Boolean getPrematureClosed() {
        return this.prematureClosed;
    }


    public void setPrematureClosed(Boolean prematureClosed) {
        this.prematureClosed = prematureClosed;
    }


    public Boolean getTransferInProgress() {
        return this.transferInProgress;
    }


    public void setTransferInProgress(Boolean transferInProgress) {
        this.transferInProgress = transferInProgress;
    }


    public Boolean getTransferOnHold() {
        return this.transferOnHold;
    }


    public void setTransferOnHold(Boolean transferOnHold) {
        this.transferOnHold = transferOnHold;
    }


    public Boolean getMatured() {
        return this.matured;
    }


    public void setMatured(Boolean matured) {
        this.matured = matured;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public Status fromJs(String js) {
        return new Gson().fromJson(js, Status.class);
    }
}


