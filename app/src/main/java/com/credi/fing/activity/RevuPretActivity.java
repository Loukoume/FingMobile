package com.credi.fing.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.credi.fing.MainActivity;
import com.credi.fing.R;
import com.credi.fing.entity.Client;
import com.credi.fing.pojo.LoanPojo;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.composant.NetworkCp;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Anim;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.S;

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
    private TextView tvCurrencyValue,tv_subtitle_no_connection;
    private TextView tvSubmissionDeadlineValue;
    private TextView tvExpectedPaymentDateValue;
    private Button buttonEditLoan;
    private Button buttonConfirmLoan;
    LinearLayout network;
    View vide;
    LoanPojo loan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revu_pret);
        TextView tx=findViewById(R.id.tx_text);
        context=this;
        network=findViewById(R.id.network);
        tv_subtitle_no_connection=findViewById(R.id.tv_subtitle_no_connection);
        tv_subtitle_no_connection.setSelected(true);
        vide=findViewById(R.id.vde);
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
                 loan= (LoanPojo) Ut.fromJs(js,LoanPojo.class);
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
                           if(loan.getProductOption()!=null||repeat)
                              saveLoan(loan);
                           else {
                               S.toast(context,"Remplire correctement les champs");
                               finish();
                           }
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
    void showPb(){
        pb.setVisibility(VISIBLE);
        vide.setVisibility(VISIBLE);
    }
    void hidePb(){
        pb.setVisibility(GONE);
        vide.setVisibility(GONE);
    }
    public static List<Object> savingsAccounts=MainActivity.savingsAccounts;
    public static List<Object> loanAccounts=MainActivity.savingsAccounts;
    Client client;
    boolean repeat=false;
    void saveLoan(LoanPojo loanAccount){
        tv_subtitle_no_connection.setVisibility(GONE);
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();
        if(!repeat){
            loanAccount.setProductId(loanAccount.getProductOption().getId());
            loanAccount.setProductOption(null);
        }
        repeat=false;
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
                    if(errorContent.contains("java.net")|| errorContent.contains("javax.net"))
                    {
                        errorContent ="Vérifier votre connexion internet et réessayer";
                       /* showNetWork("Problème de connexion","" +
                                        "Vérifier votre connexion internet et réessayer",
                                R.drawable.wifi_50);*/
                    }
                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }
                    tv_subtitle_no_connection.setText(errorContent);
                    tv_subtitle_no_connection.setVisibility(VISIBLE);
                    tv_subtitle_no_connection.startAnimation(Anim.getAnimeBH(context));
                    repeat=true;
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
                            if(titre[0].contains("java.net")|| titre[0].contains("javax.net"))
                            {
                                titre[0] ="Vérifier votre connexion internet et réessayer";
                                /*showNetWork("Problème de connexion",titre[0],
                                        R.drawable.wifi_100);*/
                            }/*else {
                                showNetWork("Erreur technique",t.getMessage(),
                                        R.drawable.erreur_tech_100);
                            }*/
                            if(titre[0].contains("{")){
                                try {
                                    ApiErrorResponse apiErrorResponse=
                                            new ApiErrorResponse().fromJs(titre[0]);
                                    titre[0]= ErrorUtils.buildErrorMessage(apiErrorResponse);
                                }catch (Exception e){

                                }
                            }
                            tv_subtitle_no_connection.setText(titre[0]);
                            tv_subtitle_no_connection.setVisibility(VISIBLE);

                            tv_subtitle_no_connection.startAnimation(Anim.getAnimeBH(context));
                            repeat=true;
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
                    finish();

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                finish();
            }
        });
    }

    private void showNetWork(String title,String msg,int icone){
        NetworkCp networkCp=new NetworkCp(context,network,(o, k)->{
            if(k==1){
                repeat=true;
                saveLoan(loan);
                network.setVisibility(GONE);
            }else {
                network.setVisibility(GONE);
            }
        });
        networkCp.parametrer(title,msg,icone);
    }

    private void erreurTechnique(String errorContent,String title){
        // Affiche le message d’erreur et le code HTTP

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
                    showNetWork(title,finalErrorContent1,R.drawable.erreur_tech_100);
                });
            }
        }
    }
}