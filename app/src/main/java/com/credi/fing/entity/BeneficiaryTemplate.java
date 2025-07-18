package com.credi.fing.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BeneficiaryTemplate implements Serializable {
    @SerializedName("accountTypeOptions")
    private List<AccountTypeOption> accountTypeOptions = null;

    public List<AccountTypeOption> getAccountTypeOptions() {
        return accountTypeOptions;
    }

    public void setAccountTypeOptions(List<AccountTypeOption> accountTypeOptions) {
        this.accountTypeOptions = accountTypeOptions;
    }

    public String js(){
        return new Gson().toJson(this);
    }
    public BeneficiaryTemplate fromJs(String js){
        return new Gson().fromJson(js, BeneficiaryTemplate.class);
    }
}
