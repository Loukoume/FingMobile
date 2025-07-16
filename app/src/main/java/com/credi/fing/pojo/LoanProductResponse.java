package com.credi.fing.pojo;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class LoanProductResponse implements Serializable {

    private boolean isLoanProductLinkedToFloatingRate;
    private boolean isFloatingInterestRate;
    private List<ProductOption> productOptions;
    private boolean canDisburse;
    private boolean isTopup;
    private boolean isInterestRecalculationEnabled;
    private boolean isVariableInstallmentsAllowed;
    private boolean isEqualAmortization;
    private boolean isRatesEnabled;

    public LoanProductResponse() {
    }

    public boolean isLoanProductLinkedToFloatingRate() {
        return isLoanProductLinkedToFloatingRate;
    }

    public void setLoanProductLinkedToFloatingRate(boolean loanProductLinkedToFloatingRate) {
        isLoanProductLinkedToFloatingRate = loanProductLinkedToFloatingRate;
    }

    public boolean isFloatingInterestRate() {
        return isFloatingInterestRate;
    }

    public void setFloatingInterestRate(boolean floatingInterestRate) {
        isFloatingInterestRate = floatingInterestRate;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }

    public boolean isCanDisburse() {
        return canDisburse;
    }

    public void setCanDisburse(boolean canDisburse) {
        this.canDisburse = canDisburse;
    }

    public boolean isTopup() {
        return isTopup;
    }

    public void setTopup(boolean topup) {
        isTopup = topup;
    }

    public boolean isInterestRecalculationEnabled() {
        return isInterestRecalculationEnabled;
    }

    public void setInterestRecalculationEnabled(boolean interestRecalculationEnabled) {
        isInterestRecalculationEnabled = interestRecalculationEnabled;
    }

    public boolean isVariableInstallmentsAllowed() {
        return isVariableInstallmentsAllowed;
    }

    public void setVariableInstallmentsAllowed(boolean variableInstallmentsAllowed) {
        isVariableInstallmentsAllowed = variableInstallmentsAllowed;
    }

    public boolean isEqualAmortization() {
        return isEqualAmortization;
    }

    public void setEqualAmortization(boolean equalAmortization) {
        isEqualAmortization = equalAmortization;
    }

    public boolean isRatesEnabled() {
        return isRatesEnabled;
    }

    public void setRatesEnabled(boolean ratesEnabled) {
        isRatesEnabled = ratesEnabled;
    }
    public String js() {
        return new Gson().toJson(this);
    }

    public LoanProductResponse fromJs(String js) {
        return new Gson().fromJson(js, LoanProductResponse.class);
    }
}

