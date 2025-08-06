package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fing.R;

import java.math.BigDecimal;
import java.io.Serializable;

public class LoanAccount implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("id")
    private String idServeur;
    private Integer clientId;
    private String accountNo;
    private Integer productId;
    private String productName;
    private String shortProductName;
    private Status status;
    private LoanType loanType;
    private Integer loanCycle;
    private LoanDate timeline;
    private Boolean inArrears;
    private BigDecimal originalLoan;
    private BigDecimal loanBalance;

    @SerializedName("principal")
    private Double principal;

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
    private Double interestRatePerPeriod;

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

    @SerializedName("maxOutstandingLoanBalance")
    private Double maxOutstandingLoanBalance;

    private String dateFormat = "dd MMMM yyyy";
    private String locale = "en";


    public String getIdLocal() {
        return this.idLocal;
    }


    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }


    public String getIdServeur() {
        return this.idServeur;
    }


    public void setIdServeur(String idServeur) {
        this.idServeur = idServeur;
    }


    public String getAccountNo() {
        return this.accountNo;
    }


    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public Integer getProductId() {
        return this.productId;
    }


    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return this.productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getShortProductName() {
        return this.shortProductName;
    }


    public void setShortProductName(String shortProductName) {
        this.shortProductName = shortProductName;
    }


    public Status getStatus() {
        return this.status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public LoanType getLoanType() {
        return this.loanType;
    }


    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }


    public Integer getLoanCycle() {
        return this.loanCycle;
    }


    public void setLoanCycle(Integer loanCycle) {
        this.loanCycle = loanCycle;
    }


    public LoanDate getDate() {
        return this.timeline;
    }


    public void setDate(LoanDate Date) {
        this.timeline = Date;
    }


    public Boolean getInArrears() {
        return this.inArrears;
    }


    public void setInArrears(Boolean inArrears) {
        this.inArrears = inArrears;
    }


    public BigDecimal getOriginalLoan() {
        return this.originalLoan;
    }


    public void setOriginalLoan(BigDecimal originalLoan) {
        this.originalLoan = originalLoan;
    }


    public BigDecimal getLoanBalance() {
        return this.loanBalance;
    }


    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public LoanDate getTimeline() {
        return timeline;
    }

    public void setTimeline(LoanDate timeline) {
        this.timeline = timeline;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
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

    public Double getInterestRatePerPeriod() {
        return interestRatePerPeriod;
    }

    public void setInterestRatePerPeriod(Double interestRatePerPeriod) {
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

    public String js() {
        return new Gson().toJson(this);
    }

    public LoanAccount fromJs(String js) {
        return new Gson().fromJson(js, LoanAccount.class);
    }



    public int checkLoanAccountStatus() {
        LoanAccount loanAccount=this;
        if (loanAccount == null || loanAccount.getStatus() == null) {
            throw new IllegalArgumentException("loanAccount et son status ne doivent pas être null");
        }

        Status status = loanAccount.getStatus();

        // 0 si le compte est clôturé (plus actif)
        if (!status.getActive()) {
            if (status.getPendingApproval() || status.getWaitingForDisbursal()) {
                return -1;
            }
            return 0;
        }

        // -1 si le compte n’est pas encore utilisable
        // (en attente d’approbation ou en attente de décaissement)
        if (status.getPendingApproval() || status.getWaitingForDisbursal()) {
            return -1;
        }

        // 2 si le compte est en défaut de paiement (en retard)
        if (loanAccount.getInArrears()) {
            return 2;
        }

        // Sinon, tout est OK
        return 1;
    }

}


