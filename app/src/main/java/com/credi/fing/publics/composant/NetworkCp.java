package com.credi.fing.publics.composant;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credi.fing.R;
import com.credi.fing.publics.OnClickView;
import com.credi.fing.publics.service.impl.Ut;
import com.google.android.material.button.MaterialButton;

public class NetworkCp {
    private Context context;
    private OnClickView onClickView;
    private View view;

    private ImageView ivNoConnection;
    private LinearLayout cardMessage;
    private TextView tvTitle, tvSubtitle;
    private MaterialButton btnRetry, btnSettings;

    public NetworkCp(Context context,View view, OnClickView onClickView) {
        this.context = context;
        this.onClickView = onClickView;
        this.view = view;
        this.traiter();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OnClickView getOnClickView() {
        return onClickView;
    }

    public void setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
    }

    public void traiter(){
        view.setVisibility(View.VISIBLE);
        ivNoConnection = view.findViewById(R.id.animation_no_connection);
        cardMessage    = view.findViewById(R.id.card_message);
        tvTitle        = view.findViewById(R.id.tv_title_no_connection);
        tvSubtitle     = view.findViewById(R.id.tv_subtitle_no_connection);
        btnRetry       = view.findViewById(R.id.btn_retry);
        btnSettings    = view.findViewById(R.id.btn_settings);

        // Listener pour Réessayer
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(onClickView!=null){
                   onClickView.onClick(v,1);
               }
            }
        });

        // Listener pour ouvrir les réglages Wi-Fi
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickView!=null){
                    onClickView.onClick(v,2);
                }
            }
        });
    }

    public void parametrer(String title,String message,int icone){
        ivNoConnection.setImageResource(icone);
        tvTitle.setText(title);
        if(message!=null&&message.contains("offline")){
            message="Le serveur est momentanément indisponible. Veuillez réessayer ultérieurement.";
        }else {
            System.out.println(" -text- "+message);
            message=traduireMessage(message);
        }
        tvSubtitle.setText(message);
    }

    public static String traduireMessage(String message) {
        if(message==null)return "";
        // Format 1 : "The principal amount 250.0 must be between 1000000.00 and 4000000.00 ."
        if (message.matches("The principal amount \\d+(\\.\\d+)? must be between \\d+(\\.\\d+)? and \\d+(\\.\\d+)?\\s*\\.")) {
            String[] parts = message.split(" ");
            String montant = parts[3];
            String min = parts[7];
            String max = parts[9];
            return "Le montant principal " + Ut.formatMontant(Double.parseDouble(montant)) + " doit être compris entre " + Ut.formatMontant(Double.parseDouble(min)) + " et " + Ut.formatMontant(Double.parseDouble(max)) + ".";
        }

        // Format 2 : "The date on which a loan is submitted cannot be after its expected disbursement date: 2025-08-04."
        if (message.contains("The date on which a loan is submitted cannot be after its expected disbursement date:")) {
            String date = message.replaceAll(".*date: ", "").replace(".", "");
            return "La date de soumission du prêt ne peut pas être postérieure à la date prévue de décaissement : " + date + ".";
        }

        // Si aucun format reconnu
        return message;
    }

}
