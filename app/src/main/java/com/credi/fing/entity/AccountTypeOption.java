package com.credi.fing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccountTypeOption implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("code")
    private String code;

    @SerializedName("value")
    private String value;
}
