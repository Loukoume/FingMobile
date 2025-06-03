package com.credi.fings.publics.service.pojo;

import java.io.Serializable;

public class Month implements Serializable {
    private String name;
    private int code;

    public Month(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    // Optionnel : Vous pouvez ajouter des setters ou d'autres méthodes si nécessaire.
}

