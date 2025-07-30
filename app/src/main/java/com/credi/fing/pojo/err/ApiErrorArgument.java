package com.credi.fing.pojo.err;

import com.google.gson.Gson;

import java.util.List;

public class ApiErrorArgument {
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Helper method if you want to extract the value as a String
     */
    public String getValueAsString() {
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof List) {
            // Convert List to string
            return ((List<?>) value).toString();
        } else if (value != null) {
            return value.toString();
        }
        return null;
    }



    public String js(){
        return new Gson().toJson(this);
    }
    public ApiErrorArgument fromJs(String js){
        return new Gson().fromJson(js,ApiErrorArgument.class);
    }
}

