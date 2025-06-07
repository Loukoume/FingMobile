package com.credi.fings.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fings.MainActivity;
import com.credi.fings.R;
import com.credi.fings.binder.LoanAccountBinder;
import com.credi.fings.entity.Client;
import com.credi.fings.main.SectionsPagerAdapter;
import com.credi.fings.pojo.LoanPojo;
import com.credi.fings.pojo.LoanProductResponse;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.service.ApiService;
import com.credi.fings.publics.service.RetrofitClient;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Dialogue;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if(getIntent().hasExtra("editeObject")){
            String js=getIntent().getStringExtra("editeObject");
            if(js!=null){
                LoanPojo loan= (LoanPojo) Ut.fromJs(js,LoanPojo.class);
                if(loan!=null){
                    if (loan != null) {
                        // Numéro de compte
                        tvAccountNumberValue.setText(loan.getAccountNo());

                        // Produit de crédit (nom du produit)
                        tvLoanProductValue.setText(
                                loan.getProductName() != null
                                        ? loan.getProductName()
                                        : loan.getShortProductName()
                        );

                        // Objet du prêt (ID à traduire en texte selon votre logique métier)
                        // Par exemple, si vous avez une Map<Integer, String> loanPurposeMap :
                        // tvLoanPurposeValue.setText( loanPurposeMap.get(loan.getLoanPurposeId()) );
                        tvLoanPurposeValue.setText(
                                loan.getLoanPurposeId() != null
                                        ? String.valueOf(loan.getLoanPurposeId())
                                        : "-"
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
                           saveLoan(loan);
                       }
                   });
                    buttonEditLoan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<String> comptes=loanAccounts.stream().map(ac->Ut.getValue(ac,"accountNo")+"").collect(Collectors.toList());
                            LoanAccountBinder pretBinder=new LoanAccountBinder();
                            EditeObject editeObject=pretBinder.editeObject(MainActivity.loanProductResponse,comptes);
                            editeObject.setObject(loan);

                            startActivity(new Intent(context, AddActivity.class)
                                    .putExtra("object",editeObject));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
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
                    //   Dialogue.neutreDialog(Ut.js(loanAccount)+" "+response.code(),response.errorBody()+"",context).show();
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();

                Dialogue.neutreDialog(t+"   "+Ut.js(loanAccount),"",context).show();
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

                } else {
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