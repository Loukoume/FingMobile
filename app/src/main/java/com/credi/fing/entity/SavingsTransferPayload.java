package com.credi.fing.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class SavingsTransferPayload implements Serializable {
    private String locale;
    private String dateFormat;
    private String transferDate;
    private Long clientId;
    private BigDecimal transferAmount;
    private Long toSavingsAccountId;
    private String transferDescription;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getToSavingsAccountId() {
        return toSavingsAccountId;
    }

    public void setToSavingsAccountId(Long toSavingsAccountId) {
        this.toSavingsAccountId = toSavingsAccountId;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
    }
}
