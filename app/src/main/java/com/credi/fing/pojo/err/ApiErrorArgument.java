package com.credi.fing.pojo.err;

import com.google.gson.Gson;

public class ApiErrorArgument {
    private String value;

    // Getter et Setter
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String js(){
        return new Gson().toJson(this);
    }
    public ApiErrorArgument fromJs(String js){
        return new Gson().fromJson(js,ApiErrorArgument.class);
    }
}

