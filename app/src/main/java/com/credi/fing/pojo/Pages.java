package com.credi.fing.pojo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Pages<T> implements Serializable {
    private T content;
    private long totalElements;
    private int totalPages;
    private int number;
    private int numberOfElements;
    private int currentPage;

    public Pages() {
        this.content= (T) new ArrayList<>();
        this.totalPages=1;
        this.totalElements=0;
        this.number=0;
        this.currentPage=1;
    }


    public Pages<List<T>> empty() {
        return new Pages<>();
    }

}
