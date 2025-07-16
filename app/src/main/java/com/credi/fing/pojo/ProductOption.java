package com.credi.fing.pojo;

import java.io.Serializable;
import java.util.List;

public class ProductOption implements Serializable {

    private int id;
    private String name;
    private boolean includeInBorrowerCycle;
    private boolean useBorrowerCycle;
    private boolean isLinkedToFloatingInterestRates;
    private boolean isFloatingInterestRateCalculationAllowed;
    private boolean allowVariableInstallments;
    private boolean isInterestRecalculationEnabled;
    private boolean canDefineInstallmentAmount;
    private List<Object> principalVariationsForBorrowerCycle;
    private List<Object> interestRateVariationsForBorrowerCycle;
    private List<Object> numberOfRepaymentVariationsForBorrowerCycle;
    private boolean canUseForTopup;
    private boolean isRatesEnabled;
    private boolean multiDisburseLoan;
    private boolean holdGuaranteeFunds;
    private boolean accountMovesOutOfNPAOnlyOnArrearsCompletion;
    private boolean syncExpectedWithDisbursementDate;
    private boolean isEqualAmortization;

    public ProductOption() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncludeInBorrowerCycle() {
        return includeInBorrowerCycle;
    }

    public void setIncludeInBorrowerCycle(boolean includeInBorrowerCycle) {
        this.includeInBorrowerCycle = includeInBorrowerCycle;
    }

    public boolean isUseBorrowerCycle() {
        return useBorrowerCycle;
    }

    public void setUseBorrowerCycle(boolean useBorrowerCycle) {
        this.useBorrowerCycle = useBorrowerCycle;
    }

    public boolean isLinkedToFloatingInterestRates() {
        return isLinkedToFloatingInterestRates;
    }

    public void setLinkedToFloatingInterestRates(boolean linkedToFloatingInterestRates) {
        isLinkedToFloatingInterestRates = linkedToFloatingInterestRates;
    }

    public boolean isFloatingInterestRateCalculationAllowed() {
        return isFloatingInterestRateCalculationAllowed;
    }

    public void setFloatingInterestRateCalculationAllowed(boolean floatingInterestRateCalculationAllowed) {
        isFloatingInterestRateCalculationAllowed = floatingInterestRateCalculationAllowed;
    }

    public boolean isAllowVariableInstallments() {
        return allowVariableInstallments;
    }

    public void setAllowVariableInstallments(boolean allowVariableInstallments) {
        this.allowVariableInstallments = allowVariableInstallments;
    }

    public boolean isInterestRecalculationEnabled() {
        return isInterestRecalculationEnabled;
    }

    public void setInterestRecalculationEnabled(boolean interestRecalculationEnabled) {
        isInterestRecalculationEnabled = interestRecalculationEnabled;
    }

    public boolean isCanDefineInstallmentAmount() {
        return canDefineInstallmentAmount;
    }

    public void setCanDefineInstallmentAmount(boolean canDefineInstallmentAmount) {
        this.canDefineInstallmentAmount = canDefineInstallmentAmount;
    }

    public List<Object> getPrincipalVariationsForBorrowerCycle() {
        return principalVariationsForBorrowerCycle;
    }

    public void setPrincipalVariationsForBorrowerCycle(List<Object> principalVariationsForBorrowerCycle) {
        this.principalVariationsForBorrowerCycle = principalVariationsForBorrowerCycle;
    }

    public List<Object> getInterestRateVariationsForBorrowerCycle() {
        return interestRateVariationsForBorrowerCycle;
    }

    public void setInterestRateVariationsForBorrowerCycle(List<Object> interestRateVariationsForBorrowerCycle) {
        this.interestRateVariationsForBorrowerCycle = interestRateVariationsForBorrowerCycle;
    }

    public List<Object> getNumberOfRepaymentVariationsForBorrowerCycle() {
        return numberOfRepaymentVariationsForBorrowerCycle;
    }

    public void setNumberOfRepaymentVariationsForBorrowerCycle(List<Object> numberOfRepaymentVariationsForBorrowerCycle) {
        this.numberOfRepaymentVariationsForBorrowerCycle = numberOfRepaymentVariationsForBorrowerCycle;
    }

    public boolean isCanUseForTopup() {
        return canUseForTopup;
    }

    public void setCanUseForTopup(boolean canUseForTopup) {
        this.canUseForTopup = canUseForTopup;
    }

    public boolean isRatesEnabled() {
        return isRatesEnabled;
    }

    public void setRatesEnabled(boolean ratesEnabled) {
        isRatesEnabled = ratesEnabled;
    }

    public boolean isMultiDisburseLoan() {
        return multiDisburseLoan;
    }

    public void setMultiDisburseLoan(boolean multiDisburseLoan) {
        this.multiDisburseLoan = multiDisburseLoan;
    }

    public boolean isHoldGuaranteeFunds() {
        return holdGuaranteeFunds;
    }

    public void setHoldGuaranteeFunds(boolean holdGuaranteeFunds) {
        this.holdGuaranteeFunds = holdGuaranteeFunds;
    }

    public boolean isAccountMovesOutOfNPAOnlyOnArrearsCompletion() {
        return accountMovesOutOfNPAOnlyOnArrearsCompletion;
    }

    public void setAccountMovesOutOfNPAOnlyOnArrearsCompletion(boolean accountMovesOutOfNPAOnlyOnArrearsCompletion) {
        this.accountMovesOutOfNPAOnlyOnArrearsCompletion = accountMovesOutOfNPAOnlyOnArrearsCompletion;
    }

    public boolean isSyncExpectedWithDisbursementDate() {
        return syncExpectedWithDisbursementDate;
    }

    public void setSyncExpectedWithDisbursementDate(boolean syncExpectedWithDisbursementDate) {
        this.syncExpectedWithDisbursementDate = syncExpectedWithDisbursementDate;
    }

    public boolean isEqualAmortization() {
        return isEqualAmortization;
    }

    public void setEqualAmortization(boolean equalAmortization) {
        isEqualAmortization = equalAmortization;
    }
}

