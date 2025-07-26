package com.credi.fing.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fing.R;
import com.credi.fing.entity.Beneficiary;
import com.credi.fing.publics.composant.BoutonCp;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.S;
import com.credi.fing.utils.QRCodeUtil;

public class ViewQrCodeReadActivity extends AppCompatActivity {

    LinearLayout lbouton;
    BoutonCp boutonCp;
    Beneficiary beneficiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qr_code_read);
        lbouton=findViewById(R.id.lbouton);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type   = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null && type.startsWith("image/")) {
            Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (imageUri != null) {
                processQrImage(imageUri);
            }
        }
        boutonCp=new BoutonCp(this)
                .setView(lbouton)
                .setTitle("Valider")
                .setOnClickView((c,i)->{

                });
        pickImageFromGallery();
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

    private void processQrImage(Uri imageUri) {
        String qrText = QRCodeUtil.decodeQrFromUri(this, imageUri);
        if (qrText != null) {
            // Affiche le texte dans un TextView
            TextView tvResult = findViewById(R.id.tv_account_number);
            TextView tvTitulaire = findViewById(R.id.tv_account_holder);
            TextView tvType = findViewById(R.id.tv_type_compte);
            if(!qrText.isEmpty()){
                try {
                    String js=toJson(qrText);
                    beneficiary=new Beneficiary().fromJs(js);
                    tvResult.setText(beneficiary.getAccountNumber());
                    tvTitulaire.setText(beneficiary.getClientName());
                    tvType.setText(beneficiary.getAccountType().getValue());
                }catch (Exception e){
                    Dialogue.showDialog(this,
                            Dialogue.neutreDialog(e.getMessage()+"","Alerte",this));
                }
            }
        } else {
            S.toast(this, "Impossible de décoder le QR Code.");
        }
    }

    private String toJson(String text) throws Exception{
        //decoder
        return text;
    }

}