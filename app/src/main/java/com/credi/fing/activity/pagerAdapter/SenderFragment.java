package com.credi.fing.activity.pagerAdapter;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.credi.fing.R;
import com.credi.fing.activity.Beneficiaire;
import com.credi.fing.activity.TransferActivity;
import com.credi.fing.enums.TypeTransFert;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.SelectService;
import com.credi.fing.publics.service.impl.SingleInputService;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.service.impl.UtilsInput;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SenderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static SenderFragment newInstance(String param1, String param2) {
        SenderFragment fragment = new SenderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Références UI existantes
    private TextInputLayout input, fieldNom, textFieldCpEmt;
    private TextInputEditText nom, id, idCpEmt;

    private TransferActivity activity;

    private TextInputLayout inputBen, fieldNomBen;
    private TextInputEditText nomBen, idBen;
    private LinearLayout lbene;

    // Attributs pour SingleInputService
    private Attribut attributFrom;
    private Attribut attributAmount;
    private Attribut attributBen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sender, container, false);
    }
    private void forceNumericKeyboard(TextInputEditText et) {
        if (et == null) return;

        // Clavier numérique avec décimales
        et.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        // Autoriser à la fois "." et "," suivant la locale/clavier
        et.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));

        // Optionnels mais utiles
        et.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        if (Build.VERSION.SDK_INT >= 21) {
            et.setShowSoftInputOnFocus(true);
        }

        // Forcer l’ouverture du clavier quand le champ prend le focus
        et.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                et.post(() -> {
                    InputMethodManager imm = (InputMethodManager) requireContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                });
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        activity = (TransferActivity) requireActivity();

        // ---- Bind des vues du layout fourni ----
        input         = root.findViewById(R.id.textField);       // TIL compte émetteur
        fieldNom      = root.findViewById(R.id.textFieldNom);    // TIL nom complet émetteur
        id            = root.findViewById(R.id.id);              // ET compte émetteur
        nom           = root.findViewById(R.id.et_full_name);    // ET nom complet émetteur (read-only)

        textFieldCpEmt = root.findViewById(R.id.textFieldCpEmt); // TIL montant
        idCpEmt        = root.findViewById(R.id.idCpEmt);        // ET montant

        lbene       = root.findViewById(R.id.lbene);
        inputBen    = lbene.findViewById(R.id.textField);        // TIL compte bénéficiaire (include)
        fieldNomBen = root.findViewById(R.id.textFieldNom);      // TIL nom complet bénéficiaire
        idBen       = lbene.findViewById(R.id.id);               // ET compte bénéficiaire (include)
        nomBen      = root.findViewById(R.id.nom);               // ET nom complet bénéficiaire (read-only)

        // Nom complet ÉMETTEUR (TIL: fieldNom, ET: et_full_name)

        // Nom complet ÉMETTEUR (TIL: fieldNom, ET: et_full_name)
        UtilsInput.setupMaterialInput(
                root,
                requireContext(),
                /*required*/ false,
                /*errorText*/ null,
                /*initialValue*/ null,
                R.id.fieldNom,
                R.id.et_full_name
        );

// Nom complet BÉNÉFICIAIRE (TIL: textFieldNom, ET: nom)
        UtilsInput.setupMaterialInput(
                root,
                requireContext(),
                /*required*/ false,
                /*errorText*/ null,
                /*initialValue*/ null,
                R.id.textFieldNom,
                R.id.nom
        );

        // ===========================================
        // 1) Compte ÉMETTEUR (oneSelect) en injection
        // ===========================================
        attributFrom = new Attribut();
        attributFrom.setName("Numéro du compte");
        attributFrom.setType("oneSelect");
        attributFrom.setLabel("accountNo");
        attributFrom.setSubLabel("clientName");
        if (activity.getFromAccountOptions() != null) {
            attributFrom.setValues(new ArrayList<>(activity.getFromAccountOptions()));
        }
        // Optionnel: activer la validation "requis" au blur (si dispo dans Attribut)
        // attributFrom.setRequierd(true);

        new SingleInputService<>(
                requireContext(), Object.class, new Object(),
                "fromAccountNo", attributFrom
        ).withRoot(input)                         // on réutilise la TIL existante comme racine
                .withIds(R.id.textField, R.id.id, R.id.ups, null)
                .build();                                // UtilsInput s’attache et gère couleurs vide/rempli/focus/erreur

        // Événements sélection (logique existante conservée)
        if (activity.getTransferPayload() != null) {
            id.setOnClickListener(v ->
                    showSelectDialogue(activity.getFromAccountOptions(), null, "accountNo", "accountNo", id, attributFrom)
            );
            nom.setOnClickListener(v ->
                    showSelectDialogue(activity.getFromAccountOptions(), null, "accountNo", "accountNo", id, attributFrom)
            );
        }

        // ==============================
        // 2) MONTANT (number) injection
        // ==============================
        attributAmount = new Attribut();
        attributAmount.setName("Montant");
        attributAmount.setType("number");
        // attributAmount.setRequierd(true); // si tu veux l’erreur auto "Champs obligatoire" au blur

       /* new SingleInputService<>(
                requireContext(), Object.class, new Object(),
                "transferAmount", attributAmount
        ).withRoot(textFieldCpEmt)
                .withIds(R.id.textFieldCpEmt,
                         R.id.idCpEmt,
                        null,
                        null)
                .build();*/

        // Conserver la mise à jour du payload comme avant
        idCpEmt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                String value = s == null ? "" : s.toString();
                if (!value.isEmpty()) {
                    textFieldCpEmt.setError(null); // efface l’erreur si tu en affiches une
                    activity.updateTransferPayload("transferAmount", value);
                } else {
                    activity.updateTransferPayload("transferAmount", null);
                }
            }
        });

        // ===========================================
        // 3) Compte BÉNÉFICIAIRE (oneSelect) injection
        // ===========================================
        attributBen = new Attribut();
        attributBen.setName("Numéro du compte");
        attributBen.setType("oneSelect");
        attributBen.setLabel("accountNo");
        attributBen.setSubLabel("clientName");
        if (activity.getToAccountOptions() != null) {
            attributBen.setValues(new ArrayList<>(activity.getToAccountOptions()));
        }
        // attributBen.setRequierd(true);

        new SingleInputService<>(
                requireContext(), Object.class, new Object(),
                "toAccountNo", attributBen
        ).withRoot(inputBen)
                .withIds(R.id.textField, R.id.id, R.id.ups, null)
                .build();

        if (activity.getTransferPayload() != null) {
            idBen.setOnClickListener(v ->
                    showSelectDialogueBene(activity.getToAccountOptions(), null, "accountNo", "acountNo", idBen, attributBen)
            );
            nomBen.setOnClickListener(v ->
                    showSelectDialogueBene(activity.getToAccountOptions(), null, "accountNo", "acountNo", idBen, attributBen)
            );
        }

        forceNumericKeyboard(idCpEmt);
    }

    // =======================
    // Sélections (inchangées)
    // =======================

    private void showSelectDialogue(final List<AccountOption> data, Object sec, String label, String field,
                                    TextInputEditText editText, Attribut attribut) {
        activity.hidKey();
        View dialogView = Ut.getView(getContext(), R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();

        List<Object> selection = (sec == null) ? new ArrayList<>() : (List<Object>) sec;

        SelectService selectService = new SelectService(getContext(), new ArrayList<>(data), label, attribut,
                (d, v, k) -> {
                    d.cancel();
                    AccountOption accountOption = data.get(Integer.parseInt(k + ""));
                    id.setText(accountOption.getAccountNo());         // déclenche UtilsInput (état rempli)
                    nom.setText(accountOption.getClientName());

                    activity.updateTransferPayload("fromOfficeId", accountOption.getOfficeId());
                    activity.updateTransferPayload("fromClientId", accountOption.getClientId());
                    activity.updateTransferPayload("fromAccountType", accountOption.getAccountType().getIdServeur());
                    activity.updateTransferPayload("fromAccountId", accountOption.getAccountId());
                    activity.setFromAccountOption(accountOption);
                    //activity.setCurrentePage(1);
                })
                .setTitle("Compte émetteur".toUpperCase())
                .setMultiselect(false)
                .setSelect(selection);

        View content = selectService.view(alertDialog);
        lm.addView(content);
        alertDialog.show();
    }

    private void showSelectDialogueBene(final List<AccountOption> data, Object sec, String label, String field,
                                        TextInputEditText editText, Attribut attribut) {
        activity.hidKey();
        View dialogView = Ut.getView(getContext(), R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        if (activity.getTypeTransFert() == TypeTransFert.TIERS) {
            MaterialButton add = dialogView.findViewById(R.id.outlinedButton);
            LinearLayout espace = dialogView.findViewById(R.id.espace);
            LinearLayout ladd = dialogView.findViewById(R.id.add_bouton);
            if (ladd != null) ladd.setVisibility(VISIBLE);
            if (espace != null) espace.setVisibility(VISIBLE);
            if (add != null) {
                add.setText("Plus");
                add.setOnClickListener(v -> {
                    activity.startActivity(new Intent(getContext(), Beneficiaire.class)
                            .putExtra("add", "add")
                            .putExtra("titre", "Bénéficiaires"));
                    activity.finish();
                });
            }
        }

        final AlertDialog alertDialog = builder.create();

        List<Object> selection = (sec == null) ? new ArrayList<>() : (List<Object>) sec;

        SelectService selectService = new SelectService(getContext(), new ArrayList<>(data), label, attribut,
                (d, v, k) -> {
                    d.cancel();
                    int index = Integer.parseInt(k + "");
                    AccountOption accountOption = data.get(index);

                    // ✅ appliquer sur le TextInputEditText passé en paramètre
                    if (editText != null) {
                        editText.setText(accountOption.getAccountNo()); // déclenche UtilsInput (état rempli)
                    }

                    // Mise à jour éventuelle du champ "nom" si présent dans le fragment
                    if (nomBen != null) {
                        nomBen.setText(accountOption.getClientName());
                    }

                    // Payload/app state (inchangé)
                    activity.updateTransferPayload("toOfficeId",  accountOption.getOfficeId());
                    activity.updateTransferPayload("toClientId",  accountOption.getClientId());
                    activity.updateTransferPayload("toAccountType", accountOption.getAccountType().getIdServeur());
                    activity.updateTransferPayload("toAccountId", accountOption.getAccountId());
                    activity.setToAccountOption(accountOption);
                })
                .setTitle("Compte bénéficiaire".toUpperCase())
                .setMultiselect(false)
                .setSelect(selection);

        View content = selectService.view(alertDialog);
        lm.addView(content);

        alertDialog.show();
    }

}
