package com.credi.fing.entity;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Beneficiary implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("officeName")
    private String officeName;

    @SerializedName("clientName")
    private String clientName;

    @SerializedName("accountType")
    private AccountTypeOption accountType;

    @SerializedName("accountNumber")
    private String accountNumber;

    @SerializedName("transferLimit")
    private Double transferLimit;
}
