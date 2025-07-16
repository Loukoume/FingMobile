package com.credi.fing.publics.service.interfacs;

public class CrudInterface {
    private OnDelete onDelete;
    private OnSave onSave;
    private OnSelect onSelect;

    public CrudInterface() {
    }

    public CrudInterface(OnDelete onDelete, OnSave onSave, OnSelect onSelect) {
        this.onDelete = onDelete;
        this.onSave = onSave;
        this.onSelect = onSelect;
    }

    public OnDelete getOnDelete() {
        return onDelete;
    }

    public CrudInterface setOnDelete(OnDelete onDelete) {
        this.onDelete = onDelete;
        return this;
    }

    public OnSave getOnSave() {
        return onSave;
    }

    public CrudInterface setOnSave(OnSave onSave) {
        this.onSave = onSave;
        return this;
    }

    public OnSelect getOnSelect() {
        return onSelect;
    }

    public CrudInterface setOnSelect(OnSelect onSelect) {
        this.onSelect = onSelect;
        return this;
    }
}
