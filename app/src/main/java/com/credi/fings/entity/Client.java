package com.credi.fings.entity;

import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.credi.fings.publics.AddActivity;
import com.credi.fings.R;
import com.credi.fings.publics.adapters.generiqueAdapter.ActionMenu;
import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.adapters.generiqueAdapter.ElementRow;
import com.credi.fings.publics.adapters.generiqueAdapter.MenuContextuel;
import com.credi.fings.publics.adapters.generiqueAdapter.RowObject;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.Request;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class Client implements Serializable {
    private String idLocal;
    @Expose
    @SerializedName("idClient")
    private String idServeur;
    private String accountNo;
    private String externalId;
    private Status status;
    private SubStatus subStatus;
    private Boolean active;
    private List<Integer> activationDate;
    private String firstname;
    private String lastname;
    private String displayName;
    private String mobileNo;
    private List<Integer> dateOfBirth;
    private Gender gender;
    private ClientType clientType;
    private ClientClassification clientClassification;
    private Boolean isStaff;
    private Integer officeId;
    private String officeName;

    private Long savingsAccountId;
    private LegalForm legalForm;
    private List<Group> groups;
    private ClientNonPersonDetails clientNonPersonDetails;
    private List<LoanAccount> loanAccounts;
    private List<SavingsAccount> savingsAccounts;
    private List<LoanAccount> groupLoanIndividualMonitoringAccounts;
    private List<LoanAccount> guarantorAccounts;

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


    public String getExternalId() {
        return this.externalId;
    }


    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    public Status getStatus() {
        return this.status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public SubStatus getSubStatus() {
        return this.subStatus;
    }


    public void setSubStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
    }


    public Boolean getActive() {
        return this.active;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }





    public String getFirstname() {
        return this.firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return this.lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getDisplayName() {
        return this.displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getMobileNo() {
        return this.mobileNo;
    }


    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }



    public Gender getGender() {
        return this.gender;
    }


    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public ClientType getClientType() {
        return this.clientType;
    }


    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }


    public ClientClassification getClientClassification() {
        return this.clientClassification;
    }


    public void setClientClassification(ClientClassification clientClassification) {
        this.clientClassification = clientClassification;
    }


    public Boolean getIsStaff() {
        return this.isStaff;
    }


    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }


    public Integer getOfficeId() {
        return this.officeId;
    }


    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }


    public String getOfficeName() {
        return this.officeName;
    }


    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }


    public Long getSavingsAccountId() {
        return this.savingsAccountId;
    }


    public void setSavingsAccountId(Long savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }


    public LegalForm getLegalForm() {
        return this.legalForm;
    }


    public void setLegalForm(LegalForm legalForm) {
        this.legalForm = legalForm;
    }




    public ClientNonPersonDetails getClientNonPersonDetails() {
        return this.clientNonPersonDetails;
    }


    public void setClientNonPersonDetails(ClientNonPersonDetails clientNonPersonDetails) {
        this.clientNonPersonDetails = clientNonPersonDetails;
    }




    public String js() {
        return new Gson().toJson(this);
    }

    public Client fromJs(String js) {
        return new Gson().fromJson(js, Client.class);
    }
}


