package com.credi.fing.pojo;

import com.credi.fing.entity.AccountType;
import com.credi.fing.entity.AccountTypeOption;
import com.google.gson.Gson;

import java.io.Serializable;

public class AccountInfo implements Serializable {

    private String locale="en_GB";
    private String name;
    private String accountNumber;
    private Integer accountType;
    private AccountTypeOption type;
    private Integer transferLimit;
    private String officeName;

    // Constructeur vide
    public AccountInfo() {
    }

    // Getters et Setters

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountTypeOption getType() {
        return type;
    }

    public void setType(AccountTypeOption type) {
        this.type = type;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(Integer transferLimit) {
        this.transferLimit = transferLimit;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String js(){
        return new Gson().toJson(this);
    }
}

