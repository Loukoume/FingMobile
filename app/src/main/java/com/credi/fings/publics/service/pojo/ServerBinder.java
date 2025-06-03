package com.credi.fings.publics.service.pojo;

import android.app.Activity;
import android.view.View;

import com.credi.fings.publics.adapters.generiqueAdapter.Binder;
import com.credi.fings.publics.repository.sqlite.Data;
import com.credi.fings.publics.service.ApiClient;
import com.credi.fings.publics.service.BinderInterface;
import com.credi.fings.publics.service.IBinder;
import com.credi.fings.publics.service.impl.Attribut;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.ListActivity;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.MonFichier;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerBinder implements Serializable, BinderInterface {
    @Override
    public Binder binder() {
        return new Binder("name","protocole\\(://)ip\\(:)port");
    }

    @Override
    public List<Attribut> setAttribut() {
        List<Attribut> attributs = Arrays.asList(
                new Attribut("Désignation du serveur", "name", true),
                new Attribut("Protocole", "protocole","object", true)
                        .setValues("http","https","ws","wss"),
                new Attribut("Port", "port", true),
                new Attribut("Adress Ip", "ip", true)
        );
        return attributs;
    }

    @Override
    public List<Attribut> setAttribut(List<Object> values) {
        return Collections.emptyList();
    }
    public EditeObject editeObject(Object object){
        EditeObject editeObject=new EditeObject();
        editeObject.setObject(object);
        editeObject.setAttribute(setAttribut());
        editeObject.setDesignation(("Ajouter un serveur"));
        editeObject.setaClass(Server.class);
        editeObject.setPostUrl(null);
        return editeObject;
    }
    public LocalData localData(){
        LocalData localData=new LocalData();
        localData.setDataClasse(Server.class);
        localData.setTable("className");
        localData.setCondition("like");
        localData.setValue("server");
        return localData;
    }
    public NavigateObject navigateObject(Object object){
        NavigateObject navigateObject=new NavigateObject()
                .setBinder(binder())
                .setaClass(Data.class)
                .setTitle("Serveurs".toUpperCase())
                .setAddButton(true)
                .setTypeData(2)
                .setLocalData(localData())
                .setEditeObject(editeObject(object));
        ListActivity.iBinder=new IBinder() {
            @Override
            public void onClick(View itemView, Object data, int position) {
               Server server=(Server) Ut.creatObject(data, Server.class);
               String url=server.getProtocole()+"://"+server.getIp()+":"+server.getPort()+"/";
                MonFichier.ecrire(itemView.getContext(), ApiClient.SERVER_FILE_NAME,url);
                ApiClient.getServer(itemView.getContext());
                ((Activity)itemView.getContext()).finish();
            }

            @Override
            public void onLongClick(View itemView, Object data, int position) {

            }
        };

        return navigateObject;
    }
}
