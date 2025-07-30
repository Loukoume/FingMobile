package com.credi.fing.pojo.transaction;

// Transfer.java
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Transfer implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("reversed")
    private boolean reversed;

    @SerializedName("currency")
    private Currency currency;

    @SerializedName("transferAmount")
    private Double transferAmount;

    @SerializedName("transferDate")
    private List<Integer> transferDate;

    @SerializedName("transferDescription")
    private String transferDescription;

    public Transfer() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

