package com.credi.fing.pojo.transaction;

// Transaction.java
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("transactionType")
    private TransactionType transactionType;

    @SerializedName("accountId")
    private Long accountId;

    @SerializedName("accountNo")
    private String accountNo;

    /** format [year, month, day] **/
    @SerializedName("date")
    private List<Integer> date;

    @SerializedName("currency")
    private Currency currency;

    @SerializedName("amount")
    private Double amount;

    @SerializedName("runningBalance")
    private Double runningBalance;

    @SerializedName("reversed")
    private boolean reversed;

    @SerializedName("transfer")
    private Transfer transfer;

    /** format [year, month, day] **/
    @SerializedName("submittedOnDate")
    private List<Integer> submittedOnDate;

    @SerializedName("interestedPostedAsOn")
    private boolean interestedPostedAsOn;

    @SerializedName("submittedByUsername")
    private String submittedByUsername;

    public Transaction() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public List<Integer> getDate() {
        return date;
    }

    public void setDate(List<Integer> date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(Double runningBalance) {
        this.runningBalance = runningBalance;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public List<Integer> getSubmittedOnDate() {
        return submittedOnDate;
    }

    public void setSubmittedOnDate(List<Integer> submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
    }

    public boolean isInterestedPostedAsOn() {
        return interestedPostedAsOn;
    }

    public void setInterestedPostedAsOn(boolean interestedPostedAsOn) {
        this.interestedPostedAsOn = interestedPostedAsOn;
    }

    public String getSubmittedByUsername() {
        return submittedByUsername;
    }

    public void setSubmittedByUsername(String submittedByUsername) {
        this.submittedByUsername = submittedByUsername;
    }
}

