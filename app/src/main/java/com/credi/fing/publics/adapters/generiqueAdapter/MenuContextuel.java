package com.credi.fing.publics.adapters.generiqueAdapter;

import com.credi.fing.publics.service.FindObject;

import java.io.Serializable;

public class MenuContextuel implements Serializable {
    private String label;
    private ActionMenu actionMenu;
    private Class<?> t;
    private FindObject findObject;

    public FindObject getFindObject() {
        return findObject;
    }

    public void setFindObject(FindObject findObject) {
        this.findObject = findObject;
    }


    public MenuContextuel(Object... attributs) {
        for (Object attr : attributs) {
            if (attr instanceof String) {
                this.label = (String) attr;
            } else if (attr instanceof ActionMenu) {
                this.actionMenu = (ActionMenu) attr;
            } else if (attr instanceof Class<?>) {
                this.t = (Class<?>) attr;
            }else if(attr instanceof FindObject){
                this.findObject=(FindObject) attr;
            }
        }
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ActionMenu getActionMenu() {
        return actionMenu;
    }

    public void setActionMenu(ActionMenu actionMenu) {
        this.actionMenu = actionMenu;
    }

    public Class<?> getT() {
        return t;
    }

    public void setT(Class<?> t) {
        this.t = t;
    }
}
