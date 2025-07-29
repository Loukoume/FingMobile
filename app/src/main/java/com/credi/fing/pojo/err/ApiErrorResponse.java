package com.credi.fing.pojo.err;

import com.google.gson.Gson;

import java.util.List;

public class ApiErrorResponse {
    private String developerMessage;
    private String httpStatusCode;
    private String defaultUserMessage;
    private String userMessageGlobalisationCode;
    private List<ApiErrorDetail> errors;

    // Getters et Setters
    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
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

    public List<ApiErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiErrorDetail> errors) {
        this.errors = errors;
    }

    public String js(){
        return new Gson().toJson(this);
    }
    public ApiErrorResponse fromJs(String js){
        return new Gson().fromJson(js,ApiErrorResponse.class);
    }
}

