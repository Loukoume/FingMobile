package com.credi.fing.activity.pagerAdapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.credi.fing.R;
import com.credi.fing.activity.Beneficiaire;
import com.credi.fing.activity.TransferActivity;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.publics.utils.S;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeneficiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeneficiaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BeneficiaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeneficiaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeneficiaryFragment newInstance(String param1, String param2) {
        BeneficiaryFragment fragment = new BeneficiaryFragment();
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
        return inflater.inflate(R.layout.fragment_beneficiary, container, false);
    }

    TextInputLayout input,fieldNom;
    TextInputEditText nom,id;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        input=view.findViewById(R.id.textField);
        input.setHint("Numéro du compte");
        fieldNom=view.findViewById(R.id.textFieldNom);
        id=view.findViewById(R.id.id);
        nom=view.findViewById(R.id.nom);

        TransferActivity activity= (TransferActivity) view.getContext();

        if(activity!=null&&activity.getTransferPayload()!=null){
           // S.toast(getContext(),"input -v");
            id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String m[]=activity.getToAccountOptions().stream().filter(o->o!=null).map(o->
                            o.getAccountNo()
                    ).collect(Collectors.toList()).toArray(new String[0]);

                    PopupMenu pop= S.popupMenu(v,m);
                    pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AccountOption accountOption=activity.getToAccountOptions().get(item.getItemId()-1);
                            id.setText(accountOption.getAccountNo());
                            nom.setText(accountOption.getClientName());
                            activity.updateTransferPayload("toOfficeId",accountOption.getOfficeId());
                            activity.updateTransferPayload("toClientId",accountOption.getClientId());
                            activity.updateTransferPayload("toAccountType",accountOption.getAccountType().getIdServeur());
                            activity.updateTransferPayload("toAccountId",accountOption.getAccountId());
                            activity.setCurrentePage(2);
                            return false;
                        }
                    });
                }
            });
        }
    }
}