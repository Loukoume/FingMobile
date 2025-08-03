package com.credi.fing.activity;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.credi.fing.MainActivity;
import com.credi.fing.R;
import com.credi.fing.entity.LoanAccount;
import com.credi.fing.entity.SavingsAccount;
import com.credi.fing.entity.TransferPayload;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.pojo.AccountOptionsResponse;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.composant.NetworkCp;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.SelectService;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemboursementActivity extends AppCompatActivity {
    TextInputEditText id, idMontant, textInputEditText;
    TextInputLayout textInputLayout,textFieldMontant, textField;
    LoanAccount loanAccount;
    AccountOption savingAccount;
    Context context;
    //List<Object> savingAccountes;
    TransferPayload transferPayload;
    ProgressBar pb;
    Attribut attribut;
    LinearLayout network;
    View vide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remboursement);
        context = this;
        network=findViewById(R.id.network);
        String js = getIntent().getStringExtra("compte");
        if (js != null && !js.isEmpty()) {
            loanAccount = (LoanAccount) Ut.fromJs(js, LoanAccount.class);
            //Dialogue.neutreDialog(Ut.js(loanAccount),"",this).show();
            attribut=new Attribut();
            attribut.setSubLabel("accountNo");

            System.out.println(" loanAccount == "+loanAccount.getAccountNo());
            textInputLayout = findViewById(R.id.textInputLayout);
            idMontant = findViewById(R.id.id_montant);
            textField = findViewById(R.id.textField);
            textInputEditText = findViewById(R.id.textInputEditText);
            textFieldMontant = findViewById(R.id.textFieldMontant);
            transferPayload = new TransferPayload();
            transferPayload.setToAccountId(loanAccount.getProductId());
            id = findViewById(R.id.id);
            textField.setHint("Numéro du compte");
            vide=findViewById(R.id.vide);
            pb=findViewById(R.id.pb);
            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String value = s.toString();
                    if(!value.isEmpty()){
                        textInputLayout.setError(null);
                        transferPayload.setTransferDescription(value);
                    }else {
                        transferPayload.setTransferDescription(value);
                    }
                }
            });
            idMontant.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String value = s.toString();
                    if(!value.isEmpty())
                      transferPayload.setTransferAmount(Double.valueOf(value));
                }
            });

            id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Object productName_ob = Ut.getValue(s, "productName");
                    showSelectDialogue(fromAccountOptions, null, "accountType:value", "accountType:value", id, attribut);
                }
            });

            MaterialButton outlinedButton = findViewById(R.id.outlinedButton);
            outlinedButton.setText("Valider");

            outlinedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ok()) {
                        transferPayload.setTransferDate(S.dateToString(new Date(),
                                transferPayload.getDateFormat(), transferPayload.getLocale()));
                       // System.out.println(" -transferPayload- " + transferPayload.js());
                        saveTransfert(transferPayload);
                    }
                }
            });
        }
        getTemplate();
    }

    private boolean ok() {
        if (transferPayload != null) {
            if (transferPayload.getToAccountId() != null && transferPayload.getToClientId() != null) {
                if (transferPayload.getTransferAmount() != null && transferPayload.getTransferDescription() != null
                ) {
                    return true;
                }
                if(transferPayload.getTransferAmount() != null){
                    textField.setError("Obligatoire");
                }else if(transferPayload.getTransferDescription() != null)
                 textFieldMontant.setError("Obligatoire");
                else {
                    textField.setError("Obligatoire");
                    textFieldMontant.setError("Obligatoire");
                }

            } else {
                //textInputLayout.setError("Obligatoire");
                S.toast(context,"Problème de connexion");
            }

        }
        return false;
    }

    private void showSelectDialogue(final List<AccountOption> data, Object sec, String label, String field, TextInputEditText editText,
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
                    AccountOption o = data.get(Integer.parseInt(k + ""));
                    savingAccount =  o;
                    transferPayload.setFromAccountId(savingAccount.getAccountId());
                    transferPayload.setToClientId(Long.valueOf(savingAccount.getClientId()));
                    transferPayload.setFromOfficeId(savingAccount.getOfficeId());
                    transferPayload.setFromAccountType(savingAccount.getAccountType().getIdServeur());
                    transferPayload.setFromClientId(Long.valueOf(savingAccount.getClientId()));
                    editText.setText(savingAccount.getAccountType().getValue());

                })
                .setTitle("Sélectionnez un compte".toUpperCase())
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

    void saveTransfert(TransferPayload loanAccount){
        MonFichier.ecrire(context,"transmis","");
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String tenant = "default";              // tenantIdentifier
        String tpt="tpt?";

        Call<Object> call = api.saveTransFertInterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                loanAccount, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MonFichier.ecrire(context,"transmis","ok");
                    finish();
                } else {
                    String errorContent;
                    try {
                        // Lit le corps de la réponse d’erreur en String
                        errorContent = response.errorBody() != null
                                ? response.errorBody().string()
                                : "Corps de l’erreur vide";
                    } catch (IOException e) {
                        // En cas de problème de lecture
                        e.printStackTrace();
                        errorContent = "Impossible de lire le contenu de l’erreur";
                    }

                    erreurTechnique(errorContent,"Erreur technique",1);

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            if(titre[0].contains("java.net")|| titre[0].contains("javax.net")
                                    || titre[0].contains("failed")|| titre[0].contains("Unable to resolve host"))
                            {
                                titre[0] ="Vérifier votre connexion internet et réessayer";
                                showNetWork("Problème de connexion",titre[0],
                                        R.drawable.wifi_100,1);
                            }else {
                                showNetWork("Erreur technique",t.getMessage(),
                                        R.drawable.erreur_tech_100,1);
                            }
                        });
                    }
                }

            }
        });
    }

    void showPb(){
        pb.setVisibility(View.VISIBLE);
         vide.setVisibility(View.VISIBLE);
    }
    void hidePb(){
        pb.setVisibility(GONE);
        vide.setVisibility(View.GONE);
    }

    AccountOptionsResponse accountOptionsResponse;
    private List<AccountOption> fromAccountOptions;
    private List<AccountOption> toAccountOptions;
    AccountOption toAccountOption;
    void getTemplate(){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
        // String password = Inscription.user;

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String clientId = "individual";                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<AccountOptionsResponse> call = api.getTemplateTransfertInterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<AccountOptionsResponse>() {
            @Override
            public void onResponse(Call<AccountOptionsResponse> call, Response<AccountOptionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accountOptionsResponse = response.body();
                    toAccountOptions=accountOptionsResponse.getToAccountOptions();
                    fromAccountOptions=accountOptionsResponse.getFromAccountOptions().stream().filter(
                            a-> Objects.equals(a.getAccountType().getValue(),"Savings Account")
                                    ||a.getAccountType().getIdServeur()==2
                    ).collect(Collectors.toList());;

                    toAccountOption=toAccountOptions.stream().filter(
                            t-> Objects.equals(t.getAccountNo(),loanAccount.getAccountNo())
                    ).findFirst().orElse(null);

                    if(toAccountOption!=null){
                        transferPayload.setToClientId(Long.valueOf(toAccountOption.getClientId()));
                        transferPayload.setToAccountType(toAccountOption.getAccountType().getIdServeur());
                        transferPayload.setTransferDate(S.dateToString(
                                new Date(),transferPayload.getDateFormat(),transferPayload.getLocale()
                        ));
                        transferPayload.setToOfficeId(toAccountOption.getOfficeId());
                        transferPayload.setToAccountId(toAccountOption.getAccountId());
                    }

                    System.out.println("==cotoAccountOptionsmpte=== "+toAccountOptions.stream().map(t->t.getAccountNo()).collect(Collectors.toList()));
                 } else {
                    String errorContent;
                    try {
                        // Lit le corps de la réponse d’erreur en String
                        errorContent = response.errorBody() != null
                                ? response.errorBody().string()
                                : "Corps de l’erreur vide";
                    } catch (IOException e) {
                        // En cas de problème de lecture
                        e.printStackTrace();
                        errorContent = "Impossible de lire le contenu de l’erreur";
                    }

                    erreurTechnique(errorContent,"Erreur technique",0);

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<AccountOptionsResponse> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            if(titre[0].contains("java.net")|| titre[0].contains("javax.net")
                                    || titre[0].contains("failed")|| titre[0].contains("Unable to resolve host"))
                            {
                                titre[0] ="Vérifier votre connexion internet et réessayer";
                                showNetWork("Problème de connexion",titre[0],
                                        R.drawable.wifi_100,0);
                            }else {
                                showNetWork("Erreur technique",t.getMessage(),
                                        R.drawable.erreur_tech_100,0);
                            }
                        });
                    }
                }
            }
        });
    }

    private void showNetWork(String title,String msg,int icone,int p){
        NetworkCp networkCp=new NetworkCp(context,network,(o, k)->{
            if(k==1){
              if(p==1){
                  saveTransfert(transferPayload);
              }else {
                  getTemplate();
              }
                network.setVisibility(View.GONE);
            }else {
                finish();
            }
        });
        networkCp.parametrer(title,msg,icone);
    }

    private void erreurTechnique(String errorContent,String title,int p){
        // Affiche le message d’erreur et le code HTTP
        if(errorContent.contains("offline")||errorContent.contains("404")){
            errorContent="Service indisponible pour le moment, veuillez réessayer ultérieurement.";
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                String finalErrorContent = errorContent;
                if(finalErrorContent.contains("{")){
                    try {
                        ApiErrorResponse apiErrorResponse=
                                new ApiErrorResponse().fromJs(finalErrorContent);
                        finalErrorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                    }catch (Exception e){

                    }
                }
                String finalErrorContent1 = finalErrorContent;
                activity.runOnUiThread(() -> {
                    showNetWork(title,finalErrorContent1,R.drawable.erreur_tech_100,p);
                });
            }
        }
    }
}