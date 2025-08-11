package com.credi.fing.pojo;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.List;

public class LoanProductResponse implements Serializable {

    // Champs principaux
    private int loanProductId;
    private String loanProductName;
    private String loanProductDescription;
    private boolean isLoanProductLinkedToFloatingRate;
    private Currency currency;
    private double principal;
    private double approvedPrincipal;
    private double proposedPrincipal;
    private int termFrequency;
    private TermFrequencyType termPeriodFrequencyType;
    private int numberOfRepayments;
    private int repaymentEvery;
    private RepaymentFrequencyType repaymentFrequencyType;
    private double interestRatePerPeriod;
    private InterestRateFrequencyType interestRateFrequencyType;
    private double annualInterestRate;
    private boolean isFloatingInterestRate;
    private AmortizationType amortizationType;
    private InterestType interestType;
    private InterestCalculationPeriodType interestCalculationPeriodType;
    private boolean allowPartialPeriodInterestCalcualtion;
    private int transactionProcessingStrategyId;

    // Listes d'options
    private List<ProductOption> productOptions;
    private List<LoanPurposeOption> loanPurposeOptions;
    private List<FundOption> fundOptions;

    // Autres flags
    private boolean canDisburse;
    private boolean isTopup;
    private boolean isInterestRecalculationEnabled;
    private boolean isVariableInstallmentsAllowed;
    private boolean isEqualAmortization;
    private boolean isRatesEnabled;

    public LoanProductResponse() {}

    // Getters & setters générés (à compléter via IDE si besoin)

    public String js() {
        return new Gson().toJson(this);
    }

    public LoanProductResponse fromJs(String js) {
        return new Gson().fromJson(js, LoanProductResponse.class);
    }

    public int getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(int loanProductId) {
        this.loanProductId = loanProductId;
    }

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    public String getLoanProductDescription() {
        return loanProductDescription;
    }

    public void setLoanProductDescription(String loanProductDescription) {
        this.loanProductDescription = loanProductDescription;
    }

    public boolean isLoanProductLinkedToFloatingRate() {
        return isLoanProductLinkedToFloatingRate;
    }

    public void setLoanProductLinkedToFloatingRate(boolean loanProductLinkedToFloatingRate) {
        isLoanProductLinkedToFloatingRate = loanProductLinkedToFloatingRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getApprovedPrincipal() {
        return approvedPrincipal;
    }

    public void setApprovedPrincipal(double approvedPrincipal) {
        this.approvedPrincipal = approvedPrincipal;
    }

    public double getProposedPrincipal() {
        return proposedPrincipal;
    }

    public void setProposedPrincipal(double proposedPrincipal) {
        this.proposedPrincipal = proposedPrincipal;
    }

    public int getTermFrequency() {
        return termFrequency;
    }

    public void setTermFrequency(int termFrequency) {
        this.termFrequency = termFrequency;
    }

    public TermFrequencyType getTermPeriodFrequencyType() {
        return termPeriodFrequencyType;
    }

    public void setTermPeriodFrequencyType(TermFrequencyType termPeriodFrequencyType) {
        this.termPeriodFrequencyType = termPeriodFrequencyType;
    }

    public int getNumberOfRepayments() {
        return numberOfRepayments;
    }

    public void setNumberOfRepayments(int numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
    }

    public int getRepaymentEvery() {
        return repaymentEvery;
    }

    public void setRepaymentEvery(int repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
    }

    public RepaymentFrequencyType getRepaymentFrequencyType() {
        return repaymentFrequencyType;
    }

    public void setRepaymentFrequencyType(RepaymentFrequencyType repaymentFrequencyType) {
        this.repaymentFrequencyType = repaymentFrequencyType;
    }

    public double getInterestRatePerPeriod() {
        return interestRatePerPeriod;
    }

    public void setInterestRatePerPeriod(double interestRatePerPeriod) {
        this.interestRatePerPeriod = interestRatePerPeriod;
    }

    public InterestRateFrequencyType getInterestRateFrequencyType() {
        return interestRateFrequencyType;
    }

    public void setInterestRateFrequencyType(InterestRateFrequencyType interestRateFrequencyType) {
        this.interestRateFrequencyType = interestRateFrequencyType;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public boolean isFloatingInterestRate() {
        return isFloatingInterestRate;
    }

    public void setFloatingInterestRate(boolean floatingInterestRate) {
        isFloatingInterestRate = floatingInterestRate;
    }

    public AmortizationType getAmortizationType() {
        return amortizationType;
    }

    public void setAmortizationType(AmortizationType amortizationType) {
        this.amortizationType = amortizationType;
    }

    public InterestType getInterestType() {
        return interestType;
    }

    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    public InterestCalculationPeriodType getInterestCalculationPeriodType() {
        return interestCalculationPeriodType;
    }

    public void setInterestCalculationPeriodType(InterestCalculationPeriodType interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
    }

    public boolean isAllowPartialPeriodInterestCalcualtion() {
        return allowPartialPeriodInterestCalcualtion;
    }

    public void setAllowPartialPeriodInterestCalcualtion(boolean allowPartialPeriodInterestCalcualtion) {
        this.allowPartialPeriodInterestCalcualtion = allowPartialPeriodInterestCalcualtion;
    }

    public int getTransactionProcessingStrategyId() {
        return transactionProcessingStrategyId;
    }

    public void setTransactionProcessingStrategyId(int transactionProcessingStrategyId) {
        this.transactionProcessingStrategyId = transactionProcessingStrategyId;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }

    public List<LoanPurposeOption> getLoanPurposeOptions() {
        return loanPurposeOptions;
    }

    public void setLoanPurposeOptions(List<LoanPurposeOption> loanPurposeOptions) {
        this.loanPurposeOptions = loanPurposeOptions;
    }

    public List<FundOption> getFundOptions() {
        return fundOptions;
    }

    public void setFundOptions(List<FundOption> fundOptions) {
        this.fundOptions = fundOptions;
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

    // Classes internes pour mapper les objets
    public static class Currency implements Serializable {
        private String code;
        private String name;
        private int decimalPlaces;
        private int inMultiplesOf;
        private String displaySymbol;
        private String nameCode;
        private String displayLabel;

        // Getters & setters
    }

    public static class TermFrequencyType implements Serializable {
        private int id;
        private String code;
        private String value;
    }

    public static class RepaymentFrequencyType implements Serializable {
        private int id;
        private String code;
        private String value;
    }

    public static class InterestRateFrequencyType implements Serializable {
        private int id;
        private String code;
        private String value;
    }

    public static class AmortizationType implements Serializable {
        private int id;
        private String code;
        private String value;
    }

    public static class InterestType implements Serializable {
        private int id;
        private String code;
        private String value;
    }

    public static class InterestCalculationPeriodType implements Serializable {
        private int id;
        private String code;
        private String value;
    }




    public static class FundOption implements Serializable {
        private int id;
        private String name;
    }
}
