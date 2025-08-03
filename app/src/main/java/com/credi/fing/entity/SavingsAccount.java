package com.credi.fing.entity;

import com.google.gson.Gson;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.util.List;
import java.io.Serializable;

public class SavingsAccount implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idSavingsAccount")
    private String idServeur;
    private String accountNo;
    private Integer productId;
    private String productName;
    private String shortProductName;
    private Status status;
    private Currency currency;
    private BigDecimal accountBalance;
    private AccountType accountType;
    private SavingsDate savingsDate;
    private SubStatus subStatus;
    private List<Integer> lastActiveTransactionDate;
    private DepositType depositType;

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


    public Currency getCurrency() {
        return this.currency;
    }


    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }


    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }


    public AccountType getAccountType() {
        return this.accountType;
    }


    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }




    public SubStatus getSubStatus() {
        return this.subStatus;
    }


    public void setSubStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
    }





    public DepositType getDepositType() {
        return this.depositType;
    }


    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }


    public String js() {
        return new Gson().toJson(this);
    }

    public SavingsAccount fromJs(String js) {
        return new Gson().fromJson(js, SavingsAccount.class);
    }

    /**
     * Vérifie le statut d’un compte d’épargne et retourne :
     *   0 si le compte est clôturé,
     *  -1 si le compte n’est pas encore utilisable (en attente d’approbation),
     *   2 si le compte est bloqué,
     *   1 si tout est OK.
     */
    public int checkSavingsAccountStatus() {
        SavingsAccount savingsAccount=this;
        if (savingsAccount == null || savingsAccount.getStatus() == null || savingsAccount.getSubStatus() == null) {
            throw new IllegalArgumentException("Le SavingsAccount, son status et son subStatus ne doivent pas être null");
        }

        Status status     = savingsAccount.getStatus();
        SubStatus subStatus = savingsAccount.getSubStatus();

        // 0 : compte clôturé ou inactif
        if (!status.getActive()
                || status.getClosed()
                || status.getPrematureClosed()
                || status.getRejected()
        ) {
            return 0;
        }

        // -1 : compte pas encore utilisable (en attente d’approbation)
        if (status.getSubmittedAndPendingApproval()) {
            return -1;
        }

        // 2 : compte bloqué
        if (subStatus.getBlock()
                || subStatus.getBlockDebit()
                || subStatus.getBlockCredit()
                || status.getTransferOnHold()
                || status.getTransferInProgress()
        ) {
            return 2;
        }

        // 1 : tout est OK
        return 1;
    }

}


