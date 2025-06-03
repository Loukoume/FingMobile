package com.credi.fings.publics.service;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetourData<T> {
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("description")
    private String description;

    private Object object;


    @Expose
    @SerializedName("data")
    private T data;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RetourData fromGson(String json){
        Gson gson=new Gson();
        return gson.fromJson(json,RetourData.class);
    }

    public String json(){
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "RetourData{" +
                "status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
