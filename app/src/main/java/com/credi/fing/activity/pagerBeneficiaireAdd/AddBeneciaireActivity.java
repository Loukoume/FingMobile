package com.credi.fing.activity.pagerBeneficiaireAdd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.credi.fing.R;
import com.credi.fing.activity.Inscription;
import com.credi.fing.activity.pagerAdapter.SenderFragment;
import com.credi.fing.activity.pagerAdapter.TransferPagerAdapter;
import com.credi.fing.entity.AccountTypeOption;
import com.credi.fing.entity.Beneficiary;
import com.credi.fing.entity.BeneficiaryTemplate;
import com.credi.fing.enums.TypeAdapter;
import com.credi.fing.pojo.AccountInfo;
import com.credi.fing.pojo.LoanPojo;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.EditeObject;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.publics.utils.S;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBeneciaireActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    TextView tx;
    private MaterialButton btnPrev, btnNext;
    private TransferPagerAdapter adapter;
    private TextView tvStepHeader;
    private final List<String> steps = Arrays.asList(
            "Infos générales"
    );
    AccountInfo beneficiary;
    EditeObject editeObject;
    List<Attribut> attributs;
    public List<Object> typeAccounts;
    Context context;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneciaire);
        viewPager = findViewById(R.id.view_pager);
        tvStepHeader = findViewById(R.id.tv_step_header);
        btnPrev   = findViewById(R.id.btn_prev);
        btnNext   = findViewById(R.id.btn_next);
        tx=findViewById(R.id.tx_text);
        pb=findViewById(R.id.prb);
        context=this;
        if(getIntent().hasExtra("object")){
            editeObject= (EditeObject) getIntent().getSerializableExtra("object");
        }
        if(getIntent().hasExtra("beneficiary")){
            beneficiary= (AccountInfo) getIntent().getSerializableExtra("beneficiary");
        }
        adapter = new TransferPagerAdapter(this,1, TypeAdapter.BENEFICIAIRE);
        viewPager.setAdapter(adapter);

        if(editeObject!=null){
            attributs=editeObject.getAttribute();
            typeAccounts=attributs.get(3).getValues();
            //Dialogue.neutreDialog(typeAccounts.size()+"","",context).show();
        }

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


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                tvStepHeader.setText(steps.get(pos));
                btnPrev.setEnabled(pos > 0);
                btnNext.setText(pos < steps.size() - 1 ? "Suivant" : "Terminer");
            }
        });

        getBeneficiareTemplate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void submitAllData() {
        if(ok()){
            beneficiary.setTransferLimit(4000);
            if(beneficiary.getType()!=null){
                beneficiary.setType(null);
            }
            saveBeneficiaire(beneficiary);
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
        if(getBeneficiary()!=null){
           if(beneficiary.getName()!=null&&beneficiary.getOfficeName()!=null){
               if(beneficiary.getAccountNumber()!=null){
                   return true;
               }
              er();
           }else {

               er();
              // viewPager.setCurrentItem(0, true);
           }
        }
        return false;
    }

    private void er(){
        int position = 0; // position du fragment à récupérer
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag("f" + position);
        if (fragment instanceof BeneficiaireFragment) {
            BeneficiaireFragment myFragment = (BeneficiaireFragment) fragment;
            if (myFragment.getView() != null) {
                TextInputLayout nom;
                TextInputLayout id;
                TextInputLayout nomComp,type;
                nomComp=myFragment.getView().findViewById(R.id.textFieldCompte);
                type=myFragment.getView().findViewById(R.id.textFieldType);
                nom=myFragment.getView().findViewById(R.id.textFieldNom);
                id=myFragment.getView().findViewById(R.id.textField);
                putError(nom,id,nomComp,type);
            }
        }
    }

    public AccountInfo getBeneficiary() {
        if(beneficiary==null){
            beneficiary=new AccountInfo();
        }
        return beneficiary;
    }

    public void setBeneficiary(AccountInfo beneficiary) {
        this.beneficiary = beneficiary;
    }

    public EditeObject getEditeObject() {
        return editeObject;
    }

    public void setEditeObject(EditeObject editeObject) {
        this.editeObject = editeObject;
    }

    public void updateBeneFiciaire(String key, Object value){
        if(beneficiary==null){
            beneficiary=new AccountInfo();
        }
        Object object= Ut.setField(key,beneficiary,value);
        this.beneficiary= (AccountInfo) Ut.creatObject(object, AccountInfo.class);
    }

    void saveBeneficiaire(AccountInfo loanAccount){
        showPb();
        // 1. Spécifiez vos identifiants Basic Auth
        String username = Inscription.body.getUsername();

        System.out.println("Beneficiary "+loanAccount.js());
        // 2. Obtenez l'instance de RetrofitClient
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l'appel
        String tenant = "default";              // tenantIdentifier

        Call<Object> call = api.saveBeneF("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                loanAccount, tenant);

        // 4. Exécutez l'appel de manière asynchrone
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Dialogue.neutreDialog(Ut.js(response.body()),"Information",context).show();
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
        pb.setVisibility(View.GONE);
        //vide.setVisibility(View.GONE);
    }
    
    private void putError(TextInputLayout nom,TextInputLayout id,TextInputLayout nomComp
            ,TextInputLayout type){
        if(beneficiary.getOfficeName()==null)
            id.setError("Champ obligatoir");

        if(beneficiary.getName()==null){
            nom.setError("Champ obligatoir");
        }
        if(beneficiary.getAccountNumber()==null){
            nomComp.setError("Champ obligatoir");
        }
        if(beneficiary.getAccountType()==null){
            type.setError("Champ obligatoir");
        }
    }
    List<AccountTypeOption> typeAcounts;

    public List<AccountTypeOption> getTypeAcounts() {
        return typeAcounts==null?new ArrayList<>():typeAcounts;
    }

    public void setTypeAcounts(List<AccountTypeOption> typeAcounts) {
        this.typeAcounts = typeAcounts;
    }

    void getBeneficiareTemplate() {
        // 1. Récupérez vos identifiants
        String username = Inscription.user.getUsername();
        String password = Inscription.body.getPassword();

        // 2. Instanciez RetrofitClient (préconfiguré avec Basic Auth dans le constructor)
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        // 3. Préparez l’appel
        String authHeader = "Basic " + okhttp3.Credentials.basic(username, password);
        String tenant     = "default";

        Call<BeneficiaryTemplate> call = api.getBeneficiariesTemplate(authHeader, tenant);

        // 4. Exécutez l’appel asynchrone
        call.enqueue(new Callback<BeneficiaryTemplate>() {
            @Override
            public void onResponse(Call<BeneficiaryTemplate> call, Response<BeneficiaryTemplate> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BeneficiaryTemplate template = response.body();
                    typeAcounts=template.getAccountTypeOptions();
                    // Exemple : stocker le JSON brut dans un fichier
                    MonFichier.ecrire(context, "beneficiaries_template", template.js());
                    Log.d("Template", template.toString());

                } else {
                    // Erreur serveur ou JSON malformé
                    String msg = response.message();
                    String err = response.errorBody() != null ? response.errorBody().toString() : "";
                    //Dialogue.neutreDialog(msg + " " + err, "null", context).show();
                }
            }

            @Override
            public void onFailure(Call<BeneficiaryTemplate> call, Throwable t) {
                // Problème réseau ou exception
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            // Dialogue.neutreDialog(t.toString(), "", context).show();
                        });
                    }
                }

            }
        });
    }

}