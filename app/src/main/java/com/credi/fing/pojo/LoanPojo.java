package com.credi.fing.pojo;

import com.credi.fing.entity.LoanDate;
import com.credi.fing.entity.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class LoanPojo implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idLoanAccount")
    private String idServeur;
    private Integer clientId;
    private String accountNo;
    private Integer productId;
    private ProductOption productOption;
    private String productName;
    private String shortProductName;
    private Status status;
    private String loanType;
    private Integer loanCycle;
    private LoanDate timeline;
    private Boolean inArrears;
    private BigDecimal originalLoan;
    private BigDecimal loanBalance;
    private Boolean isEqualAmortization;
    private Boolean allowPartialPeriodInterestCalcualtion;
    private Integer fundId;

    @SerializedName("principal")
    private Integer principal;

    @SerializedName("loanTermFrequency")
    private Integer loanTermFrequency;

    @SerializedName("loanTermFrequencyType")
    private Integer loanTermFrequencyType;

    @SerializedName("numberOfRepayments")
    private Integer numberOfRepayments;

    @SerializedName("repaymentEvery")
    private Integer repaymentEvery;

    @SerializedName("repaymentFrequencyType")
    private Integer repaymentFrequencyType;

    @SerializedName("interestRatePerPeriod")
    private Integer interestRatePerPeriod;

    @SerializedName("amortizationType")
    private Integer amortizationType;

    @SerializedName("interestType")
    private Integer interestType;

    @SerializedName("interestCalculationPeriodType")
    private Integer interestCalculationPeriodType;

    @SerializedName("transactionProcessingStrategyId")
    private Integer transactionProcessingStrategyId;

    @SerializedName("expectedDisbursementDate")
    private String expectedDisbursementDate;

    @SerializedName("submittedOnDate")
    private String submittedOnDate;

    @SerializedName("linkAccountId")
    private Integer linkAccountId;

    @SerializedName("loanPurposeId")
    private Integer loanPurposeId;

    private LoanPurposeOption loanPurpose;

    @SerializedName("maxOutstandingLoanBalance")
    private Double maxOutstandingLoanBalance;

    private String dateFormat = "dd MMMM yyyy";
    private String locale = "fr";

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(String idServeur) {
        this.idServeur = idServeur;
    }

    public LoanPurposeOption getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(LoanPurposeOption loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public Boolean getEqualAmortization() {
        return isEqualAmortization;
    }

    public void setEqualAmortization(Boolean equalAmortization) {
        isEqualAmortization = equalAmortization;
    }

    public Boolean getAllowPartialPeriodInterestCalcualtion() {
        return allowPartialPeriodInterestCalcualtion;
    }

    public void setAllowPartialPeriodInterestCalcualtion(Boolean allowPartialPeriodInterestCalcualtion) {
        this.allowPartialPeriodInterestCalcualtion = allowPartialPeriodInterestCalcualtion;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortProductName() {
        return shortProductName;
    }

    public void setShortProductName(String shortProductName) {
        this.shortProductName = shortProductName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Integer getLoanCycle() {
        return loanCycle;
    }

    public void setLoanCycle(Integer loanCycle) {
        this.loanCycle = loanCycle;
    }

    public LoanDate getTimeline() {
        return timeline;
    }

    public void setTimeline(LoanDate timeline) {
        this.timeline = timeline;
    }

    public Boolean getInArrears() {
        return inArrears;
    }

    public void setInArrears(Boolean inArrears) {
        this.inArrears = inArrears;
    }

    public BigDecimal getOriginalLoan() {
        return originalLoan;
    }

    public void setOriginalLoan(BigDecimal originalLoan) {
        this.originalLoan = originalLoan;
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Integer getPrincipal() {
        return principal;
    }

    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }

    public Integer getLoanTermFrequency() {
        return loanTermFrequency;
    }

    public void setLoanTermFrequency(Integer loanTermFrequency) {
        this.loanTermFrequency = loanTermFrequency;
    }

    public Integer getLoanTermFrequencyType() {
        return loanTermFrequencyType;
    }

    public void setLoanTermFrequencyType(Integer loanTermFrequencyType) {
        this.loanTermFrequencyType = loanTermFrequencyType;
    }

    public Integer getNumberOfRepayments() {
        return numberOfRepayments;
    }

    public void setNumberOfRepayments(Integer numberOfRepayments) {
        this.numberOfRepayments = numberOfRepayments;
    }

    public Integer getRepaymentEvery() {
        return repaymentEvery;
    }

    public void setRepaymentEvery(Integer repaymentEvery) {
        this.repaymentEvery = repaymentEvery;
    }

    public Integer getRepaymentFrequencyType() {
        return repaymentFrequencyType;
    }

    public void setRepaymentFrequencyType(Integer repaymentFrequencyType) {
        this.repaymentFrequencyType = repaymentFrequencyType;
    }

    public Integer getInterestRatePerPeriod() {
        return interestRatePerPeriod;
    }

    public void setInterestRatePerPeriod(Integer interestRatePerPeriod) {
        this.interestRatePerPeriod = interestRatePerPeriod;
    }

    public Integer getAmortizationType() {
        return amortizationType;
    }

    public void setAmortizationType(Integer amortizationType) {
        this.amortizationType = amortizationType;
    }

    public Integer getInterestType() {
        return interestType;
    }

    public void setInterestType(Integer interestType) {
        this.interestType = interestType;
    }

    public Integer getInterestCalculationPeriodType() {
        return interestCalculationPeriodType;
    }

    public void setInterestCalculationPeriodType(Integer interestCalculationPeriodType) {
        this.interestCalculationPeriodType = interestCalculationPeriodType;
    }

    public Integer getTransactionProcessingStrategyId() {
        return transactionProcessingStrategyId;
    }

    public void setTransactionProcessingStrategyId(Integer transactionProcessingStrategyId) {
        this.transactionProcessingStrategyId = transactionProcessingStrategyId;
    }

    public String getExpectedDisbursementDate() {
        return expectedDisbursementDate;
    }

    public void setExpectedDisbursementDate(String expectedDisbursementDate) {
        this.expectedDisbursementDate = expectedDisbursementDate;
    }

    public String getSubmittedOnDate() {
        return submittedOnDate;
    }

    public void setSubmittedOnDate(String submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
    }

    public Integer getLinkAccountId() {
        return linkAccountId;
    }

    public void setLinkAccountId(Integer linkAccountId) {
        this.linkAccountId = linkAccountId;
    }

    public Integer getLoanPurposeId() {
        return loanPurposeId;
    }

    public void setLoanPurposeId(Integer loanPurposeId) {
        this.loanPurposeId = loanPurposeId;
    }

    public Double getMaxOutstandingLoanBalance() {
        return maxOutstandingLoanBalance;
    }

    public void setMaxOutstandingLoanBalance(Double maxOutstandingLoanBalance) {
        this.maxOutstandingLoanBalance = maxOutstandingLoanBalance;
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
}
