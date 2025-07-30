package com.credi.fing.pojo.transaction;

// Currency.java
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Currency implements Serializable {
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("decimalPlaces")
    private Integer decimalPlaces;

    @SerializedName("displaySymbol")
    private String displaySymbol;

    @SerializedName("nameCode")
    private String nameCode;

    @SerializedName("displayLabel")
    private String displayLabel;

    public Currency() { }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}

