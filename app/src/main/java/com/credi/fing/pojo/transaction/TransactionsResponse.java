package com.credi.fing.pojo.transaction;

// TransactionsResponse.java
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class TransactionsResponse implements Serializable {
    @SerializedName("pageItems")
    private List<Transaction> pageItems;

    @SerializedName("totalFilteredRecords")
    private Integer totalFilteredRecords;

    @SerializedName("totalPages")
    private Integer totalPages;

    @SerializedName("offset")
    private Integer offset;

    public TransactionsResponse() { }

    public List<Transaction> getPageItems() {
        return pageItems;
    }
    public void setPageItems(List<Transaction> pageItems) {
        this.pageItems = pageItems;
    }

    public Integer getTotalFilteredRecords() {
        return totalFilteredRecords;
    }
    public void setTotalFilteredRecords(Integer totalFilteredRecords) {
        this.totalFilteredRecords = totalFilteredRecords;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getOffset() {
        return offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String js(){
        return new Gson().toJson(this);
    }
    public TransactionsResponse fromJs(String js){
        return new Gson().fromJson(js,TransactionsResponse.class);
    }
}

