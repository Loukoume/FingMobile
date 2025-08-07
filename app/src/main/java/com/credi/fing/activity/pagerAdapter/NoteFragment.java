package com.credi.fing.activity.pagerAdapter;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.credi.fing.R;
import com.credi.fing.activity.TransferActivity;
import com.credi.fing.entity.TransferPayload;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.publics.service.impl.Ut;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    // Déclaration des vues
    private TextView tvLabelEmitter;
    private TextView tvLabelEmitterCompte;
    private TextView tvValueEmitterAccount;
    private TextView tvLabelEmitterName;
    private TextView tvValueEmitterName;
    private TextView tvLabelEmitterOffice;
    private TextView tvValueEmitterOffice;

    private TextView tvLabelBeneficiary;
    private TextView tvLabelBeneficiaryCompte;
    private TextView tvValueBeneficiaryAccount;
    private TextView tvLabelBeneficiaryName;
    private TextView tvValueBeneficiaryName;
    private TextView tvLabelBeneficiaryOffice;
    private TextView tvValueBeneficiaryOffice;

    private TextView tvLabelAmount;
    private TextView tvValueAmount;
    private TextView tvLabelDate;
    private TextView tvValueDate;
    private TextView tvLabelDescription;
    private TextView tvValueDescription;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bloc Émetteur
        tvLabelEmitter             = view.findViewById(R.id.tv_label_emitter);
        tvLabelEmitterCompte       = view.findViewById(R.id.tv_label_emitter_compte);
        tvValueEmitterAccount      = view.findViewById(R.id.tv_value_emitter_account);
        tvLabelEmitterName         = view.findViewById(R.id.tv_label_emitter_name);
        tvValueEmitterName         = view.findViewById(R.id.tv_value_emitter_name);
        tvLabelEmitterOffice       = view.findViewById(R.id.tv_label_emitter_office);
        tvValueEmitterOffice       = view.findViewById(R.id.tv_value_emitter_office);

        // Bloc Bénéficiaire
        tvLabelBeneficiary             = view.findViewById(R.id.tv_label_beneficiary);
        tvLabelBeneficiaryCompte       = view.findViewById(R.id.tv_label_beneficiary_compte);
        tvValueBeneficiaryAccount      = view.findViewById(R.id.tv_value_beneficiary_account);
        tvLabelBeneficiaryName         = view.findViewById(R.id.tv_label_beneficiary_name);
        tvValueBeneficiaryName         = view.findViewById(R.id.tv_value_beneficiary_name);
        tvLabelBeneficiaryOffice       = view.findViewById(R.id.tv_label_beneficiary_office);
        tvValueBeneficiaryOffice       = view.findViewById(R.id.tv_value_beneficiary_office);

        // Détails additionnels
        tvLabelAmount       = view.findViewById(R.id.tv_label_amount);
        tvValueAmount       = view.findViewById(R.id.tv_value_amount);
        tvLabelDate         = view.findViewById(R.id.tv_label_date);
        tvValueDate         = view.findViewById(R.id.tv_value_date);
        tvLabelDescription  = view.findViewById(R.id.tv_label_description);
        tvValueDescription  = view.findViewById(R.id.tv_value_description);
        activity = getActivity();
        traitement();
    }
    Activity activity;

    @Override
    public void onResume() {
        super.onResume();
        traitement();
    }

    void traitement(){
        if (activity instanceof TransferActivity) {
            TransferActivity transferActivity = (TransferActivity) activity;
            TransferPayload payload = transferActivity.getTransferPayload();
            // ← Assure-toi que TransferActivity expose un getter :
            // public TransferPayload getTransferPayload() { return this.transferPayload; }

            if (payload != null) {
                // 3. Remplissage des blocs Émetteur / Bénéficiaire
                if (transferActivity.getFromAccountOption() != null) {
                    AccountOption em = transferActivity.getFromAccountOption();
                    if (em.getAccountNo() != null)
                        tvValueEmitterAccount.setText(em.getAccountNo());
                    if (em.getClientName() != null)
                        tvValueEmitterName.setText(em.getClientName());
                    if (em.getOfficeName() != null)
                        tvValueEmitterOffice.setText(em.getOfficeName());
                }
                if (transferActivity.getToAccountOption() != null) {
                    AccountOption ben = transferActivity.getToAccountOption();
                    if (ben.getAccountNo() != null)
                        tvValueBeneficiaryAccount.setText(ben.getAccountNo());
                    if (ben.getClientName() != null)
                        tvValueBeneficiaryName.setText(ben.getClientName());
                    if (ben.getOfficeName() != null)
                        tvValueBeneficiaryOffice.setText(ben.getOfficeName());
                }
                // 4. Remplissage des détails additionnels
                // Montant
                if(payload.getTransferAmount()!=null)
                    tvValueAmount.setText(
                            Ut.formatMontant(payload.getTransferAmount())
                    );
                // Date
                if (payload.getTransferDate() != null)
                    tvValueDate.setText(payload.getTransferDate());
                // Description
                if (payload.getTransferDescription() != null)
                    tvValueDescription.setText(payload.getTransferDescription());
            }
        }
    }
}