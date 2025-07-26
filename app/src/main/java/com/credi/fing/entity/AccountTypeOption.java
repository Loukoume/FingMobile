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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
