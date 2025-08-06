package com.credi.fing.pojo.err;

import com.google.gson.Gson;

import java.util.List;

public class ApiErrorDetail {
    private String developerMessage;
    private String defaultUserMessage;
    private String userMessageGlobalisationCode;
    private String parameterName;
    private Object value;
    private List<ApiErrorArgument> args;

    // Getters et Setters
    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getDefaultUserMessage() {
        return defaultUserMessage;
    }

    public void setDefaultUserMessage(String defaultUserMessage) {
        this.defaultUserMessage = defaultUserMessage;
    }

    public String getUserMessageGlobalisationCode() {
        return userMessageGlobalisationCode;
    }

    public void setUserMessageGlobalisationCode(String userMessageGlobalisationCode) {
        this.userMessageGlobalisationCode = userMessageGlobalisationCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<ApiErrorArgument> getArgs() {
        return args;
    }

    public void setArgs(List<ApiErrorArgument> args) {
        this.args = args;
    }
    public String js(){
        return new Gson().toJson(this);
    }
    public ApiErrorDetail fromJs(String js){
        return new Gson().fromJson(js,ApiErrorDetail.class);
    }

    @Override
    public String toString() {
        if(defaultUserMessage!=null){
            return defaultUserMessage;
        }
        if(developerMessage!=null){
            return developerMessage;
        }
        if(userMessageGlobalisationCode!=null){
            return userMessageGlobalisationCode.replace("."," ");
        }
        return "Une erreur s'est produit";
    }
}

