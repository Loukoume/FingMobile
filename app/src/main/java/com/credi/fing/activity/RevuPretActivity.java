package com.credi.fing.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.credi.fing.MainActivity;
import com.credi.fing.R;
import com.credi.fing.entity.Client;
import com.credi.fing.pojo.LoanPojo;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevuPretActivity extends AppCompatActivity {
    Context context;
     private TextView tvAccountNumberValue;
    private TextView tvLoanProductValue;
    private TextView tvLoanPurposeValue;
    private TextView tvPrincipalAmountValue;
    private TextView tvCurrencyValue;
    private TextView tvSubmissionDeadlineValue;
    private TextView tvExpectedPaymentDateValue;
    private Button buttonEditLoan;
    private Button buttonConfirmLoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revu_pret);
        TextView tx=findViewById(R.id.tx_text);
        context=this;
        tx.setText("Revue Demande de prêt".toUpperCase());
        tvAccountNumberValue         = findViewById(R.id.tvAccountNumberValue);
        tvLoanProductValue           = findViewById(R.id.tvLoanProductValue);
        tvLoanPurposeValue           = findViewById(R.id.tvLoanPurposeValue);
        tvPrincipalAmountValue       = findViewById(R.id.tvPrincipalAmountValue);
        tvCurrencyValue              = findViewById(R.id.tvCurrencyValue);
        tvSubmissionDeadlineValue    = findViewById(R.id.tvSubmissionDeadlineValue);
        tvExpectedPaymentDateValue   = findViewById(R.id.tvExpectedPaymentDateValue);

        // 4) Boutons d’action (inclus via <include layout="@layout/two_button"/>)
        buttonEditLoan    = findViewById(R.id.saves);
        buttonConfirmLoan = findViewById(R.id.ajouter);
        vide=findViewById(R.id.vide);
        pb=findViewById(R.id.pb);
        if(getIntent().hasExtra("editeObject")){
            String js=getIntent().getStringExtra("editeObject");
            if(js!=null){
                LoanPojo loan= (LoanPojo) Ut.fromJs(js,LoanPojo.class);
                if(loan!=null){
                    if (loan != null) {
                        // Numéro de compte
                        tvAccountNumberValue.setText("Indéfini");

                        // Produit de crédit (nom du produit)
                        tvLoanProductValue.setText(
                                loan.getProductOption() != null
                                        ? loan.getProductOption().getName()
                                        : loan.getProductName()
                        );

                        // Objet du prêt (ID à traduire en texte selon votre logique métier)
                        // Par exemple, si vous avez une Map<Integer, String> loanPurposeMap :
                        // tvLoanPurposeValue.setText( loanPurposeMap.get(loan.getLoanPurposeId()) );
                        tvLoanPurposeValue.setText(
                               ""
                        );

                        // Montant principal
                        tvPrincipalAmountValue.setText(
                                loan.getPrincipal() != null
                                        ? NumberFormat.getNumberInstance(Locale.FRANCE)
                                        .format(loan.getPrincipal()) + ",00"
                                        : "-"
                        );

                        // Monnaie (fixe ou extraite d’un champ si vous l’avez)
                       // tvCurrencyValue.setText("XOF");

                        // Date limite de soumission (submittedOnDate au format "yyyy-MM-dd")
                        if (loan.getSubmittedOnDate() != null) {
                            // Supposons que loan.getSubmittedOnDate() = "2025-06-15"
                            tvSubmissionDeadlineValue.setText(
                                    formatDate(loan.getSubmittedOnDate(), "yyyy-MM-dd", "dd MM yyyy")
                            );
                        }

                        // Date de paiement attendue (expectedDisbursementDate)
                        if (loan.getExpectedDisbursementDate() != null) {
                            tvExpectedPaymentDateValue.setText(
                                    formatDate(loan.getExpectedDisbursementDate(), "yyyy-MM-dd", "dd MM yyyy")
                            );
                        }
                    }

                   buttonConfirmLoan.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           AddActivity.finish=true;
                           saveLoan(loan);
                       }
                   });
                    buttonEditLoan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           /* List<String> comptes=loanAccounts.stream().map(ac->Ut.getValue(ac,"accountNo")+"").collect(Collectors.toList());
                            LoanAccountBinder pretBinder=new LoanAccountBinder();
                            EditeObject editeObject=pretBinder.editeObject(MainActivity.loanProductResponse,comptes);
                            editeObject.setObject(loan);

                            startActivity(new Intent(context, AddActivity.class)
                                    .putExtra("object",editeObject));*/
                            AddActivity.finish=false;
                            finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        AddActivity.finish=true;
    }
    private String formatDate(String dateStr, String fromPattern, String toPattern) {
        try {
            SimpleDateFormat src = new SimpleDateFormat(fromPattern, Locale.US);
            SimpleDateFormat dst = new SimpleDateFormat(toPattern, Locale.FRANCE);
            Date date = src.parse(dateStr);
            return dst.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }
    ProgressBar pb;
    View vide;
    void showPb(){
        pb.setVisibility(View.VISIBLE);
        vide.setVisibility(View.VISIBLE);
    }
    void hidePb(){
        pb.setVisibility(View.GONE);
        vide.setVisibility(View.GONE);
    }
    public static List<Object> savingsAccounts=MainActivity.savingsAccounts;
    public static List<Object> loanAccounts=MainActivity.savingsAccounts;
    Client client;
    void saveLoan(LoanPojo loanAccount){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
        loanAccount.setProductId(loanAccount.getProductOption().getId());
        loanAccount.setProductOption(null);
        // String password = Inscription.user;

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String tenant = "default";              // tenantIdentifier

        Call<Object> call = api.saveLoan("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                loanAccount, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getClientAcount();
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
                    // Affiche le message d’erreur et le code HTTP

                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            String finalErrorContent = errorContent;
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(
                                        finalErrorContent,
                                        "Code d'erreur : " + response.code(),
                                        context
                                ).show();
                           });
                        }
                    }

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();

                // 1) Loggez la stack trace dans Logcat
                Log.e("LoanSave", "Erreur onFailure", t);

                // 2) Récupérez la stack trace complète
                String stackTrace = Log.getStackTraceString(t);

                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            Dialogue.neutreDialog(stackTrace, "Echec", context).show();
                        });
                    }
                }

            }
        });
    }



    void getClientAcount(){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
        // String password = Inscription.user;

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        long clientId = Inscription.user.getClientId();                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<Client> call = api.getClientAccountsById("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {
                    client = response.body();
                    loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    startActivity(new Intent(context, PagerActivity.class)
                            .putExtra("client",client)
                            .putExtra("new","new"));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(response.message()+" "+response.code(),response.errorBody()+"",context).show();
                            });
                        }
                    }

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();

            }
        });
    }
}