package com.credi.fing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransferPayload implements Serializable {
    @SerializedName("fromOfficeId")
    Integer fromOfficeId;

    @SerializedName("fromClientId")
    Long fromClientId;

    @SerializedName("fromAccountType")
    Integer fromAccountType;

    @SerializedName("fromAccountId")
    Integer fromAccountId;

    @SerializedName("toOfficeId")
    Integer toOfficeId;

    @SerializedName("toClientId")
    Long toClientId;

    @SerializedName("toAccountType")
    Integer toAccountType;

    @SerializedName("toAccountId")
    Integer toAccountId;

    @SerializedName("transferDate")
    String transferDate;

    @SerializedName("transferAmount")
    Double transferAmount;

    @SerializedName("transferDescription")
    String transferDescription;

    String dateFormat = "dd MMMM yyyy";
    String locale = "en";
}
