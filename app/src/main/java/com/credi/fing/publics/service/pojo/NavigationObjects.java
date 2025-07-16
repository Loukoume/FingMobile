package com.credi.fing.publics.service.pojo;

import com.credi.fing.publics.adapters.generiqueAdapter.RowObject;

import java.io.Serializable;
import java.util.List;

public class NavigationObjects implements Serializable {
  private List<Navig> navigs;
  private Object object;
  private RowObject rowObject;

    public List<Navig> getNavigs() {
        return navigs;
    }

    public NavigationObjects setNavigs(List<Navig> navigs) {
        this.navigs = navigs;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public NavigationObjects setObject(Object object) {
        this.object = object;
        return this;
    }

    public RowObject getRowObject() {
        return rowObject;
    }

    public NavigationObjects setRowObject(RowObject rowObject) {
        this.rowObject = rowObject;
        return this;
    }
}
