package com.credi.fing.pojo.transaction;

// TransactionType.java
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TransactionType implements Serializable {
    private int id;
    private String code;
    private String value;
    private boolean deposit;
    private boolean dividendPayout;
    private boolean withdrawal;
    private boolean interestPosting;
    private boolean feeDeduction;
    private boolean initiateTransfer;
    private boolean approveTransfer;
    private boolean withdrawTransfer;
    private boolean rejectTransfer;
    private boolean overdraftInterest;
    private boolean writtenoff;
    private boolean overdraftFee;
    private boolean withholdTax;
    private boolean escheat;
    private boolean amountHold;
    private boolean amountRelease;

    public TransactionType() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public boolean isDividendPayout() {
        return dividendPayout;
    }

    public void setDividendPayout(boolean dividendPayout) {
        this.dividendPayout = dividendPayout;
    }

    public boolean isWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    public boolean isInterestPosting() {
        return interestPosting;
    }

    public void setInterestPosting(boolean interestPosting) {
        this.interestPosting = interestPosting;
    }

    public boolean isFeeDeduction() {
        return feeDeduction;
    }

    public void setFeeDeduction(boolean feeDeduction) {
        this.feeDeduction = feeDeduction;
    }

    public boolean isInitiateTransfer() {
        return initiateTransfer;
    }

    public void setInitiateTransfer(boolean initiateTransfer) {
        this.initiateTransfer = initiateTransfer;
    }

    public boolean isApproveTransfer() {
        return approveTransfer;
    }

    public void setApproveTransfer(boolean approveTransfer) {
        this.approveTransfer = approveTransfer;
    }

    public boolean isWithdrawTransfer() {
        return withdrawTransfer;
    }

    public void setWithdrawTransfer(boolean withdrawTransfer) {
        this.withdrawTransfer = withdrawTransfer;
    }

    public boolean isRejectTransfer() {
        return rejectTransfer;
    }

    public void setRejectTransfer(boolean rejectTransfer) {
        this.rejectTransfer = rejectTransfer;
    }

    public boolean isOverdraftInterest() {
        return overdraftInterest;
    }

    public void setOverdraftInterest(boolean overdraftInterest) {
        this.overdraftInterest = overdraftInterest;
    }

    public boolean isWrittenoff() {
        return writtenoff;
    }

    public void setWrittenoff(boolean writtenoff) {
        this.writtenoff = writtenoff;
    }

    public boolean isOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(boolean overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public boolean isWithholdTax() {
        return withholdTax;
    }

    public void setWithholdTax(boolean withholdTax) {
        this.withholdTax = withholdTax;
    }

    public boolean isEscheat() {
        return escheat;
    }

    public void setEscheat(boolean escheat) {
        this.escheat = escheat;
    }

    public boolean isAmountHold() {
        return amountHold;
    }

    public void setAmountHold(boolean amountHold) {
        this.amountHold = amountHold;
    }

    public boolean isAmountRelease() {
        return amountRelease;
    }

    public void setAmountRelease(boolean amountRelease) {
        this.amountRelease = amountRelease;
    }
}

