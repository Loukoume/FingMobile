package com.credi.fings.publics.service.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.credi.fings.publics.service.HttpApi;

public class NetworkReceiver extends BroadcastReceiver {

    TextView wifi;

    public NetworkReceiver(TextView wifi) {
        this.wifi = wifi;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Appel de la méthode utilitaire pour vérifier la connexion
       /* boolean isConnected = NetworkUtils.isNetworkAvailable(context);
       // Dialogue.neutreDialog(isConnected+"","text",context).show();
        if (isConnected) {
            if(wifi!=null){
                wifi.setVisibility(View.GONE);
            }
            else Toast.makeText(context, "Connexion Internet disponible", Toast.LENGTH_SHORT).show();
        } else {
            if(wifi!=null){
                wifi.setVisibility(View.VISIBLE);
            }
           else   Toast.makeText(context, "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
        }*/
       ping(context);
    }

    private void ping(Context context) {
        TextView textView=new TextView(context);
        HttpApi httpApi=new HttpApi("arret/ping",textView,context);
        httpApi.getDatas();
        TextViewHandler textViewHandler = new TextViewHandler(textView);
        textViewHandler.setAfterTextChangedAction(() -> {
            switch (textView.getText().toString()){
                case "Ok":
                    if (wifi != null) {
                        wifi.setVisibility(View.GONE);
                    }else
                     Toast.makeText(context, "Domaine accessible !", Toast.LENGTH_SHORT).show();
                    break;
                case "Error": case "technique":
                    if (wifi != null) {
                        wifi.setVisibility(View.VISIBLE);
                    }else
                     Toast.makeText(context, "Domaine inaccessible !", Toast.LENGTH_SHORT).show();
                   break;

            }
        });
    }
}

