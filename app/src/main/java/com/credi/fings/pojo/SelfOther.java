package com.credi.fings.pojo;

public class SelfOther<T,N>{
    private T self;
    private N other;

    public SelfOther() {
    }

    public SelfOther(T self, N other) {
        this.self = self;
        this.other = other;
    }

    public T getSelf() {
        return self;
    }

    public void setSelf(T self) {
        this.self = self;
    }

    public N getOther() {
        return other;
    }

    public void setOther(N other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "SelfOther{" +
                "self=" + self +
                ", other=" + other +
                '}';
    }
}
