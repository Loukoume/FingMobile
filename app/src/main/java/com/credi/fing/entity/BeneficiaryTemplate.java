package com.credi.fing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BeneficiaryTemplate implements Serializable {
    @SerializedName("accountTypeOptions")
    private List<AccountTypeOption> accountTypeOptions = null;
}
