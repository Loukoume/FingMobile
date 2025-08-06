package com.credi.fing.pojo.err;

import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class ApiErrorResponse {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    private String developerMessage;
    private String httpStatusCode;
    private String defaultUserMessage;
    private String userMessageGlobalisationCode;
    private List<ApiErrorDetail> errors;

    @Override
    public String toString() {
        String er=errorString();
        if(er!=null){
            return er;
        }
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

    private String errorString(){
        if(errors!=null){
            for (ApiErrorDetail api:errors){
                String s=api.toString();
                if(!Objects.equals(s,"Une erreur s'est produit")){
                    return s;
                }
            }
        }
        return null;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

