package com.credi.fing.publics.repository.sqlite;

public class Data {
    private Long idLocal;
    private String js;
    private String className;

    public Data(String js, String className) {
        this.js = js;
        this.className = className;
    }

    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


}
