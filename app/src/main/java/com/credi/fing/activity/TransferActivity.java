package com.credi.fing.activity;


import static android.view.View.GONE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager2.widget.ViewPager2;

import com.credi.fing.R;
import com.credi.fing.activity.pagerAdapter.TransferPagerAdapter;
import com.credi.fing.entity.SavingsTransferPayload;
import com.credi.fing.entity.TransferPayload;
import com.credi.fing.enums.TypeAdapter;
import com.credi.fing.enums.TypeTransFert;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.pojo.AccountOptionsResponse;
import com.credi.fing.pojo.LoanProductResponse;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.composant.NetworkCp;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    TextView tx;
    private MaterialButton btnPrev, btnNext;
    private TransferPagerAdapter adapter;
    private TextView tvStepHeader,tv_type;
    private final List<String> steps = Arrays.asList(
            "Émetteur", "Bénéficiaire", "Montant","Détail du transfert"
    );
    public TransferPayload transferPayload;
    Context context;
    ProgressBar pb;
    View vide;
    ImageView type_tr_icone;
    LinearLayout button_bar2,button_bar,network;
    String[] typs={"Transfert entre mes comptes","Transfert vers un tiers"};
    TypeTransFert typeTransFert=TypeTransFert.INTERNE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        viewPager = findViewById(R.id.view_pager);
        tvStepHeader = findViewById(R.id.tv_step_header);
        btnPrev   = findViewById(R.id.btn_prev2);
        tv_type=findViewById(R.id.tv_type);
        btnNext   = findViewById(R.id.btn_next2);
        tx=findViewById(R.id.tx_text);
        type_tr_icone=findViewById(R.id.type_tr_icone);
        vide=findViewById(R.id.vde);
        network=findViewById(R.id.network);
        vide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pb=findViewById(R.id.pb);
        button_bar2=findViewById(R.id.button_bar2);
        button_bar=findViewById(R.id.button_bar);
        button_bar.setVisibility(GONE);button_bar2.setVisibility(View.VISIBLE);
        tx.setText("Transfert d'argent".toUpperCase());

        String type=getIntent().getStringExtra("type");
        if(Objects.equals(type,"interne")){
           typeTransFert=TypeTransFert.INTERNE;
            tv_type.setText(typs[0]);
        }else {
            typeTransFert=TypeTransFert.TIERS;
            tv_type.setText(typs[1]);
        }

        /*type_tr_icone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop=S.popupMenu(v,typs);
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        tv_type.setText(typs[item.getItemId()-1]);
                        if(item.getItemId()==1){
                            if(typeTransFert!=TypeTransFert.INTERNE){
                                typeTransFert=TypeTransFert.INTERNE;
                                transferPayload.setToAccountId(null);
                                transferPayload.setToOfficeId(null);
                                transferPayload.setToAccountType(null);
                                transferPayload.setToAccountType(null);
                                viewPager.setCurrentItem(0);
                                viewPager.setCurrentItem(1);
                            }
                        }else {
                            if(typeTransFert!=TypeTransFert.TIERS)
                            {
                                typeTransFert=TypeTransFert.TIERS;
                                transferPayload.setToAccountId(null);
                                transferPayload.setToOfficeId(null);
                                transferPayload.setToAccountType(null);
                                transferPayload.setToAccountType(null);
                                viewPager.setCurrentItem(0);
                                viewPager.setCurrentItem(1);
                            }
                        }

                        return false;
                    }
                });
            }
        });
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_tr_icone.performClick();
            }
        });*/
        context=this;
        adapter = new TransferPagerAdapter(this,4, TypeAdapter.TRANSFERT);
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
    private List<AccountOption> toTiersAccountOptions;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public List<AccountOption> getFromAccountOptions() {
        if(fromAccountOptions==null){
            fromAccountOptions=new ArrayList<>();
            return fromAccountOptions;
        }
        return fromAccountOptions.stream().filter(
                a-> Objects.equals(a.getAccountType().getValue(),"Savings Account")
                ||a.getAccountType().getIdServeur()==2
        ).collect(Collectors.toList());
    }

    public void setFromAccountOptions(List<AccountOption> fromAccountOptions) {
        this.fromAccountOptions = fromAccountOptions;
    }

    public List<AccountOption> getToAccountOptions() {
        if(toAccountOptions==null){
            toAccountOptions=new ArrayList<>();
            return toAccountOptions;
        }
        return toAccountOptions;
    }

    public void setToAccountOptions(List<AccountOption> toAccountOptions) {
        this.toAccountOptions = toAccountOptions;
    }

    private void submitAllData() {
        if(ok()){
            transferPayload.setTransferDate(S.dateToString(new Date(),
                    transferPayload.getDateFormat(),transferPayload.getLocale()));
            /*Dialogue.neutreDialog(Ut.js(transferPayload),S.dateToString(new Date(),
                    transferPayload.getDateFormat()),context).show();*/
           // System.out.println(" -transferPayload- "+transferPayload.js());
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
            transferPayload.setTransferDate(S.dateToString(new Date(),
                    transferPayload.getDateFormat(),transferPayload.getLocale()));
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

    void saveTransfert(TransferPayload transferPayload){
        MonFichier.ecrire(context,"transmis","");
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();

        System.out.println(" TransferPayload => "+Ut.js(transferPayload));

        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String tenant = "default";              // tenantIdentifier
        String tpt="tpt?";

        Call<Object> call =typeTransFert==TypeTransFert.TIERS? api.saveTransFertExterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                transferPayload,tpt, tenant):
                api.saveTransFertInterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                        transferPayload,tenant) ;

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MonFichier.ecrire(context,"transmis","ok");
                    Dialogue.neutreDialogF("Transfert effectué avec succè","Infermation",context,
                            (Activity) context).show();
                   // finish();
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
                               try {
                                   ApiErrorResponse apiErrorResponse=
                                           new ApiErrorResponse().fromJs(finalErrorContent);
                                   finalErrorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                               }catch (Exception e){

                               }
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

    void saveTransfert_(TransferPayload transferPayload) {
        showPb();
        String username = Inscription.body.getUsername();
        String password = Inscription.body.getPassword();
        String authHeader = "Basic " + Credentials.basic(username, password);

        // 1) Récupérez correctement fromAccountId
        long fromAccountId = transferPayload.getFromAccountId(); // ← 74

        // 2) Convertissez le payload
        SavingsTransferPayload savingsPayload = toSavingsTransferPayload(transferPayload);

        // 3) Construisez Retrofit et l’interface
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 4) Passez fromAccountId dans le Path, pas le toAccountId
        Call<Object> call = api.transferSavings(
                authHeader,
                fromAccountId,            // 74 et non 77
                "transfer",
                "default",
                savingsPayload
        );

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                hidePb();
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


    void saveTransfert2(SavingsTransferPayload payload) {
        MonFichier.ecrire(context, "transmis", "");
        showPb();

        String username = Inscription.body.getUsername();
        String password = Inscription.body.getPassword();
        String authHeader = "Basic " + okhttp3.Credentials.basic(username, password);

        // 1) Construisez le payload
        //    (vous l'avez déjà rempli avant d'appeler cette méthode)

        // 2) Obtenez l'instance Retrofit
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3) Préparez l'appel
        long fromAccountId = payload.getToSavingsAccountId(); // attention : ici c'est le compte source
        // si votre payload ne contient que toSavingsAccountId, passez le fromAccountId en param séparé
        Call<Object> call = api.transferSavings(
                authHeader,
                fromAccountId,            // ex. 74L
                "transfer",               // commande transfer
                "default",                // tenantIdentifier
                payload                   // corps JSON
        );

        // 4) Exécutez l'appel
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                hidePb();
                if (response.isSuccessful() && response.body() != null) {
                    MonFichier.ecrire(context, "transmis", "ok");
                    finish();
                } else {
                    String errorContent = "Corps de l’erreur vide";
                    try {
                        if (response.errorBody() != null) {
                            errorContent = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            String finalError = errorContent;
                            if (finalError.contains("{")) {
                                try {
                                    ApiErrorResponse err = new ApiErrorResponse().fromJs(finalError);
                                    finalError = ErrorUtils.buildErrorMessage(err);
                                } catch (Exception ignored) {}
                            }
                            String finalError1 = finalError;
                            activity.runOnUiThread(() ->
                                    Dialogue.neutreDialog(finalError1,
                                            "Code d'erreur : " + response.code(),
                                            context).show()
                            );
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                hidePb();
                Log.e("TransferSavings", "Erreur onFailure", t);
                String stackTrace = Log.getStackTraceString(t);
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() ->
                                Dialogue.neutreDialog(stackTrace, "Echec réseau / parsing", context).show()
                        );
                    }
                }
            }
        });
    }


    /**
     * Convertit un TransferPayload (votre POJO existant) en SavingsTransferPayload
     */
    public SavingsTransferPayload toSavingsTransferPayload(TransferPayload tp) {
        if (tp == null) throw new IllegalArgumentException("TransferPayload ne doit pas être null");
        SavingsTransferPayload sp = new SavingsTransferPayload();
        sp.setLocale(tp.getLocale());
        sp.setDateFormat(tp.getDateFormat());
        sp.setTransferDate(tp.getTransferDate());
        sp.setTransferAmount(BigDecimal.valueOf(tp.getTransferAmount()));
        sp.setToSavingsAccountId(Long.valueOf(tp.getToAccountId()));
        sp.setClientId(tp.getFromClientId());         // <— on alimente ici
        sp.setTransferDescription(tp.getTransferDescription());
        return sp;
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
        String type = "tpt";                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<AccountOptionsResponse> call =typeTransFert==TypeTransFert.TIERS? api.getTemplateTransfertExterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                type, tenant):
                api.getTemplateTransfertInterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                         tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<AccountOptionsResponse>() {
            @Override
            public void onResponse(Call<AccountOptionsResponse> call, Response<AccountOptionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accountOptionsResponse = response.body();
                    toAccountOptions=accountOptionsResponse.getToAccountOptions();
                    toTiersAccountOptions=accountOptionsResponse.getToTiersAccountOptions();
                    fromAccountOptions=accountOptionsResponse.getFromAccountOptions();
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

                    erreurTechnique(errorContent,"Erreur technique");

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
                            if(titre[0].contains("java.net")|| titre[0].contains("javax.net"))
                            {
                                titre[0] ="Vérifier votre connexion internet et réessayer";
                                showNetWork("Problème de connexion",titre[0],
                                        R.drawable.wifi_100);
                            }else {
                                showNetWork("Erreur technique",t.getMessage(),
                                        R.drawable.erreur_tech_100);
                            }
                        });
                    }
                }
            }
        });
    }

    private void showNetWork(String title,String msg,int icone){
        NetworkCp networkCp=new NetworkCp(context,network,(o, k)->{
            if(k==1){
                getTemplate();
                network.setVisibility(View.GONE);
            }else {
                finish();
            }
        });
        networkCp.parametrer(title,msg,icone);
    }

    private void erreurTechnique(String errorContent,String title){
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
                    showNetWork(title,finalErrorContent1,R.drawable.erreur_tech_100);
                });
            }
        }
    }
}