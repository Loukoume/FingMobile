package com.credi.fing.activity.pagerBeneficiaireAdd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.credi.fing.publics.composant.NetworkCp;
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
    LinearLayout network;
    ProgressBar pb;
    View vide;
    boolean update;
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
        vide=findViewById(R.id.vide);
        network=findViewById(R.id.network);
        context=this;
        if(getIntent().hasExtra("object")){
            editeObject= (EditeObject) getIntent().getSerializableExtra("object");
            Object object=editeObject.getObject();
            Beneficiary bn= (Beneficiary) Ut.creatObject(object, Beneficiary.class);
            if(bn!=null){
                Object ids=Ut.getValue(object,"id");
                update=ids!=null&&!ids.toString().isEmpty();
                beneficiary=new AccountInfo().toAccountInfo(bn);
            }
        }
        if(getIntent().hasExtra("beneficiary")){
            Beneficiary bn= (Beneficiary) getIntent().getSerializableExtra("beneficiary");
            if(bn!=null){
                beneficiary=new AccountInfo().toAccountInfo(bn);
            }
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
            beneficiary.setLocale("en_GB");
            if(beneficiary.getType()!=null){
                beneficiary.setType(null);
            }
            //Dialogue.neutreDialog(Ut.js(beneficiary)+"",update+"",context).show();
            if(update){
                Object object=editeObject.getObject();
                Object ids=Ut.getValue(object,"id");
                if(ids!=null){
                   modifierBeneficiaire(Long.parseLong(ids.toString()),beneficiary);
                }
            }else {
                saveBeneficiaire(beneficiary);
            }
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
        if(loanAccount==null){
            return;
        }
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
                    S.toast(context,"Enregistré avec succès");
                    MonFichier.ecrire(context,"refresh","ok");
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

                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }
                    int httpCode = response.code();
                    erreurTechnique(errorContent,httpCode);

                }
                hidePb();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            if(titre[0].contains("{")){
                                try {
                                    ApiErrorResponse apiErrorResponse=
                                            new ApiErrorResponse().fromJs(titre[0]);
                                    titre[0]= ErrorUtils.buildErrorMessage(apiErrorResponse);
                                }catch (Exception e){

                                }
                            }
                            int pseudoCode = S.mapThrowableToCode(t);
                            erreurTechnique(titre[0],pseudoCode);
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
        pb.setVisibility(View.GONE);
        vide.setVisibility(View.GONE);
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
        showPb();
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

                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }
                    int httpCode = response.code();
                    erreurTechnique(errorContent,httpCode);
                }
                hidePb();
            }

            @Override
            public void onFailure(Call<BeneficiaryTemplate> call, Throwable t) {
                // Problème réseau ou exception
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            if(titre[0].contains("{")){
                                try {
                                    ApiErrorResponse apiErrorResponse=
                                            new ApiErrorResponse().fromJs(titre[0]);
                                    titre[0]= ErrorUtils.buildErrorMessage(apiErrorResponse);
                                }catch (Exception e){

                                }
                            }
                            int pseudoCode = S.mapThrowableToCode(t);
                            erreurTechnique(titre[0],pseudoCode);
                        });
                    }
                }

            }
        });
    }

    private void showNetWork(String msg,int code){
        Dialogue.dialog(msg,code,context).show();
    }

    private void erreurTechnique(String errorContent,int code){
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
                    showNetWork(finalErrorContent1,code);
                });
            }
        }
    }

    void modifierBeneficiaire(long beneficiaryId, AccountInfo updateRequest) {
        String tenant = "default";
        showPb();

        String username = Inscription.user.getUsername();
        String password = Inscription.body.getPassword();

        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, password);
        ApiService api = retrofitClient.getFineractApi();

        Call<Object> call = api.updateBeneficiary(beneficiaryId, tenant, updateRequest);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                hidePb();
                if (response.isSuccessful() && response.body() != null) {
                    // mise à jour OK
                    S.toast(context,"Enregistré avec succès");
                    MonFichier.ecrire(context,"refresh","ok");
                    finish();
                } else {
                    // erreur côté serveur
                    String errorContent;
                    try {
                        errorContent = response.errorBody() != null && !response.errorBody().string().isEmpty()
                                ? response.errorBody().string()
                                : "Corps de l’erreur vide";
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorContent = "Impossible de lire le contenu de l’erreur";
                    }
                    System.out.println("Erreur updateBeneficiaire: " + errorContent);
                    if(errorContent.contains("{")){
                        try {
                            ApiErrorResponse apiErrorResponse=
                                    new ApiErrorResponse().fromJs(errorContent);
                            errorContent= ErrorUtils.buildErrorMessage(apiErrorResponse);
                        }catch (Exception e){

                        }
                    }
                    int httpCode = response.code();
                    erreurTechnique(errorContent,httpCode);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                hidePb();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    final String[] titre = {t.getMessage()};
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            if(titre[0].contains("{")){
                                try {
                                    ApiErrorResponse apiErrorResponse=
                                            new ApiErrorResponse().fromJs(titre[0]);
                                    titre[0]= ErrorUtils.buildErrorMessage(apiErrorResponse);
                                }catch (Exception e){

                                }
                            }
                            int pseudoCode = S.mapThrowableToCode(t);
                            erreurTechnique(titre[0],pseudoCode);
                        });
                    }
                }
            }
        });
    }

}