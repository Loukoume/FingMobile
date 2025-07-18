package com.credi.fing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BeneficiaryUpdatePayload implements Serializable {
    @SerializedName("name")
    String name;

    @SerializedName("transferLimit")
    int transferLimit;
}
