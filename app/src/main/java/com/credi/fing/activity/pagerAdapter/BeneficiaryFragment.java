package com.credi.fing.activity.pagerAdapter;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.credi.fing.R;
import com.credi.fing.activity.Beneficiaire;
import com.credi.fing.activity.TransferActivity;
import com.credi.fing.enums.TypeTransFert;
import com.credi.fing.pojo.AccountOption;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.SelectService;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
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

    TransferActivity activity;
    TextInputLayout input,fieldNom;
    TextInputEditText nom,id;
    Attribut attribut;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        input=view.findViewById(R.id.textField);
        input.setHint("Numéro du compte");
        fieldNom=view.findViewById(R.id.textFieldNom);
        id=view.findViewById(R.id.id);
        nom=view.findViewById(R.id.nom);
        attribut=new Attribut();
        attribut.setSubLabel("clientName");
         activity= (TransferActivity) view.getContext();

        if(activity!=null&&activity.getTransferPayload()!=null){
           // S.toast(getContext(),"input -v");
            id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectDialogue(activity.getToAccountOptions(), null, "accountNo", "acountNo", id, attribut);
                }
            });
        }
    }

    private void showSelectDialogue(final List<AccountOption> data, Object sec, String label, String field, TextInputEditText editText,
                                    Attribut attribut) {
        View dialogView = Ut.getView(getContext(), R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        if(activity.getTypeTransFert()== TypeTransFert.TIERS){
            MaterialButton add=dialogView.findViewById(R.id.outlinedButton);
            LinearLayout espace=dialogView.findViewById(R.id.espace);
            LinearLayout ladd=dialogView.findViewById(R.id.add_bouton);
            ladd.setVisibility(VISIBLE);
            espace.setVisibility(VISIBLE);
            add.setText("Plus");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(getContext(), Beneficiaire.class)
                            .putExtra("add","add")
                            .putExtra("titre","Bénéficiaires"));
                    activity.finish();
                }
            });
        }

        final AlertDialog alertDialog = builder.create();

        List<Object> selection;
        if (sec == null) selection = new ArrayList<>();
        else selection = (List<Object>) sec;

        SelectService selectService = new SelectService(getContext(), new ArrayList<>(data), label, attribut,
                (d, v, k) -> {
                    d.cancel();
                    // String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                    AccountOption accountOption=data.get(Integer.parseInt(k + ""));;
                   // AccountOption accountOption=activity.getToAccountOptions().get(item.getItemId()-1);
                    id.setText(accountOption.getAccountNo());
                    nom.setText(accountOption.getClientName());
                    activity.updateTransferPayload("toOfficeId",accountOption.getOfficeId());
                    activity.updateTransferPayload("toClientId",accountOption.getClientId());
                    activity.updateTransferPayload("toAccountType",accountOption.getAccountType().getIdServeur());
                    activity.updateTransferPayload("toAccountId",accountOption.getAccountId());
                    activity.setCurrentePage(2);
                    activity.setToAccountOption(accountOption);

                })
                .setTitle("Compte bénéficiaire".toUpperCase())
                .setMultiselect(false)
                .setSelect(selection);

        View view = selectService.view(alertDialog);

        View btn1 = Ut.getView(getContext(), R.layout.outline_bouton);
        MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
        mtbt1.setText("Valider la sélection");
        lm.addView(view);
        //  lm.addView(btn1);


        alertDialog.show();
        mtbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                String fs = field.contains(":") ? field.substring(0, field.indexOf(":")) : field;
                // object=Ut.setField(fs,object,selectService.getSelect());
                editText.setText(selectService.getStringSelect());
                //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
            }
        });

    }
}