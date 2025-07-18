package com.credi.fing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BeneficiaryPayload implements Serializable {
    String locale = "en_GB";

    @SerializedName("name")
    String name;

    @SerializedName("accountNumber")
    String accountNumber;

    @SerializedName("accountType")
    int accountType;

    @SerializedName("transferLimit")
    double transferLimit;

    @SerializedName("officeName")
    String officeName;
}
