package com.credi.fing.entity;

import android.os.Parcelable;

import com.credi.fing.publics.utils.S;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Beneficiary implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("officeName")
    private String officeName;

    @SerializedName("clientName")
    private String clientName;

    @SerializedName("accountType")
    private AccountTypeOption accountType;

    @SerializedName("accountNumber")
    private String accountNumber;

    @SerializedName("transferLimit")
    private Double transferLimit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public AccountTypeOption getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeOption accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(Double transferLimit) {
        this.transferLimit = transferLimit;
    }

    public static Beneficiary generate(){
        Beneficiary beneficiary=new Beneficiary();
        beneficiary.setClientName("Nom client");
        beneficiary.setAccountNumber("0214501278");
         return beneficiary;
    }

    public Beneficiary fromJs(String js){
        return new Gson().fromJson(js,Beneficiary.class);
    }
    public String js(){
        return new Gson().toJson(this);
    }
}
