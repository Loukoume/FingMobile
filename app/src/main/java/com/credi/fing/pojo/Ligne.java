package com.credi.fing.pojo;



public class Ligne {
    private Long id;
    private String text;
   // private Type type;
   // private Style style;
    private boolean update;


    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

  /*  public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }*/
}
