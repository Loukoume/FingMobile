package com.credi.fing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.credi.fing.R;
import com.credi.fing.entity.TransferPayload;
import com.credi.fing.enums.TypeTransFert;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.pojo.AccountOptionsResponse;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
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

public class TransferAccordionActivity extends AppCompatActivity {

    // UI Components
    private LinearLayout mainContainer;
    private View step1View, step2View, step3View, step4View;
    private MaterialButton btnFinal;
    private ProgressBar pbLoading;
    private Context context;

    // Champs de saisie
    private TextView tvEmitterValue, tvBeneficiaryValue,tv_type;
    private TextInputEditText etAmount, etNote;
    private TextInputLayout layoutAmount, layoutNote;

    // Data
    private TransferPayload transferPayload = new TransferPayload();
    private List<AccountOption> fromAccountOptions = new ArrayList<>();
    private List<AccountOption> toAccountOptions = new ArrayList<>();
    private AccountOption selectedFromAccount;
    private AccountOption selectedToAccount;

    String[] typs={"Transfert entre mes comptes","Transfert vers un tiers"};
    TypeTransFert typeTransFert=TypeTransFert.INTERNE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_accordion);
        context = this;

        initViews();

        String type=getIntent().getStringExtra("type");
        if(Objects.equals(type,"interne")){
            typeTransFert=TypeTransFert.INTERNE;
            tv_type.setText(typs[0]);
        }else {
            typeTransFert=TypeTransFert.TIERS;
            tv_type.setText(typs[1]);
        }

        setupSteps();

        // Charger les données (Comptes émetteurs et bénéficiaires)
        getTemplate();

        // Initialiser le payload
        transferPayload.setTransferDate(S.dateToString(new Date(), "dd MMMM yyyy"));
    }

    private void initViews() {
        mainContainer = findViewById(R.id.main_container);
        btnFinal = findViewById(R.id.btn_validate_final);
        pbLoading = findViewById(R.id.pb_loading);
        tv_type=findViewById(R.id.tv_type);
        step1View = findViewById(R.id.step_emitter);
        step2View = findViewById(R.id.step_beneficiary);
        step3View = findViewById(R.id.step_amount);
        step4View = findViewById(R.id.step_note);

        btnFinal.setOnClickListener(v -> submitTransfer());
    }

    private void setupSteps() {
        // Configuration des étapes avec leurs contenus spécifiques
        setupGenericStep(step1View, 1, "Compte Émetteur", this::inflateEmitterContent);
        setupGenericStep(step2View, 2, "Bénéficiaire", this::inflateBeneficiaryContent);
        setupGenericStep(step3View, 3, "Montant", this::inflateAmountContent);
        setupGenericStep(step4View, 4, "Motif / Commentaire", this::inflateNoteContent);

        // Ouvrir la première étape par défaut
        expandStep(step1View,1);
    }

    // --- Configuration Générique des Étapes ---

    interface ContentInflator {
        void inflate(FrameLayout container);
    }

    private void setupGenericStep(View stepView, int number, String title, ContentInflator inflator) {
        TextView tvNum = stepView.findViewById(R.id.tv_step_number);
        TextView tvTitle = stepView.findViewById(R.id.tv_step_title);
        LinearLayout header = stepView.findViewById(R.id.layout_header);
        MaterialButton btnContinue = stepView.findViewById(R.id.btn_continue);
        FrameLayout contentFrame = stepView.findViewById(R.id.content_frame);

        tvNum.setText(String.valueOf(number));
        tvTitle.setText(title);

        // Injection du contenu
        inflator.inflate(contentFrame);

        // Clic sur l'entête (Accordéon)
        header.setOnClickListener(v -> toggleStep(stepView,number));

        // Clic sur Continuer
        btnContinue.setOnClickListener(v -> {
            if (validateStep(number)) {
                collapseStep(stepView,number);

                // 1. Marquer l'étape actuelle comme VALIDÉE (Coche verte)
                markStepAsCompleted(stepView);

                // 2. Ouvrir et marquer l'étape suivante comme ACTIVE (Bleu)
                if (number == 1) {
                    int numberNotValidate=firstNumberNotvalidateStep();
                    if(numberNotValidate==-1){
                        showFinalButton();
                    }else {
                        if(validateStep(number+1)){
                            expandStep(getViewAt(numberNotValidate),numberNotValidate);
                        }else {
                            expandStep(step2View,number+1);
                            markStepAsActive(step2View);
                        }
                    }

                }
                else if (number == 2) {

                    int numberNotValidate=firstNumberNotvalidateStep();
                    if(numberNotValidate==-1){
                        showFinalButton();
                    }else {
                        if(validateStep(number+1)){
                            expandStep(getViewAt(numberNotValidate),numberNotValidate);
                        }else {
                            expandStep(step3View,number+1);
                            markStepAsActive(step3View);
                        }
                    }
                }
                else if (number == 3) {

                    int numberNotValidate=firstNumberNotvalidateStep();
                    if(numberNotValidate==-1){
                        showFinalButton();
                    }else {
                        if(validateStep(number+1)){
                            expandStep(getViewAt(numberNotValidate),numberNotValidate);
                        }else {
                            expandStep(step4View,number+1);
                            markStepAsActive(step4View);
                        }
                    }
                }
                else if (number == 4) {
                    // Fin
                    int numberNotValidate=firstNumberNotvalidateStep();
                    if(numberNotValidate==-1){
                        showFinalButton();
                    }else {
                        expandStep(getViewAt(numberNotValidate),numberNotValidate);
                    }
                }
            }
        });
    }

    private View getViewAt(int numberNotValidate) {
        switch (numberNotValidate){
            case 1:
                return step1View;
            case 2:
                return step2View;
            case 3:
                return step3View;
            default:
                return step4View;
        }
    }

    private void showFinalButton(){
        btnFinal.setVisibility(View.VISIBLE);
        TransitionManager.beginDelayedTransition(mainContainer);
    }

    // --- Contenus Spécifiques (Inflation) ---

    private void inflateEmitterContent(FrameLayout container) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_step_selector, container, false);
        tvEmitterValue = v.findViewById(R.id.tv_selection_value);
        tvEmitterValue.setText("Sélectionner un compte");
        v.findViewById(R.id.layout_selector_click).setOnClickListener(view -> showEmitterDialog());
        container.addView(v);
    }

    private void inflateBeneficiaryContent(FrameLayout container) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_step_selector, container, false);
        tvBeneficiaryValue = v.findViewById(R.id.tv_selection_value);
        tvBeneficiaryValue.setText("Sélectionner un bénéficiaire");
        v.findViewById(R.id.layout_selector_click).setOnClickListener(view -> showBeneficiaryDialog());
        container.addView(v);
    }

    private void inflateAmountContent(FrameLayout container) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_step_input, container, false);
        layoutAmount = v.findViewById(R.id.input_layout);
        layoutAmount.setHint("Montant (XOF)");
        etAmount = v.findViewById(R.id.et_input_value);
        etAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        container.addView(v);
    }

    private void inflateNoteContent(FrameLayout container) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_step_input, container, false);
        layoutNote = v.findViewById(R.id.input_layout);
        layoutNote.setHint("Description");
        etNote = v.findViewById(R.id.et_input_value);
        etNote.setInputType(InputType.TYPE_CLASS_TEXT);
        container.addView(v);
    }

    // --- Dialogues de Sélection ---

    private void showEmitterDialog() {
        if (fromAccountOptions == null || fromAccountOptions.isEmpty()) {
            S.toast(context, "Chargement des comptes en cours...");
            return;
        }

        Attribut attribut = new Attribut();
        attribut.setSubLabel("clientName");

        showSelectDialogue(fromAccountOptions, null, "accountNo", attribut, (option) -> {
            selectedFromAccount = option;
            tvEmitterValue.setText(option.getAccountNo() + " - " + option.getAccountType().getValue());

            // Mise à jour Payload
            transferPayload.setFromOfficeId(option.getOfficeId());
            transferPayload.setFromClientId(option.getClientId());
            transferPayload.setFromAccountType(option.getAccountType().getIdServeur());
            transferPayload.setFromAccountId(option.getAccountId());

            updateSubtitle(step1View, option.getAccountNo());
        });
    }

    private void showBeneficiaryDialog() {
        if (toAccountOptions == null || toAccountOptions.isEmpty()) {
            S.toast(context, "Aucun bénéficiaire disponible");
            return;
        }

        Attribut attribut = new Attribut();
        attribut.setSubLabel("clientName");

        showSelectDialogue(toAccountOptions, null, "accountNo", attribut, (option) -> {
            selectedToAccount = option;
            tvBeneficiaryValue.setText(option.getAccountNo() + " - " + option.getClientName());

            // Mise à jour Payload
            transferPayload.setToOfficeId(option.getOfficeId());
            transferPayload.setToClientId(option.getClientId());
            transferPayload.setToAccountType(option.getAccountType().getIdServeur());
            transferPayload.setToAccountId(option.getAccountId());

            updateSubtitle(step2View, option.getClientName());
        });
    }

    // Helper pour votre SelectService existant
    interface SelectionCallback {
        void onSelected(AccountOption option);
    }

    private void showSelectDialogue(List<AccountOption> data, Object sec, String label, Attribut attribut, SelectionCallback callback) {
        // Adaptez cette méthode à la signature exacte de votre SelectService dans le projet
        // Ceci est basé sur vos fichiers existants (RemboursementActivity / SenderFragment)

        View dialogView = Ut.getView(context, R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        SelectService selectService = new SelectService(context, new ArrayList<>(data), label, attribut,
                (d, v, k) -> {
                    d.cancel();
                    AccountOption accountOption = data.get(Integer.parseInt(k + ""));
                    callback.onSelected(accountOption);
                })
                .setTitle("SÉLECTION")
                .setMultiselect(false);

        View view = selectService.view(alertDialog);
        lm.addView(view);
        alertDialog.show();
    }

    // --- Validation ---

    private boolean validateStep(int step) {
        switch (step) {
            case 1:
                if (selectedFromAccount == null) {
                   // S.toast(context, "Veuillez choisir un compte émetteur");
                    return false;
                }
                return true;
            case 2:
                if (selectedToAccount == null) {
                   // S.toast(context, "Veuillez choisir un bénéficiaire");
                    return false;
                }
                return true;
            case 3:
                String amount = etAmount.getText().toString();
                if (amount.isEmpty()) {
                    layoutAmount.setError("Montant requis");
                    return false;
                }
                transferPayload.setTransferAmount(Double.valueOf(amount));
                updateSubtitle(step3View, Ut.formatMontant(Double.valueOf(amount)));
                layoutAmount.setError(null);
                return true;
            case 4:
                String note = etNote.getText().toString();
                if (note.isEmpty()) {
                   // S.toast(context, "Veuillez éditer le motif");
                    return false;
                }
                transferPayload.setTransferDescription(note);
                updateSubtitle(step4View,  note);
                return true;
        }
        return false;
    }

    private boolean validateAllStep(){
        for(int i=1;i<5;i++)
          if(!validateStep(i))return false;
        return true;
    }
    private int firstNumberNotvalidateStep(){
        for(int i=1;i<5;i++)
            if(!validateStep(i))return i;
        return -1;
    }

    // --- Animation Accordéon ---

    private void toggleStep(View stepView,int number) {
        View body = stepView.findViewById(R.id.layout_body);
        if (body.getVisibility() == View.VISIBLE) {
            collapseStep(stepView,number);
            if(validateAllStep()){
                showFinalButton();
            }
        } else {
            // Optionnel : fermer les autres pour n'en avoir qu'un ouvert
            collapseAll();
            expandStep(stepView,number);
        }
    }

    private void expandStep(View stepView,int number) {
        View body = stepView.findViewById(R.id.layout_body);
        ImageView arrow = stepView.findViewById(R.id.iv_arrow);
        TextView num = stepView.findViewById(R.id.tv_step_number);

        TransitionManager.beginDelayedTransition(mainContainer, new AutoTransition());
        body.setVisibility(View.VISIBLE);
        arrow.setRotation(180);
        num.setText(String.valueOf(number));
        num.setBackgroundResource(R.drawable.circle_bg_blue); // Actif
    }

    private void collapseStep(View stepView,int number) {
        boolean valid=validateStep(number);
        View body = stepView.findViewById(R.id.layout_body);
        ImageView arrow = stepView.findViewById(R.id.iv_arrow);

        TransitionManager.beginDelayedTransition(mainContainer, new AutoTransition());
        body.setVisibility(View.GONE);
        arrow.setRotation(0);
        if(!valid) {
            TextView num = stepView.findViewById(R.id.tv_step_number);
            num.setBackgroundResource(R.drawable.circle_bg_gray);
        }else {
           markStepAsCompleted(stepView);
        }
    }

    private void collapseAll() {
        collapseStep(step1View,1);
        collapseStep(step2View,2);
        collapseStep(step3View,3);
        collapseStep(step4View,4);
    }


    private void updateSubtitle(View stepView, String text) {
        TextView sub = stepView.findViewById(R.id.tv_step_subtitle);
        sub.setText(text);
        sub.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    // --- API Logic (Copié de TransferActivity) ---

    private void getTemplate() {
        pbLoading.setVisibility(View.VISIBLE);
        String username = Inscription.user.getUsername(); // Ou Inscription.body.getUsername() selon votre structure
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        // Récupération Template Interne (adaptable selon besoin)
        String type = "tpt";                     // ID du client (ici 8)
        String tenant = "default";              // tenantIdentifier

        Call<AccountOptionsResponse> call =typeTransFert== TypeTransFert.TIERS? api.getTemplateTransfertExterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                type, tenant):
                api.getTemplateTransfertInterne("Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                        tenant);

        call.enqueue(new Callback<AccountOptionsResponse>() {
            @Override
            public void onResponse(Call<AccountOptionsResponse> call, Response<AccountOptionsResponse> response) {
                pbLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    AccountOptionsResponse res = response.body();

                    // Filtrer pour Savings Account comme dans TransferActivity
                    fromAccountOptions = res.getFromAccountOptions().stream().filter(
                            a -> Objects.equals(a.getAccountType().getValue(), "Savings Account")
                                    || a.getAccountType().getIdServeur() == 2
                    ).collect(Collectors.toList());

                    toAccountOptions = res.getToAccountOptions();
                } else {
                    S.toast(context, "Erreur chargement template");
                }
            }

            @Override
            public void onFailure(Call<AccountOptionsResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                S.toast(context, "Erreur réseau");
            }
        });
    }

    private void submitTransfer() {
        pbLoading.setVisibility(View.VISIBLE);
        btnFinal.setEnabled(false);

        String username = Inscription.user.getUsername();
        RetrofitClient retrofitClient = RetrofitClient.getInstance(username, Inscription.body.getPassword());
        ApiService api = retrofitClient.getFineractApi();

        Call<Object> call = api.saveTransFertInterne(
                "Basic " + okhttp3.Credentials.basic(username, Inscription.body.getPassword()),
                transferPayload, "default");

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                pbLoading.setVisibility(View.GONE);
                btnFinal.setEnabled(true);
                if (response.isSuccessful()) {
                    MonFichier.ecrire(context,"transmis","ok");
                    Dialogue.neutreDialog("Transfert réussi !", "Succès", context).show();
                    // finish(); // Optionnel : fermer l'activité
                } else {
                    try {
                        String error = response.errorBody() != null ? response.errorBody().string() : "Erreur inconnue";
                        if(error.contains("{")){
                            try {
                                ApiErrorResponse apiErrorResponse= new ApiErrorResponse().fromJs(error);
                                error= ErrorUtils.buildErrorMessage(apiErrorResponse);
                            }catch (Exception e){}
                        }
                        Dialogue.neutreDialog(error, "Erreur", context).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                btnFinal.setEnabled(true);
                Dialogue.neutreDialog(t.getMessage(), "Erreur Réseau", context).show();
            }
        });
    }

    /**
     * Marque visuellement une étape comme validée (Check vert)
     */
    private void markStepAsCompleted(View stepView) {
        TextView tvNum = stepView.findViewById(R.id.tv_step_number);

        // On enlève le texte (le chiffre "1", "2"...)
        tvNum.setText("");

        // On met l'icône de validation en background
        tvNum.setBackgroundResource(R.drawable.ic_step_validated);
    }

    /**
     * Marque une étape comme active (Bleu)
     */
    private void markStepAsActive(View stepView) {
        TextView tvNum = stepView.findViewById(R.id.tv_step_number);
        // On garde le texte existant (ex: "2")
        tvNum.setBackgroundResource(R.drawable.circle_bg_blue);
        tvNum.setTextColor(getResources().getColor(android.R.color.white));
    }
}