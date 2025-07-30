package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransferPayload implements Serializable {
    @SerializedName("fromOfficeId")
    Integer fromOfficeId;

    @SerializedName("fromClientId")
    Long fromClientId;

    @SerializedName("fromAccountType")
    Integer fromAccountType;

    @SerializedName("fromAccountId")
    Integer fromAccountId;

    @SerializedName("toOfficeId")
    Integer toOfficeId;

    @SerializedName("toClientId")
    Long toClientId;

    @SerializedName("toAccountType")
    Integer toAccountType;

    @SerializedName("toAccountId")
    Integer toAccountId;

    @SerializedName("transferDate")
    String transferDate;

    @SerializedName("transferAmount")
    Double transferAmount;

    @SerializedName("transferDescription")
    String transferDescription;

    String dateFormat = "dd MMMM yyyy";
    String locale = "en";

    public Integer getFromOfficeId() {
        return fromOfficeId;
    }

    public void setFromOfficeId(Integer fromOfficeId) {
        this.fromOfficeId = fromOfficeId;
    }

    public Long getFromClientId() {
        return fromClientId;
    }

    public void setFromClientId(Long fromClientId) {
        this.fromClientId = fromClientId;
    }

    public Integer getFromAccountType() {
        return fromAccountType;
    }

    public void setFromAccountType(Integer fromAccountType) {
        this.fromAccountType = fromAccountType;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToOfficeId() {
        return toOfficeId;
    }

    public void setToOfficeId(Integer toOfficeId) {
        this.toOfficeId = toOfficeId;
    }

    public Long getToClientId() {
        return toClientId;
    }

    public void setToClientId(Long toClientId) {
        this.toClientId = toClientId;
    }

    public Integer getToAccountType() {
        return toAccountType;
    }

    public void setToAccountType(Integer toAccountType) {
        this.toAccountType = toAccountType;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String js(){
        return new Gson().toJson(this);
    }
}
