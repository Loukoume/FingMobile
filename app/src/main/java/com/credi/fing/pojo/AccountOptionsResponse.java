package com.credi.fing.pojo;

import java.io.Serializable;
import java.util.List;

public class AccountOptionsResponse implements Serializable {
    private List<AccountOption> fromAccountOptions;
    private List<AccountOption> toAccountOptions;

    public List<AccountOption> getFromAccountOptions() {
        return fromAccountOptions;
    }

    public void setFromAccountOptions(List<AccountOption> fromAccountOptions) {
        this.fromAccountOptions = fromAccountOptions;
    }

    public List<AccountOption> getToAccountOptions() {
        return toAccountOptions;
    }

    public void setToAccountOptions(List<AccountOption> toAccountOptions) {
        this.toAccountOptions = toAccountOptions;
    }
}

