package com.credi.fing.activity;


import static android.view.View.GONE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.credi.fing.R;
import com.credi.fing.activity.pagerAdapter.TransferPagerAdapter;
import com.credi.fing.entity.TransferPayload;
import com.credi.fing.enums.TypeAdapter;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.pojo.AccountOptionsResponse;
import com.credi.fing.pojo.LoanProductResponse;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    TextView tx;
    private MaterialButton btnPrev, btnNext;
    private TransferPagerAdapter adapter;
    private TextView tvStepHeader;
    private final List<String> steps = Arrays.asList(
            "Émetteur", "Bénéficiaire", "Montant"
    );
    public TransferPayload transferPayload;
    Context context;
    ProgressBar pb;
    LinearLayout button_bar2,button_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        viewPager = findViewById(R.id.view_pager);
        tvStepHeader = findViewById(R.id.tv_step_header);
        btnPrev   = findViewById(R.id.btn_prev2);
        btnNext   = findViewById(R.id.btn_next2);
        tx=findViewById(R.id.tx_text);
        pb=findViewById(R.id.pb);
        button_bar2=findViewById(R.id.button_bar2);
        button_bar=findViewById(R.id.button_bar);
        button_bar.setVisibility(GONE);button_bar2.setVisibility(View.VISIBLE);
        tx.setText("Transfert d'argent".toUpperCase());
       /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });*/
        //mort.setVisibility(GONE);
        // 1) Pager + Adapter

        context=this;
        adapter = new TransferPagerAdapter(this,3, TypeAdapter.TRANSFERT);
        viewPager.setAdapter(adapter);
//        configureStepView();


        // 3) Navigation des boutons
        btnPrev.setOnClickListener(v -> {
            int p = viewPager.getCurrentItem();
            if (p > 0) viewPager.setCurrentItem(p - 1, true);
        });
        btnNext.setOnClickListener(v -> {
            int p = viewPager.getCurrentItem();
            if (p < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(p + 1, true);
            } else {
                submitAllData();
            }
        });

        // 4) Mise à jour des boutons selon la page
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                tvStepHeader.setText(steps.get(pos));
                btnPrev.setEnabled(pos > 0);
                btnNext.setText(pos < steps.size() - 1 ? "Suivant" : "Terminer");
                //numero_etape.setText((pos+1)+"");
                //un_sur_total.setText((pos+1)+"/"+steps.size());
            }
        });

        getTemplate();
    }

    public void setCurrentePage(int index){
       viewPager.setCurrentItem(index,true);
    }

    private List<AccountOption> fromAccountOptions;
    private List<AccountOption> toAccountOptions;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public List<AccountOption> getFromAccountOptions() {
        return fromAccountOptions;
    }

    public void setFromAccountOptions(List<AccountOption> fromAccountOptions) {
        this.fromAccountOptions = fromAccountOptions;
    }

    public List<AccountOption> getToAccountOptions() {
        return toAccountOptions;
    }

    public void setToAccountOptions(List<AccountOption> toAccountOptions) {
        this.toAccountOptions = toAccountOptions;
    }

    private void submitAllData() {
        if(ok()){
            transferPayload.setTransferDate(S.dateToString(new Date(),
                    transferPayload.getDateFormat()));
            Dialogue.neutreDialog(Ut.js(transferPayload),S.dateToString(new Date(),
                    transferPayload.getDateFormat()),context).show();
            saveTransfert(transferPayload);
        }
    }

    boolean clikSubmit=false;

    public void updatClikSubmit(boolean clik){
        this.clikSubmit=clik;
    }

    public boolean isClikSubmit() {
        return clikSubmit;
    }

    private boolean ok(){
        updatClikSubmit(true);
        if(transferPayload!=null){
            if(transferPayload.getFromOfficeId()!=null&&transferPayload.getFromClientId()!=null){
                if(transferPayload.getToAccountId()!=null&&transferPayload.getToClientId()!=null){
                    if(transferPayload.getTransferAmount()!=null&&transferPayload.getTransferDescription()!=null
                    ){
                        return true;
                    }
                    viewPager.setCurrentItem(1, true);
                    viewPager.setCurrentItem(2, true);
                }else {
                    viewPager.setCurrentItem(1, true);
                }
            }else {
                viewPager.setCurrentItem(0, true);
            }
        }
        return false;
    }

    public TransferPayload getTransferPayload() {
        if(transferPayload==null){
            transferPayload=new TransferPayload();
        }
        return transferPayload;
    }

    public void setTransferPayload(TransferPayload transferPayload) {
        this.transferPayload = transferPayload;
    }



    public void updateTransferPayload(String key, Object value){
        if(transferPayload==null){
            transferPayload=new TransferPayload();
            transferPayload.setTransferDate(S.dateToString(new Date(),
                    transferPayload.getDateFormat()));
        }
        Object object= Ut.setField(key,transferPayload,value);
        this.transferPayload= (TransferPayload) Ut.creatObject(object, TransferPayload.class);
    }

    void saveTransfert(TransferPayload loanAccount){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String tenant = "default";              // tenantIdentifier
        String tpt="tpt";

        Call<Object> call = api.saveTransFert("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                loanAccount,tpt, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
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
                    // Affiche le message d’erreur et le code HTTP

                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            String finalErrorContent = errorContent;
                            if(finalErrorContent.contains("{")){
                                ApiErrorResponse apiErrorResponse=
                                        new ApiErrorResponse().fromJs(finalErrorContent);
                                finalErrorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                            }
                            String finalErrorContent1 = finalErrorContent;
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(
                                        finalErrorContent1,
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

    void showPb(){
        pb.setVisibility(View.VISIBLE);
        // vide.setVisibility(View.VISIBLE);
    }
    void hidePb(){
        pb.setVisibility(GONE);
        //vide.setVisibility(View.GONE);
    }

    AccountOptionsResponse accountOptionsResponse;
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

        Call<AccountOptionsResponse> call = api.getTemplateTransfert("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                clientId, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<AccountOptionsResponse>() {
            @Override
            public void onResponse(Call<AccountOptionsResponse> call, Response<AccountOptionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accountOptionsResponse = response.body();
                    toAccountOptions=accountOptionsResponse.getToAccountOptions();
                    fromAccountOptions=accountOptionsResponse.getFromAccountOptions();
                } else {
                    //client= (Client) Ut.fromJs(Json.json,Client.class);
                    //loanAccounts= (List<Object>) Ut.getValue(client,"loanAccounts");
                    // savingsAccounts= (List<Object>) Ut.getValue(client,"savingsAccounts");
                    // setComptesValues();
                    // Erreur côté serveur ou JSON non parsable
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
            public void onFailure(Call<AccountOptionsResponse> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();

            }
        });
    }
}