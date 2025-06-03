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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class LoanAccount implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idLoanAccount")
    private String idServeur;
    private String accountNo;
    private Integer productId;
    private String productName;
    private String shortProductName;
    private Status status;
    private LoanType loanType;
    private Integer loanCycle;
    private LoanDate Date;
    private Boolean inArrears;
    private BigDecimal originalLoan;
    private BigDecimal loanBalance;

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


    public String getAccountNo() {
        return this.accountNo;
    }


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public Integer getProductId() {
        return this.productId;
    }


    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return this.productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getShortProductName() {
        return this.shortProductName;
    }


    public void setShortProductName(String shortProductName) {
        this.shortProductName = shortProductName;
    }


    public Status getStatus() {
        return this.status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public LoanType getLoanType() {
        return this.loanType;
    }


    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }


    public Integer getLoanCycle() {
        return this.loanCycle;
    }


    public void setLoanCycle(Integer loanCycle) {
        this.loanCycle = loanCycle;
    }


    public LoanDate getDate() {
        return this.Date;
    }


    public void setDate(LoanDate Date) {
        this.Date = Date;
    }


    public Boolean getInArrears() {
        return this.inArrears;
    }


    public void setInArrears(Boolean inArrears) {
        this.inArrears = inArrears;
    }


    public BigDecimal getOriginalLoan() {
        return this.originalLoan;
    }


    public void setOriginalLoan(BigDecimal originalLoan) {
        this.originalLoan = originalLoan;
    }


    public BigDecimal getLoanBalance() {
        return this.loanBalance;
    }


    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public LoanAccount fromJs(String js) {
        return new Gson().fromJson(js, LoanAccount.class);
    }
}


