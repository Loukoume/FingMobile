package com.credi.fings.publics.service;

import com.credi.fings.publics.service.interfacs.CrudInterface;

public class ClickHandler {
    private static OnItemViewClick onItemViewClick;
    private static CrudInterface crudInterface;
    private static OnBindViewHolderAction onBindViewHolderAction;
    private static SendHttp sendHttp;

    public static void setOnItemViewClick(OnItemViewClick clickListener) {
        onItemViewClick = clickListener;
    }

    public static OnItemViewClick getOnItemViewClick() {
        return onItemViewClick;
    }

    public static CrudInterface getCrudInterface() {
        return crudInterface;
    }

    public static void setCrudInterface(CrudInterface crudInterface) {
        ClickHandler.crudInterface = crudInterface;
    }

    public static OnBindViewHolderAction getOnBindViewHolderAction() {
        return onBindViewHolderAction;
    }

    public static void setOnBindViewHolderAction(OnBindViewHolderAction onBindViewHolderAction) {
        ClickHandler.onBindViewHolderAction = onBindViewHolderAction;
    }

    public static SendHttp getSendHttp() {
        return sendHttp;
    }

    public static void setSendHttp(SendHttp sendHttp) {
        ClickHandler.sendHttp = sendHttp;
    }

    public static void annuler(){
        crudInterface=null;
        onItemViewClick=null;
        onBindViewHolderAction=null;
        sendHttp=null;
    }
}

