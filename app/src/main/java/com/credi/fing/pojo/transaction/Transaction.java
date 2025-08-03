package com.credi.fing.pojo.transaction;

// Transaction.java
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {
    private int id;
    private TransactionType transactionType;
    private int accountId;
    private String accountNo;
    private List<Integer> date;              // [YYYY, M, D]
    private Currency currency;
    private double amount;
    private double runningBalance;
    private boolean reversed;
    private Transfer transfer;              // peut être null pour certains types
    private List<Integer> submittedOnDate;   // [YYYY, M, D]
    private boolean interestedPostedAsOn;
    private String submittedByUsername;

    public Transaction() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setRunningBalance(double runningBalance) {
        this.runningBalance = runningBalance;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

