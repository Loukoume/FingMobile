package com.credi.fings.publics.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.credi.fings.publics.service.DialogAction;


/**
 * Created by Loukoume on 05/02/2018.
 */

public class Dialogue {

    public static AlertDialog neutreDialog(String titre, String T, final Context context) {
        // Création d'un boite de dialogue
        if(titre.contains("java.net")||titre.contains("javax.net"))titre="Vérifier votre connexion internet et réessayer";
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);
        builder.setMessage(titre);
        builder.setTitle(T);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialog = builder.create();
        return dialog;
    }
    public static AlertDialog neutreDialog(String titre, String T, final Context context, DialogAction dialogInterface,Object object) {
        // Création d'un boite de dialogue
        // if(titre.contains("java.net")||titre.contains("javax.net"))titre="Vérifier votre connexion internet et réessayer";
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);
        builder.setMessage(titre);
        builder.setTitle(T);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(dialogInterface!=null){
                            dialogInterface.execut(object,context);
                        }
                        dialog.cancel();
                    }
                });

        dialog = builder.create();
        return dialog;
    }



    public static Dialog neutreDialogF(final String titre, String T, final Context context, final Activity activity) {
        // Création d'un boite de dialogue
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(T);
        builder.setCancelable(false);
        builder.setMessage(titre);

        builder.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        activity.finish();
                    }
                });

        dialog = builder.create();
        return dialog;
    }



}
