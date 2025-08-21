package com.credi.fing.activity;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fing.R;
import com.credi.fing.enums.TypeTransFert;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.SelectService;
import com.credi.fing.publics.service.impl.Ut;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NewTransfertActivity extends AppCompatActivity {
    LinearLayout lsender,outlin_sender,lreceaver,outlin_recieaver;
    Context context;
    TextInputLayout input,fieldNom,textFieldCpEmt;
    TextInputEditText nom,id,idCpEmt;
    Attribut attribut;

    TextInputLayout inputBen,fieldNomBen,textField;
    TextInputEditText nomBen,idBen;
    TextView titleSender,titleRecieaver;
    Attribut attributBen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transfert);
        context=this;
        lsender=findViewById(R.id.top);
        lreceaver=findViewById(R.id.lreceaver);

        System.out.println(lsender+"  sd-rvr  "+lreceaver);
        senderService();
        recieverService();
    }

    void recieverService(){
        View view= Ut.getView(context,R.layout.layout_info_receaver);
        traiterBeneficiaire(view);
        outlin_recieaver=lreceaver.findViewById(R.id.main);
        titleRecieaver=lreceaver.findViewById(R.id.title);
        titleRecieaver.setText("Informations bénéficiaire");
        outlin_recieaver.addView(view);
    }

    void senderService(){
        View view= Ut.getView(context,R.layout.layout_info_sender);
        textFieldCpEmt=view.findViewById(R.id.textFieldCpEmt);
        idCpEmt=view.findViewById(R.id.idCpEmt);
        input=view.findViewById(R.id.textField);
        input.setHint("Numéro du compte");
        id=view.findViewById(R.id.id);
        fieldNom=view.findViewById(R.id.fieldNom);
        nom=view.findViewById(R.id.et_full_name);

        attribut=new Attribut();
        attribut.setSubLabel("clientName");


        idCpEmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value=s.toString();
                /*
                  if(!value.isEmpty()){
                        textFieldCpEmt.setError(null);
                        activity.updateTransferPayload("transferAmount",value);
                    }else {
                        activity.updateTransferPayload("transferAmount",null);
                    }
                */
            }
        });
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showSelectDialogue(activity.getFromAccountOptions(), null, "accountNo", "accountNo", id, attribut);
            }
        });
        //traiterBeneficiaire(lbene);
        outlin_sender=lsender.findViewById(R.id.main);
        outlin_sender.addView(view);
        titleSender=lsender.findViewById(R.id.title);
        titleSender.setText("Informations émetteur");

    }


    private void showSelectDialogue(final List<AccountOption> data, Object sec,
                                    String label, String field, TextInputEditText editText,
                                    Attribut attribut) {
        View dialogView = Ut.getView(context, R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();

        List<Object> selection;
        if (sec == null) selection = new ArrayList<>();
        else selection = (List<Object>) sec;

        SelectService selectService = new SelectService(context, new ArrayList<>(data), label, attribut,
                (d, v, k) -> {
                    d.cancel();
                    // String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                    AccountOption accountOption=data.get(Integer.parseInt(k + ""));;
                    id.setText(accountOption.getAccountNo());
                    nom.setText(accountOption.getClientName());
                   /* activity.updateTransferPayload("fromOfficeId",accountOption.getOfficeId());
                    activity.updateTransferPayload("fromClientId",accountOption.getClientId());
                    activity.updateTransferPayload("fromAccountType",accountOption.getAccountType().getIdServeur());
                    activity.updateTransferPayload("fromAccountId",accountOption.getAccountId());
                    activity.setFromAccountOption(accountOption);*/

                })
                .setTitle("Compte émetteur".toUpperCase())
                .setMultiselect(false)
                .setSelect(selection);

        View view = selectService.view(alertDialog);

        View btn1 = Ut.getView(context, R.layout.outline_bouton);
        MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
        mtbt1.setText("Valider la sélection");
        lm.addView(view);
        //  lm.addView(btn1);


        alertDialog.show();
        mtbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                String fs = field.contains(":") ? field.substring(0, field.indexOf(":")) : field;
                // object=Ut.setField(fs,object,selectService.getSelect());
                editText.setText(selectService.getStringSelect());
                //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
            }
        });

    }


    private void traiterBeneficiaire(View view){
        inputBen=view.findViewById(R.id.textField);
        inputBen.setHint("Numéro du compte");
        fieldNomBen=view.findViewById(R.id.textFieldNom);
        idBen=view.findViewById(R.id.id);
        nomBen=view.findViewById(R.id.nom);
        attributBen=new Attribut();
        attributBen.setSubLabel("clientName");

       /* if(activity!=null&&activity.getTransferPayload()!=null){
            // S.toast(getContext(),"input -v");
            idBen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectDialogueBene(activity.getToAccountOptions(), null, "accountNo", "acountNo", idBen, attributBen);
                }
            });
        }*/
    }

    private void showSelectDialogueBene(final List<AccountOption> data, Object sec,
                                        String label, String field, TextInputEditText editText,
                                        Attribut attribut) {
        View dialogView = Ut.getView(context, R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        if(true){
            MaterialButton add=dialogView.findViewById(R.id.outlinedButton);
            LinearLayout espace=dialogView.findViewById(R.id.espace);
            LinearLayout ladd=dialogView.findViewById(R.id.add_bouton);
            ladd.setVisibility(VISIBLE);
            espace.setVisibility(VISIBLE);
            add.setText("Plus");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*activity.startActivity(new Intent(context, Beneficiaire.class)
                            .putExtra("add","add")
                            .putExtra("titre","Bénéficiaires"));
                    activity.finish();*/
                }
            });
        }

        final AlertDialog alertDialog = builder.create();

        List<Object> selection;
        if (sec == null) selection = new ArrayList<>();
        else selection = (List<Object>) sec;

        SelectService selectService = new SelectService(context, new ArrayList<>(data), label, attribut,
                (d, v, k) -> {
                    d.cancel();
                    // String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                    AccountOption accountOption=data.get(Integer.parseInt(k + ""));;
                    // AccountOption accountOption=activity.getToAccountOptions().get(item.getItemId()-1);
                    idBen.setText(accountOption.getAccountNo());
                    nomBen.setText(accountOption.getClientName());
                   /* activity.updateTransferPayload("toOfficeId",accountOption.getOfficeId());
                    activity.updateTransferPayload("toClientId",accountOption.getClientId());
                    activity.updateTransferPayload("toAccountType",accountOption.getAccountType().getIdServeur());
                    activity.updateTransferPayload("toAccountId",accountOption.getAccountId());
                    activity.setToAccountOption(accountOption);*/

                })
                .setTitle("Compte bénéficiaire".toUpperCase())
                .setMultiselect(false)
                .setSelect(selection);

        View view = selectService.view(alertDialog);

        View btn1 = Ut.getView(context, R.layout.outline_bouton);
        MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
        mtbt1.setText("Valider la sélection");
        lm.addView(view);
        //  lm.addView(btn1);


        alertDialog.show();
        mtbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                String fs = field.contains(":") ? field.substring(0, field.indexOf(":")) : field;
                // object=Ut.setField(fs,object,selectService.getSelect());
                editText.setText(selectService.getStringSelect());
                //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
            }
        });

    }
}