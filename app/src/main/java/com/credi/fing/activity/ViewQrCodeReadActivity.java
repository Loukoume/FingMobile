package com.credi.fing.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fing.R;
import com.credi.fing.activity.pagerBeneficiaireAdd.AddBeneciaireActivity;
import com.credi.fing.entity.Beneficiary;
import com.credi.fing.publics.composant.BoutonCp;
import com.credi.fing.publics.composant.TwoBoutonCp;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.S;
import com.credi.fing.utils.QRCodeUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ViewQrCodeReadActivity extends AppCompatActivity {

    LinearLayout lbouton;
    TwoBoutonCp boutonCp;
    Beneficiary beneficiary;
    TextView tvResult;
    TextView tvTitulaire;
    TextView tvType;
    TextView tv_infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qr_code_read);
        lbouton = findViewById(R.id.lbouton);
        tvResult = findViewById(R.id.tv_account_number);
        tvTitulaire = findViewById(R.id.tv_account_holder);
        tvType = findViewById(R.id.tv_type_compte);
        tv_infos = findViewById(R.id.tv_infos);
        if (getIntent().hasExtra("qr_data")) {
            String qrText = getIntent().getStringExtra("qr_data");
            try {
                String js = toJson(qrText);
                beneficiary = new Beneficiary().fromJs(js);
                tvResult.setText(beneficiary.getAccountNumber());
                tvTitulaire.setText(beneficiary.getClientName());
                tvType.setText(beneficiary.getAccountType().getValue());
                if (existe()) {
                    existe = true;
                    tv_infos.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {

            }
        } else {
            Intent intent = getIntent();
            String action = intent.getAction();
            String type = intent.getType();

            if (Intent.ACTION_SEND.equals(action) && type != null && type.startsWith("image/")) {
                Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    processQrImage(imageUri);
                }
            }

            pickImageFromGallery();
        }
        boutonCp = new TwoBoutonCp(this)
                .setView(lbouton)
                .setTitle2("Valider")
                .setOnClickView1((c, i) -> {
                    pickImageFromGallery();
                })
                .setOnClickView2((c, i) -> {
                    if (beneficiary != null) {
                        if (!existe) {
                            startActivity(new Intent(this, AddBeneciaireActivity.class)
                                    .putExtra("beneficiary", beneficiary));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        finish();
                    }
                })
                .setTitle1("Code Qr");

        lbouton.addView(boutonCp.view());
    }

    // Déclarez ceci comme champ de classe
    private static final int REQUEST_PICK_IMAGE = 1001;

    // Lancez l’intent au clic d’un bouton
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                processQrImage(imageUri);
            }
        }
    }

    boolean existe = false;

    private void processQrImage(Uri imageUri) {
        existe = false;
        String qrText = QRCodeUtil.decodeQrFromUri(this, imageUri);
        if (qrText != null) {
            // Affiche le texte dans un TextView

            if (!qrText.isEmpty()) {
                try {
                    String js = toJson(qrText);
                    beneficiary = new Beneficiary().fromJs(js);
                    tvResult.setText(beneficiary.getAccountNumber());
                    tvTitulaire.setText(beneficiary.getClientName());
                    tvType.setText(beneficiary.getAccountType().getValue());
                    if (existe()) {
                        existe = true;
                        tv_infos.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Dialogue.showDialog(this,
                            Dialogue.neutreDialog(e.getMessage() + "", "Alerte", this));
                }
            }
        } else {
            S.toast(this, "Impossible de décoder le QR Code.");
        }
    }

    private String toJson(String text) throws Exception {
        //decoder
        return text;
    }

    private boolean existe() {
        if (beneficiary != null && Beneficiaire.listBeneficiaires != null) {
            List<Object> ben = Beneficiaire.listBeneficiaires
                    .stream().filter(o -> Objects.equals(beneficiary.getAccountNumber(),
                            Ut.getValue(o, "accountNumber"))).collect(Collectors.toList());
            return !ben.isEmpty();
        }
        return false;
    }

}