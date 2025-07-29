package com.credi.fing.activity.pagerBeneficiaireAdd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.credi.fing.R;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.utils.S;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeneficiaireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeneficiaireFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BeneficiaireFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeneficiaireFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeneficiaireFragment newInstance(String param1, String param2) {
        BeneficiaireFragment fragment = new BeneficiaireFragment();
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

    //Libelle service

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beneficiaire, container, false);
    }
    TextInputLayout input,fieldNom,textFieldCompte;
    TextInputEditText nom,id,nomComp;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         input=view.findViewById(R.id.textField);
         fieldNom=view.findViewById(R.id.textFieldNom);
        textFieldCompte=view.findViewById(R.id.textFieldCompte);
        nomComp=view.findViewById(R.id.cpte);
         nom=view.findViewById(R.id.nom);
        id=view.findViewById(R.id.id);
        input.setHint("Nom du bureau");

        AddBeneciaireActivity activity= (AddBeneciaireActivity) view.getContext();

        if(activity!=null&&activity.beneficiary!=null){
            if(activity.beneficiary.getOfficeName()!=null)
                id.setText(activity.beneficiary.getOfficeName());

            if(activity.beneficiary.getClientName()!=null){
                nom.setText(activity.beneficiary.getClientName());
            }
            if(activity.beneficiary.getAccountNumber()!=null){
                nomComp.setText(activity.beneficiary.getAccountNumber());
            }
        }


        nom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value=s.toString();
              if(activity!=null){
                  if(!value.isEmpty()){
                      fieldNom.setError(null);
                      activity.updateBeneFiciaire("clientName",value);
                  }else {
                      activity.updateBeneFiciaire("clientName",null);
                  }
              }
            }
        });
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value=s.toString();
                if(activity!=null){
                    if(!value.isEmpty()){
                        input.setError(null);
                        activity.updateBeneFiciaire("officeName",value);
                    }else {
                        activity.updateBeneFiciaire("officeName",null);
                    }
                }
            }
        });

        nomComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value=s.toString();
                if(activity!=null){
                    if(!value.isEmpty()){
                        textFieldCompte.setError(null);
                        activity.updateBeneFiciaire("accountNumber",value);
                    }else {
                        activity.updateBeneFiciaire("accountNumber",null);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AddBeneciaireActivity activity= (AddBeneciaireActivity) getActivity();

        if(activity!=null&&activity.beneficiary!=null){
            if(activity.beneficiary.getOfficeName()!=null)
                id.setText(activity.beneficiary.getOfficeName());

            if(activity.beneficiary.getClientName()!=null){
                nom.setText(activity.beneficiary.getClientName());
            }
            if(activity.beneficiary.getAccountNumber()!=null){
                nomComp.setText(activity.beneficiary.getAccountNumber());
            }
        }

        if(activity!=null&&activity.isClikSubmit()){
             if(input!=null&&activity.getBeneficiary().getOfficeName()==null){
                 input.setError("Champ obligatoire");
             }
            if(fieldNom!=null&&activity.getBeneficiary().getClientName()==null){
                fieldNom.setError("Champ obligatoire");
            }
            if(textFieldCompte!=null&&activity.getBeneficiary().getAccountNumber()==null){
                textFieldCompte.setError("Champ obligatoire");
            }
        }
    }
}