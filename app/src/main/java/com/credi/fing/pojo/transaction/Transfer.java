package com.credi.fing.pojo.transaction;

// Transfer.java
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Transfer implements Serializable {
    private int id;
    private boolean reversed;
    private Currency currency;
    private double transferAmount;
    private List<Integer> transferDate;    // [YYYY, M, D]
    private String transferDescription;

    public Transfer() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public List<Integer> getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(List<Integer> transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
    }
}

